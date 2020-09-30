# Task

**Maximum**: 1 point

1. Fork project [https://gitlab.fel.cvut.cz/ear/b201-eshop](https://gitlab.fel.cvut.cz/ear/b201-eshop)
2. Clone your fork of the _b201-eshop_ project
3. Checkout branch _b201-seminar-02-task_
4. Create a Maven project using the `HelloWorld.java`, `HelloWorldTest.java`, and `logback.xml` files
    * `HelloWorld` is a servlet which should be accessible to the client
    * `HelloWorldTest` is a simple JUnit-based test of `HelloWorld`
    * `logback.xml` is a Logback configuration file

# Acceptance Criteria

* Project has groupId, artifactId, name and description
* Project can be packaged as **WAR** using Maven
* Tests are run during build (and they pass)
* When the resulting WAR is deployed to an application server (e.g., Tomcat), the servlet is accessible through a web browser
* Servlet access is logged based on the provided Logback configuration

### Hints

* Use Google or Maven central to find exact dependency identifiers
* `logback.xml` should go into `src/main/resources`

##### Useful Dependencies

* SLF4J, Logback
* Servlet API (scope _provided_)
* JUnit (scope _test_)

##### Useful Maven Plugins

* Maven compiler plugin
* Maven WAR plugin
* Maven Surefire plugin
