# COSC349-assignment2

## Project Information
For this assignment a database and two other web services were designed. 
The two services work together to provide a property management application. 
The request service provides functionality for tenants to create an account and submit requests for work that may be required in a property, i.e A sink has broken.
Once the work request has been completed, the tenant can update the request, marking it as completed or deleting the request.


The management service provides functionality for managers to create an account, add a property and manage their properties.
Managers will be able to see the number requests from the tenant and make requests for that to work to be completed.

Both the Request & Management service containers are served by a database; a single container running a PostgreSQL.

### Description of the Repository:
Within the root of this repository is a build.gradle with dependencies to 3 other sub-projects, common, request-service and management-service. 
The common directory contains all the Data Access objects & Domain classes. 
While the request and management services each contain java web server code and specification for the end-points for each service.

### To run the project follow these steps:
1. clone the repository
2. ensure you have docker desktop running.
3. Run the command **docker compose up** within the assignment1 directory.

### Once Docker containers are running 

Use this login to get into the Manger service running on : http://localhost:8081
* Username: johndoe
* Password: test

Use this login for the Tenant Service running on http://localhost:8080
* Username: alicebrown
* Password: test

### To shut down the project
1. Run **docker compose down**
2. To remove the images - type **docker image pune <containerimageID>**
3. To remove the volume (database storage) - type **docker volume ls** & then **docker volume rm <image-name>**

#### Notes

* To add a new property, there must be a tenant account made up in the tenant service in order to create the relationship between the two.



