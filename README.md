# abn-test-recipes
A REST API service which allows users to manage their favorite recipes.

This is an **MVP** application and more feature can be build on this.

**Development frameworks:**

* Java 11
* Springboot
* Gradle
* MongoDB
* Mockito
* Jacoco

**How to run the API:**

* **Setup the local mongo db and collection in your system.**

Install "mongodb compass GUI".

Use the connection string as "mongodb://localhost:27017" to configure a new connection.

Add a new database with name "recipe-management".

If need be, please import "recipes.json" to the collection, which is placed in the project directory in following path-
**src/main/resources/static/recipes.json**

Or you can just use the 'addRecipe' endpoint to add new recipes.

* **Using IDE (ex: IntelliJ)**

Import the project to your IDE.
Right-click on the project and click on 'Build module recipe-management'.

Once the project is successfully build, right-click on 'RecipeManagementApplication.java' file.
And click on "Run RecipeManagementApplication.main()".

This will start the REST API to run on localhost with port 8080.

Now you can use the Postman or any other REST API testing platform to hit the endpoints.

**Features of the application:**

* The application is a recipe management REST API developed using Java 11 and Spring boot 2.7.7
* Gradle has been used as the build framework and mongoDB as NoSQL database for it's schemaless properties.
* The REST API has been documented using **OPEN-API** and can be viewed at below url when the app is running
  http://localhost:8080/swagger-ui/index.html
* Mockito has been used as testing framework.
* Jacoco test coverage report is also implemented with minimum coverage threshold as **90%** and can be viewed in the below path once the test cases with coverage are run
**build/reports/tests/test/index.html**
* Lombok java library has been used to reduce the boiler-plate code.
* Mongo indexing.
* 'stylecheck' plugin has been implemented to perform quality checks on the project's java source files and generate reports from these checks. This can be run in Gradle tab under 'Tasks/other' and generated reports can be checked under **"build/reports/checkstyle"**
* 'owasp dependency-check' plugin has been installed to check any vulnerabilities in the project's used dependencies. The generated report can be viewed under **"build/reports/dependency-check-report.html"**.
* Actuator endpoints have been enabled for app monitoring.
* Env profiling has been done for specific environments to ease the release cycle and production readiness.
* mockMvc has been used for integration testing of the application.