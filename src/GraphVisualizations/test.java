/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphVisualizations;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.SwingWorker;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.standalone.readme.SwingWorkerRealTime;

/**
 *
 * @author Preetham Reddy
 */
public class test {
  static  MySwingWorker mySwingWorker;
  static SwingWrapper<XYChart> sw;
  static XYChart chart;
  static XYDataset dataset;
  static int nop;
  public test(String title, Map<String, Map<Double, Double>> particleProgress, int nop) {
      this.nop = nop;
    this.dataset = createDataset(particleProgress);
    go();
  }
  
  
  private XYDataset createDataset(Map<String, Map<Double, Double>> particleProgress) {
		
		XYSeriesCollection dataset = new XYSeriesCollection();
		
		for (Map.Entry<String, Map<Double, Double>> entry : particleProgress.entrySet()) {
		    //System.out.println(entry.getKey()+" -> "+entry.getValue());		    
			
			XYSeries series = new XYSeries(entry.getKey());
			for(Map.Entry<Double,Double> e: entry.getValue().entrySet()){
				series.add(e.getKey(), e.getValue());				
			}
			
			dataset.addSeries(series);
		}
		


		return dataset;

	}
  
  
  
  
  
  public static void go() {

    // Create Chart
    chart =
        QuickChart.getChart(
            "SwingWorker XChart Real-time Demo",
            "Time",
            "Value",
            "randomWalk",
            new double[] {0},
            new double[] {0});
    chart.getStyler().setLegendVisible(false);
    chart.getStyler().setXAxisTicksVisible(false);

    // Show it
    sw = new SwingWrapper<XYChart>(chart);
    sw.displayChart();

    mySwingWorker = new MySwingWorker();
    mySwingWorker.execute();
  }
    
    private static class MySwingWorker extends SwingWorker<Boolean, double[]> {

    

    public MySwingWorker() {

      
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        int k = 50;
      while (k>0) {

        

        double[] array = new double[nop];
        for(int j = 0; j<50;j++){
            for (int i = 0; i < nop; i++) {
              array[i] = dataset.getYValue(j, i);
            }
            publish(array);

            try {
              Thread.sleep(5);
            } catch (InterruptedException e) {
              // eat it. caught when interrupt is called
              System.out.println("MySwingWorker shut down.");
            }
          }
        k--;
      }
      
      return true;
    }

    @Override
    protected void process(List<double[]> chunks) {

      System.out.println("number of chunks: " + chunks.size());

      double[] mostRecentDataSet = chunks.get(chunks.size() - 1);

      chart.updateXYSeries("randomWalk", null, mostRecentDataSet, null);
      sw.repaintChart();

      long start = System.currentTimeMillis();
      long duration = System.currentTimeMillis() - start;
      try {
        Thread.sleep(40 - duration); // 40 ms ==> 25fps
        // Thread.sleep(400 - duration); // 40 ms ==> 2.5fps
      } catch (InterruptedException e) {
        System.out.println("InterruptedException occurred.");
      }
    }
  }
}
