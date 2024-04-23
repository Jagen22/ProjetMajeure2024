package com.example.prompt.model;
import com.example.postdto.PostDTO;

import java.io.Serializable;
import java.util.List;


public class PromptDTO implements Serializable {

    private static final long serialVersionUID = 1069270118228032176L;

    Integer id;
    PostDTO postDTO;
    List<String> params;
    public PromptDTO(Integer id, PostDTO postDTO){
        this.id = id;
        this.postDTO = postDTO;
    }
    public PromptDTO(Integer id, List<String> params){
        this.id=id;
        this.params=params;
    }
    public List<String> getParams() {
        return params;
    }
    public PromptDTO(Integer id){
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public PostDTO getPostDTO() {
        return postDTO;
    }

    public void setPostDTO(PostDTO postDTO) {
        this.postDTO = postDTO;
    }
}
