#!/usr/bin/env bash
# 初始化核心参数
# jenkins任务名
task_name='test'
# jar包名称
app_name='-test'
# 发布版本
version='latest'
# harbor镜像仓库域名地址
harbor_registry='www.example.com'
# 镜像仓库
image_prefix='test'
# maven构建版本
maven_version='0.0.1-SNAPSHOT'
# 初始端口
INIT_EXPOSE=8086
# 对外服务端口
EXPOSE=8086
# jenkins任务构建原路径
jenkins_jar_path='/usr/local/docker/jenkins/jenkins_home/workspace/'${task_name}
# 构建镜像路径
projects_path='/usr/local/projects/'

# 停止删除容器
docker stop ${app_name}
echo 'stop container '${app_name}' success!'
docker rm ${app_name}
echo 'delete container '${app_name}' success!'

# 复制jar包到指定目录
# 注意：单体maven不需要加${app_name}，聚合项目需要加入${app_name}
cp ${jenkins_jar_path}${app_name}/target/${app_name}-${maven_version}.jar  ${projects_path}${app_name}/
cp ${jenkins_jar_path}${app_name}/src/docker/Dockerfile ${projects_path}${app_name}/

# 构建推送镜像
docker login --username=zhouxinlei --password=Zxl298828 https://${harbor_registry}

docker build -t ${image_prefix}/${app_name}:${maven_version} -f ${projects_path}${app_name}/Dockerfile ${projects_path}${app_name}/.

docker tag ${image_prefix}/${app_name}:${maven_version} ${harbor_registry}/${image_prefix}/${app_name}:${version}

docker push ${harbor_registry}/${image_prefix}/${app_name}:${version}

docker rmi `docker images|grep none | awk '{print $3}'`
docker rmi ${image_prefix}/${app_name}:${maven_version}
# 运行容器
docker run -p ${EXPOSE}:${INIT_EXPOSE} --name ${app_name} -v /etc/localtime:/etc/localtime -v ${projects_path}${app_name}/logs:/var/logs -d ${harbor_registry}/${image_prefix}/${app_name}:${version}
echo 'run container '${app_name}' success!'