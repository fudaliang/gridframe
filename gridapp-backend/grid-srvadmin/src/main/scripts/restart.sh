#!/bin/bash

JAR_NAME='springboot-admin-0.0.1-SNAPSHOT.jar'
SERVER_NAME='springboot-admin'
SERVER_PORT='8088'

DEPLOY_DIR=`pwd`
LOGS_DIR=$DEPLOY_DIR/logs
if [ ! -d $LOGS_DIR ]; then
    mkdir $LOGS_DIR
fi

#检查应用进程存在就KILL掉
PID=`ps -ef | grep java | grep "$SERVER_NAME" |grep -v grep | awk '{print $2}'`
if [ -n "$PID" ];then
	  echo "kill --> PID: $PID"
      kill $PID > /dev/null 2>&1
fi

#启动应用进程
echo -e "Starting the $SERVER_NAME ..."
STDOUT_FILE=$LOGS_DIR/$SERVER_NAME.log
nohup java -jar $DEPLOY_DIR/$JAR_NAME > $STDOUT_FILE 2>&1 &

#检查应用进程是否启动
COUNT=0
while [ $COUNT -lt 1 ]; do
	echo -e ".\c"
	sleep 1
	if [ -n "$SERVER_PORT" ];then
		COUNT=`netstat -an | grep "$SERVER_PORT" | wc -l`
	else
		COUNT=`ps -ef | grep java | grep "$SERVER_NAME" | awk '{print $2}' | wc -l`
	fi
	if [ $COUNT -gt 0 ];then
		break
	fi
done
echo "OK!"
PID=`ps -ef | grep java | grep "$SERVER_NAME" |grep -v grep | awk '{print $2}'`
echo "Current PID: $PID"
echo "STDOUT: $STDOUT_FILE"
