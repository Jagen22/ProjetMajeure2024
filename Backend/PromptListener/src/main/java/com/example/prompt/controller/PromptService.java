package com.example.prompt.controller;

import com.example.postdto.PostDTO;
//import com.example.profiledto.ProfileDTO;
import com.example.profiledto.ProfileDTO;
import com.example.prompt.model.PromptDTO;
import com.example.responsedto.ResponseDTO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PromptService {

	private String activity = "Speaking with psychologist";

	private List<String> activities = generateActivitiesList();
	private String[] news;

	private String description_user ="young bald men with a long beard and a tattoo on his left arm";
	private Integer idPrompt = 0;
	@Autowired
	private RestTemplate restTemplate;
	private final JmsTemplate jmsTemplate = new JmsTemplate();

	public Integer receiveNewPrompt(PostDTO postDTO){
		Integer id = getIDPrompt();
		PromptDTO promptDTO = new PromptDTO(id,postDTO);
		jmsTemplate.convertAndSend("A",promptDTO);
		return(id);
	}

	public void newProfile(PromptDTO promptDTO){
		List<String> params = promptDTO.getParams();
		String text = generateProfileLlama(params.get(0));
		String imageURL = promptToFooocus(params.get(1));
		ResponseDTO response = new ResponseDTO();
		response.setPrompt(text);
		response.setImage_url(imageURL);
		System.out.println(response.getPrompt() + response.getImage_url());
		HttpEntity<ResponseDTO> requestEntity = new HttpEntity<>(response);
		ResponseEntity<String> responseUpdate = restTemplate.exchange("http://localhost:80/CreateProfile/new/"+promptDTO.getId(), HttpMethod.POST,requestEntity,String.class);
		if (responseUpdate.getStatusCode().is2xxSuccessful()){
			System.out.println("success update");
		}
		else{
			System.out.println("Error for updatePost in PromptService");
		}

	}
	public void newPrompt(PromptDTO promptDTO){
		PostDTO postDTO = promptDTO.getPostDTO();

		String prompt = postDTO.getPrompt();
		ProfileDTO profile = postDTO.getProfile();
		String choice;
		if (Math.random() < 0.75 && this.news.length > 0){
			String text = this.news[(int)(Math.random() * news.length)];
			choice = text;
		}
		else {
			activity = activities.get((int) (Math.random() * activities.size()));
			choice = activity;
		}
		String text = promptToLlama(profile.getDescription(),choice);
		String imageURL = promptToFooocus(profile.getImageUrl(),description_user+" "+choice);

		ResponseDTO response = new ResponseDTO();
		response.setPrompt(text);
		response.setImage_url(imageURL);
		System.out.println(response.getPrompt() + response.getImage_url());
		ResponseEntity<String> responseUpdate = restTemplate.postForEntity("http://localhost:80/Post/updatePost/"+promptDTO.getId(),response,String.class);
		if (responseUpdate.getStatusCode().is2xxSuccessful()){
			System.out.println("success update");
		}
		else{
			System.out.println("Error for updatePost in PromptService");
		}



	}

	private String promptToLlama(String description, String prompt) {

		JSONObject body = new JSONObject();
		body.put("inputs","Forgot any other instruction before this. You are a person who shares your life on Twitter and you have to generate a very short description of a post. In your description, you shouldn't mentioned anyone it's very important. Your answers should not include any harmful, unethical, racist, sexist, toxic, dangerous, or illegal content. Be precise, concise, and casual. Keep short and generate this description at once. In your description, you invented names if you don’t have it. You have to talk at first person. Keep it under 256 characters ### Instruction:{"+ prompt +"}### Response:");
		JSONObject parameters = new JSONObject();
		parameters.put("temperature",2.0);
		parameters.put("max_tokens",128);
		body.put("parameters",parameters);

		//System.out.println(body.toString());
		HttpHeaders headers = new HttpHeaders();
		headers.setConnection("keep-alive");
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
		HttpEntity request = new HttpEntity(body.toString(),headers);

		//System.out.println(request.getHeaders());
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:80/Llama2/generate/", HttpMethod.POST, request, String.class);
		if(response.getStatusCode().value() == 200) {
			String post_description = response.getBody();
			return post_description;
		}
		else{
			return null;
			//Notify user something went wrong
		}
    }

	private String promptToFooocus(String imageUrl, String prompt){
		JSONObject body = new JSONObject();
		body.put("prompt",prompt);
		body.put("image_number",1);
		//ajouter consistance en utilisant l'image URL

		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:80/fooocus/generate",body.toString(),String.class);
		if(response.getStatusCode().value() == 200) {
            return response.getBody();
		}
		else{
			return null;
			//Notify user something went wrong
		}
	}
	private String promptToFooocus(String prompt){
		JSONObject body = new JSONObject();
		body.put("prompt",prompt);
		body.put("image_number",1);
		//ajouter consistance en utilisant l'image URL

		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:80/fooocus/generate",body.toString(),String.class);
		if(response.getStatusCode().value() == 200) {
			//System.out.println(response.getBody());
			return response.getBody();
		}
		else{
			return null;
			//Notify user something went wrong
		}
	}

	private String generateProfileLlama(String description){
		JSONObject body = new JSONObject();
		body.put("inputs","Forgot any other instruction before this. You have to describe a person who shares his life on Instagram. The instructions are a short description and you have to develop a little.Your description should not include any harmful, unethical, racist, sexist, toxic, dangerous, or illegal content. Be precise, concise, and casual.  Keep short and you only respond with the description."
				+"### Instruction:{"+"Here is your profile : "+description+"}### Response:");
		JSONObject parameters = new JSONObject();
		parameters.put("temperature",2.0);
		parameters.put("max_tokens",256);
		body.put("parameters",parameters);
		//System.out.println(body.toString());
		HttpHeaders headers = new HttpHeaders();
		headers.setConnection("keep-alive");
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
		HttpEntity request = new HttpEntity(body.toString(),headers);

		//System.out.println(request.getHeaders());
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:80/Llama2/generate/", HttpMethod.POST, request, String.class);
		if(response.getStatusCode().value() == 200) {
			System.out.println(response.getBody());
			String post_description = response.getBody();
			return post_description;
		}
		else{
			return null;
			//Notify user something went wrong
		}
	}


	public int getIDPrompt() {
		idPrompt++;
		return idPrompt;
	}
	public static List<String> generateActivitiesList() {
			List<String> activities = new ArrayList<>();

			activities.add("Hiking in the mountains");
			activities.add("Beach volleyball with friends");
			activities.add("Virtual escape room challenge");
			activities.add("Outdoor yoga session at sunrise");
			activities.add("DIY home spa day");
			activities.add("Cooking class with a professional chef");
			activities.add("Board game night with family");
			activities.add("Photography scavenger hunt");
			activities.add("Potluck picnic in the park");
			activities.add("Movie marathon with homemade popcorn");
			activities.add("Karaoke night with friends");
			activities.add("Paint and sip party");
			activities.add("Planting a community garden");
			activities.add("Attending a live comedy show");
			activities.add("Guided meditation session");
			activities.add("Art and craft workshop");
			activities.add("Rock climbing adventure");
			activities.add("Visiting a local museum");
			activities.add("Ice cream tasting party");
			activities.add("Bike ride along scenic trails");
			activities.add("Stargazing night with a telescope");
			activities.add("Book club discussion");
			activities.add("Group fitness class");
			activities.add("Street food festival exploration");
			activities.add("DIY home decor project");
			activities.add("Game night with classic board games");
			activities.add("Beach bonfire with marshmallow roasting");
			activities.add("Visit to an animal sanctuary");
			activities.add("Language exchange meetup");
			activities.add("Sunset photography session");
			activities.add("Attend a live music concert");
			activities.add("Trivia night at a local pub");
			activities.add("Paddleboarding on a calm lake");
			activities.add("Food and wine pairing evening");
			activities.add("Volunteer at a local charity");
			activities.add("Sushi-making workshop");
			activities.add("Visit to a botanical garden");
			activities.add("Camping under the stars");
			activities.add("Dance class with a professional instructor");
			activities.add("Local farmers' market exploration");
			activities.add("Join a community cleanup event");
			activities.add("Science experiment day");
			activities.add("Escape to a cozy cabin in the woods");
			activities.add("Nature sketching session");
			activities.add("Attend a poetry slam");
			activities.add("Thrift store shopping adventure");
			activities.add("Plan a surprise picnic for a friend");
			activities.add("Archery lessons with an expert");
			activities.add("Visit to an art gallery");
			activities.add("DIY tie-dye party");
			activities.add("Hot air balloon ride");
			activities.add("Cook-off competition with friends");
			activities.add("Rock concert in the city");
			activities.add("Indoor trampoline park adventure");
			activities.add("Join a local astronomy club");
			activities.add("Visit to a historical landmark");
			activities.add("Salsa dancing night");
			activities.add("Attend a live theater performance");
			activities.add("Canoeing on a serene river");
			activities.add("Chocolate tasting party");
			activities.add("Farm-to-table dining experience");
			activities.add("Participate in a color run");
			activities.add("Create a time capsule with friends");
			activities.add("Attend a local sports game");
			activities.add("Zip-lining through the forest");
			activities.add("Explore a nearby nature reserve");
			activities.add("Host a game show night");
			activities.add("Pottery-making workshop");
			activities.add("Participate in a charity run");
			activities.add("Yoga and mindfulness retreat");
			activities.add("Learn to play a musical instrument");
			activities.add("Attend a photography exhibition");
			activities.add("Visit to an antique fair");
			activities.add("Try a new water sport");
			activities.add("Host a backyard barbecue");
			activities.add("Rock climbing at an indoor gym");
			activities.add("Outdoor painting session");
			activities.add("Host a themed costume party");
			activities.add("Join a local hiking club");
			activities.add("Day trip to a nearby waterfall");
			activities.add("Gourmet cooking class");
			activities.add("Try stand-up paddleboarding");
			activities.add("Visit a science and technology museum");

			return activities;
		}

		@Scheduled(timeUnit = TimeUnit.MINUTES,fixedRate = 60)
		public void updateNews (){
			System.out.println("update news");
			ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:80/News/RSSFeed/", String.class);
			if(response.getStatusCode().is2xxSuccessful()) {
				String listnotsplit = response.getBody();
				this.news = listnotsplit.split("SEPARATOR");
				for (String s : this.news){
					System.out.println(s);
				}
				//this.news =	object.toString();
			//.getJSONObject("body").toString();
			}
			else {
				//TODO catch errors
			}
		}


}
