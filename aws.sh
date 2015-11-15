#!/bin/sh

sbt docker:stage

cd target/docker/stage
zip -r vacancy.zip *
