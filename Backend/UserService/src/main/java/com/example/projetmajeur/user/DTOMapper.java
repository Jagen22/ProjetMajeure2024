package com.example.projetmajeur.user;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.example.projetmajeur.user.model.UserDTO;
import com.example.projetmajeur.user.model.UserModel;

public class DTOMapper {

    public static UserDTO fromUserModelToUserDTO(UserModel uM) {
        UserDTO uDto = new UserDTO(uM);
        return uDto;
    }

    public static List<UserDTO> fromUserModelListToUserDTOList(Iterable<UserModel> userModelIterable) {
        List<UserModel> userModelList = StreamSupport
                .stream(userModelIterable.spliterator(), false)
                .collect(Collectors.toList());

        return userModelList.stream()
                .map(DTOMapper::fromUserModelToUserDTO)
                .collect(Collectors.toList());
    }
}
