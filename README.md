# 常用命令
````
# 源码执行
./gradlew bootRun

# 构建
./gradlew build

# 执行测试
./gradlew test

# 生成jar
./gradlew clean bootJar

# 生成镜像到本地
./gradlew bootBuildImage

# 启动jar，传入环境变量
java -jar catalog-service-0.0.1-SNAPSHOT.jar
java -jar catalog-service-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
java -jar catalog-service-0.0.1-SNAPSHOT.jar --spring.profiles.active=testdata

# 使用Dockerfile构建镜像并推送到GitHub Container Registry
docker build -t catalog-service .
docker tag catalog-service ghcr.io/shaopeng6666/catalog-service 
docker push ghcr.io/shaopeng6666/catalog-service

# 使用gradle内置的Buildpacks构建镜像并推送到GitHub Container Registry
# 本地运行没有成功，提示调用docker api没有成功，可能跟我本机用orbstack有关
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
````

Kubernetes命令
````
# 运行deployment
kubectl apply -f k8s/deployment.yml

# 查看deployment
kubectl get deployments

# 查看日志
kubectl logs deployment/catalog-service 

# 删除deployment
kubectl delete deployment catalog-service

# 运行service
kubectl apply -f k8s/service.yml

# 查看service
kubectl get services

# 删除service
kubectl delete service catalog-service

# 映射到本机80端口
kubectl port-forward service/catalog-service 9001:80

# 删除k8s配置文件生成的所有资源
kubectl delete -f k8s
````

# Tilt
````
# 启动Tilt
tilt up

# 停止Tilt
tilt down
````

# Octant
````
# Homebrew安装失败
brew install octant

# 启动
octant
````