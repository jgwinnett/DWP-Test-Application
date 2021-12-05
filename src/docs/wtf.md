# Random points of information

## Why round distance to 1 decimal point? 

Mostly to make my life easier while writing tests - due to the varieties of possible geodistance algorithms (and those being applied on different shaped earths) it was difficult to find an authoritative answer to the number of decemial places given by the double. 

## Why no WireMock tests? 

I initially wrote the gateway test using Wiremock but [fell afoul of this longstanding bug](https://github.com/wiremock/wiremock/issues/97) which causes integration tests to _sometimes_ fail (usually the install was fine and then followup runs would fail). I spent a lot of time experimenting with the workarounds suggested on the issue but none of them were able to provide a permanent solution and I couldn't stomach committing code that ran the risk of failing tests.

## Why a relatively unknown Geocode library (simplelatlng)

While there were plenty of examples of implementable algorithms around the internet I didn't want to spend unnecessary time writing tests and generally validating correctness when someone else has already put the work in to provide something that is 'good enough' for the purpose. It being entirel offline (rather than offered as part of a paid for API) was also a strong determining factor. 

I did in my [planning_nodes](planning_notes.md) speculate about using Apache Lucene but the API I used was radically altered in subsequent release versions and I prefer not to pin myself to an old version if possible (for security / vulnerability reasons in particular)