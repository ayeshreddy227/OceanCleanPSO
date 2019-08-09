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
import oceanclean.DistributionModel;
public class Swarm {
    
    double[] gBest;
	double gFitnessValue;
	double[] gBestVelocity;
	Particle[] particles;		
	double[][] distanceMatrix;

	public Swarm(int noOfParticles, DistributionModel dm){
		
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
		
		int prevStore = 0; // since we will be starting from the depot which has node 0
		double fitnessSum = 0;
				
		//return the value of objective function
		for(int i=0; i<currentSolution.length; i++){
			int v = (int) Math.round(currentSolution[i]);
		    fitnessSum += distanceMatrix[prevStore][v];
		    prevStore = v;
		}
		
		fitnessSum += distanceMatrix[prevStore][0]; // add distance back to the depot
		
		return fitnessSum;
	}
    
}
