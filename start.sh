#!/usr/bin/env bash

docker stop belimo-frontend
docker rm belimo-frontend

docker run -d \
  --name belimo-frontend \
  --net belimo \
  -p 3000:80 \
  robertbrem/belimo-frontend:0.0.1
