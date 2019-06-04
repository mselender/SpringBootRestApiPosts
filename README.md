"# SpringBootRestApiPosts" 

Port is set to 8090

mvn clean package

mvn package docker:build 
(need to put dockerhub creds in plugin config for this to work?)

from /target/docker/
docker build -t springio:SpringBootRestApiPosts .

docker run -p 8090:8090/tcp springio:SpringBootRestApiPosts

from from /src/main/docker/
kubectl create -f k8pod.yaml
or
kubectl apply -f k8pod.yaml

kubectl port-forward posts-pod 8090:8090

REST API:

Retrieve/retrieve all - GET
localhost:8090/SpringBootRestApi/api/post/{id}
localhost:8090/SpringBootRestApi/api/post/

Posts count - GET
localhost:8090/SpringBootRestApi/postsCount/

Unique users count - GET
localhost:8090/SpringBootRestApi/uniqueUsersCount/

Create - POST
localhost:8090/SpringBootRestApi/post/
payload: {"id":1,"userId":1,"title":"some text","body":"some text"}

Update - PUT
localhost:8090/SpringBootRestApi/post/{id}
payload: {"id":1,"userId":1,"title":"some text","body":"some text"}

Delete and delete all - DELETE
localhost:8090/SpringBootRestApi/api/post/{id}
localhost:8090/SpringBootRestApi/api/post/

