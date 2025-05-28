class InputValidator {
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