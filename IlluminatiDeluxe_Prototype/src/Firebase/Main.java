package Firebase;

//public class Main implements Runnable {
//	
//	@Override
//	public void run() {
////		AccessFirebase.insertFirebase();
//	}
//	
//	public static void main(String[] args) throws InterruptedException {
//		AccessFirebase.firebaseProjecAuth();
//		
//	    Thread t = new Thread(new Main(), "TestThread");
//	    t.start();
//	    t.join();
//	    Thread.sleep(10000);
//	    
//	    AccessFirebase.createUser();
//
//	}
//
//}

public class Main  {
	

	public static void main(String[] args) throws InterruptedException {
		
		AccessFirebase.firebaseProjecAuth();	//establish secure connection between this project and firebase
//	    AccessFirebase.createUser("Darrian","12sddsf34567");		//register user with a username
	    AccessFirebase.fectchUsers();
	    
	}

}
