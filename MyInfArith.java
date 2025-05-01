// MyInfArith class to do math with big numbers
// Takes 4 arguments: type, operation, first number, second number
public class MyInfArith {
    public static void main(String[] args) {
        // Check if we have 4 arguments
        if (args.length != 4) {
            System.out.println("Error: Need exactly 4 arguments!");
            System.out.println("Usage: java MyInfArith <int/float> <add/sub/mul/div> <first number> <second number>");
            return;
        }

        // Get the arguments
        String type = args[0]; // int or float
        String operation = args[1]; // add, sub, mul, div
        String firstNum = args[2]; // first number
        String secondNum = args[3]; // second number

        // Check if type is valid
        if (!type.equals("int") && !type.equals("float")) {
            System.out.println("Error: Type must be 'int' or 'float'!");
            return;
        }

        // Check if operation is valid
        if (!operation.equals("add") && !operation.equals("sub") && 
            !operation.equals("mul") && !operation.equals("div")) {
            System.out.println("Error: Operation must be 'add', 'sub', 'mul', or 'div'!");
            return;
        }

        // Do integer operations
        if (type.equals("int")) {
            // Make AInteger objects
            arbitraryarithmetic.AInteger num1 = new arbitraryarithmetic.AInteger(firstNum);
            arbitraryarithmetic.AInteger num2 = new arbitraryarithmetic.AInteger(secondNum);

            // Do the operation
            arbitraryarithmetic.AInteger result = null;
            if (operation.equals("add")) {
                result = num1.add(num2);
            } else if (operation.equals("sub")) {
                result = num1.subtract(num2);
            } else if (operation.equals("mul")) {
                result = num1.multiply(num2);
            } else if (operation.equals("div")) {
                result = num1.divide(num2);
            }

            // Print the result
            System.out.println(num1 + " " + operation + " " + num2 + " = " + result);
        }
        // Do float operations
        else if (type.equals("float")) {
            // Make AFloat objects
            arbitraryarithmetic.AFloat num1 = new arbitraryarithmetic.AFloat(firstNum);
            arbitraryarithmetic.AFloat num2 = new arbitraryarithmetic.AFloat(secondNum);

            // Do the operation
            arbitraryarithmetic.AFloat result = null;
            if (operation.equals("add")) {
                result = num1.add(num2);
            } else if (operation.equals("sub")) {
                result = num1.subtract(num2);
            } else if (operation.equals("mul")) {
                result = num1.multiply(num2);
            } else if (operation.equals("div")) {
                result = num1.divide(num2);
            }

            // Print the result
            System.out.println(num1 + " " + operation + " " + num2 + " = " + result);
        }
    }
}