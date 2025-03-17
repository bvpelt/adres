#!/bin/bash

# remove all images
# docker image prune -a

# local docker image
# mvn -DactiveProfile=runtime clean package jib:dockerBuild

# Build and push image to dockerhub
mvn -DactiveProfile=runtime clean package jib:build

