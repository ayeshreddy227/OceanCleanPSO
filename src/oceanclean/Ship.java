/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package oceanclean;

/**
 *
 * @author Preetham Reddy
 */
public class Ship {
    int capacity;
	
	public Ship(int cap){
		this.capacity = cap;
	}

	@Override
	public String toString() {
		return "Ship [capacity=" + capacity + "]";
	}
}
