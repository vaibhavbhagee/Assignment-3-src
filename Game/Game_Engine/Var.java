package Game_Engine;
public class Var{
	public static double width;		// width of the board
	public static double height;	// height of the board
	public static double speed;		// value of the velocity of every ball
	public static double freq = 50;
	public static double[] level_AI = {0.5, 0.75, 1.5, 1};
	public static double[] level_power = {0.0, 0.5, 0.75, 1.0, 1.25};
	public static double speed_factor = 0.9;
	public static double speed_increase;
	public static double speed_decay_factor = 0.99;
	public static double length_factor = 3;
	public static double spin_factor = 3;
	public static double spin_decay_factor = 0.97;
}