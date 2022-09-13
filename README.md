# SDET - Test
## NASA - Challenge

### Requirements 
- Java 8 or greater.
- Maven.
- [Allure](https://docs.qameta.io/allure-report/#_get_started)

### Tests Created
#### Mars Rover Photos:
1. Retrieve the first 10 Mars photos made by "Curiosity" on 1000 Martian sol.
2. Retrieve the first 10 Mars photos made by "Curiosity" on Earth date equal to 1000 Martian sol.
3. Retrieve and compare the first 10 Mars photos made by "Curiosity" on 1000 sol and on Earth date equal to 1000 Martian sol.
4. Validate that the amounts of pictures that each "Curiosity" camera took on 1000 Mars sol is not greater than 10 times the amount taken by other cameras on the same date.

#### Other Tests 

Three more tests were created, 2 validating negative scenarios, plus one more using a DDT approach with a Data Provider. 

### Tests Execution

It could be done either by using the *testng.xml*, `mvn test` command or by running the tests directly on the classes.


### Report Generation

If the requirements are met then simply execute `allure serve` on the command line.

### Time Invested

5 hours approximately. 



