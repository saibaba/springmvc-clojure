First, run:
${appserver.home}/bin/startup.sh
where appserver = ~/tools/apache-tomcat.../

Then,

* ant clean
* ant resolve
* ant build 
* ant deploy stop start

* point browser at: http://localhost:9080/hw/clojure

