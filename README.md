First, run:
${appserver.home}/bin/startup.sh
where appserver.home = ~/tools/apache-tomcat.../

Also, make sure that you create the user specified in build.properties in tomcat: ${appserver.home}/conf/tomcat-users.xml

Then,

* ant clean
* ant resolve
* ant build 
* ant tests
* ant deploy stop start

* point browser at: http://localhost:9080/springapp-clj/

Ref:

* http://docs.spring.io/docs/Spring-MVC-step-by-step/part1.html
* http://terasolunaorg.github.io/guideline/1.0.x/en/Overview/SpringMVCOverview.html
* http://steveliles.github.io/configuring_global_exception_handling_in_spring_mvc.html
* http://www.journaldev.com/2623/spring-bean-autowire-by-name-type-constructor-autowired-and-qualifier-annotations-example
* https://dzone.com/articles/converting-spring
* http://stackoverflow.com/questions/1069958/neither-bindingresult-nor-plain-target-object-for-bean-name-available-as-request
