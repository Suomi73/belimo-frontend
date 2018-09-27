#!/usr/bin/env bash

ng build
docker build -t robertbrem/belimo-frontend:0.0.1 .
