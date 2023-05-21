# PasteText-API (Inspiration from Pastebin).

## This is the first phase on this project and is still being developed.

### Description

The project draws inspiration from Pastebin, the goal is to create a text-sharing platform that can be deployed on a 
private server, where team member can sign up, input text, create the text URL and share to other colleagues.

### Instructions to use

- Clone this repository
- Import into your preferred IDE as a maven project
- Now, the project is all yours.


### Project Folder Structure

- auth -> For authenticating a user, returns a JWT token when successful
- config -> Contains all configuration setup, including JWT and security config
- employee -> Contains the onboarding flow for users of the platform, all users must first be created
- exception -> Contains all exception related date, including the exception controller that intercepts runtime exceptions
- pasteText -> Contains the logic behind the text-pasting-url-generation flow from a logged in user
- scheduled -> Contains a scheduler service that is executed at intervals to clean up expired pasted-text (pasted-text can have an expiry date)
- startup -> Contains commands that execute at startup, it creates default employees/users. This is useful because the project currently uses in-memory database.

### Package terminologies

- controller -> A class file containing REST endpoints that can be accessible via the browser
- dto -> Data Transfer Object, used to communicate data with the client, also helps create abstraction from the database entities
- service -> Contains an interface class and an implementation package
- implementation -> Housed within the service package, this contains the business logic implementation for the interface implemented from the service package
- repository -> Contains specific entity-class interfaces that intermediates with the database via JPA and Hibernate
- model -> Contains classes that maps to database tables, the class name becomes the table name and the class fields maps to the database column
- enums -> Contains a class-like file with a list of constant variables

### Run UNIT test

- Navigate to the src/test/java folder
- Right click on the com.ayenijeremiaho.pastebinapi and run the test
- For intelliJ users, right click on project root and click on 'Run Tests ..'

### Contact

Your Name - [@twitter_handle](https://twitter.com/ayenijeremiaho) - ayenijeremiah@gmail.com

Project Link: [https://github.com/ayenijeremiaho/pastebin-api](https://github.com/ayenijeremiaho/pastebin-api)