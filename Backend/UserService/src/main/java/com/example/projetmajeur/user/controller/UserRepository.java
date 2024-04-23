package com.example.projetmajeur.user.controller;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.projetmajeur.user.model.UserModel;

public interface UserRepository extends CrudRepository<UserModel, Integer> {
	
	List<UserModel> findByLoginOrEmail(String login, String email);
	List<UserModel> findByLogin(String login); 
	List<UserModel> findByLoginAndPwd(String login, String pwd);

}
