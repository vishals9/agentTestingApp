#################################################################################################
## Makefile for springNsecom.war
## @Author : Vishal Singh	
## @Date Created : 01 Nov 2019
##
#################################################################################################
# Installing oracle driver into maven repository
build:
	mvn clean
	mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc7 -Dversion=12.1.0.2 -Dpackaging=jar -Dfile=./lib/ojdbc7.jar
	mvn install
