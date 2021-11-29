# Rough Notes

INSTRUCTIONS:

>   "todo": "Build an API which calls this API, and returns people who are listed as either living in London, or whose current coordinates are within 50 miles of London. Push the answer to Github, and send us a link." 

Task breakdown: 

1. Retrieve a list of people who live in London
2. Retrieve a list of people whose coordinates are within 50 miles of London 
3. Make 1) and 2) accessible via an API (combined as one result)

## Task 1 planning: 

If we're not validating the results then blindly return the downstream API results... but they're pretty suspect (see Random observations)

Probably going to offer a 'tier list' of data trusting with options to cross-validate:

E.g.
Trust 'city' > 'Lat/Long' >  I.P
Allow requirement that city AND Lat/Long match

Won't prioritise I.P data due to 'realistic' use cases like VPNs 

### Random observations

* The City API Only returns results for 'London' -- case matters 
* None of the example results for city = London have an I.P lookup that matches the lat/long listed 
* User (266)  with a Lat/Long that's definitely Near London (Loughton) has a city of "L’govskiy" ?? and an I.P lookup that resolves to Minnesota ?? 
* User (322) with A Lat/Long that's sort of near London (~ 50 miles) has a city of "Rokiciny" (Poland), and an I.P lookup that resolves to Marseille...
* There are users whose City + Lat/Long do match up (687) but not I.P

## Task 2 Planning

Will have more details in future days - searching 'Java lat long distance' reveals several useful S/O links

https://stackoverflow.com/questions/837872/calculate-distance-in-meters-when-you-know-longitude-and-latitude-in-java
https://stackoverflow.com/questions/120283/how-can-i-measure-distance-and-create-a-bounding-box-based-on-two-latitudelongi

Prefer to use an external library:


org.apache.lucene.spatial.util
Class GeoDistanceUtils

looks perfect (and Apache, woo)

https://github.com/JavadocMD/simplelatlng  may be okay 


## Notes on API 

### LAT / LONG Notes:

Positive Long means West
Negative Long means East

Positive Lat means North
Negative Lat means South

A London longitude should be pretty close to 0
A London latitude should be pretty close to ~51.5


## Available API reference 

All use `GET`

#### City 

`/city/{city}/users`

Returns an array of objects with the following form: 

```
{
    "id": int,
    "first_name": string,
    "last_name": string,
    "email": string,
    "ip_address": string,
    "latitude": double, (but some of them are stored as Strings!) 
    "longitude": double (but some of them are stored as Strings!) 
}
```

#### Users

`/users`

Returns an array of users 

Same format as above


#### Specific User

`/user/{id}/`

Same as above but also contains 

{"city": String}

