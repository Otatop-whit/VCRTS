package job.ui;

import job.model.JobOwner;
import job.service.JobOwnerService;
import job.service.JobOwnerServiceImpl;

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
        setTitle("VCRTS — Vehicular Cloud Real Time System");
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

        private JTextField duration = new JTextField();
        private JTextField deadline = new JTextField();
        private JComboBox<String> reqs = new JComboBox<>(new String[] {"Low", "Medium", "High"});

        private void checkField(JButton estimateJob) {
            boolean filled = !duration.getText().isEmpty() && !deadline.getText().isEmpty() && reqs.getSelectedIndex()!=-1;
            estimateJob.setEnabled(filled);
            if (filled) {
                estimateJob.setBackground(buttoncolorgreen);
            } else {
                estimateJob.setBackground(Color.gray);
            }
        }

        private JobOwnerService service = new JobOwnerServiceImpl();

        SubmitJobFrame() {
            setTitle("Create New Job");
            setSize(720, 480);
            setLocationRelativeTo(null);

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

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        form.setOpaque(false);
            
        JLabel durationLabel = new JLabel("Approximate Job Duration:");
        durationLabel.setFont(labelfont);
        JLabel deadlineLabel = new JLabel("Job Deadline:");
        deadlineLabel.setFont(labelfont);
        JLabel reqsLabel = new JLabel("Job Requirements:");
        reqsLabel.setFont(labelfont);

        reqs.setSelectedIndex(-1);

        form.add(durationLabel);
        form.add(duration);
        form.add(deadlineLabel);
        form.add(deadline);
        form.add(reqsLabel);
        form.add(reqs);

        JPanel buttonsRow = new JPanel(new GridBagLayout());
        buttonsRow.setOpaque(false);
        JButton estimateJob = new JButton("Calculate Estimated Completion Time");
        styleButton(estimateJob, buttoncolorgreen, buttonfont, buttonsize);
        buttonsRow.add(estimateJob);
        estimateJob.setPreferredSize(new Dimension(380, 50));
        estimateJob.setBackground(Color.gray);
        estimateJob.setEnabled(false);

        //When all fields are filled, button will turn green and become clickable

        DocumentListener listener = new DocumentListener() {

            public void insertUpdate(DocumentEvent event) {
                checkField(estimateJob);
            }

            public void removeUpdate(DocumentEvent event) {
                checkField(estimateJob);
            }

            public void changedUpdate(DocumentEvent event) {
                checkField(estimateJob);
            }

        };

        duration.getDocument().addDocumentListener(listener);
        deadline.getDocument().addDocumentListener(listener);

        reqs.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent event) {
                checkField(estimateJob);
            }
            
        });

        estimateJob.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String enteredDuration = duration.getText();
                String enteredDeadline = deadline.getText();
                String enteredReqs = (String) reqs.getSelectedItem();
                EstimateJobFrame estimatePage = new EstimateJobFrame(enteredDuration, enteredDeadline, enteredReqs);
                estimatePage.setVisible(true);
            }
        });

        root.add(title);
        root.add(Box.createVerticalStrut(60));
        root.add(form);
        root.add(buttonsRow);
        setContentPane(root);

        }

    }

    static class EstimateJobFrame extends JFrame {

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



