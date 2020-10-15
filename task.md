<<<<<<< HEAD
# EAR seminar-03-tasks


#Task 1: 1 Point
The application is missing an object model (JPA entity classes). Create an object model corresponding to the class diagram below.

**Acceptance criteria**: Project is compilable, and the object model corresponds to the class diagram below.

![E-shop object model][model]

### License

LGPLv3

[model]: doc/eshop-model.png "E-shop object model class diagram"
=======
#Task: 1 Point
There is one more DAO missing: `UserDao`. Besides basic CRUD operations, it will eventually have to provide a method for getting user based on their username (for login).

Also, we are still missing update and delete methods in our DAOs.

1. Create class `UserDao` and implement its method `findByUsername` which retrieves user given the specified username.
    * _Hint: use a named query._
    * _Hint: do not forget to annotate the DAO with `@Repository` (we will find out more about it next time)._
2. Add missing implementation of update and removal into our DAOs.

**Acceptance criteria**: Project is buildable - tests pass.
>>>>>>> upstream/b201-seminar-04-task
