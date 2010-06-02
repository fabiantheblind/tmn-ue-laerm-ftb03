package main;

import java.util.ArrayList;

import particleSystem.Particle;
import particleSystem.ParticleSystem;
import particleSystem.Path;
import particleSystem.Repeller;
import processing.core.PApplet;
import processing.core.PVector;
import processing.core.PFont;



public class Main extends PApplet {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6828184868296552766L;

	PFont f;

	// A path object (series of connected points)
	Path path;
//	our particle System
	ParticleSystem ps;

//	Some Arraylists to store the objects
	ArrayList <Particle> ptclsList =  new ArrayList<Particle>();
	ArrayList  <Particle>obstaclesList =  new ArrayList<Particle>();
	ArrayList<Repeller> repellers = new ArrayList<Repeller>();

//	just for unique filenames when saving a frame as jpg in the folder data
	public float time;

	int numPtcls = 500; // number of particles
	int numObstcls = 13; // number of particles
	public int counter;

	Particle obstcls [] = new Particle[numObstcls]; // particle array
	int obstRadius = 20;// the obstacles radius
	 public float dist;

	 float ptclRadius = 5;

	private float obstForce = 0.3f;

	private float obstSpeed = 0.3f;
	
	PVector ColCenterVec;
	
	public float myForce = 0.5f;

	public boolean writeImg = false;
	public int imgNum = 0;
	
	
	// standard processing setup function

	public void setup() {
		colorMode(HSB,360,100,100);
		background(0);
		size(640,480);
		smooth();
		f = createFont("Gentium",12,true);

	  // Call a function to generate new Path object with 12 segments
		initCirclePath(23);
	  // We are now making random Particles and storing them in an ArrayList ptclsList
		initParticles(numPtcls);
//		initObstacles(numObstcls);
	ps = new ParticleSystem(this,1,new PVector(width/2,height/2),ptclsList);
		
		for (int i = 0; i < 5; i++) {
		    repellers.add(new Repeller(this, random(width),random(height)));

		  }
	  time = millis();
	  
	  
	  counter = 0;	 
	}

	
	// standard processing draw function

	public void draw() {
		
//		just a clearScreen method
		cls();
		
//		Testing around
		
		Particle testPtcl = ptclsList.get(0);
		testPtcl.setMaxspeed(myForce);
		testPtcl.setRadius(5);
		int h= floor(cos(counter%60)*360);
		int a = 70;
		float s = 20;
		float b = 100;
//		testPtcl.setColorCol1(h, s, b, a);
		
		repellers.get(0).setG(pow(10,3));
//		repellers.get(0).setColor1(100, 50, 100, 100);

//		testPtcl.setColorCol1(floor(sin(radians(90))*360),100, 100, 255);
//		println("X: "+ptclsList.get(0).loc.x+" Y: "+ptclsList.get(0).loc.y);
		
	
		
		
		
		  for (int i = 0; i < ptclsList.size(); i++) {
			    Particle ptkl =  ptclsList.get(i);
			    // Path following and separation are worked on in this function
			    ptkl.applyForces(ptclsList,path);
				// Call the generic run method (update, borders, display, etc.)
//			    ptkl.setColorCol1(255, 0, 0, 20);
			    ptkl.run();

			    
			    // this is some own force tryout
//			    ptkl.myForce(obstaclesList);
			  }
		  

		  // Apply repeller objects to all Particles
		  ps.applyRepellers(repellers);

		  // Run the Particle System
		  ps.run();
		  
		  // Add more particles with an emitter
		  ps.addParticleEmitter();
		  // use the setEmitterOrigin PVector to set the emitter.
//		  I will soon build a real method for costumizing the emitted particles
//		  ps.setEmitterOrigin(new PVector (width/2,height/2));
		  
		  // Display all repellers
		  for (int i = 0; i < repellers.size(); i++) {
		    Repeller r = (Repeller) repellers.get(i); 
		    r.display();
		    r.drag();
		  }

		  counter++;
		  writeIMGs();
		  
	}
	
void cls(){

		noStroke();
		fill(360,0,0,23);
		rect(0,0,width,height);
	}


//	This is a simple path with 5 points
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

//	This a number of points circling  around the center. for a smother path 
//	give him more segments


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
		    
//		    Set some random force and speed
//		    ptclsList.get(i).setMaxforce(random(-5,5));
//		    ptclsList.get(i).setMaxspeed(random(-2,2));

		  }
		
	}
	
//	Obstacles are just particles ; )
	void initObstacles(int numObstcls){
		  obstaclesList =  new ArrayList<Particle>();
		  for (int i = 0; i < numObstcls; i++) {
//			newObstcl(random(width),random(height),obstaclesList,obstRadius,1f,1f);
//		  float myMaxspeed = Particle.maxspeed;
//		  float myMaxforce = Particle.maxforce;//+random(-1f,1f);
			  
		Particle ptcle = new Particle(this,new PVector(random(width),random(height)),
				new PVector(random(width),random(height)), obstRadius,obstSpeed,obstForce);
		ptcle.setGravity(0); 
		
		obstaclesList.add(ptcle);			  
			  
		  }
		
	}
	
	
	
	void newPtkl(float x, float y,ArrayList<Particle> ptclsList) {
		
//		  float maxforce = 0.3f;    // Maximum steering force
//		  float maxspeed =  0.3f;    // Maximum speed
//		  float myMaxspeed = Particle.maxspeed;
//		  float myMaxforce = Particle.maxforce;//+random(-1f,1f);
		Particle ptcl = new Particle(this,new PVector(x,y),new PVector(x,y), ptclRadius);
		ptcl.setMaxforce(10f);
		ptcl.setMaxforce(5f);
		ptcl.setMaxspeed(2f);

		  ptclsList.add(ptcl);
//		or use:
//		  ptclsList.add(new Particle(this,new PVector(x,y),new PVector(x,y), Particle.radius));

	}
	


	public void writeIMGs(){
		if(writeImg){
			String sa = nf(imgNum,6);
			  saveFrame("./data/ParticleSystem-"+sa+".tif");
			  imgNum++;
		}
		
	}
	
	public void keyPressed() {
	  if (key == 'd') {
//		do something fancy
	  }
	  
	    if( key==CODED ){
	        if( keyCode == UP ){ 
	        	myForce += 0.1f;
	        }
	        if( keyCode == DOWN ){ 
	        	myForce -= 0.1f;

	        }
	    }
	}

	
	public void keyReleased(){
		
		if (key == 's' || key == 'S') {
			saveFrame("./data/MyImg"+time+".jpg");
			
		}	
		if (key == 'e' || key == 'E') {
		exit();			
		}
		if (key == 'i' || key == 'I') {
			writeImg = true;
		}
		if (key == 'o' || key == 'O') {
			writeImg = false;
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

	
	public static void main(String _args[]) {
		PApplet.main(new String[] { main.Main.class.getName() });
	}
}
