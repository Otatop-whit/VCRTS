package job.ui;

import job.model.JobOwner;
import job.service.JobOwnerService;
import job.service.JobOwnerServiceImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JobOwnerPage extends JFrame {

    //Job Owner Dashboard Intro Window
    public JobOwnerPage()
    {
        setTitle("Job Dashboard");
        setSize(720,480);
        setLocationRelativeTo(null);
        JLabel dashIntro = new JLabel("What would you like to do?", SwingConstants.CENTER);
        JButton submitJob = new JButton("Submit New Job");
        JButton viewJob = new JButton("View Current/Previous Jobs");

        submitJob.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                new SubmitJobFrame().setVisible(true);
            }
        });

        viewJob.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                new ViewJobFrame().setVisible(true);
            }
        });

        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(dashIntro);
        panel.add(submitJob);
        panel.add(viewJob);
        add(panel);
    }

    //Submit New Job
    static class SubmitJobFrame extends JFrame {
        private JTextField id = new JTextField();
        private JTextField name = new JTextField();
        private JTextField duration = new JTextField();
        private JTextField deadline = new JTextField();
        private JTextField reqs = new JTextField();

        private JobOwnerService service = new JobOwnerServiceImpl();

        SubmitJobFrame() {
            setTitle("Submit New Job");
            setSize(650, 500);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout(10,10));

        JLabel idLabel = new JLabel("Job Owner ID:");
        JLabel nameLabel = new JLabel("Job Owner Name:");
        JLabel durationLabel = new JLabel("Approximate Job Duration:");
        JLabel deadlineLabel = new JLabel("Job Deadline:");
        JLabel reqsLabel = new JLabel("Job Requirements (If needed):");

        JPanel panel = new JPanel(new GridLayout(5,2));
        panel.add(idLabel);
        panel.add(id);
        panel.add(nameLabel);
        panel.add(name);
        panel.add(durationLabel);
        panel.add(duration);
        panel.add(deadlineLabel);
        panel.add(deadline);
        panel.add(reqsLabel);
        panel.add(reqs);

        JButton submitJob = new JButton("Submit");

        submitJob.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int enteredId = Integer.parseInt(id.getText());
                String enteredName = name.getText();
                String enteredDuration = duration.getText();
                String enteredDeadline = deadline.getText();
                String enteredReqs = reqs.getText();

                JobOwner jo = new JobOwner();
                jo.setId(enteredId);
                jo.setJobOwnerName(enteredName); 
                jo.setApproximateJobDuration(enteredDuration);
                jo.setJobDeadline(enteredDeadline);
                jo.setRequirements(enteredReqs);
                service.addJobOwner(jo);
            }
        });

        add(panel, BorderLayout.CENTER);
        add(submitJob, BorderLayout.SOUTH);

        }

    }

    //View Jobs
    static class ViewJobFrame extends JFrame {

        private JTextArea area = new JTextArea();

        ViewJobFrame() {

            setTitle("Current/Previous Jobs");
            setSize(650, 500);
            setLocationRelativeTo(null);
            add(new JScrollPane(area));
            loadFile();
        }

        private void loadFile() {
            try {
                byte[] data = Files.readAllBytes(Paths.get("src/job/repo/JobOwnerData.txt"));
                area.setText(new String(data));
            } catch (java.io.IOException e) {
                area.setText("");
            }
         
        }

    }

}



