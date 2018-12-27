FROM maven:3.5.3-jdk-8

#修改时区
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN dpkg-reconfigure -f noninteractive tzdata

#开启ll快捷命令代替ls -l
RUN echo "alias ll='ls \$LS_OPTIONS -l'" >> ~/.bashrc

#替换成阿里的apt-get源
RUN echo  \
    "deb http://mirrors.aliyun.com/debian stretch main contrib non-free" \
    "deb http://mirrors.aliyun.com/debian stretch-proposed-updates main contrib non-free" \
    "deb http://mirrors.aliyun.com/debian stretch-updates main contrib non-free" \
    "deb-src http://mirrors.aliyun.com/debian stretch main contrib non-free" \
    "deb-src http://mirrors.aliyun.com/debian stretch-proposed-updates main contrib non-free" \
    "deb-src http://mirrors.aliyun.com/debian stretch-updates main contrib non-free" \
    "deb http://mirrors.aliyun.com/debian-security/ stretch/updates main non-free contrib" \
    "deb-src http://mirrors.aliyun.com/debian-security/ stretch/updates main non-free contrib" \
    "deb http://mirrors.aliyuncs.com/debian stretch main contrib non-free" \
    "deb http://mirrors.aliyuncs.com/debian stretch-proposed-updates main contrib non-free" \
    "deb http://mirrors.aliyuncs.com/debian stretch-updates main contrib non-free" \
    "deb-src http://mirrors.aliyuncs.com/debian stretch main contrib non-free" \
    "deb-src http://mirrors.aliyuncs.com/debian stretch-proposed-updates main contrib non-free" \
    "deb-src http://mirrors.aliyuncs.com/debian stretch-updates main contrib non-free" \
    "deb http://mirrors.aliyuncs.com/debian-security/ stretch/updates main non-free contrib" \
    "deb-src http://mirrors.aliyuncs.com/debian-security/ stretch/updates main non-free contrib" \
    > /etc/apt/sources.list


RUN apt-get update && \
    #安装中文字符集zh_CN.UTF-8支持
    apt-get -y install locales && \
    sed -i -e 's/# zh_CN.UTF-8 UTF-8/zh_CN.UTF-8 UTF-8/' /etc/locale.gen && \
    echo 'LANG="zh_CN.UTF-8"' > /etc/default/locale && \
    dpkg-reconfigure --frontend=noninteractive locales && \
    update-locale LANG=zh_CN.UTF-8 && \
    rm -rf /var/lib/apt/lists/*


# set ssh user source,为通过ssh进来的shell导出必要的环境变量
RUN echo "export JAVA_HOME=${JAVA_HOME}" >> ${HOME}/.bashrc && \
    echo "export MAVEN_HOME=${MAVEN_HOME}" >> ${HOME}/.bashrc && \
    echo "export PATH=${PATH}" >> ${HOME}/.bashrc && \
    echo "export TERM=xterm" >> ~/.bashrc && \
    echo "export LANG=zh_CN.UTF-8" >> ${HOME}/.bashrc && \
    echo "export LANGUAGE=zh_CN.UTF-8" >> ${HOME}/.bashrc && \
    echo "export LC_ALL=zh_CN.UTF-8" >> ${HOME}/.bashrc

ENV RUN_ENV prod

COPY pom.xml /tmp/build/
COPY src /tmp/build/src

RUN echo "root:Q!W@E#R$" | chpasswd \
    && cd /tmp/build \
    && mkdir /app \
    && mvn clean package -q -P${RUN_ENV} -DskipTests=true \
    && mv target/*.jar /app/app.jar \
    && rm -rf /tmp/build

ENV APP_BASE_HOME="/app" 
ENV APP_HOME="${APP_BASE_HOME}"

WORKDIR $APP_HOME

COPY *.sh $APP_HOME/

ENTRYPOINT [ "/bin/bash", "entrypoint.sh" ]