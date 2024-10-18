package exercise3;

import javax.swing.*;

// Driver Class
public class ProcessMortgage {
    public static void main(String[] args) {
        Mortgage[] mortgages = new Mortgage[3];

        // Get interest rate from the user
        float interestRate = getInterestRate();

        // Process mortgages for the array
        for (int i = 0; i < mortgages.length; i++) {
            processMortgage(interestRate, mortgages, i);
        }

        // Display mortgage information
        for (Mortgage mortgage : mortgages) {
            mortgage.getMortgageInfo();
        }
    }

    // Method to process each mortgage
    public static void processMortgage(float interestRate, Mortgage[] mortgages, int index) {
        if (mortgages.length <= index) {
            return;
        }

        String[] mortgageTypes = {"Business", "Personal"};
        Object selectedMortgageType = JOptionPane.showInputDialog(null, "Select Mortgage Type: ", "Mortgage Portal",
                JOptionPane.PLAIN_MESSAGE, null, mortgageTypes, "Business");

        if (selectedMortgageType != null) {
            mortgages[index] = createMortgage(selectedMortgageType.toString(), interestRate);
        } else {
            JOptionPane.showMessageDialog(null, "Mortgage Type selection is required!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to create a mortgage based on the type and user input
    public static Mortgage createMortgage(String mortgageType, float interestRate) {
        JTextField mortgageNumberField = new JTextField();
        JTextField customerNameField = new JTextField();
        JTextField amountField = new JTextField();
        JTextField termField = new JTextField();

        Object[] inputFields = {
                "Mortgage Number: ", mortgageNumberField,
                "Customer Name: ", customerNameField,
                "Amount: ", amountField,
                "Term (in years): ", termField
        };

        boolean isValidInput;
        do {
            int option = JOptionPane.showConfirmDialog(null, inputFields, "Enter Mortgage Details", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                isValidInput = validateMortgageData(mortgageNumberField.getText(), customerNameField.getText(), amountField.getText(), termField.getText());
                if (!isValidInput) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                return null;  // User canceled the input
            }
        } while (!isValidInput);

        return instantiateMortgage(mortgageType, mortgageNumberField.getText(), customerNameField.getText(), amountField.getText(), termField.getText(), interestRate);
    }

    // Helper method to instantiate either a Business or Personal mortgage
    private static Mortgage instantiateMortgage(String mortgageType, String mortgageNumber, String customerName, String amount, String term, float interestRate) {
        int number = Integer.parseInt(mortgageNumber);
        double amountValue = Double.parseDouble(amount);
        int termValue = Integer.parseInt(term);

        if ("Business".equals(mortgageType)) {
            return new BusinessMortgage(number, customerName, amountValue, interestRate, termValue);
        } else {
            return new PersonalMortgage(number, customerName, amountValue, interestRate, termValue);
        }
    }

    // Method to validate mortgage data
    private static boolean validateMortgageData(String mortgageNumber, String customerName, String amount, String term) {
        if (isNonNullAndNotEmpty(customerName)) {
            try {
                int mortgageNum = Integer.parseInt(mortgageNumber);
                double loanAmount = Double.parseDouble(amount);
                int loanTerm = Integer.parseInt(term);
                return mortgageNum > 0 && loanAmount > 0 && loanTerm > 0;
            } catch (NumberFormatException e) {
                return false;  // Catch any parsing errors
            }
        }
        return false;
    }

    // Method to get the interest rate from the user
    public static float getInterestRate() {
        float interestRate = 0f;
        boolean isValid;

        do {
            String inputRate = JOptionPane.showInputDialog(null, "Enter Interest Rate: ", "Mortgage Portal", JOptionPane.PLAIN_MESSAGE);
            isValid = validateFloatInput(inputRate);

            if (isValid) {
                interestRate = Float.parseFloat(inputRate);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid interest rate. Please enter a positive number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } while (!isValid);

        return interestRate;
    }

    // Method to validate the interest rate input
    private static boolean validateFloatInput(String inputRate) {
        if (isNonNullAndNotEmpty(inputRate)) {
            try {
                return Float.parseFloat(inputRate) > 0f;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    // Helper method to check if a string is non-null and not empty
    private static boolean isNonNullAndNotEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }
}
