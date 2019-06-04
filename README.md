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
