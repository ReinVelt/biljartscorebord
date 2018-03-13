/**
 * 
 */
package com.mechanicape.biljardscore.model;

/**
 * @author rein
 *
 */
public class Player {

	/**
	 * 
	 */
	
	private String name;
	private int id;
	private int handicap;
	private int key;
	private int status;
	private int created;
	
	//public Player() {
	//	// TODO Auto-generated constructor stub
	//}
	
	
	
	//setters
	
	public void setId(int playerId)
	{
		this.id=playerId;
	}
	
	public void setName(String playerName)
	{
		this.name=playerName;
	}
	
	public void setHandicap(int playerHandicap)
	{
		this.handicap=playerHandicap;
	}
	
	public void setKey(int playerKey)
	{
		this.key=playerKey;
	}
	
	public void setStatus(int playerStatus)
	{
		this.status=playerStatus;
	}
	
	public void setCreated(int created)
	{
		this.created=created;
	}
	
	
	
	
	//getters
	public int getId()
	{ 
		return this.id;
	}
	
	public String getName()
	{ 
		return this.name;
	}
	
	public int getHandicap()
	{ 
		return this.handicap;
	}
	
	public int getKey()
	{ 
		return this.key;
	}
	
	public int getStatus()
	{ 
		return this.status;
	}
	
	public int getCreated()
	{ 
		return this.created;
	}

}
