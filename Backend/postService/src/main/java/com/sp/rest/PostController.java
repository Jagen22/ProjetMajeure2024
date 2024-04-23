package com.sp.rest;

import com.example.responsedto.ResponseDTO;
import com.sp.dto.InstagramDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sp.service.PostService;

@RestController
public class PostController {
	@Autowired
	PostService pService;
	
	@RequestMapping("/")
	public String sayHello() {
		return "Hello World";
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/createPost/{profileId}")
	public String newPost(@PathVariable Integer profileId) {
		pService.createPost(profileId);
		return "OK";
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/getPost/{postId}")
	public InstagramDTO getPost(@PathVariable Integer postId) {
		InstagramDTO instagramDTO = pService.getPost(postId);
		return instagramDTO;
	}

	@RequestMapping(method=RequestMethod.POST,value="/updatePost/{postId}")
	public String updatePost(@PathVariable Integer postId,@RequestBody ResponseDTO responseDTO) {
		System.out.println("DTO"+ responseDTO.getPrompt());
		pService.updatePost(postId,responseDTO);
		return "OK";
	}

}
