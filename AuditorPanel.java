

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class creates the audit GUI where the auditor can pick candidates
 * @author Zafar Khan
 */
public class AuditorPanel extends JPanel{

    /**
     * URL for the database query
     */
    private static final String DATABASE_URL = "jdbc:mysql://s-l112.engr.uiowa.edu:3306/engr_class012";
    /**
     * Database username
     */
    private static final String USERNAME = "engr_class012";
    /**
     * Password for database
     */
    private static final String PASSWORD = "zafarmikenihaal";

    /**
     * manages connection
     */
    Connection connection = null;
    /**
     * query statement
     */
    Statement statement = null;
    /**
     * manages results
     */
    ResultSet resultSet = null;

    /**
     * String abbreviations for states
     */
    private final String[] STATES_ABBREV = {"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL",
            "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY",
            "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"};

    /**
     * textfield fo county name
     */
    private JTextField textField;
    /**
     * button to submit program
     */
    private JButton button;
    /**
     * lbl for component
     */
    private JLabel lbl;
    /**
     * lbl for component
     */
    private JLabel lbl2;
    /**
     * combobox for state selection
     */
    private JComboBox list;
    /**
     * back button
     */
    public JButton backButton;

    /**
     * Panel on which all components are placed
     */
    private JPanel basePanel;
    /**
     * How I implement scrolling to the panel
     */
    private JScrollPane scrollPane;

    /**
     * Keeps track of all the panels being displayed
     */
    private ArrayList<JPanel> panelSet = new ArrayList<JPanel>();
    /**
     * Keeps track of all the labels being displayed
     */
    private ArrayList<JLabel> labelSet = new ArrayList<JLabel>();
    /**
     * Keeps track of remaining components being displayed
     */
    private ArrayList<JComponent> components = new ArrayList<JComponent>();

    /**
     * Keeps track of names of candidates
     */
    private ArrayList<JTextField> nameAreas = new ArrayList<JTextField>();
    /**
     * Keeps track of names of party of the candidates
     */
    private ArrayList<JTextField> partyAreas = new ArrayList<JTextField>();
    /**
     * Keeps track of names of office of the candidates
     */
    private ArrayList<String> officeAreas = new ArrayList<String>();
    /**
     * The list which contains all the important info regarding candidates
     */
    private ArrayList<String[]> outputList = new ArrayList<String[]>();


    /**
     * Constructor which builds the GUI components
     */
    public AuditorPanel(){
        basePanel= new JPanel();
        //setPreferredSize(new Dimension(1000, 800));
        basePanel.setPreferredSize(new Dimension(1000, 800));
        basePanel.setLayout(null);

        lbl = new JLabel("Specify State: ");
        lbl.setFont(new Font("Verdana", Font.PLAIN, 12));
        String[] stateArr = {"Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut",
                "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky",
                "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana",
                "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota",
                "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee",
                "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};

        list = new JComboBox<String>(STATES_ABBREV);

        lbl2 = new JLabel("County: ");
        lbl2.setFont(new Font("Verdana", Font.PLAIN, 12));
        textField = new JTextField();


        lbl.setBounds(20,15,500,50);
        basePanel.add(lbl);
        list.setBounds(115,25,150,30);
        basePanel.add(list);
        lbl2.setBounds(20,50,500,50);
        basePanel.add(lbl2);
        textField.setBounds(115,60,100,30);
        basePanel.add(textField);

        JLabel test = generateTitle("Senator");
        test.setFont(new Font("Verdana", Font.BOLD, 12));
        test.setBounds(40,150,500,50);
        basePanel.add(test);

        test = generateTitle("Governor");
        test.setFont(new Font("Verdana", Font.BOLD, 12));
        test.setBounds(230,150,500,50);
        basePanel.add(test);

        test = generateTitle("Lieutenant Governor");
        test.setFont(new Font("Verdana", Font.BOLD, 12));
        test.setBounds(465,150,500,50);
        basePanel.add(test);

        test = generateTitle("Attorney General");
        test.setFont(new Font("Verdana", Font.BOLD, 12));
        test.setBounds(740,150,500,50);
        basePanel.add(test);


        JPanel person = generatePerson();
        person.setBounds(40,200,170,70);
        basePanel.add(person);

        person = generatePerson();
        person.setBounds(230,200,170,70);
        basePanel.add(person);

        person = generatePerson();
        person.setBounds(465,200,170,70);
        basePanel.add(person);

        person = generatePerson();
        person.setBounds(740,200,170,70);
        basePanel.add(person);


        test = generateTitle("Auditor");
        test.setFont(new Font("Verdana",Font.BOLD, 12));
        test.setBounds(40,300,500,50);
        basePanel.add(test);

        test = generateTitle("Commissioner of Agriculture");
        test.setFont(new Font("Verdana", Font.BOLD, 12));
        test.setBounds(230,300,500,50);
        basePanel.add(test);

        test = generateTitle("Commissioner of Insurance");
        test.setFont(new Font("Verdana", Font.BOLD, 12));
        test.setBounds(465,300,500,50);
        basePanel.add(test);

        test = generateTitle("Commissioner of Labor");
        test.setFont(new Font("Verdana", Font.BOLD, 12));
        test.setBounds(740,300,500,50);
        basePanel.add(test);

        person = generatePerson();
        person.setBounds(40,350,170,70);
        basePanel.add(person);

        person = generatePerson();
        person.setBounds(230,350,170,70);
        basePanel.add(person);

        person = generatePerson();
        person.setBounds(465,350,170,70);
        basePanel.add(person);

        person = generatePerson();
        person.setBounds(740,350,170,70);
        basePanel.add(person);

        test = generateTitle("Secretary of State");
        test.setFont(new Font("Verdana", Font.BOLD, 12));
        test.setBounds(40,450,500,50);
        basePanel.add(test);

        test = generateTitle("Treasurer");
        test.setFont(new Font("Verdana", Font.BOLD, 12));
        test.setBounds(230,450,500,50);
        basePanel.add(test);

        test = generateTitle("Representative");
        test.setFont(new Font("Verdana", Font.BOLD, 12));
        test.setBounds(465,450,500,50);
        basePanel.add(test);

        test = generateTitle("President");
        test.setFont(new Font("Verdana", Font.BOLD, 12));
        test.setBounds(740,450,500,50);
        basePanel.add(test);

        person = generatePerson();
        person.setBounds(40,500,170,70);
        basePanel.add(person);

        person = generatePerson();
        person.setBounds(230,500,170,70);
        basePanel.add(person);

        person = generatePerson();
        person.setBounds(465,500,170,70);
        basePanel.add(person);

        person = generatePerson();
        person.setBounds(740,500,170,70);
        basePanel.add(person);



        button = new JButton("Submit");
        button.setFont(new Font("Verdana", Font.BOLD, 12));
        button.setBounds(740,25,150,60);

        button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String[] location = {(String)list.getSelectedItem(), textField.getText()};
                outputList.add(location);
                getOffice();
                for(int i =0;i<panelSet.size();i++){
                    if(!nameAreas.get(i).getText().equals("")||!partyAreas.get(i).getText().equals("")){
                        String[] personInfo = {nameAreas.get(i).getText(), partyAreas.get(i).getText(), officeAreas.get(i)};
                        outputList.add(personInfo);
                    }
                }
                String infoString = "";
                String voteString = "";
                for(int i=1;i<outputList.size();i++ ){
                    String[] arr = outputList.get(i);
                    for(int k=0;k<3;k++){
                        infoString+=arr[k];
                        if(k!=2){
                            infoString+=",";
                        }
                    }
                    voteString+="0";
                    if(i!=outputList.size()-1) {

                        infoString += "-";
                        voteString += ",";
                    }
                }

                try {

                    // establish connection to database
                    connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
                    statement = connection.createStatement();
                    resultSet = statement.executeQuery("SELECT * FROM MASTER");

                    String query = " insert into MASTER(State, County, NamePartyOffice, vote_count)"
                            + " values (?, ?, ?, ?)";

                    PreparedStatement preparedStmt = connection.prepareStatement(query);
                    preparedStmt.setString (1, location[0]);
                    preparedStmt.setString (2, location[1]);
                    preparedStmt.setString  (3, infoString);
                    preparedStmt.setString(4, voteString);

                    // execute the preparedstatement
                    preparedStmt.execute();

                    connection.close();


                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                System.out.println(infoString);
            }
        });
        basePanel.add(button);
        components.add(button);

        backButton = new JButton("Back");
        backButton.setFont(new Font("Verdana", Font.BOLD, 12));
        backButton.setBounds(40, 600, 100, 50);
        basePanel.add(backButton);
        components.add(backButton);

        scrollPane = new JScrollPane(basePanel,   ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(1000, 780));

        add(scrollPane);

    }

    /**
     * rGets the office of the person running to submit to sql
     */
    private void getOffice(){

        for(JPanel people:panelSet){
            Rectangle r = people.getBounds();
            int x = (int)r.getX();
            int y = (int)r.getY();

            int lblDist =0;
            int lblIndex=0;
            for(int i = 0; i<labelSet.size(); i++){ //For loop for getting the correct office
                JLabel item2 = labelSet.get(i);
                Rectangle r2 = item2.getBounds();
                int x2 = (int)r2.getX();
                int y2 = (int)r2.getY();
                int dist = y-y2;
                if(x==x2 && dist>0 && (dist<lblDist||lblDist==0)){
                    lblDist = dist;
                    lblIndex = i;
                }
            }

            String office = labelSet.get(lblIndex).getText();
            officeAreas.add(office);

        }
    }

    /**
     * generates a new panel for a person
     * @return return the panel with info
     */
    private JPanel generatePerson(){
        JPanel personPanel = new JPanel();
        JLabel name = new JLabel("Name: ");
        name.setFont(new Font("Verdana", Font.PLAIN, 12));
        JTextField tf1 = new JTextField();
        nameAreas.add(tf1);
        JLabel party = new JLabel("Party: ");
        party.setFont(new Font("Verdana", Font.PLAIN, 12));
        JTextField tf2 = new JTextField();
        partyAreas.add(tf2);
        JButton addButton = new JButton("+");
        addButton.setFont(new Font("Verdana", Font.BOLD, 12));
        JButton remButton = new JButton("-");
        remButton.setFont(new Font("Verdana", Font.BOLD, 12));
        personPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        personPanel.setLayout(new GridLayout(3,2));


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                Rectangle r = personPanel.getBounds();
                int x = (int)r.getX();
                int y = (int)r.getY();
                int width2 = (int)r.getWidth();
                int height2 = (int)r.getHeight();
                int flag = 0;
                for(JPanel item:panelSet){
                    Rectangle r2 = item.getBounds();
                    int y2 = (int)r2.getY();
                    if(y+70==y2&&!(item.equals(personPanel))){
                        flag+=1;
                    }
                }
                for(JPanel item:panelSet){
                    Rectangle r2 = item.getBounds();
                    int x2 = (int)r2.getX();
                    int y2 = (int)r2.getY();

                    Dimension d = item.getSize();
                    int width = (int)d.getWidth();
                    int height = (int)d.getHeight();
                    if(y2>y&&!(flag>0)){
                        item.setBounds(x2,y2+70,width,height);
                        Rectangle r3 = basePanel.getBounds();
                        int origWidth = (int)r3.getWidth();
                        int origHeight = (int)r3.getHeight();
                        basePanel.setPreferredSize(new Dimension(origWidth, origHeight+70));
                        scrollPane.setPreferredSize(new Dimension(origWidth, origHeight+70));
                    }
                }
                for(JLabel item2:labelSet){
                    Rectangle r2 = item2.getBounds();
                    int x2 = (int)r2.getX();
                    int y2 = (int)r2.getY();

                    Dimension d = item2.getSize();
                    int width = (int)d.getWidth();
                    int height = (int)d.getHeight();
                    if(y2>y&&!(flag>0)){
                        item2.setBounds(x2,y2+70,width,height);
                    }
                }
                for(JComponent item3:components){
                    Rectangle r2 = item3.getBounds();
                    int x2 = (int)r2.getX();
                    int y2 = (int)r2.getY();

                    Dimension d = item3.getSize();
                    int width = (int)d.getWidth();
                    int height = (int)d.getHeight();
                    if(y2>y&&!(flag>0)){
                        item3.setBounds(x2,y2+70,width,height);
                        Rectangle r3 = basePanel.getBounds();
                        int origWidth = (int)r3.getWidth();
                        int origHeight = (int)r3.getHeight();
                        basePanel.setPreferredSize(new Dimension(origWidth, origHeight+70));
                        scrollPane.setPreferredSize(new Dimension(origWidth, origHeight+70));
                    }
                }


                JPanel test = generatePerson();
                test.setBounds(x,y+70,width2,height2);

                basePanel.add(test);
                basePanel.revalidate();
                basePanel.repaint();

            }
        });

        remButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Rectangle r = personPanel.getBounds();
                int x = (int)r.getX();
                int y = (int)r.getY();
                int flag = 0;
                for(JPanel item:panelSet){
                    Rectangle r2 = item.getBounds();
                    int y2 = (int)r2.getY();
                    if(y==y2&&!(item.equals(personPanel))){
                        flag+=1;
                    }
                }
                for(JPanel item:panelSet){
                    Rectangle r2 = item.getBounds();
                    int x2 = (int)r2.getX();
                    int y2 = (int)r2.getY();

                    Dimension d = item.getSize();
                    int width = (int)d.getWidth();
                    int height = (int)d.getHeight();
                    if(y2>y&&!(flag>0)){
                        item.setBounds(x2,y2-70,width,height);
                        Rectangle r3 = basePanel.getBounds();
                        int origWidth = (int)r3.getWidth();
                        int origHeight = (int)r3.getHeight();
                        basePanel.setPreferredSize(new Dimension(origWidth, origHeight-70));
                        scrollPane.setPreferredSize(new Dimension(origWidth, origHeight-70));
                    }
                }
                for(JLabel item2:labelSet){
                    Rectangle r2 = item2.getBounds();
                    int x2 = (int)r2.getX();
                    int y2 = (int)r2.getY();

                    Dimension d = item2.getSize();
                    int width = (int)d.getWidth();
                    int height = (int)d.getHeight();
                    if(y2>y&&!(flag>0)){
                        item2.setBounds(x2,y2-70,width,height);
                    }
                }

                for(JComponent item3:components){
                    Rectangle r2 = item3.getBounds();
                    int x2 = (int)r2.getX();
                    int y2 = (int)r2.getY();

                    Dimension d = item3.getSize();
                    int width = (int)d.getWidth();
                    int height = (int)d.getHeight();
                    if(y2>y&&!(flag>0)){
                        item3.setBounds(x2,y2-70,width,height);
                    }
                }

                panelSet.remove(personPanel);
                nameAreas.remove(tf1);
                partyAreas.remove(tf2);
                basePanel.remove(personPanel);

                basePanel.revalidate();
                basePanel.repaint();

            }
        });




        personPanel.add(name);
        personPanel.add(tf1);
        personPanel.add(party);
        personPanel.add(tf2);
        personPanel.add(addButton);
        personPanel.add(remButton);

        panelSet.add(personPanel);
        return personPanel;
    }

    /**
     * creates a string label for position
     * @param title label text
     * @return returns label to be placed in panel
     */
    private JLabel generateTitle(String title){
        JLabel newLabel = new JLabel(title);
        labelSet.add(newLabel);
        return newLabel;
    }

}
