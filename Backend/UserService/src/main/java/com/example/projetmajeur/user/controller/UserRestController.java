package com.example.projetmajeur.user.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.projetmajeur.user.DTOMapper;
import com.example.projetmajeur.user.model.AuthDTO;
import com.example.projetmajeur.user.model.UserDTO;
import com.example.projetmajeur.user.model.UserModel;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        List<UserDTO> userDTOList = DTOMapper.fromUserModelListToUserDTOList(userService.getAllUsers());
        return userDTOList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Integer id) {
        Optional<UserModel> optionalUser = userService.getUser(id);
        UserModel user = optionalUser.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return new ResponseEntity<>(DTOMapper.fromUserModelToUserDTO(user), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) throws Exception {
        UserDTO addedUser = userService.addUser(userDTO);
        return new ResponseEntity<>(addedUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO, @PathVariable Integer id) {
        userDTO.setId(id);
        UserDTO updatedUser = userService.updateUser(userDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody AuthDTO authDTO) throws Exception {
        UserDTO loggedInUser = userService.loginUser(authDTO.getUsername(), authDTO.getPassword());
        return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) throws Exception {
        // System.out.println("JSON reçu : " + userDTO);
        // Check if the user already exists
        List<UserModel> existingUsers = userService.getUserByLoginPwd(userDTO.getLogin(), userDTO.getPwd());
        if (!existingUsers.isEmpty()) {
            throw new Exception("User already exists");
        }

        // Create a new user
        UserDTO registeredUser = userService.addUser(userDTO);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
}
