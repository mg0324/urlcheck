## 部署mysql 5.7
``` shell
docker pull mysql:5.7
docker run -d -p 33306:3306 --name mysql \
-e MYSQL_ROOT_PASSWORD=123456 \
mysql:5.7
```

## 部署phpmyadmin
``` shell
docker pull phpmyadmin/phpmyadmin
docker run -d --name mysql-admin-ui -e PMA_HOST=127.0.0.1 -e PMA_PORT=33306 -p 8080:80 phpmyadmin/phpmyadmin
```