package exercise1;

import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    private static String insuranceType = null;
    private static double insuranceAmount = -1;
    private static final ArrayList<Insurance> insurances = new ArrayList<>();

    public static void main(String[] args) {
        displayInsurancePanel();
    }

    private static void displayInsurancePanel() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("******* Welcome to the Insurance Portal!! *******");

        // Insurance Type Query
        System.out.println("Pick an insurance type:");
        System.out.println("1. Life Insurance");
        System.out.println("2. Health Insurance");
        System.out.print("Select an option (1/2): ");

        int typeSelection = scanner.nextInt();
        if (typeSelection == 1) {
            insuranceType = "Life Insurance";
        } else if (typeSelection == 2) {
            insuranceType = "Health Insurance";
        } else {
            System.out.println("Invalid selection. Defaulting to Life Insurance.");
            insuranceType = "Life Insurance";
        }

        // Insurance Amount Query
        System.out.print("Enter Insurance Cost: ");
        insuranceAmount = scanner.nextDouble();

        // Validate input and add insurance
        if (validateInput(insuranceType, String.valueOf(insuranceAmount))) {
            insurances.add(createInsuranceObject(insuranceType, insuranceAmount));
            askUserForRetry(scanner);
        } else {
            System.out.println("Enter proper details!!");
        }

        scanner.close();
    }

    // Recursion for validation and making the code ask for more entry of Insurances
    private static void askUserForRetry(Scanner scanner) {
        System.out.print("Do you want to enter another insurance entry? (Yes/No): ");
        String enterAnotherEntry = scanner.next();

        if (validateRetry(enterAnotherEntry)) {
            if (shouldRetry(enterAnotherEntry)) {
                displayInsurancePanel();
            } else {
                displayInsurances();
            }
        } else {
            System.out.println("Enter proper input!!");
            askUserForRetry(scanner);
        }
    }

    private static void displayInsurances() {
        System.out.println("Entered Insurances:");
        for (Insurance insurance : insurances) {
            insurance.display();
        }
    }

    private static boolean validateRetry(String entryString) {
        return entryString.equalsIgnoreCase("Y") || entryString.equalsIgnoreCase("YES") ||
                entryString.equalsIgnoreCase("N") || entryString.equalsIgnoreCase("NO");
    }

    private static boolean shouldRetry(String entryString) {
        return entryString.equalsIgnoreCase("Y") || entryString.equalsIgnoreCase("YES");
    }

    private static Insurance createInsuranceObject(String insuranceType, double insuranceAmount) {
        Insurance insurance;

        switch (insuranceType) {
            case "Health Insurance":
                insurance = new Health();
                break;
            case "Life Insurance":
            default:
                insurance = new Life();
                break;
        }

        insurance.setInsuranceCost(insuranceAmount);
        return insurance;
    }

    private static boolean validateInput(String type, String amount) {
        insuranceType = type;

        try {
            if (amount != null && !amount.isEmpty()) {
                insuranceAmount = Double.parseDouble(amount);
                return true;
            }
        } catch (Exception ignored) {
        }

        insuranceType = null;
        insuranceAmount = -1;
        return false;
    }
}

