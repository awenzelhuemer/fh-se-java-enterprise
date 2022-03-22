# FH Bay

Basic spring application for a small ebay clone.

## Steps for starting the application:

- Preparation
  - Run `docker compose up`
  - Install dependencies `mvn clean install`
- Web API
  - Start web api with `mvn spring-boot:run -pl fh-bay.api`
- Console client
  - Start client with `mvn spring-boot:run -pl fh-bay.client`
  - Show commands with `help`
  - Commands with * are only available after sign in.
  - Exit with Strg + C


## Steps for running tests

- Run `mvn test`