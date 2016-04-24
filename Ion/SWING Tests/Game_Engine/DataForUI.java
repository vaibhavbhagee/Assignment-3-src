package Game_Engine;
public class DataForUI{

	private boolean[] ball_paddle_collision = new boolean[4];
	private boolean[] ball_wall_collision = new boolean[4];
	private int[] lives = new int[4];
	public int paddle_pos[] = new int[4];
	public int powered_up[] = new int[4];
	private int ballX;
	private int ballY;
	//private boolean[] paddle_paddle_collision

	private int maxLives = 5;

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