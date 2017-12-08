#!/bin/sh

echo "#######################################################################"
echo "########### Compile, abrir Navegador y subir servidor #################"
echo "#######################################################################"
mvn compile && xdg-open http://localhost:8080 && mvn spring-boot:run
