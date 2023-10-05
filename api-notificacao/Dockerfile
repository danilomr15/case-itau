FROM openjdk:17-alpine
COPY ./target/api-notificacao.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch api-notificacao.jar'
ENTRYPOINT ["java","-jar","api-notificacao.jar"]