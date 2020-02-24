start cmd /k D:\MariaDB10.2\bin\mysqld
start cmd /k D:\gridframe\gridapp-backend\grid-nacos-1.1.3\bin\startup.cmd

@ping 127.0.0.1 -n 25

start cmd /k java -jar D:\gridframe\gridapp-backend\grid-srvadmin\target\grid-srvadmin-0.0.1-SNAPSHOT.jar
start cmd /k java -jar D:\gridframe\gridapp-backend\grid-gateway\target\grid-gateway-0.0.1-SNAPSHOT.jar
start cmd /k java -jar D:\gridframe\gridapp-backend\grid-base\target\grid-base-0.0.1-SNAPSHOT.jar
start cmd /k java -jar D:\gridframe\gridapp-backend\grid-privadmin\target\grid-privadmin-0.0.1-SNAPSHOT.jar

