package com.mechanicape.biljardscore.helper;

import  com.mechanicape.biljardscore.model.Group;
import  com.mechanicape.biljardscore.model.Player;
import  com.mechanicape.biljardscore.model.Game;
import  com.mechanicape.biljardscore.model.Turn;

//import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Date;
import java.util.List;
//import java.util.Locale;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
//@TODO - This file needs some refactoring. To many code in one file
//        better split it up...one file per model should be nice
//        but it works for now.
public class DatabaseHelper extends SQLiteOpenHelper {
	 
    // Logcat tag
    private static final String LOG = "DatabaseHelper";
 
    // Database Version
    private static final int DATABASE_VERSION = 12;
 
    // Database Name
    private static final String DATABASE_NAME = "biljardb";
 
    // Table Names
    // Common column names
    private static final String TABLE_PLAYERS = "players";
    private static final String PLAYER_ID = "player_id";
    private static final String PLAYER_NAME = "name";
    private static final String PLAYER_HANDICAP = "handicap";
    private static final String PLAYER_KEY = "key";
    private static final String PLAYER_STATUS = "status";
    private static final String PLAYER_CREATED = "created";
 
    private static final String TABLE_GROUPS = "groups";
    private static final String GROUP_ID ="group_id";
    private static final String GROUP_NAME ="name";
    private static final String GROUP_DESCRIPTION ="description";
    private static final String GROUP_ADMINMAIL ="adminmail";
    
    private static final String TABLE_GROUPPLAYER ="groups_players";
    private static final String GROUPPLAYER_ID ="groupplayer_id";
    private static final String GROUPPLAYER_GROUP_ID ="group_id";
    private static final String GROUPPLAYER_PLAYER_ID ="player_id";
    
    private static final String TABLE_GAMES = "games";
    private static final String GAME_ID = "game_id";
    private static final String GAME_GROUP_ID = "group_id";
    private static final String GAME_PLAYER1_ID = "player1_id";
    private static final String GAME_PLAYER2_ID = "player2_id";
    private static final String GAME_START = "start";
    private static final String GAME_END = "end";
    
    private static final String TABLE_TURNS = "turns";
    private static final String TURN_ID ="turn_id";
    private static final String TURN_GAME_ID = "game_id";
    private static final String TURN_PLAYER_ID = "player_id";
    private static final String TURN_POINTS = "points";
    private static final String TURN_CREATED = "created";
   
 
    // Table Create Statements
    private static final String CREATE_TABLE_PLAYERS = "CREATE TABLE "+ TABLE_PLAYERS + "(" + 
    		PLAYER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE," + 
    		PLAYER_NAME+ " TEXT," + 
    		PLAYER_HANDICAP + " INTEGER,"+
    		PLAYER_KEY+" INTEGER,"  + 
    		PLAYER_STATUS+" INTEGER, "+
    		PLAYER_CREATED + " INTEGER" + ")";
    
    private static final String CREATE_TABLE_GROUPS = "CREATE TABLE "+ TABLE_GROUPS + "(" + 
    		GROUP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE," + 
    		GROUP_NAME+ " TEXT," + 
    		GROUP_DESCRIPTION + " TEXT," +
    		GROUP_ADMINMAIL + "TEXT"+")";
 
    private static final String CREATE_TABLE_GROUPPLAYER = "CREATE TABLE "+ TABLE_GROUPPLAYER + "(" + 
    		GROUPPLAYER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE ," + 
    		GROUPPLAYER_GROUP_ID+ " INTEGER," + 
    		GROUPPLAYER_PLAYER_ID + " INTEGER" +")";
    
    private static final String CREATE_TABLE_GAMES = "CREATE TABLE "+ TABLE_GAMES + "(" + 
    		GAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE," +
    		GAME_GROUP_ID+ " INTEGER," + 
    		GAME_PLAYER1_ID+ " INTEGER," + 
    		GAME_PLAYER2_ID+ " INTEGER," + 
    		GAME_START + " INTEGER,"+
    		GAME_END+" INTEGER"  + ")";
    
    private static final String CREATE_TABLE_TURNS = "CREATE TABLE "+ TABLE_TURNS + "(" + 
    		TURN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE," + 
    		TURN_GAME_ID+ " INTEGER," + 
    		TURN_PLAYER_ID+ " INTEGER," + 
    		TURN_POINTS + " INTEGER,"+
    		TURN_CREATED +" INTEGER"  + ")";
 
    private static final String INSERT_TEST_GROUP="INSERT INTO "+TABLE_GROUPS+" ("+ GROUP_NAME + ") VALUES ('testclub')";
    private static final String INSERT_TEST_PLAYER1="INSERT INTO "+TABLE_PLAYERS+" ("+ PLAYER_ID + ","+ PLAYER_NAME + ","+ PLAYER_KEY +") VALUES (NULL,'Theo Tester',1)";
    private static final String INSERT_TEST_PLAYER2="INSERT INTO "+TABLE_PLAYERS+" ("+ PLAYER_ID + ","+ PLAYER_NAME + ","+ PLAYER_KEY +") VALUES (NULL,'Tinus Tester',2)";
    private static final String INSERT_TEST_PLAYERGROUP1="INSERT INTO "+TABLE_GROUPPLAYER+" ("+ GROUPPLAYER_PLAYER_ID + ","+GROUPPLAYER_GROUP_ID+") VALUES (1,1)";
    private static final String INSERT_TEST_PLAYERGROUP2="INSERT INTO "+TABLE_GROUPPLAYER+" ("+ GROUPPLAYER_PLAYER_ID + ","+GROUPPLAYER_GROUP_ID+") VALUES (2,1)";
    
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
 
        // creating required tables
        db.execSQL(CREATE_TABLE_PLAYERS);
        db.execSQL(CREATE_TABLE_GROUPS);
        db.execSQL(CREATE_TABLE_GROUPPLAYER);
        db.execSQL(CREATE_TABLE_GAMES);
        db.execSQL(CREATE_TABLE_TURNS);
        db.execSQL(INSERT_TEST_GROUP);
        db.execSQL(INSERT_TEST_PLAYER1);
        db.execSQL(INSERT_TEST_PLAYER2);
        db.execSQL(INSERT_TEST_PLAYERGROUP1);
        db.execSQL(INSERT_TEST_PLAYERGROUP2);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPS);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPPLAYER);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_TURNS);
    	db.execSQL(" ALTER TABLE "+TABLE_PLAYERS+" ADD "+PLAYER_STATUS+" INT NOT NULL DEFAULT 0");
    	
        
       
        // create new tables
        //onCreate(db);
    }
    
    
    
    
    
    
    //model helpers players
    /**
     * Creating a player
     */
    public long createPlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PLAYER_NAME, player.getName());
        values.put(PLAYER_HANDICAP, player.getHandicap());
        values.put(PLAYER_KEY, player.getKey());
        values.put(PLAYER_CREATED, "00"); //system.getDateTime());
        // insert row
        long player_id = db.insert(TABLE_PLAYERS, null, values);
        return player_id;
    }
 
    /**
     * get single player
     */
    public Player getPlayer(long player_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_PLAYERS + " WHERE "
                + PLAYER_ID + " = " + player_id;
        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();
        Player p = new Player();
        p.setId(c.getInt(c.getColumnIndex(PLAYER_ID)));
        p.setName((c.getString(c.getColumnIndex(PLAYER_NAME))));
        p.setHandicap(c.getInt(c.getColumnIndex(PLAYER_HANDICAP)));
        p.setKey(c.getInt(c.getColumnIndex(PLAYER_KEY)));
        p.setCreated(c.getInt(c.getColumnIndex(PLAYER_CREATED)));
        c.close();
        return p;
    } 
 
    /**
     * getting all players (why?)
     * */
    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<Player>();
        String selectQuery = "SELECT  * FROM " + TABLE_PLAYERS;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Player p = new Player();
                p.setId(c.getInt(c.getColumnIndex(PLAYER_ID)));
                p.setName((c.getString(c.getColumnIndex(PLAYER_NAME))));
                p.setHandicap(c.getInt(c.getColumnIndex(PLAYER_HANDICAP)));
                p.setKey(c.getInt(c.getColumnIndex(PLAYER_KEY)));
                p.setCreated(c.getInt(c.getColumnIndex(PLAYER_CREATED)));
                // adding to todo list
                players.add(p);
            } while (c.moveToNext());
        }
        c.close();
        return players;
    }
    
    /**
     * getting all players belonging to group
     * */
    public List<Player> getPlayersByGroupId(long group_id) {
        List<Player> players = new ArrayList<Player>();
        String selectQuery = 
        	"SELECT  * FROM " + 
            TABLE_PLAYERS +" as P,"+TABLE_GROUPPLAYER +" as G "+
            " WHERE "+ 
               "G."+GROUPPLAYER_GROUP_ID + "="+GROUP_ID+
            " AND "+
               "P."+PLAYER_ID + "=G."+GROUPPLAYER_PLAYER_ID;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Player p = new Player();
                p.setId(c.getInt(c.getColumnIndex(PLAYER_ID)));
                p.setName((c.getString(c.getColumnIndex(PLAYER_NAME))));
                p.setHandicap(c.getInt(c.getColumnIndex(PLAYER_HANDICAP)));
                p.setKey(c.getInt(c.getColumnIndex(PLAYER_KEY)));
                p.setCreated(c.getInt(c.getColumnIndex(PLAYER_CREATED)));
                // adding to todo list
                players.add(p.getId(),p);
            } while (c.moveToNext());
        }
        c.close();
        return players;
    }
    
    /**
     * getting all players belonging to group
     * */
    public List<Player> getPlayersByGameId(long game_id) {
        List<Player> players = new ArrayList<Player>();
        String selectQuery = 
        	"SELECT  * FROM " + 
            TABLE_PLAYERS +" as P,"+TABLE_GAMES +" as G "+
            " WHERE "+ 
               "((P."+PLAYER_ID + "=G."+GAME_PLAYER1_ID+")"+
            " OR "+
               "(P."+PLAYER_ID + "=G."+GAME_PLAYER2_ID+"))"+
            " AND "+
               "G."+GAME_ID + "="+game_id;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
            	Player p = new Player();
            	//int playerId=c.getInt(c.getColumnIndex(PLAYER_ID));
                p.setId(c.getInt(c.getColumnIndex(PLAYER_ID)));
                p.setName((c.getString(c.getColumnIndex(PLAYER_NAME))));
                p.setHandicap(c.getInt(c.getColumnIndex(PLAYER_HANDICAP)));
                p.setKey(c.getInt(c.getColumnIndex(PLAYER_KEY)));
                p.setCreated(c.getInt(c.getColumnIndex(PLAYER_CREATED)));
                // adding to todo list
                players.add(p);
            } while (c.moveToNext());
        }
        c.close();
        return players;
    }
    
    
    /**
     * getting all players belonging to group
     * */
    public List<String> getPlayerListByGroupId(long group_id) {
        List<String> players = new ArrayList<String>();
        players.add("player");
        String selectQuery = 
        	"SELECT  * FROM " + 
            TABLE_PLAYERS +" as P,"+TABLE_GROUPPLAYER +" as G "+
            " WHERE ("+ 
            "P."+PLAYER_ID+ "= G."+GROUPPLAYER_PLAYER_ID +
               		
            " AND "+
               "G."+GROUPPLAYER_GROUP_ID + "="+group_id+
            ") AND "+PLAYER_STATUS+"=0";
        
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                String s = new String();
                s=c.getString(c.getColumnIndex(PLAYER_KEY)+0);
                s+="\t";
                s+=c.getString(c.getColumnIndex(PLAYER_NAME));
                s+="\t";
                s+=c.getString(c.getColumnIndex(PLAYER_ID));
                
                // adding to todo list
                players.add(s);
            } while (c.moveToNext());
        }
        c.close();
        return players;
    }
    
    public List<String> getPlayerList() {
        List<String> players = new ArrayList<String>();
        players.add("player");
        String selectQuery = 
        	"SELECT  * FROM " + TABLE_PLAYERS ;
            
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                String s = new String();
                s=c.getString(c.getColumnIndex(PLAYER_ID)+0);
                s+=" ";
                s+=c.getString(c.getColumnIndex(PLAYER_NAME));
                
                // adding to todo list
                players.add(c.getInt(c.getColumnIndex(PLAYER_ID)),s);
            } while (c.moveToNext());
        }
        c.close();
        return players;
    }
    
    public int getPlayerCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PLAYERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }
    
    public int updatePlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PLAYER_NAME, player.getName()); 
        values.put(PLAYER_HANDICAP, player.getHandicap());
        values.put(PLAYER_KEY, player.getKey());
        values.put(PLAYER_STATUS, player.getStatus());
        // updating row
        int rownum = db.update(TABLE_PLAYERS, values, PLAYER_ID + "=?",
                new String[] { String.valueOf(player.getId()) });
        //@TODO: refactor
        return player.getId();
    }
    
    public void deletePlayer(long player_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        db.delete(TABLE_PLAYERS, PLAYER_ID + " = ?",
                new String[] { String.valueOf(player_id) });
    }
    
    public long addPlayerGroup(int playerId, int groupId)
    {  SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GROUPPLAYER_GROUP_ID, groupId);
        values.put(GROUPPLAYER_PLAYER_ID, playerId);
        //values.put(GROUP_ID,)
    	long playergroupId=db.insert(TABLE_GROUPPLAYER, null, values); //db.update(TABLE_PLAYERGROUPS, values, PLAYER_ID + " = ?",
        return playergroupId;         
    }
 
    
    
    
    
    ////group shit
    public long createGroup(Group group) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GROUP_NAME, group.getName());
        values.put(GROUP_DESCRIPTION, group.getDescription());
        // insert row
        long group_id = db.insert(TABLE_GROUPS, null, values);
        return group_id;
    }
 
    /**
     * get single group
     */
    public Group getGroup(long group_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_GROUPS + 
        		" WHERE "
                + GROUP_ID + " = " + group_id;
        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();
        Group g = new Group();
        g.setId(c.getInt(c.getColumnIndex(GROUP_ID)));
        g.setName((c.getString(c.getColumnIndex(GROUP_NAME))));
        g.setDescription(c.getString(c.getColumnIndex(GROUP_DESCRIPTION)));
        g.setAdminEmail(c.getString(c.getColumnIndex(GROUP_ADMINMAIL)));
        c.close();
        return g;
    } 
 
    /**
     * getting all groups
     * */
    public List<Group> getAllGroups() {
        List<Group> groups = new ArrayList<Group>();
        String selectQuery = "SELECT  * FROM " + TABLE_GROUPS;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Group g = new Group();
                g.setId(c.getInt(c.getColumnIndex(GROUP_ID)));
                g.setName((c.getString(c.getColumnIndex(GROUP_NAME))));
                g.setDescription(c.getString(c.getColumnIndex(GROUP_DESCRIPTION)));
                g.setAdminEmail(c.getString(c.getColumnIndex(GROUP_ADMINMAIL)));
                // adding to todo list
                groups.add(g);
            } while (c.moveToNext());
        }
        c.close();
        return groups;
    }
    
    /**
     * getting all groups as stringlist (to use with anroid widgets)
     * */
    public List<String> getGroupList() {
        List<String> groups = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + TABLE_GROUPS;
        Log.e(LOG, selectQuery);
        groups.add(0,"groups");
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                String s = new String();
                s=c.getString(c.getColumnIndex(GROUP_ID));
                s+=" ";
                s+=c.getString(c.getColumnIndex(GROUP_NAME));
                //g.setDescription(c.getString(c.getColumnIndex(GROUP_DESCRIPTION)));
                //g.setAdminEmail(c.getString(c.getColumnIndex(GROUP_ADMINMAIL)));
                // adding to todo list
                groups.add(c.getInt(c.getColumnIndex(GROUP_ID)),s);
            } while (c.moveToNext());
        }
        c.close();
        return groups;
    }
    
    public int getGroupCount() {
        String countQuery = "SELECT * FROM " + TABLE_GROUPS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }
    
    public int updateGroup(Group group) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GROUP_NAME, group.getName());
        values.put(GROUP_DESCRIPTION, group.getDescription());
        values.put(GROUP_ADMINMAIL, group.getAdminEmail());
        // updating row
        return db.update(TABLE_GROUPS, values, GROUP_ID + " = ?",
                new String[] { String.valueOf(group.getId()) });
    }
    
    public void deleteGroup(long group_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GROUPS, GROUP_ID + " = ?",
                new String[] { String.valueOf(group_id) });
    }
 
    
    
    ////game shit
    public long createGame(Game game) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GAME_START, game.getStart());
        values.put(GAME_PLAYER1_ID, game.getPlayerId(0));
        values.put(GAME_PLAYER2_ID, game.getPlayerId(1));
        // insert row
        long game_id = db.insert(TABLE_GAMES, null, values);
        return game_id;
    }
 
 
    
    /**
     * * Get single game with full object hierachy
     */
    public Game loadGame(long game_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_GAMES + 
        		" WHERE "
                + GAME_ID + " = " + game_id;
        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();
        Game g = new Game();
        g.setId(c.getInt(c.getColumnIndex(GAME_ID)));
        g.setGroupId(c.getInt(c.getColumnIndex(GAME_GROUP_ID)));
        //g.setPlayerId(1,(c.getInt(c.getColumnIndex(PLAYER2_ID))));
        g.setStart(c.getInt(c.getColumnIndex(GAME_START)));
        g.setEnd(c.getInt(c.getColumnIndex(GAME_END)));
        //@BUG...players are not added in the right order..should be fixed now
        long player1Id=c.getInt(c.getColumnIndex(GAME_PLAYER1_ID));
        long player2Id=c.getInt(c.getColumnIndex(GAME_PLAYER2_ID));
        g.addPlayer(this.getPlayer(player1Id));
        g.addPlayer(this.getPlayer(player2Id));
        //g.setPlayers(this.getPlayersByGameId(game_id));
        g.setTurns(this.getTurnsByGameId( game_id));
        c.close();
        return g;
    } 
 
    /**
     * getting all games
     * */
    public List<Game> getAllGames() {
        List<Game> games = new ArrayList<Game>();
        String selectQuery = "SELECT  * FROM " + TABLE_GAMES;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Game g = new Game();
                g.setId(c.getInt(c.getColumnIndex(GAME_ID)));
                //g.setPlayerId(0,(c.getInt(c.getColumnIndex(PLAYER1_ID))));
                //g.setPlayerId(1,(c.getInt(c.getColumnIndex(PLAYER2_ID))));
                g.setStart(c.getInt(c.getColumnIndex(GAME_START)));
                g.setEnd(c.getInt(c.getColumnIndex(GAME_END)));
                // adding to todo list
                games.add(g);
            } while (c.moveToNext());
        }
        c.close();
        return games;
    }
    
    public int getLastGameId()
    {
    	int game_id=0;
    	SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_GAMES + 
        		" ORDER BY  "+GAME_ID+ " DESC";
    
        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();
        Game G = new Game();
        game_id=c.getInt(c.getColumnIndex(GAME_ID));
    	return game_id;
    }
    
    public List<String> getOpenGamesList() {
    	List<String> games = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM " + TABLE_GAMES + " WHERE "+GAME_END+" IS NULL";
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
            	String s = new String();
                s="00"+c.getString(c.getColumnIndex(GAME_ID));
                s+=" ";
                s+=c.getInt(c.getColumnIndex(GAME_START));
                //g.setDescription(c.getString(c.getColumnIndex(GROUP_DESCRIPTION)));
                //g.setAdminEmail(c.getString(c.getColumnIndex(GROUP_ADMINMAIL)));
                // adding to todo list
                games.add(s);
            } while (c.moveToNext());
        }
        c.close();
        return games;
    }
    
    
    
    public int getGameCount() {
        String countQuery = "SELECT * FROM " + TABLE_GAMES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }
    
    public int updateGame(Game game) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GAME_PLAYER1_ID, game.getPlayerId(0));
        values.put(GAME_PLAYER2_ID, game.getPlayerId(1));
        values.put(GAME_START, game.getStart());
        values.put(GAME_END, game.getEnd());
        // updating row
        return db.update(TABLE_GAMES, values, GAME_ID + " = ?",
                new String[] { String.valueOf(game.getId()) });
    }
    
    public void deleteGame(long game_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GAMES, GAME_ID + " = ?",
                new String[] { String.valueOf(game_id) });
    }
    
    
    // @todo: THE REST OF THE CODE IS NOT CORRECT. NEEDS UPDATE
    
    ////turn shit
    public long createTurn(Turn turn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TURN_PLAYER_ID, turn.getPlayerId());
        values.put(TURN_GAME_ID, turn.getGameId());
        values.put(TURN_POINTS, turn.getPoints());
        values.put(TURN_CREATED, turn.getCreated());
        // insert row
        long turn_id = db.insert(TABLE_TURNS, null, values);
        return turn_id;
    }
 
    /**
     * get single turn
     */
    public Turn getTurn(long turn_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_TURNS + 
        		" WHERE "
                + TURN_ID + " = " + turn_id;
        Log.e(LOG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();
        Turn t = new Turn();
        t.setId(c.getInt(c.getColumnIndex(TURN_ID)));
        t.setGameId((c.getInt(c.getColumnIndex(TURN_GAME_ID))));
        t.setPlayerId((c.getInt(c.getColumnIndex(TURN_PLAYER_ID))));
        t.setPoints(c.getInt(c.getColumnIndex(TURN_POINTS)));
        t.setCreated(c.getInt(c.getColumnIndex(TURN_CREATED)));
        c.close();
        return t;
    } 
 
    /**
     * getting all turns
     * */
    public List<Turn> getTurnsByGameId(long game_id) {
        List<Turn> turns = new ArrayList<Turn>();
        String selectQuery = "SELECT  * FROM " + TABLE_TURNS+
        		" WHERE "+ TURN_GAME_ID+"="+game_id;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
            	Turn t = new Turn();
                t.setId(c.getInt(c.getColumnIndex(TURN_ID)));
                t.setGameId((c.getInt(c.getColumnIndex(TURN_GAME_ID))));
                t.setPlayerId((c.getInt(c.getColumnIndex(TURN_PLAYER_ID))));
                t.setPoints(c.getInt(c.getColumnIndex(TURN_POINTS)));
                t.setCreated(c.getInt(c.getColumnIndex(TURN_CREATED)));
                // adding to todo list
                turns.add(t);
            } while (c.moveToNext());
        }
        c.close();
        return turns;
    }
    
    public List<Turn> getTurnsByGameId(long game_id, long playerId) {
        List<Turn> turns = new ArrayList<Turn>();
        String selectQuery = "SELECT  * FROM " + TABLE_TURNS+
        		" WHERE "+ TURN_GAME_ID+"="+game_id+
        		" AND "+TURN_PLAYER_ID+"="+playerId;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
            	Turn t = new Turn();
                t.setId(c.getInt(c.getColumnIndex(TURN_ID)));
                t.setGameId((c.getInt(c.getColumnIndex(TURN_GAME_ID))));
                t.setPlayerId((c.getInt(c.getColumnIndex(TURN_PLAYER_ID))));
                t.setPoints(c.getInt(c.getColumnIndex(TURN_POINTS)));
                t.setCreated(c.getInt(c.getColumnIndex(TURN_CREATED)));
                // adding to todo list
                turns.add(t);
            } while (c.moveToNext());
        }
        c.close();
        return turns;
    }
    public Turn getLastPlayerByGameId(long game_id){
    	Turn t = new Turn();
    	String selectQuery = "SELECT  * FROM " + TABLE_TURNS+
        		" WHERE "+ TURN_GAME_ID+"="+game_id+
        		" ORDER BY "+TURN_ID+" DESC LIMIT 1";
    	Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
                t.setId(c.getInt(c.getColumnIndex(TURN_ID)));
                t.setGameId((c.getInt(c.getColumnIndex(TURN_GAME_ID))));
                t.setPlayerId((c.getInt(c.getColumnIndex(TURN_PLAYER_ID))));
                t.setPoints(c.getInt(c.getColumnIndex(TURN_POINTS)));
                t.setCreated(c.getInt(c.getColumnIndex(TURN_CREATED)));
        }
        c.close();
        return t;
    }
    
    
    
    public int getTurnCount() {
        String countQuery = "SELECT * FROM " + TABLE_TURNS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }
    
    public int updateTurn(Turn turn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TURN_GAME_ID, turn.getGameId());
        values.put(TURN_PLAYER_ID, turn.getPlayerId());
        values.put(TURN_POINTS, turn.getPoints());
        values.put(TURN_CREATED, turn.getCreated());
        // updating row
        return db.update(TABLE_GROUPS, values, TURN_ID + " = ?",
                new String[] { String.valueOf(turn.getId()) });
    }
    
    public void undoTurn(long turn_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TURNS, TURN_ID + " = ?",
                new String[] { String.valueOf(turn_id) });
    }
    
    

}




