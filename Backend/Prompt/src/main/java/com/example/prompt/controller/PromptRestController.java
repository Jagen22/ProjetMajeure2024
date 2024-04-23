package com.example.prompt.controller;

import com.example.api_ai.ImagePromptDTO;
import com.example.api_ai.TextPromptDTO;
import com.example.postdto.PostDTO;
import com.example.profiledto.ProfileDTO;
import com.example.responsedto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//ONLY FOR TEST NEED ALSO TO ALLOW CROOS ORIGIN ON WEB BROWSER SIDE
@CrossOrigin
@RestController
public class PromptRestController {

	@Autowired
	private PromptService promptService;

	@RequestMapping(method=RequestMethod.POST,value="/new_post")
	private Integer newPost(@RequestBody PostDTO postDTO) {
        return promptService.receiveNewPrompt(postDTO);
	}

	@RequestMapping(method=RequestMethod.POST,value="/new_profile")
	private Integer newProfile(@RequestBody List<String> params) {
		return promptService.receiveNewProfile(params);
	} //0 : description Llama
	//1 : description Fooocus
	//TODO createProfile
}
