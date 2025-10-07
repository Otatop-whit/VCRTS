package common.ui;

import javax.swing.*;
import java.awt.*;

public class UserPage {
    public UserPage(){
        JFrame frame = createJFrame("VCRTS", 720,480);
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("User");
        JMenuItem logoutItem = new JMenuItem("Log-Out");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(logoutItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        JLabel label = new JLabel("VCRT Project Label thing");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("SansSerif", Font.BOLD, 26));
        label.setForeground(new Color(25, 50, 120)); // deep navy


        JPanel rootPanel= new JPanel();
        rootPanel.setLayout(new BoxLayout(rootPanel,BoxLayout.Y_AXIS));
        rootPanel.setBorder(BorderFactory.createEmptyBorder(32,40,32,40));
        rootPanel.setBackground(new Color(240,248,255));
        JButton JobOwnerbutton = new JButton("JobOwner");
        JButton VehicleOwnerbutton = new JButton("VehicleOwner");
        JPanel buttonPanel = new JPanel();


        //styleButton();
        rootPanel.add(label);
        rootPanel.add(JobOwnerbutton);
        rootPanel.add(VehicleOwnerbutton);
        frame.setLayout(new BorderLayout());
        logoutItem.addActionListener(e -> {
            frame.dispose();
            new WelcomePage();

        });
        frame.setJMenuBar(menuBar);
        frame.setContentPane(rootPanel);
        frame.setVisible(true);


    }
    private void styleButton(){

    }
    private JFrame createJFrame(String title, int width, int height){
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width,height);
        return frame;
    }
    private JPanel createPanel(){
        return null;
    }

}
