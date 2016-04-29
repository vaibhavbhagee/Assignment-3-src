package Game_Engine;
public class Player{
	String name;
	String ip;
	int player_number;	// defined as 0,1,2,3 Needed for orientation
	boolean is_AI;		// current_player isn't AI
	int level_AI;		// defined the difficulty level of the AI
	// int score; 			// if required
	int lives; 			// number of lives left
	Paddle p;

	public Player(String name, String ip, int player_number){
		this.name = name;
		this.ip = ip;
		this.player_number = player_number;
		is_AI = true;
		level_AI = 3;
		// score = 0;
		lives = 5;
		if(player_number == 0) p = new Paddle(1,0, Var.width*0.25, Var.height*0.05,0);
		if(player_number == 1) p = new Paddle(2,0, Var.height*0.25, Var.width*0.05,1);
		if(player_number == 2) p = new Paddle(1,0, Var.width*0.25, Var.height*0.05,2);
		if(player_number == 3) p = new Paddle(2,0, Var.height*0.25, Var.width*0.05,3);
	}

	public String getName()
	{
		return name;
	}
	
	public void movePaddle(double x, double y){
		p.movePaddle(x,y,level_AI);
	}

	public String to_String(){
		return (ip+"#"+name+"#"+player_number+"#"+
			is_AI+"#"+level_AI+"#"+lives+"%"+p.to_String());
	}
	public void from_String(String val, int their_joining_order, int my_joining_order){
		int difference = their_joining_order - my_joining_order;
		if(difference < 0) difference+=4;
		String[] s1 = val.split("%");
		String[] s = s1[0].split("#");
		boolean is_AI_temp = Boolean.parseBoolean(s[3]);
		System.out.println("difference: "+difference+" ip "+ip);
		if(!is_AI_temp){		
			ip = s[0];
			name = s[1];
			player_number = Integer.parseInt(s[2]);
			level_AI = Integer.parseInt(s[4]);
			//lives = Integer.parseInt(s[5]);
			p.from_String(s1[1], (difference==1 || difference==2));
		}
	}

	public void update_lives(String s){
		lives = Integer.parseInt(s);
	}

	public void reduce_life(){
		if(lives > 0) --lives;
		if(lives <= 0) p.disable();
	}

}