mysql -uroot -pvitalnoc <<MYSQLEOF

DROP DATABASE IF EXISTS wsa;

CREATE DATABASE wsa;

MYSQLEOF

cd target/

sudo java -jar wsa_nodeChecker-0-jar-with-dependencies.jar

cd ../

clear
clear
