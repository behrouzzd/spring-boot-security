# spring-boot-security

The application is a web application, which uses Maven, JPA and Spring (Boot, Security, Data). It comes with an embedded H2 database.
This application covers following tasks:

1. Implemented a Restful Controller to manage users and items (there is a many-to-many relationship between user and item)
2. Securing the app using Spring Security including storing user credentials and access
3. Restricting user access to items using ACL mechanism in Spring Security
5. Caching loged in users using EhCahce
6. Logging user activities with AspectJ
7. Supporting token-based authentication
