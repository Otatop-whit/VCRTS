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

    //Theme of welcome and user page
    private static final Color background = new Color(240, 248, 255);
    private static final Color titlecolor = new Color(25, 50, 120);
    private static final Color buttoncolor = new Color(35, 99, 188);
    private static final Color buttoncolorgreen = new Color(25, 140, 100);
    private static final Font titlefont = new Font("SansSerif", Font.BOLD, 22);
    private static final Font buttonfont = new Font("SansSerif", Font.BOLD, 18);
    private static final Font labelfont = new Font("SansSerif", Font.PLAIN, 16);
    private static final Dimension buttonsize = new Dimension(220, 56);
    
    //Job Owner Dashboard Intro Window
    public JobOwnerPage()
    {
        setTitle("VCRTS — Vehicular Cloud Real Time System");
        setSize(720,480);
        setLocationRelativeTo(null);

        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBackground(background);

        JLabel dashTitle = new JLabel("Job Owner Dashboard");
        dashTitle.setFont(titlefont);
        dashTitle.setForeground(titlecolor);
        dashTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel dashIntro = new JLabel("What would you like to do?", SwingConstants.CENTER);
        dashIntro.setFont(labelfont);
        dashIntro.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Button design
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        JButton submitJob = new JButton("Submit New Job");
        styleButton(submitJob,buttoncolor, buttonfont, buttonsize);

        JButton viewJob = new JButton("View Jobs History");
        styleButton(viewJob,buttoncolorgreen, buttonfont, buttonsize);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        
        buttonPanel.add(submitJob, gbc);
        buttonPanel.add(viewJob, gbc);
        //

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

        root.add(Box.createVerticalStrut(35));
        root.add(dashTitle);
        root.add(Box.createVerticalStrut(75));
        root.add(dashIntro);
        root.add(buttonPanel);
        setContentPane(root);

    }

    //Button styling
    private void styleButton(JButton button, Color baseColor, Font font, Dimension size) {
        button.setFont(font);
        button.setPreferredSize(size);
        button.setFocusPainted(false);
        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor);




            }
        });
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
            setSize(720, 480);
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



