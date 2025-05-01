# This script compiles and runs my Java math program
# Run it like: python3 compile_and_run.py int add 123 456

import sys
import os
import subprocess

# Check if we have 4 arguments
if len(sys.argv) != 5:
    print("Error: Need exactly 4 arguments!")
    print("Usage: python3 compile_and_run.py <int/float> <add/sub/mul/div> <first number> <second number>")
    sys.exit(1)

# Get the arguments
type_arg = sys.argv[1]  # int or float
operation = sys.argv[2]  # add, sub, mul, div
first_num = sys.argv[3]  # first number
second_num = sys.argv[4]  # second number

# Check if type is valid
if type_arg != "int" and type_arg != "float":
    print("Error: Type must be 'int' or 'float'!")
    sys.exit(1)

# Check if operation is valid
if operation not in ["add", "sub", "mul", "div"]:
    print("Error: Operation must be 'add', 'sub', 'mul', or 'div'!")
    sys.exit(1)

# Check if Java files exist
java_files = [
    "arbitraryarithmetic/AInteger.java",
    "arbitraryarithmetic/AFloat.java",
    "MyInfArith.java"
]
for file in java_files:
    if not os.path.exists(file):
        print(f"Error: Cannot find {file}!")
        sys.exit(1)

# Compile the Java files
print("Compiling Java files...")
try:
    result = subprocess.run(
        ["javac", "arbitraryarithmetic/AInteger.java", "arbitraryarithmetic/AFloat.java", "MyInfArith.java"],
        capture_output=True,
        text=True
    )
    if result.returncode != 0:
        print("Error: Compilation failed!")
        print(result.stderr)
        sys.exit(1)
    print("Compilation successful!")
except Exception as e:
    print("Error: Something went wrong while compiling!")
    print(e)
    sys.exit(1)

# Run the Java program
print(f"Running: MyInfArith {type_arg} {operation} {first_num} {second_num}")
try:
    result = subprocess.run(
        ["java", "MyInfArith", type_arg, operation, first_num, second_num],
        capture_output=True,
        text=True
    )
    if result.returncode != 0:
        print("Error: Program failed to run!")
        print(result.stderr)
        sys.exit(1)
    # Print the output
    print("Output:")
    print(result.stdout)
except Exception as e:
    print("Error: Something went wrong while running the program!")
    print(e)
    sys.exit(1)