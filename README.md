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

### To run the project on AWS please follow these steps (First Time Deployment)
1. Create an two EC2 instances, one for the Request Service and one for the Management Service.
2. Configure the port mappings for IPv4 and IPv6 inbound traffic - 8080 for Request Service and 8081 for the Management Service.
2. SSH into each instance either through the terminal or the AWS console.
3. Install Docker on each instance by running `sudo yum update` & `sudo yum install docker`
4. Start the Docker service by running `sudo service docker start`
5. Pull the image from Docker Hub
    - For the Request Service run `docker pull hamish27/cosc349-request-service:latest`
    - For the Management Service run `docker pull hamish27/cosc349-management-service:latest`
6. Run the docker containers
    - For the Request Service run `docker run -d -p 8080:8080 --name request-service hamish27/cosc349-request-service:latest`
    - For the Management Service run `docker run -d -p 8081:8081 --name management-service hamish27/cosc349-management-service:latest`
7. The services should now be running on the public IP of the EC2 instances on ports 8080 for Request Service and 8081 for the Management Service.


### To Run the project once it is shutdown (Subsequent Deployments)
1. SSH into each EC2 instance 
2. Run `docker system prune -a -f`
3. Pull the image from Docker Hub
    - For the Request Service run `docker pull hamish27/cosc349-request-service:latest`
    - For the Management Service run `docker pull hamish27/cosc349-management-service:latest`
4. Run the docker containers
    - For the Request Service run `docker run -d -p 8080:8080 --name request-service hamish27/cosc349-request-service:latest`
    - For the Management Service run `docker run -d -p 8081:8081 --name management-service hamish27/cosc349-management-service:latest`
5. The services should now be running on the public IP of the EC2 instances on ports 8080 for Request Service and 8081 for the Management Service.


### Once EC2 instances are running 

Use this login to get into the Manger service running on the public IP address.
* Username: `johndoe`
* Password: `test`

Use this login for the Tenant Service running the other EC2 instance on the public IP address.
* Username: `alicebrown`
* Password: `test`


#### Notes

* To add a new property, there must be a tenant account made up in the tenant service in order to create the relationship between the two.



