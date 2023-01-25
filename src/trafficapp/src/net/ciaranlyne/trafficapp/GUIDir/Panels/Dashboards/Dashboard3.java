/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ciaranlyne.trafficapp.GUIDir.Panels.Dashboards;

import net.ciaranlyne.trafficapp.Count;
import net.ciaranlyne.trafficapp.GUIDir.Models.DashboardModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Burak Kavus (w1726580)
 */
public class Dashboard3 extends DashboardModel {

    public Dashboard3() { 

        designer = "Burak";
        populate(); //loads all the data.

        DefaultCategoryDataset dataset3 = new DefaultCategoryDataset();
        DefaultCategoryDataset dataset3p2 = new DefaultCategoryDataset();

        for (Count count : trafficData) {
            if ("Major".equals(trafficCountPoints.get(count.getCountPointID()).getRoadType())) {
                String roadName = trafficCountPoints.get(count.getCountPointID()).getRoadName();
                
                //adding data for the charts.
                dataset3.addValue(count.getCountData().getHgvs2RigidAxle(), roadName, "2 Rigid Axles");
                dataset3.addValue(count.getCountData().getHgvs3RigidAxle(), roadName, "3 Rigid Axles");
                dataset3.addValue(count.getCountData().getHgvs4OrMorerigidAxle(), roadName, "4 Rigid Axles");
                dataset3.addValue(count.getCountData().getAllHgvs(), roadName, "All HGVS");
            }
        }
        
        for (Count count : trafficData) {
            if ("Major".equals(trafficCountPoints.get(count.getCountPointID()).getRoadType())) {
                String roadName = trafficCountPoints.get(count.getCountPointID()).getRoadName();
                
                //adding data for the charts.
                dataset3p2.addValue(count.getCountData().getHgvs3Or4ArticulatedAxle(), roadName, "3/4 Artic Axle");
                dataset3p2.addValue(count.getCountData().getHgvs5ArticulatedAxle(), roadName, "5 Artic Axle");
                dataset3p2.addValue(count.getCountData().getHgvs6ArticulatedAxle(), roadName, "6 Artic Axle");
                dataset3p2.addValue(count.getCountData().getAllHgvs(), roadName, "All HGVS");
            }
        }

        //creating the charts using JFreeChart library, LineChart variation. 
        JFreeChart chart3 = ChartFactory.createLineChart("Types of Rigid Axles HGVS on Roads", "Amount of Rigid Axles on Vehicle", "Number of Vehicles", dataset3, PlotOrientation.VERTICAL, true, true, false);
        JFreeChart chart3p2 = ChartFactory.createLineChart("Types of Articulated Axle HGVS on Roads", "Amount of Articulated Axles on Vehicle", "Number of Vehicles", dataset3p2, PlotOrientation.VERTICAL, true, true, false);

        ChartPanel chartPanel3 = new ChartPanel(chart3);
        this.add(chartPanel3);
        //changing chart sizes.
        chartPanel3.setPreferredSize(new java.awt.Dimension(500,260));
        
        ChartPanel chartPanel3p2 = new ChartPanel(chart3p2);
        this.add(chartPanel3p2);
        chartPanel3p2.setPreferredSize(new java.awt.Dimension(500,260));
    }
}
