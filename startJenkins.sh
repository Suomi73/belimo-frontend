
docker stop jenkins
docker rm jenkins

docker run -d \
  --name jenkins \
  -p 8181:8080 \
  jenkins/jenkins:2.143
