/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PSO_Package;

import java.util.Random;

/**
 *
 * @author Preetham Reddy
 */
public class Particle {
    double[] xSolution;
    double xFitnessValue;
    double[] pBest;
    double pBestValue;
    double[] pBestVelocity;
    double[] pVelocity;
    
    public Particle(){
        
    }
    
    
    public void setRandomVelcities(int n) {
        this.pVelocity = new double[n];
        for (int i = 0; i < n; i++) {
                this.pVelocity[i] = getRandomVelocity(n);
        }
    }

    private double getRandomVelocity(int upper) {
            int lower = 0;
            return (new Random().nextDouble() * (upper - lower)) + lower;
    }
}
