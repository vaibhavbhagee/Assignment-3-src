package Game_Engine;
public class DataForUI{

//TODO: SHREYAN SET THESE FOR NOW:
//THESE ARE NEEDED FOR PLAYING SOUNDS
	private boolean ball_paddle_collide;	//Any ball paddle collision takes place
	private boolean ball_wall_collide;		//Any ball wall collision takes place
	private boolean lifeLostByPlayer;		//A player just lost a life because above
	private int playerDead;				//This player died
	private boolean spin_ball_paddle_collide;//Like first, but spin imparted
	private boolean power_ball_paddle_collide;//Like first, but power up hit

	public void resetAllFlags()
	{
		ball_paddle_collide = false;
		ball_wall_collide = false;
		lifeLostByPlayer = false;
		playerDead = -1;
		spin_ball_paddle_collide = false;
		power_ball_paddle_collide = false;
		//TBC
	}

	public void setBallPaddleCollide(boolean b){ball_paddle_collide=b;}
	public void setBallWallCollide(boolean b){ball_wall_collide=b;}
	public void setLifeLostByPlater(boolean b){lifeLostByPlayer=b;}
	public void setPlayerDead(int p){playerDead = p;}
	public void setPowerBallPaddleCollide(boolean b){power_ball_paddle_collide=b;}

	public boolean getBallPaddleCollide(){return ball_paddle_collide;}
	public boolean getBallWallCollide(){return ball_wall_collide;}
	public boolean getLifeLostByPlayer(){return lifeLostByPlayer;}
	public int getPlayerDied(){return playerDead;}
	public boolean getSpinBallPaddleCollide(){return spin_ball_paddle_collide;}
	public boolean getPowerBallPaddleCollide(){return power_ball_paddle_collide;}

	private boolean[] ball_paddle_collision = new boolean[4];
	private boolean[] ball_wall_collision = new boolean[4];
	public int[] lives = new int[4];
	public int paddle_pos[] = new int[4];
	public int powered_up[] = new int[4];
	private int ballX;
	private int ballY;

	private int maxLives = 5;


	//test for ion only shreyan ignore
	public void redLife(int player)
	{
		if(lives[player]>0)
			lives[player]--;
		System.out.println("asd" + lives[player]);
	}

	public DataForUI()
	{
		for(int i=0; i<4; i++)
		{
			ball_wall_collision[i] = false;
			ball_paddle_collision[i]=false;
			lives[i]=maxLives;
			paddle_pos[i] = 0;
		}
	}

	public void setPower(int player, int pow)
	{
		powered_up[player]=pow;
	}

	public void resetPower()
	{
		for(int i=0;i<4;i++)
			powered_up[i]=0;
	}

	public int[] getPowers()
	{
		return powered_up;
	}

	public int getLife(int player)
	{
		return lives[player];
	}

	public void resetLives()
	{
		for(int i=0;i<4;i++)
			lives[i]=maxLives;
	}

	public void oneLifeLostBy(int player_id)
	{
		if(lives[player_id]>0)
			lives[player_id]--;
	}

	public void collisionPaddle(int player_id)
	{
		ball_paddle_collision[player_id]=true;
	}

	public void collisionWall(int player_id)
	{
		ball_wall_collision[player_id]=true;
	}
	public void noCollision()
	{
		for(int i=0;i<4;i++)
		{
			ball_paddle_collision[i]=false;
			ball_wall_collision[i]=false;
		}
	}
	public void setPaddlePositions(int a, int b, int c, int d){
		paddle_pos[0] = a;
		paddle_pos[1] = b;
		paddle_pos[2] = c;
		paddle_pos[3] = d;
	}

	public void setBallPos(int x, int y){
		ballX = x;
		ballY = y;
	}
	public int getBallX(){
		return ballX;
	}
	public int getBallY(){
		return ballY;
	}
}