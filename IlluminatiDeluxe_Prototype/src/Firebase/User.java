package Firebase;

public class User {
	
	private String name;
	private String password;
	private String uuid;
	
	public String getUUID(){
		return uuid;
	}
	
	public void setUUID(String uuid){
		this.uuid = uuid;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public User(){}

}
