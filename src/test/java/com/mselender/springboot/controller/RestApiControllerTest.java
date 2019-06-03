package com.mselender.springboot.controller;
 
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.mselender.springboot.model.Post;
import com.mselender.springboot.model.Count;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
//@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RestApiControllerTest {

    @LocalServerPort
    int randomServerPort;

    private final String REST_SERVICE_URI = "http://localhost:" + 8090 + "/SpringBootRestApi/api";

    @Mock
    RestTemplate mockedRestTemplate;

    /* GET */
    @SuppressWarnings("unchecked")
    @Test
    public void listAllPosts() {
        System.out.println("Testing listAllUsers API-----------");
         
        RestTemplate restTemplate = new RestTemplate();
        List<LinkedHashMap<String, Object>> postsMap = restTemplate.getForObject(REST_SERVICE_URI+"/post/", List.class);

        if(postsMap!=null){
            for(LinkedHashMap<String, Object> map : postsMap){
                System.out.println("Post : id="+map.get("id")+", UserId="+map.get("userId")+"Title="+map.get("title")+", Body="+map.get("body"));
            }
        }else{
            System.out.println("No post exists----------");
        }

        Assert.assertNotNull(postsMap);
        Assert.assertFalse(postsMap.isEmpty());
    }
     
    /* GET */
    @Test
    public void getPost() {
        System.out.println("Testing getPost API----------");
        RestTemplate restTemplate = new RestTemplate();
        Post post = restTemplate.getForObject(REST_SERVICE_URI+"/post/1", Post.class);
        System.out.println(post);

        Assert.assertNotNull(post);
        Assert.assertEquals(1, post.getId());


    }

    /* GET */
    @Test
    public void getPostMocked() {
        System.out.println("Testing getPost API----------");
        when(mockedRestTemplate.getForObject(REST_SERVICE_URI + "/post/4", Post.class)).
                thenReturn(new Post(4, 1, "xxxxxxx","yyyyy"));
        //RestTemplate restTemplate = new RestTemplate();
        Post post = mockedRestTemplate.getForObject(REST_SERVICE_URI+"/post/4", Post.class);
        System.out.println(post);

        Assert.assertNotNull(post);
        Assert.assertEquals(4, post.getId());

    }

    /* GET */
    @Test
    public void getPostsCount() {
        System.out.println("Testing getPostCount API----------");
        RestTemplate restTemplate = new RestTemplate();
        Count count = restTemplate.getForObject(REST_SERVICE_URI+"/postsCount/",Count.class);
        System.out.println(count);

        Assert.assertNotNull(count);
        Assert.assertNotEquals(0, count.getCount());
    }

    /* GET */
    @Test
    public void getUniqueUsersCount() {
        System.out.println("Testing getUniqueUsersCount API----------");
        RestTemplate restTemplate = new RestTemplate();
        Count count = restTemplate.getForObject(REST_SERVICE_URI+"/uniqueUsersCount/", Count.class);
        System.out.println(count);

        Assert.assertNotNull(count);
        Assert.assertNotEquals(0, count.getCount());


    }
    /* POST */
    @Test
    public void createPost()  {
        System.out.println("Testing create Post API----------");
        RestTemplate restTemplate = new RestTemplate();
        Post post = new Post(0,25, "Added post","blah blah blah");
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/post/", post, Post.class);
        System.out.println("Location : "+uri.toASCIIString());
        Assert.assertNotNull(uri);

    }
 
    /* PUT */
    @Test
    public void updatePost() {
        System.out.println("Testing update Post API----------");
        RestTemplate restTemplate = new RestTemplate();
        //Post post = new Post(2,24, "Second post updated", "yadda yadda yadda");
        Post post1 = restTemplate.getForObject(REST_SERVICE_URI + "/post/4", Post.class);
        post1.setTitle("1800Flowers");
        post1.setBody("1800Flowers");
        restTemplate.put(REST_SERVICE_URI + "/post/4", post1);
        System.out.println(post1);

        System.out.println("Getting updated Post----------");
        //RestTemplate restTemplate = new RestTemplate();
        Post post2 = restTemplate.getForObject(REST_SERVICE_URI + "/post/4", Post.class);
        System.out.println(post2);

        Assert.assertNotNull(post2);
        Assert.assertEquals(post1.getTitle(), post2.getTitle());
        Assert.assertEquals(post1.getBody(), post2.getBody());
    }

    /* PUT */
    @Test
    public void updatePostMocked() {
        System.out.println("Testing update Post API----------");
        when(mockedRestTemplate.getForObject(REST_SERVICE_URI + "/post/4", Post.class)).
                thenReturn(new Post(4, 1, "1800Flowers","1800Flowers"));
        Post post1 = new Post(4, 1, "1800Flowers", "1800Flowers");
        mockedRestTemplate.put(REST_SERVICE_URI + "/post/4", post1);
        System.out.println(post1);

        System.out.println("Getting updated Post----------");
        Post post2 = mockedRestTemplate.getForObject(REST_SERVICE_URI + "/post/4", Post.class);
        System.out.println(post2);

        Assert.assertNotNull(post2);
        Assert.assertEquals(post1.getTitle(), post2.getTitle());
        Assert.assertEquals(post1.getBody(), post2.getBody());
    }

    /* DELETE */
    @Test
    public void deletePost()  {
        System.out.println("Testing delete Post API----------");
        RestTemplate restTemplate = new RestTemplate();
        //restTemplate.delete(REST_SERVICE_URI+"/post/3");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(REST_SERVICE_URI+"/post/3", HttpMethod.DELETE, entity, String.class);
        Assert.assertEquals("204", response.getStatusCode().toString());
    }
 
 
    /* DELETE ALL */
    @Ignore
    public void deleteAllPosts()  {
        System.out.println("Testing all delete Posts API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI+"/post/");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(REST_SERVICE_URI+"/post/", HttpMethod.DELETE, entity, String.class);
        Assert.assertEquals("204", response.getStatusCode().toString());

    }

    public static void main(String args[]){
        /*
        listAllPosts();
        getPost();
        createPost();
        listAllPosts();
        updatePost();
        listAllPosts();
        deletePost();
        listAllPosts();
        deleteAllPosts();
        listAllPosts();
         */
    }


}