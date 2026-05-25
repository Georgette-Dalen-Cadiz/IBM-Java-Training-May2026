package mypackage;

public class BankTransferPayment extends Payment implements Verifiable {

	private String accNumber;
	
	BankTransferPayment(double fieldAmount, String accNumber) {
		super(fieldAmount);
		this.accNumber = accNumber;
	}
	
	@Override
    public boolean verifyPayment() {
    	System.out.print("Processing bank transfer...");
    	
    	if(accNumber.length() == 10)
        {
        	return true;
        } else {
        	return false;
        }
       
    }

	 // Getter 
    String getaccNumber()
    {
    	return accNumber;
    }
}
