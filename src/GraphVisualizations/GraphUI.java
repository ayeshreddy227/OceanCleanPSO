/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GraphVisualizations;


import java.util.List;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

/**
 *
 * @author Ayesh Reddy
 */
public class GraphUI {
    private String styleSheet =
	        	"graph {fill-mode: image-scaled-ratio-max; fill-image: url('https://ak9.picdn.net/shutterstock/videos/32493949/thumb/1.jpg');}"
            
            +"node {fill-mode: image-scaled;fill-image: url('https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRXeWTHqmhha0af3mJ0FW2xaBzdha2v8NoOgk7qSthqGRYFoC_BUA');  text-color: yellow; size: 55px; text-size: 15px;} "+
	            "node#0 { fill-mode: image-scaled;fill-image: url('https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRt_lIcVT6RgYGll4ztJcFj51DZLWObBiWM54fB8KLpDgmxgxW-CQ'); size: 100px; } "+
	        	"edge.marked {fill-color: red;}"+		
	        	"edge {text-size: 20px; text-alignment: above; size: 3px; fill-color: black; arrow-shape: arrow; arrow-size: 8px;}";
	public void displayGraph(String gName, List<Integer> edges){
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		
		Graph graph = new MultiGraph(gName);
		graph.addAttribute("ui.stylesheet", styleSheet);
		//graph.addAttribute("ui.label", gName);
		graph.setAutoCreate(true);
        graph.setStrict(false);        
        graph.display();
       	
        
		for(int e=0; e<edges.size()-1;e++){					
			graph.addEdge(Integer.toString(e), Integer.toString(edges.get(e)), Integer.toString(edges.get(e+1)), true);
		}
		
		for (Node node : graph.getNodeSet())
	        node.addAttribute("ui.label", node.getId());
	    
		
		explore(graph);		
		
	}
	
	private void explore(Graph graph){
		//int tripCount = Integer.parseInt(graph.getId().split(",")[1].split(":")[1]);
		int t =1;
		int i=0;
		for(; i< graph.getEdgeCount(); i++){
			graph.getEdge(String.valueOf(i)).addAttribute("ui.class", "marked");
			if(graph.getEdge(String.valueOf(i)).getNode1().getId().equals("0"))
				graph.getEdge(String.valueOf(i)).addAttribute("ui.label", "Trip:"+t++);
			sleep();
		}
		graph.getEdge(String.valueOf(i-1)).addAttribute("ui.label", graph.getId());
	}
	
	private void sleep() {
        try { Thread.sleep(500); } catch (Exception e) {}
    }
}
