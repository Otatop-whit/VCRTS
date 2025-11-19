package vccontroller.ui;

import common.model.Account;
import common.model.User;
import common.service.AccountData;
import common.ui.WelcomePage;
import vccontroller.model.JobsCache;
import vccontroller.model.VehicleCache;
import vehicle.model.Vehicle;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
/*
  Controller dashboard UI.

  Reads job info from backend file:
   src/vccontroller/repo/JobData.txt
   Timestamp: ...
   Job Name: ...
   Duration: ...
   Job Completion Time: ...
   Job Deadline: ...
   Requirements: ...
 */
public class ControllerPage extends JFrame {

    private static ControllerPage INSTANCE;

    private final User user = User.getInstance();

    private JPanel contentPanel;   // CardLayout 
    private CardLayout cardLayout;
    private JPanel jobsListPanel;  // Holds header + job rows on Home
    private JPanel vehiclesListPanel;  // Holds header + vehicle rows on Vehicles tab

    public ControllerPage() {
        initUI();
        INSTANCE = this;
    }

    private void initUI() {
        setTitle("Controller Dashboard");
        setSize(900, 600); // frame size
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(15, 23, 42));

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(new Color(15, 23, 42));

        JPanel homePanel = buildHomePanel();
        JPanel vehiclesPanel = buildVehiclesPanel();
        JPanel notificationsPanel = createPlaceholderPanel("Notifications");
        JPanel viewAccountsPanel = createViewAccountsPanel();

        contentPanel.add(homePanel, "HOME");
        contentPanel.add(vehiclesPanel, "VEHICLES");
        contentPanel.add(notificationsPanel, "NOTIFICATIONS");
        contentPanel.add(viewAccountsPanel, "View Accounts");

        JPanel sidebar = buildSidebar();

        root.add(sidebar, BorderLayout.WEST);
        root.add(contentPanel, BorderLayout.CENTER);

        setContentPane(root);
        setVisible(true);
    }

    // Sidebar with divider
    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(15, 23, 42));

        // Divider + padding: 1px line on the right, padding inside
        sidebar.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(30, 41, 59)), // divider
                        new EmptyBorder(20, 16, 20, 16) // inner padding
                )
        );

        sidebar.setPreferredSize(new Dimension(200, 0));

        JLabel appLabel = new JLabel("Controller");
        appLabel.setForeground(Color.WHITE);
        appLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        appLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(appLabel);

        sidebar.add(Box.createVerticalStrut(24));

        // Navigation buttons
        JButton homeBtn = createNavButton("Home");
        JButton vehiclesBtn = createNavButton("Vehicles");
        JButton notifBtn = createNavButton("Notifications");
        JButton viewAccButton = createNavButton("View Accounts");

        homeBtn.addActionListener(e -> cardLayout.show(contentPanel, "HOME"));
        vehiclesBtn.addActionListener(e -> cardLayout.show(contentPanel, "VEHICLES"));
        notifBtn.addActionListener(e -> cardLayout.show(contentPanel, "NOTIFICATIONS"));
        viewAccButton.addActionListener(e -> cardLayout.show(contentPanel, "View Accounts"));

        sidebar.add(homeBtn);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(vehiclesBtn);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(notifBtn);
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(viewAccButton);

        sidebar.add(Box.createVerticalGlue());

        JButton logoutBtn = createNavButton("Log Out");
        logoutBtn.addActionListener(e -> {
            dispose();
            new WelcomePage();
        });
        logoutBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(logoutBtn);

        return sidebar;
    }

    private JButton createNavButton(String text) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(180, 36));
        btn.setBackground(new Color(15, 23, 42));
        btn.setForeground(Color.LIGHT_GRAY);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setForeground(Color.LIGHT_GRAY);
            }
        });

        return btn;
    }

    // Home panel with header and job list
    private JPanel buildHomePanel() {
        JPanel home = new JPanel(new BorderLayout());
        home.setBackground(new Color(15, 23, 42));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(15, 23, 42));
        header.setBorder(new EmptyBorder(16, 20, 8, 20));

        JPanel titleBox = new JPanel();
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.setBackground(new Color(15, 23, 42));

        JLabel title = new JLabel("Job Requests");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));

        JLabel subtitle = new JLabel("Monitor and manage incoming jobs in real time");
        subtitle.setForeground(new Color(148, 163, 184));
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));

        titleBox.add(title);
        titleBox.add(Box.createVerticalStrut(4));
        titleBox.add(subtitle);

        header.add(titleBox, BorderLayout.WEST);

        JPanel userBox = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        userBox.setBackground(new Color(15, 23, 42));

        String emailText = (user != null && user.getEmail() != null) ? user.getEmail() : "controller@vcrts.com";
        JLabel userLabel = new JLabel(emailText);
        userLabel.setForeground(new Color(148, 163, 184));
        userLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JLabel avatar = new JLabel();
        avatar.setPreferredSize(new Dimension(32, 32));
        avatar.setOpaque(true);
        avatar.setBackground(new Color(30, 64, 175));
        avatar.setBorder(BorderFactory.createLineBorder(new Color(15, 23, 42), 2));

        String initials = "C";
        if (emailText != null && !emailText.isEmpty()) {
            initials = String.valueOf(Character.toUpperCase(emailText.charAt(0)));
        }
        avatar.setText(initials);
        avatar.setHorizontalAlignment(SwingConstants.CENTER);
        avatar.setForeground(Color.WHITE);
        avatar.setFont(new Font("SansSerif", Font.BOLD, 12));

        userBox.add(userLabel);
        userBox.add(avatar);

        header.add(userBox, BorderLayout.EAST);
        home.add(header, BorderLayout.NORTH);

        // List area (header row + jobs)
        jobsListPanel = new JPanel();
        jobsListPanel.setBackground(new Color(15, 23, 42));
        jobsListPanel.setBorder(new EmptyBorder(10, 20, 20, 20));
        jobsListPanel.setLayout(new GridBagLayout()); // top alignment

        loadJobsFromBackendFile(); // backend hook

        JScrollPane scrollPane = new JScrollPane(jobsListPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(15, 23, 42));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        home.add(scrollPane, BorderLayout.CENTER);

        return home;
    }

    // Reads job records from src/vccontroller/repo/JobData.txt and creates one row per record.
    private void loadJobsFromBackendFile() {
        jobsListPanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        gbc.gridy = 0;
        gbc.weighty = 0;
        jobsListPanel.add(createHeaderRow(), gbc);

        int jobIndex = 1;
        int rowY = 1;

        JobsCache cache = JobsCache.getInstance();
        for (int i = 0; i < cache.length(); i++) {
            job.model.JobOwner job = cache.getJob(i);
            if (job != null) {
                String jobId = "#J-" + String.format("%04d", jobIndex++);
                JPanel row = createJobRow(
                        jobId,
                        job.getJobOwnerName() != null ? job.getJobOwnerName() : "Job " + (jobIndex - 1),
                        job.getJobDeadline() != null ? job.getJobDeadline() : "-",
                        "-",
                        String.valueOf(job.getDuration()) + " hours",
                        job.getCompletionTime() > 0 ? String.valueOf(job.getCompletionTime()) : "-",
                        "Pending"
                );

                gbc.gridy = rowY++;
                gbc.weighty = 0;
                jobsListPanel.add(row, gbc);
            }
        }

        String filePath = "src/vccontroller/repo/JobData.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String timestamp = null;
            String jobName = null;
            String duration = null;
            String completionTime = null;
            String deadline = null;


            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("Timestamp:")) {
                    timestamp = line.substring("Timestamp:".length()).trim();
                } else if (line.startsWith("Job Name:")) {
                    jobName = line.substring("Job Name:".length()).trim();
                } else if (line.startsWith("Duration:")) {
                    duration = line.substring("Duration:".length()).trim();
                } else if (line.startsWith("Job Completion Time:")) {
                    completionTime = line.substring("Job Completion Time:".length()).trim();
                } else if (line.startsWith("Job Deadline:")) {
                    deadline = line.substring("Job Deadline:".length()).trim();
                } else if (line.startsWith("~~~~")) {
                    String jobId = "#J-" + String.format("%04d", jobIndex++);

                    JPanel row = createJobRow(
                            jobId,
                            jobName != null ? jobName : "Job " + (jobIndex - 1),
                            deadline != null ? deadline : "-",
                            "N/A",  // requirements removed
                            duration != null ? duration : "-",
                            completionTime != null ? completionTime : "-",
                            "Accepted"   // initial status
                    );

                    gbc.gridy = rowY++;
                    gbc.weighty = 0;
                    jobsListPanel.add(row, gbc);

                    // reset for next record
                    timestamp = jobName = duration = completionTime = deadline  = null;
                }
            }

            if (jobIndex == 1) {
                gbc.gridy = 1;
                gbc.weighty = 0;
                JLabel empty = new JLabel("No job requests found.");
                empty.setForeground(new Color(148, 163, 184));
                empty.setFont(new Font("SansSerif", Font.PLAIN, 13));
                jobsListPanel.add(empty, gbc);
            }

            // Filler so everything sticks to the top
            gbc.gridy++;
            gbc.weighty = 1.0;
            JPanel filler = new JPanel();
            filler.setOpaque(false);
            jobsListPanel.add(filler, gbc);

        } catch (IOException ex) {
            GridBagConstraints errGbc = new GridBagConstraints();
            errGbc.gridx = 0;
            errGbc.gridy = 1;
            errGbc.weightx = 1.0;
            errGbc.weighty = 0;
            errGbc.fill = GridBagConstraints.HORIZONTAL;
            errGbc.anchor = GridBagConstraints.NORTHWEST;

            JLabel error = new JLabel("Error loading jobs: " + ex.getMessage());
            error.setForeground(new Color(248, 113, 113));
            error.setFont(new Font("SansSerif", Font.PLAIN, 13));
            jobsListPanel.add(error, errGbc);
        }

        jobsListPanel.revalidate();
        jobsListPanel.repaint();
    }

    // Header row for column labels
    private JPanel createHeaderRow() {
        // 6 columns: Job ID, Job Name, Duration, Deadline, Status, Actions
        JPanel header = new JPanel(new GridLayout(1, 6));
        header.setBackground(new Color(15, 23, 42));
        header.setBorder(new EmptyBorder(0, 12, 4, 12)); 

        header.add(createHeaderLabel("Job ID", SwingConstants.LEFT));
        header.add(createHeaderLabel("Job Name", SwingConstants.LEFT));
        header.add(createHeaderLabel("Duration", SwingConstants.LEFT));
        header.add(createHeaderLabel("Deadline", SwingConstants.LEFT));
        header.add(createHeaderLabel("Status", SwingConstants.CENTER));
        header.add(createHeaderLabel("Actions", SwingConstants.CENTER));

        return header;
    }

    private JLabel createHeaderLabel(String text, int align) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(148, 163, 184));
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setHorizontalAlignment(align);
        return label;
    }

    // Single job row 
    private JPanel createJobRow(String jobId,
                                String jobName,
                                String jobDeadline,
                                String Duration,
                                String duration,
                                String completionTime,
                                String status) {

        // One row → 6 columns
        JPanel row = new JPanel(new GridLayout(1, 6));
        row.setBackground(new Color(30, 41, 59));
        row.setBorder(new EmptyBorder(10, 12, 10, 12));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 56));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Column 1: Job ID
        JPanel col1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        col1.setOpaque(false);
        JLabel idLabel = new JLabel(jobId);
        idLabel.setForeground(Color.WHITE);
        idLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        col1.add(idLabel);

        // Column 2: Job Name
        JPanel col2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        col2.setOpaque(false);
        JLabel nameLabel = new JLabel(jobName);
        nameLabel.setForeground(new Color(148, 163, 184));
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        col2.add(nameLabel);

        // Column 3: Duration
        JPanel col3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        col3.setOpaque(false);
        JLabel reqLabel = new JLabel(duration);
        reqLabel.setForeground(new Color(148, 163, 184));
        reqLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        col3.add(reqLabel);

        // Column 4: Deadline
        JPanel col4 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        col4.setOpaque(false);
        JLabel deadlineLabel = new JLabel(jobDeadline);
        deadlineLabel.setForeground(new Color(100, 116, 139));
        deadlineLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        col4.add(deadlineLabel);

        // Column 5: Status pill (centered)
        JPanel col5 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        col5.setOpaque(false);

        JLabel statusLabel = new JLabel(status);
        statusLabel.setOpaque(true);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        statusLabel.setBorder(new EmptyBorder(4, 10, 4, 10));
        statusLabel.setPreferredSize(new Dimension(90, 24)); 
        applyStatusStyle(statusLabel, status);

        col5.add(statusLabel);

        // Column 6: Actions 
        JPanel col6 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        col6.setOpaque(false);

        JButton detailsBtn = new JButton("View");
        detailsBtn.setFocusPainted(false);
        detailsBtn.setFont(new Font("SansSerif", Font.PLAIN, 11));
        detailsBtn.setBackground(new Color(15, 23, 42));
        detailsBtn.setForeground(new Color(148, 163, 184));
        detailsBtn.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        detailsBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        detailsBtn.addActionListener(e -> showJobDetailsDialog(
                jobId,
                jobName,
                jobDeadline,
                duration,
                completionTime,
                statusLabel
        ));

        col6.add(detailsBtn);

        // Add columns to row
        row.add(col1);
        row.add(col2);
        row.add(col3);
        row.add(col4);
        row.add(col5);
        row.add(col6);

        return row;
    }

    private void applyStatusStyle(JLabel statusLabel, String status) {
        String s = status.toLowerCase();
        if (s.contains("complete")) {
            statusLabel.setBackground(new Color(22, 163, 74));
            statusLabel.setForeground(Color.WHITE);
        } else if (s.contains("pending")) {
            statusLabel.setBackground(new Color(234, 179, 8));
            statusLabel.setForeground(Color.BLACK);
        } else if (s.contains("decline")) {
            statusLabel.setBackground(new Color(220, 38, 38));
            statusLabel.setForeground(Color.WHITE);
        } else if (s.contains("accept")) {
            statusLabel.setBackground(new Color(59, 130, 246));
            statusLabel.setForeground(Color.WHITE);
        } else {
            statusLabel.setBackground(new Color(51, 65, 85));
            statusLabel.setForeground(Color.WHITE);
        }
    }

    // Job Details Dialog (View popup)
    private void showJobDetailsDialog(String jobId,
                                      String jobName,
                                      String jobDeadline,
                                      String duration,
                                      String completionTime,
                                      JLabel statusLabelToUpdate) {

        JDialog dialog = new JDialog(this, "Job Details", true);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(new Color(15, 23, 42));

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(16, 20, 16, 20));
        content.setBackground(new Color(15, 23, 42));

        content.add(createDetailLabel("Job ID: ", jobId));
        content.add(Box.createVerticalStrut(6));
        content.add(createDetailLabel("Job name: ", jobName));
        content.add(Box.createVerticalStrut(6));
        content.add(createDetailLabel("Job deadline: ", jobDeadline));
        content.add(Box.createVerticalStrut(6));
        content.add(createDetailLabel("Duration: ", duration));
        content.add(Box.createVerticalStrut(6));
        content.add(createDetailLabel("Job completion time: ", completionTime));

        dialog.add(content, BorderLayout.CENTER);

        //Buttons row 
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.setBackground(new Color(15, 23, 42));
        buttons.setBorder(new EmptyBorder(0, 0, 12, 12));

        Color acceptBase = new Color(59, 130, 246);     // blue
        Color acceptHover = new Color(37, 99, 235);
        Color declineBase = new Color(220, 38, 38);     // red
        Color declineHover = new Color(185, 28, 28);
        Color closeBase = Color.WHITE;                          // white
        Color closeHover = new Color(229, 231, 235);    // light gray
        Color closeText = new Color(30, 41, 59);        // dark text

        JButton acceptBtn = new JButton("Accept");
        JButton declineBtn = new JButton("Decline");
        JButton closeBtn = new JButton("Close");

        // Accept button style
        acceptBtn.setFocusPainted(false);
        acceptBtn.setOpaque(true);
        acceptBtn.setContentAreaFilled(true);
        acceptBtn.setBackground(acceptBase);
        acceptBtn.setForeground(Color.WHITE);
        acceptBtn.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        acceptBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        acceptBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                acceptBtn.setBackground(acceptHover);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                acceptBtn.setBackground(acceptBase);
            }
        });

        // Decline button style
        declineBtn.setFocusPainted(false);
        declineBtn.setOpaque(true);
        declineBtn.setContentAreaFilled(true);
        declineBtn.setBackground(declineBase);
        declineBtn.setForeground(Color.WHITE);
        declineBtn.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        declineBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        declineBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                declineBtn.setBackground(declineHover);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                declineBtn.setBackground(declineBase);
            }
        });

        // Close button style
        closeBtn.setFocusPainted(false);
        closeBtn.setOpaque(true);
        closeBtn.setContentAreaFilled(true);
        closeBtn.setBackground(closeBase);
        closeBtn.setForeground(closeText);
        closeBtn.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        closeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        closeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                closeBtn.setBackground(closeHover);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                closeBtn.setBackground(closeBase);
            }
        });

        // Button actions
        acceptBtn.addActionListener(e -> {
            JobsCache cache = JobsCache.getInstance();
            int jobIdx = -1;
            for (int i = 0; i < cache.length(); i++) {
                if (cache.getJob(i).getJobOwnerName().equals(jobName)) {
                jobIdx = i;
                break;
            }
        }

            vccontroller.service.ClientHandler.acceptJob(jobIdx);
            statusLabelToUpdate.setText("Accepted");
            applyStatusStyle(statusLabelToUpdate, "Accepted");
            ControllerPage.refreshIfOpen();
            dialog.dispose();
        });

        declineBtn.addActionListener(e -> {
            JobsCache cache = JobsCache.getInstance();
            int jobIdx = -1;
            for (int i = 0; i < cache.length(); i++) {
                if (cache.getJob(i).getJobOwnerName().equals(jobName)) {
                    jobIdx = i;
                    break;
                }
            }
            vccontroller.service.ClientHandler.rejectJob(jobIdx);
            statusLabelToUpdate.setText("Declined");
            applyStatusStyle(statusLabelToUpdate, "Declined");
            ControllerPage.refreshIfOpen();
            dialog.dispose();
        });

        closeBtn.addActionListener(e -> dialog.dispose());

        // Add to panel
        buttons.add(acceptBtn);
        buttons.add(Box.createHorizontalStrut(8));
        buttons.add(declineBtn);
        buttons.add(Box.createHorizontalStrut(8));
        buttons.add(closeBtn);

        dialog.add(buttons, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        
        dialog.setVisible(true);
    }

    private JPanel createDetailLabel(String label, String value) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(15, 23, 42));

        JLabel key = new JLabel(label);
        key.setForeground(new Color(148, 163, 184));
        key.setFont(new Font("SansSerif", Font.BOLD, 12));

        JLabel val = new JLabel(value != null ? value : "-");
        val.setForeground(Color.WHITE);
        val.setFont(new Font("SansSerif", Font.PLAIN, 12));

        panel.add(key, BorderLayout.WEST);
        panel.add(val, BorderLayout.CENTER);

        return panel;
    }

    // VEHICLES TAB 
    private JPanel buildVehiclesPanel() {
        JPanel vehicles = new JPanel(new BorderLayout());
        vehicles.setBackground(new Color(15, 23, 42));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(15, 23, 42));
        header.setBorder(new EmptyBorder(16, 20, 8, 20));

        JPanel titleBox = new JPanel();
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.setBackground(new Color(15, 23, 42));

        JLabel title = new JLabel("Vehicles");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));

        JLabel subtitle = new JLabel("Monitor and manage registered vehicles in real time");
        subtitle.setForeground(new Color(148, 163, 184));
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));

        titleBox.add(title);
        titleBox.add(Box.createVerticalStrut(4));
        titleBox.add(subtitle);

        header.add(titleBox, BorderLayout.WEST);
        vehicles.add(header, BorderLayout.NORTH);

        vehiclesListPanel = new JPanel();
        vehiclesListPanel.setBackground(new Color(15, 23, 42));
        vehiclesListPanel.setBorder(new EmptyBorder(10, 20, 20, 20));
        vehiclesListPanel.setLayout(new GridBagLayout());

        loadVehiclesFromBackendFile();

        JScrollPane scrollPane = new JScrollPane(vehiclesListPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(15, 23, 42));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        vehicles.add(scrollPane, BorderLayout.CENTER);

        return vehicles;
    }
    /*
     Reads vehicle records from src/vccontroller/repo/VehicleData.txt and creates one row per record.
        Model: 
        Make: 
        Year: 
        Computing Power: 
        LicensePlate: 
        Arrival Date: 
       Departure Date: 
       Residency: 
     */
    private void loadVehiclesFromBackendFile() {
        vehiclesListPanel.removeAll();
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
    
        // Header row
        gbc.gridy = 0;
        gbc.weighty = 0;
        vehiclesListPanel.add(createVehicleHeaderRow(), gbc);
    
        int vehicleIndex = 1;
        int rowY = 1;

        VehicleCache cache = VehicleCache.getInstance();
        for (int i = 0; i < cache.length(); i++) {
            Vehicle vehicle = cache.getVehicle(i);
            String email = null;
            String license = null;
            String make = null;
            String model = null;
            String year = null;
            String computePower = null;
            String arrDate = null;
            String depDate = null;
            String resident = null;
            String createdAt = null;
            String updateAt = null;
            if (vehicle != null) {
                System.out.println(vehicle.getMake());
                String vehicleId = "#V-" + String.format("%04d", vehicleIndex++);
                email = user.getEmail();
                license = vehicle.getLicensePlate();
                make = vehicle.getMake();
                model = vehicle.getModel();
                year = vehicle.getYear().toString();
                computePower = vehicle.getComputingPower();
                arrDate = vehicle.getArriveDate().toString();
                depDate = vehicle.getDepartDate().toString();
                resident = vehicle.getResidency();
                createdAt = vehicle.getTimestamp().toString();
                updateAt = vehicle.getLastModified().toString();
                

                JPanel row = createVehicleRow(
                        vehicleId,
                        model,
                        make,
                        year,
                        computePower,
                        license,
                        arrDate,
                        depDate,
                        resident,
                        email,
                        createdAt,
                        updateAt
                );

                gbc.gridy = rowY++;
                gbc.weighty = 0;
                vehiclesListPanel.add(row, gbc);
            }
        }


        String filePath = "src/vccontroller/repo/VehicleData.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
    
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue; // skip blanks
                }
    
                // Split by "/"
                String[] parts = line.split("/");
    
                // Expect at least: email / id / plate / model / make / year / power / arrival / departure / residency
                if (parts.length < 10) {
                    continue;
                }
    
                String ownerEmail     = parts[0].trim();
                String rawId          = parts[1].trim();   // internal id, not used in UI right now
                String licensePlate   = parts[2].trim();
                String model          = parts[3].trim();
                String make           = parts[4].trim();
                String year           = parts[5].trim();
                String computingPower = parts[6].trim();
                String arrivalDate    = parts[7].trim();
                String departureDate  = parts[8].trim();
                String residency      = parts[9].trim();
                String createdAt      = (parts.length > 10) ? parts[10].trim() : "-";
                String updatedAt      = (parts.length > 11) ? parts[11].trim() : "-";
    
                //  UI ID for the controller
                String vehicleId = "#V-" + String.format("%04d", vehicleIndex++);
    
                JPanel row = createVehicleRow(
                        vehicleId,
                        model,
                        make,
                        year,
                        computingPower,
                        licensePlate,
                        arrivalDate,
                        departureDate,
                        residency,
                        ownerEmail,
                        createdAt,
                        updatedAt
                );
    
                gbc.gridy = rowY++;
                gbc.weighty = 0;
                vehiclesListPanel.add(row, gbc);
            }
    
            if (vehicleIndex == 1) {
                // no vehicles added
                gbc.gridy = 1;
                gbc.weighty = 0;
                JLabel empty = new JLabel("No vehicle records found.");
                empty.setForeground(new Color(148, 163, 184));
                empty.setFont(new Font("SansSerif", Font.PLAIN, 13));
                vehiclesListPanel.add(empty, gbc);
            }
    
            // Filler so everything sticks to the top
            gbc.gridy++;
            gbc.weighty = 1.0;
            JPanel filler = new JPanel();
            filler.setOpaque(false);
            vehiclesListPanel.add(filler, gbc);
    
        } catch (IOException ex) {
            GridBagConstraints errGbc = new GridBagConstraints();
            errGbc.gridx = 0;
            errGbc.gridy = 1;
            errGbc.weightx = 1.0;
            errGbc.weighty = 0;
            errGbc.fill = GridBagConstraints.HORIZONTAL;
            errGbc.anchor = GridBagConstraints.NORTHWEST;
    
            JLabel error = new JLabel("Error loading vehicles: " + ex.getMessage());
            error.setForeground(new Color(248, 113, 113));
            error.setFont(new Font("SansSerif", Font.PLAIN, 13));
            vehiclesListPanel.add(error, errGbc);
        }
    
        vehiclesListPanel.revalidate();
        vehiclesListPanel.repaint();
    }
    
    

        private JPanel createVehicleHeaderRow() {
            // 7 columns: Vehicle ID, Model, Make, Year, License Plate, Status, Actions
            JPanel header = new JPanel(new GridLayout(1, 7));
            header.setBackground(new Color(15, 23, 42));
            header.setBorder(new EmptyBorder(0, 12, 4, 12));
        
            header.add(createHeaderLabel("Vehicle ID", SwingConstants.LEFT));
            header.add(createHeaderLabel("Model", SwingConstants.LEFT));
            header.add(createHeaderLabel("Make", SwingConstants.LEFT));
            header.add(createHeaderLabel("Year", SwingConstants.LEFT));
            header.add(createHeaderLabel("License Plate", SwingConstants.LEFT));
            header.add(createHeaderLabel("Status", SwingConstants.CENTER));
            header.add(createHeaderLabel("Actions", SwingConstants.CENTER));
        
            return header;
    }

                private JPanel createVehicleRow(String vehicleId,
                String model,
                String make,
                String year,
                String computingPower,
                String licensePlate,
                String arrivalDate,
                String departureDate,
                String residency,
                String ownerEmail,
                String createdAt,
                String updatedAt) {

            // 7 columns: ID, Model, Make, Year, Plate, Status, Actions
            JPanel row = new JPanel(new GridLayout(1, 7));
            row.setBackground(new Color(30, 41, 59));
            row.setBorder(new EmptyBorder(10, 12, 10, 12));
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 56));
            row.setAlignmentX(Component.LEFT_ALIGNMENT);

            // Column 1: Vehicle ID
            JPanel col1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            col1.setOpaque(false);
            JLabel idLabel = new JLabel(vehicleId);
            idLabel.setForeground(Color.WHITE);
            idLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
            col1.add(idLabel);

            // Column 2: Model
            JPanel col2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            col2.setOpaque(false);
            JLabel modelLabel = new JLabel(model);
            modelLabel.setForeground(new Color(148, 163, 184));
            modelLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
            col2.add(modelLabel);

            // Column 3: Make
            JPanel col3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            col3.setOpaque(false);
            JLabel makeLabel = new JLabel(make);
            makeLabel.setForeground(new Color(148, 163, 184));
            makeLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
            col3.add(makeLabel);

            // Column 4: Year
            JPanel col4 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            col4.setOpaque(false);
            JLabel yearLabel = new JLabel(year);
            yearLabel.setForeground(new Color(148, 163, 184));
            yearLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
            col4.add(yearLabel);

            // Column 5: License Plate
            JPanel col5 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            col5.setOpaque(false);
            JLabel plateLabel = new JLabel(licensePlate);
            plateLabel.setForeground(new Color(148, 163, 184));
            plateLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
            col5.add(plateLabel);

            // Column 6: Status pill (like jobs)
            JPanel col6 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            col6.setOpaque(false);

            JLabel statusLabel = new JLabel("Pending");
            statusLabel.setOpaque(true);
            statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
            statusLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
            statusLabel.setBorder(new EmptyBorder(4, 10, 4, 10));
            statusLabel.setPreferredSize(new Dimension(90, 24));
            applyStatusStyle(statusLabel, "Pending"); // reuse same styling as jobs

            col6.add(statusLabel);

            // Column 7: Actions (View)
            JPanel col7 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            col7.setOpaque(false);

            JButton detailsBtn = new JButton("View");
            detailsBtn.setFocusPainted(false);
            detailsBtn.setFont(new Font("SansSerif", Font.PLAIN, 11));
            detailsBtn.setBackground(new Color(15, 23, 42));
            detailsBtn.setForeground(new Color(148, 163, 184));
            detailsBtn.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
            detailsBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            detailsBtn.addActionListener(e -> showVehicleDetailsDialog(
            vehicleId,
            model,
            make,
            year,
            computingPower,
            licensePlate,
            arrivalDate,
            departureDate,
            residency,
            ownerEmail,
            createdAt,
            updatedAt,
            statusLabel
            ));

            col7.add(detailsBtn);

            // Add all columns to the row
            row.add(col1);
            row.add(col2);
            row.add(col3);
            row.add(col4);
            row.add(col5);
            row.add(col6);
            row.add(col7);

            return row;
            }
            private void showVehicleDetailsDialog(String vehicleId,
                                                String model,
                                                String make,
                                                String year,
                                                String computingPower,
                                                String licensePlate,
                                                String arrivalDate,
                                                String departureDate,
                                                String residency,
                                                String ownerEmail,
                                                String createdAt,
                                                String updatedAt,
                                                JLabel statusLabelToUpdate) {

    JDialog dialog = new JDialog(this, "Vehicle Details", true);
    dialog.setLayout(new BorderLayout());
    dialog.getContentPane().setBackground(new Color(15, 23, 42));

    JPanel content = new JPanel();
    content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
    content.setBorder(new EmptyBorder(16, 20, 16, 20));
    content.setBackground(new Color(15, 23, 42));

    content.add(createDetailLabel("Vehicle ID: ", vehicleId));
    content.add(Box.createVerticalStrut(6));
    content.add(createDetailLabel("Owner Email: ", ownerEmail));
    content.add(Box.createVerticalStrut(6));
    content.add(createDetailLabel("Model: ", model));
    content.add(Box.createVerticalStrut(6));
    content.add(createDetailLabel("Make: ", make));
    content.add(Box.createVerticalStrut(6));
    content.add(createDetailLabel("Year: ", year));
    content.add(Box.createVerticalStrut(6));
    content.add(createDetailLabel("Computing Power: ", computingPower));
    content.add(Box.createVerticalStrut(6));
    content.add(createDetailLabel("License Plate: ", licensePlate));
    content.add(Box.createVerticalStrut(6));
    content.add(createDetailLabel("Arrival Date: ", arrivalDate));
    content.add(Box.createVerticalStrut(6));
    content.add(createDetailLabel("Departure Date: ", departureDate));
    content.add(Box.createVerticalStrut(6));
    content.add(createDetailLabel("Residency: ", residency));
    content.add(Box.createVerticalStrut(6));
    content.add(createDetailLabel("Created At: ", createdAt));
    content.add(Box.createVerticalStrut(6));
    content.add(createDetailLabel("Updated At: ", updatedAt));

    dialog.add(content, BorderLayout.CENTER);

    // Buttons row (Accept / Decline / Close) like jobs
    JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    buttons.setBackground(new Color(15, 23, 42));
    buttons.setBorder(new EmptyBorder(0, 0, 12, 12));

    Color acceptBase  = new Color(59, 130, 246); // blue
    Color acceptHover = new Color(37, 99, 235);
    Color declineBase = new Color(220, 38, 38);  // red
    Color declineHover= new Color(185, 28, 28);
    Color closeBase   = Color.WHITE;             // white
    Color closeHover  = new Color(229, 231, 235);
    Color closeText   = new Color(30, 41, 59);   // dark text

    JButton acceptBtn = new JButton("Accept");
    JButton declineBtn = new JButton("Decline");
    JButton closeBtn = new JButton("Close");

    // Accept button style
    acceptBtn.setFocusPainted(false);
    acceptBtn.setOpaque(true);
    acceptBtn.setContentAreaFilled(true);
    acceptBtn.setBackground(acceptBase);
    acceptBtn.setForeground(Color.WHITE);
    acceptBtn.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
    acceptBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    acceptBtn.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            acceptBtn.setBackground(acceptHover);
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
            acceptBtn.setBackground(acceptBase);
        }
    });
    acceptBtn.addActionListener(e -> {
        VehicleCache cache = VehicleCache.getInstance();
        int vehicleIdx = -1;
        for(int i = 0; i < cache.length(); i++){
                if (cache.getVehicle(i).getLicensePlate().equals(licensePlate)) {
                vehicleIdx = i;
                break;
                }
            }
        vccontroller.service.ClientHandler.acceptVehicle(vehicleIdx);
            statusLabelToUpdate.setText("Accepted");
            applyStatusStyle(statusLabelToUpdate, "Accepted");
            ControllerPage.refreshIfOpen();
            dialog.dispose();
    });
    // Decline button style
    declineBtn.setFocusPainted(false);
    declineBtn.setOpaque(true);
    declineBtn.setContentAreaFilled(true);
    declineBtn.setBackground(declineBase);
    declineBtn.setForeground(Color.WHITE);
    declineBtn.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
    declineBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    declineBtn.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            declineBtn.setBackground(declineHover);
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
            declineBtn.setBackground(declineBase);
        }
    });

    // Close button style
    closeBtn.setFocusPainted(false);
    closeBtn.setOpaque(true);
    closeBtn.setContentAreaFilled(true);
    closeBtn.setBackground(closeBase);
    closeBtn.setForeground(closeText);
    closeBtn.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
    closeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    closeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e) {
            closeBtn.setBackground(closeHover);
        }

        @Override
        public void mouseExited(java.awt.event.MouseEvent e) {
            closeBtn.setBackground(closeBase);
        }
    });

    // Button actions — update status pill in the table
    /* 
    acceptBtn.addActionListener(e -> {
        statusLabelToUpdate.setText("Accepted");
        applyStatusStyle(statusLabelToUpdate, "Accepted");
        dialog.dispose();
    });
    */
    declineBtn.addActionListener(e -> {
        statusLabelToUpdate.setText("Declined");
        applyStatusStyle(statusLabelToUpdate, "Declined");
        dialog.dispose();
    });

    closeBtn.addActionListener(e -> dialog.dispose());

    buttons.add(acceptBtn);
    buttons.add(Box.createHorizontalStrut(8));
    buttons.add(declineBtn);
    buttons.add(Box.createHorizontalStrut(8));
    buttons.add(closeBtn);

    dialog.add(buttons, BorderLayout.SOUTH);

    dialog.pack();
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
}



    private JPanel createPlaceholderPanel(String title) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(15, 23, 42));

        JLabel label = new JLabel(title + " (coming soon)");
        label.setForeground(new Color(148, 163, 184));
        label.setFont(new Font("SansSerif", Font.PLAIN, 18));

        panel.add(label);
        return panel;
    }
    public static void refreshIfOpen() {
        if (INSTANCE != null) {
            INSTANCE.loadJobsFromBackendFile();
            INSTANCE.loadVehiclesFromBackendFile();
        }
    }

    private JPanel createViewAccountsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(15, 23, 42));
        panel.setBorder(new EmptyBorder(16, 20, 16, 20));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(15, 23, 42));

        JLabel title = new JLabel("User Accounts");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));

        JPanel titleBox = new JPanel();
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        titleBox.setBackground(new Color(15, 23, 42));
        titleBox.add(title);
        titleBox.add(Box.createVerticalStrut(4));

        header.add(titleBox, BorderLayout.WEST);
        panel.add(header, BorderLayout.NORTH);

        // Accounts list
        JPanel accountsListPanel = new JPanel();
        accountsListPanel.setBackground(new Color(15, 23, 42));
        accountsListPanel.setBorder(new EmptyBorder(10, 20, 20, 20));
        accountsListPanel.setLayout(new GridBagLayout());

        loadAccountsAsTable(accountsListPanel);

        JScrollPane scrollPane = new JScrollPane(accountsListPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(15, 23, 42));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void loadAccountsAsTable(JPanel panel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        gbc.gridy = 0;
        gbc.weighty = 0;
        panel.add(createAccountHeaderRow(), gbc);

        //load accounts from AccountData.txt
        AccountData accountData = new AccountData();
        List<Account> accounts = accountData.getAllAccounts();

        int rowY = 1;

        // no accounts found case
        if (accounts.isEmpty()) {
            gbc.gridy = 1;
            JLabel empty = new JLabel("No accounts found.");
            empty.setForeground(new Color(148, 163, 184));
            empty.setFont(new Font("SansSerif", Font.PLAIN, 13));
            panel.add(empty, gbc);
        } else {
            for (int i = 0; i < accounts.size(); i++) {
                Account account = accounts.get(i);
                JPanel row = createAccountRow(
                        "#" + String.format("%04d", i + 1),
                        account.getName(),
                        account.getEmail(),
                        account.getRole()
                );
                // Add row to panel
                gbc.gridy = rowY++;
                gbc.weighty = 0;
                panel.add(row, gbc);
            }
        }

        // Filler
        gbc.gridy++;
        gbc.weighty = 1.0;
        JPanel filler = new JPanel();
        filler.setOpaque(false);
        panel.add(filler, gbc);
    }

    private JPanel createAccountHeaderRow() {
        JPanel header = new JPanel(new GridLayout(1, 4));
        header.setBackground(new Color(15, 23, 42));
        header.setBorder(new EmptyBorder(0, 12, 4, 12));

        header.add(createHeaderLabel("Account ID", SwingConstants.LEFT));
        header.add(createHeaderLabel("Name", SwingConstants.LEFT));
        header.add(createHeaderLabel("Email", SwingConstants.LEFT));
        header.add(createHeaderLabel("Role", SwingConstants.CENTER));

        return header;
    }

    private JPanel createAccountRow(String accountId, String name, String email, String role) {
        // One row → 4 columns
        JPanel row = new JPanel(new GridLayout(1, 4));
        row.setBackground(new Color(30, 41, 59));
        row.setBorder(new EmptyBorder(10, 12, 10, 12));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 56));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Column 1: Account ID
        JPanel col1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        col1.setOpaque(false);
        JLabel idLabel = new JLabel(accountId);
        idLabel.setForeground(Color.WHITE);
        idLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        col1.add(idLabel);

        // Column 2: Name
        JPanel col2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        col2.setOpaque(false);
        JLabel nameLabel = new JLabel(name);
        nameLabel.setForeground(new Color(148, 163, 184));
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        col2.add(nameLabel);

        // Column 3: Email
        JPanel col3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        col3.setOpaque(false);
        JLabel emailLabel = new JLabel(email);
        emailLabel.setForeground(new Color(148, 163, 184));
        emailLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        col3.add(emailLabel);

        // Column 4: Role pill style
        JPanel col4 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        col4.setOpaque(false);

        JLabel roleLabel = new JLabel(role);
        roleLabel.setOpaque(true);
        roleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        roleLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        roleLabel.setBorder(new EmptyBorder(4, 10, 4, 10));
        roleLabel.setPreferredSize(new Dimension(100, 24));
        applyRoleStyle(roleLabel, role);

        col4.add(roleLabel);

        // Add to row
        row.add(col1);
        row.add(col2);
        row.add(col3);
        row.add(col4);

        return row;
    }

    private void applyRoleStyle(JLabel roleLabel, String role) {
        String roleLower = role.toLowerCase();
        if (roleLower.contains("controller")) {
            roleLabel.setBackground(new Color(220, 38, 38)); // red
            roleLabel.setForeground(Color.WHITE);
        } else if (roleLower.contains("client")) {
            roleLabel.setBackground(new Color(59, 130, 246)); // blue
            roleLabel.setForeground(Color.WHITE);
        }
    }
}
