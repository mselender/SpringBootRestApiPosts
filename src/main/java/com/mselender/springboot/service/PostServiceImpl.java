package com.mselender.springboot.service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.mselender.springboot.model.Post;
import com.mselender.springboot.model.Count;
import org.springframework.web.client.RestTemplate;


@Service("postService")
public class PostServiceImpl implements PostService {
	
	private static final AtomicLong counter = new AtomicLong();
	
	private static List<Post> posts;
	
	static{
		//this.posts = populateDummyPosts();
		posts = populatePosts();
	}

	private static List<Post> populateDummyPosts(){
		List<Post> posts = new ArrayList<>();
		posts.add(new Post(counter.incrementAndGet(),20, "First post", "yadda yadda yadda"));
		posts.add(new Post(counter.incrementAndGet(),20, "Second post", "yadda yadda yadda"));
		posts.add(new Post(counter.incrementAndGet(),20, "Third post", "yadda yadda yadda"));

		return posts;
	}

	private static List<Post> populatePosts() {
		List<Post> posts = new ArrayList<>();
		RestTemplate restTemplate = new RestTemplate();
		Post[] postsArray = restTemplate.getForObject("http://jsonplaceholder.typicode.com/posts", Post[].class);
		return new ArrayList<Post>(Arrays.asList(postsArray));
	}

	public List<Post> findAllPosts() {
		return posts;
	}
	
	public Post findById(long id) {
		for(Post post : posts){
			if(post.getId() == id){
				return post;
			}
		}
		return null;
	}
	
	public Post findByTitle(String title) {
		for(Post post : posts){
			if(post.getTitle().equalsIgnoreCase(title)){
				return post;
			}
		}
		return null;
	}
	
	public void savePost(Post post) {
		//post.setId(counter.incrementAndGet());
		post.setId(getMaxId() + 1);
		posts.add(post);
	}

	public void updatePost(Post post) {
		int index = posts.indexOf(post);
		posts.set(index, post);
	}

	public void deletePostById(long id) {

		for (Iterator<Post> iterator = posts.iterator(); iterator.hasNext(); ) {
		    Post post = iterator.next();
		    if (post.getId() == id) {
		        iterator.remove();
		    }
		}
	}

	public boolean isPostExist(Post post) {
		return findByTitle(post.getTitle())!=null;
	}
	
	public void deleteAllPosts(){
		posts.clear();
	}

	public Count getPostsCount() {
	    return new Count(posts.size());
    }

    public Count getUniqueUsersCount() {
	    List<Long> uniqueUserIds = new ArrayList<>();
        for(Post post : posts){
            if(!uniqueUserIds.contains(post.getUserId())){
                uniqueUserIds.add(post.getUserId());
            }
        }
        return new Count(uniqueUserIds.size());
    }

    public long getMaxId() {
		long maxId = 0;

		if (posts != null && posts.size() > 0) {
			maxId = posts.get(posts.size() - 1).getId();
		}

		return maxId;
	}

}
