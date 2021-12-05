## DWP Test application 

A Demo app with a minimal(ish) solution to the follow instruction: 

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

---

#### GET `/InOrNearLondon/users`
 
Request Parameters: None

Returns: a JSON response of `User`s who have a downstream `city` value of `London` or whose coordinates are within 50 miles of a predetermined coordinate value (51.5072, -0.1275)

Example invocation: `curl -X GET http://localhost:8080/InOrNearLondon/users`

#### GET `/InOrNearLondon/usersWithCity`

Request Parameters: None

Returns: a JSON response of `UserWithCity`s who have a downstream `city` value of `London` or whose coordinates are within 50 miles of a predetermined coordinate value (51.5072, -0.1275).
Results determined by coordinate may not have a City value of London due to quirks in the downstream API's data. 

Example invocation: `curl -X GET http://localhost:8080/InOrNearLondon/usersWithCity`

--- 
Upon starting the application the following endpoints will be exposed on `localhost:8081`:

#### GET `/healthcheck`

Request Parameters: None

Returns: `HTTP 200` if application is healthy (meaning it can reach the BPTDS API) or `HTTP 500` if not. 

Example invocation: `curl -X GET http://localhost:8081/healthcheck`

## Running tests 

Unit tests can be run with `mvn test`

Integration tests can be run with `mvn verify`

Both are automatically ran when `mvn install` is invoked. 

### Documentation links:

For possible improvements and speculation on their implementation refer to [improvements](src/docs/improvements.md)

For the rough planning notes drawn up in initial ideation refer to [planning notes](src/docs/planning_notes.md) 

For 'assumptions and questions' formulated while implementing refer to [assumptions and questions](src/docs/assumptions%20and%20questions.md)

For 'why the flip' explanations of some choices refer to [wtf](src/docs/wtf.md)

### Dependencies 

A full list of dependencies can be found in the [pom.xml](pom.xml) but highlights include:

Dev:
* [Dropwizard](www.dropwizard.io) - framework for creation of RESTFUL APIs
* [simplelatlng](https://github.com/JavadocMD/simplelatlng) - A lightweight library for determining distance between coordinates using the Haversine formula and a mean earth radius of 6371.009km
Test:
* [Junit5](https://junit.org/junit5/docs/current/user-guide/) - Unit testing framework
* [assertj](https://assertj.github.io/doc/) - fluent assertion library
* [logcaptor](https://github.com/Hakky54/log-captor) - a library for easily intercepting and asserting Log values without having to mess around with Mockito's static mocking

## ATTRIBUTIONS:

CITY to Lat/Long dataset provided by https://simplemaps.com/data/world-cities. Ended up unused but I did use their coordinate for London. 

Initially developed on Manjaro Linux i3 21.1.6.2, with additional train-based development and validation on macO Big Sur 11.6.

