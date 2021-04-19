FROM maven:3-openjdk-8
WORKDIR /project
COPY . .
RUN mvn clean package

FROM tomcat:7
MAINTAINER Roman Boyarsky <boyarsky1997@gmail.com>
RUN rm -rf /usr/local/tomcat/webapps/ROOT/
COPY --from=0 /project/target/online-store-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

