package vccontroller.ui;
import common.model.User;
import common.ui.WelcomePage;
import job.ui.JobOwnerPage;
import vccontroller.model.JobsCache;
import vehicle.ui.vehicle_ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ControllerPage extends JFrame {
    User user = User.getInstance();
    JobsCache jobsCache = JobsCache.getInstance();

    public ControllerPage()  {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200,800);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Create boxes
        for (int i = 0; i < jobsCache.length(); i++) {
            JPanel box = new JPanel();
            box.setPreferredSize(new Dimension(300, 80));  // width, height
            box.setMaximumSize(new Dimension(250, 180)); // makes all boxes fill width
            box.setBackground(new Color(160 , 211, 255));
            box.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

            JLabel title = new JLabel("Box " + i + 1);
            title.setFont(new Font("Arial", Font.BOLD, 16));

            JLabel jobName = new JLabel(jobsCache.getJob(i).getJobOwnerName());
            JLabel jobDuration = new JLabel(String.valueOf(jobsCache.getJob(i).getDuration()));
            JLabel jobCompletionTime = new JLabel(String.valueOf(jobsCache.getJob(i).getCompletionTime()));

            JButton Accept = new JButton("Accept");
            JButton Reject = new JButton("Reject");
            Accept.setFont(new Font("Arial",Font.BOLD,16));
            Reject.setFont(new Font("Arial",Font.BOLD,16));
            Accept.setOpaque(true);
            Accept.setContentAreaFilled(true);
            Reject.setOpaque(true);
            Reject.setContentAreaFilled(true);
            Reject.setBackground(new Color(255,53,43));
            Accept.setBackground(new Color(8,191,51));
            Accept.setBorderPainted(false);
            Reject.setBorderPainted(false);
            Accept.setFocusPainted(false);
            Reject.setFocusPainted(false);
            box.add(title, BorderLayout.NORTH);
            box.add(jobName, BorderLayout.CENTER);
            box.add(jobDuration, BorderLayout.CENTER);
            box.add(jobCompletionTime, BorderLayout.CENTER);
            box.add(Accept,BorderLayout.SOUTH);
            box.add(Reject,BorderLayout.SOUTH);
            // Label inside the box

            // Add margin between boxes
            mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            mainPanel.add(box);
        }



    setContentPane(mainPanel);
        setVisible(true);
   }
   public static void main (String[] args){

       new ControllerPage().setVisible(true);
   }
}

        
