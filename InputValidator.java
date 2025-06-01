import java.util.regex.Pattern;

public class InputValidator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@(gmail\\.com|yahoo\\.com|outlook\\.com|.*\\.edu|.*\\.org)$");

    public static String validateEmail(String email) throws Exception {
        if (email == null || email.trim().isEmpty()) {
            throw new Exception("Email cannot be empty");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new Exception("Invalid email format. Must be @gmail.com or organizational email");
        }
        return email.trim();
    }

    public static String validatePhoneNumber(String phone, String countryCode) throws Exception {
        if (phone == null || phone.trim().isEmpty()) {
            throw new Exception("Phone number cannot be empty");
        }

        switch (countryCode) {
            case "BD":
                if (!phone.matches("^01[3-9]\\d{8}$")) {
                    throw new Exception("Bangladeshi numbers must be 11 digits starting with 01");
                }
                break;
            case "US":
            case "CA":
                if (!phone.matches("^\\d{10}$")) {
                    throw new Exception("US/Canadian numbers must be 10 digits");
                }
                break;
            case "DE":
                if (!phone.matches("^\\d{10,12}$")) {
                    throw new Exception("German numbers must be 10-12 digits");
                }
                break;
            case "UK":
                if (!phone.matches("^\\d{10,11}$")) {
                    throw new Exception("UK numbers must be 10-11 digits");
                }
                break;
            default:
                throw new Exception("Unsupported country code");
        }
        return phone.trim();
    }

    public static String validateStringInput(String input, String fieldName) throws Exception {
        if (input == null || input.trim().isEmpty()) {
            throw new Exception(fieldName + " cannot be empty");
        }
        return input.trim();
    }

    public static int validateIntegerInput(String input, String fieldName) throws Exception {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new Exception(fieldName + " must be a valid number");
        }
    }

    public static int validateMenuChoice(String input, int min, int max) throws Exception {
        int choice = validateIntegerInput(input, "Menu choice");
        if (choice < min || choice > max) {
            throw new Exception("Please enter a number between " + min + " and " + max);
        }
        return choice;
    }
}