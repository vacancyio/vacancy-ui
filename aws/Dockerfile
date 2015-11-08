FROM java:latest
ADD vacancy-1.0-SNAPSHOT.tgz /opt/vacancy
WORKDIR /opt/vacancy
RUN /opt/vacancy/vacancy-1.0-SNAPSHOT/bin/vacancy
EXPOSE 9000