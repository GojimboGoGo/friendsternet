# Friends Management API Demo

API for management of friend actions (Create Friend, Subscribe, Block, etc.). Users query via email address to search against the DB.

## Development Tools

The following instructions assume you're using IntelliJ. They may (or may not) work on other IDEs.

## Technology Used

### Prerequisites

* JDK 11
* Docker (If deploying Docker container)

### Frameworks

* Spring Boot 2
* Spring JPA
* Spring MVC
* Swagger

### Databases

* H2 (Embedded)

### Development

For building, run:

``./mvnw clean install``

To deploy locally, run

``./mvnw spring-boot:run``

Then you may access the local server on your [machine](http://localhost:8080/swagger-ui.html).

For running tests, run

``./mvnw clean test``


## Accessing Cloud Deployed App

The app's Swagger UI has been deployed via Heroku.

[https://salty-sea-74277.herokuapp.com/swagger-ui.html]()

## Deploying via Docker

From the base directory, run

``docker-compose up``

After it has been deployed, to access the swagger, go to

[test]: http://localhost:8080/swagger-ui.html

## API
All of the endpoints below accept and output JSON.

Accepts: application/json

Produces: application/json

###POST /api/friends/connections

Create a friend connection between two email addresses.

Accepts:

``{
    "friends":
       [
          "andy@example.com",
          "john@example.com"
       ]
}``

Produces:

* 200 OK - `{ "success": true }`
* 500 ERROR - `{ "message": "Invalid connection request! Please enter 2 email addresses only." }`

### POST /api/friends/list

Retrieve friends list for an email address.

Accepts:

``{ "email": "andy@example.com" }``

Produces:

* 200 OK - `{ "success": true }`
* 500 ERROR - `{ "message": "Invalid user email" }`


### POST /api/friends/list/commons

Retrieve the common friends list between two email addresses.

Accepts:

``{
    "friends":
       [
          "andy@example.com",
          "john@example.com"
       ]
}``

Produces:

* 200 OK - `{ "success": true, "friends": [ "common@example.com" ], "count": 1 }`
* 500 ERROR - `{ "message": "Invalid connection request! Please enter 2 email addresses only." }`

### POST /api/friends/subscriptions

Subscribe to updates from an email address.

Accepts:

``{ "requestor": "lisa@example.com", "target": "john@example.com" }``

Produces:

* 200 OK - `{ "success": true }`
* 500 ERROR - `{ "message": "Invalid user email" }`

### POST /api/friends/blacklists

Block updates from an email address.

Accepts:

``{ "requestor": "andy@example.com", "target": "john@example.com" }``

Produces:

* 200 OK - `{ "success": true }`
* 500 ERROR - `{ "message": "Invalid user email" }`

### POST /api/friends/notifications

Retrieve all email addresses that can receive updates from an email address.

Accepts:

``{
   "sender": "john@example.com",
   "text": "Hello World! kate@example.com"
}``

Produces:

* 200 OK - `{
 "success": true,
 "recipients": 
 [
   "lisa@example.com",
   "kate@example.com"
 ]
 }`
* 500 ERROR - `{ "message": "Invalid user email" }`

