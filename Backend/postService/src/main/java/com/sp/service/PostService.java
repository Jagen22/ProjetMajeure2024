package com.sp.service;

import com.example.postdto.PostDTO;
import com.example.profiledto.ProfileDTO;
import com.example.responsedto.ResponseDTO;
import com.sp.repository.PostRepository;
import com.sp.model.Post;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sp.dto.InstagramDTO;

import java.util.List;

@Service
public class PostService {

	@Autowired
	PostRepository postRepository;
	@Autowired
	RestTemplate restTemplate;
	
	
	public void createPost(Integer profileId) {
		ProfileDTO profileDTO = getProfileAndHistory(profileId);
		PostDTO postDTO = new PostDTO(profileDTO.getDescription(),profileDTO);
		Integer idPrompt = newPrompt(postDTO);
		Post post = postRepository.save(new Post(idPrompt,profileId,profileDTO.getDescription()));
	}

	//TODO changer pour la gestion de la réponse de l'api instagram
	public void updatePost(Integer idPrompt,ResponseDTO responseDTO) {
		String postInstaId = publishToSocialNetworks(responseDTO);
		Post post = postRepository.findByIdPrompt(idPrompt);
		updateHistory(post.getIdUser(),responseDTO.getPrompt());
		savePost(post.getIdUser(), idPrompt);
		//TODO if responseDTO is null notify Client error else notify Client success
		notifyClient(idPrompt,"success");

	}
	private ProfileDTO getProfileAndHistory(Integer profileId) {
		ResponseEntity<ProfileDTO> response = restTemplate.getForEntity("http://localhost:80/Profile/getHistory/"+profileId ,ProfileDTO.class);
		if(response.getStatusCode().is2xxSuccessful()) {
			return response.getBody();
		}
		else {
			//TODO catch errors
		}
			return null;
		}

	
	private Integer newPrompt(PostDTO postDTO) {
		ResponseEntity<Integer> response = restTemplate.postForEntity("http://localhost:80/Prompt/new_post", postDTO ,Integer.class); // PromptService
		if(response.getStatusCode().is2xxSuccessful()) {
			return response.getBody();
		}
		else {
			//TODO catch errors
		}
		return null;
	}
	
	private String publishToSocialNetworks(ResponseDTO promptDTO) {
		String result = "";
		String access_token = "1747556289343795200-uTYUl2s41LKYMrFhIwUxOOOCOEmOfV";
		String secret = "E6Nvb60f2M2VeqPGfqXkAX4dyB9LTONQibN8eAbR12Fx7";
		JSONObject body = new JSONObject();
		body.put("access_token",access_token);
		body.put("access_token_secret",secret);
		String text = promptDTO.getPrompt();
		String[] listText = text.split("### Note ");
		String[] listText2=  listText[0].split("###");
		promptDTO.setPrompt(listText2[1]);
		if (promptDTO.getPrompt().length() >256){
			promptDTO.setPrompt(promptDTO.getPrompt().substring(0,256));
		}
		body.put("text",promptDTO.getPrompt());
		body.put("imageURL",promptDTO.getImage_url());
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:80/Twitter/PostWithPicture", body.toString() ,String.class); //TwitterAPI
		/*Boolean instagramAnswer = restTemplate.postForObject("http://instagram.com/Insta/newPicture", promptDTO , Boolean.class); //InstagramAPI
		if(instagramAnswer) { //TODO catch API exceptions
			result = "id"; //get from insta answer
		}
		else {
			//TODO catch errors
		}*/
		if (response.getStatusCode().is2xxSuccessful()) {
			return response.getBody();
		}
		else {
			//TODO catch errors
		}
		System.out.println(promptDTO.getPrompt()+promptDTO.getImage_url());
		return null;
	}
	
	private String updateHistory(Integer profileId, String text) {
		HttpEntity<String> requestEntity = new HttpEntity<>(text);
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:80/Profile/updateHistory/"+profileId,text,String.class);	//ProfileAPI
		if (response.getStatusCode().is2xxSuccessful()) {
			return response.getBody();
		}
		else {
			//TODO catch errors
		}
		return null;
	}
	
	private void savePost(Integer profileId,Integer postId) {
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:80/Profile/updatePostList/"+ profileId,postId,String.class);
		if (response.getStatusCode().is2xxSuccessful()) {
			//TODO catch errors
		}
		else {
			//TODO catch errors
		}
	
	}
	
	private void notifyClient(Integer promptId,String message){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String requestBody = "{\"promptId\":\""+promptId+"\",\"message\":\""+message+"\"}";
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

		ResponseEntity<String> response = restTemplate.exchange("http://localhost:80/SocketIO/notifyClient", HttpMethod.POST, requestEntity, String.class);
		if (response.getStatusCode().is2xxSuccessful()) {
			//TODO catch errors
		}
		else {
			//TODO catch errors
		}
	}
	
	public InstagramDTO getPost(Integer postId) {
		ResponseEntity<InstagramDTO> response = restTemplate.getForEntity("http://localhost:80/Instagram/getPost/"+postId.toString() ,InstagramDTO.class);
		if(response.getStatusCode().is2xxSuccessful()) {
			return response.getBody();
		}
		else {
			//TODO catch errors
		}
		return null;
	}
	
}
