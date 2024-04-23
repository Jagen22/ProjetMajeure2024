package com.projetmajeure.influgenerator.createprofile.controller;

import com.example.responsedto.ResponseDTO;
import org.springframework.web.bind.annotation.RestController;

//import com.projetmajeure.influgenerator.profilepublic.dto.ResponseDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class CreateProfileRestController {

    @Autowired
    CreateProfileService cpService;

    @RequestMapping(method=RequestMethod.POST, value="/new")
    public String add(@RequestBody List<String> params){
        cpService.newProfile(params);
        return "OK";
    }

    @RequestMapping(method=RequestMethod.POST, value="/new/{promptId}")
    public String valid(@PathVariable Integer promptId, @RequestBody ResponseDTO response){
        cpService.validProfile(promptId, response);
        return "OK";
    }
}
