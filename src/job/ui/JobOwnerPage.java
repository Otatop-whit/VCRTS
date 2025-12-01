package job.ui;

import common.model.User;
//import common.service.JobOwnerService;
import job.model.JobOwner;
import job.service.JobOwnerServiceImpl;
import vccontroller.model.JobInfo;
import vccontroller.model.JobReq;
import vccontroller.service.VcControllerServiceImpl;
import common.service.JobNetworkClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

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
        setTitle("VCRTS — Job Owner Dashboard");
        setSize(720,480);
        setLocationRelativeTo(null);

    
        // === Back Arrow in Menu Bar 
        JButton backBtn = new JButton("⟵");
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        backBtn.setFocusPainted(false);
        backBtn.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));
        backBtn.setContentAreaFilled(false);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // When clicked, go back to UserPage
        backBtn.addActionListener(e -> {
            dispose(); // closes current JobOwnerPage frame
            new common.ui.UserPage(); // navigates back to UserPage
        });

        // Add to top menu or a small bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(backBtn);
        menuBar.add(Box.createHorizontalStrut(8)); 
        setJMenuBar(menuBar);


        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBackground(background);

        JLabel dashTitle = new JLabel("Job Owner Dashboard");
        dashTitle.setFont(titlefont);
        dashTitle.setForeground(titlecolor);
        dashTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Button design
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        JButton submitJob = new JButton("Submit New Job");
        styleButton(submitJob,buttoncolor, buttonfont, buttonsize);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        
        buttonPanel.add(submitJob, gbc);
        //

        submitJob.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                new SubmitJobFrame().setVisible(true);
            }
        });

        root.add(Box.createVerticalStrut(35));
        root.add(dashTitle);
        root.add(Box.createVerticalStrut(1));
        root.add(buttonPanel);
        setContentPane(root);
        
        // Footer 
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(235, 240, 250));
        footerPanel.setBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(210, 220, 235))
        );
        footerPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 24));

        JLabel footerText = new JLabel(
                "© 2025 Vehicular Cloud Real Time System  |  All Rights Reserved",
                SwingConstants.CENTER
        );
        footerText.setFont(new Font("SansSerif", Font.PLAIN, 11));
        footerText.setForeground(new Color(90, 90, 90));
        footerPanel.add(footerText, BorderLayout.CENTER);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(background);
        wrapper.add(root, BorderLayout.CENTER);
        wrapper.add(footerPanel, BorderLayout.SOUTH);

        setContentPane(wrapper);
        ((JComponent) getContentPane()).setBorder(
                BorderFactory.createEmptyBorder(12, 12, 28, 12)
        );
        }

    //Button styling
    private static void styleButton(JButton button, Color baseColor, Font font, Dimension size) {
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
                if (button.isEnabled()) {
                    button.setBackground(baseColor.darker());
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(baseColor);
                }




            }
        });
    }

    //Submit New Job
    static class SubmitJobFrame extends JFrame {

        private JTextField jobName = new JTextField();
        private JSpinner deadline;
        private JComboBox<Integer> durationHours;

        private void checkField(JButton submitJob) {
            boolean nameFilled = !jobName.getText().trim().isEmpty();
            boolean deadlineFilled = (deadline != null && deadline.getValue() != null);
            boolean durationFilled = (durationHours != null && durationHours.getSelectedItem() != null);
            boolean filled = nameFilled && deadlineFilled && durationFilled;
            submitJob.setEnabled(filled);
            if (filled) {
                submitJob.setBackground(buttoncolorgreen);
            } else {
                submitJob.setBackground(Color.gray);
            }
        }

        SubmitJobFrame() {
            setTitle("Create New Job");
            setSize(720, 480);
            setLocationRelativeTo(null);

            // Initialize deadline spinner (yyyy-MM-dd)
            SpinnerDateModel dateModel = new SpinnerDateModel(
                    new java.util.Date(),
                    null,
                    null,
                    java.util.Calendar.DAY_OF_MONTH
            );
            deadline = new JSpinner(dateModel);
            JSpinner.DateEditor editor = new JSpinner.DateEditor(deadline, "yyyy-MM-dd");
            deadline.setEditor(editor);

            // Initialize duration dropdown (1–99 hours)
            Integer[] hoursOptions = new Integer[99];
            for (int i = 0; i < 99; i++) {
                hoursOptions[i] = i + 1;
            }
            durationHours = new JComboBox<>(hoursOptions);
            durationHours.setSelectedIndex(-1);

         // === Back Arrow in Menu Bar 
        JButton backBtn = new JButton("⟵");
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        backBtn.setFocusPainted(false);
        backBtn.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));
        backBtn.setContentAreaFilled(false);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // When clicked, go back to JobOwnerPage
        backBtn.addActionListener(e -> {
            dispose(); // closes SubmitJobFrame
            new JobOwnerPage().setVisible(true); // returns to Job Owner Dashboard
        });

        // Add to top menu or a small bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(backBtn);
        menuBar.add(Box.createHorizontalStrut(8)); 
        setJMenuBar(menuBar);


        JLabel title = new JLabel("Create New Job");
        title.setFont(titlefont);
        title.setForeground(titlecolor);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBackground(background);
        root.setBorder(BorderFactory.createEmptyBorder(24,32,23,32));

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.setOpaque(false);
            
        JLabel jobIdLabel = new JLabel ("Job ID (email):");
        jobIdLabel.setFont(labelfont);
        JLabel jobLabel = new JLabel("Job Name:");
        jobLabel.setFont(labelfont);
        JLabel deadlineLabel = new JLabel("Job Deadline:");
        deadlineLabel.setFont(labelfont);
        JLabel reqsLabel = new JLabel("Job Duration (hours)");
        reqsLabel.setFont(labelfont);

        User user = User.getInstance();
        String userEmail = user.getEmail();
        JTextField jobIdField = new JTextField(userEmail);
        jobIdField.setEditable(true);
        
        form.add(jobIdLabel);
        form.add(jobIdField);
        form.add(jobLabel);
        form.add(jobName);
        form.add(deadlineLabel);
        form.add(deadline);
        form.add(reqsLabel);
        form.add(durationHours);

        JPanel buttonsRow = new JPanel(new GridBagLayout());
        buttonsRow.setOpaque(false);
        JButton submitJob = new JButton("Submit Job");
        styleButton(submitJob, buttoncolorgreen, buttonfont, buttonsize);
        buttonsRow.add(submitJob);
        submitJob.setPreferredSize(new Dimension(380, 50));
        submitJob.setBackground(Color.gray);
        submitJob.setEnabled(false);

        //When all fields are filled, button will turn green and become clickable

        DocumentListener listener = new DocumentListener() {

            public void insertUpdate(DocumentEvent event) {
                checkField(submitJob);
            }

            public void removeUpdate(DocumentEvent event) {
                checkField(submitJob);
            }

            public void changedUpdate(DocumentEvent event) {
                checkField(submitJob);
            }

        };

        jobName.getDocument().addDocumentListener(listener);
        deadline.addChangeListener(e -> checkField(submitJob));
        durationHours.addActionListener(e -> checkField(submitJob));

        submitJob.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String enteredName = jobName.getText().trim();
                String JobId = jobIdField.getText().trim();
                if(JobId.isEmpty()){
                    JobId = userEmail;
                }
                java.util.Date deadlineDate = (java.util.Date) deadline.getValue();
                String enteredDeadline = new java.text.SimpleDateFormat("yyyy-MM-dd").format(deadlineDate);
                Integer selectedHours = (Integer) durationHours.getSelectedItem();
                int enteredDuration = selectedHours != null ? selectedHours : 0;

                String line = String.join("|", "JOB", JobId, enteredName, String.valueOf(enteredDuration), enteredDeadline);

                JDialog statusDialog = new JDialog(SubmitJobFrame.this, "Job Status", false);
                JLabel statusLabel = new JLabel("Waiting for approval...please don't close this message");
                statusLabel.setFont(labelfont);
                statusLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                statusDialog.add(statusLabel);
                statusDialog.pack();
                statusDialog.setLocationRelativeTo(SubmitJobFrame.this);
                statusDialog.setVisible(true);
                
                JobNetworkClient client = new JobNetworkClient("localhost", 1234);
                client.setStatusCallback(status -> {
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        if (status.equals("Accepted")) {
                            statusLabel.setText("Your job has been accepted!");
                        } else if (status.equals("Rejected")) {
                            statusLabel.setText("Sorry, this job has been rejected.");
                        }
                        statusDialog.pack();
                    });
                });
                client.sendJobLine(line);

                jobName.setText("");
                deadline.setValue(new java.util.Date());
                durationHours.setSelectedIndex(-1);
                submitJob.setEnabled(false);
                submitJob.setBackground(Color.gray);
            }
        });

        root.add(title);
        root.add(Box.createVerticalStrut(30));
        root.add(form);
        root.add(buttonsRow);
        setContentPane(root);

        }

    }
    //Estimate Job Completion Time and Submit Job
    /* static class EstimateJobFrame extends JFrame {

        private final String enteredName; 
        private final String enteredDeadline; 
        private final String enteredReqs;

        private final VcControllerServiceImpl controller = new VcControllerServiceImpl();

        public EstimateJobFrame(String name, String deadline, String reqs) {

            this.enteredName = name; 
            this.enteredDeadline = deadline;
            this.enteredReqs = reqs;

            setTitle("Job Information:");
            setSize(720, 480);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout(10,10));
            getContentPane().setBackground(background);

            JobReq level = JobReq.valueOf(this.enteredReqs);
            JobInfo info = controller.jobCalculations(level);
            int computedDurationHours = info.getJobDuration();
            int estimatedCompletionRun = info.getJobCompletionTime();

            JobOwner job = new JobOwner();
            job.setJobOwnerName(enteredName);
            job.setDuration(computedDurationHours);
            job.setCompletionTime(estimatedCompletionRun );
            job.setJobDeadline(enteredDeadline);
            job.setRequirements(this.enteredReqs);

            JTextArea jobinfo = new JTextArea();
            jobinfo.setEditable(false);
            jobinfo.setFont(labelfont);
            jobinfo.setBorder(BorderFactory.createLineBorder(titlecolor, 1));
            jobinfo.setBackground(background);
            StringBuilder sb = new StringBuilder();
            sb.append(" Job Name: ").append(this.enteredName).append("\n");
            sb.append(" Job Deadline: ").append(this.enteredDeadline).append("\n");
            sb.append(" Requirement Level: ").append(this.enteredReqs).append("\n");
            sb.append(" Computed Duration: ").append(computedDurationHours + " hours").append("\n");
            sb.append(" Estimated Completion Time: ").append(estimatedCompletionRun + " hours").append("\n");
            jobinfo.setText(sb.toString());

            jobinfo.setPreferredSize(new Dimension(500,200));

            JPanel jobPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
            jobPanel.add(jobinfo);
            jobPanel.setBackground(background);
            add(jobPanel, BorderLayout.CENTER);

            JLabel question = new JLabel("Would you like to submit this Job?");
            question.setFont(titlefont);
            question.setForeground(titlecolor);
            
            JPanel questionPanel = new JPanel();
            questionPanel.setBackground(background);
            questionPanel.add(question);
            add(questionPanel, BorderLayout.NORTH);

            JPanel buttons = new JPanel(new GridBagLayout());
            buttons.setBackground(background);

            JButton submit = new JButton("Submit");
            styleButton(submit, buttoncolorgreen, buttonfont, buttonsize);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 8, 16, 8);
            buttons.add(submit, gbc);

            add(buttons, BorderLayout.SOUTH);

            submit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    JobOwnerServiceImpl jobOwnerService = new JobOwnerServiceImpl();
                    jobOwnerService.addJobOwner(job);
                    controller.submitJob(job);
                    JOptionPane.showMessageDialog(EstimateJobFrame.this, "Job Successfully Submitted!");
                }
            });

        }

    } */

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

                User user = User.getInstance();
                byte[] data = Files.readAllBytes(Paths.get("src/job/repo/"+user.getEmail()+".txt"));
                area.setText(new String(data));
            } catch (java.io.IOException e) {
                area.setText("");
            }
         
        }

    }

}