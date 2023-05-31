# Stage 1: Build and install the DTO project into local maven repository
FROM maven:3.8.3-openjdk-17 AS builder
WORKDIR /PochemonLib

# Copy your DTO project
COPY ./PochemonLib/pom.xml ./pom.xml
COPY ./PochemonLib/src ./src

# Build the DTO project and install it into the local maven repository
RUN mvn clean install

WORKDIR /StoreService

# Copy your main project
COPY ./StoreService/pom.xml ./pom.xml
COPY ./StoreService/src ./src

# Build the main project with the dependencies
RUN mvn clean package -DskipTests

# Stage 3: Copy the artifact and run
FROM openjdk:17-jdk-slim
WORKDIR /StoreService

COPY --from=builder /StoreService/target/store-0.0.1-SNAPSHOT.jar ./app.jar

ENTRYPOINT ["java","-jar","app.jar"]