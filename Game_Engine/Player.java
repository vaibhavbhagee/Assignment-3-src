public class Player{
	String name;
	int player_number;	// defined as 0,1,2,3 Needed for orientation
	boolean is_AI;		// current_player isn't AI
	int level_AI;		// defined the difficulty level of the AI
	int score; 			// if required
	int lives; 			// number of lives left
	Paddle p;

	public Player(String name, int player_number, double width, double height){
		this.name = name;
		this.player_number = player_number;
		is_AI = true;
		level_AI = 3;
		score = 0;
		lives = 3;
		//if(player_number == 0) p = new Paddle(1,2*width/5, 3*width/5, height/25);
		if(player_number == 0) p = new Paddle(1,0, width, height/25);
		if(player_number == 1) p = new Paddle(2,0, height, width/25);
		if(player_number == 2) p = new Paddle(1,0, width, 24*height/25);
		if(player_number == 3) p = new Paddle(2,0, height, 24*width/25);

	}
}