# Web application with struts2

This Web application in Java is a example that use struts2

### Pre requirements
Install 8

http://www.oracle.com/technetwork/java/javase/downloads/index.html

Setup JAVA_HOME environment variable

`set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_202`

Install maven

https://maven.apache.org/install.html

### Installation
-------------------

To install this web application make the following steps:

#### 1. Clone the project
`create local directory, example mkdir project-example`

`cd project-example`

`git clone https://github.com/hendrosusanto926/Inventory.git`

#### 2. Install the project
`cd Inventory`

`mvn clean`

`mvn install`

### Run the project
-------------------

To run this web application make the following steps:

#### 1. Run embedded tomcat 7 with maven
`mvn tomcat7:run`

Open the URL following

`http://localhost:9090/index`
