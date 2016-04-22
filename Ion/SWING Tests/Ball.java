public class Ball{
	static int ball_count = 0;
	int ball_number;// ball number
	double posX;	// x coordinate of ball
	double posY;	// y coordinate of ball
	double velX;	// x coordinate velocity
	double velY;	// y coordinate velocity
	double radius;	// radius of ball

	public Ball(double posX, double posY, double velX, double velY, double radius){
		this.ball_number = ball_count;
		this.posX = posX;
		this.posY = posY;
		this.velX = velX;
		this.velY = velY;
		this.radius = radius;
		++ball_count;
	}
}