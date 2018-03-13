/**
 * 
 */
package com.mechanicape.biljardscore.model;


import java.util.ArrayList;
import java.util.List;
import com.mechanicape.biljardscore.model.*;

/**
 * @author rein
 *
 */
public class Game {
	private int    id;
	private int    group_id;
	private List<Player> players;
	private long    start;
	private long    end;
	private List<Turn>   turns;
	int status;

	/**
	 * 
	 */
	public Game() {
         players=new ArrayList<Player>();
		 turns=new ArrayList<Turn>();
		 start=System.currentTimeMillis()/1000;
	}
	
	
	
	
	public int getPlayerId(int index)
	{
		return this.players.get(index).getId();
	}
	
	public Player getPlayer(int index)
	{
		return this.players.get(index);
	}
	

   public List<Player> getPlayers()
   {
	   return this.players;
   }
	
	public void addPlayer(Player player)
	{
		this.players.add(player);
		//return this.players.size()-1;
		
	}
	
	
	public int countTurnsByPlayerId(long player_id)
	{
		int count=0;
		for (int i=0;i<this.turns.size();i++)
		{
			if (this.turns.get(i).getPlayerId()==player_id)
			{
				count++;
			}
		}
		return count;
	}
	
	
	
	
	
	public void setPlayers(List<Player> playerslist)
	{ 
		this.players.clear();
		this.players=playerslist;
	}
	
	public void setTurns(List<Turn> turnslist)
	{
	    this.turns.clear();
	    this.turns=turnslist;	
	}
	
	public List<Turn> getTurns()
	{
		
		return this.turns;
	}
	
	//getters and setters for id
     public int getId()
	 {
		return this.id;
	 }
		
	public void setId(int newid)
	{
		this.id=newid;
		
	}
	
	//getters and setters for id
    public int getGroupId()
	 {
		return this.group_id;
	 }
		
	public void setGroupId(int id)
	{
		this.group_id=id;
		
	}
	
	//getters and setters for start
    public long getStart()
	 {
		return this.start;
	 }
		
	public void setStart(long start)
	{
		this.start=start;
		
	}
	
	//getters and setters for end
    public long getEnd()
	 {
		return this.end;
	 }
		
	public void setEnd(long end)
	{
		this.end=end;
		
	}
	
	public int getGameStatus()
	{
		int status_playing=0;
		int status_goal=1;
		int status_post=2;
		int status_exit=3;
		
		int status=status_playing;
		if (this.players.size()>1 && this.turns.size()>1)
		{
			if (getPointsRemaining(0)<1)
			{
			  status=status_goal;
			  if (this.getLastPlayer()==this.players.get(1))
			  {
				  status=status_exit;
			  }
			  else
			  {
				  status=status_post;
			  }
			}
			
			if (getPointsRemaining(1)<1)
			{
			 status=status_exit;
			}
		}
		return status;
	}
	
	public String getWinnerName()
	{
		String name="anonymous";
		if (this.getGameStatus()==3)
		{
			for (int i=0;i<this.players.size();i++)
			{
				if (this.getPointsRemaining(i)<1)
				{
					name=this.players.get(i).getName();
				}
			}
		}
		return name;
	}
	
	public void updateGameStatus()
	{
		this.status=0;
		if (this.turns.size()>5)
		{
		   this.status=this.getGameStatus();
		}
		
		
	}

	
	
	public void addTurn(Turn turn)
	{
		//total points can not exceed handicap
		int playerId=turn.getPlayerId();
		Player player=this.getPlayerById(playerId);
		int playerHandicap=player.getHandicap();
		int oldPoints=this.getPointsByPlayerId(playerId);
		int newPoints=turn.getPoints();
		if (oldPoints+turn.getPoints()>playerHandicap)
		{
			turn.setPoints(newPoints-((oldPoints+newPoints)-playerHandicap));
		}
		this.turns.add(turn);
		this.updateGameStatus();
		
	}
	
	public int getPointsByPlayerId(int playerId)
	{
		int points=0;
		for (int i=0; i<this.turns.size();i++)
		{
			if (this.turns.get(i).getPlayerId()==playerId)
			{
			   points=points+this.turns.get(i).getPoints();
			}
		}
		return points;
	}
	
	public int getPointsRemaining(int playerIndex)
	{
		int playerId=this.getPlayer(playerIndex).getId();
		int points=this.getPointsByPlayerId(playerId);
		int handicap=this.getPlayer(playerIndex).getHandicap();
		//if (handicap<1){handicap=15;}
		int remainingPoints=handicap-points;
		return remainingPoints;
		
		
	}
	
	public Player getPlayerById(int playerId)
	{
		Player player=null;
		if (this.players.get(0).getId()==playerId)
		{
		  	player=this.players.get(0);
		}
		else
		{
			player=this.players.get(1);
		}
		return player;
	}
	
	public Player getLastPlayer()
	{
		int lastPlayerId=this.turns.get(this.turns.size()-1).getPlayerId();
		Player lastPlayer=new Player();
		for (int i=0;i<this.players.size();i++)
		{
			if (this.players.get(i).getId()==lastPlayerId)
			{
				lastPlayer=this.players.get(i);
			}
		}
		
		return lastPlayer;
		
	}
	public List<Turn> getTurnsByPlayerId(int playerId)
	{
		List <Turn> turns=new ArrayList<Turn>();
		for (int i=0;i<this.turns.size();i++)
		{
			if (this.turns.get(i).getPlayerId()==playerId)
			{
			   turns.add(this.turns.get(i));
			}
		}
		return turns;
	}
	
	public int getMostCarambolesByPlayerId(int playerId)
	{
		int mostCarambolesPerTurn=0;
		List<Turn> turns=getTurnsByPlayerId(playerId);
		for (int i=0;i<turns.size();i++)
		{
			if (turns.get(i).getPoints()>mostCarambolesPerTurn)
			{
				mostCarambolesPerTurn=turns.get(i).getPoints();
			}
		}
		return mostCarambolesPerTurn;
	}
	
	public float getAverageCarambolesByPlayerId(int playerId)
	{
		float averageCarambolesPerGame=0;
		int totalCarambolesPerGame=0;
		List<Turn> turns=getTurnsByPlayerId(playerId);
		if (turns.size()>0)
		{
			for (int i=0;i<turns.size();i++)
			{
				totalCarambolesPerGame+=turns.get(i).getPoints();
			}
			averageCarambolesPerGame=(float)totalCarambolesPerGame/(float)turns.size();
		}
		return averageCarambolesPerGame;
	}
	
	public float getGamePointsByPlayerId(int playerId)
	{
		float gamePoints=0;
		int playerHandicap=this.getPlayerById(playerId).getHandicap();
		if (playerHandicap>0)
		{
			float playerPoints=this.getPointsByPlayerId(playerId);
			gamePoints=(float)this.getPointsByPlayerId(playerId)/(float)playerHandicap;
		}
		return gamePoints;
	}
	
	
	public int getGameScore(int playerId)
	{
		int gameScore=0;
		float otherPlayerPoints=0;
		float playerPoints=getGamePointsByPlayerId(playerId);
	    if (playerPoints==1 || playerPoints>1)
	    {
	    	//player has reached goal..
	    	//player get 12 points (winner) 
	    	//or player gets 10 points (remise)
	    	if (this.getPlayer(0).getId()==playerId)
	    	{
	    		otherPlayerPoints=this.getGamePointsByPlayerId(this.getPlayer(1).getId());
	    	}
	    	else
	    	{
	    		otherPlayerPoints=this.getGamePointsByPlayerId(this.getPlayer(0).getId());
	    	}
	    	
	    	//compare playerpoints with points of other player...who is the winner?
	    	if (otherPlayerPoints==1 || otherPlayerPoints>1)
	    	{
	    		//remise
	    		gameScore=10;
	    	}
	    	else
	    	{
	    		//winner
	    		gameScore=12;
	    	}
	    	
	    }
	    else
	    {
	    	//player has not reached goal
	    	//player is no winner
	    	//no remise
	    	//points is first digit after the point/comma
	    	int scoreInt=(int)((playerPoints-(int) playerPoints)*10);
	    	
	    	//String scoreStr[]=String.valueOf(gameScore).split("."); //get string after the comma
	    	gameScore =scoreInt; //Integer.parseInt(scoreStr[1].substring(0,1)); //get first digit
	    }
	    return gameScore;
	
	}

}
