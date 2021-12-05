# Improvements

While the app in its present state meets requirements there are several possible areas for improvement: 


* UserInOrNearLondonService and Resource should be abstracted to take a city name as an argument and pass it on to the relative downstream call
* ~~UserInOrNearLondonResource can be expanded to return a List<UserWithCity> (it was unclear what 'User' data was being asked for in the spec given it's only returned from one downstream endpoint~~
    * ~~Instead of multiple GETS for similar calls whether the end-user wants `User`s or `UsersWithCity` could be controlled by a request parameter~~ - implemented.  
* The filterUserByProximityService can be improved in multiple ways:
    * Abstract to allow for within X distance rather than hardCoded 50
    *  Offer the ability to use a custom coordinate for a city rather than the hard coded London Value
        * As mentioned in [assumptions](assumptions%20and%20questions.md) for the sake of achieving MVP viability I opted for a static coordinate pulled from a reputable dataset but the meaning of "near London" is nebulous and there's a strong argument that it should be a boundary point not a centre
    * Abstract to allow for with 50 miles of any city rather than hardcoded London (or at least a decent subset of big' cities / towns / locations )
        * This could be done in an offline manner by storing a dataset (e.g. simpleMaps) of locations and their coordinates in a file (or a DB if you were really serious about it) that can be parsed at startup into a Map<CityName, LatLng> 
        * Or by using another API to provide geocoding functionality. 
* Should have added a standalone JsonMapper serialisation test 
* Don't use 'LIVE' data as part of test data set (initially done as a shortcut to help time management), would not do with actual user data))       
* In general the naming of TEST DATA variables is not great and probably means some tests aren't immediately intuitive for those unfamiliar with code base 
* Improve test readability with Display Names
