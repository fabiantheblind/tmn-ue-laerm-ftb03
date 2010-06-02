	package particleSystem;
	
import processing.core.PApplet;
import processing.core.PVector;
	
	//Particles + Forces
	//Daniel Shiffman <http://www.shiffman.net>
	
	//A very basic Repeller class
	public class Repeller {
	
	// Gravitational Constant
	float G = 100;
	
	// Location
	PVector loc;
	PApplet p;
	
	float r = 10;
	
	// For mouse interaction
	boolean dragging = false; // Is the object being dragged?
	boolean rollover = false; // Is the mouse over the ellipse?
	PVector drag;  // holds the offset for when object is clicked on
	
	public Repeller(PApplet p_,float x, float y)  {
		p = p_;
	 loc = new PVector(x,y);
	 drag = new PVector(0,0);
	}
	
	public void display() {
	 p.stroke(255,100);
	 if (dragging) p.fill (255,100);
	 else if (rollover) p.fill(255,200);
	 else p.noFill();
	 p.ellipse(loc.x,loc.y,r*2,r*2);
	}
	
	// Calculate a force to push particle away from repeller
	public PVector pushParticle(Particle ptcl) {
	 PVector dir = PVector.sub(loc, ptcl.loc);      // Calculate direction of force
	 float d = dir.mag();                       // Distance between objects
	 dir.normalize();                           // Normalize vector (distance doesn't matter here, we just want this vector for direction)
	 d = p.constrain(d,5,100);                     // Keep distance within a reasonable range
	 float force = -1 * G / (d * d);            // Repelling force is inversely proportional to distance
	 dir.mult(force);                           // Get force vector --> magnitude * direction
	 return dir;
	}  
	
	// The methods below are for mouse interaction
	public void clicked(int mx, int my) {
	 float d = p.dist(mx,my,loc.x,loc.y);
	 if (d < r) {
	   dragging = true;
	   drag.x = loc.x-mx;
	   drag.y = loc.y-my;
	 }
	}
	
	void rollover(int mx, int my) {
	 float d = p.dist(mx,my,loc.x,loc.y);
	 if (d < r) {
	   rollover = true;
	 } 
	 else {
	   rollover = false;
	 }
	}
	
	public void stopDragging() {
	 dragging = false;
	}
	
	public void drag() {
	 if (dragging) {
	   loc.x = p.mouseX + drag.x;
	   loc.y = p.mouseY + drag.y;
	 }
	}
	
	
	}
	

