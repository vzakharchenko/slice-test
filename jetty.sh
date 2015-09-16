#! /bin/bash
mvn clean install
cd webapp
export MAVEN_OPTS="-Xdebug -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=9190 -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9290 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dnet.sf.ehcache.skipUpdateCheck=true"

mvn -P jetty_run clean install  -DskipTests=true