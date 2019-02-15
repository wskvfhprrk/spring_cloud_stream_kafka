# springcloud stream kafka遇到的坑

springcloud stream rabbit搭建很顺利，但是改为kafka之后总是一次次报错，掉进坑中很长时间，现总结如下：

#### kafka服务搭建(坑)

特别要注意版本，我使用的是docker方式搭建的kafka——`wurstmeister/kafka`

- 注意去官方网站查看[docker-compose.yml](https://raw.githubusercontent.com/wurstmeister/kafka-docker/master/docker-compose.yml )文件，**切莫去baidu查询**。
- 版本问题，如果使用springboot-2.1.0.RELEASE以上版本，要使用kafka-2以上版本，在实用中使用到1以下版本时程序报错（1-2中间版本没有测试），`wurstmeister/kafka`版本要使用`2.12-2.x.x`版本（wurstmeister/kafka后面的版本号`2.x.x`是kafka的版本号）

#### 先决条件

1. 安装[docker-compose](https://github.com/docker/compose/tags)
2. 修改`KAFKA_ADVERTISED_HOST_NAME`在docker-compose.yml以符合您的泊坞窗主机IP（注意：不要使用localhost或127.0.0.1作为主机IP）

`docker-compose.yml`文件（含`kafka_manager`）

```yaml
version: '2'
services:
  zookeeper:
    image: wurstmeister/zookeeper
    ports:
      - "2181:2181"
  kafka:
    #build: .  #官方推给的，需要kafka的Dockerfile，简便方法加上后面的image即可
    image: wurstmeister/kafka:2.12-2.1.0
    ports:
      - "9092:9092"
    environment:
      KAFKA_ADVERTISED_HOST_NAME: 192.168.166.182 #服务器地址
      KAFKA_CREATE_TOPICS: "test:1:1"
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
  kafka-manager:
    image: hlebalbau/kafka-manager:stable
    ports:
      - "9091:9000"
    environment:
      ZK_HOSTS: "zookeeper:2181"
      APPLICATION_SECRET: "random-secret"
    command: -Dpidfile.path=/dev/null
```

#### 用法

在`docker-compose.yml`文件所在目录下运行命令

启动集群：

- `docker-compose up -d`

添加更多经纪人：

- `docker-compose scale kafka=3`

销毁集群：

- `docker-compose stop`

#### 详情请去githup查询

官网[wurstmeister/kafka-docker获得](https://github.com/wurstmeister/kafka-docker)

#### spring boot配置 yml

```yaml
spring:
  application:
    name: shop-server
  cloud:
    stream:
      bindings:
        #配置自己定义的通道与哪个中间件交互
        shop_input: #ShopChannel里Input和Output的值
          destination: zhibo #目标主题
        shop_output:
          destination: zhibo
      default-binder: kafka #默认的binder是kafka
  kafka:
    bootstrap-servers: 192.168.166.180:9092 #kafka服务地址
    consumer:
      group-id: consumer1
    producer:
      key-serializer: org.apache.kafka.common.serialization.ByteArraySerializer
      value-serializer: org.apache.kafka.common.serialization.ByteArraySerializer
      client-id: producer1
server:
  port: 8100
```

