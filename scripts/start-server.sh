#!/bin/bash

echo "--------------- 서버 배포 시작 -----------------"
docker stop lionking-server || true
docker rm lionking-server || true
docker pull {ECR Repository 주소}/lionking-server:latest
docker run -d --name lionking-server -p 8080:8080 {ECR Repository 주소}/lionking-server:latest
echo "--------------- 서버 배포 끝 -----------------"