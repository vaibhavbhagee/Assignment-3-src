package Game_Engine;
public class DataForEngine{

	private int left_position;
	private int right_position;
	private int current_power;
	private int current_velocity;

	public DataForEngine()
	{
		current_power = 0;
		left_position = 0;
		right_position = 0;
	}

	public void setAll(int left_pos, int right_pos, int pow, int vel)
	{
		left_position = left_pos;
		right_position = right_pos;
		current_power = pow;
		current_velocity = vel;
	}

	public void setCurrentPower(int p)
	{
		current_power = p;
	}

	public void setCurrentVelocity(int v)
	{
		current_velocity = v;
	}

	public int getLeftPosition()
	{
		return left_position;
	}
	
	public int getRightPosition()
	{
		return right_position;
	}

	public int getCurrentPower()
	{
		return current_power;
	}

	public int getCurrentVelocity()
	{
		return current_velocity;
	}

}