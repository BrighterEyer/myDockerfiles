

docker network create --driver bridge --subnet 172.60.0.0/16 mynetwork
docker network rm mynetwork
docker network inspect mynetwork

docker build -t redis .

docker run -itd --name redis-master --net=mynetwork -p 6382:6379 --ip 172.60.0.2 redis

docker run -itd --name redis-slave1 --net=mynetwork -p 6383:6379 --ip 172.60.0.3 redis

docker run -itd --name redis-slave2 --net=mynetwork -p 6384:6379 --ip 172.60.0.4 redis


redis-master配置
```
docker exec -it redis-master /bin/bash
vi /etc/redis.conf
bind 0.0.0.0    #允许所有IP能访问
protected-mode no   #redis保护模式，redis登录密码设置为空，无验证，这里为了方便测试

redis-server /etc/redis.conf &
ps -ef | grep redis
```

docker exec -it redis-slave1 /bin/bash
```
vi /etc/redis.conf

bind 0.0.0.0
protected-mode no

#分别启动redis
redis-server /etc/redis.conf &

redis-cli

slaveof 172.60.0.2 6379  #指定为redis主节点的IP和对应端口
```


docker exec -it redis-slave2 /bin/bash
```
vi /etc/redis.conf

bind 0.0.0.0
protected-mode no

#分别启动redis
redis-server /etc/redis.conf &

redis-cli

slaveof 172.60.0.2 6379  #指定为redis主节点的IP和对应端口
```


简单测试master主节点的redis数据是否同步到两台slave从节点，很明显已经同步成功了~
```
docker exec -it redis-master /bin/bash

ifconfig eth0 | egrep -o "([0-9]{1,3}\.){3}[0-9]{1,3}" | head -1

redis-cli
set try success


docker exec -it redis-slave1 /bin/bash
redis-cli
get try
```

