# Starting with Redis
## install Redis on local
### On Mac
Please run `brew install redis`

## Run Redis cluster on local
### Silicon Mac
* please run this command before execute the command to run Redis cluster
    ```shell
    export REDIS_CLUSTER_IP=0.0.0.0
    ```
* and then run below.
  ```shell
  docker-compose up
  ```
## Connect to Redis node
Please run below
```shell
redis-cli -h localhost -p 27000 -a [password]
```
> Note
> Please replace `[password]` with real password. :) 

## Check Cluster status
After connection to Redis cluster, execute below commands.
```shell
cluster nodes
```
### Status result
You should see result like below. There are 3 masters and 3 slaves. And slave node(`eb80cdefd800f09a7cb9c875c0fca9e1866d3fac`) is for master node(`32a611266f72bd0d7ce00d9affde346d0954b139`), and so on.

```
32a611266f72bd0d7ce00d9affde346d0954b139 172.20.0.2:7000@17000 myself,master - 0 1722409005000 1 connected 0-5460
eb80cdefd800f09a7cb9c875c0fca9e1866d3fac 172.20.0.2:7003@17003 slave 32a611266f72bd0d7ce00d9affde346d0954b139 0 1722409005893 1 connected
a5d56c3eeee846d4bd03027d801f19453bac91a9 172.20.0.2:7004@17004 slave c4e01dbc27e3da84fabc7c271e008a2068594b17 0 1722409004000 2 connected
fd8727a8f9096b8aceec95447d6523e07e5b0aaa 172.20.0.2:7002@17002 master - 0 1722409004861 3 connected 10923-16383
e837f8ee0239f09fa7ffa8f1845fe7bee701fb5c 172.20.0.2:7005@17005 slave fd8727a8f9096b8aceec95447d6523e07e5b0aaa 0 1722409005000 3 connected
c4e01dbc27e3da84fabc7c271e008a2068594b17 172.20.0.2:7001@17001 master - 0 1722409005581 2 connected 5461-10922
```