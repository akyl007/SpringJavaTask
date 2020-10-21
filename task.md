#Task: 1 Point (Configuration of Persistence Layer)
1. Declare missing bean declarations and injections.
    * Some of the classes in the `dao` package should be declared as beans, but they are not. Declared them properly.
    * In the `dao` package, there is also one dependency injection which not is declared properly. Fix it.
    * **Hint:** `@Repository`, `@PersistenceContext`
2. Create a prototype bean of type `java.util.Date`.
    * **Hint:** `@Configuration`, `@Bean`

* **Hint:** Use tests to help you debug the issues.
* **Acceptance criteria:** All enabled tests are passing.


#Task: 1 Point (Implementation of a Service)
1. Remove `@Ignore` annotation from `CartServiceTest` and verify that tests are now failing
2. Implement `CartService` that allows to
    * Add specific items to a cart
    * Remove specific items from a cart
    * Amount of products available in stock is correctly adjusted after every add/remove operation
    * `CartService` class must be declared as a Spring bean
    * Inject beans necessary to implement the service methods (DAO)

* Make sure that service methods are transactional
* **Hint:** `@Service`
* **Acceptance criteria:** Transactional processing is configured properly and all tests are passing.
