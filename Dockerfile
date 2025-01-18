FROM openjdk:23-jdk-slim as builder

# Install Maven
RUN apt-get update && apt-get install -y maven

# Set the working directory inside the container
WORKDIR /app

# Copy the local code to the container
COPY . .

# Run Maven to build the application
RUN mvn clean package -DskipTests

FROM openjdk:23-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Install unzip
RUN apt-get update && apt-get install -y unzip

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/parkmate-0.0.1-SNAPSHOT.war /app/parkmate.war

# Copy the Oracle Wallet .zip file from the project to the container
COPY src/main/resources/Wallet_parkingdb.zip /app/Wallet_parkingdb.zip

# Unzip the wallet to a specific directory
RUN unzip /app/Wallet_parkingdb.zip -d /app/wallet && rm /app/Wallet_parkingdb.zip

# Set the environment variable for the wallet location
ENV TNS_ADMIN=/app/wallet

# Expose the port your application is running on
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "/app/parkmate.war"]