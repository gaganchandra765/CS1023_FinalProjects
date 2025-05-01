package arbitraryarithmetic;

public class AInteger {
    public  String number;
    public boolean isNegative;
    // lets say the default is 0
    public AInteger(){
        this.number = "0";
        this.isNegative = false;
    }

    public AInteger(String s){
        if (s.startsWith("-")) {
            isNegative = true;
            number = s.substring(1); // Take out the minus
        } else {
            isNegative = false;
            number = s;
        }
        // Remove leading zeros, this is yet to be defined
        number = removeLeadingZeros(number);
        if (number.equals("")) {
            number = "0";
            isNegative = false;
        }
    }

     // Copy constructor
     public AInteger (AInteger other) {
        this.number = other.number;
        this.isNegative = other.isNegative;
    }

    // Making AIntegers from Strings
    public static AInteger parse(String s) {
        return new AInteger(s);
    }
    // Check if string is a valid number
    private boolean isValidNumber(String s) {
        if (s == null || s.isEmpty()) return false;
        if (s.equals("-")) return false;
        if (s.startsWith("-")) s = s.substring(1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }
    // removing leading zeros
    private String removeLeadingZeros(String s) {
        String result = s;
        while (result.startsWith("0") && result.length() > 1) {
            result = result.substring(1);
        }
        if (result.equals("0") || result.isEmpty()) {
            isNegative = false;
            return "0";
        }
        return result;
    }
    // this is to display our number
    public String toString() {
        if (number.equals("0")) {
            return "0";
        }
        if (isNegative) {
            return "-" + number;
        }
        return number;
    }
    // Starting with operations
    // Addition
    public AInteger add(AInteger b) {
        if (isNegative == b.isNegative) {
            String sum = addStrings(number, b.number);
            AInteger result = new AInteger(sum);
            result.isNegative = isNegative;
            return result;
        } else {
            if (compareAbsolute(number, b.number) >= 0) {
                String diff = subtractStrings(number, b.number);
                AInteger result = new AInteger(diff);
                result.isNegative = isNegative;
                return result;
            } else {
                String diff = subtractStrings(b.number, number);
                AInteger result = new AInteger(diff);
                result.isNegative = b.isNegative;
                return result;
            }
        }
    }
     // Subtract two numbers
     public AInteger subtract(AInteger b) {
        AInteger negB = new AInteger(b);
        negB.isNegative = !b.isNegative;
        return add(negB);
    }

    // Multiply two numbers
    public AInteger multiply(AInteger b) {
        String product = multiplyStrings(number, b.number);
        AInteger result = new AInteger(product);
        result.isNegative = isNegative != b.isNegative; // Negative if signs are different
        return result;
    }

    // Divide two numbers
    public AInteger divide(AInteger b) {
        if (b.number.equals("0")) {
            System.out.println("Error: Cannot divide by zero!");
            return new AInteger();
        }
        String quotient = divideStrings(number, b.number);
        AInteger result = new AInteger(quotient);
        result.isNegative = isNegative != b.isNegative;
        return result;
    }

    // Compare absolute values of two strings
    private int compareAbsolute(String a, String b) {
        a = removeLeadingZeros(a);
        b = removeLeadingZeros(b);
        if (a.length() != b.length()) {
            return a.length() - b.length();
        }
        return a.compareTo(b);
    }

    // Add two strings
    private String addStrings(String a, String b) {
        StringBuilder result = new StringBuilder();
        int carry = 0;
        int i = a.length() - 1;
        int j = b.length() - 1;

        while (i >= 0 || j >= 0 || carry > 0) {
            int sum = carry;
            if (i >= 0) {
                sum += a.charAt(i) - '0';
                i--;
            }
            if (j >= 0) {
                sum += b.charAt(j) - '0';
                j--;
            }
            carry = sum / 10;
            result.append(sum % 10);
        }

        return removeLeadingZeros(result.reverse().toString());
    }

    // Subtract two strings (a >= b)
    private String subtractStrings(String a, String b) {
        StringBuilder result = new StringBuilder();
        int borrow = 0;
        int i = a.length() - 1;
        int j = b.length() - 1;

        while (i >= 0) {
            int digitA = a.charAt(i) - '0';
            int digitB = (j >= 0) ? b.charAt(j) - '0' : 0;
            digitA -= borrow;
            if (digitA < digitB) {
                digitA += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }
            result.append(digitA - digitB);
            i--;
            if (j >= 0) j--;
        }

        return removeLeadingZeros(result.reverse().toString());
    }

    // Multiply two strings
    private String multiplyStrings(String a, String b) {
        int[] result = new int[a.length() + b.length()];
        for (int i = a.length() - 1; i >= 0; i--) {
            for (int j = b.length() - 1; j >= 0; j--) {
                int digitA = a.charAt(i) - '0';
                int digitB = b.charAt(j) - '0';
                int pos = (a.length() - 1 - i) + (b.length() - 1 - j);
                result[pos] += digitA * digitB;
                result[pos + 1] += result[pos] / 10;
                result[pos] %= 10;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = result.length - 1; i >= 0; i--) {
            sb.append(result[i]);
        }
        return removeLeadingZeros(sb.toString());
    }

    // Divide two strings
    private String divideStrings(String a, String b) {
        if (a.equals("0")) return "0";
        StringBuilder quotient = new StringBuilder();
        String remainder = "";
        for (int i = 0; i < a.length(); i++) {
            remainder += a.charAt(i);
            remainder = removeLeadingZeros(remainder);
            int count = 0;
            while (compareAbsolute(remainder, b) >= 0) {
                remainder = subtractStrings(remainder, b);
                count++;
            }
            quotient.append(count);
        }
        return removeLeadingZeros(quotient.toString());
    }
}
