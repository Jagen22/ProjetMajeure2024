package com.projetmajeure.influgenerator.createprofile.controller;

import java.util.Date;
import java.util.List;

import com.example.historydto.CreateHistoryDTO;
import com.example.profiledto.ProfileDTO;
import com.example.responsedto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.projetmajeure.influgenerator.createprofile.model.CreateProfile;

//import com.projetmajeure.influgenerator.profilepublic.dto.ProfileDTO;
//import com.projetmajeure.influgenerator.profilepublic.dto.ResponseDTO;

@Service
public class CreateProfileService {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CreateProfileRepository cpRepo;
    String paramsString = "";


    public Integer newPrompt(List<String> params) {
        String Url = "http://localhost:80/Prompt/new_profile";
        ResponseEntity<Integer> response = restTemplate.postForEntity(Url, params, Integer.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            //TODO catch errors
        }
        return null;
    }

    public Integer newHist(String description){
        String Url = "http://localhost:80/History/createHistory";
        Date currentDate = new Date();
        CreateHistoryDTO newHist= new CreateHistoryDTO();
        newHist.setText(description);
        newHist.setDate(currentDate);
        ResponseEntity<Integer> response = restTemplate.postForEntity(Url, newHist, Integer.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            //TODO catch errors
        }
        return null;
    }

    public void newProfile(List<String> params){
        Integer promptId = newPrompt(params);
        for (String p : params) {
            paramsString = paramsString + "|" + p;
        }
        cpRepo.save(new CreateProfile(promptId, paramsString));
    }

    public void validProfile(Integer promptId, ResponseDTO response) {
        
        CreateProfile cprofile = cpRepo.findById(promptId).get();

        Integer histId = newHist(response.getPrompt());
        String Url = "http://localhost:80/Profile/add";
        ProfileDTO newProfile = new ProfileDTO(histId, response.getPrompt(), response.getImage_url());
        System.out.println(newProfile.getDescription() + newProfile.getImageUrl());
        ResponseEntity<String> response2 = restTemplate.postForEntity(Url, newProfile, String.class);
        if (response2.getStatusCode().is2xxSuccessful()) {
            System.out.println("success update");
        } else {
            System.out.println("Error for updatePost in PromptService");
        }

    }
    
}
