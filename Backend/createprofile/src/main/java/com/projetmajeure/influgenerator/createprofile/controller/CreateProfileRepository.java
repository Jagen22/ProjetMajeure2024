package com.projetmajeure.influgenerator.createprofile.controller;


import com.projetmajeure.influgenerator.createprofile.model.CreateProfile;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CreateProfileRepository extends CrudRepository<CreateProfile, Integer> {
    public Optional<CreateProfile> findById(Integer promptId);
}