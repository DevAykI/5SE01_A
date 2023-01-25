/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ciaranlyne.trafficapp.GUIDir.Panels.Dashboards;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import net.ciaranlyne.trafficapp.Count;
import net.ciaranlyne.trafficapp.GUIDir.Models.DashboardModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 * File contributors:
 * Ciaran Lyne (w1725430)
 */
public class Dashboard1 extends DashboardModel implements ActionListener{
    
    //Attributes
    private ChartPanel chart1Panel;
    private ChartPanel chart2Panel;
    private ChartPanel chart3Panel;
    private JPanel chartControlPanel;
    
    JFreeChart chart1;
    JFreeChart chart2;
    JFreeChart chart3;
    
    private JComboBox<String> dayList;
    
    private DefaultPieDataset pieDataset; //pie chart data for total traffic type distribution
    private DefaultCategoryDataset lineDataset; //average cars per hour
    private DefaultCategoryDataset weekDataset; //average hourly vehicles for the whole week
    
    //Constructor
    public Dashboard1() {
        designer = "Ciaran";
        //gather information from database
        populate();

        //grid layout for charts
        this.setLayout(new GridLayout(0, 2));
 
        //drop down menu for day of week
        dayList = new JComboBox<>(new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"});
        
        //init datasets
        pieDataset = new DefaultPieDataset();
        lineDataset = new DefaultCategoryDataset();
        weekDataset = new DefaultCategoryDataset();
        
        //init charts
        chart1 = ChartFactory.createPieChart("Avg. Hourly Vehicles on Major Roads for: Monday", pieDataset, false, true, false);
        chart2 = ChartFactory.createLineChart("Avg. Cars on: Monday", "Hour in Day", "No. of Cars & Taxis", lineDataset, PlotOrientation.VERTICAL, true, true, false);
        chart3 = ChartFactory.createBarChart("Avg. Hourly Total Vehicles on Major Roads in Bournemouth", "Week Day", "", weekDataset, PlotOrientation.VERTICAL, true, true, true);
        
        //default dataset information to monday
        chartGen("Mon");

        //generate panels for charts and drop down menu
        chart1Panel = new ChartPanel(chart1);
        chart3Panel = new ChartPanel(chart3);
        
        chart2Panel = new ChartPanel(chart2);
        
        chartControlPanel = new JPanel();
        
        //set drop down menu to be able to change and listen for change
        dayList.setEditable(false);
        dayList.addActionListener(this);
        
        chartControlPanel.setLayout(new BorderLayout());
        chartControlPanel.add(dayList, BorderLayout.CENTER);
        
        //add panels to main dashboard panel
        this.add(chart3Panel);
        this.add(chart1Panel);
        
        this.add(chart2Panel);

        this.add(chartControlPanel);
        
        this.setVisible(true);
    }
    
    /*
    * func: chartGen
    * use: generates datasets for all charts based off variables inherited from DashboardModel
    * params: String dayOfWeek (day of week to generate information for)
    * returns: none
    */
    private void chartGen(String dayOfWeek) {
        int totalCounts = 0;
        
        int totalCarsTaxis = 0;
        int totalBuses = 0;
        int totalLgvs = 0;
        int totalHgvs = 0;
        int total2WheelMotor = 0;
        int totalPedalCycle = 0;
        
        int[][] weekCount = {{0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0}, {0,0}};
        
        int[][] hourlyCarsTaxis = new int[25][2];
        
        for (Count count : trafficData) {    
            
            if (trafficCountPoints.get(count.getCountPointID()).getLocalAuthorityID() == 182 && "Major".equals(trafficCountPoints.get(count.getCountPointID()).getRoadType())) {

                String curDayOfWeek;
                try {
                    Calendar c = Calendar.getInstance();
                    c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(count.getCountData().getCountDate()));
                    
                    switch(c.get(Calendar.DAY_OF_WEEK)) {
                        case 2:
                            curDayOfWeek = "Mon";
                            break;
                        case 3:
                            curDayOfWeek = "Tue";
                            break;
                        case 4:
                            curDayOfWeek = "Wed";
                            break;
                        case 5: 
                            curDayOfWeek = "Thu";
                            break;
                        case 6:
                            curDayOfWeek = "Fri";
                            break;
                        default:
                            continue;
                    }
                    
                    weekCount[c.get(Calendar.DAY_OF_WEEK)][0] += count.getCountData().getAllMotorVehicles();
                    weekCount[c.get(Calendar.DAY_OF_WEEK)][1] += 1;
                    
                } catch (ParseException e) {
                    continue;
                }
                
                if(curDayOfWeek.equals(dayOfWeek)) {
                    totalCounts++;
                    
                    //Data for pie chart
                    totalCarsTaxis += count.getCountData().getCarsAndTaxis();
                    totalBuses += count.getCountData().getBusesAndCoaches();
                    totalLgvs += count.getCountData().getLgvs();
                    totalHgvs += count.getCountData().getAllHgvs();
                    total2WheelMotor += count.getCountData().getTwoWheeldedMotorVehicles();
                    totalPedalCycle += count.getCountData().getPedalCycles();
                    
                    hourlyCarsTaxis[count.getCountData().getHour()][0] += count.getCountData().getCarsAndTaxis();
                    hourlyCarsTaxis[count.getCountData().getHour()][1] += 1;
                }
                
            }
        }
        
        pieDataset.setValue("Cars & Taxis", totalCarsTaxis / totalCounts);
        pieDataset.setValue("Buses & Coaches", totalBuses / totalCounts);
        pieDataset.setValue("Light Goods Vehicles", totalLgvs / totalCounts);
        pieDataset.setValue("Heavy Goods Vehicles", totalHgvs / totalCounts);
        pieDataset.setValue("Motorbikes", total2WheelMotor / totalCounts);
        pieDataset.setValue("Cycles", totalPedalCycle / totalCounts);
        
        weekDataset.addValue(weekCount[2][0] / weekCount[2][1], "Mon", "");
        weekDataset.addValue(weekCount[3][0] / weekCount[3][1], "Tue", "");
        weekDataset.addValue(weekCount[4][0] / weekCount[4][1], "Wed", "");
        weekDataset.addValue(weekCount[5][0] / weekCount[5][1], "Thu", "");
        weekDataset.addValue(weekCount[6][0] / weekCount[6][1], "Fri", "");
        
        for(int i = 1; i < hourlyCarsTaxis.length; i++) {
            if(hourlyCarsTaxis[i][1] != 0) {
                double avg = hourlyCarsTaxis[i][0] / hourlyCarsTaxis[i][1];
                
                lineDataset.addValue(avg, "Avg. Cars (Major Roads)", String.valueOf(i));
            }
        }
    }

    /*
    * func: actionPerformed
    * use: override for ActionListener. Used in this case when user changes drop down menu day they wish chart data to be displayed for
    * params: ActionEvent e
    * returns: none
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        String dayLong = dayList.getSelectedItem().toString();
        String dayShort = dayLong.substring(0, Math.min(dayLong.length(), 3));
        
        chart1.setTitle("Avg. Hourly Vehicles on Major Roads for: " + dayLong);
        chart2.setTitle("Avg. Cars on: " + dayLong);
        
        pieDataset.clear();
        lineDataset.clear();
        weekDataset.clear();
        
        chartGen(dayShort);
        
        chart1.fireChartChanged();
        chart2.fireChartChanged();
        chart3.fireChartChanged();
        
        this.setVisible(true);
        
    }
    
}
