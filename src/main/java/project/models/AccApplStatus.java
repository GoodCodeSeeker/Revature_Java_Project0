package Pj.models;

public class AccApplStatus {
	
	//Use a singleton pattern for making a account apply status class
	
	private AccApplStatus() {
		
	}
	
	private static AccApplStatus instance = null;
	private static boolean isOpen;
	
	public static AccApplStatus getInstance() {
		if (instance == null) {
			instance = new AccApplStatus();
			}
		return instance;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public void setOpen(boolean isOpen) {
		AccApplStatus.isOpen = isOpen;
	}
	
}
