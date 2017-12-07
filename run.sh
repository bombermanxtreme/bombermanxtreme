#!/bin/sh

echo "Compile, abrir Navegador y subir servidor"
mvn compile && xdg-open http://localhost:8080 && mvn spring-boot:run
