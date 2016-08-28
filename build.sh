#!/bin/bash
tomcatWebappsPath="/Users/weihongyan/Documents/workspace/apache-tomcat-9.0.0.M9/webapps"
targetWarPath="/Users/weihongyan/Documents/workspace/javaLearnNew/learn-web/target"
warName="learn"

cd "${targetWarPath}/../../"
mvn clean package -DskipTests=true
rm -rf "${tomcatWebappsPath}/${warName}"
rm -rf "${tomcatWebappsPath}/${warName}.war"
mv "${targetWarPath}/${warName}.war" "${tomcatWebappsPath}/${warName}.war"