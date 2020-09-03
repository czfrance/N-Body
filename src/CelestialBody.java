

/**
 * Celestial Body class for NBody
 * @author ola
 *
 */
public class CelestialBody {

	private double myXPos;
	private double myYPos;
	private double myXVel;
	private double myYVel;
	private double myMass;
	private String myFileName;

	/**
	 * Create a Body from parameters	
	 * @param xp initial x position
	 * @param yp initial y position
	 * @param xv initial x velocity
	 * @param yv initial y velocity
	 * @param mass of object
	 * @param filename of image for object animation
	 */
	public CelestialBody(double xp, double yp, double xv,
			             double yv, double mass, String filename){
		myXPos = xp;
		myYPos = yp;
		myXVel = xv;
		myYVel = yv;
		myMass = mass;
		myFileName = filename;
	}

	/**
	 * Copy constructor: copy instance variables from one
	 * body to this body
	 * @param b used to initialize this body
	 */
	public CelestialBody(CelestialBody b){
		myXPos = b.myXPos;
		myYPos = b.myYPos;
		myXVel = b.myXVel;
		myYVel = b.myYVel;
		myMass = b.myMass;
		myFileName = b.myFileName;
	}

	/**
	 * Return x position of this Body
	 * @return value of x-position
	 */
	public double getX() {
		return myXPos;
	}

	/**
	 * Return y position of this Body
	 * @return value of y-position
	 */
	public double getY() {
		return myYPos;
	}

	/**
	 * Return x velocity of this Body
	 * @return value of x-velocity
	 */
	public double getXVel() {
		return myXVel;
	}

	/**
	 * Return y-velocity of this Body.
	 * @return value of y-velocity.
	 */
	public double getYVel() {
		return myYVel;
	}

	/**
	 * Return the mass of this Body.
	 * @return value of mass
	 */
	public double getMass() {
		return myMass;
	}

	/**
	 * Return the name of this Body.
	 * @return value of name
	 */
	public String getName() {
		return myFileName;
	}

	/**
	 * Return the distance between this body and another
	 * @param b the other body to which distance is calculated
	 * @return distance between this body and b
	 */
	public double calcDistance(CelestialBody b) {
		double dx = myXPos - b.myXPos;
		double dy = myYPos - b.myYPos;
		double d = dx * dx + dy * dy;
		return Math.sqrt(d);
	}

	/**
	 * Return the force exerted on this Body by another
	 * @param b the other body that exerts force on this Body
	 * @return force exerted on this Body by another
	 */
	public double calcForceExertedBy(CelestialBody b) {
		double g = 6.67E-11;
		double f = myMass * b.myMass;
		f = f / (calcDistance(b) * calcDistance(b));
		return f * g;
	}

	/**
	 * Return the force exerted in the X direction on this Body by another
	 * @param b the other body that exerts force on this Body
	 * @return force exerted in the X direction
	 */
	public double calcForceExertedByX(CelestialBody b) {
		double dx = b.myXPos - myXPos;
		double fx = calcForceExertedBy(b) * dx;
		return fx / calcDistance(b);
	}

	/**
	 * Return the force exerted in the Y direction on this Body by another
	 * @param b the other body that exerts force on this Body
	 * @return force exerted in the Y direction
	 */
	public double calcForceExertedByY(CelestialBody b) {
		double dy = b.myYPos - myYPos;
		double fy = calcForceExertedBy(b) * dy;
		return fy / calcDistance(b);
	}

	/**
	 * Return the total force exerted in the X direction on this Body by all other bodies
	 * in the array (but not itself)
	 * @param bodies the array containing other bodies that are exerting force on this Body
	 * @return total force exerted in X direction
	 */
	public double calcNetForceExertedByX(CelestialBody[] bodies) {
		double sum = 0.0;
		for (CelestialBody b : bodies) {
			if (!b.equals(this)) {
				sum += calcForceExertedByX(b);
			}
		}
		return sum;
	}

	/**
	 * Return the total force exerted in the Y direction on this Body by all other bodies
	 * in the array (but not itself)
	 * @param bodies the array containing other bodies that are exerting force on this Body
	 * @return total force exerted in Y direction
	 */
	public double calcNetForceExertedByY(CelestialBody[] bodies) {
		double sum = 0.0;
		for (CelestialBody b : bodies) {
			if (!b.equals(this)) {
				sum += calcForceExertedByY(b);
			}
		}
		return sum;
	}

	/**
	 * updates the instance variables of this Body
	 * @param deltaT the value of the time steps with which this Body will be updated
	 * @param xforce the value of the net forces in the X direction exerted on this
	 *                  body by all other bodies in the simulation
	 * @param yforce the value of the net forces in the Y direction exerted on this
	 *                  body by all other bodies in the simulation
	 */
	public void update(double deltaT, 
			           double xforce, double yforce) {
		double ax = xforce / myMass;
		double ay = yforce / myMass;
		double nvx = myXVel + deltaT*ax;
		double nvy = myYVel + deltaT*ay;
		double nx = myXPos + deltaT*nvx;
		double ny = myYPos + deltaT*nvy;

		myXPos = nx;
		myYPos = ny;
		myXVel = nvx;
		myYVel = nvy;
	}

	/**
	 * Draws this planet's image at its current position
	 */
	public void draw() {
		StdDraw.picture(myXPos,myYPos,"images/"+myFileName);
	}
}
