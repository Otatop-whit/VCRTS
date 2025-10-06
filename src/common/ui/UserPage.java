package common.ui;

import job.ui.JobOwnerPage;

import javax.swing.*;

import vehicle.ui.vehicle_ui;

import java.awt.*;

public class UserPage {
    public UserPage(){
        JFrame frame = new JFrame("My First JFrame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,400);
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("User");
        JMenuItem logoutItem = new JMenuItem("Log-Out");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(logoutItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        JLabel label = new JLabel("VCRT Project Label thing");
        JPanel panel= new JPanel();
        JButton JobOwnerbutton = new JButton("JobOwner");
        JButton VehicleOwnerbutton = new JButton("VehicleOwner");
        panel.add(JobOwnerbutton);
        panel.add(VehicleOwnerbutton);
        JobOwnerbutton.addActionListener(e -> {
            frame.dispose();
            new JobOwnerPage().setVisible(true);
        });
        VehicleOwnerbutton.addActionListener(e -> {
            frame.dispose();
            new vehicle_ui();
        });
        frame.setLayout(new BorderLayout());
        logoutItem.addActionListener(e -> {
            frame.dispose();
            new WelcomePage();

        });
        frame.setJMenuBar(menuBar);
        frame.add(label, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);


    }
}
