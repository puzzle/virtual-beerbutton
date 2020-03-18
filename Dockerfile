FROM fabric8/s2i-java:latest

USER 0

COPY . /tmp/src/

RUN /usr/local/s2i/assemble && rm -rf /tmp/src

USER 1000