package Firebase;

import java.util.Date;

public class Message implements Comparable<Message> {

	  private Date timeStamp;
	  private String name;
	  private String message;
//	  private String timeStamp;
	  
	  public Message(){}

	  public String getMessage() {
	    return message;
	  }

	  public void setMessage(String textMessage) {
	    this.message = textMessage;
	  }
	  
	  public String getName() {
	    return name;
	  }

	  public void setName(String username) {
	    this.name = username;
	  }	  
	  
//	  public String getTimeStamp() {
//	    return timeStamp;
//	  }
//
//	  public void setTimeStamp(String timeStamp) {
//	    this.timeStamp = timeStamp;
//	  }	

	  public Date getDateTime() {
	    return timeStamp;
	  }

	  public void setDateTime(Date datetime) {
	    this.timeStamp = datetime;
	  }

	  @Override
	  public int compareTo(Message o) {
//		  return 0;
	    return getDateTime().compareTo(o.getDateTime());
	  }
}