package com.mechanicape.biljardscore;
import java.util.List;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.mechanicape.biljardscore.graphview.GraphViewData;
import com.mechanicape.biljardscore.helper.DatabaseHelper;
import com.mechanicape.biljardscore.model.Game;
import com.mechanicape.biljardscore.model.Turn;

import android.app.Activity; 
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class GameStatsActivity extends Activity{

	Spinner spinnerGame;
	// Database Helper
    DatabaseHelper db;
    int gameId;
    
	protected void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_gamestats);
		// get Instance  of Database Adapter 
	    db = new DatabaseHelper(getApplicationContext());
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    gameId  = extras.getInt("gameId");
		}
		else
		{
			gameId=  db.getLastGameId();
		}
		
		
		this.showGameTimeline(gameId);
		db.close();
		
	}
	
	
	@Override 
	protected void onDestroy() { 
		// TODO Auto-generated method stub 
        db.close(); //IS THIS REQUIRED? OR DOUBLE?
		super.onDestroy();
		
		
	} 
	/**
	 * @param args
	 */
	
	protected void showGameTimeline(long game_id)
	{   
		
		Game game=db.loadGame(game_id);
		
		List <Turn>turns=game.getTurns();
		GraphViewData[] player1Data=new GraphViewData[game.countTurnsByPlayerId(game.getPlayer(0).getId())];
		GraphViewData[] player2Data=new GraphViewData[game.countTurnsByPlayerId(game.getPlayer(1).getId())] ;
		Turn thisturn;
		int p1c=0;
		int p2c=0;
		int p1t=0;
		int p2t=0;
		for (int i=0;i<turns.size();i++)
		{
		    thisturn=turns.get(i);
		    if (thisturn.getPlayerId()==game.getPlayer(0).getId())
		    {
		    	p1t=p1t+thisturn.getPoints();
		    	player1Data[p1c]=new GraphViewData(i,p1t);
		    	p1c++;
		    }
		    else
		    {
		    	p2t=p2t+thisturn.getPoints();
		    	player2Data[p2c]=new GraphViewData(i,p2t);
		    	p2c++;
				   
		    }
		}
		
		//GraphViewSeries player1Series = new GraphViewSeries(game.getPlayers().get(0).getName(), new GraphViewSeriesStyle(Color.BLUE, 3), player1Data);
		//GraphViewSeries player2Series = new GraphViewSeries(game.getPlayers().get(1).getName(), new GraphViewSeriesStyle(Color.RED, 3), player2Data);
		GraphViewSeries player1Series = new GraphViewSeries(game.getPlayer(0).getName(),new GraphViewSeriesStyle(Color.RED,3),player1Data);
		GraphViewSeries player2Series = new GraphViewSeries(game.getPlayer(1).getName(),new GraphViewSeriesStyle(Color.BLUE,3),player2Data);
		
		GraphView graphView = new LineGraphView(
			    this // context
			    , "Game graph" // heading
			);
		
			graphView.addSeries(player1Series); // 
			graphView.addSeries(player2Series); // data
			
			//graphView.setViewPort(2, 10);
			graphView.setScalable(true);
			// optional - legend
			graphView.setShowLegend(true);
			// set legend
			graphView.setShowLegend(true);
			graphView.setLegendAlign(LegendAlign.BOTTOM);
			graphView.setLegendWidth(200);
			 
			LinearLayout layout = (LinearLayout) findViewById(R.id.statsSubLayout);
			layout.addView(graphView);
	}

	protected void end()
	{
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_back, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case  R.id.btnback:
				this.end();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
}
