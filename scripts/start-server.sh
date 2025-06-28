#!/bin/bash

echo "--------------- 서버 배포 시작 -----------------"
docker stop lionking-server || true
docker rm lionking-server || true
docker pull 222634398055.dkr.ecr.ap-northeast-2.amazonaws.com/lionking-server:latest
docker run -d --name lionking-server -p 8080:8080 222634398055.dkr.ecr.ap-northeast-2.amazonaws.com/lionking-server:latest
echo "--------------- 서버 배포 끝 -----------------"