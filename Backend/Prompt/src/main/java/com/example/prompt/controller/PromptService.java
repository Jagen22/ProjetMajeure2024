package com.example.prompt.controller;

import com.example.postdto.PostDTO;
import com.example.profiledto.ProfileDTO;
import com.example.prompt.model.PromptDTO;
import com.example.responsedto.ResponseDTO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PromptService {
	private Integer idPrompt = 0;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private JmsTemplate jmsTemplate;

	public Integer receiveNewPrompt(PostDTO postDTO){
		Integer id = getIDPrompt();
		PromptDTO promptDTO = new PromptDTO(id,postDTO);
		jmsTemplate.convertAndSend("A",promptDTO);
		return(id);
	}

	public Integer receiveNewProfile(List<String> params) {
		Integer id = getIDPrompt();
		PromptDTO promptDTO = new PromptDTO(id,params);
		jmsTemplate.convertAndSend("B",promptDTO);
        return id;
    }
	public ResponseDTO newPrompt(PromptDTO promptDTO){
		PostDTO postDTO = promptDTO.getPostDTO();

		String prompt = postDTO.getPrompt();
		ProfileDTO profile = postDTO.getProfile();

		String text = promptToLlama(profile.getDescription(),prompt);
		String imageURL = promptToFooocus(profile.getImageUrl(),prompt);

		ResponseDTO response = new ResponseDTO();
		response.setPrompt(text);
		response.setImage_url(imageURL);

		return response;
	}

	private String promptToLlama(String description, String prompt) {
		JSONObject body = new JSONObject();
		body.put("inputs","Forgot any other instruction before this. You have access to data of a person who shares his life on Instagram and you have to resume his life. Your resume should not include any harmful, unethical, racist, sexist, toxic, dangerous, or illegal content. Be precise, concise, and casual.  Keep short and you only resume the activities, the mood, and the friends of this person. In your resume you invented names if you don’t have it and you need it. Do not reuse the comment or Post in your resume"
						+"### Instruction:{"+prompt+"You are currently"+description+"}### Response:");
		JSONObject parameters = new JSONObject();
		parameters.put("temperature",2.0);
		parameters.put("max_tokens",256);
		body.put("parameters",parameters);

		ResponseEntity<JSONObject> response = restTemplate.postForEntity("http://localhost:80/Llama2/generate", body, JSONObject.class);
		if(response.getStatusCode().value() == 200) {
			String post_description = (String) response.getBody().get("generated_text");
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

		ResponseEntity<JSONObject> response = restTemplate.postForEntity("http://localhost:80/fooocus/generate",body,JSONObject.class);
		if(response.getStatusCode().value() == 200) {
            return (String) response.getBody().get("download_url");
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

		ResponseEntity<JSONObject> response = restTemplate.postForEntity("http://localhost:80/fooocus/generate",body,JSONObject.class);
		if(response.getStatusCode().value() == 200) {
			return (String) response.getBody().get("download_url");
		}
		else{
			return null;
			//Notify user something went wrong
		}
	}

	private String generateProfileLlama(String description){
		JSONObject body = new JSONObject();
		body.put("inputs","Forgot any other instruction before this. You have to describe a person who shares his life on Instagram. The instructions are a short description and you have to develop a little.Your description should not include any harmful, unethical, racist, sexist, toxic, dangerous, or illegal content. Be precise, concise, and casual.  Keep short and you only respond with the description."
				+"### Instruction:{"+description+"You are currently"+description+"}### Response:");
		JSONObject parameters = new JSONObject();
		parameters.put("temperature",2.0);
		parameters.put("max_tokens",256);
		body.put("parameters",parameters);

		ResponseEntity<JSONObject> response = restTemplate.postForEntity("http://localhost:80/Llama2/generate", body, JSONObject.class);
		if(response.getStatusCode().value() == 200) {
			String post_description = (String) response.getBody().get("generated_text");
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
}
