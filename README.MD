# Group Project 1

**Goals:**

* Perform use-case analysis techniques to discover and specify the conceptual
classes.
* Use design principles to translate conceptual class design into an appropriate set of
abstract and concrete classes and interfaces
* Efficiently develop systems using design patterns, including Facade and Singleton
* Use the Unified Modeling Language to document work
* Implement a design utilizing structures such as classes and interfaces,
* Work in small groups
* Employ Java coding standards

**The Problem**

Create, test, and document a system for an application with business processes and other
requirements described below.
A small co-op grocery store works by having members join it by paying a certain fee. The
business processes (12 in number) /system functionalities are:

* Enroll a member: the system enrolls a member and remembers him/her; it keeps track of
the name, address, phone number, date joined, and the fee paid. Also, the system creates
and maintains a unique id for each member. Only one member is added when this
functionality is invoked.
* Remove a member: If a valid id is received, the corresponding member is removed;
the system would need the member’s id for this purpose. Only one member is removed
when this functionality is invoked.
* Add a product: From time to time, the store decides to carry a new set of products.
Relevant attributes input by the user are: product name, id, stock in hand, current price,
and a reorder level for the product. Once the quantity in stock reaches the reorder level or
below, the product will be automatically reordered. No two products have the same name.
So, if the store carries coconut milk, there could be products such as “Low-value coconut
milk” and “Unsafeway coconut milk.” Only one product is added when this functionality
is invoked. Immediately after a new product is created, the system generates an order for
the product for twice the reorder level. As an example, suppose we have a new product “1 lb packets of Milano Pasta” with a
reorder level of 100 lb, When the product is created, 200 lbs. of the product are ordered.
* Check out a member’ items: The member comes to the check-out counter with a cart
of grocery items. The cashier inputs the product id and quantity of each item in the cart.
The system computes the price and, of course, computes the total price. All members pay
by cash. The system must display the individual items, the number of units, unit price,
and price for the item. For example, if the user purchases three gallons of milk, the
display might be
Milk 3 $4.50 $13.50
The total price must also be displayed.
When a customer is checked out, reorder twice the reorder level for any product whose
level reaches the reorder level (or below that value). The system must display a message
saying that the item will be reordered, how much was reordered, and what the order
number is.
* Process shipment: Whenever there is delivery of items from a supplier, the stocks
must be updated. The system needs the order number. We assume that the quantity
ordered has been delivered. Multiple products may be processed, one by one. Display the
product id, name, and the new stock.
* Change price: Changes the price of a product given its id. The system displays the
product name and the new price.
* Retrieve product info: Given a string, list all products with name that starts with this
string. List the product name, product id, price, stock in hand, and reorder level.
* Retrieve member info: Given a string, the system displays the member’s address, fee
paid, and id of all members whose name begins with the given string. In general, there
will be multiple members listed.
* Print transactions: Given the id of a member and two dates (input in the mm/dd/yyyy
format), the system prints all transactions for the corresponding member between the two
dates (including both dates). The first date input by the actor must be validated to ensure
that it equal to or earlier than the second date.
* List all outstanding (not yet fulfilled) orders: Display the order id, corresponding
product name, date of order, and quantity ordered.
* List all members. Display the name, date joined, address, and phone number.
* 1List all products. Display the product name, id, stock in hand, current price, and a
reorder level for the product
The following could be considered business processes, perhaps, but let us not treat them that
way. They form part of the functionality of the system.
* Save: Saves all data to disk.
* Help: Displays a list of commands.
* Exit: Quits the application.

Make sure that wherever there is reasonable common functionality, factor out code fragments
from similar classes and move them to/create super classes. Use generics where applicable.

**The User Interface and Other Aspects**

For the purposes of ensuring uniformity in grading, I ask the following.
The interface must be non-GUI, but command driven, just like the library system.
I should be able to invoke the business processes by typing in a number as listed under
the business processes above: 

1 for adding members, 2 for removing members, etc and 13 for saving data to disk. Also use 0 for exit and 14 for help.
2. When the program starts, it should give an option to look for and load existing data
on stable storage. If the user answers in the affirmative, that data should be loaded
and used. Do not assume any specific directory structure on my system: use the
current directory.
3. In general (that is unless specified elsewhere), the look and feel of the interface
should be similar to that of the library system. (Obviously, the functionality is
different.) This includes the nature of inputting commands and information, displays,
help screen, etc.
4. Error messages must be as specific as possible.
5. The façade and user interface should communicate via the data transfer pattern, as
implemented in the library system.
6. When the user interface starts, if the user does NOT wish to use a saved file, the program
should give an option to programmatically set up a test bed, by prompting as follows.
7. The system should be organized into packages in a very clean manner.
Do you want to generate a test bed and invoke the functionality using asserts?
If the user answers y or yes:
1) The program first creates a number of products (at least 20) and members (at least 5).
2) After completing Step 1, the program invokes the façade methods for business processes
numbered 1 through 6. Using assert statements, it checks the return values and ensure that
the returned values are correct. This step should be a thorough test of business processes
numbered 1-6.
3) After the automated testing processing is complete, there should be at least 20 products
and 5 members. There should have been at least a couple of outstanding orders, multiple
products (at least 3 or 4) starting with the same sequence of 2-4 characters.
4) Finally, the program presents the command line interface, so the user can enter more test
data using the interactive approach. The test bed from Step (3) must be available for
testing in Step 4.

You may have to put the automated tester class in the business package, if you have provided
restricted view of business objects in the ui packag
