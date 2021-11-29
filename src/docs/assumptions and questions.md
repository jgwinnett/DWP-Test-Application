# Assumption Log


## Questions

What is the source of the 'City' data? User input? Can it be a mistake? 



## Confident in these assumptions: 

1. A London longitude should be pretty close to 0 given it's a measurement based off of Greenwich 
    Positive Long means West
    Negative Long means East 
2. A London latitude should be pretty close to ~51.5
    Positive Lat means 


2. Must pick an arbitrary(ish) co-ordinate for 'the centre of London' if going down co-ord route 
3. ... or a ring for the outskirts (more work)


## Assumptions to test:

1. All data other than Long + Lat are consistent types 
2. That results of {city} API are correct 
   * Most of the results from a London lookup are an awful long way away... 


## Gotchas to be aware of: 

* Longitude + Latitude are sometimes returned as Strings 

