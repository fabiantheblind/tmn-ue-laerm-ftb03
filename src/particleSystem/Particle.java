package particleSystem;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class Particle {
	PApplet p;
	public PVector loc;
	public PVector vel;
	public PVector acc;

	public float  gravity = 0.0f;
	public float maxforce = 0.3f;    // Maximum steering force
	public float maxspeed =  0.3f;    // Maximum speed
	public float radius;// = 5f;    // radius
	public float lifeTime = 100;    // the lifetime of an Particle
    public float mass = 0.5f; // The higher the mass the lesser the particles get pushed by repellers

	
	
//	some graphical stuff
	
	public int col1;
	public int col2;
	

	
	
	public Particle(PApplet p_, PVector loc_, PVector vel_, float r_,float ms, float mf) {
		// TODO Auto-generated constructor stub
		p = p_;
		loc = loc_.get();
		
		vel = vel_;
		radius = r_;
		maxspeed = ms;
		maxforce = mf;
		acc = new PVector(0,0);
		vel = new PVector(0,0);
	    lifeTime = 100000.0f;
		col1 = p.color(255,20);
		 col2 = p.color(255,5);

	}
	
	public Particle(PApplet p_, PVector loc_, PVector vel_, float r_) {
		// TODO Auto-generated constructor stub
		p = p_;
		loc = loc_.get();
		
		vel = vel_;
		radius = r_;
		acc = new PVector(0,0);
		vel = new PVector(0,0);
	    lifeTime = 100000.0f;

		col1 = p.color(255,20);
		 col2 = p.color(255,5);
	}
	
//	this is the particle for the ParticleSystem Emitter
	  public Particle(PApplet p_, PVector loc_) {
		  p = p_;
		  
		    acc = new PVector(0,0);
		    vel = new PVector(0,0);
		    loc = loc_.get();
//		    r = 10.0;
		    lifeTime = 100000.0f;
//		    maxspeed = 2;
		    
			col1 = p.color(255,20);
			 col2 = p.color(255,5);
		  }
	
	  // Is the particle still useful?
	  boolean dead() {
	    if (lifeTime <= 0.0) {
	      return true;
	    } else {
	      return false;
	    }
	  }
	  
//	  set the lifetime of the particle
	  
	  public void setLifeTime(float lifeTimeIn){
		  
		  lifeTime = lifeTimeIn;
	  }
	  
	  
////	  some graphical stuff in RGB
//	  public void setColorCol1(int r, int g, int b, int a){
//			col1 = p.color(r,g,b,a);
//	  }
//	  
//	  public void setColorCol1Grey(int greyVal, int a){
//			col1 = p.color(greyVal,a);
//	  }
//	  
//	  public void setColorCol2(int r, int g, int b, int a){
//			col2 = p.color(r,g,b,a);
//	  }
//	  
//	  public void setColorCol2Grey(int greyVal, int a){
//			col2 = p.color(greyVal,a);
//	  }
	  
//	  some graphical stuff in HSB
	  public void setColorCol1(int h, float s, float b, int a){
			col1 = p.color(h,s,b,a);
	  }
	  
	  public void setColorCol1Grey(int greyVal, int a){
			col1 = p.color(360,0,greyVal,a);
	  }
	  
	  public void setColorCol2(int h, float s, float b, int a){
			col2 = p.color(h,s,b,a);
	  }
	  
	  public void setColorCol2Grey(int greyVal, int a){
			col2 = p.color(360,0,greyVal,a);
	  }
	  
	  
		public void setGravity(float inGravity){
			gravity = inGravity;
		}
		
		public void setMaxforce(float inMaxforce){
			maxforce = inMaxforce;
		}
		
		public void setMaxspeed(float inMaxspeed){
			maxspeed = inMaxspeed;
		}
		
		public void setRadius(float inRadius) {
			radius = inRadius;
		}
		  
		public void setMass(float massIn){
			
			mass = massIn;
			
		}
	  
	
//	  this is for playing around with forces
	public void myForce(ArrayList<Particle> obstacles){
		
		for(int i = 0;i<obstacles.size();i++){
			Particle obstcl = obstacles.get(i);
			
			PVector force = new PVector(obstcl.loc.x,obstcl.loc.y); //obstcl.loc;

			force.normalize();
			
			loc.x = loc.x +force.x;
			loc.y = loc.y +force.y;
			
//			if( (loc.x > (obstcl.loc.x + obstcl.radius/2))){
//					vel.x = vel.x + 1;
//			}else if (loc.x < (obstcl.loc.x - obstcl.radius/2 )){
//				vel.x = vel.x - 1;	
//			}
//			if( (loc.y > (obstcl.loc.y + obstcl.radius/2))){
//				vel.y = vel.y + 1;
//		}else if (loc.y < (obstcl.loc.y - obstcl.radius/2 )){
//			vel.y = vel.y - 1;	
//		} else {
//			
//			vel.x = vel.x;
//			vel.y = vel.y;
//		}
		
		}
		
		
	}
		
	
	@SuppressWarnings("static-access")
	void limit (){
		if(loc.y> p.height-radius|| loc.y <radius){
			
			
			vel.y = -vel.y;
			loc.y= p.constrain(loc.y,-p.height*p.height,p.height-radius);
			
		}
		if((loc.x < radius)||(loc.x > p.width-radius)){
			vel.x = -vel.x;
			loc.x= p.constrain(loc.x, radius, p.width-radius);
			
			
		}
		
		
	}
	
	
	public void display(){
		p.noStroke();
		p.fill(col1);
		p.ellipse(loc.x, loc.y, radius, radius);
		p.fill(col2);
		p.ellipse(loc.x,loc.y,radius*1.5f,radius*1.5f);
		
	    for(int i=0;i<2;i++){
	    	p.strokeWeight(3);
	    	p.stroke(col2);
		    p.beginShape(p.LINES);
		    p.vertex(loc.x+p.random(-radius*1.5f,radius*1.5f), loc.y+p.random(-radius*1.5f,radius*1.5f));
		    p.vertex(loc.x+p.random(-radius*1.5f,radius*1.5f), loc.y+p.random(-radius*1.5f,radius*1.5f));
		    p.endShape();
		    }
	    p.noStroke();
		
	}
	


	  // A function to deal with path following and separation
	  public void applyForces(ArrayList<Particle> ptkls, Path path) {
	    // Follow path force
	    PVector f = follow(path);
	    // Separate from other boids force
	    PVector s = separate(ptkls);
	    // Arbitrary weighting
	    f.mult(3);
	    s.mult(1);
	    // Accumulate in acceleration
	    acc.add(f);
	    acc.add(s);
	  }
	  
	  public void applyRepellForce(PVector force){
		  
//		    float mass = 0.001f; // We aren't bothering with mass here
		    force.div(mass);
		    acc.add(force);
  
	  }

	  // Main "run" function
	  public void run() {
	    update();
	    display();
		limit();

	  }

	  // This function implements Craig Reynolds' path following algorithm
	  // http://www.red3d.com/cwr/steer/PathFollow.html
	  private PVector follow(Path pt) {

	    // Predict location 25 (arbitrary choice) frames ahead
	    PVector predict = vel.get();
	    predict.normalize();
	    predict.mult(25);
	    PVector predictLoc = PVector.add(loc, predict);

//	    // Draw the predicted location
//	    if (debug) {
//	      p.fill(0);
//	      p.stroke(0);
//	      p.line(loc.x,loc.y,predictLoc.x, predictLoc.y);
//	      p.ellipse(predictLoc.x, predictLoc.y,4,4);
//	    }

	    // Now we must find the normal to the path from the predicted location
	    // We look at the normal for each line segment and pick out the closest one
	    PVector target = null;
	    PVector dir = null;
	    float record = 1000000;  // Start with a very high record distance that can easily be beaten

	    // Loop through all points of the path
	    for (int i = 0; i < pt.points.size(); i++) {

	      // Look at a line segment
	      PVector a = (PVector) pt.points.get(i);
	      PVector b = (PVector) pt.points.get((i+1)%pt.points.size());  // Path wraps around

	      // Get the normal point to that line
	      PVector normal = getNormalPoint(predictLoc,a,b);

	      // Check if normal is on line segment
	      float da = PVector.dist(normal,a);
	      float db = PVector.dist(normal,b);
	      PVector line = PVector.sub(b,a);
	      // If it's not within the line segment, consider the normal to just be the end of the line segment (point b)
	      if (da + db > line.mag()+1) {
	        normal = b.get();
	        // If we're at the end we really want the next line segment for looking ahead
	        a = (PVector) pt.points.get((i+1)%pt.points.size());
	        b = (PVector) pt.points.get((i+2)%pt.points.size());  // Path wraps around
	        line = PVector.sub(b,a);
	      }

	      // How far away are we from the path?
	      float d = PVector.dist(predictLoc,normal);
	      // Did we beat the record and find the closest line segment?
	      if (d < record) {
	        record = d;
	        // If so the target we want to steer towards is the normal
	        target = normal;

	        // Look at the direction of the line segment so we can seek a little bit ahead of the normal
	        dir = line;
	        dir.normalize();
	        // This is an oversimplification
	        // Should be based on distance to path & velocity
	        dir.mult(25);
	      }
	    }

//	    // Draw the debugging stuff
//	    if (debug) {
//	      // Draw normal location
//	      p.fill(0);
//	      p.noStroke();
//	      p.line(predictLoc.x,predictLoc.y,target.x,target.y);
//	      p.ellipse(target.x,target.y,4,4);
//	      p.stroke(0);
//	      // Draw actual target (red if steering towards it)
//	      p.line(predictLoc.x,predictLoc.y,target.x,target.y);
//	      if (record > pt.radius) p.fill(255,0,0);
//	      p.noStroke();
//	      p.ellipse(target.x+dir.x, target.y+dir.y, 8, 8);
//	    }

	    // Only if the distance is greater than the path's radius do we bother to steer
	    if (record > pt.radius || vel.mag() < 0.1) {
	      target.add(dir);
	      return steer(target,false);		
	    } 
	    else {
	      return new PVector(0,0);
	    }
	  }

	  // A function to get the normal point from a point (p) to a line segment (a-b)
	  // This function could be optimized to make fewer new Vector objects
	 public PVector getNormalPoint(PVector p, PVector a, PVector b) {
	    // Vector from a to p
	    PVector ap = PVector.sub(p,a);
	    // Vector from a to b
	    PVector ab = PVector.sub(b,a);
	    ab.normalize(); // Normalize the line
	    // Project vector "diff" onto line by using the dot product
	    ab.mult(ap.dot(ab));
	    PVector normalPoint = PVector.add(a,ab);
	    return normalPoint;
	  }

	  // Separation
	  // Method checks for nearby boids and steers away
	  public PVector separate (ArrayList<Particle> ptkls) {
	    float desiredseparation = radius*2;
	    PVector steer = new PVector(0,0);
	    int count = 0;
	    // For every boid in the system, check if it's too close
	    for (int i = 0 ; i < ptkls.size(); i++) {
	      Particle other = (Particle) ptkls.get(i);
	      float d = PVector.dist(loc,other.loc);
	      // If the distance is greater than 0 and less than an arbitrary amount (0 when you are yourself)
	      if ((d > 0) && (d < desiredseparation)) {
	        // Calculate vector pointing away from neighbor
	        PVector diff = PVector.sub(loc,other.loc);
	        diff.normalize();
	        diff.div(d);        // Weight by distance
	        steer.add(diff);
	        count++;            // Keep track of how many
	      }
	    }
	    // Average -- divide by how many
	    if (count > 0) {
	      steer.div((float)count);
	    }

	    // As long as the vector is greater than 0
	    if (steer.mag() > 0) {
	      // Implement Reynolds: Steering = Desired - Velocity
	      steer.normalize();
	      steer.mult(maxspeed);
	      steer.sub(vel);
	      steer.limit(maxforce);
	    }
	    return steer;
	  }


	  // Method to update location
	  public void update() {
	    // Update velocity
	    vel.add(acc);
	    // Limit speed
	    vel.limit(maxspeed);
	    loc.add(vel);
	    // Reset accelertion to 0 each cycle
	    acc.mult(0);
	    lifeTime -= 0.5;

	  }

	  public void seek(PVector target) {
	    acc.add(steer(target,false));
	  }

	  public void arrive(PVector target) {
	    acc.add(steer(target,true));
	  }

	  // A method that calculates a steering vector towards a target
	  // Takes a second argument, if true, it slows down as it approaches the target
	  public PVector steer(PVector target, boolean slowdown) {
	    PVector steer;  // The steering vector
	    PVector desired = PVector.sub(target,loc);  // A vector pointing from the location to the target
	    float d = desired.mag(); // Distance from the target is the magnitude of the vector
	    // If the distance is greater than 0, calc steering (otherwise return zero vector)
	    if (d > 0) {
	      // Normalize desired
	      desired.normalize();
	      // Two options for desired vector magnitude (1 -- based on distance, 2 -- maxspeed)
	      if ((slowdown) && (d < 100.0f)) desired.mult(maxspeed*(d/100.0f)); // This damping is somewhat arbitrary
	      else desired.mult(maxspeed);
	      // Steering = Desired minus Velocity
	      steer = PVector.sub(desired,vel);
	      steer.limit(maxforce);  // Limit to maximum steering force
	    } 
	    else {
	      steer = new PVector(0,0);
	    }
	    return steer;
	  }


	  


}
