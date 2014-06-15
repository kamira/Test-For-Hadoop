Test-For-Hadoop
===============

```sh
mkdir ${APP_DIR}
cd ${APP_DIR}
mkdir ${JAVA_APP_CLASS_DIR}
javac -classpath ${HADOOP_HOME}/hadoop-0.20.2-core.jar -d ${JAVA_APP_CLASS_DIR} ${JAVA_APP_NAME}
jar -cvf ${JAR_NAME} -C ${JAVA_CLASS_DIR} .
```

Input
```sh
mkdir input
echo ${SAMPLE_STRING_1} > input/file01
echo ${SAMPLE_STRING_2} > input/file02
```

Copy to HDFS
```sh
${HADOOP_PATH} fs -mkdir input
${HADOOP_PATH} fs -put input/* input
```

Run
```sh
${HADOOP_PATH} jar ${JAR_NAME} org.myorg.${JAVA_APP_NAME} input output
```

Output
```sh
${HADOOP_PATH} dfs -cat output/*
```

Clean Output
```sh
${HADOOP_PATH} dfs -rmr output
```
