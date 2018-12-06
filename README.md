# Android_Xkcd_Persistance

## Introduction

This app will add some persistence to the xkcd app to allow users to set certain comics as favorites and see a list of their favorites..

## Instructions

### Part 1 - Database Schema

First thing we need to do is design a schema for our database. What data do we want to store? We could store the entire comic. This has it's own advantages and disadvantages, it would improve load times, but also add a lot of local storage. For this project, we'll store a history of viewed comics, when they were viewed and allow the user to mark them as a favorite.

1. Our DB will be a single table which will hold 3 columns, an `integer` ID, an `integer` for the timestamp when the item was last read, and an `integer` which will serve as a bool replacement (1 for true and 0 for false)

### Part 2 - Database Contract

The contract is where we define our schema in Android.

1. Create a new Java class called `XkcdDbContract`. Inside of that class, create a static class called `ComicEntry` which will `implements` `BaseColumns`.

> BaseColumns gives us a default _ID field which we can use and is used when wrapping your database with other classes

1. Create a constant data member for your table's name and one for each of the column names.

> We don't need a data member for ID as it is provided with the BaseColumns

1. In db-fiddle, write a CREATE TABLE query to build your comics table as defined in Part 1
2. In your contract class, create a constant `String` called `SQL_CREATE_TABLE`. Copy the query from db-fiddle to this member
3. Replace all column names in your create member with references to the data members.
4. Create a constant data member called `SQL_DELETE_TABLE` this will delete the entire table

> Use the `DROP TABLE IF EXISTS` query to delete your table, just put your table name at the end

1. Be sure that your SQL queries all end in a semi colon.

### Part 3 - Database Helper

1. Create a new `XkcdDbHelper` class which `extends` `SQLiteOpenHelper`
2. Add data members for DATABASE_VERSION and DATABASE_NAME
3. Add a constructor to the class which accepts a `Context` and calls the super constructor passing in the context, db name, `null`, and db version.
4. Override the `onCreate` method. Call `execSQL` on the `SQLiteDatabase` object which is passed into the method. Pass the `SQL_CREATE_TABLE` data member from your contract class to `execSQL`
5. Override the `onUpgrade` method. Call `execSQL` on the passed database and pass in your `SQL_DELETE_TABLE` data member.

### Part 4 - SQL Dao

We will need to implement the CRUD(L) functions in a DAO class to facilitate database interactions and make them clearer to understand. After the CRUD functions are implemented using ids, we can add additional functions as necessary, like filtered read, broader updates, or joins as necessary.

1. First, we need a method to get a singleton instance of the database.

   > A singleton is an object which can only be instantiated once. We can do this by only having private constructors and creating a `getInstance` method which would normally return a reference to the object instance, however, in this case, it won't return anything and will just store the value in a static member so the name will be different

2. Create a data member of type `SQLiteDatabase` 

3. Write a method called `initializeInstance` which will create an instance of your `XkcdDbHelper` class, call `getWritableDatabase` on the object and then store that all in the `SQLiteDatabase` object. This is all done if the `SQLiteDatabase` object is `null`

   > In all methods which need to access the database member, check to see if it null before accessing it.

4. We now need an object to store our db results in as it will store things that aren't in the web api objects. Create a class called `XkcdDbInfo`

5. In this class, add data members to match the ones in your db schema (the id already exists in the comic object so that isn't necessary)

6. Add getters and setters

7. Write a constructor which accepts no parameters and sets the favorite to false and the timestamp to `System.currentTimeMillis`

8. In your `XkcdComic` object, add a data member of type `XkcdDbInfo`, as well as a getter and setter for it.

9. Write a method called `createComic` which accepts a `XkcdComic` object.

10. Create a `ContentValues` variable in this method and assign it using the empty constructor for that class.

11. call the `put` method on that variable for each column in your database

    > be sure to put the column name constant as the first parameter and the desired value from the comic object as the second parameter

12. Once the values object is built, call `insert` on the database object and pass the name of the table, `null`, and the values object

13. Write a method called `readComic`, this will accept an int id. It will return a `XkcdDbInfo` object.

14. In db-fiddle, write a query to read a single entry from the table when provided with the id

15. Take that query and copy it into a string value in your method.

16. Replace the hard coded ID with the one provided in the method signature

17. Call the `rawQuery` method on your database, pass it in your query string and a null value. Store the resulting cursor.

18. If a call to `moveToNext` on that cursor returns true retrieve all the values from that cursor and stores them in a `XkcdDbInfo` object

    > remember, there are two parts to retrieving each piece of data from a cursor, first you call `getInt` on it (or whatever type you want), then you must pass in the column index which is done by calling `getColumnIndexOrThrow` and passing in the column name

19. Return the constructed `XkcdDbInfo`  object

    > For added peace of mind call `getCount` on your cursor and make sure it returns 1 before getting the data

20. Write a method called `updateComic` which accepts a `XkcdDbInfo` object.

21. First we want to write a where clause and check it. Create two String variables, one for the where clause and one for the whole query.

    > Write the where clause then write the rest of the query and tack the where clause onto the end of it

22. Call `rawQuery` with the complete clause and store the result

23. Check that the result affects the correct number of entries (in this case, 1) by checking the `getCount` method

24. Build a `ContentValues` object like in the create method.

25. Pass it into the database's `update` method, then pass in the table name, your values object, the where clause, then null

26. Create a method called `deleteComic` which accepts an id

27. Test your where clause like in the update method

28. Write a DELETE query using your where clause.

29. Call `execSQL` with your query.

    > execSQL will execute an SQL query like rawSQL will, but will not return a result.

    > You can also use the delete method if so inclined.

30. Test your methods by entering test data, reading it, updating it and then deleting it. There is no need to attach this to the GUI yet, just use the debugger or log for now.

If you get to this point, you can continue or wait until tomorrow when we have covered this.

### Part 5 - Connect to Comic DAO

Now that we have the initial chunk of our SQL interface done, we'll want to start connecting it to the existing `XkcdDao`. Thankfully, all of the comic retrieval methods in this class use the same method in the end `getImage` . We'll start working there.

1. In `getComic` , after the comic and image are downloaded, we'll update our database. First call `readComic` and store the returned object.
2. If the returned value is null, we'll need to add a new entry in our database. Construct a new `XkcdDbInfo` object , add it to the retrieved `XkcdComic` object and pass it to your `createComic` method.
3. If the value isn't null, update it with an updated timestamp, store it in your `XkcdComic` object, and then pass it to the `updateComic` method.

### Part 6 - Allow for Favorite Selection

1. In the main activity, add some sort of way the user can mark a comic as favorite and show the result.

   > I just put a checkbox in the top corner, but you could allow the user to long press the comic and change it's border or do anything else you feel clearly communicates this. Remember, we'll also have to allow them to get to the favorites/history page

2. Whatever, you choose, in your listener for that, you'll need to update the favorite value in your comic object (be sure to allow for removing favorite as well as adding favorite) and pass it to a new method from your `XkcdDao` class called `setFavorite`.

3. This class will accept a `XkcdComic` object and use it to call `updateComic`

   > Remember, since we check each comic when we download it, we don't need to check again here.

### Part 7 - Build Favorites Page

Finally, we'll add methods to each of our layers to support.

1. In your `XkcdDbDao` write a new method called `readFavorites`. Select all entries which have the favorite value set to true (or 1 since there isn't a boolean value for SQLite). Return this list.
2. In your `XkcdDao` object, write a wrapper for this method which just calls it and returns the result.
3. Create a new activity. This will need to have a button and the ability to display a list of items in your preferred way.
4. In this activity, call `readFavorites` and use the result to update your list.

#### Challenge

Improve the get random comic feature, by only retrieving unseen comics until they have all been viewed.

Add a list of history to the favorites page
