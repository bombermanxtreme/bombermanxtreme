#!/bin/sh

sudo apt update && sudo apt install lamp-server^ -y && sudo /etc/init.d/apache2 start && sudo apt-get install nginx -y