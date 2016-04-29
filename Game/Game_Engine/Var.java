package Game_Engine;
public class Var{
	public static double width;		// width of the board
	public static double height;	// height of the board
	public static double speed;		// value of the velocity of every ball
	public static double freq = 62.5;
	public static double[] level_AI = {0.5, 0.75, 1.5, 2.0};
	// public static double[] level_AI = {0.0, 2.0, 2.0, 2.0};
	public static double[] level_power = {0.0, 0.5, 0.75, 1.0, 1.0, 1.0};
	public static double speed_factor = 0.9;
	public static double speed_increase;
	public static double speed_decay_factor = 0.99;
	public static double length_factor = 3;
	public static double spin_factor = 3;
	public static double spin_decay_factor = 0.97;

	public static void speed_increase_fn(double d){
		speed_increase+=d;
		if(speed_increase > 1.8) speed_increase=1.8;
	}
}