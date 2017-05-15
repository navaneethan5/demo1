# MyRetail PRODUCT Service
MyRetail RESTful project.  
This project is building RESTful services using Spring framework and utilize Spring Data as entity framework, SpringBoot to generate stand alone runnable jar, and MongoDB as database repository.  In addition, it is also setup for CI/CD with Jenkins.  

## Getting started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes  
  
### Prerequisites
You will need following tools below to have it up and running in your local  
1. JDK (This was compiled with JDK 1.8, but it should work with previous version of Java too)  
2. Eclipse or equivalent Java IDE  
3. Maven plugin for your IDE  
4. Maven (only if you want to run in your terminal)  
5. MongoDB (this was developed using 3.4.2)  

### Installing
 Importing program
```
1. Clone this repo in your local  
2. Import this project as existing Maven project
3. To compile, type "mvn package"
4. Install 
```
   
Installing MongoDB
```
1. Install MongoDB to your local  
2. Once installed, open up mongo shell   
3. This program is created using default test database in Mongo  
4. You will need to create new collection called "ProductPricing"  
5. Type db.createCollection("ProductPricing")  
6. Insert new data to newly created collection  
7. Type “db.ProductPricing.insert({ productId: '13860428', currentPrice : 19.99, currencyName : "USD" })”  
```
Note:  
- Make sure MongoDB is running after exiting the shell  
- To start Mongo as service type "service mongod start"  
- To stop, type “service mongod stop”  

## Service API
```
GET : http://104.236.103.221:8080/products/{productId}  
```

```
PUT : http://104.236.103.221:8080/products/  
Body: {"name":"The Big Lebowski (Blu-ray)","id":"13860428","current_price":{"value":19.99,"currency_code":"USD"}}  
```

Sample working product Id :13860428, 16696652 

## POSTMAN Collection
You can also use Postman to test API above.  
```
1. Open up Postman  
2. Click Import  
3. Select Import from link  
4. Copy paste this url https://www.getpostman.com/collections/9a0b6b79fe1fa17f59ad  
5. You should be able to see sample request and run the request to Postman  
```

## Jenkins
This git repo is set to Jenkins job for CI/CD demo purposes.  
Jenkins URL is: http://104.236.103.221:8081/  
User id: guest  
Password: guest  
  
There will be three jobs already configured.  
1. FunShop  
2. FunShopStart  
3. FunShopStop  

<i>FunShop</i>  
This is the main job which will pull the codes from this repo, compile, and deploy as spring boot application using maven.  
Note: This job does not currently auto deploy the service. You will need to run FunShopStart to start the service  
  
<i>FunShopStart</i>  
This is to start the service. Please note that currently this job is set as not a background job. You will see once the build is initiated, it will keep running until you cancel the job or starting FunShopStop. Stopping this job will shut down RESTful service.  

<i>FunShopStop</i>  
This is to stop the service.  

## Built With  
  
* [Spring](http://www.spring.io) - The web framework used  
* [Maven](https://maven.apache.org/) - Dependency Management  
* [Jenkins](https://jenkins.io) - Automation tool  
* [Eclipse](https://eclipse.org) - IDE  
* [Java](https://www.oracle.com/java) - Programming language  
* [MongoDB](https://www.mongodb.com) - NoSQL database  

## Author
David Kumoro  
