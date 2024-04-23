package com.projetmajeure.influgenerator.profile.repository;


import com.projetmajeure.influgenerator.profile.model.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Integer> {

	public Optional<Profile> findById(Integer id);

}

