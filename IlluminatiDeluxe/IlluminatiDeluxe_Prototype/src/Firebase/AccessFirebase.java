package Firebase;

import com.google.firebase.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.*;
import com.google.firebase.database.*;

import fxml.loginmenuController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;
import java.util.TreeMap;
import java.util.UUID;

public class AccessFirebase{
	
	// *NOTE* semaphore disable UI elements' thread temporary until firebase thread is done. Not good practice.
	
	static boolean isAvailable;
	static boolean isRegistered;
	
	//url to firebase database acount
	public static final String BASE_URL = "https://illuminatideluxe-95801.firebaseio.com/";
	
	//**Firebase-Project-Auth, make connnect between project and firebase. Have to run this method at least once
	public static void firebaseConfig() {
		
		//create an empty FileInputStream
		FileInputStream serviceAccount = null;
		//try and catch method to read the json file
		try {
			//create file object
			serviceAccount = new FileInputStream("support/serviceAccountKey.json");
			//attempt to connect to firebase account with credential
			FirebaseOptions options = new FirebaseOptions.Builder()
					  .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
					  .setDatabaseUrl(BASE_URL)
					  .build();
			FirebaseApp.initializeApp(options);
			System.out.println("Firebase Project Configuration Complete");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}//end firebaseProjecAuth()
	

	//*****************************************************************************************************

	// **** create instance user to firebase-databse
	public static boolean validateUsername(String name, String password) {	
		
		
		// create a java.util.concurrent.Semaphore with 0 initial permits
		Semaphore semaphore = new Semaphore(0);
		
		// Get a reference to the database
		final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
		final DatabaseReference ref = rootRef.child("profile");

		// Attach a listener to read the data at our posts reference
		//specifically query this user's name
		ref.orderByChild("name").equalTo(name.toLowerCase()).addValueEventListener(new ValueEventListener() {
		    @Override
		    public void onDataChange(DataSnapshot dataSnapshot) {
		    	//System.out.println(name);
		    	//System.out.println(dataSnapshot.getValue());
		    	//if object return is null then username is available to register
		    	if(dataSnapshot.exists()) { 
		    		System.out.println("Username is taken.");
		    		isAvailable = false;
		    		ref.removeEventListener(this);
		    	} else {
			    	System.out.println("Username is available.");
			    	//create a new user account
			    	createUser(name, password);
		    		isAvailable = true;
		    		ref.removeEventListener(this);
		    	}
		        // tell the caller that we're done
		    	semaphore.release();
		    }

		    @Override
		    public void onCancelled(DatabaseError databaseError) {
		        System.out.println("The read failed: " + databaseError.getCode());
		        semaphore.release();
		    }
		});
        try {
        	// wait until the onDataChange callback has released the semaphore
			semaphore.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return isAvailable;
	}//end validateUsername()
	
	//*****************************************************************************************************

	
	// **** create instance user to firebase-databse. Do not use this method, ***use validateUsername instead***
	public static void createUser(String name, String password) {
//		Semaphore semaphore = new Semaphore(0);
		
		//create date
		//DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
		DateFormat accountCreateDate = new SimpleDateFormat("MM/dd/yy");
		Date dateobj = new Date();
		

		name = name.toLowerCase(); //convert name to lowercase
		TreeMap<String,String> profileMap = new TreeMap<String,String>();  
		profileMap.put("name", name);
		profileMap.put("password", password);
		profileMap.put("createdDate", accountCreateDate.format(dateobj));
		profileMap.put("win", "0");
		profileMap.put("imageName", "null");
//		profileMap.put("lose", "0");
//		profileMap.put("left", "0");

		// date sign up maybe
		
		
		//create a unique uuid the user
    	String uuid = UUID.randomUUID().toString();
    	//System.out.println("uuid = " + uuid);
    	
    	//create database reference for the user
		final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
		final DatabaseReference ref = rootRef.child("profile").child(uuid);
		
		ref.setValue(profileMap, new DatabaseReference.CompletionListener() {
		    @Override
		    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
		        if (databaseError != null) {
		            System.out.println("Data could not be saved " + databaseError.getMessage());
//		            semaphore.release();
		        } else {
		            System.out.println("User account created successfully.");
//		            semaphore.release();
		        }
		    }
		});
//        try {
//			semaphore.acquire();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}//end createUser()

	
	
	//*****************************************************************************************************
	
	
	// **** Check user's data existence
	public static boolean userAuth(String name, String password) {
		// create a java.util.concurrent.Semaphore with 0 initial permits
		Semaphore semaphore = new Semaphore(0);

		// Get a reference to the database
		final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
		final DatabaseReference ref = rootRef.child("profile");

		// Attach a listener to read the data at our posts reference
		//specifically query this user's name
		ref.orderByChild("name").equalTo(name.toLowerCase()).addValueEventListener(new ValueEventListener() {

		    @Override
		    public void onDataChange(DataSnapshot dataSnapshot) {
		    	
		    	System.out.println(dataSnapshot);
		    	
		    	//iterate each node to get the children value
		    	for (DataSnapshot messageSnapshot:dataSnapshot.getChildren()){
		    		//parse DataSnapshot to String
		    		String dataPassword = (String)messageSnapshot.child("password").getValue();
		    		//validate password 
		    		if (dataPassword.equals(password)){
		    			System.out.println("User found.");
		    			isRegistered = true;
		    			ref.removeEventListener(this);
		    		} else {
		    			System.out.println("User Not Found");
		    			isRegistered = false;
		    			ref.removeEventListener(this);
		    		}
		    	}
		        // tell the caller that we're done
		    	semaphore.release();
		    }

		    @Override
		    public void onCancelled(DatabaseError databaseError) {
		        System.out.println("The read failed: " + databaseError.getCode());
		        semaphore.release();
		    }
		});
        try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        return isRegistered;
	}// end fectchUsers()
	

	
}//end class











