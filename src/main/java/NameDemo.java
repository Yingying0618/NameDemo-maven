
import java.util.Objects;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;


public class NameDemo implements ActionListener, MouseMotionListener {

    private final JFrame frame;
    private final JPanel panel;
    private final JButton button;
    private final JLabel label; //label for image
    private final JLabel name; //label for student name
    private final JMenuBar menuBar;
    private final JMenu fileMenu;
    private final JMenuItem menuItemAdd, menuItemList,menuItemDelete;
    private String newName,deleteName;
    private ArrayList<String> text;

    public static void main(String[] args) {
        new NameDemo(); //run the Demo

    }
    public NameDemo() {
        //Create an instance of frame
        frame = new JFrame();
        //Frame setting
        frame.setSize(410, 300);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Name Challenge");

        //Create an image panel
        panel = new JPanel();
        //Panel setting
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 200, 10));
        panel.setBackground(Color.PINK);

        //Create a menubar
        menuBar = new JMenuBar();
        //Create sub-menu for menubar
        fileMenu = new JMenu("File");

        //Input file
        //File file = new File("src/NameList.txt");
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(classLoader.getResource("NameList.txt").getFile());
        try {
            text = new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String student;
            while ((student = bufferedReader.readLine()) != null) {
                text.add(student);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Create menuItem
        menuItemList = new JMenuItem("List");
        menuItemAdd = new JMenuItem("Add");
        menuItemDelete = new JMenuItem("Delete");
        fileMenu.addMouseMotionListener(this);
        menuItemAdd.addActionListener(e -> {
            //output added name
            String newName = showMenuAddDialog(panel);
            //add name
            if (!newName.trim().isEmpty()){
                text.add(newName);
                //show add successful
                showAddDialog(panel);
            }
        });

        menuItemList.addActionListener(e -> showMenuListDialog(frame, frame));

        menuItemDelete.addActionListener(e -> {
            //Output deleted name
            String deleteName = showMenuDeleteDialog(panel);
            if(!deleteName.trim().isEmpty() && text.contains(deleteName)){
                text.remove(deleteName);
                //show delete successful
                showDeleteDialog(panel);
            }
        });

        //put menubar together
        fileMenu.add(menuItemList);
        fileMenu.add(menuItemAdd);
        fileMenu.add(menuItemDelete);
        menuBar.add(fileMenu);

        //create a button
        button = new JButton("Click me!");
        button.setLocation(120, 150);
        button.addActionListener(this); //listen to Click

        //Add a scaled image to label
        ImageIcon image = new ImageIcon(Objects.requireNonNull(classLoader.getResource("cat_picture.jpg")));
        image.setImage(image.getImage().getScaledInstance(200, 150, Image.SCALE_DEFAULT));
        label = new JLabel(image);

        //Add name generator to label
        name = new JLabel("Who is this?");

        //Add panel,button and label to frame
        panel.add(menuBar);
        panel.add(label);
        panel.add(name);
        panel.add(button);

        //Add close setting and panel to frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.setJMenuBar(menuBar);
        frame.setVisible(true); //show the frame

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //Randomly select a name
        Random rand = new Random();
        int num = rand.nextInt(text.size());
        String a = text.get(num);
        //remove selected name from list
        text.remove(a);


        name.setText(a);
        name.setLocation(120,150);
        name.setSize(120,50);
        panel.add(name);
        button.setLocation(120,200);
        panel.add(button);

    }

    private String showMenuAddDialog(JPanel panel){
        //create a text dialog
        //JOptionPane dialog = new JOptionPane();
        newName= JOptionPane.showInputDialog(panel,"Name","Add Name",1);
        return newName; //return added name
    }

    private void showMenuListDialog(JFrame owner, Component parentComponent){
        //create a text dialog
        JDialog dialog = new JDialog(owner,"Student Roster",true);
        dialog.setSize(500,400);
        dialog.setLocationRelativeTo(parentComponent);

        //show student list
        Object[] columnNames= {"Number","Student Name"};
        Object[][] rowData = new Object[text.size()][2];
        for(int i = 0; i < text.size(); i++){
            rowData[i][1]=text.get(i);
            rowData[i][0]=i+1;
        }
        JTable table = new JTable(rowData,columnNames);
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.setPreferredScrollableViewportSize(new Dimension(350, 300));

        JButton confirmButton = new JButton("Ok"); //add ok button
        confirmButton.addActionListener(e -> dialog.dispose());
        JPanel panel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
        panel.add(confirmButton);
        dialog.setContentPane(panel);
        dialog.setVisible(true);

    }
    private String showMenuDeleteDialog(JPanel panel){
        //create a text dialog
        deleteName = JOptionPane.showInputDialog(panel,"Name","Delete Name",1);

        return deleteName; //return deleted name
    }
    private void showAddDialog(JPanel panel){
        //create a text dialog
        JOptionPane.showConfirmDialog(panel,"Add Successful!","Name",-1);
    }

    private void showDeleteDialog(JPanel panel){
        //create a text dialog
         JOptionPane.showConfirmDialog(panel,"Delete Successful!","Name",-1);

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        fileMenu.setBackground(Color.PINK);

    }

}

