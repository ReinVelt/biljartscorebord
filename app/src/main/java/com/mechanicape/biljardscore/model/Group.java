/**
 * 
 */
package com.mechanicape.biljardscore.model;

/**
 * @author admin
 *
 */
public class Group {

	/**
	 * A group of people playing a game
	 * eg: local, national, event1, event2
	 */
	private int    id;
	private String name;
	private String description;
	private String adminEmail;
	
	public Group() {
	}

	public void setId(int id)
	{
		this.id=id;
	}
	
	public void setName(String name)
	{
		this.name=name;
	}
	
	public void setDescription(String description)
	{
		this.description=description;
	}
	
	public void setAdminEmail(String email)
	{
		this.adminEmail=email;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public String getAdminEmail()
	{
		return this.adminEmail;
	}
	
	
	
	


}
