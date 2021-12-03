## DWP Test application 

ADemo app following the instruction to: 

>Build an API which calls this API, and returns people who are listed as either living in London, or whose current coordinates are within 50 miles of London. Push the answer to Github, and send us a link.

## Usage

### Get code + Installation 

From the repository you are reading this (presumably Github) copy the repository code URL (i.e. `https://github.com/bubbayaga/dwp-test.git`) and `git clone $url` onto your machine. 

To install the application run `mvn clean install` 

### Starting the application 

From the command line: 

`java -jar target/dwp-test-1.0-SNAPSHOT.jar server config.yml` 

From intellij: 

Goto `run` -> `edit Configuration` -> add a application configuration with the `+` symbol: 

for `Main class` enter `com.bubba.yaga.app.DwpTestApplication` 
for `Program arguments` enter `server config.yml` 

And then run the `DwpTestApplication`'s `main` method. 

You can skip manually adding an application config by trying to run the application and once it throws an error adding the `program arguments` as described above. 

### Using the app

Upon starting the application the following endpoints will be exposed on `localhost:8080`:

#### `/users/InOrNearLondon`
 
Request Parameters: None

Returns: a JSON response of `User`s who have a downstream `city` value of `London` or whose coordinates are within 50 miles of a predetermined coordinate value (51.5072, -0.1275;)

Example invocation: `curl -X GET http://localhost:8080/users/InOrNearLondon`

--- 
Upon starting the application the following endpoints will be exposed on `localhost:8081`:

#### `/healthcheck`

Request Parameters: None

Returns: `HTTP 200` if application is healthy (meaning it can reach the BPTDS API) or `HTTP 500` if not. 

Example invocation: `curl -X GET http://localhost:8081/healthcheck`

## Running tests 

Unit tests can be run with `mvn test`

Integration tests can be run with `mvn verify`

Both are automatically ran when `mvn install` is invoked. 

### Documentation links:

For the rough planning notes drawn up in initial ideation refer to [planning_nodes](src/docs/planning_notes.md) 

For 'assumptions and questions' formulated while implementing refer to [assumptions and questions](src/docs/assumptions%20and%20questions.md)

For 'why the flip' explanations of some choices refer to [wtf](src/docs/wtf.md)

For possible improvements and speculation on their implementation refer to [improvements](src/docs/improvements.md)

## ATTRIBUTIONS:

CITY to Lat/Long Databse provided by https://simplemaps.com/data/world-cities.  

