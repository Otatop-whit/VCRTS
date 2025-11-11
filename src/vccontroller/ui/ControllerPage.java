package vccontroller.ui;
import common.model.User;
import common.ui.WelcomePage;
import job.ui.JobOwnerPage;
import vehicle.ui.vehicle_ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ControllerPage extends JFrame {
    User user = User.getInstance();

    public ControllerPage()  {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200,800);
        setLocationRelativeTo(null);
      JPanel sideBar = new JPanel();
      sideBar.setLayout(new BoxLayout(sideBar,BoxLayout.Y_AXIS));
      sideBar.setBorder(new EmptyBorder(12,12,12,12));
      JPanel dashboard = new JPanel();
      dashboard.setBorder(new EmptyBorder(16,16,16,16));

        JTextArea t1 = new JTextArea(10, 10);
        JTextArea t2 = new JTextArea(10, 10);

        // set texts
        t1.setText("This is menuBar");
        t2.setText("this is dashboard");
        sideBar.add(t1);
        dashboard.add(t2);
    JSplitPane sl = new JSplitPane(SwingConstants.VERTICAL,sideBar,dashboard);
    sl.setDividerSize(4);

    sl.setResizeWeight(0);
    setContentPane(sl);
   }
}

        
