# Case técnico - Recuperação de crédito

## Tecnologias utilizadas
* Java 17
* Spring boot 3
* RabbitMQ
* PostgreSQL
* Docker

## Pré requisitos para a execução do artefato
* JAVA_HOME apontando para a versão 17 da JDK
* Docker

## Build de artefatos
É necessário gerarmos o executável dos 3 micro erviços antes de montarmos 
o nosso container:

Nas seguintes pastas precisaremos fazer o build dos artefatos utilizando o maven:

``` 
api-cliente
api-cobranca
api-notificacao
``` 

Comando maven:
``` 
./mvnw clean package
``` 

## Criando o container
Após ter gerado os artefatos, só falta o comando que criará nosso container:
``` 
docker-compose up -d
``` 

## API Clientes

A API de clientes é basicamente um CRUD para clientes da plataforma. O
objetivo dessa API é demonstrar as técnicas de desenvolvimento adotadas
em um cenário simples e com persistência de dados.

## API Cobranças

A API de cobranças é o core desse serviço. É através dela que são enviados
os emails de cobrança, além da gestão de restrição de CPF de clientes devido 
a inadimplência, além da possibilidade de receber pagamentos. 
O objetido desse serviço é demonstrar técnicas de 
desenvolvimento um pouco mais complexas, regras de negócio mais
elaboradas, operações assíncronas e persistência de dados. 

## API Notificação

A API de notificações serve exclusivamente para alertar o cliente
através de emails para a existência de dívidas, além de manter
o banco de dados de restrições, que simula um órgão de proteção
ao crédito. O objetivo dessa API é ser exclusivamente utilizada
para o envio de emails de forma asssíncrona e fornecer os dados
de clientes com restrição de CPF.


