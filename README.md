# 常用命令
````
# 源码执行
./gradlew bootRun

# 生成jar
./gradlew clean bootJar

# 启动jar，传入环境变量
java -jar catalog-service-0.0.1-SNAPSHOT.jar
java -jar catalog-service-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
java -jar catalog-service-0.0.1-SNAPSHOT.jar --spring.profiles.active=testdata

# 使用Dockerfile构建镜像并推送到GitHub Container Registry
docker build -t ghcr.io/shaopeng6666/catalog-service .
docker push ghcr.io/shaopeng6666/catalog-service

# 使用gradle内置的Buildpacks构建镜像并推送到GitHub Container Registry
./gradlew bootBuildImage \
    --imageName ghcr.io/shaopeng6666/catalog-service \
    --publishImage \
    -PregistryUrl=ghcr.io \
    -PregistryUsername=shaopeng6666 \
    -PregistryToken=${GITHUB_TOKEN}
````

Docker命令
````
# 使用Docker启动PostgreSQL
docker run -d \
    --name polar-postgres \
    --net catalog-network \
    -e POSTGRES_USER=user \
    -e POSTGRES_PASSWORD=password \
    -e POSTGRES_DB=polardb_catalog \
    -p 5432:5432 \
    postgres:14.12

# Docker启动服务
docker run -d \
    --name catalog-service \
    --net catalog-network \
    -p 9001:9001 \
    -e SPRING_DATASOURCE_URL=jdbc:postgresql://polar-postgres:5432/polardb_catalog \
    -e SPRING_PROFILES_ACTIVE=testdata \
    catalog-service

# 删除容器    
docker rm -fv catalog-service polar-postgres

# 使用Docker Compose启动服务
docker-compose up -d

# 使用Docker Compose停止服务
docker-compose down
````