/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ciaranlyne.trafficapp.GUIDir.Panels.Dashboards;

import net.ciaranlyne.trafficapp.GUIDir.Models.DashboardModel;
import net.ciaranlyne.trafficapp.Count;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.EventQueue;

/**
 *
 * @author ciara
 */
public class Dashboard5 extends DashboardModel{
    
    public Dashboard5() {
        designer = "Tevin";
        populate(); //This function/class is executed when loaded to extract and collect data.//
        

        DefaultCategoryDataset datasetNo5 = new DefaultCategoryDataset();
        
        datasetNo5.setValue(384086, "Total No Vehicles", "2000");
        datasetNo5.setValue(457355, "Total No Vehicles", "2001");
        datasetNo5.setValue(173165, "Total No Vehicles", "2002");
        datasetNo5.setValue(549316, "Total No Vehicles", "2003");
        datasetNo5.setValue(401950, "Total No Vehicles", "2004");
        datasetNo5.setValue(382073, "Total No Vehicles", "2005");
        datasetNo5.setValue(458858, "Total No Vehicles", "2006");
        datasetNo5.setValue(418688, "Total No Vehicles", "2007");
        datasetNo5.setValue(471472, "Total No Vehicles", "2008");
        datasetNo5.setValue(525501, "Total No Vehicles", "2009");
        datasetNo5.setValue(396163, "Total No Vehicles", "2010");
        datasetNo5.setValue(294766, "Total No Vehicles", "2011");
        datasetNo5.setValue(329259, "Total No Vehicles", "2012");
        datasetNo5.setValue(388805, "Total No Vehicles", "2013");
        datasetNo5.setValue(297767, "Total No Vehicles", "2014");
        datasetNo5.setValue(186834, "Total No Vehicles", "2015");
        datasetNo5.setValue(214302, "Total No Vehicles", "2016");
        datasetNo5.setValue(439609, "Total No Vehicles", "2017");
        datasetNo5.setValue(71048, "Total No Vehicles", "2018");
        
        JFreeChart barChart = ChartFactory.createBarChart(
            "Vehicles travelled in Bournmouth in 2 Decades",
            "",
            "Total No of Vehicles",
            datasetNo5,
            PlotOrientation.VERTICAL, false, true, false);
            
        ChartPanel barChartPane = new ChartPanel(barChart);
        barChartPane.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        barChartPane.setBackground(Color.GRAY);
        add(barChartPane);
        
    }
            
}
