package com.mselender.springboot.controller;

import java.util.List;

import com.mselender.springboot.model.Post;
import com.mselender.springboot.model.Count;
import com.mselender.springboot.service.PostService;
import com.mselender.springboot.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class RestApiController {

	private static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	PostService postService; //Service which will do all data retrieval/manipulation work

	// -------------------Retrieve All Posts---------------------------------------------

	@RequestMapping(value = "/post/", method = RequestMethod.GET)
	public ResponseEntity<List<Post>> listAllPosts() {
		List<Post> posts = postService.findAllPosts();
		if (posts.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
	}

	// -------------------Retrieve Single Post------------------------------------------

	@RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getPost(@PathVariable("id") long id) {
		logger.info("Fetching Post with id {}", id);
		Post post = postService.findById(id);
		if (post == null) {
			logger.error("Post with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Post with id " + id
					+ " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Post>(post, HttpStatus.OK);
	}

	// ------------------- get Posts count-----------------------------

	@RequestMapping(value = {"/postsCount/", "/postsCount", "/postCount/", "/postCount"}, method = RequestMethod.GET)
	public ResponseEntity<?> getPostsCount() {
		logger.info("Getting Posts count");
		Count count = postService.getPostsCount();
		return new ResponseEntity<Count>(count, HttpStatus.OK);
	}

	// ------------------- get Unique Users count-----------------------------

	@RequestMapping(value = {"/uniqueUsersCount/", "/uniqueUsersCount","/uniqueUserCount/", "/uniqueUsersCount"}, method = RequestMethod.GET)
	public ResponseEntity<?> getUniqueUsersCount() {
		logger.info("Getting Unique Users count");
		Count count = postService.getUniqueUsersCount();
		return new ResponseEntity<Count>(count, HttpStatus.OK);
	}


	// -------------------Create a Post-------------------------------------------

	@RequestMapping(value = "/post/", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody Post post, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Post : {}", post);

		if (postService.isPostExist(post)) {
			logger.error("Unable to create. A Post with title {} already exists", post.getTitle());
			return new ResponseEntity(new CustomErrorType("Unable to create. A Post with title " +
			post.getTitle() + " already exists."),HttpStatus.CONFLICT);
		}
		postService.savePost(post);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/post/{id}").buildAndExpand(post.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Post ------------------------------------------------

	@RequestMapping(value = "/post/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updatePost(@PathVariable("id") long id, @RequestBody Post post) {
		logger.info("Updating Post with id {}", id);

		Post currentPost = postService.findById(id);

		if (currentPost == null) {
			logger.error("Unable to update. Post with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to update. Post with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentPost.setUserId(post.getUserId());
		currentPost.setTitle(post.getTitle());
		currentPost.setBody(post.getBody());


		postService.updatePost(currentPost);
		return new ResponseEntity<Post>(currentPost, HttpStatus.OK);
	}

	// ------------------- Delete a Post-----------------------------------------

	@RequestMapping(value = "/post/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting Post with id {}", id);

		Post post = postService.findById(id);
		if (post == null) {
			logger.error("Unable to delete. Post with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. Post with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		postService.deletePostById(id);
		return new ResponseEntity<Post>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Users-----------------------------

	@RequestMapping(value = "/post/", method = RequestMethod.DELETE)
	public ResponseEntity<Post> deleteAllUsers() {
		logger.info("Deleting All Posts");

		postService.deleteAllPosts();
		return new ResponseEntity<Post>(HttpStatus.NO_CONTENT);
	}


}