# Yoga App Testing Project !

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

> Git
> Node.js
> npm
> Java
> Maven
> MySQL

## START PROJECT

Git clone:

> git clone https://github.com/sabriderbala/test_full_stack_api_java_front_angular.git

Go inside folder:

> cd front

Install dependencies:

> npm install

Launch Front-end:

> npm run start;

Launch Back-end:

Go inside folder:

> cd back

> mvn spring-boot:run

 Launch DB :

> sudo service mysql start

Launch App : 

> http://localhost:4200/

## RESSOURCES

### Mockoon env 

### Postman collection

For Postman import the collection

> ressources/postman/yoga.postman_collection.json 

### MySQL

SQL script for creating the schema is available `ressources/sql/script.sql`

By default the admin account is:
- login: yoga@studio.com
- password: test!1234

## RUN TESTS

### FRONT

- cd/front
- npm run test
- Report is available here: front/coverage/lcov-report/index.html

### END TO END

- cd/front
- npm run e2e --> run 5 specs on cypress
- Report is available ( you need to run npm run e2e before ) : npm run e2e:coverage

### BACK
- cd/back
- sudo service mysql start
- mvn clean test
- Report is available here: back/target/site/jacoco/index.html
