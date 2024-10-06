# COSC349-assignment2

## Project Information
For this assignment a database and two other web services were designed. 
The two services work together to provide a property management application. 
The request service provides functionality for tenants to create an account and submit requests for work that may be required in a property, i.e A sink has broken.
Once the work request has been completed, the tenant can update the request, marking it as completed or deleting the request.

The management service provides functionality for managers to create an account, add a property and manage their properties.
Managers will be able to see the number requests from the tenant and make requests for that to work to be completed.
An extension has been added using AWS's Simple Notification Service, whereby a developer can monitor users creating accounts, ensuring valid data is being entered. The data from this operation could be forwarded through to use in agencies that manage the property managers.

Both the Request & Management service containers are served by a database; a single container running a PostgreSQL.

### Description of the Repository:
Within the root of this repository is a build.gradle with dependencies to 3 other subprojects, common, request-service and management-service. 
The common directory contains all the Data Access objects & Domain classes. 
While the request and management services each contain java web server code and specification for the end-points for each service.

### Accessing the Services on my AWS EC2 Instances
- Request Service: [http://54.209.64.249:8080]
- Management Service: [http://100.28.69.129:8081]

### First time Deployment on AWS
1. Create a two EC2 instances, one for the Request Service and one for the Management Service.
2. Configure the port mappings for IPv4 and IPv6 inbound traffic - 8080 for the Request Service and 8081 for the Management Service.
3. If you wish to receive emails detailing new users, please see below section on how to set up SNS for your email address.
4. Pull the repository to your local machine. 
5. Navigate to the root of the repository, and open the docker compose file. 
6. Add in your AWS credentials as environment variables. This allows the docker container images to connect with the Simple Notification Service.
7. Run `docker compose build`. 
8. Tag the images with your Docker Hub username. e.g `docker tag assignment2-request-service:latest {docker-username}/cosc349-request-service:latest`
9. Push the images to Docker Hub. e.g `docker push {docker-username}/assignment2-request-service:latest`
10. SSH into each EC2 instance either through the terminal or the AWS console.
11. Install Docker on each instance by running `sudo yum update` & `sudo yum install docker`
12. Start the Docker service by running `sudo service docker start`
13. Pull the image from Docker Hub
     - For the Request Service run `docker pull {docker-username}/assignment2-request-service:latest`
     - For the Management Service run `docker pull {docker-username}/assignment2-management-service:latest`
14. Run the docker containers
     - For the Request Service run 
    `docker run -d -p 8080:8080 --name request-service hamish27/assignment2-request-service:latest`
     - For the Management Service run - pass in the AWS Credentials using the following template:
    `docker run -d \
  -p 8081:8081 \
  -e AWS_ACCESS_KEY_ID={access_key_id} \
  -e AWS_SECRET_ACCESS_KEY={secret_access_key} \
  -e AWS_SESSION_TOKEN={session_token} \
  -e AWS_REGION=us-east-1 \
  {docker-username}/assignment2-management-service:latest`
15. The services should now be running on the Public IP of the EC2 instances on ports 8080 for Request Service and 8081 for the Management Service. Run `docker ps` to verify

### Subsequent Deployments
1. SSH into each EC2 instance 
2. Run `docker system prune -a -f`
3. On your local machine navigate to the root of the repository and update the docker compose file with your AWS credentials as environment variables.
4. Run `docker compose build`
5. Tag the images with your Docker Hub username. e.g `docker tag cosc349-request-service:latest {docker-username}/cosc349-request-service:latest`
6. Push the images to Docker Hub. e.g `docker push {docker-username}/cosc349-request-service:latest`
7. Pull the image from Docker Hub
    - For the Request Service run `docker pull {docker-username}/cosc349-request-service:latest`
    - For the Management Service run `docker pull {docker-username}/cosc349-management-service:latest`
8. Run the docker containers
    - For the Request Service run `docker run -d -p 8080:8080 --name request-service hamish27/cosc349-request-service:latest`
        - For the Management Service run - pass in the AWS Credentials using the following template:
          `docker run -d \
        -p 8081:8081 \
        -e AWS_ACCESS_KEY_ID={access_key_id} \
        -e AWS_SECRET_ACCESS_KEY={secret_access_key} \
        -e AWS_SESSION_TOKEN={session_token} \
        -e AWS_REGION=us-east-1 \
        {docker-username}/assignment2-management-service:latest`
9. The services should now be running on the Public IP of the EC2 instances on ports 8080 for Request Service and 8081 for the Management Service. Run `docker ps` to verify.


### Accessing SNS (Simple Notification Service) on AWS
1. Navigate to the AWS console and search for Simple Notification Service.
2. Click on the topics tab and click on registrations 
3. Subscribe to the topic by entering your email address and clicking subscribe.
4. Any new managers will now be sent through to your email address.

### Using the Request & Management Service

Use this login to get into the Manger service running on the public IP address.
* Username: `johndoe`
* Password: `test`

Use this login for the Tenant Service running the other EC2 instance on the public IP address.
* Username: `alicebrown`
* Password: `test`


#### Notes

* To add a new property, there must be a tenant account made up in the tenant service in order to create the relationship between the two.



