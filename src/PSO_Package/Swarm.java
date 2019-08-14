/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PSO_Package;

/**
 *
 * @author imperio2494
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import oceanclean.DistributionModel;
import oceanclean.timetest;
//import oceanclean.timetest;
class Helper extends TimerTask 
{   
    
    public static int i = 0; 
    public int geti(){
    return i;
    }
    public int maxi = 0;
    Particle p;
    double[][] distanceMatrix;
    double[] gBest;
    Helper ( Particle particles, double[][] dm,double[] gBest,int maxi ){
        this.p = particles;
        this.distanceMatrix = dm;
        this.gBest=gBest;
        this.maxi = maxi;
    }
    
    public void run() { 
//        System.out.println("Timer ran" ); 
             
//                System.out.println("gggg");
                updateVelocity(p);			

                updateSolution(p);
                p.xFitnessValue = generateFitnessValue(p.xSolution);
                if(p.xFitnessValue < p.pBestValue){
                        p.pBest = p.xSolution;
                        p.pBestValue = p.xFitnessValue;
                        p.pBestVelocity = p.pVelocity;
                }
                i++;
                if(i==maxi){
//                    synchronized(Swarm.obj) 
//                        {  
//                            System.out.println("!!!!");
                            Swarm.obj = true;
//                            Swarm.obj.notify(); 

//                        } 
                }
        }
    
        public void updateVelocity(Particle p){
		
		double w = 0.6;
		
		double o1 = 0.2;
		double b1 = 0.3;
		
		double o2 = 0.2;
		double b2 = 0.5;
		
		double[] newV = new double[p.pVelocity.length];
		
		for(int i=0; i<newV.length; i++){			
			newV[i] = w*p.pVelocity[i] + (o1*b1*(p.pBest[i]-p.xSolution[i])) + (o2*b2*(gBest[i] - p.xSolution[i])); 			
		}
		
		p.pVelocity = newV;
		
	}
	
	public void updateSolution(Particle p){
		
		for(int i=0; i<p.xSolution.length; i++){
			p.xSolution[i] = p.xSolution[i] + p.pVelocity[i] > p.xSolution.length ? p.xSolution.length : p.xSolution[i] + p.pVelocity[i];
		}
		
	}
        private double generateFitnessValue(double[] currentSolution){		
		
		int prevTrashDump = 0; // since we will be starting from the depot which has node 0
		double fitnessSum = 0;
				
		//return the value of objective function
		for(int i=0; i<currentSolution.length; i++){
			int v = (int) Math.round(currentSolution[i]);
		    fitnessSum += distanceMatrix[prevTrashDump][v];
		    prevTrashDump = v;
		}
		
		fitnessSum += distanceMatrix[prevTrashDump][0]; // add distance back to the depot
		
		return fitnessSum;
	}

}

public class Swarm {
     public static boolean obj; 
    double[] gBest;
	double gFitnessValue;
	double[] gBestVelocity;
	Particle[] particles;		
	double[][] distanceMatrix;

	public Swarm(int noOfParticles, DistributionModel dm){
		obj = false;
		this.distanceMatrix = dm.getDistanceMatrix();	
		
		int solutionLength = dm.getNoOfTrashDumps();
		
		// define the possible solution scope
		int[] possibleSolution = new int[solutionLength];	
		
		for(int i=0; i<solutionLength; i++){
			possibleSolution[i] = i+1;
		}
		
		// initialize the Swarm Particles
		this.particles = new Particle[noOfParticles];
		
		for(int i=0; i<noOfParticles; i++){
			Optimizer.shuffleArray(possibleSolution);
			particles[i] = new Particle(possibleSolution);
			particles[i].xFitnessValue = generateFitnessValue(particles[i].xSolution);
			particles[i].pBestValue    = generateFitnessValue(particles[i].pBest);			
		}
		
		//find global best	
		gBest = new double[solutionLength];	
		gBestVelocity = new double[solutionLength];
		gFitnessValue = Double.MAX_VALUE;
		findGlobalBest();
		
	}
	
	private void findGlobalBest(){	
		for(Particle p: particles){
			if(p.xFitnessValue < gFitnessValue){
				gFitnessValue = p.xFitnessValue;
				gBest = p.pBest;
				gBestVelocity = p.pBestVelocity;
			}				
		}		
	}

	private double generateFitnessValue(double[] currentSolution){		
		
		int prevTrashDump = 0; // since we will be starting from the depot which has node 0
		double fitnessSum = 0;
				
		//return the value of objective function
		for(int i=0; i<currentSolution.length; i++){
			int v = (int) Math.round(currentSolution[i]);
		    fitnessSum += distanceMatrix[prevTrashDump][v];
		    prevTrashDump = v;
		}
		
		fitnessSum += distanceMatrix[prevTrashDump][0]; // add distance back to the depot
		
		return fitnessSum;
	}
        public void optimizeSolutions()  throws InterruptedException{
                int i=0;
		for(Particle p:particles){
                    TimerTask task = new Helper(p,distanceMatrix,gBest,particles.length); 
                    task.run();
                    i++;
//                    if(i==particles.length){
//                        while(task.geti()==i){
//                        
//                        }
//                    }
                }
                while(obj==false){
                    System.out.println("abc");
                }
//                synchronized(obj) 
//                { 
//                    //this thread waits until i reaches 4 
//                    obj.wait(); 
//                    
//                } 
                findGlobalBest();
//		for(Particle p: particles){
//			
//			// find the new velocity
//			updateVelocity(p);			
//			
//			// find new solution
//			updateSolution(p);
//			
//			// update the fitness value of the particles
//			p.xFitnessValue = generateFitnessValue(p.xSolution);
//			
//			// update pBest of the particle
//			if(p.xFitnessValue < p.pBestValue){
//				p.pBest = p.xSolution;
//				p.pBestValue = p.xFitnessValue;
//				p.pBestVelocity = p.pVelocity;
//			}
//		
//		}
//		
//		//update the gBest after this one iteration
//		findGlobalBest();
		
		
	}

	
	public void updateVelocity(Particle p){
		
		double w = 0.6;
		
		double o1 = 0.2;
		double b1 = 0.3;
		
		double o2 = 0.2;
		double b2 = 0.5;
		
		double[] newV = new double[p.pVelocity.length];
		
		for(int i=0; i<newV.length; i++){			
			newV[i] = w*p.pVelocity[i] + (o1*b1*(p.pBest[i]-p.xSolution[i])) + (o2*b2*(gBest[i] - p.xSolution[i])); 			
		}
		
		p.pVelocity = newV;
		
	}
	
	public void updateSolution(Particle p){
		
		for(int i=0; i<p.xSolution.length; i++){
			p.xSolution[i] = p.xSolution[i] + p.pVelocity[i] > p.xSolution.length ? p.xSolution.length : p.xSolution[i] + p.pVelocity[i];
		}
		
	}
	
	public int[] decodeOptimalSolution(){
		
		System.out.println("gFitnessValue=" + gFitnessValue);
		System.out.println("gBest="+Arrays.toString(gBest));		
		
		Map<Double, List<Integer>> indicies = new HashMap<>();
		
		for(int i=0; i<gBest.length ; i++){
			if(indicies.get(gBest[i])== null)
				indicies.put(gBest[i], new ArrayList<Integer>());
			
			indicies.get(gBest[i]).add(i);
		}
		
		Arrays.sort(gBest);
		
		int[] optimalRoute = new int[gBest.length];
		
		for(int i=0; i<optimalRoute.length;i++){
			if(indicies.get(gBest[i]).size() > 1){
				// find the lowest velocity and add that first				
				int ii = i;
				for(int k=0; k<indicies.get(gBest[ii]).size(); k++){					
					optimalRoute[i] = indicies.get(gBest[ii]).get(k) + 1;
					i++;
				}				
				
			}else			
			 optimalRoute[i] = indicies.get(gBest[i]).get(0) + 1;
		}
		
		return optimalRoute;
	}
	
	public void printSwarmDetails(){
		
		
		System.out.println("No of Particles : " + particles.length);
		
		
		System.out.println("Particle Details : ");
		for(Particle p: particles)
			System.out.println(p);
		
		System.out.println("Global   [gBest="+Arrays.toString(gBest)+", gFitnessValue=" + gFitnessValue+"]");
				
	}
	
	public void printIterationResults(int t, Map<String, Map<Double, Double>> particleProgress){
		System.out.print(t+ " \t\t");
		int pNo = 1;
		for(Particle p: particles){
			if(particleProgress.get("p"+pNo) == null)
				particleProgress.put("p"+pNo, new HashMap<Double, Double>());
			
			particleProgress.get("p"+pNo).put((double) t, p.pBestValue);			
			System.out.print(p.xFitnessValue + "\t" + p.pBestValue + "\t\t");
			pNo++;
		}
		System.out.println(gFitnessValue);
	}

	public double[] getgBest() {
		return gBest;
	}

	public void setgBest(double[] gBest) {
		this.gBest = gBest;
	}

	public Particle[] getParticles() {
		return particles;
	}

	public void setParticles(Particle[] particles) {
		this.particles = particles;
	}

	public double getgFitnessValue() {
		return gFitnessValue;
	}

	public void setgFitnessValue(double gFitnessValue) {
		this.gFitnessValue = gFitnessValue;
	}
    
}
