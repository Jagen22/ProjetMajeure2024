package com.projetmajeure.influgenerator.profile.tools;

import com.example.profiledto.ProfileDTO;
import com.projetmajeure.influgenerator.profile.model.Profile;

public class ProfileMapper {

    public static ProfileDTO FromProfileToDTO(Profile p){
		ProfileDTO pDTO = new ProfileDTO();
		pDTO.setId(p.getId());
		pDTO.setHistId(p.getHistId());
		pDTO.setDescription(p.getDescription());
		pDTO.setImageUrl(p.getImageUrl());
		return pDTO;
	}
	
	public static Profile FromDTOTOProfile(ProfileDTO pDTO) {
		Profile p = new Profile();
		System.out.println(pDTO.getHistId());
		p.setHistId(pDTO.getHistId());
		p.setDescription(pDTO.getDescription());
		p.setImageUrl(pDTO.getImageUrl());
		return p;
	}
}
