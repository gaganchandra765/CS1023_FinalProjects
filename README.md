Arbitrary-Precision Arithmetic Library ****


This project provides a Java library for performing arbitrary-precision arithmetic on integers and floating-point numbers. It includes tools to automate building and running the application.

Project Structure: 
CS1023JavaProject/
- arbitraryarithmetic/
  - AInteger.java
  - AFloat.java
- MyInfArith.java
- compile_and_run.py
- build.xml
- Dockerfile
- README.md
- report.tex

Prerequisites

Java Development Kit (JDK): Version 17 or higher.
Python 3: For running the compile_and_run.py script.
Apache Ant: For building the project with build.xml.
Docker: for running the project in a container.

Building the Project
Option 1: Using Ant (Local)

Navigate to the project directory:cd /path/to/project


Run Ant to compile and create the JAR:ant

This creates:
build/aarithmetic.jar
build/classes/ with compiled classes.



Option 2: Using Docker

Build the Docker image:docker build -t arithmetic-app .

This compiles the Java code and creates the JAR inside the container.

Running the Program
Option 1: Using the Python Script (Local)
After building with Ant:
python3 compile_and_run.py int add 123 456


Arguments: <int/float> <add/sub/mul/div> <first number> <second number>
Example output:123 add 456 = 579



Option 2: Directly Running Java (Local)
After building with Ant:
java -cp build/aarithmetic.jar MyInfArith int add 123 456


Output: 123 add 456 = 579

Option 3: Using Docker
docker run --rm arithmetic-app int add 123 456


Output: 123 add 456 = 579

Example Usage

Integer addition:
python3 compile_and_run.py int add 123456789 987654321

Output: 123456789 add 987654321 = 1111111110

Float multiplication:
python3 compile_and_run.py float mul 123.456 -78.123

Output: 123.456 mul -78.123 = -9643.237576288

Integer division:
python3 compile_and_run.py int div -100 5

Output: -100 div 5 = -20

Float subtraction:
python3 compile_and_run.py float sub 0.0 0.5

Output: 0.0 sub 0.5 = -0.5


Notes

The library supports arbitrary-precision integers and floats (up to 30 decimal digits).
Invalid inputs (e.g., "abc") are handled by setting the number to 0 or 0.0 with an error message.
Division by zero outputs an error message and returns 0 or 0.0.



