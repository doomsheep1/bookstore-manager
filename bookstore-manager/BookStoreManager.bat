REM BOOK-STORE-MAANGER APPLICATION STARTUP BAT SCRIPT
@echo off

REM user and secret properties, ideally these should be externalized in another secure directory but for demonstration purposes...
set NORMAL_CLIENT_ID=normal-client
set NORMAL_CLIENT_SECRET={noop}secret-client
set ADMIN_CLIENT_ID=admin-client
set ADMIN_CLIENT_SECRET={noop}secret-admin

REM Optional: Display environment variables to confirm they are set
echo NORMAL_CLIENT_ID=%NORMAL_CLIENT_ID%
echo NORMAL_CLIENT_SECRET=%NORMAL_CLIENT_SECRET%
echo ADMIN_CLIENT_ID=%ADMIN_CLIENT_ID%
echo ADMIN_CLIENT_SECRET=%ADMIN_CLIENT_SECRET%

java -jar ./target/bookstore-manager-0.0.1-SNAPSHOT.jar
pause