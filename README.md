# Money Transfer
[![Build Status](https://travis-ci.org/flaviojmendes/money-transfer.svg?branch=master)](https://travis-ci.org/flaviojmendes/money-transfer)
[![codecov](https://codecov.io/gh/flaviojmendes/money-transfer/branch/master/graph/badge.svg?token=LIUQbn7CLD)](https://codecov.io/gh/flaviojmendes/money-transfer)

This is a simple Java application that uses Dropwizard to provide
an account money transfer service. 

### How to start the Application

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/moneytransfer-1.0-SNAPSHOT.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8081/healthcheck`

### Functionalities

The service basically handles 3 things:

1. Account Operations: Retrieving, Creating, Updating and Deleting;
1. Receipt: Retrieving;
1. Transfer: Transference between two accounts.

### Usage

You can find the complete Postman collection here: [![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/5820b307bef0c3288e6e)

Or:

---
##### Account

List All:
```
[HTTP GET]
http://localhost:8080/account/list 
```
Find One:
```
[HTTP GET]
http://localhost:8080/account?id={id}

Ex: http://localhost:8080/account?id=1

Will return:

{
    "id": 1,
    "name": "John Doe",
    "balance": 10234
}
  
```
Create:
```
[HTTP POST]
http://localhost:8080/account

JSON BODY: 
{
    "name" : "Michael Phelps",
    "balance" : 30000.00 
}
```
Update:
```
[HTTP PUT]
http://localhost:8080/account?id={id}

JSON BODY: 
{
    "name" : "Michael Phelpo"
}
```
Delete:
```
[HTTP DELETE]
http://localhost:8080/account?id={id}
```

---

##### Receipt

List All:
```
[HTTP GET]
http://localhost:8080/receipt/list 
```
Find One:
```
[HTTP GET]
http://localhost:8080/receipt?id={id}

Ex: http://localhost:8080/receipt?id=1

Will return:

{
    "id": 1,
    "amount": 1000.1,
    "from": {
        "id": 1,
        "name": "John Doe",
        "balance": 9233.9
    },
    "to": {
        "id": 2,
        "name": "Mark Spencer",
        "balance": 1011.33
    }
}
  
```

---

##### Transfer
##### Receipt

Transfer:
```
[HTTP GET]
http://localhost:8080/transfer?from=1&to=2&amount=1000.10

Will Return a Receipt:
{
    "id": 1,
    "amount": 1000.1,
    "from": {
        "id": 1,
        "name": "John Doe",
        "balance": 9233.9
    },
    "to": {
        "id": 2,
        "name": "Mark Spencer",
        "balance": 1011.33
    }
} 
 
```
