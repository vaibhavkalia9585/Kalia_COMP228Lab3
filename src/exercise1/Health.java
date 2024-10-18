package exercise1;

public class Health extends Insurance {
    public Health() {
        super("Health");
    }

    @Override
    public void setInsuranceCost(double monthlyCost) {
        this.monthlyCost = monthlyCost;
    }

    @Override
    public void display() {
        // Displaying information in the terminal
        System.out.println("Insurance Type: " + getInsuranceType());
        System.out.println("Monthly Cost: $" + getMonthlyCost());
    }
}
