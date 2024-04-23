package com.example.projetmajeur.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projetmajeur.user.model.UserDTO;
import com.example.projetmajeur.user.model.UserModel;
import com.example.projetmajeur.user.DTOMapper;
// import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {


	// @Autowired
	// // PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepository;


	public Iterable<UserModel> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<UserModel> getUser(Integer id) {
		return userRepository.findById(id);
	}

	public List<UserModel> getUserByLoginPwd(String login, String pwd) {
        return userRepository.findByLoginAndPwd(login, pwd);
    }

	public UserDTO addUser(UserDTO user) throws Exception {
		// Check if the login or email already exists
		if (!userRepository.findByLoginOrEmail(user.getLogin(), user.getEmail()).isEmpty()) {
			throw new Exception("Login or email already exists");
		}
		UserModel u = new UserModel(user);
		UserModel uBd = userRepository.save(u);
		return new UserDTO(uBd);
	}

	public UserDTO updateUser(UserDTO user) {
		UserModel u = fromUDtoToUModel(user);
		UserModel uBd =userRepository.save(u);
		return DTOMapper.fromUserModelToUserDTO(uBd);
	}

	public void deleteUser(Integer id) {
		userRepository.deleteById(Integer.valueOf(id));
	}


	private UserModel fromUDtoToUModel(UserDTO user) {
		UserModel u = new UserModel(user);
		return u;
	}

    public UserDTO loginUser(String username, String password) throws Exception {
        List<UserModel> userList = userRepository.findByLogin(username);

        // Vérifie si l'utilisateur existe
        if (userList.isEmpty()) {
            throw new Exception("Invalid login credentials");
        }

        // Récupère le premier utilisateur (en supposant que le login est unique)
        UserModel loggedInUser = userList.get(0);

        // Vérifie le mot de passe
        if (!password.equals(loggedInUser.getPwd())) {
            throw new Exception("Invalid login credentials");
        }

        // Retourne l'utilisateur connecté sous forme de UserDTO
        return DTOMapper.fromUserModelToUserDTO(loggedInUser);	
    }

}
