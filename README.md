"# SpringBootRestApiPosts" 

Port is 8090

mvn clean package

mvn package docker:build

from /target/docker/
docker build -t springio:SpringBootRestApiPosts .

docker run -p 8090:8090/tcp [image]
