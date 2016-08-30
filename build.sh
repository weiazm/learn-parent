#!/bin/bash
# tomcatWebappsPath="/Users/weihongyan/Documents/workspace/apache-tomcat-8.5.4/webapps"
# targetWarPath="/Users/weihongyan/Documents/workspace/learn-parent/learn-web/target"
# warName="learn"

# cd "${targetWarPath}/../../"
# mvn clean package -DskipTests=true
# rm -rf "${tomcatWebappsPath}/${warName}"
# rm -rf "${tomcatWebappsPath}/${warName}.war"
# mv "${targetWarPath}/${warName}.war" "${tomcatWebappsPath}/${warName}.war"

mvn clean install
cd learn-web
mvn cargo:run
cd ..