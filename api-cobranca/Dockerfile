FROM openjdk:17-alpine
COPY ./target/api-cobranca.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch api-cobranca.jar'
ENTRYPOINT ["java","-jar","api-cobranca.jar"]