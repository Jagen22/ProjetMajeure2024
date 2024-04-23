package com.sp.repository;

import org.springframework.data.repository.CrudRepository;
import com.sp.model.Post;

import java.util.List;

public interface PostRepository extends CrudRepository<Post,Integer> {
	public Post findByIdPrompt(Integer idPrompt);
	public List<Post> findByIdUser(Integer idUser);

}
