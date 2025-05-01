
package arbitraryarithmetic;

public class AFloat {
    private String integerPart; // Part before decimal, like "123"
    private String fractionalPart; // Part after decimal, like "456"
    private boolean isNegative; // True if number is negative
    private static final int MAX_PRECISION = 30; // Max decimal digits

    // Default constructor, sets to 0.0
    public AFloat() {
        integerPart = "0";
        fractionalPart = "0";
        isNegative = false;
    }

    // Constructor with string like "-123.456"
    public AFloat(String s) {
        if (!isValidFloat(s)) {
            System.out.println("Error: Invalid float, setting to 0.0");
            integerPart = "0";
            fractionalPart = "0";
            isNegative = false;
            return;
        }
        if (s.startsWith("-")) {
            isNegative = true;
            s = s.substring(1);
        } else {
            isNegative = false;
        }
        // Split at decimal point
        String[] parts = s.split("\\.");
        integerPart = parts[0].isEmpty() ? "0" : parts[0];
        fractionalPart = parts.length > 1 ? parts[1] : "0";
        // Clean up
        integerPart = removeLeadingZeros(integerPart);
        fractionalPart = removeTrailingZeros(fractionalPart);
        // Truncate to max precision
        if (fractionalPart.length() > MAX_PRECISION) {
            fractionalPart = fractionalPart.substring(0, MAX_PRECISION);
        }
        if (integerPart.equals("0") && fractionalPart.equals("0")) {
            isNegative = false;
        }
    }

    // Copy constructor
    public AFloat(AFloat other) {
        this.integerPart = other.integerPart;
        this.fractionalPart = other.fractionalPart;
        this.isNegative = other.isNegative;
    }

    // Static parse to make AFloat from string
    public static AFloat parse(String s) {
        return new AFloat(s);
    }

    // Check if string is a valid float
    private boolean isValidFloat(String s) {
        if (s == null || s.isEmpty()) return false;
        if (s.equals("-") || s.equals("-.") || s.equals(".")) return false;
        // Allow minus sign
        String check = s.startsWith("-") ? s.substring(1) : s;
        // Check for one decimal point
        int dotCount = 0;
        for (char c : check.toCharArray()) {
            if (c == '.') dotCount++;
            else if (c < '0' || c > '9') return false;
        }
        if (dotCount > 1) return false;
        return true;
    }

    // Remove leading zeros
    private String removeLeadingZeros(String s) {
        String result = s;
        while (result.startsWith("0") && result.length() > 1) {
            result = result.substring(1);
        }
        return result.isEmpty() ? "0" : result;
    }

    // Remove trailing zeros
    private String removeTrailingZeros(String s) {
        String result = s;
        while (result.endsWith("0") && result.length() > 1) {
            result = result.substring(0, result.length() - 1);
        }
        return result.isEmpty() ? "0" : result;
    }

    // Convert to string
    public String toString() {
        if (integerPart.equals("0") && fractionalPart.equals("0")) {
            return "0.0";
        }
        String result = integerPart;
        if (!fractionalPart.equals("0")) {
            result += "." + fractionalPart;
        }
        return isNegative ? "-" + result : result;
    }

    // Add two floats
    public AFloat add(AFloat b) {
        // Align decimal places
        int maxFracLen = Math.max(fractionalPart.length(), b.fractionalPart.length());
        String aWhole = integerPart + fractionalPart + "0".repeat(maxFracLen - fractionalPart.length());
        String bWhole = b.integerPart + b.fractionalPart + "0".repeat(maxFracLen - b.fractionalPart.length());
        AInteger aInt = new AInteger(aWhole);
        AInteger bInt = new AInteger(bWhole);
        aInt.isNegative = isNegative;
        bInt.isNegative = b.isNegative;
        // Add as integers
        AInteger sum = aInt.add(bInt);
        // Convert back to float
        String sumStr = sum.toString();
        if (sumStr.equals("0")) {
            return new AFloat("0.0");
        }
        String intPart, fracPart;
        if (sumStr.length() <= maxFracLen) {
            intPart = "0";
            fracPart = "0".repeat(maxFracLen - sumStr.length()) + sumStr;
        } else {
            intPart = sumStr.substring(0, sumStr.length() - maxFracLen);
            fracPart = sumStr.substring(sumStr.length() - maxFracLen);
        }
        fracPart = removeTrailingZeros(fracPart);
        // Truncate to max precision
        if (fracPart.length() > MAX_PRECISION) {
            fracPart = fracPart.substring(0, MAX_PRECISION);
        }
        AFloat result = new AFloat(intPart + "." + fracPart);
        result.isNegative = sum.isNegative;
        return result;
    }

    // Subtract two floats
    public AFloat subtract(AFloat b) {
        AFloat negB = new AFloat(b);
        negB.isNegative = !b.isNegative;
        return add(negB);
    }

    // Multiply two floats
    public AFloat multiply(AFloat b) {
        // Convert to integers by removing decimals
        String aWhole = integerPart + fractionalPart;
        String bWhole = b.integerPart + b.fractionalPart;
        AInteger aInt = new AInteger(aWhole);
        AInteger bInt = new AInteger(bWhole);
        aInt.isNegative = isNegative;
        bInt.isNegative = b.isNegative;
        AInteger product = aInt.multiply(bInt);
        // Adjust for decimal places
        int totalFracLen = fractionalPart.length() + b.fractionalPart.length();
        String prodStr = product.toString();
        if (prodStr.equals("0")) {
            return new AFloat("0.0");
        }
        String intPart, fracPart;
        if (prodStr.length() <= totalFracLen) {
            intPart = "0";
            fracPart = "0".repeat(totalFracLen - prodStr.length()) + prodStr;
        } else {
            intPart = prodStr.substring(0, prodStr.length() - totalFracLen);
            fracPart = prodStr.substring(prodStr.length() - totalFracLen);
        }
        fracPart = removeTrailingZeros(fracPart);
        // Truncate to max precision
        if (fracPart.length() > MAX_PRECISION) {
            fracPart = fracPart.substring(0, MAX_PRECISION);
        }
        AFloat result = new AFloat(intPart + "." + fracPart);
        result.isNegative = product.isNegative;
        return result;
    }

    // Divide two floats
    public AFloat divide(AFloat b) {
        if (b.integerPart.equals("0") && b.fractionalPart.equals("0")) {
            System.out.println("Error: Cannot divide by zero!");
            return new AFloat();
        }
        // Convert to integers
        String aWhole = integerPart + fractionalPart;
        String bWhole = b.integerPart + b.fractionalPart;
        AInteger aInt = new AInteger(aWhole);
        AInteger bInt = new AInteger(bWhole);
        aInt.isNegative = isNegative;
        bInt.isNegative = b.isNegative;
        // To get more precision, multiply numerator by 10^MAX_PRECISION
        StringBuilder aExtended = new StringBuilder(aWhole);
        aExtended.append("0".repeat(MAX_PRECISION));
        AInteger aBig = new AInteger(aExtended.toString());
        aBig.isNegative = isNegative;
        AInteger quotient = aBig.divide(bInt);
        String quotStr = quotient.toString();
        // Adjust decimal point
        int totalFracLen = fractionalPart.length() - b.fractionalPart.length() + MAX_PRECISION;
        String intPart, fracPart;
        if (totalFracLen <= 0) {
            intPart = quotStr + "0".repeat(-totalFracLen);
            fracPart = "0";
        } else if (quotStr.length() <= totalFracLen) {
            intPart = "0";
            fracPart = "0".repeat(totalFracLen - quotStr.length()) + quotStr;
        } else {
            intPart = quotStr.substring(0, quotStr.length() - totalFracLen);
            fracPart = quotStr.substring(quotStr.length() - totalFracLen);
        }
        fracPart = removeTrailingZeros(fracPart);
        // Truncate to max precision
        if (fracPart.length() > MAX_PRECISION) {
            fracPart = fracPart.substring(0, MAX_PRECISION);
        }
        AFloat result = new AFloat(intPart + "." + fracPart);
        result.isNegative = quotient.isNegative;
        return result;
    }
}