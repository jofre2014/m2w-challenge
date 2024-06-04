#
# Build
#
FROM maven:3.9.6-amazoncorretto-21 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean install -DskipTests


#
# RUN
#
FROM eclipse-temurin:21
COPY --from=build /home/app/target/w2m-*.jar /opt/app/w2mApp.jar
EXPOSE 8080
CMD ["java", "-jar", "/opt/app/w2mApp.jar"]