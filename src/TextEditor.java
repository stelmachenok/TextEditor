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
class TextEditor {
    File workFile = new File("");
    JTextArea jta;
    JFrame jfrm;
    TextEditor(){
        jfrm = new JFrame("MyTextEditor");
        jfrm.setSize(1920,1080);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JToolBar jtb = new JToolBar("Toolbar", JToolBar.HORIZONTAL);
        JMenuBar jmb = new JMenuBar();
        jta = new JTextArea();
        /*jta.setLocation(50,100);
        jta.setSize(800,600);*/

        ImageIcon iconNew = new ImageIcon("Images/New.gif");
        JButton jbtnNew = new JButton(iconNew);
        jbtnNew.setSize(40,40);
        jbtnNew.setActionCommand("New");
        jtb.add(jbtnNew);

        JMenuItem menuItem;
        JMenu jmFile = new JMenu("File");
        menuItem = new JMenuItem("New");
        ActionListener newAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("New")) {
                    JFileChooser jfch = new JFileChooser();
                    jfch.showOpenDialog(jfrm);
                    workFile = jfch.getSelectedFile();
                    if (workFile != null) {
                        jta.setVisible(true);
                        //jta.setLocation(50, 0);
                    }
                }
            }
        };
        jbtnNew.addActionListener(newAction);
        menuItem.addActionListener(newAction);
        jmFile.add(menuItem);


        ImageIcon iconOpen = new ImageIcon("Images/Open.gif");
        JButton jbtnOpen = new JButton(iconOpen);
        jbtnOpen.setSize(40,40);
        jbtnOpen.setActionCommand("Open");
        jtb.add(jbtnOpen);


        menuItem = new JMenuItem("Open");
        ActionListener openAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("Open")) {
                    JFileChooser jfch = new JFileChooser();
                    jfch.showOpenDialog(jfrm);
                    workFile = jfch.getSelectedFile();

                    try (FileReader fr = new FileReader(workFile)) {
                        char tmp[] = new char[(int) workFile.length()];
                        fr.read(tmp);
                        jta.setText(new String(tmp));
                        jta.setVisible(true);
                        //jta.setLocation(50,0);
                    } catch (Exception e2) {
                        JOptionPane.showMessageDialog(jfrm, "Reading Error");
                    }
                }
            }
        };

        jbtnOpen.addActionListener(openAction);
        menuItem.addActionListener(openAction);
        jmFile.add(menuItem);

        ImageIcon iconClose = new ImageIcon("Images/Close.gif");
        JButton jbtnClose = new JButton(iconClose);
        jbtnClose.setSize(40,40);
        jbtnClose.setActionCommand("Close");
        jtb.add(jbtnClose);


        menuItem = new JMenuItem("Close");
        ActionListener closeAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("Close")) {
                    jta.setText("");
                    jta.setVisible(false);
                }
            }
        };

        jbtnClose.addActionListener(closeAction);
        menuItem.addActionListener(closeAction);
        jmFile.add(menuItem);

        ImageIcon iconSave = new ImageIcon("Images/Save.gif");
        JButton jbtnSave = new JButton(iconSave);
        jbtnSave.setSize(40,40);
        jbtnSave.setActionCommand("Save");
        jtb.add(jbtnSave);


        menuItem = new JMenuItem("Save");
        ActionListener saveAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("Save")) {
                    try (FileWriter fw = new FileWriter(workFile)) {
                        fw.write(jta.getText());
                        fw.flush();
                    }
                    catch (Exception e3) {
                        JOptionPane.showMessageDialog(jfrm, "Saving Error");
                    }
                }
            }
        };

        jbtnSave.addActionListener(saveAction);
        menuItem.addActionListener(saveAction);
        jmFile.add(menuItem);

        ImageIcon iconExit = new ImageIcon("Images/Exit.gif");
        JButton jbtnExit = new JButton(iconExit);
        jbtnExit.setSize(40,40);
        jbtnExit.setActionCommand("Exit");
        jtb.add(jbtnExit);


        menuItem = new JMenuItem("Exit");
        ActionListener exitAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("Exit")) {
                    System.exit(0);
                }
            }
        };

        jbtnExit.addActionListener(exitAction);
        menuItem.addActionListener(exitAction);
        jmFile.add(menuItem);

        jmb.add(jmFile);

        JMenu jmEdit = new JMenu("Edit");

        ImageIcon iconB= new ImageIcon("Images/B.gif");
        JButton jbtnB = new JButton(iconB);
        jbtnB.setSize(40,40);
        jbtnB.setActionCommand("B");
        jtb.add(jbtnB);


        menuItem = new JMenuItem("B");
        ActionListener bAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("B")) {

                }
            }
        };

        jbtnB.addActionListener(bAction);
        menuItem.addActionListener(bAction);
        jmEdit.add(menuItem);

        ImageIcon iconI = new ImageIcon("Images/I.gif");
        JButton jbtnI = new JButton(iconI);
        jbtnI.setSize(40,40);
        jbtnI.setActionCommand("I");
        jtb.add(jbtnI);


        menuItem = new JMenuItem("I");
        ActionListener iAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals("I")) {

                }
            }
        };

        jbtnI.addActionListener(iAction);
        menuItem.addActionListener(iAction);
        jmEdit.add(menuItem);

        jmb.add(jmEdit);

        String jcbFontsComponents[] = {"Arial", "Times New Roman", "Console"};
        final JComboBox jcbFonts = new JComboBox(jcbFontsComponents);
        String jcbSizeComponents[] = {"8","9","10","11","12","14","16","18","20","22","24","26","28","36","48","72"};
        final JComboBox jcbSize = new JComboBox(jcbSizeComponents);
        jcbSize.setSelectedItem("24");

        jcbFonts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jta.setFont(new Font(jcbFonts.getSelectedItem().toString(), Font.PLAIN, Integer.parseInt((String)jcbSize.getSelectedItem())));
            }
        });
        jtb.add(jcbFonts);


        jcbSize.setSelectedItem("25");
        jcbSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jta.setFont(new Font(jcbFonts.getSelectedItem().toString(), Font.PLAIN, Integer.parseInt((String)jcbSize.getSelectedItem())));
            }
        });
        jtb.add(jcbSize);


        JMenu jmOptions = new JMenu("Options");
        JMenu jmColors = new JMenu("Colors");
        menuItem = new JMenuItem("Red");
        jmColors.add(menuItem);
        menuItem = new JMenuItem("Green");
        jmColors.add(menuItem);
        menuItem = new JMenuItem("Blue");
        jmColors.add(menuItem);

        jmOptions.add(jmColors);

        JMenu jmPriority = new JMenu("Priority");
        menuItem = new JMenuItem("High");
        //menuItem.addActionListener(this);
        jmPriority.add(menuItem);
        menuItem = new JMenuItem("Low");
        //menuItem.addActionListener(this);
        jmPriority.add(menuItem);
        jmOptions.add(jmPriority);

        jmb.add(jmOptions);

        JMenu jmHelp = new JMenu("Help");
        menuItem = new JMenuItem("About");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(jfrm, "Author: Max");
            }
        });
        jmHelp.add(menuItem);
        jmb.add(jmHelp);


        jfrm.setJMenuBar(jmb);

        jtb.setBounds(0, 0, 1920, 50);
        jfrm.add(jtb);


        jfrm.add(jta);
        jta.setVisible(false);
        jta.setFont(new Font("Arial", Font.PLAIN, 24));
        //jfrm.add(new JLabel());

        jfrm.setVisible(true);




    }


    public static void main (String args[]){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new TextEditor();
            }
        });
    }
}