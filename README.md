## Simple demo example of spring boot and webflux

## Requirements
Java - 1.8.x

Maven - 3.x.x

MongoDB - 3.x.x

## Exploring the Rest APIs
The application defines following REST APIs

1. GET /posts - Get All Posts

2. POST /posts - Create a new Post

3. GET /posts/{id} - Retrieve a Post by Id

3. PUT /posts/{id} - Update a Post

4. DELETE /posts/{id} - Delete a Post

5. GET /stream/posts - Stream posts to a browser as Server-Sent Events

## Running integration tests
The project also contains integration tests for all the Rest APIs. For running the integration tests, go to the root directory of the project and type mvn test in your terminal.
