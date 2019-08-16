/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oceanclean;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.jfree.ui.RefineryUtilities;
/**
 *
 * @author root
 */
import PSO_Package.Swarm;
import GraphVisualizations.GraphUI;
import GraphVisualizations.ParticleLineGraph;
import org.graphstream.ui.util.swing.ImageCache;
public class Simulator {

    /**
     * @param args the command line arguments
     */
    
    
   
    public final static int S = 15;  // no of trashDumps
    public final static int[] Q = {12,15,20, 25, 19}; // capacity of each ship
    public final static int K = 5;  // no of ships

    private final static int MAX_TRASHDUMP_DEMAND = 10;
    private final static int MAX_TRASHDUMP_RECYCLABLES = 8;

    public final static int N = 50;  // no of particles in swarm
    public final static int T = 50;  // iteration count
    public static void main(String[] args)  throws InterruptedException{
//           String URL_IMAGE = ImageCache.class.getClassLoader().getResource("/Users/imperio2494/OceanCleanPSO/src/oceanclean/trash.png").toString();
        // TODO code application logic here
        System.out.println("---------------------------------------------------------");	
		System.out.println("Ocean Cleaning Model");
		System.out.println("---------------------------------------------------------");		
		
		//Design the distribution model for the problem
		DistributionModel dm = new DistributionModel(S, Q, K, MAX_TRASHDUMP_DEMAND, MAX_TRASHDUMP_RECYCLABLES);
		dm.printModelDetails();
		System.out.println("---------------------------------------------------------");
		
		System.out.println("Swarm Model");
		System.out.println("---------------------------------------------------------");
		//Initialize the swarm for the distribution model
		Swarm swarm = new Swarm(N, dm);
		swarm.printSwarmDetails();
		System.out.println("---------------------------------------------------------");
		
		System.out.println("Iterations Details");
		System.out.println("---------------------------------------------------------");
		Map<String, Map<Double, Double>> particleProgress = new HashMap<String, Map<Double,Double>>();
		
		//print iteration 0 results
		System.out.print("Iteration\t");
		for(int i=0; i<swarm.getParticles().length; i++)
			System.out.print("f(x:"+(i+1)+") f(pBest:"+(i+1)+")\t");		
		
		System.out.println("f(gBest)");
		swarm.printIterationResults(0, particleProgress);
		
				
		//Optimize the solution and return the best solution after the iterations terminate
		for(int t=1; t<=T;t++){
			swarm.optimizeSolutions();	
			swarm.printIterationResults(t, particleProgress);
//                        break;
		}
		System.out.println("---------------------------------------------------------");
		
               
		ParticleLineGraph particleGraph = new ParticleLineGraph("Particles Progress", particleProgress);
		particleGraph.pack();        
//        RefineryUtilities.centerFrameOnScreen(particleGraph);
        particleGraph.setVisible(true);
		
        
        System.out.println("Continue with decoding the best solution... ? Y or N");
        Scanner sc = new Scanner(System.in);
        if(sc.hasNext()){
        	if(sc.nextLine().equals("Y")){
        		
        		System.out.println("Decode gBest Solution");
        		System.out.println("---------------------------------------------------------");
        		//Decode the gBest Solution
        		int[] optimalRoute =  swarm.decodeOptimalSolution();
        		System.out.println("Optimal Route : " + Arrays.toString(optimalRoute) );
        		
        		System.out.println("---------------------------------------------------------");
        		//Print analysis for optimal route for the distribution model
        		System.out.println("Analysis of Optimal Route for dropOff Only: ");
        		System.out.println("---------------------------------------------------------");
        		GraphUI gui = new GraphUI();
        		Map<String, List<Integer>> distributionMap =  dm.analyzeOptimalRoute(optimalRoute);		
        		        		
        		for (Map.Entry<String, List<Integer>> entry : distributionMap.entrySet()) {
        		    System.out.println(entry.getKey()+" -> "+entry.getValue());		    
        			gui.displayGraph("Graph"+entry.getKey(),entry.getValue());
        		}
        		
        		System.out.println("---------------------------------------------------------");        		
        		System.out.println("Analysis of Optimal Route for simultaneously pickup and dropOff: ");
        		System.out.println("---------------------------------------------------------");
//        		distributionMap = dm.analyzeOptimalRouteSimultaneous(optimalRoute);
//        		for (Map.Entry<String, List<Integer>> entry : distributionMap.entrySet()) {
//        		    System.out.println(entry.getKey()+" -> "+entry.getValue());
//        		}
        		
        	}        	
        }
        
        //particleGraph.dispose();
        System.out.println("---------------------------------------------------------");
        System.out.println("PSO implementation for CVRP is done !! :) ");
		sc.close();
		
		
		
    }
    
}