/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oceanclean;

import java.util.Random;

/**
 *
 * @author Ayesh Reddy
 */
public class DistributionModel {
    int noOfTrashDumps;
    int noOfShips;

    double[][] distanceMatrix;
    TrashDump[] trashDumps;
    Ship[] ships;

    private static Random rand = new Random();
    
    
    
    public DistributionModel(int trashDumpCount, int[] shipCapacity, int shipCount,int msd, int msr){	
		noOfTrashDumps = trashDumpCount;
		noOfShips = shipCount;
		distanceMatrix = new double[trashDumpCount+1][trashDumpCount+1];
		trashDumps = new TrashDump[trashDumpCount];
		ships = new Ship[shipCount];
		
		//initialize the distance between trashDumps and trashDump demands 
		for(int i=0; i<noOfTrashDumps+1; i++){
			for(int j=i; j<noOfTrashDumps+1; j++){
				if(i==j)
					distanceMatrix[i][j] = 0;
				else
					distanceMatrix[i][j] = distanceMatrix[j][i] = getRandomDistance(50);
			}			
		}
		
		for(int i=0; i<noOfTrashDumps; i++){
			int demand = getRandomTrashQuantity(msd);
			int rec = getRandomTrashQuantity(msr);
			if( rec > demand){
			    int temp = demand;
				demand = rec;
				rec = temp;
			}
			else if(rec == demand)
				rec--;
			trashDumps[i] = new TrashDump(demand, rec);
		}
		
		//initialize capacity of each Ship
		for(int i=0; i<noOfShips; i++){
			ships[i] = new Ship(shipCapacity[i]);
		}
    }
    
    public void printModelDetails(){
		
		System.out.println("No Of TrashDumps : " + noOfTrashDumps);		
		System.out.println("TrashDump Details : ");
		for(TrashDump s: trashDumps)
			System.out.println(s);
		System.out.println();
		
		System.out.println("No Of Ships : " + noOfShips);
		for(Ship v: ships)
			System.out.println(v);
		System.out.println();
		
		System.out.println("Distance Matrix : ");
		System.out.print("\tDepot\t");
		for(int k=0; k<trashDumps.length;k++)  
			System.out.print("TrashDump"+(k+1)+"\t");
		System.out.println();
		
		for(int i=0; i<distanceMatrix.length; i++){
			System.out.print((i==0?"Depot":"TrashDump"+i)  + "\t");
			for(int j=0; j<distanceMatrix.length; j++){
				System.out.print(distanceMatrix[i][j] + "\t");  	
			}
			System.out.println();
		}
		
		
	}
    
    private int getRandomDistance(int max){
		return rand.nextInt(max) + 1;		
        }
	
    private int getRandomTrashQuantity(int max){
            return rand.nextInt(max) + 1;		
    }
    public int getNoOfTrashDumps() {
        return noOfTrashDumps;
    }

    public void setNoOfTrashDumps(int noOfTrashDumps) {
        this.noOfTrashDumps = noOfTrashDumps;
    }

    public int getNoOfShips() {
        return noOfShips;
    }

    public void setNoOfShips(int noOfShips) {
        this.noOfShips = noOfShips;
    }

    public double[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    public void setDistanceMatrix(double[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    public TrashDump[] getTrashDumps() {
        return trashDumps;
    }

    public void setTrashDumps(TrashDump[] trashDumps) {
        this.trashDumps = trashDumps;
    }

    public Ship[] getShips() {
        return ships;
    }

    public void setShips(Ship[] ships) {
        this.ships = ships;
    }

}
