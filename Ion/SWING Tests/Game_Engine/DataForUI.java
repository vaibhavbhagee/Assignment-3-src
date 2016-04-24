package Game_Engine;
public class DataForUI{

	private boolean[] ball_paddle_collision = new boolean[4];
	private boolean[] ball_wall_collision = new boolean[4];
	private int[] lives = new int[4];
	//private boolean[] paddle_paddle_collision

	private int maxLives = 5;

	public DataForUI()
	{
		for(int i=0; i<4; i++)
		{
			ball_wall_collision[i] = false;
			ball_paddle_collision[i]=false;
			lives[i]=maxLives;
		}
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
}