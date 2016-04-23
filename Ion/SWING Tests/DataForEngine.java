public class DataForEngine{

	private int current_position;
	private int current_power;
	private int current_velocity;

	public DataForEngine()
	{
		current_power =0;
		current_position =0;
	}

	public void setAll(int pos, int pow, int vel)
	{
		current_position = pos;
		current_power = pow;
		current_velocity = vel;
	}

	public void setCurrentPosition(int p)
	{
		current_position = p;
	}

	public void setCurrentPower(int p)
	{
		current_power = p;
	}

	public void setCurrentVelocity(int v)
	{
		current_velocity = v;
	}

	public int getCurrentPosition()
	{
		return current_position;
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