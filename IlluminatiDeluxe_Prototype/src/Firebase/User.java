package Firebase;

// *Note* important class for user data persistent with Singleton design pattern
public class User {
	
	private String uid;
	private String name;
	private String password;
	private String createdDate;
	private String win;
	private String imageName;
	private String currentChannel;
	
	private static User instance = null;
	
	public User(){}

	//important singleton function
	public static User getInstance(){
		if(instance == null){
			instance = new User();
		}
		return instance;
	}
	
	public void setUID(String uid){
		this.uid = uid;
	}
	
	public String getUID(){
		return uid;
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
	
	public String getCreatedDate(){
		return createdDate;
	}
	
	public void setCreatedDate(String date){
		this.createdDate = date;
	}
	
	public String getWin(){
		return win;
	}
	
	public void setWin(String winCount){
		this.win = winCount;
	}
	
	public String getimageName(){
		return imageName;
	}
	
	public void setimageName(String imageurl){
		this.imageName = imageurl;
	}
	
	public String getCurrentChannel(){
		return currentChannel;
	}
	
	public void setCurrentChannel(String channelName){
		this.currentChannel = channelName;
	}

}
