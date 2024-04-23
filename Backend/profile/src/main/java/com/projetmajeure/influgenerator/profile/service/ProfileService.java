package com.projetmajeure.influgenerator.profile.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.example.historydto.HistoryDTO;
import com.example.profiledto.ProfileDTO;
import com.example.responsedto.ResponseDTO;
import com.projetmajeure.influgenerator.profile.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.projetmajeure.influgenerator.profile.model.Profile;
import com.projetmajeure.influgenerator.profile.tools.ProfileMapper;
import org.springframework.web.client.RestTemplate;

@Service
public class ProfileService {

	@Autowired
	ProfileRepository pRepo;
	@Autowired
	RestTemplate restTemplate;

    public ProfileDTO getProfile(Integer id) {
        Optional<Profile> p = null;
        ProfileDTO pdto = null;
        if (pRepo.findById(id) != null){
            p = pRepo.findById(id);
            pdto = ProfileMapper.FromProfileToDTO(p.get());
        }
		System.out.println(pdto.getImageUrl());
        return pdto;
    }

    public void addProfile(ProfileDTO profile) {
        Profile p = ProfileMapper.FromDTOTOProfile(profile);
		System.out.println(p.getHistId());
		pRepo.save(p);
    }

    public List<ProfileDTO> getAllProfile() {
        List<ProfileDTO> pDtoList = new ArrayList<ProfileDTO>();
		Iterable<Profile> pList = pRepo.findAll();
		for(Profile p:pList) {
			pDtoList.add(ProfileMapper.FromProfileToDTO(p));
		}
		return pDtoList;
    }


    public void updatePostList(Integer profileId, Integer postId) {
        ProfileDTO pdto = getProfile(profileId);
        Profile p = ProfileMapper.FromDTOTOProfile(pdto);
        p.addPost(postId);
    }

    public void updateProfile(Integer profileId, ResponseDTO response){
        ProfileDTO pdto = getProfile(profileId);
        Profile p = ProfileMapper.FromDTOTOProfile(pdto);
        p.setImageUrl(response.getImage_url());
        p.setDescription(response.getPrompt());
    }

	public void updateHistory(Integer profileId, String text) {
		HistoryDTO hdto = new HistoryDTO(text);
		Optional<Profile> profile = pRepo.findById(profileId);
		Integer histId = profile.get().getHistId();
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:80/History/updateHistory/"+histId, hdto, String.class);
	}

	public ProfileDTO getHistory(Integer profileId) {
		Profile p = pRepo.findById(profileId).get();
		Integer histId = p.getHistId();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:80/History/getHistory/"+histId, String.class);
		if (response.getStatusCode().is2xxSuccessful()) {
			ProfileDTO pdto = ProfileMapper.FromProfileToDTO(p);
			pdto.setDescription(response.getBody());
			return pdto;
		}
		else {
			//TODO catch errors
		}
		return null;
	}

	@Scheduled(timeUnit = TimeUnit.MINUTES,fixedRate = 3)
	public void newPost(){
		Iterable<Profile> pList = pRepo.findAll();
		for(Profile p:pList) {
			ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:80/Post/createPost/"+p.getId(),"", String.class);
			if (response.getStatusCode().is2xxSuccessful()){
				System.out.println("New post for profile "+ p.getId() +response.getBody());
			}
		}
	}
}
