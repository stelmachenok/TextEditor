import javax.swing.*;
import javax.swing.plaf.FileChooserUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.management.OperatingSystemMXBean;
import java.nio.channels.FileChannel;
import java.sql.Savepoint;

/**
 * Created by y50-70 on 27.02.2017.
 */
class TextEditor implements ActionListener{
    File workFile = new File("");
    JTextArea jta;
    JFrame jfrm;
    TextEditor(){
        jfrm = new JFrame("MyTextEditor");
        jfrm.setSize(640,480);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar jmb = new JMenuBar();

        JMenu jmFile = new JMenu("File");
        JMenuItem jmiNew = new JMenuItem("New");
        JMenuItem jmiOpen = new JMenuItem("Open");
        JMenuItem jmiClose = new JMenuItem("Close");
        JMenuItem jmiSave = new JMenuItem("Save");
        JMenuItem jmiExit = new JMenuItem("Exit");
        jmFile.add(jmiNew);
        jmFile.add(jmiOpen);
        jmFile.add(jmiClose);
        jmFile.add(jmiSave);
        jmFile.add(jmiExit);
        jmb.add(jmFile);

        JMenu jmOptions = new JMenu("Options");
        JMenu jmColors = new JMenu("Colors");
        JMenuItem jmiRed = new JMenuItem("Red");
        JMenuItem jmiGreen= new JMenuItem("Green");
        JMenuItem jmiBlue= new JMenuItem("Blue");
        jmColors.add(jmiRed);
        jmColors.add(jmiGreen);
        jmColors.add(jmiBlue);
        jmOptions.add(jmColors);

        JMenu jmPriority = new JMenu("Priority");
        JMenuItem jmiHigh = new JMenuItem("High");
        JMenuItem jmiLow = new JMenuItem("Low");
        jmPriority.add(jmiHigh);
        jmPriority.add(jmiLow);
        jmOptions.add(jmPriority);
        //jmOptions.addSeparator();
        jmb.add(jmOptions);

        JMenu jmHelp = new JMenu("Help");
        JMenuItem jmiAbout = new JMenuItem("About");
        jmHelp.add(jmiAbout);
        jmb.add(jmHelp);

        jmiNew.addActionListener(this);
        jmiOpen.addActionListener(this);
        jmiClose.addActionListener(this);
        jmiSave.addActionListener(this);
        jmiExit.addActionListener(this);
        jmiRed.addActionListener(this);
        jmiGreen.addActionListener(this);
        jmiBlue.addActionListener(this);
        jmiHigh.addActionListener(this);
        jmiLow.addActionListener(this);
        jmiAbout.addActionListener(this);

        jfrm.setJMenuBar(jmb);

        jta = new JTextArea();
        jta.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        jta.setVisible(false);
        jfrm.add(jta);

        jfrm.setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){
        String comStr = ae.getActionCommand();
        switch (comStr) {
            case "New": {
                JFileChooser jfch = new JFileChooser();
                jfch.showOpenDialog(jfrm);
                workFile = jfch.getSelectedFile();
                jta.setVisible(true);
                break;
            }
            case "Open": {
                JFileChooser jfch = new JFileChooser();
                jfch.showOpenDialog(jfrm);
                workFile = jfch.getSelectedFile();

                try (FileReader fr = new FileReader(workFile)) {
                    char tmp[] = new char[(int) workFile.length()];
                    fr.read(tmp);
                    jta.setText(new String(tmp));
                    jta.setVisible(true);
                }
                catch (Exception e2) {
                    JOptionPane.showMessageDialog(jfrm, "Reading Error");
                }
                break;
            }
            case "Save": {
                try (FileWriter fw = new FileWriter(workFile)) {
                    fw.write(jta.getText());
                    fw.flush();
                }
                catch (Exception e3) {
                    JOptionPane.showMessageDialog(jfrm, "Saving Error");
                }
                break;
            }
            case "Close": {
                jta.setText("");
                jta.setVisible(false);
                break;
            }
            case "About": {
                JOptionPane.showMessageDialog(jfrm, "Author: Max");
                break;
            }
            case "Exit": {
                System.exit(0);
                break;
            }
        }
    }

    public static void main (String args[]){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new TextEditor();
            }
        });
    }
}
