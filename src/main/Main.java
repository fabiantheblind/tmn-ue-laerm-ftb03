package main;

import java.util.ArrayList;
import java.util.Iterator;


//import particelsystem.Detector;
//import particelsystem.Grid;
import particleSystem.Particle;
import particleSystem.ParticleSystem;
import particleSystem.Path;
import particleSystem.Repeller;
import processing.core.PApplet;
import processing.core.PVector;
import processing.core.PFont;



public class Main extends PApplet {

	
	PFont f;

	// A path object (series of connected points)
	Path path;
	ParticleSystem ps;
	  
//	ArrayList <Boid>boids;
	ArrayList <Particle> ptclsList =  new ArrayList<Particle>();
	ArrayList  <Particle>obstaclesList =  new ArrayList<Particle>();
	ArrayList<Repeller> repellers = new ArrayList<Repeller>();
//	Grid grid; 
//	Detector detector = new Detector(this);
	public float time;
//	final static int X = Grid.X;
//	final static int Y = Grid.Y;
	int numPtcls = 500; // number of particles
	int numObstcls = 13; // number of particles

	Particle obstcls [] = new Particle[numObstcls]; // particle array
	int obstRadius = 20;// the obstacles radius


//	Detector Class was in the original Sketch inside the main
//	Detector detector = new Detector(this);

	 float ptclRadius = 5;

	private float obstForce = 0.3f;

	private float obstSpeed = 0.3f;
	
	
	// standard processing setup function

	public void setup() {
	
		background(0);
//		frameRate(25);
		size(640,480);
		smooth();
		f = createFont("Gentium",12,true);
//		g = new Grid(this,(obstRadius), (obstRadius));

	  // Call a function to generate new Path object
		initCirclePath(12);
	  // We are now making random Particles and storing them in an ArrayList ptclsList
		initParticles(numPtcls);
//		initObstacles(numObstcls);
	ps = new ParticleSystem(this,1,new PVector(width/2,height/4),ptclsList);
		
		for (int i = 0; i < 15; i++) {
		    repellers.add(new Repeller(this, random(width),random(height)));

		  }
	  time = millis();
	  
	}

	
	// standard processing draw function

	public void draw() {
		cls();
		
		
		float now = (millis());

		  for (int i = 0; i < ptclsList.size(); i++) {
			    Particle ptkl =  ptclsList.get(i);
			    // Path following and separation are worked on in this function
			    
//			    detector.animate(ptkl,(now - time)/1000, g);
			    ptkl.applyForces(ptclsList,path);
			    
				// Call the generic run method (update, borders, display, etc.)
			    ptkl.run();
//			    ptkl.myForce(obstaclesList);
			  }
		  
//		  for(int i=0; i < obstaclesList.size(); i++){
//			  
//			  Particle obstcl = obstaclesList.get(i);
////			  detector.animate(obstcl, (now - time)/1000, g);
//			  obstcl.applyForces(obstaclesList, path);
//			  obstcl.run();
//			  }
		  time = now;

		  // Apply repeller objects to all Particles
		  ps.applyRepellers(repellers);
		  // Run the Particle System
		  ps.run();
		  // Add more particles
//		  ps.addParticleEmitter();
		  
		  // Display all repellers
		  for (int i = 0; i < repellers.size(); i++) {
		    Repeller r = (Repeller) repellers.get(i); 
		    r.display();
		    r.drag();
		  }
 
//	  for(int i=0; i < ptkls.length;i++){
//		    detector.animate(ptkls[i], (now - time) / 1000, g);
//		    ptkls[i].draw();
//		  }

		  
//		  saveFrame("./data/ParticleSystem-####.tif");
	}
	
void cls(){

		noStroke();
		fill(0,10);
		rect(0,0,width,height);
	}

//	void newPath(path) {
//	  // A path is a series of connected points
//	  // A more sophisticated path might be a curve
//	  path = new Path(this);
//	  float offset = 60;
//	  path.addPoint(offset,offset);
//	  path.addPoint(width-offset,offset);
//	  path.addPoint(width-offset,height-offset);
//	  path.addPoint(width/2,height-offset*3);
//	  path.addPoint(offset,height-offset);
//	}
//	
	void initCirclePath(int segments){
		
		path = new Path(this);
		for(int i = 0; i <=360;i+=360/segments){
			  path.addPoint(width / 2 + sin(radians(i))*100,height / 2 + cos(radians(i))*100);
		}
	}

	void initParticles(int numPtkls){
		
		  ptclsList =  new ArrayList<Particle>();
		  for (int i = 0; i < numPtkls; i++) {
		    newPtkl(random(width),random(height),ptclsList);
//		    ptclsList.get(i).setMaxforce(random(-5,5));
//		    ptclsList.get(i).setMaxspeed(random(-2,2));

		  }
		
	}
	
	void initObstacles(int numObstcls){
		  obstaclesList =  new ArrayList<Particle>();
		  for (int i = 0; i < numObstcls; i++) {
//			  newObstcl(random(width),random(height),obstaclesList,obstRadius,1f,1f);
//		  float myMaxspeed = Particle.maxspeed;
//		  float myMaxforce = Particle.maxforce;//+random(-1f,1f);
		Particle ptcle = new Particle(this,new PVector(random(width),random(height)),
				new PVector(random(width),random(height)), obstRadius,obstSpeed,obstForce);
		ptcle.setGravity(0); 
		obstaclesList.add(ptcle);
//	    g.addParticle(ptcle);
			  
			  
		  }
		
	}
	
	

//	void newBoid(float x, float y) {
//	  float maxspeed = random(2,4);
//	  float maxforce = 0.3f;
//	  boids.add(new Boid(this,new PVector(x,y),maxspeed,maxforce));
//	}
	
	
	void newPtkl(float x, float y,ArrayList<Particle> ptclsList) {
//		  float maxforce = 0.3f;    // Maximum steering force
//		  float maxspeed =  0.3f;    // Maximum speed
//		  float myMaxspeed = Particle.maxspeed;
//		  float myMaxforce = Particle.maxforce;//+random(-1f,1f);
		  ptclsList.add(new Particle(this,new PVector(x,y),new PVector(x,y), ptclRadius));
//		or use:
//		  ptclsList.add(new Particle(this,new PVector(x,y),new PVector(x,y), Particle.radius));

	}
	
//	void newObstcl(float x, float y,ArrayList<Particle> obstclsList,float obstRadius,float obstSpeed,float obstForce) {
////		  float myMaxspeed = Particle.maxspeed;
////		  float myMaxforce = Particle.maxforce;//+random(-1f,1f);
//		Particle ptcle = new Particle(this,new PVector(x,y),new PVector(x,y), obstRadius,obstSpeed,obstForce);
//		ptcle.setGravity(0); 
//		obstclsList.add(ptcle);
//	    g.addParticle(ptcle);
//
////		or use:
////		  ptclsList.add(new Particle(this,new PVector(x,y),new PVector(x,y), Particle.radius));
//
//	}
//	Collision Detection Stuff
	public static void assertion(boolean tautology){
		  if (!tautology) throw new AssertionError();
		}
//	public void buildWall(){
//	  for (int i = 0; i < width; i+= radius){
//	    Particle p = new Particle(this,new PVector( i, 0),new PVector(0,0), radius/2,0,0);
//	    
//	    obstaclesList.add(p);
//	    grid.addParticle(p);
//	  }
//	  
//	}

	public void keyPressed() {
	  if (key == 'd') {
//		do something fancy
	  }
	}

	
	public void keyReleased(){
		
		if (key == 's' || key == 'S') {
			saveFrame("./data/MyImg"+time+".jpg");
			
		}	
		if (key == 'e' || key == 'E') {
		exit();			
		}	
		
	}
	public void mousePressed() {
//	  newPtkl(mouseX,mouseY,ptclsList);
		
		for (int i = 0; i < repellers.size(); i++) {
		    Repeller r = (Repeller) repellers.get(i); 
		    r.clicked(mouseX,mouseY);
		  }
	}
	
	public void mouseReleased() {
		  for (int i = 0; i < repellers.size(); i++) {
		    Repeller r = (Repeller) repellers.get(i); 
		    r.stopDragging();
		  }
		}
//	void printAABB(Particle obj){
//		  float pts[][] = obj.getAABB();
//		  println("x,y: (" + obj.loc.x + "," + obj.loc.y + ")");
//		  for(int i = 0; i < pts.length; i++){
//		    println(i + ": (" + pts[i][X] + "," + pts[i][Y] + ")");
//		  }
//		}
//
//		void printSectors(Particle obj){
//		  ArrayList sectors = g.getSectors(obj);
//		  Iterator sects = sectors.iterator();
//		  if (!sects.hasNext()){
//		    println(millis() + ": (" + obj.loc.x + "," + obj.loc.y + ")");
//		    assertion(sects.hasNext());
//		  }
//		}
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { main.Main.class.getName() });
	}
}

	
//	public static void main(String _args[]) {
//		PApplet.main(new String[] { main.Main.class.getName() });
//	}
//}
