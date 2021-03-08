FROM fabric8/java-centos-openjdk11-jdk

LABEL maintainer="philipona@puzzle.ch"

EXPOSE 8080 9000


RUN mkdir -p /tmp/src/
ADD . /tmp/src/

RUN cd /tmp/src && sh gradlew build -Dorg.gradle.daemon=false

RUN cp -a  /tmp/src/build/libs/*.jar /deployments/virtual-beerbutton.jar
