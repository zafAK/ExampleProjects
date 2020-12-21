import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

/**
 * Class which represents JPanel for where user can view current voting data
 */
public class ResultsPanel extends JPanel {
    private static final String DATABASE_URL = "jdbc:mysql://s-l112.engr.uiowa.edu:3306/engr_class012";
    private static final String USERNAME = "engr_class012";
    private static final String PASSWORD = "zafarmikenihaal";

    private final String[] STATES_ABBREV = {"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL",
            "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY",
            "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"};

    private final String[] STATES = {"Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut",
            "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky",
            "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana",
            "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota",
            "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee",
            "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};

    private final String[] EXAMPLE_CANDS = {"zafar", "mike", "nihaal", "kanye"};

    private final JComboBox<String> chooseState;

    private final JTextField countyField;

    private final JLabel presidentWinnerLabel;

    private final JLabel representativeWinnerLabel;

    private final JLabel senateWinnerLabel;

    private final JLabel governorWinnerLabel;

    private final JLabel lieutenantGovernorWinnerLabel;

    private final JLabel attorneyGeneralWinnerLabel;

    private final JLabel auditorWinnerLabel;

    private final JLabel commissionerOfAgricultureWinnerLabel;

    private final JLabel commissionerOfInsuranceWinnerLabel;

    private final JLabel commissionerOfLaborWinnerLabel;

    private final JLabel secretaryOfStateWinnerLabel;

    private final JLabel treasurerWinnerLabel;

    private final JButton getCandidatesButton;

    private final JLabel errorLabel;

    private final JLabel digitalBallotSSN;

    public final JButton backButton;

    private String presidentArr[] = new String[4];
    private String representativeArr[] = new String[4];
    private String sentateArr[] = new String[4];
    private String governorArr[] = new String[4];
    private String lieutGovArr[] = new String[4];
    private String attGeneral[] = new String[4];
    private String auditor[] = new String[4];
    private String comissOfAgr[] = new String[4];
    private String comissOfIns[] = new String[4];
    private String commissOfLabor[] = new String[4];
    private String secOfState[] = new String[4];
    private String treasurer[] = new String[4];

    public ResultsPanel() {
        setPreferredSize(new Dimension(1000, 800)); // set dimensions of JFrame
        this.setLayout(null); // I want to create my own layout


        digitalBallotSSN = new JLabel("Results");
        digitalBallotSSN.setFont(new Font("Verdana", Font.PLAIN, 32));
        digitalBallotSSN.setBounds(20, 15, 1000, 50);
        this.add(digitalBallotSSN);

        JLabel specifyState = new JLabel("Specify State");
        specifyState.setFont(new Font("Verdana", Font.PLAIN, 12));
        specifyState.setBounds(20, 100, 125, 20);
        this.add(specifyState);

        chooseState = new JComboBox<>(STATES_ABBREV);
        chooseState.setFont(new Font("Verdana", Font.PLAIN, 12));
        chooseState.setBounds(150, 100, 150, 20);
        this.add(chooseState);

        JLabel specifyCounty = new JLabel("Specify County");
        specifyCounty.setFont(new Font("Verdana", Font.PLAIN, 12));
        specifyCounty.setBounds(20, 130, 100, 20);
        this.add(specifyCounty);

        countyField = new JTextField();
        countyField.setFont(new Font("Verdana", Font.PLAIN, 12));
        countyField.setBounds(150, 130, 150, 20);
        this.add(countyField);

        JLabel presidentLabel = new JLabel("President");
        presidentLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        presidentLabel.setBounds(20, 200, 300, 20);
        this.add(presidentLabel);

        presidentWinnerLabel = new JLabel(">WINNER<");
        presidentWinnerLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
        presidentWinnerLabel.setBounds(20, 230, 300, 20);
        this.add(presidentWinnerLabel);

        JLabel representativeLabel = new JLabel("Representative");
        representativeLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        representativeLabel.setBounds(340, 200, 300, 20);
        this.add(representativeLabel);

        representativeWinnerLabel = new JLabel(">WINNER<");
        representativeWinnerLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
        representativeWinnerLabel.setBounds(340, 230, 300, 20);
        this.add(representativeWinnerLabel);

        JLabel senateLabel = new JLabel("Senate");
        senateLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        senateLabel.setBounds(660, 200, 300, 20);
        this.add(senateLabel);

        senateWinnerLabel = new JLabel(">WINNER<");
        senateWinnerLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
        senateWinnerLabel.setBounds(660, 230, 300, 20);
        this.add(senateWinnerLabel);

        JLabel governorLabel = new JLabel("Governor");
        governorLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        governorLabel.setBounds(20, 280, 300, 20);
        this.add(governorLabel);

        governorWinnerLabel = new JLabel(">WINNER<");
        governorWinnerLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
        governorWinnerLabel.setBounds(20, 310, 300, 20);
        this.add(governorWinnerLabel);

        JLabel lieutenantGovernorLabel = new JLabel("Lieutenant Governor");
        lieutenantGovernorLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        lieutenantGovernorLabel.setBounds(340, 280, 300, 20);
        this.add(lieutenantGovernorLabel);

        lieutenantGovernorWinnerLabel = new JLabel(">WINNER<");
        lieutenantGovernorWinnerLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
        lieutenantGovernorWinnerLabel.setBounds(340, 310, 300, 20);
        this.add(lieutenantGovernorWinnerLabel);

        JLabel attorneyGeneralLabel = new JLabel("Attorney General");
        attorneyGeneralLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        attorneyGeneralLabel.setBounds(660, 280, 300, 20);
        this.add(attorneyGeneralLabel);

        attorneyGeneralWinnerLabel = new JLabel(">WINNER<");
        attorneyGeneralWinnerLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
        attorneyGeneralWinnerLabel.setBounds(660, 310, 300, 20);
        this.add(attorneyGeneralWinnerLabel);

        JLabel auditorLabel = new JLabel("Auditor");
        auditorLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        auditorLabel.setBounds(20, 360, 300, 20);
        this.add(auditorLabel);

        auditorWinnerLabel = new JLabel(">WINNER<");
        auditorWinnerLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
        auditorWinnerLabel.setBounds(20, 390, 300, 20);
        this.add(auditorWinnerLabel);

        JLabel commissionerOfAgricultureLabel = new JLabel("Commissioner of Agriculture");
        commissionerOfAgricultureLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        commissionerOfAgricultureLabel.setBounds(340, 360, 300, 20);
        this.add(commissionerOfAgricultureLabel);

        commissionerOfAgricultureWinnerLabel = new JLabel(">WINNER<");
        commissionerOfAgricultureWinnerLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
        commissionerOfAgricultureWinnerLabel.setBounds(340, 390, 300, 20);
        this.add(commissionerOfAgricultureWinnerLabel);

        JLabel commissionerOfInsuranceLabel = new JLabel("Commissioner of Insurance");
        commissionerOfInsuranceLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        commissionerOfInsuranceLabel.setBounds(660, 360, 300, 20);
        this.add(commissionerOfInsuranceLabel);

        commissionerOfInsuranceWinnerLabel = new JLabel(">WINNER<");
        commissionerOfInsuranceWinnerLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
        commissionerOfInsuranceWinnerLabel.setBounds(660, 390, 300, 20);
        this.add(commissionerOfInsuranceWinnerLabel);

        JLabel commissionerOfLaborLabel = new JLabel("Commissioner of Labor");
        commissionerOfLaborLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        commissionerOfLaborLabel.setBounds(20, 440, 300, 20);
        this.add(commissionerOfLaborLabel);

        commissionerOfLaborWinnerLabel = new JLabel(">WINNER<");
        commissionerOfLaborWinnerLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
        commissionerOfLaborWinnerLabel.setBounds(20, 470, 300, 20);
        this.add(commissionerOfLaborWinnerLabel);

        JLabel secretaryOfStateLabel = new JLabel("Secretary of State");
        secretaryOfStateLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        secretaryOfStateLabel.setBounds(340, 440, 300, 20);
        this.add(secretaryOfStateLabel);

        secretaryOfStateWinnerLabel = new JLabel(">WINNER<");
        secretaryOfStateWinnerLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
        secretaryOfStateWinnerLabel.setBounds(340, 470, 300, 20);
        this.add(secretaryOfStateWinnerLabel);

        JLabel treasurerLabel = new JLabel("Treasurer");
        treasurerLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        treasurerLabel.setBounds(660, 440, 300, 20);
        this.add(treasurerLabel);

        treasurerWinnerLabel = new JLabel(">WINNER<");
        treasurerWinnerLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
        treasurerWinnerLabel.setBounds(660, 470, 300, 20);
        this.add(treasurerWinnerLabel);

        getCandidatesButton = new JButton("Get Results...");
        getCandidatesButton.setFont(new Font("Verdana", Font.BOLD, 12));
        getCandidatesButton.setBounds(340, 130, 170, 20);
        getCandidatesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(countyField.getText() + ", " + chooseState.getSelectedItem());
            }
        });
        this.add(getCandidatesButton);

        errorLabel = new JLabel(">>> AN ERROR WOULD GO HERE <<<");
        errorLabel.setFont(new Font("Verdana", Font.ITALIC, 12));
        errorLabel.setForeground(Color.RED);
        errorLabel.setBounds(550, 130, 450, 20);
        this.add(errorLabel);

        backButton = new JButton("Return to Login Screen");
        backButton.setFont(new Font("Verdana", Font.BOLD, 12));
        backButton.setBounds(20, 690, 250, 25);
        this.add(backButton);

        try {
            //@author Zafar Khan
            Connection connection = null; // manages connection
            Statement statement = null; // query statement
            ResultSet countySet = null; // names info
            ResultSet stateSet = null; //vote info
            ResultSet info = null;
            ResultSet votes = null;


            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            Statement statement2 = connection.createStatement();
            Statement statement3 = connection.createStatement();
            Statement statement4 = connection.createStatement();
            countySet = statement.executeQuery("SELECT County FROM MASTER");
            votes = statement2.executeQuery("SELECT vote_count FROM MASTER");
            stateSet = statement3.executeQuery("SELECT STATE FROM MASTER");
            info = statement4.executeQuery("SELECT NamePartyOffice FROM MASTER");


            ArrayList<String> names = new ArrayList<String>();
            ArrayList<String> party = new ArrayList<String>();
            ArrayList<String> office = new ArrayList<String>();
            ArrayList<Integer> numVotes = new ArrayList<Integer>();

            while(countySet.next() && stateSet.next() && info.next() && votes.next()){
                String tempCounty = countySet.getString("County");
                String tempState = stateSet.getString("State");
                String tempName = info.getString("NamePartyOffice");
                String tempVotes = votes.getString("vote_count");
                if(tempCounty.equals(countyField.getText())&&tempState.equals(chooseState.getSelectedItem())){
                    String[] allPeople = tempName.split("-");
                    for(String item: allPeople){
                        String[] everything = item.split(",");
                        names.add(everything[0]);
                        party.add(everything[1]);
                        office.add(everything[2]);
                    }
                    String[] counts = tempVotes.split(",");
                    for(String item: counts){
                        numVotes.add(Integer.valueOf(item));
                    }
                }
            }

            String[] positions = {"Senator", "Governor", "Lieutenant Governor", "Attorney General", "Auditor",
                    "Commissioner of Agriculture", "Commissioner of Insurance", "Commissioner of Labor",
                    "Secretary of State", "Treasurer", "Representative", "President"};

            ArrayList<String> winners = new ArrayList<String>();
            int winNum = -1;
            String winName = "";
            for(String pos:positions){
                for(int i=0; i <names.size();i++){
                    if(office.get(i).equals(pos)){
                        if(numVotes.get(i)>winNum){
                            winNum=numVotes.get(i);
                            winName = names.get(i);
                        }
                    }
                }
                winners.add(winName);
                winNum=-1;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}