cls
cd\
echo The folder is :%~dp0
cd %~dp0
call mvn clean
call mvn compile
call mvn test