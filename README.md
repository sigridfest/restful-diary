[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2106/gr2106.git)

# This repository

​
This application will represent a diary where a user can write about their day and save it in our app. A longer description of user requirements are explained in the readme file of [diaryProject](./diaryProject/README.md).
​

#### [/docs](/docs)

This folder will continuously be updated with relevant documentation for all three iterations.
​
#### [/diaryProject](/diaryProject)
This folder contains all code and logic of the diary. **This is the folder you enter in order to run the application.**  
​
- [**/core**](diaryProject/core)
contains the core classes as well as corresponding tests.
​
- [**/ui**](diaryProject/ui)
contains the controller and the fxml file.

- [**/json**](diaryProject/json)
necessary for creating reading and writing to json files.

- [**/rest**](diaryProject/rest)
necessary for implementation of REST-API.
​

### Build with maven

This project is built with maven, therefore there is a pom.xml file for configuration.  <br  /> 



# Trying it out

​When you open the project in gitpod, "mvn install" and "mvn test" are both run automatically. 


## Run the code
To run the application, first start the server with the terminal command: 

```bash
mvn -pl rest spring-boot:run
```

The project can then be tried out by opening a new terminal, cd-ing into the diaryProject/ui folder and using the command:

```bash
mvn javafx:run
```

## Test the code

code is automatically tested when opening the project in gitpod, but can be tested again using:

```bash
mvn test
```

## Check test coverage

- check test coverage with

```bash
mvn verify
```

and open the index.html file that appears in the target/site directory of each individual module

