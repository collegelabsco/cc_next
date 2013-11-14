package com.devsquare.fts;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;

class Connectivity extends Thread implements Runnable

{
	public static Hashtable<String, Connectivity> mUserCons = new Hashtable<String, Connectivity>();
	public static Hashtable<String, Connectivity> mSimulCons = new Hashtable<String, Connectivity>();
    public static Hashtable<String, GameRunner> gameObj = new Hashtable<String, GameRunner>();
    public static Hashtable<String, String> userMessage = new Hashtable<String , String>();
    public static Hashtable<String, String> simMessage = new Hashtable<String , String>();
    public static Hashtable<String, Integer> gameSteps = new Hashtable<String, Integer>();
    public static Hashtable<String, Integer> gamebonus = new Hashtable<String, Integer>();

    //public static BufferedWriter bw;
    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    public static Calendar cal = Calendar.getInstance();
    

    private DataInputStream in;

	//private DataOutputStream out;
	//private BufferedReader in;

	private BufferedWriter out;

	private Socket clientSocket;
	int level;  
	String gameID;
	String message;
	

	public Connectivity(Socket aClientSocket)
	{
		try
		{
			clientSocket = aClientSocket;

			in = new DataInputStream(clientSocket.getInputStream());
			//out = new DataOutputStream(clientSocket.getOutputStream());	
			//in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            this.start();

		}

		catch (IOException e)

		{

			System.out.println("Connection:" + e.getMessage());

		}

	}

	public void write(String str) throws IOException {
		out.write(str + "\n");
        out.flush();
    }

	public String read() throws IOException {
		return in.readLine();
	}

	public void run()
	{

		int source = -1;
        //String gameid = null;
        String data = null;       
        try

		{

			while (true) {

                data = this.read();
                System.out.println("Data Recieved: " + data);
                String[] dataArray= data.split(";");
				String type = "";
                String cmd = "";
                int steps = 0;
                int bonus = 0;             
                String msg = "";
                
                for(int i=0; i<dataArray.length;i++){
					if(dataArray[i].trim().startsWith("gameid")){
						String[] tempArray= dataArray[i].split("=");
						gameID=tempArray[1].trim();
					}
					if(dataArray[i].trim().startsWith("source")){
						String[] tempArray= dataArray[i].split("=");
						type=tempArray[1].trim();
					}
                    if(dataArray[i].trim().startsWith("command")){
						String[] tempArray= dataArray[i].split("=");
						cmd=tempArray[1].trim();
					}
                    if(dataArray[i].trim().startsWith("step")){
						String[] tempArray= dataArray[i].split("=");
						steps=Integer.parseInt(tempArray[1].trim());
					}
                    if(dataArray[i].trim().startsWith("level")){
						String[] tempArray= dataArray[i].split("=");
						level=Integer.parseInt(tempArray[1].trim());
					}
                    if(dataArray[i].trim().startsWith("bonus")){
						String[] tempArray= dataArray[i].split("=");
						bonus=Integer.parseInt(tempArray[1].trim());
					}
                    if(dataArray[i].trim().startsWith("msg")){
						String[] tempArray= dataArray[i].split("=");
						msg=tempArray[1].trim();
					}
					
                }               
                
                //System.out.println("Check: " +cmd+" "+x);

                if(cmd.compareToIgnoreCase("stop")==0){
                    gameSteps.put(gameID,steps);
                    gamebonus.put(gameID, bonus);
                    if(msg.equals("Success")){
                        System.out.println("Writing user scores");
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        java.util.Date date = new java.util.Date ();
                        String dateStr = dateFormat.format (date);
                        //System.out.println("Adding rescue_scores GameID:" + gameID + ", level:" + level + ",Steps: " + gameSteps.get(gameID) + ", Bonus:" + gamebonus.get(gameID) + ", Time:" + dateStr );
                        try {
                            //DBConnection dbc = new DBConnection();
                            Connection con = DBConnection.getConnection();
                            Statement st = con.createStatement();
                            st.executeUpdate("INSERT rescue_scores VALUES('" + gameID + "'," + level + "," + gameSteps.get(gameID) + "," + gamebonus.get(gameID) + ",'" + dateStr + "')");
                            DBConnection.closeConnection(con);
                            gameSteps.remove(gameID);
                            gamebonus.remove(gameID);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                }
                /*if(cmd.equalsIgnoreCase("init")) {
                	gamelevel.put(gameid, level);
                }*/

                if (source == -1 && type.equals("user")) {
                    source = 1;
                    System.out.println("Connected to user ..." + gameID);
                    mUserCons.put(gameID, this);
                    if(userMessage.get(gameID)!=null){
                        System.out.println("Connection.java: Writing sim message to user: " + userMessage.get(gameID));
                        mUserCons.get(gameID).write(userMessage.get(gameID));
                        Thread.sleep(100);
                    }
                } else if (source == -1 && type.equals("server")) {
                    source = 2;
					mSimulCons.put(gameID, this);
                    System.out.println("Connected to Simulation ..." + gameID);
                    if(simMessage.get(gameID)!=null){
                        System.out.println("Connection.java: Writing user message to sim: " + simMessage.get(gameID));
                        mSimulCons.get(gameID).write(simMessage.get(gameID));
                        Thread.sleep(100);
                    }
                }

				if (source == 1 && mSimulCons.get(gameID)!=null) {
                    System.out.println("Connection.java: Writing to simulation: " + data);
                    mSimulCons.get(gameID).write(data);
				}else if(source == 1 && mSimulCons.get(gameID) == null){
                    System.out.println("Connection.java: User message id:" + gameID + " message: " + data);
                    simMessage.put(gameID, data);
                }			
                else if (source == 2 && mUserCons.get(gameID)!=null) {
                	if(cmd.compareToIgnoreCase("init")!=0){
                		System.out.println("Connection.java: Writing to user: " + data);
                    	mUserCons.get(gameID).write(data);                    	
                	}
                    if(cmd.equalsIgnoreCase("stop")){
                        mUserCons.remove(gameID);
                        userMessage.remove(gameID);
                    }
                }
                else if(source == 2 && mUserCons.get(gameID) == null && cmd.compareToIgnoreCase("stop") !=0){
                    System.out.println("Connection.java: Sim message id:" + gameID + " message: " + data);                    
                    if(cmd.compareToIgnoreCase("init")!=0)
                    userMessage.put(gameID, data);
                }

               Thread.sleep(100);
            }

		}	

		catch (Exception e)

		{
			//System.out.println("Check:);
			//e.printStackTrace();
			System.out.println("source: " + source + ",IO:" + e.getMessage());

            if(e.getMessage()!=null && e.getMessage().compareToIgnoreCase("Connection reset")==0){
                if(source == 1 && mUserCons.containsKey(gameID)){
                    System.out.println("Removing user game");
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    java.util.Date date = new java.util.Date ();
                    String dateStr = dateFormat.format (date);
                    
                    //if(gamebonus.get(gameid) > 0)
                    	//gamelevel++;
                    /*if(gameSteps.containsKey(gameID)&& gamebonus.containsKey(gameID)){
                        try {
                            //DBConnection dbc = new DBConnection();
                            Connection con = DBConnection.getConnection();
                            Statement st = con.createStatement();
                            st.executeUpdate("INSERT rescue_scores VALUES('" + gameID + "'," + level + "," + gameSteps.get(gameID) + "," + gamebonus.get(gameID) + ",'" + dateStr + "')");
                            DBConnection.closeConnection(con);
                            gameSteps.remove(gameID);
                            gamebonus.remove(gameID);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }*/
					//try {
                        //System.out.println("Writing to file: " + "gameid:" + gameid + ",points:" + gameSteps.get(gameid) + ",time:" + dateStr);
                        //bw.write("gameid:" + gameid + ",level:" + level + ",points:" + gameSteps.get(gameid)+ ",bonus:" +gamebonus.get(gameid)+ ",time:" + dateStr + "\n");
					//String points = "gameid:" + gameID + ";level:" + level + ";points:" + gameSteps.get(gameID)+ ";bonus:" +gamebonus.get(gameID)+ ";time:" + dateStr + "\n";	
                    //Logger.add(points);
                    
                   // } catch (Exception e1) {
                      //  e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                      //  System.out.println("Not able to write to Points file.");
                   // }
                    
                    mUserCons.remove(gameID);
                    userMessage.remove(gameID);
                    source = -1;
                }
                else if(source == 2){
                    System.out.println("Removing simulation game");
                    
                    mSimulCons.remove(gameID);
                    simMessage.remove(gameID);
                    source = -1;
                }
            }
            else if(e.getMessage() == null){
                if(source == 1 ){
                    System.out.println("Removing user game");
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    java.util.Date date = new java.util.Date ();
                    String dateStr = dateFormat.format (date);

                    //if(gamebonus.get(gameid) > 0)
                    	//gamelevel++;
                    /*if(gameSteps.containsKey(gameID)&& gamebonus.containsKey(gameID)){
                        try {
                            //DBConnection dbc = new DBConnection();
                            Connection con = DBConnection.getConnection();
                            Statement st = con.createStatement();
                            st.executeUpdate("INSERT rescue_scores VALUES('" + gameID + "'," + level + "," + gameSteps.get(gameID) + "," + gamebonus.get(gameID) + ",'" + dateStr + "')");
                            DBConnection.closeConnection(con);
                            gameSteps.remove(gameID);
                            gamebonus.remove(gameID);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }

                    }*/

                    mUserCons.remove(gameID);
                    userMessage.remove(gameID);
                    source = -1;
                }
                else if(source == 2){
                    System.out.println("Removing simulation game");

                    mSimulCons.remove(gameID);
                    simMessage.remove(gameID);
                    source = -1;
                }
            }
            
            /*else if(e.getMessage().compareToIgnoreCase("Socket closed")==0){
                if(source == 1 && mSimulCons.get(gameid)!=null){
                    mUserCons.remove(gameid);
                    source = -1;
                    if(data!=null)
                        simMessage.put(gameid, data);
                }
                if(source == 2 && mUserCons.get(gameid)!=null){
                    mSimulCons.remove(gameid);
                    source = -1;
                    if(data!=null)
                        userMessage.put(gameid, data);
                }
            }*/
		}

		finally

		{
			try

			{
				
                clientSocket.close();
			}		
			
			catch (IOException e)
			{

				System.out.println(e.getMessage());

			}
			

		}

	}

}
