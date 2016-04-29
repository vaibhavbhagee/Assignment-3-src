package Game_Engine;

import java.util.*;

public class Board{
	long counter = 0;				// counts per update of game
	double epsilon;
	Ball b;
	Player[] plr = new Player[4];	// player[0] is the current_player
	DataForUI data_out;
	Socket_handler socket;
	String name;
	Queue<Player_Info> plr_q;
	int game_mode = -100;
	String current_ip;
	boolean game_started = false;
	boolean already_set_param = false;
	int singleOrMultiPlayer, isHost;
	int my_joining_order = -100;
	Thread t;
	public String[] connectedPlayers = new String[4];

	public Board(int a, int b, String name, int singleOrMultiPlayer, int isHost){
		this.name = name;
		System.out.println("Constructor");
		this.singleOrMultiPlayer = singleOrMultiPlayer;
		this.isHost = isHost;
		plr_q = new LinkedList<Player_Info>();
		data_out = new DataForUI();
		connectedPlayers[0]="PEER1";
		connectedPlayers[1]="PEER2";
		connectedPlayers[2]="PEER3";
		connectedPlayers[3]="PEER4";
	}			// 460 x 460

	public void getConnectedPlayers()
	{
		System.out.println("getConnectedPlayers");
		//System.out.println("##############################################################################"+plr_q.size());
		for(Player_Info p:plr_q)
		{
			//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+p.ip);
		}
		String[] connectedPlayers2 = new String[4];
		int j=0;
		for(Player_Info p:plr_q)
		{
			boolean flag = true;
			for(int i=0;i<4;i++)
			{
				if(connectedPlayers[i]!=null)
					if( flag == true && connectedPlayers[i].equals("PEER"+(j+1)+":"+p.name+":"+p.ip) )
					{
						flag = false;
						connectedPlayers2[j]="PEER"+(j+1)+":"+p.name+":"+p.ip;
						j++;
						//System.out.println("wow");
					}
				
				//System.out.println("LOOPNE"+connectedPlayers[i]);
			}
			if(flag)
			{
				connectedPlayers2[j]="PEER"+(j+1)+":"+p.name+":"+p.ip;
				j++;
			}
			//System.out.println("FUCKBHKJSBHKJSAHDLALDJFBDKGBADFGKFGVBKSAJFDBJLGHFC<JVFHKFKHBVFJKKFCZ>FbKFVGBJBSVDJFBGVSDKgbfSFgbKFGBVKBDGf"+connectedPlayers2[j-1]);
		}
		for(;j<4;j++)
			if(connectedPlayers2[j]==null)
				connectedPlayers2[j]="PEER"+(j+1);

		for(int o=0;o<4;o++)
			connectedPlayers[o] = connectedPlayers2[o];

		if(this.isHost==1)
		{
			//connectedPlayers[3]="PEER4:Host:"+getHostIP();
		}

				for(int c=0;c<4;c++) ;
			//System.out.println(connectedPlayers[c]+"AOPSDIHASJHBSGBFJFSHBLFJL");
	}

	public void setIndividualAILevel(int ai)
	{
		for(int i=0; i<4; ++i){
			plr[i].level_AI = ai;
		}
	}

	public String playerIName(int i)
	{
		return plr[i].getName();
	}

	public void acceptIP(String ip1)
	{	
		System.out.println("IP" + ip1);
		current_ip = ip1;
	}
	public void setParams(int w, int h)
	{
		if(already_set_param){
			System.out.println("already_set_param");
		}else{
			System.out.println("setParams");
			Var.width = w;
			Var.height = h;
			Var.speed = Var.width/Var.freq*Var.speed_factor;
			Var.speed_increase = 0;
			epsilon = Var.speed;
			b = new Ball(Var.width/2,Var.height/2,Math.PI/5,20);
			plr[0] = new Player(name, null, 0);
			plr[1] = new Player("AI_1", "", 1);
			plr[2] = new Player("AI_2", "", 2);
			plr[3] = new Player("AI_3", "", 3);
			plr[0].is_AI = false;
			already_set_param = true;
		}
		
	}
	public void setGameMode(int i){
		if(game_mode==-100){
			game_mode = i;
			System.out.println("Set gam mode " + i);
			init_network();
		}else{
			System.out.println("FAQ YOU ION");
		}
		
		// 0 - single player
		// 1 - Hosting
		// 2 - connecting
	}

//Called by host of the game when he presses game start
	public void hostApproval(boolean b) 
	{
		broadcast("Start_Game");
		game_started = true;
		//if host approves, b is true
	}

//this is polled by each client user. return true when host has started the game
	public boolean requestForHostApproval()
	{
		return game_started; //true when game has started by host
	}

	public int min(int a, int b)
	{
		if(a<b) return a; else return b;
	}

	public DataForUI update(DataForEngine o){
		// called by UI
		// update the position of the ball
		// take care of reflections
		// return an Object to render the board
		System.out.println("update");
		++counter;
		epsilon = Var.speed * (Var.speed_increase + 1);
		//if(game_mode!=0) periodic_network();

		{
			if(!plr[0].p.disable){
				plr[0].p.d1 = o.getLeftPosition();
				plr[0].p.d2 = o.getRightPosition();
				plr[0].p.current_power = o.getCurrentPower();
				plr[0].p.paddle_speed = o.getCurrentVelocity();
			}
			data_out.noCollision();
			data_out.resetAllFlags();
		}
		
		if(is_pseudo_server()){
			Var.speed_increase *= Var.speed_decay_factor;
			b.update_position();
		}
		collision();

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
			data_out.lives[0] = plr[0].lives;
			data_out.lives[1] = plr[1].lives;
			data_out.lives[2] = plr[2].lives;
			data_out.lives[3] = plr[3].lives;
		}
		if(game_mode!=0) periodic_network();
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
			Var.speed_increase += Var.level_power[min(4,plr[0].p.current_power)];		
			//System.out.println("Paddle 0");
			//System.out.println(plr[0].p.current_power+" "+ plr[0].p.paddle_speed);
		}else if(Math.abs(b.posY-b.diameter/2) < epsilon){		//w0
			// b.velY*=-1;
			b.theetha = 2*Math.PI - b.theetha;
			//data_out.oneLifeLostBy(0);
			plr[0].reduce_life();
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
			//data_out.oneLifeLostBy(1);
			plr[1].reduce_life();
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
			//data_out.oneLifeLostBy(2);
			plr[2].reduce_life();
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
			//data_out.oneLifeLostBy(3);
			plr[3].reduce_life();
			data_out.setBallWallCollide(true);
			data_out.collisionWall(3);
			System.out.println("wall 3");
		}
	}

	void init_network(){
		//game_mode = Integer.parseInt(System.console().readLine("Enter Choice: "));
		System.out.println("init_network "+isHost+" "+current_ip+" Game Mode: "+game_mode);
		if(game_mode!=0)
		try{
			//String s1 = System.console().readLine("Enter Choice: ");
			if(socket==null){
				System.out.println("socket null tha");
				socket = new Socket_handler(isHost+"");
				t = new Thread(socket);
				t.start();
				if(isHost==2)
				socket.connect_to_user(current_ip);
				plr[0].ip = socket.my_ip_address();
			}else{
				System.out.println("socket null nahi tha");
			}
		}catch(Exception e){e.printStackTrace();}
	}

	public void periodic_network(){
		if(game_mode==-100) return;
		System.out.println("PeriodicNetwork");
		get_all_messages();
		if(is_pseudo_server()){
			String msg1 = "Message1;"+b.to_String()+plr[0].to_String()+plr[1].to_String()+plr[2].to_String()+plr[3].to_String()+Var.speed_increase+";"+my_joining_order;
			//System.out.println(msg1);
			broadcast(msg1);
		}else{
			String msg2 = "Message2;"+plr[0].to_String()+my_joining_order;
			//System.out.println(msg2);
			broadcast(msg2);
		}
		if(game_started){ 
			broadcast("Start_Game");
			System.out.println("broadcasting Start Game");
		}
	}

	boolean is_pseudo_server(){
		// returns true depending on whether the current player is the pseudo server
		if(game_mode==0) return true;
		else{
			try{
				return socket.is_pseudo_server();
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("IS PSEUDO SERVER RETURNS TRUE");
				return true;
			}
		}

	}

	void broadcast(String str){
		try{
			socket.send_message_to_all(str);
		}catch(Exception e){e.printStackTrace();}
	}

	void decode(String str){
		String ip_temp;
		String s[] = str.split(";");
		switch(s[0]){
			case "Message1" : if(!is_pseudo_server()) {
				System.out.println("Message1");
				b.from_String(s[1], Integer.parseInt(s[7]), my_joining_order);
				ip_temp = s[2].substring(0, s[2].indexOf("#"));
				for(int i=1; i<4; ++i) 
					if(plr[i].ip.equals(ip_temp)) plr[i].from_String(s[2], Integer.parseInt(s[7]), my_joining_order, is_pseudo_server());
				ip_temp = s[3].substring(0, s[3].indexOf("#"));
				for(int i=1; i<4; ++i) 
					if(plr[i].ip.equals(ip_temp) && plr[i].is_AI) plr[i].from_String(s[3], Integer.parseInt(s[7]), my_joining_order, is_pseudo_server());
				ip_temp = s[4].substring(0, s[4].indexOf("#"));
				for(int i=1; i<4; ++i) 
					if(plr[i].ip.equals(ip_temp) && plr[i].is_AI) plr[i].from_String(s[4], Integer.parseInt(s[7]), my_joining_order, is_pseudo_server());
				ip_temp = s[5].substring(0, s[5].indexOf("#"));
				for(int i=1; i<4; ++i) 
					if(plr[i].ip.equals(ip_temp) && plr[i].is_AI) plr[i].from_String(s[5], Integer.parseInt(s[7]), my_joining_order, is_pseudo_server());
				Var.speed_increase = Double.parseDouble(s[6]);
			}	break;
			case "Message2" : {
				System.out.println("Message2");
				ip_temp = s[1].substring(0, s[1].indexOf("#"));
				for(int i=1; i<4; ++i)
					if(plr[i].ip.equals(ip_temp)) plr[i].from_String(s[1], Integer.parseInt(s[2]), my_joining_order, is_pseudo_server());
				break;
			}
			case "Start_Game" : {
				game_started = true;
				//System.out.println("The game has started!!!");
				break;
			}
			case "User-Added" : 
			case "User-Joined" : 
			case "New-User-Added" : {
				System.out.println("New User");
				broadcast("User-Name;"+socket.my_ip_address()+";"+name);
				plr_q.add(new Player_Info(s[1]));
				break;
			}
			case "User-Name" : {
				System.out.println("User Name, IP: "+s[1]+" Name: "+s[2]);
				for(Player_Info p : plr_q){
					if(p.ip.equals(s[1])) p.name = s[2];
				}
				for(int i=1; i<4; ++i){
					if(plr[i].ip.equals(s[1])){
						plr[i].name = s[2];
						break;
					}
				}
				break;
			}
			case "User-Reconnected" : {
				System.out.println("Reconnected");
				for(int i=0; i<4; ++i){
					if(plr[i].ip.equals(s[1])){
						plr[i].is_AI = false;
						break;
					}
				}
				break;
			}
			case "User-Disconnected" : {
				System.out.println("User-Disconnected");
				for(int i=0; i<4; ++i){
					if(plr[i].ip.equals(s[1])){
						plr[i].is_AI = true;
						break;
					}
				}
				break;
			}
			case "Joining-Order" : {
				System.out.println("Joining order");
				//my_joining_order = -100;
				try{
					for(Player_Info p : plr_q){
						if(p.ip.equals(s[1])) 
							p.joining_order = Integer.parseInt(s[2]);
					}
					if(socket.my_ip_address().equals(s[1])) if(my_joining_order==-100) my_joining_order = Integer.parseInt(s[2]);
					for(Player_Info p : plr_q){
						if(p.ip.equals(s[3])) 
							p.joining_order = Integer.parseInt(s[4]);
					}
					if(socket.my_ip_address().equals(s[3])) if(my_joining_order==-100) my_joining_order = Integer.parseInt(s[4]);
					for(Player_Info p : plr_q){
						if(p.ip.equals(s[5])) 
							p.joining_order = Integer.parseInt(s[6]);
					}
					if(socket.my_ip_address().equals(s[5])) if(my_joining_order==-100) my_joining_order = Integer.parseInt(s[6]);
					for(Player_Info p : plr_q){
						if(p.ip.equals(s[7])) 
							p.joining_order = Integer.parseInt(s[8]);
					}
					if(socket.my_ip_address().equals(s[7])) if(my_joining_order==-100) my_joining_order = Integer.parseInt(s[8]);
				}catch(Exception e){e.printStackTrace();}
				System.out.println("My joining_order: "+my_joining_order);
				for(Player_Info p : plr_q){
					int index = p.joining_order - my_joining_order;
					if(index<0) index += 4;
					plr[index].name = p.name;
					plr[index].ip = p.ip;
					plr[index].is_AI = false;
					//System.out.println(index + " "+p.ip+" "+p.name+" "+p.joining_order);
				}
				for(int i=0; i<4;++i){
					System.out.println("IP: "+plr[i].ip+" Name: "+plr[i].name+" AI? "+plr[i].is_AI);
				}
				break;
			}
		}
	}
	void get_all_messages(){
		try{
			Queue<String> messageQueue = socket.ret_q();
			for(String s : messageQueue){
			decode(s);
			//System.out.println(s);
		}
		}catch(Exception e){
			System.out.println("Get all messages null hai");
		}
		
		// System.out.println(messageQueue);
		//System.out.println(socket.message_queue);
		

		// receive the broadcasted message from the server and decode them appropriately
		// type of messages
		// 1) position of ball and all players
		// 2) position of paddle of a certain player
		// 3) new player has been added - Get me the details of the player along with player number
	}

	public int getSpeed()
	{
		return (int)(Var.speed*(1+Var.speed_increase));
	}

	public void end_game(){
		// TODO: Figure out why giving null pointer on reconnection multiplayer game
		try
		{
			if(t!=null)
			{ 
				if (socket != null) socket.socket_close();
				t.stop();
			}
			socket = null;
			//
			game_mode = -100;
			my_joining_order = -100;
			game_started = false;
			already_set_param = false;
			setParams((int)Var.width, (int)Var.height);
			//
			System.out.println("Game khatam ho gaya");
		}
		catch (Exception e) {
			System.out.println("exception inside end game method");
		}
	}
}
