/* Author: Prayusha Parikh
/* I created the PayingFee.java, with the required classes to calculate latefees 
and showes the output of when the book was due and when it was return, 
and depending of how many days its late its provding total fee. 
 * 
 */
public class PayingFee {
   
    public static void main(String[] args) {  
        int dueDate = 5; 
        int returnDate = 10;
        double dailyFeeRate = 0.5; 

        // Create an instance of FeeCalculation and set values
        FeeCalculation feeCalculation = new FeeCalculation();
        feeCalculation.setDueDate(dueDate);
        feeCalculation.setReturnDate(returnDate);
        feeCalculation.setFlatFee(flatFee);
        feeCalculation.setDailyFeeRate(dailyFeeRate);

        // Calculate the total fee
        double totalFee = feeCalculation.calculateFees(dueDate, returnDate);
        
        // Output the result
        System.out.println("Total fees to pay: $" + totalFee);
    }
}

// FeeCalculation class
class FeeCalculation {
    private int dueDate;
    private int returnDate;
    private double flatFee;
    private double dailyFeeRate;
    private double totalFees;

    // Getters and Setters
    public int getDueDate() {
        return dueDate;
    }

    public void setDueDate(int dueDate) {
        this.dueDate = dueDate;
    }

    public int getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(int returnDate) {
        this.returnDate = returnDate;
    }

    public double getFlatFee() {
        return flatFee;
    }

    public void setFlatFee(double flatFee) {
        this.flatFee = flatFee;
    }

    public double getDailyFeeRate() {
        return dailyFeeRate;
    }

    public void setDailyFeeRate(double dailyFeeRate) {
        this.dailyFeeRate = dailyFeeRate;
    }

    public double getTotalFees() {
        return totalFees;
    }

    public void setTotalFees(double totalFees) {
        this.totalFees = totalFees;
    }

    // Method to calculate fees
    public double calculateFees(int dueDate, int returnDate) {
        if (returnDate <= dueDate) {
            totalFees = 0; // No fee if returned on or before due date
        } else {
            int lateDays = returnDate - dueDate;
            totalFees = flatFee + (lateDays * dailyFeeRate);
        }
        return totalFees;
    }
}
 

