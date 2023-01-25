/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ciaranlyne.trafficapp.GUIDir.Panels.Dashboards;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import net.ciaranlyne.trafficapp.Count;
import net.ciaranlyne.trafficapp.GUIDir.Models.DashboardModel;

/**
 * File contributors:
 * Aykut Inalan (w1741621)
 */
public class Dashboard2 extends DashboardModel implements ChangeListener,ActionListener{
    private static int Hour = 12;
    private static String Road = "Both";
    private static JSlider slider = new JSlider();
    private static JTextPane textHourPane = new JTextPane();
    private static JComboBox comboBox = new JComboBox();
    private ChartPanel chart1Panel;
    private ChartPanel chart2Panel;
    private DefaultPieDataset pieDataset; 
    private DefaultCategoryDataset barDataset;
    JFreeChart chart1;
    JFreeChart chart2;
    
    public Dashboard2() {
    	
    	/* [Dashboard2.java]
    	 * The purpose of this class is to display my dashboard
    	 * Two types of data is displayed here:
    	 * + Highest, avg, lowest car traffic levels
    	 * + hgvs vs lgvs
    	 */
    	populate();
    	
    	
    	// Creating GUI //
    	this.setLayout(new BorderLayout(0, 0));
        
    	
        JTextPane Title = new JTextPane();
        this.add(Title, BorderLayout.NORTH);
        SimpleAttributeSet attribs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attribs, StyleConstants.ALIGN_CENTER);
        Title.setParagraphAttributes(attribs, true);
        Title.setText("Traffic depending on the hour of the day on major and minor roads.");
        // Sets the specified boolean to indicate whether or not this textPanel should be editable.
        
        Title.setEditable(false);
        Title.getFont().deriveFont(Font.BOLD, Title.getFont().getSize());
        
        // bottomPanel that holds the interactive parts!
        JPanel BottomPanel = new JPanel();
        this.add(BottomPanel, BorderLayout.SOUTH);
        
        JPanel sliderHolder = new JPanel();
        BottomPanel.add(sliderHolder, BorderLayout.WEST);
        
        
        textHourPane.setText("12:00");
        sliderHolder.add(textHourPane);
        
        slider.setPaintTicks(true);
        slider.setSnapToTicks(true);
        slider.setPaintLabels(true);
        slider.setMinorTickSpacing(1);
        slider.setValue(12);
        slider.setMinimum(7);
        slider.setMaximum(18);
        sliderHolder.add(slider);
        JPanel comboBoxHolder = new JPanel();
        BottomPanel.add(comboBoxHolder, BorderLayout.EAST);
        

        String[] RoadMajor = getRoadMajor(); 
        comboBox.setModel(new DefaultComboBoxModel(RoadMajor));
        comboBoxHolder.add(comboBox);
        
        // scrollPane to make sure everything fits inside the pane
        JSplitPane splitPane = new JSplitPane();
        splitPane.setResizeWeight(0.5);
        splitPane.setOneTouchExpandable(true);
        this.add(splitPane, BorderLayout.CENTER);

        
        // left panel self explanatory
        JPanel LeftPanel = new JPanel();
        splitPane.setLeftComponent(LeftPanel);
        
        // Right panel self explanatory
        JPanel RightPanel = new JPanel();
        splitPane.setRightComponent(RightPanel);
        
        
        pieDataset = new DefaultPieDataset();
        barDataset = new DefaultCategoryDataset();
        
        chart1 = ChartFactory.createPieChart("average types at vehicle ", pieDataset, false, true, false);
        chart1Panel = new ChartPanel(chart1);
        LeftPanel.add(chart1Panel);
        
        splitPane.setRightComponent(RightPanel);
        chart2 = ChartFactory.createBarChart("Average & minmum Taffic by vehicle type", "Vehicle Types", "Highest number of vehicles", barDataset, PlotOrientation.VERTICAL, true, true, true);
        chart2Panel = new ChartPanel(chart2);
        RightPanel.add(chart2Panel);
        
        slider.addChangeListener((ChangeListener) this);
        comboBox.addActionListener(this);
        this.setVisible(true);
        CreateDatChart445();
    }
    // Event handlers! //
    @Override
    public void stateChanged(ChangeEvent e) {
    	// Clears dataset \\
    	barDataset.clear();
    	pieDataset.clear();
    	
    	// display time \\
    	textHourPane.setText(String.format("%02d", slider.getValue())+":00");
    	Hour = slider.getValue();
    	CreateDatChart445();
    	
    	// Change made \\
    	chart1.fireChartChanged();
    	chart2.fireChartChanged();
    	
    	this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
    	 barDataset.clear();
    	 pieDataset.clear();
    	 Road = comboBox.getSelectedItem().toString();
    	 CreateDatChart445();
    	 chart1.fireChartChanged();
     	chart2.fireChartChanged();
     	this.setVisible(true);
    }
    // Functionalties //
    private String[] getRoadMajor() {
    	ArrayList<String> roadtypes = new ArrayList<String>();
    	roadtypes.add("Both");
    	
    	for (Count count : trafficData) { // incase more 
			String roadName = trafficCountPoints.get(count.getCountPointID()).getRoadType();
            if (!roadtypes.contains(roadName)){
            	roadtypes.add(roadName);
            	//System.out.println(roadName);
            }
    		
		
    	}
    	return roadtypes.toArray(new String[0]);
    	
    }
    private void CreateDatChart445() {
    	// this generates the chart
    		
    	boolean gotvalue = false;
    	int totalCounts = 0;
        int totalCarsTaxis = 0;
        int totalBuses = 0;
        int totalLgvs = 0;
        int totalHgvs = 0;
        int total2WheelMotor = 0;
        int totalPedalCycle = 0;
        
        //Creating arraylist to hold the respective data
        ArrayList<Integer> carsTaxis = new ArrayList<>();
        ArrayList<Integer> Buses = new ArrayList<>();
        ArrayList<Integer> Lgvs = new ArrayList<>();
        ArrayList<Integer> Hgvs = new ArrayList<>();
        ArrayList<Integer> WheelMotor = new ArrayList<>();
        ArrayList<Integer> PedalCycle = new ArrayList<>();
        
        // for loop to loop through Count.java
    	for (Count count : trafficData) {    
            if (Road.equals("Both")){
            	// if Both is selected for Road Type
            	if (count.getCountData().getHour() == Hour) {
            		// collecting data if hour chosen matches
            		totalCounts++;
            		gotvalue = true;
            		
            		totalCarsTaxis += count.getCountData().getCarsAndTaxis();
                    totalBuses += count.getCountData().getBusesAndCoaches();
                    totalLgvs += count.getCountData().getLgvs();
                    totalHgvs += count.getCountData().getAllHgvs();
                    total2WheelMotor += count.getCountData().getTwoWheeldedMotorVehicles();
                    totalPedalCycle += count.getCountData().getPedalCycles();
                    
                    carsTaxis.add(count.getCountData().getCarsAndTaxis());
                    Buses.add(count.getCountData().getBusesAndCoaches());
                    Lgvs.add(count.getCountData().getLgvs());
                    Hgvs.add(count.getCountData().getAllHgvs());
                    WheelMotor.add(count.getCountData().getTwoWheeldedMotorVehicles());
                    PedalCycle.add(count.getCountData().getPedalCycles());
                }
            }else {
            	if (count.getCountData().getHour() == Hour && Road.equals(trafficCountPoints.get(count.getCountPointID()).getRoadType())) {
            		// collecting data if hour chosen matches & Road Type matches
            		totalCounts++;
            		gotvalue = true;
            		totalCarsTaxis += count.getCountData().getCarsAndTaxis();
                    totalBuses += count.getCountData().getBusesAndCoaches();
                    totalLgvs += count.getCountData().getLgvs();
                    totalHgvs += count.getCountData().getAllHgvs();
                    total2WheelMotor += count.getCountData().getTwoWheeldedMotorVehicles();
                    totalPedalCycle += count.getCountData().getPedalCycles();
                    
                    carsTaxis.add(count.getCountData().getCarsAndTaxis());
                    Buses.add(count.getCountData().getBusesAndCoaches());
                    Lgvs.add(count.getCountData().getLgvs());
                    Hgvs.add(count.getCountData().getAllHgvs());
                    WheelMotor.add(count.getCountData().getTwoWheeldedMotorVehicles());
                    PedalCycle.add(count.getCountData().getPedalCycles());
                }
            }
            
    	}
    	// ReGenerating the names of the Charts! \\
    	chart1.setTitle("lgvs vs hgvs (Hour:"+Hour+" | Road Type: "+Road+")");
        chart2.setTitle("max, avg, & min by vehicle type (Hour:"+Hour+" | Road Type: "+Road+") ");
    	if (gotvalue) {// a Extra check to make sure the data actually exists
    		// Generating PieChart \\
            pieDataset.setValue("Light Goods Vehicles", totalLgvs / totalCounts);
            pieDataset.setValue("Heavy Goods Vehicles", totalHgvs / totalCounts);
            
            // Generating BarChart \\
            barDataset.addValue(Collections.max(carsTaxis), "Cars & Taxis", "Max");
    		barDataset.addValue(Collections.max(Buses), "Buses & Coaches", "Max");
    		barDataset.addValue(Collections.max(Lgvs), "Light Goods Vehicles", "Max");
    		barDataset.addValue(Collections.max(Hgvs),"Heavy Goods Vehicles", "Max");
    		barDataset.addValue(Collections.max(WheelMotor),"Motorbikes", "Max");
    		barDataset.addValue(Collections.max(PedalCycle),"Cycles", "Max");
            
    		barDataset.addValue(totalCarsTaxis /totalCounts, "Cars & Taxis", "Average");
    		barDataset.addValue(totalBuses / totalCounts, "Buses & Coaches", "Average");
    		barDataset.addValue(totalLgvs / totalCounts, "Light Goods Vehicles", "Average");
    		barDataset.addValue(totalHgvs / totalCounts,"Heavy Goods Vehicles", "Average");
    		barDataset.addValue(total2WheelMotor / totalCounts,"Motorbikes", "Average");
    		barDataset.addValue(totalPedalCycle / totalCounts,"Cycles", "Average");
    		
    		barDataset.addValue(Collections.min(carsTaxis), "Cars & Taxis", "Minimum");
    		barDataset.addValue(Collections.min(Buses), "Buses & Coaches", "Minimum");
    		barDataset.addValue(Collections.min(Lgvs), "Light Goods Vehicles", "Minimum");
    		barDataset.addValue(Collections.min(Hgvs),"Heavy Goods Vehicles", "Minimum");
    		barDataset.addValue(Collections.min(WheelMotor),"Motorbikes", "Minimum");
    		barDataset.addValue(Collections.min(PedalCycle),"Cycles", "Minimum");
    		
        	
    	}else {
    		// if no data exists set the graphs to 0
    		pieDataset.setValue("Cars & Taxis", 0);
            pieDataset.setValue("Buses & Coaches", 0);
            pieDataset.setValue("Light Goods Vehicles", 0);
            pieDataset.setValue("Heavy Goods Vehicles",0);
            pieDataset.setValue("Motorbikes", 0);
            pieDataset.setValue("Cycles", 0);
    		
    		
    		barDataset.addValue(0, "Cars & Taxis", "");
    		barDataset.addValue(0, "Buses & Coaches", "");
    		barDataset.addValue(0, "Light Goods Vehicles", "");
    		barDataset.addValue(0,"Heavy Goods Vehicles", "");
    		barDataset.addValue(0,"Motorbikes", "Average");
    		barDataset.addValue(0,"Cycles", "");
    	}
    	
    }
}
