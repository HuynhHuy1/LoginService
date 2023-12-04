FROM alpine

RUN apk update && apk add openjdk11 && apk add maven

WORKDIR /app

COPY ./src ./src

COPY ./pom.xml ./

CMD mvn clean package && java -jar target/Server_chat-1.0-SNAPSHOT-jar-with-dependencies.jar

EXPOSE 9090 1099

