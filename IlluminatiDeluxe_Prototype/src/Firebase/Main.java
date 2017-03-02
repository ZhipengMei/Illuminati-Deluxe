package Firebase;




// *************Do NOT DELETE THE FOLLOWING CODE************
//public class Main implements Runnable {
//	
//	@Override
//	public void run() {
//	    AccessFirebase.fectchUsers();
//	}
//	
//	public static void main(String[] args) throws InterruptedException {
//		AccessFirebase.firebaseProjecAuth();
//
//		
//	    Thread t = new Thread(new Main(), "TestThread");
//	    t.start();
//	    t.join();
//	    Thread.sleep(10000);
//	    
//	    
//	}
//
//}
//*************Do NOT DELETE THE ABOVE CODE************


public class Main  {
	

	public static void main(String[] args) throws InterruptedException {
		
		AccessFirebase.firebaseConfig();	//establish secure connection between this project and firebase
	    AccessFirebase.userAuth("Darrian","12sddsf345678");
//		AccessFirebase.validateUsername("Adrian", "123");
		

	    
	}

}
