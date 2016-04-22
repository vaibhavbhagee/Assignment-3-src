import java.util.*;

public class Board{
	long counter;					// counts per update of game
	double epsilon;
	double width;					// width of the board
	double height;					// height of the board
	int freq;
	double speed;					// brief value of the velocity of every ball
	double tan_omega;				// used for reflection
	ArrayList<Ball> ball_list;
	Player[] plr = new Player[4];	// player[0] is the current_player

	public Board(){
		width = 832;
		height = 832;
		freq = 50;
		tan_omega = 0.28;
		speed = width/freq;
		epsilon = speed;

		plr[0] = new Player("Shreyan", 0, width, height);
		ball_list = new ArrayList<Ball>();
		ball_list.add(new Ball(390,350,speed*0.7,speed*0.8,20));
	}

	public Object update(){
		// called by UI
		// update the position of the ball
		// take care of reflections
		// return an Object to render the board

		for(Ball b: ball_list){
			b.posX += b.velX;
			b.posY += b.velY;
			
			if((Math.abs(b.posY-b.radius - plr[0].p.delta) < epsilon)&&(b.posX > plr[0].p.d1)&&(b.posX < plr[0].p.d2)){
				double x = b.posX - (plr[0].p.d1+plr[0].p.d2)/2;
				double l = (plr[0].p.d2-plr[0].p.d1)/2;
				double tan_theetha = tan_omega*x/l;
				double cos_2theetha = (1-tan_theetha*tan_theetha)/(1+tan_theetha*tan_theetha);
				double sin_2theetha = 2*tan_theetha/(1+tan_theetha*tan_theetha);
				b.velX = b.velX*cos_2theetha - b.velY*sin_2theetha;
				b.velY = - b.velX*sin_2theetha - b.velY*cos_2theetha;
			}
			
			if(Math.abs(b.posX+b.radius - width) < epsilon)	b.velX*=-1;
			if(Math.abs(b.posX-b.radius) < epsilon)			b.velX*=-1;
			if(Math.abs(b.posY-b.radius) < epsilon)			b.velY*=-1;
			if(Math.abs(b.posY+b.radius - height) < epsilon)b.velY*=-1;

			//System.out.println(b.posX+" "+b.posY);
		}

		//broadcast();
		//get_all_messages();
		return null;
	}

	public int getX(){
		for(Ball b: ball_list){
			return (int)b.posX;
		}
		return 0;
	}
	public int getY(){
		for(Ball b: ball_list){
			return (int)b.posY;
		}
		return 0;
	}
/*

	void move_paddle(){
		// called by UI to update the position of the paddle
	}

	void artificial_intelligence(int i){
		if(plr[i].is_AI){
			// i is the player who's paddle has to be updated
			;
		}
	}

	boolean is_pseudo_server(){
		// returns true depending on whether the current player is the pseudo server
		return RequestHandler.is_pseudo_server();
	}

	void broadcast(){
		if(is_pseudo_server()){
			// broadcast the position of the ball and all players
			RequestHandler.broadcast("Appropriate Message");
		}else{
			// broadcast the position of the current_player paddle (Player Object)
			RequestHandler.broadcast("Appropriate Message");
		}
	}
	void get_all_messages(){
		PriorityQueue<String> messageQueue = RequestHandler.get_all_messages();
		// receive the broadcasted message from the server and decode them appropriately
		// type of messages
		// 1) position of ball and all players
		// 2) position of paddle of a certain player
		// 3) new player has been added - Get me the details of the player along with player number
	}
*/
}