package Extension;

//*Note* important class for user data persistent with Singleton design pattern
public class Player implements Comparable<Player> {
	
	private String dice; //dice image name
	private String name;
	private int diceVal; //value within the dice roll
	private String turn;   
	private String imageName;
	private String announcement;
	
//	private static Player instance = null;
	
	public Player(){}

	//important singleton function
//	public static Player getInstance(){
//		if(instance == null){
//			instance = new Player();
//		}
//		return instance;
//	}
	
	public void setTurn(String turn){
		this.turn = turn;
	}
	
	public String getTurn(){
		return turn;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getDice(){
		return dice;
	}
	
	public void setDice(String dice){
		this.dice = dice;
	}
	
	public int getDiceVal(){
		return diceVal;
	}
	
	public void setDiceVal(int diceVal){
		this.diceVal = diceVal;
	}
	
	public String getAnnouncement(){
		return announcement;
	}
	
	public void setAnnouncement(String announcement){
		this.announcement = announcement;
	}
	
	public String getimageName(){
		return imageName;
	}
	
	public void setimageName(String imageurl){
		this.imageName = imageurl;
	}

	@Override
	public int compareTo(Player other) {
		if(this.diceVal < other.diceVal) return -1;
		else if(other.diceVal < this.diceVal) return 1;
		return 0;
	}
	


}
