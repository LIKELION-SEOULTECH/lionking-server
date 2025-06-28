#!/bin/bash

echo "--------------- 서버 배포 시작 -----------------"
docker stop lionking-server || true
docker rm lionking-server || true
cd /home/ubuntu/lionking-server
docker-compose down --remove-orphans || true
# 사용하지 않는 이미지 정리
docker image prune -f

# ECR 로그인 (필수!)
aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin 222634398055.dkr.ecr.ap-northeast-2.amazonaws.com

docker-compose up -d
# 컨테이너 상태 확인
echo "실행 중인 컨테이너들:"
docker ps
echo "--------------- 서버 배포 끝 -----------------"