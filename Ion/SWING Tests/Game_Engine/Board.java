package Game_Engine;

import java.util.*;

public class Board{
	long counter = 0;					// counts per update of game
	double epsilon;
	Ball b;
	Player[] plr = new Player[4];	// player[0] is the current_player
	DataForUI data_out;
	Socket_handler socket;

	public Board(int width, int height){
		Var.width = width;
		Var.height = height;
		Var.speed = Var.width/Var.freq*Var.speed_factor;
		epsilon = Var.speed;
		plr[0] = new Player("Shreyan", 0);
		plr[1] = new Player("Shreyan", 1);
		plr[2] = new Player("Shreyan", 2);
		plr[3] = new Player("Shreyan", 3);
		b = new Ball(width/2,height/2,Var.speed*0.6,Var.speed*0.8,20);
		data_out = new DataForUI();
		try{
			socket = new Socket_handler("1");
		}catch(Exception e){e.printStackTrace();}
	}			// 460 x 460

	public DataForUI update(DataForEngine o){
		// called by UI
		// update the position of the ball
		// take care of reflections
		// return an Object to render the board
		++counter;
		{		
			plr[0].p.d1 = o.getLeftPosition();
			plr[0].p.d2 = o.getRightPosition();
			plr[0].p.current_power = o.getCurrentPower();
			plr[0].p.paddle_speed = o.getCurrentVelocity();
			data_out.noCollision();
			data_out.resetAllFlags();
		}
		
		artificial_intelligence();
		collision();
		b.update_position();
		
		{
			int a = (int)(plr[0].p.d2+plr[0].p.d1)/2;
			int bb = (int)(Var.height-(plr[1].p.d2+plr[1].p.d1)/2);
			int c = (int)(plr[2].p.d2+plr[2].p.d1)/2;
			int d = (int)(Var.height-(plr[3].p.d2+plr[3].p.d1)/2);
			data_out.setPaddlePositions(a,bb,c,d);
			int x = (int)b.posX;
			int y = (int)(Var.height - b.posY);
			data_out.setBallPos(x,y);
		}

		return data_out;
	}

	// public int getX(){
	// 	return (int)b.posX;
	// }
	// public int getY(){
	// 	return (int)(Var.height - b.posY);
	// }

	void hit_paddle(int paddle_num, Ball b){
		double x,l,phi;
		//System.out.print("Previous angle: "+(b.theetha*180/Math.PI));
		if(paddle_num%2==0) x = b.posX - (plr[paddle_num].p.d1+plr[paddle_num].p.d2)/2;
		else x = b.posY - (plr[paddle_num].p.d1+plr[paddle_num].p.d2)/2;
		l = (plr[paddle_num].p.d2-plr[paddle_num].p.d1);
		if(paddle_num==0){
			phi = Math.atan(l*Var.length_factor/x);
			b.posY += epsilon;
		}
		else if(paddle_num==1){
			phi = Math.atan(x/l/Var.length_factor);
			b.posX += epsilon;
		}
		else if(paddle_num==2){
			phi = -Math.atan(l*Var.length_factor/x);
			b.posY -= epsilon;
		}
		else {
			phi = -Math.atan(x/l/Var.length_factor);
			b.posX -= epsilon;
		}
		b.set_velocity(2*phi - b.theetha + Math.PI);
		//System.out.println(" Final angle: "+(b.theetha*180/Math.PI));
		//System.out.println("Phi: "+phi*180/Math.PI);
	}

	void artificial_intelligence(){
		// i is the player who's paddle has to be updated
		for(int i=1;i<4;++i)
		if(plr[i].is_AI){
			plr[i].movePaddle(b.posX, b.posY);
		}
	}
	void collision(){
		// w0
		if((Math.abs(b.posY - b.diameter/2 - plr[0].p.delta) < epsilon)&&(b.posX > plr[0].p.d1)&&(b.posX < plr[0].p.d2)){
			hit_paddle(0,b);
			b.addSpin(plr[0].p.paddle_speed);
			data_out.collisionPaddle(0);
			//System.out.println("Paddle 0");
			//System.out.println(plr[0].p.current_power+" "+ plr[0].p.paddle_speed);
		}else if(Math.abs(b.posY-b.diameter/2) < epsilon){		//w0
			b.velY*=-1;
			b.theetha = 2*Math.PI - b.theetha;
			data_out.collisionWall(0);
			System.out.println("wall 0");
		}

		// w1
		if((Math.abs(b.posX - b.diameter/2 - plr[1].p.delta) < epsilon)&&(b.posY > plr[1].p.d1)&&(b.posY < plr[1].p.d2)){
			hit_paddle(1,b);
			data_out.collisionPaddle(1);
			//System.out.println("Paddle 1");
		}else if(Math.abs(b.posX-b.diameter/2) < epsilon){		//w1
			b.velX*=-1;
			if(b.theetha < Math.PI) b.theetha = Math.PI - b.theetha;
			else b.theetha = 3*Math.PI - b.theetha;
			data_out.collisionWall(1);
			System.out.println("wall 1");
			//System.out.println("Angle: "+(b.theetha*180/Math.PI));
		}

		// w2
		if((Math.abs(b.posY + b.diameter/2 + plr[2].p.delta - Var.height) < epsilon)&&(b.posX > plr[2].p.d1)&&(b.posX < plr[2].p.d2))
		{
			hit_paddle(2,b);
			data_out.collisionPaddle(2);
			//System.out.println("Paddle 2");
		}else if(Math.abs(b.posY+b.diameter/2 - Var.height) < epsilon){	//w2
			b.velY*=-1;
			b.theetha = 2*Math.PI - b.theetha;
			data_out.collisionWall(2);
			System.out.println("wall 2");
			//System.out.println("Angle: "+(b.theetha*180/Math.PI));
		}

		// w3
		if((Math.abs(b.posX + b.diameter/2 + plr[3].p.delta - Var.width) < epsilon)&&(b.posY > plr[3].p.d1)&&(b.posY < plr[3].p.d2)){
			hit_paddle(3,b);
			data_out.collisionPaddle(3);
			//System.out.println("Paddle 3");
		}else if(Math.abs(b.posX+b.diameter/2 - Var.width) < epsilon){	//w3
			b.velX*=-1;
			if(b.theetha < Math.PI) b.theetha = Math.PI - b.theetha;
			else b.theetha = 3*Math.PI - b.theetha;
			data_out.collisionWall(3);
			System.out.println("wall 3");
		}
	}
/*
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