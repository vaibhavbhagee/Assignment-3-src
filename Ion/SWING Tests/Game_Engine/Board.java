package Game_Engine;

import java.util.*;

public class Board{
	long counter = 0;					// counts per update of game
	double epsilon;
	Ball b;
	Player[] plr = new Player[4];	// player[0] is the current_player
	DataForUI data_out;
	Socket_handler socket;
	String name;
	Queue<Player_Info> plr_q;

	public Board(int width, int height, String name, int singleOrMultiPlayer, int isHost){
		Var.width = width;
		Var.height = height;
		Var.speed = Var.width/Var.freq*Var.speed_factor;
		Var.speed_increase = 0;
		epsilon = Var.speed;
		this.name = name;

		plr[0] = new Player(name, null, 0);
		plr[1] = new Player("AI_1", null, 1);
		plr[2] = new Player("AI_2", null, 2);
		plr[3] = new Player("AI_3", null, 3);
		plr[0].is_AI = false;
		plr_q = new LinkedList<Player_Info>();

		b = new Ball(width/2,height/2,Math.PI/5,20);
		data_out = new DataForUI();
		init_network();
	}			// 460 x 460

	public void setParams(int w, int h)
	{
		Var.width = w;
		Var.height = h;
	}

	public DataForUI update(DataForEngine o){
		// called by UI
		// update the position of the ball
		// take care of reflections
		// return an Object to render the board
		++counter;
		epsilon = Var.speed * (Var.speed_increase + 1);
		////////////////////////////////////////////////////periodic_network();

		{		
			plr[0].p.d1 = o.getLeftPosition();
			plr[0].p.d2 = o.getRightPosition();
			plr[0].p.current_power = o.getCurrentPower();
			plr[0].p.paddle_speed = o.getCurrentVelocity();
			data_out.noCollision();
			data_out.resetAllFlags();
		}
		
		if(is_pseudo_server()){
			Var.speed_increase *= Var.speed_decay_factor;
			collision();
			b.update_position();
		}

		artificial_intelligence();
		
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
		periodic_network();
		return data_out;
	}

	void hit_paddle(int paddle_num, Ball b){
		double x,l,phi;
		//System.out.print("Previous angle: "+(b.theetha*180/Math.PI));
		if(paddle_num%2==0) x = b.posX - (plr[paddle_num].p.d1+plr[paddle_num].p.d2)/2;
		else x = b.posY - (plr[paddle_num].p.d1+plr[paddle_num].p.d2)/2;
		l = (plr[paddle_num].p.d2-plr[paddle_num].p.d1);
		if(paddle_num==0){
			phi = Math.atan(l*Var.length_factor/x);
			b.posY += epsilon;
			data_out.collisionPaddle(0);
		}
		else if(paddle_num==1){
			phi = Math.atan(x/l/Var.length_factor);
			b.posX += epsilon;
			data_out.collisionPaddle(1);
		}
		else if(paddle_num==2){
			phi = -Math.atan(l*Var.length_factor/x);
			b.posY -= epsilon;
			data_out.collisionPaddle(2);
		}
		else {
			phi = -Math.atan(x/l/Var.length_factor);
			b.posX -= epsilon;
			data_out.collisionPaddle(3);
		}
		data_out.setBallPaddleCollide(true);
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
			data_out.collisionPaddle(0);
			b.addSpin(plr[0].p.paddle_speed);
			Var.speed_increase += Var.level_power[plr[0].p.current_power];		
			//System.out.println("Paddle 0");
			//System.out.println(plr[0].p.current_power+" "+ plr[0].p.paddle_speed);
		}else if(Math.abs(b.posY-b.diameter/2) < epsilon){		//w0
			// b.velY*=-1;
			b.theetha = 2*Math.PI - b.theetha;
			data_out.oneLifeLostBy(0);
			data_out.setBallWallCollide(true);
			data_out.collisionWall(0);
			//System.out.println("wall 0");
		}

		// w1
		if((Math.abs(b.posX - b.diameter/2 - plr[1].p.delta) < epsilon)&&(b.posY > plr[1].p.d1)&&(b.posY < plr[1].p.d2)){
			hit_paddle(1,b);
			data_out.collisionPaddle(1);
			b.addSpin(plr[1].p.paddle_speed);
			Var.speed_increase += Var.level_power[plr[1].p.current_power];
			//System.out.println("Paddle 1");
		}else if(Math.abs(b.posX-b.diameter/2) < epsilon){		//w1
			// b.velX*=-1;
			if(b.theetha < Math.PI) b.theetha = Math.PI - b.theetha;
			else b.theetha = 3*Math.PI - b.theetha;
			data_out.oneLifeLostBy(1);
			data_out.setBallWallCollide(true);
			data_out.collisionWall(1);
			System.out.println("wall 1");
			//System.out.println("Angle: "+(b.theetha*180/Math.PI));
		}

		// w2
		if((Math.abs(b.posY + b.diameter/2 + plr[2].p.delta - Var.height) < epsilon)&&(b.posX > plr[2].p.d1)&&(b.posX < plr[2].p.d2))
		{
			hit_paddle(2,b);
			data_out.collisionPaddle(2);
			b.addSpin(plr[2].p.paddle_speed);
			Var.speed_increase += Var.level_power[plr[2].p.current_power];
			//System.out.println("Paddle 2");
		}else if(Math.abs(b.posY+b.diameter/2 - Var.height) < epsilon){	//w2
			// b.velY*=-1;
			b.theetha = 2*Math.PI - b.theetha;
			data_out.oneLifeLostBy(2);
			data_out.setBallWallCollide(true);
			data_out.collisionWall(2);
			System.out.println("wall 2");
			//System.out.println("Angle: "+(b.theetha*180/Math.PI));
		}

		// w3
		if((Math.abs(b.posX + b.diameter/2 + plr[3].p.delta - Var.width) < epsilon)&&(b.posY > plr[3].p.d1)&&(b.posY < plr[3].p.d2)){
			hit_paddle(3,b);
			data_out.collisionPaddle(3);
			b.addSpin(plr[3].p.paddle_speed);
			Var.speed_increase += Var.level_power[plr[2].p.current_power];
			//System.out.println("Paddle 3");
		}else if(Math.abs(b.posX+b.diameter/2 - Var.width) < epsilon){	//w3
			// b.velX*=-1;
			if(b.theetha < Math.PI) b.theetha = Math.PI -b.theetha;
			else b.theetha = 3*Math.PI - b.theetha;
			data_out.oneLifeLostBy(3);
			data_out.setBallWallCollide(true);
			data_out.collisionWall(3);
			System.out.println("wall 3");
		}
	}

	void init_network(){
		try{
			String s1 = System.console().readLine("Enter Choice: ");
			socket = new Socket_handler(s1);
			new Thread(socket).start();
			if(s1.equals("2"))
			socket.connect_to_user(System.console().readLine("Enter IP: "));
			plr[0].ip = socket.my_ip_address();
		}catch(Exception e){e.printStackTrace();}
	}
	public void periodic_network(){
/*ion*/	System.out.println("PeriodicNetwork");
		get_all_messages();
		if(is_pseudo_server()){
			String msg1 = "Message1;"+b.to_String()+plr[0].to_String()+plr[1].to_String()+plr[2].to_String()+plr[3].to_String()+Var.speed_increase;
			System.out.println(msg1);
			broadcast(msg1);
		}else{
			String msg2 = "Message2;"+plr[0].to_String();
			System.out.println(msg2);
			broadcast(msg2);
		}
		//System.out.println(is_pseudo_server());
	}

	boolean is_pseudo_server(){
		// returns true depending on whether the current player is the pseudo server
		try{
			return socket.is_pseudo_server();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	void broadcast(String str){
		String msg1 = b.to_String()+plr[0].to_String()+plr[1].to_String()+plr[2].to_String()+plr[3].to_String();
		String msg2 = plr[0].to_String();
		try{
			socket.send_message_to_all(str);
		}catch(Exception e){e.printStackTrace();}
		

		// if(is_pseudo_server()){
		// 	// broadcast the position of the ball and all players
		// 	RequestHandler.broadcast("Appropriate Message");
		// }else{
		// 	// broadcast the position of the current_player paddle (Player Object)
		// 	RequestHandler.broadcast("Appropriate Message");
		// }
	}
	void decode(String str){
		String ip_temp;
		String s[] = str.split(";");
		switch(s[0]){
			case "Message1" : if(!is_pseudo_server()) {
				b.from_String(s[1]);
				ip_temp = s[2].substring(0, s[2].indexOf("#"));
				for(int i=1; i<4; ++i) 
					if(plr[i].ip.equals(ip_temp)) plr[i].from_String(s[2]);
				ip_temp = s[3].substring(0, s[3].indexOf("#"));
				for(int i=1; i<4; ++i) 
					if(plr[i].ip.equals(ip_temp)) plr[i].from_String(s[3]);
				ip_temp = s[4].substring(0, s[4].indexOf("#"));
				for(int i=1; i<4; ++i) 
					if(plr[i].ip.equals(ip_temp)) plr[i].from_String(s[4]);
				ip_temp = s[5].substring(0, s[5].indexOf("#"));
				for(int i=1; i<4; ++i) 
					if(plr[i].ip.equals(ip_temp)) plr[i].from_String(s[5]);
				Var.speed_increase = Double.parseDouble(s[6]);
			}	break;
			case "Message2" : {
				ip_temp = s[1].substring(0, s[1].indexOf("#"));
				for(int i=1; i<4; ++i)
					if(plr[i].ip.equals(ip_temp)) plr[i].from_String(s[1]);
				break;
			}
			case "User-Added" : 
			case "User-Joined" : 
			case "New-User-Added" : {
				broadcast("User-Name;"+socket.my_ip_address()+";"+name);
				plr_q.add(new Player_Info(s[1]));
				break;
			}
			case "User-Name" : {
				for(Player_Info p : plr_q){
					if(p.ip.equals(s[1])) p.name = s[2];
				}
				break;
			}
			case "User-Reconnected" : {
				for(int i=0; i<4; ++i){
					if(plr[i].ip.equals(s[1])){
						plr[i].is_AI = false;
						break;
					}
				}
				break;
			}
			case "User-Disconnected" : {
				for(int i=0; i<4; ++i){
					if(plr[i].ip.equals(s[1])){
						plr[i].is_AI = true;
						break;
					}
				}
				break;
			}
			case "Joining-Order" : {
				int my_joining_order = -100;
				try{
					for(Player_Info p : plr_q){
						if(p.ip.equals(s[1])) 
							p.joining_order = Integer.parseInt(s[2]);
					}
					if(socket.my_ip_address().equals(s[1])) my_joining_order = Integer.parseInt(s[2]);
					for(Player_Info p : plr_q){
						if(p.ip.equals(s[3])) 
							p.joining_order = Integer.parseInt(s[4]);
					}
					if(socket.my_ip_address().equals(s[3])) my_joining_order = Integer.parseInt(s[4]);
					for(Player_Info p : plr_q){
						if(p.ip.equals(s[5])) 
							p.joining_order = Integer.parseInt(s[6]);
					}
					if(socket.my_ip_address().equals(s[5])) my_joining_order = Integer.parseInt(s[6]);
					for(Player_Info p : plr_q){
						if(p.ip.equals(s[7])) 
							p.joining_order = Integer.parseInt(s[8]);
					}
					if(socket.my_ip_address().equals(s[7])) my_joining_order = Integer.parseInt(s[8]);
				}catch(Exception e){e.printStackTrace();}
				System.out.println("My joining_order: "+my_joining_order);
				for(Player_Info p : plr_q){
					int index = p.joining_order - my_joining_order;
					if(index<0) index += 4;
					plr[index].name = p.name;
					plr[index].ip = p.ip;
					plr[index].is_AI = false;
					System.out.println(index + " "+p.ip+" "+p.name+" "+p.joining_order);
				}
				break;
			}
		}
	}
	void get_all_messages(){
		Queue<String> messageQueue = socket.ret_q();
		// System.out.println(messageQueue);
		System.out.println(socket.message_queue);
		for(String s : messageQueue){
			decode(s);
		}

		// receive the broadcasted message from the server and decode them appropriately
		// type of messages
		// 1) position of ball and all players
		// 2) position of paddle of a certain player
		// 3) new player has been added - Get me the details of the player along with player number
	}

	public int getSpeed()
	{
		return (int)(Var.speed);
	}

	class Player_Info{
		public String name;
		public String ip;
		public int joining_order;
		public Player_Info(String ip){
			this.name = null;
			this.ip = ip;
			joining_order = -100;
		}
	}
	//User-Added;10.208.20.232
	//User-Added;10.192.34.150
	//Joining-Order;10.208.20.232;0;10.192.34.150;1

	//User-Added;10.208.20.232
	//User-Reconnected;10.192.34.150
	//Joining-Order;10.208.20.232;0;10.192.34.150;1;10.192.45.162;2
	//User-Disconnected;10.192.45.162
}