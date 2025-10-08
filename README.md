# NUTRiAPP 
NUTRiAPP is an application that allows users to track their nutritional goals. Users can gain, lose, maintain, or adapt their goals to their fitness journey. The app also allows users to track their workouts and adjust their caloric goals based on these workouts. Each user can also view their eating and workout history for each day. The app stores ingredients, recipes, and meals for users to input meals, and creates shopping lists based on the user’s stock of ingredients. 

## Team 1 
- Dominick Polakowski 
- Kendra Patel 
- Emily Santillanes
- Cristina Peniston 
- David Martinez 

## Version History 

### v1.0 | Release 1

**Functional Requirements** 

- **Users** – Users have statistics they can enter about themselves such as Name, Height, Weight, Target Weight, and Birthdate. 
- **User Goals** – Users have weight goals that include Losing, Gaining, Maintaining, Weight, as well as a combination to help improve physical fitness. 
- **Automatic Goal Updates** – Goals transition from lose or gain weight to maintain weight once the user reaches their target weight. Goals also transition from maintain weight to lose/gain weight whenever the user's weight changes by +-5 pounds. 
- **Food** – Foods can be characterized as Ingredients, Recipes, or Meals, all with a Name, Calories per unit, and Macronutrient Totals. Meals are composed of Recipes which are composed of Ingredients. 
- **Food Stock** – A Food Stock is a searchable database consisting of every ingredient with an amount in stock. Every ingredient starts off with 0 in stock until the user purchases some. Ingredients are deducted from the stock whenever a meal is made. 
- **Shopping Lists** – Users are helped to create Shopping Lists based on low or out of stock ingredients. 
- **Workouts** – Workouts remove calories from your consumed calories for that day, based on the duration and intensity of the workout. Workouts include High Intensity (10 Kcals/min), Medium Intensity (7.5 Kcals/min), and Low Intensity(5 Kcals/min). 
- **Calorie/Workout Recomendations** – The user will recieve warnings if a meal will tip them over their calorie goal, as well as recommend workouts based on their workout history. 
- **Personal History** – User's have a history for each day, containing their Weight, Calories Consumed vs Target Calories, Meals consumed, and Workouts completed. 
- **Days** – Whenever the user indicates to change the day, they will be prompted to enter their current weight and will recieve a new calorie target based on their weight, target weight, aand goal. 

**Non-Functional Requirements**

- **PTUI** – Plain Text User Interface in any terminal to provide menu-driven commands to interact with the application. 
- **Storage** – The user's profile is stored persistently between application startups 
- **Ingredient Database** – The Ingredient Databse is imported from a CSV file 
- **Days** – The application keeps track of the day, allowing the user to dictate when to move onto another day with a command. 


## Basic Setup
Read this section first.  Take these steps as soon as you download the git repo.

### Prepare the Project
**BEFORE** you import the project into your IDE, I would recommend you do the following:
* Replace the text `TEAM-XX` in the `pom.xml` file with your own team identifier, such as
  `MUD-1` or `NutriApp-2` and so on.
* Replace the text `REPLACE-ME-WITH-PROJECT-NAME` in the `pom.xml` file with your own project name.
* Replace the text `REPLACE-ME-WITH-PROJECT-NAME` in the `application.properties` file with your own project name.
* Replace the text `YOUR PROJECT DESCRIPTION HERE` in the `pom.xml` file with your own project description.

### Using the Maven Wrapper
This project starter includes a Maven wrapper.  This means that Maven runs in a virtual
environment; not against your global Maven installation.  Use the `./mvnw` command in a *NIX
environment such as MacOS or Linux and use the `mvnw.cmd` in a Windows environment.

Learn more [here](https://maven.apache.org/wrapper/).

## Basic Build Commands
This section provides basic build commands for this project.

### Running the App
To run the Spring Boot application, use this command:

```shell
./mvnw spring-boot:run
```

This will also execute any `CommandLineRunner` implementation classes, such as
the sample `SampleCommandLineRunner` in this starter kit.

### Running Unit Tests
To run the complete suite of unit tests, use this command:

```shell
./mvnw clean test surefire-report:report
```

This generates the Surefire report (on unit tests) in the file
`target/site/surefire-report.html`.  It also generates the JaCoCo
(code coverage) report at: `target/site/jacoco/index.html`.
