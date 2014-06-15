Test-For-Hadoop
===============

```
mkdir ${APP_DIR}
cd ${APP_DIR}
mkdir ${JAVA_APP_CLASS_DIR}
javac -classpath ${HADOOP_HOME}/hadoop-${HADOOP_VERSION}-core.jar -d ${JAVA_APP_CLASS_DIR} ${JAVA_APP_NAME}
jar -cvf ${JAR_NAME} -C ${JAVA_CLASS_DIR} .
```

Input
```
mkdir input
echo ${SAMPLE_STRING_1} > input/file01
echo ${SAMPLE_STRING_2} > input/file02
```

Copy to HDFS
```
${HADOOP_PATH} fs -mkdir input
${HADOOP_PATH} fs -put input/* input
```

Run
```
${HADOOP_PATH} jar ${JAR_NAME} org.myorg.${JAVA_APP_NAME} input output
```

Output
```
${HADOOP_PATH} dfs -cat output/*
```

Clean Output
```
${HADOOP_PATH} dfs -rmr output
```
