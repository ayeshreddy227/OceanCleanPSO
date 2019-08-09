/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package oceanclean;

/**
 *
 * @author Ayesh Reddy
 */
public class TrashDump {
    	int trashQuantity;
	int rec;
	public TrashDump(int trashQuantity){
		this.trashQuantity = trashQuantity;
	}
	public TrashDump(int trashQuantity, int rec){
		this.trashQuantity = trashQuantity;
                this.rec = rec;
	}
	@Override
	public String toString() {
		return "TrashDump [trashQuantity=" + trashQuantity + "]";
	}
}
