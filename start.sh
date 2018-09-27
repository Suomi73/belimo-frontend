#!/usr/bin/env bash

docker stop belimo-frontend
docker rm belimo-frontend

docker run -d \
  --name belimo-frontend \
  -p 3000:80 \
  robertbrem/belimo-frontend:0.0.1
