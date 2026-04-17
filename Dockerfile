# build stage
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# runtime stage
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Set default environment variables
ENV SPRING_PROFILES_ACTIVE=prod
ENV USERNAME=softplan
ENV PASSWORD=softplan123
ENV DB_URL=jdbc:h2:mem:testdb
ENV DB_DRIVER=org.h2.Driver
ENV DB_USERNAME=sa
ENV DB_PASSWORD=
ENV JPA_DDL_AUTO=create-drop
ENV JPA_DIALECT=org.hibernate.dialect.H2Dialect

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]