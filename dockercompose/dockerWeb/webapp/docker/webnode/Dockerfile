FROM openjdk:8-jdk-alpine
ENV JAVA_OPTS="-Xms512M -Xmx1024M -XX:PermSize=128M -XX:MaxPermSize=512M"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar --spring.profiles.active=prd" ]