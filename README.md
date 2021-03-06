## Introduction
This is reimplementation of spring webmvc classic tutorial, in clojure.
A bunch of different spring/webmvc apis have been used in this version to account for the changes happened to spring/webmvc framework over the years.

See ivy.xml for all dependencies, and their versions used.

Also, added a (clojure) shell controller for running clojure snippets directly from the browser.

## Running application

#### Start by running app server
First, run:
${appserver.home}/bin/startup.sh
where appserver.home = ~/tools/apache-tomcat.../

Also, make sure that you create the user specified in build.properties in tomcat: ${appserver.home}/conf/tomcat-users.xml

#### Then, start database

Start DB by running db/server.sh (in another window).

#### Finally, Compile and deploy application
Finally, compile, and deploy code with following ant tasks:
* ant clean
* ant resolve
* ant build 
* ant tests
* ant stop
* (manually remove the servlet-api.jar from WEB-INF/lib, TODO: find a better way via ivy conf, also make sure it is not in ${appserver.home}/webapps/springapp-clj/WEB-INF/lib)
* ant deploy
* ant start (you might have to wait sometime before starting to let container deploy app)
* ant createTables loadData printData

#### You are ready to use it

* Point browser at: http://localhost:9080/springapp-clj/

## References

* http://docs.spring.io/docs/Spring-MVC-step-by-step/part1.html
* http://terasolunaorg.github.io/guideline/1.0.x/en/Overview/SpringMVCOverview.html
* http://steveliles.github.io/configuring_global_exception_handling_in_spring_mvc.html
* http://www.journaldev.com/2623/spring-bean-autowire-by-name-type-constructor-autowired-and-qualifier-annotations-example
* https://dzone.com/articles/converting-spring
* http://stackoverflow.com/questions/1069958/neither-bindingresult-nor-plain-target-object-for-bean-name-available-as-request
* http://stackoverflow.com/questions/2543912/how-do-i-run-junit-tests-from-inside-my-java-application
* http://examples.javacodegeeks.com/enterprise-java/spring/mvc/spring-mvc-form-handling-example/
* http://stevendick.github.io/blog/2013/08/13/write-a-spring-mvc-controller-in-clojure/
* http://docs.spring.io/spring/docs/current/spring-framework-reference/html/jdbc.html
* https://github.com/bbatsov/clojure-style-guide#comments
* http://stackoverflow.com/questions/4313505/converting-clojure-data-structures-to-java-collections
* http://clojure.org/java\_interop
* http://stackoverflow.com/a/3743975/4333370
* http://www.mkyong.com/spring-mvc/spring-mvc-textarea-example/
* http://stackoverflow.com/questions/5459865/how-can-i-throw-an-exception-in-clojure
* http://sujitpal.blogspot.com/2006/02/spring-in-chains.html (interesting dialog)
