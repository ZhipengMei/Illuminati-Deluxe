package Firebase;

import com.google.firebase.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.*;
import com.google.firebase.database.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.concurrent.Semaphore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.TreeMap;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class AccessFirebase{
	
	//url to firebase database acount
	public static final String BASE_URL = "https://illuminatideluxe-95801.firebaseio.com/";
	
	//**Firebase-Project-Auth, make connnect between project and firebase. Have to run this method at least once
	public static void firebaseProjecAuth() {
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
			System.out.println("Firebase Project Auth Complete");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}//end firebaseProjecAuth()
	

	//*****************************************************************************************************

	
	// **** create instance user to firebase-databse
	public static void createUser(String name, String password) {
		Semaphore semaphore = new Semaphore(0);
		
		TreeMap<String,String> profileMap = new TreeMap<String,String>();  
		profileMap.put("name", name);
		profileMap.put("password", password);
		// date sign up
		
		
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
		            semaphore.release();
		        } else {
		            System.out.println("Data saved successfully.");
		            semaphore.release();
		        }
		    }
		});
        try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//end insertFirebase()

	
	
	//*****************************************************************************************************
	
	
	// **** retrive all instance users from firebase-databse
	public static void fectchUsers() {
		Semaphore semaphore = new Semaphore(0);
        User user = null;	//create an empty user for later parse json data
        ObjectMapper mapper = new ObjectMapper(); //for parsing json file



		// Get a reference to the database
		final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
		final DatabaseReference ref = rootRef.child("profile");

		// Attach a listener to read the data at our posts reference
		ref.addValueEventListener(new ValueEventListener() {
		    @Override
		    public void onDataChange(DataSnapshot dataSnapshot) {

//		    	User user = dataSnapshot.getValue();
		    	System.out.println(dataSnapshot.getValue());

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}// end fectchUsers()
	

	



}//end class











