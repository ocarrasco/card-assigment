# Setup
* Set environment variables to connect mysql db using url, user and password.
* Db will be populated with data model as well as data.
* Mysql User requires permissions in order to create tables in the schema, flyway properties credentials should be used otherwise.  

# RUN
* Run Main method as java project with argument to point to the right db.  
* Visit http://localhost:8080/swagger-ui.html to check documentation.

# Usage
Call "/users/auth" endpoint to get jwt token that should be used in "Authorize section" to be sent for each request.

# Demo Users
| Email             | Password |
|-------------------|----------|
| admin@email.com   | admin    |
| member1@email.com | member   |
| member2@email.com | member   |