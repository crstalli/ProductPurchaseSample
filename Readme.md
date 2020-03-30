#About
This is a Java Spring boot project designed to meet the following requirements.

#Description
Project to compute the average price and total price of all products that match the rules of a catalog from a salesman.

#Problem
A salesman is selling a set of products. Each product is described by a set of attributes, such as name, type, color, cost and weight, each of which may have a different data type (String, Boolean, Number). 

A company is looking to buy products at the best possible prices, and which best match it’s needs. It has many different products it is looking to purchase. It defines the products it wishes to buy with a set of rules. Each rule is defined by a set of conditions, and a score for if that rule matches. Each condition is made up of an attribute name, a value, and a comparison operator. Some rules might be negative, meaning that if the rule matches, the scoring should be negative. A rule might look like:

      color == BLUE && price < 17.75 && quantity > 750, 100

The company realizes that it is very time consuming and error prone to sort through the salesman’s goods and is looking to implement a system that will:
##Functionality
a)	Score all of the salesman’s products on how well they match their product definitions by calculating the sum of the rule scores, which is the percentage of conditions which match, multiplied by the score.
b)	Filter the potential products to just those that pass a given threshold (assume 50% as the cutoff).
c)	Calculate the total and average prices for all the products that score sufficiently highly.


#Assumptions
- Product Property types will be defined by each individual condition. 
- Condition property types will be determined by attempting to parse into the 3 types in the following order: Number, Boolean, String.
- Properties can only use comparable or a combination excluding >< (>, < ==, <=, >=). The properties will be compared to the products using the types comparable operator.  
- Condition property names cannot be used more than once for a given product (and thus assume different product attribute types) - ex: Price=12.20, Price=High
- A product may not have repeating property attribute names (e.g. can't have color = blue and color = orange)
- An attribute may have a varying type across products (e.g. Product 1: Price = High, Product 2: Price = 12.20)
- Lets assume that we cannot use parenthesis for now but we will structure the application so that rule parsing could allow for it in the future.
- According to the problem, it says that we calculate the sum of the rule scores, which is the percentage of conditions matched, multiplied by the score. This seems invalid  as rules have scores. Therefore, I am going to calculate the product evaluation as: total score of passing rules/total score of all positive rule scores. Per the assignment, only products great than or equal to 50% of the maximum available score will be recommended to purchase.
- Products will be defined in a .csv(catalog) (delimiter |)file with the following format. id:Number name:String, price: Number, attributes string. Attributes must be only letters and  values must be alphanumeric. Spaces are not allowed
- When comparing a rule to the products, if the a rule attribute type doesn't match the same product attribute type OR the rule attribue isn't present on the product, the condition in the rule won't pass. - Property Names can only be composed of characters and are case insensitive.
- I cannot determine the character (an arrow is used in the example) used to denote the score of a rule, so I will use a comma. (e.g. color == BLUE && price < 17.75 && quantity > 750, 100)
- Rule Property types that don't match product type will have their condition evaluated to false(ex: price==true would always evaluate to false as price is defined /evaluated to a  double).  - Because Strings can be values as well as property names, property names in conditions should always be on the left hand side of operators. (Ex: color == blue is valid but blue==color is invalid).
- There must be a space in between a property name/value/condition and the operator.
- Null is not a valid value to compare a property against.
- We are not completely validating the content of a product, as long as it fits the definition. EX: Ids may not be unique, ID and price column could be reversed in the catalog data. As long as they are the correct type, we assume they are valid.
- The quantity of products * number of rules fits in memory with the current design.
- Comparison values are not look-up values in the product.

# Running
##Prerequisites 
- Java JDK 8
- Gradle 6.3 (or use ./gradlew to use a prepackaged gradle distribution)

##Executing
gradle clean bootRun
 
##Errors
###Catalog Parse Error Codes
- 1 - There are not 4 columns provided for a product.
- 2 - Could not create a product due to missing mandatory fields (id, name, price, attributes), or if they have an invalid type.
- 3 - Attribute name or values are not alphanumeric
- 4 - Product attributes are not in the format (attribute=value)
- 5 - Price, name, or id is found in the set of attributes.
 
 
###Rule Parsing Error Codes
- 1 - Rule is not in the format of condition(string), score(number)
- 2 - Score is not a number
- 3 - A condition is empty or does not have a left hand side, operator, and then right hand side.
- 4 - A Condition is not in the following form: propertyName ValidOperator comparisonValue
- 5 - A condition operator is invalid

##Testing Examples
In the resources folder, we have a sample catalog. 

Below is a sample interaction.
```
id >= 1026,-50
Total Price: 153.97
Average Price: 38.4925
id >= 1026,1000
Total Price: 605.44
Average Price: 100.90666666666668
id >=  ,1000
```


#Unit Tests
Todo
