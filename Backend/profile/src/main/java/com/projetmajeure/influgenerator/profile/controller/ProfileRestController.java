package com.projetmajeure.influgenerator.profile.controller;


import com.example.profiledto.ProfileDTO;
import com.example.responsedto.ResponseDTO;
import com.projetmajeure.influgenerator.profile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class ProfileRestController {

    @Autowired
    ProfileService pService;

    @RequestMapping(method=RequestMethod.GET, value="/{id}")
    public ProfileDTO getProfile(@PathVariable Integer id){
        return pService.getProfile(id);
    }

    @RequestMapping(method=RequestMethod.POST, value="/updatePostList/{profileId}")
    public String updatePostList(@PathVariable Integer profileId, @RequestBody Integer postId){
        pService.updatePostList(profileId, postId);
        return "OK";
    }

    @RequestMapping(method=RequestMethod.GET, value="/getall")
    public List<ProfileDTO> getAllProfile(){
        return pService.getAllProfile();
    }

    @RequestMapping(method=RequestMethod.POST, value="/add")
    public String addProfile(@RequestBody ProfileDTO profile){
        pService.addProfile(profile);
        return "OK";
    }

    @RequestMapping(method=RequestMethod.PUT, value="/{id}/update")
    public String updateProfile(@PathVariable Integer id, @RequestBody ResponseDTO response){
        pService.updateProfile(id, response);
        return "OK";
    }

    @RequestMapping(method=RequestMethod.POST,value ="/updateHistory/{profileId}")
    public String updateHistory(@PathVariable Integer profileId, @RequestBody String text){
        pService.updateHistory(profileId, text);
        return "OK";
    }

    @RequestMapping(method=RequestMethod.GET,value="/getHistory/{profileId}")
    public ProfileDTO getHistory(@PathVariable Integer profileId){
        return pService.getHistory(profileId);
    }
    
}