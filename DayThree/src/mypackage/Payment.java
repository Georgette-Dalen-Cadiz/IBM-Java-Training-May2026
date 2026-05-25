package mypackage;

public abstract class Payment {
	// fields
	private double fieldAmount;
	
	// constructor
	Payment(double fieldAmount)
	{
		this.fieldAmount = fieldAmount;
	}
	
	// Method to display the amount
	public void displayFieldAmount()
	{
		System.out.println(fieldAmount);
	}

	// Method to retrieve the amount (demonstrates encapsulation)
	public double getFieldAmount()
	{
		return fieldAmount;
	}
}

