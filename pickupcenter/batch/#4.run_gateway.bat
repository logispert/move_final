call setenv.bat
REM java  -Xmx400M -Djava.security.egd=file:/dev/./urandom -jar food-delivery\gateway\target\boot-camp-gateway-0.0.1-SNAPSHOT.jar --spring.profiles.active=default

cd ..\gateway
mvn clean spring-boot:run
pause ..