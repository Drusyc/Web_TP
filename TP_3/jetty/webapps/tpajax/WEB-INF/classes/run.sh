#!/bin/sh

javac EnsimagServlet.java -cp ../lib/jsoup-1.7.2.jar:../../../../lib/servlet-api-3.1.jar && cd ../../../../ && java -jar start.jar

