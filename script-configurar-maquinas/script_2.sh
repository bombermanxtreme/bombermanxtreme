#!/bin/sh

echo  #sudo nano /etc/environment#
echo #JAVA_HOME="/usr/lib/jvm/java-8-oracle"#
echo #source /etc/environment#
echo ############

sudo apt-get update -y && sudo apt-get install git -y && sudo apt-get install maven -y && sudo apt-get install oracle-java8-installer -y && sudo update-alternatives --config java