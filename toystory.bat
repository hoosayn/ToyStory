echo "Running docker container for sql server and RabbitMQ server"

cmd /c "docker run -d -p 7000:1433 -e sa_password=Aa@123 -e ACCEPT_EULA=Y microsoft/mssql-server-windows-express & docker run -d -p 8000:1433 -e sa_password=Aa@123 -e ACCEPT_EULA=Y microsoft/mssql-server-windows-express & docker run -d -p 9000:1433 -e sa_password=Aa@123 -e ACCEPT_EULA=Y microsoft/mssql-server-windows-express &
docker rm -f moon-rabbit & docker run -d --hostname my-rabbit-msg  --name moon-rabbit micdenny/rabbitmq-windows"

echo "running docker commands finished..."

ping 127.0.0.1 -n 30 > nul

echo "creating db for each microservice..." 

sqlcmd -S 127.0.0.1,7000 -U sa  -P Aa@123 -i"C:\PersonalProject\ToyStory\db\dbsript_admin.sql"
sqlcmd -S 127.0.0.1,8000 -U sa  -P Aa@123 -i"C:\PersonalProject\ToyStory\db\dbsript_registration.sql"
sqlcmd -S 127.0.0.1,9000 -U sa  -P Aa@123 -i"C:\PersonalProject\ToyStory\db\dbsript_donor.sql"

echo "creation of db is finished ..." 


