# 基础镜像
FROM java:8
# 作者信息
MAINTAINER gourd.hu<13584278267@163.com>
# 构建参数
ARG WORK_DIR
# 挂载目录
VOLUME ${WORK_DIR}
# 工作目录
WORKDIR ${WORK_DIR}
ARG JAR_FILE
ARG CONTAINER_JAR
# 类似ADD,将文件copy到容器中
COPY ${JAR_FILE} ${CONTAINER_JAR}
# 环境变量
ENV TZ 'Asia/Shanghai'
# 暴露端口
# EXPOSE 8888
# 镜像构建时需要运行的命令
# RUN yum -y install vim
# 指定容器运行时要运行的命令，追加命令
ENTRYPOINT java ${JAVA_OPTS} -jar ${CONTAINER_JAR}
# 指定容器运行时要运行的命令，替代命令
# CMD java ${JAVA_OPTS} -jar ${CONTAINER_JAR}