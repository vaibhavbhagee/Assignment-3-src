package Game_Engine;
public class Ball{
	static int ball_count = 0;
	int ball_number;// ball number
	double posX;	// x coordinate of ball
	double posY;	// y coordinate of ball
	double velX;	// x coordinate velocity
	double velY;	// y coordinate velocity
	double theetha;	// tan_inverse
	double diameter;	// radius of ball

	public Ball(double posX, double posY, double velX, double velY, double diameter){
		this.ball_number = ball_count;
		this.posX = posX;
		this.posY = posY;
		this.velX = velX;
		this.velY = velY;

		theetha = Math.atan(velY/velX);
		if(velX<0 && velY<0) theetha += Math.PI;
		else if(velX<0 && velY>0) theetha += Math.PI;
		else if(velX>=0 && velY<0) theetha += 2*Math.PI;
		this.diameter = diameter;
		++ball_count;
		//System.out.println("WHERE ARE?? "+(theetha*180/Math.PI));
	}

	public void set_velocity(double speed, double t){
		if(t<0) theetha = t + 2*Math.PI;
		else theetha = t;
		velX = Math.cos(theetha) * speed;
		velY = Math.sin(theetha) * speed;
	}

	public void update_position(){
		posX += velX;
		posY += velY;
		// double accX = (width/2 - posX)/width * acc;
		// double accY = (height/2 - posY)/height * acc;
		// velX += accX;
		// velY += accY;
		// speed += (velX*accX + velY*accY)/speed;
		// System.out.println(speed);
	}
}