package com.mselender.springboot.service;


import java.util.List;

import com.mselender.springboot.model.Post;
import com.mselender.springboot.model.Count;

public interface PostService {
	
	Post findById(long id);
	
	Post findByTitle(String title);
	
	void savePost(Post post);
	
	void updatePost(Post post);
	
	void deletePostById(long id);

	List<Post> findAllPosts();
	
	void deleteAllPosts();
	
	boolean isPostExist(Post post);

	Count getPostsCount();

	Count getUniqueUsersCount();

	long getMaxId();
	
}
