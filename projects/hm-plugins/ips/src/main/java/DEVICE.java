

public final class DEVICE {

	private DEVICE(){}
	
	public String getSystemID(int internalID){
		return "N/A";
	}
	
	public String getSystemID(String name){
		return "N/A";
	}
	
	public static void switchon(String systemID, boolean onOff){
		// get Provider
		// deleagate command to provider
	}
	
	public static void dimm(String systemID, int value){
		if(value<0 || value > 100){
			throw new IllegalArgumentException("Value must be 0 >= value >= 100");
		}
		// get Provider
		// deleagate command to provider
	}
}
