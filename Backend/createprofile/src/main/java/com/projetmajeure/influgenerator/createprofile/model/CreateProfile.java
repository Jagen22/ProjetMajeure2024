package com.projetmajeure.influgenerator.createprofile.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.List;

@Entity
public class CreateProfile{

	@Id
    private Integer id;

	@Column(length = 4096)
	private String params;

	public CreateProfile() {
	}

	public CreateProfile(Integer id, String params) {
		this.id= id;
		this.params = params;
	}

    public Integer getId(){
        return this.id;
    }

    public void setId(Integer id){
        this.id=id;
    }

	public String getParams() {
		return params;
	}
    
	public void setParams(String params) {
		this.params = params;
	}

}
