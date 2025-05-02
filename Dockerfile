FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

COPY . .
 
RUN javac arbitraryarithmetic/AInteger.java \
        arbitraryarithmetic/AFloat.java \
        MyInfArith.java

ENTRYPOINT [ "java" , "MyInfArith" ]