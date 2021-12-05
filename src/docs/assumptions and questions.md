# Assumption Log


## Questions

What is the source of the 'City' data? User input? Can it be a mistake? 
    A: Instructions say to trust it
What representation of 'user' do they want returned?
    - 

## Confident in these assumptions: 

1. A London longitude should be pretty close to 0 given it's a measurement based off of Greenwich 
    Positive Long means West
    Negative Long means East 
2. A London latitude should be pretty close to ~51.5
    Positive Lat means 


2. Must pick an arbitrary(ish) co-ordinate for 'the centre of London' if going down co-ord route 
   - Used a static value from simple-maps dataset 
3. ... or a ring for the outskirts (more work)
4. For integration testing assume that emails are unique


## Assumptions to test:

1. All data other than Long + Lat are consistent types 
    - Serialisation int test seems to indicate that there are no other problems (and that the inconsistent data is parsed fine) 
2. That results of {city} API are correct 
   * Most of the results from a London lookup are an awful long way away... 
   * But going with a strict reading of the instructions. 

## Gotchas to be aware of: 

* Longitude + Latitude are sometimes returned as Strings 

