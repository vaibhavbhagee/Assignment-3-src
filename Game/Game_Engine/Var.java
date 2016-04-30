package Game_Engine;
public class Var{
	// The MOST IMPORTANT CLASS OF THE GAME :P

	public static double width;			// width of the board
	public static double height;		// height of the board
	public static double speed;			// value of the velocity of every ball
	public static double freq = 62.5;	// freq set for 60Hz 
	public static double[] level_AI = {0.5, 0.75, 1.5, 2.0};				// speed factor for AI
	public static double[] level_power = {0.0, 0.5, 0.75, 1.0, 1.0, 1.0};	// vel increase of ball on power up
	public static double speed_factor = 0.9;			// increasing this increases game speed
	public static double speed_increase;				// global variable hai
	public static double speed_decay_factor = 0.99;		// self suggestive
	public static double length_factor = 3;				// decreasing this makes board more circular
	public static double spin_factor = 3;				// increasing this increases spin
	public static double spin_decay_factor = 0.97;		// self suggestive

	public static void speed_increase_fn(double d){		// just to set a limit on speed
		speed_increase+=d;
		if(speed_increase > 1.8) speed_increase=1.8;
	}
}