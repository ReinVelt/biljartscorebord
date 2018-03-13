package com.mechanicape.biljardscore.model;

public class Turn {
	
	private int id;
	private int game_id;
	private int player_id;
	private int points;
	private long created;

	public Turn() {
	 this.created=System.currentTimeMillis()/1000;
	}

	public void setId(int id)
	{
		this.id=id;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public void setPlayerId(int player_id)
	{
		this.player_id=player_id;
	}
	
	public int getPlayerId()
	{
		return this.player_id;
	}
	
	public void setGameId(int game_id)
	{
		this.game_id=game_id;
	}
	
	public int getGameId()
	{
		return this.game_id;
	}
	
	
	public void setPoints(int addpoints)
	{
		this.points=addpoints;
	}
	
	public int getPoints()
	{
		return this.points;
	}
	
	public void setCreated(int created)
	{
		this.created=created;
	}
	
	public long getCreated()
	{
		return this.created;
	}
	

}
