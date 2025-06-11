#!/bin/bash
username="${1:-test}"
password="${2:-test}"
db_name="${3:-test}"

echo "username: $username" 
echo "password: $password"
echo "db_name: $db_name"

docker stop mysql && docker rm mysql
docker run -itd -p 20306:3306 -e MYSQL_ROOT_PASSWORD=$password -e MYSQL_DATABASE=$db_name -e MYSQL_USER=$username -e MYSQL_PASSWORD=$password --name mysql mysql:8
