FROM openjdk:17-alpine
COPY ./target/api-cliente.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch api-cliente.jar'
ENTRYPOINT ["java","-jar","api-cliente.jar"]