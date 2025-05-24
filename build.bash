#!/bin/bash

# remove all images
# docker image prune -a

# local docker image
# mvn -DactiveProfile=runtime clean package jib:dockerBuild

# Build and push image to dockerhub
mvn -DactiveProfile=runtime clean package jib:build

# curl -H "x-api-key:f0583805-03f6-4c7f-8e40-f83f55b7c077" http://localhost:8080/adres/api/v1/adresses

# curl -H "x-api-key:f0583805-03f6-4c7f-8e40-f83f55b7c077" http://172.17.0.1:8080/adres/api/v1/adresses

