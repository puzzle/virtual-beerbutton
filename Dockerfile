FROM registry.access.redhat.com/ubi8/openjdk-11 AS builder

LABEL maintainer="philipona@puzzle.ch"

RUN mkdir -p /tmp/src/
ADD . /tmp/src/

RUN cd /tmp/src && sh gradlew build -Dorg.gradle.daemon=false

RUN cp -a  /tmp/src/build/libs/*.jar /deployments/virtual-beerbutton.jar

FROM registry.access.redhat.com/ubi8/openjdk-11

EXPOSE 8080 9000

COPY --from=builder /deployments/virtual-beerbutton.jar /deployments/virtual-beerbutton.jar
