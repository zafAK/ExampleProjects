import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Contains the panel for citizens to vote
 * @author Michael Dobroski
 */
public class VotingPanel extends JPanel {

    private final String[] STATES_ABBREV = {"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL",
            "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY",
            "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"};

    private final String[] STATES = {"Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut",
            "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky",
            "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana",
            "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota",
            "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee",
            "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"};

    private final String[] EXAMPLE_CANDS = {"zafar", "mike", "nihaal", "kanye", "yeezy", "alex", "jacob", "weezy", "arnold",
            "ricardo", "zufu", "mohammed avdol"};

    private static final String DATABASE_URL = "jdbc:mysql://s-l112.engr.uiowa.edu:3306/engr_class012";
    private static final String USERNAME = "engr_class012";
    private static final String PASSWORD = "zafarmikenihaal";

    private final JComboBox<String> chooseState;

    private final JTextField countyField;

    private final JComboBox<String> presidentComboBox;

    private final JComboBox<String> representativeComboBox;

    private final JComboBox<String> senateComboBox;

    private final JComboBox<String> governorComboBox;

    private final JComboBox<String> lieutenantGovernorComboBox;

    private final JComboBox<String> attorneyGeneralComboBox;

    private final JComboBox<String> auditorComboBox;

    private final JComboBox<String> commissionerOfAgricultureComboBox;

    private final JComboBox<String> commissionerOfInsuranceComboBox;

    private final JComboBox<String> commissionerOfLaborComboBox;

    private final JComboBox<String> secretaryOfStateComboBox;

    private final JComboBox<String> treasurerComboBox;

    private final JCheckBox termsAndConditionsBox;

    private final JButton finishBallotButton;

    private final JButton getCandidatesButton;

    private final JLabel errorLabel;

    private final JLabel digitalBallotSSN;

    public final JButton returnToLoginButton;

    private boolean termsCheck;

    /**
     * Constructor. Builds GUI and listeners.
     */
    public VotingPanel() {

        // printMasterTable();

        setPreferredSize(new Dimension(1000, 800)); // set dimensions of JFrame

        this.setLayout(null); // I want to create my own layout

        digitalBallotSSN = new JLabel();
        digitalBallotSSN.setFont(new Font("Verdana", Font.PLAIN, 32));
        digitalBallotSSN.setBounds(20, 15, 1000, 50);
        this.add(digitalBallotSSN);

        JLabel specifyState = new JLabel("Specify State");
        specifyState.setFont(new Font("Verdana", Font.PLAIN, 12));
        specifyState.setBounds(20, 100, 125, 20);
        this.add(specifyState);

        chooseState = new JComboBox<>(STATES);
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

        presidentComboBox = new JComboBox<>();
        presidentComboBox.setFont(new Font("Verdana", Font.PLAIN, 12));
        presidentComboBox.setBounds(20, 230, 300, 20);
        this.add(presidentComboBox);

        JLabel representativeLabel = new JLabel("Representative");
        representativeLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        representativeLabel.setBounds(340, 200, 300, 20);
        this.add(representativeLabel);

        representativeComboBox = new JComboBox<>();
        representativeComboBox.setFont(new Font("Verdana", Font.PLAIN, 12));
        representativeComboBox.setBounds(340, 230, 300, 20);
        this.add(representativeComboBox);

        JLabel senateLabel = new JLabel("Senate");
        senateLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        senateLabel.setBounds(660, 200, 300, 20);
        this.add(senateLabel);

        senateComboBox = new JComboBox<>();
        senateComboBox.setFont(new Font("Verdana", Font.PLAIN, 12));
        senateComboBox.setBounds(660, 230, 300, 20);
        this.add(senateComboBox);

        JLabel governorLabel = new JLabel("Governor");
        governorLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        governorLabel.setBounds(20, 280, 300, 20);
        this.add(governorLabel);

        governorComboBox = new JComboBox<>();
        governorComboBox.setFont(new Font("Verdana", Font.PLAIN, 12));
        governorComboBox.setBounds(20, 310, 300, 20);
        this.add(governorComboBox);

        JLabel lieutenantGovernorLabel = new JLabel("Lieutenant Governor");
        lieutenantGovernorLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        lieutenantGovernorLabel.setBounds(340, 280, 300, 20);
        this.add(lieutenantGovernorLabel);

        lieutenantGovernorComboBox = new JComboBox<>();
        lieutenantGovernorComboBox.setFont(new Font("Verdana", Font.PLAIN, 12));
        lieutenantGovernorComboBox.setBounds(340, 310, 300, 20);
        this.add(lieutenantGovernorComboBox);

        JLabel attorneyGeneralLabel = new JLabel("Attorney General");
        attorneyGeneralLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        attorneyGeneralLabel.setBounds(660, 280, 300, 20);
        this.add(attorneyGeneralLabel);

        attorneyGeneralComboBox = new JComboBox<>();
        attorneyGeneralComboBox.setFont(new Font("Verdana", Font.PLAIN, 12));
        attorneyGeneralComboBox.setBounds(660, 310, 300, 20);
        this.add(attorneyGeneralComboBox);

        JLabel auditorLabel = new JLabel("Auditor");
        auditorLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        auditorLabel.setBounds(20, 360, 300, 20);
        this.add(auditorLabel);

        auditorComboBox = new JComboBox<>();
        auditorComboBox.setFont(new Font("Verdana", Font.PLAIN, 12));
        auditorComboBox.setBounds(20, 390, 300, 20);
        this.add(auditorComboBox);

        JLabel commissionerOfAgricultureLabel = new JLabel("Commissioner of Agriculture");
        commissionerOfAgricultureLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        commissionerOfAgricultureLabel.setBounds(340, 360, 300, 20);
        this.add(commissionerOfAgricultureLabel);

        commissionerOfAgricultureComboBox = new JComboBox<>();
        commissionerOfAgricultureComboBox.setFont(new Font("Verdana", Font.PLAIN, 12));
        commissionerOfAgricultureComboBox.setBounds(340, 390, 300, 20);
        this.add(commissionerOfAgricultureComboBox);

        JLabel commissionerOfInsuranceLabel = new JLabel("Commissioner of Insurance");
        commissionerOfInsuranceLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        commissionerOfInsuranceLabel.setBounds(660, 360, 300, 20);
        this.add(commissionerOfInsuranceLabel);

        commissionerOfInsuranceComboBox = new JComboBox<>();
        commissionerOfInsuranceComboBox.setFont(new Font("Verdana", Font.PLAIN, 12));
        commissionerOfInsuranceComboBox.setBounds(660, 390, 300, 20);
        this.add(commissionerOfInsuranceComboBox);

        JLabel commissionerOfLaborLabel = new JLabel("Commissioner of Labor");
        commissionerOfLaborLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        commissionerOfLaborLabel.setBounds(20, 440, 300, 20);
        this.add(commissionerOfLaborLabel);

        commissionerOfLaborComboBox = new JComboBox<>();
        commissionerOfLaborComboBox.setFont(new Font("Verdana", Font.PLAIN, 12));
        commissionerOfLaborComboBox.setBounds(20, 470, 300, 20);
        this.add(commissionerOfLaborComboBox);

        JLabel secretaryOfStateLabel = new JLabel("Secretary of State");
        secretaryOfStateLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        secretaryOfStateLabel.setBounds(340, 440, 300, 20);
        this.add(secretaryOfStateLabel);

        secretaryOfStateComboBox = new JComboBox<>();
        secretaryOfStateComboBox.setFont(new Font("Verdana", Font.PLAIN, 12));
        secretaryOfStateComboBox.setBounds(340, 470, 300, 20);
        this.add(secretaryOfStateComboBox);

        JLabel treasurerLabel = new JLabel("Treasurer");
        treasurerLabel.setFont(new Font("Verdana", Font.BOLD, 12));
        treasurerLabel.setBounds(660, 440, 300, 20);
        this.add(treasurerLabel);

        treasurerComboBox = new JComboBox<>();
        treasurerComboBox.setFont(new Font("Verdana", Font.PLAIN, 12));
        treasurerComboBox.setBounds(660, 470, 300, 20);
        this.add(treasurerComboBox);

        termsAndConditionsBox = new JCheckBox("Do you accept the terms and conditions?");
        termsAndConditionsBox.setFont(new Font("Verdana", Font.PLAIN, 12));
        termsAndConditionsBox.setBounds(20, 550, 500, 20);
        termsCheck = false;
        termsAndConditionsBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                termsCheck = !termsCheck;
            }
        });
        this.add(termsAndConditionsBox);

        finishBallotButton = new JButton("Finish Ballot and Send to Audit");
        finishBallotButton.setFont(new Font("Verdana", Font.BOLD, 12));
        finishBallotButton.setBounds(20, 620, 250, 25);
        finishBallotButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (termsCheck) {
                    int stateIndex = 0;
                    for (int i = 0; i < STATES.length; i++) {
                        if (STATES[i].equals((String) chooseState.getSelectedItem())) {
                            stateIndex = i;
                            break;
                        }
                    }
                    String stateAbbrev = STATES_ABBREV[stateIndex];
                    if (isCountyInMaster(stateAbbrev, countyField.getText())) { // if state and county are a valid ballot
                        String namePartyOffice = getNamePartyOffice(stateAbbrev, countyField.getText());
                        String[] namePartyOfficeSplit = namePartyOffice.split("-");
                        ArrayList<String[]> namePartyOfficeMasterSplit = new ArrayList<>();
                        for (String tmp : namePartyOfficeSplit) {
                            namePartyOfficeMasterSplit.add(tmp.split(","));
                        }
                        String voteCount = getVoteCount(stateAbbrev, countyField.getText());
                        String[] voteCountSplit = voteCount.split(",");
                        int[] voteCountSplitInt = new int[voteCountSplit.length];
                        for (int i = 0; i < voteCountSplit.length; i++) {
                            voteCountSplitInt[i] = Integer.parseInt(voteCountSplit[i]);
                        }

                        String presidentChosen = (String) presidentComboBox.getSelectedItem();
                        String representativeChosen = (String) representativeComboBox.getSelectedItem();
                        String senateChosen = (String) senateComboBox.getSelectedItem();
                        String governorChosen = (String) governorComboBox.getSelectedItem();
                        String lieutenantGovernorChosen = (String) lieutenantGovernorComboBox.getSelectedItem();
                        String attorneyGeneralChosen = (String) attorneyGeneralComboBox.getSelectedItem();
                        String auditorChosen = (String) auditorComboBox.getSelectedItem();
                        String commissionerOfAgricultureChosen = (String) commissionerOfAgricultureComboBox.getSelectedItem();
                        String commissionerOfInsuranceChosen = (String) commissionerOfInsuranceComboBox.getSelectedItem();
                        String commissionerOfLaborChosen = (String) commissionerOfLaborComboBox.getSelectedItem();
                        String secretaryOfStateChosen = (String) secretaryOfStateComboBox.getSelectedItem();
                        String treasurerChosen = (String) treasurerComboBox.getSelectedItem();

                        for (int i = 0; i < namePartyOfficeMasterSplit.size(); i++) {
                            String tmp = namePartyOfficeMasterSplit.get(i)[0] + ", " + namePartyOfficeMasterSplit.get(i)[1];
                            if (tmp.equals(presidentChosen)) {
                                voteCountSplitInt[i] += 1;
                            }
                            if (tmp.equals(representativeChosen)) {
                                voteCountSplitInt[i] += 1;
                            }
                            if (tmp.equals(senateChosen)) {
                                voteCountSplitInt[i] += 1;
                            }
                            if (tmp.equals(governorChosen)) {
                                voteCountSplitInt[i] += 1;
                            }
                            if (tmp.equals(lieutenantGovernorChosen)) {
                                voteCountSplitInt[i] += 1;
                            }
                            if (tmp.equals(attorneyGeneralChosen)) {
                                voteCountSplitInt[i] += 1;
                            }
                            if (tmp.equals(auditorChosen)) {
                                voteCountSplitInt[i] += 1;
                            }
                            if (tmp.equals(commissionerOfAgricultureChosen)) {
                                voteCountSplitInt[i] += 1;
                            }
                            if (tmp.equals(commissionerOfInsuranceChosen)) {
                                voteCountSplitInt[i] += 1;
                            }
                            if (tmp.equals(commissionerOfLaborChosen)) {
                                voteCountSplitInt[i] += 1;
                            }
                            if (tmp.equals(secretaryOfStateChosen)) {
                                voteCountSplitInt[i] += 1;
                            }
                            if (tmp.equals(treasurerChosen)) {
                                voteCountSplitInt[i] += 1;
                            }
                        }

                        StringBuilder newVoteCount = new StringBuilder();

                        for (int i = 0; i < voteCountSplitInt.length; i++) {
                            newVoteCount.append(String.valueOf(voteCountSplitInt[i]));
                            if (i != voteCountSplitInt.length - 1) {
                                newVoteCount.append(",");
                            }
                        }

                        System.out.println("SEND THIS TO SQL COLUMN 4!!!!! ~> " + newVoteCount); // TODO SEND TO SQL

                        // TODO REPLACE USER HAS_VOTED TO 1

//                        Connection connection = null; // manages connection
//                        Statement statement = null; // query statement
//                        ResultSet resultSet = null; // manages results

//                        try {
//
//                            // establish connection to database
//                            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
//                            statement = connection.createStatement();
//                            resultSet = statement.executeQuery("SELECT * FROM MASTER");
//
//                            String query = " insert into MASTER(vote_count)"
//                                    + " values (?)";
//
//                            PreparedStatement preparedStmt = connection.prepareStatement(query);
//                            preparedStmt.setString(1, String.valueOf(newVoteCount));
//
//
//                            preparedStmt.execute();
//
//                            connection.close();
//
//                        } catch (SQLException throwables) {
//                            throwables.printStackTrace();
//                        }

                    }

                } else {
                    JOptionPane.showMessageDialog(finishBallotButton, "Please accept the terms and conditions.", "Ballot Submission Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        this.add(finishBallotButton);

        getCandidatesButton = new JButton("Get Candidates...");
        getCandidatesButton.setFont(new Font("Verdana", Font.BOLD, 12));
        getCandidatesButton.setBounds(340, 130, 170, 20);
        getCandidatesButton.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                int stateIndex = 0;
                for (int i = 0; i < STATES.length; i++) {
                    if (STATES[i].equals((String) chooseState.getSelectedItem())) {
                        stateIndex = i;
                        break;
                    }
                }
                String stateAbbrev = STATES_ABBREV[stateIndex];
                if (isCountyInMaster(stateAbbrev, countyField.getText())) { // if state and county are a valid ballot
                    String namePartyOffice = getNamePartyOffice(stateAbbrev, countyField.getText());
                    String[] namePartyOfficeSplit = namePartyOffice.split("-");
                    ArrayList<String[]> namePartyOfficeMasterSplit = new ArrayList<>();
                    for (String tmp : namePartyOfficeSplit) {
                        namePartyOfficeMasterSplit.add(tmp.split(","));
                    }
                    ArrayList<String> presidentCands = new ArrayList<>();
                    ArrayList<String> representativeCands = new ArrayList<>();
                    ArrayList<String> senateCands = new ArrayList<>();
                    ArrayList<String> governorCands = new ArrayList<>();
                    ArrayList<String> lieutenantGovernorCands = new ArrayList<>();
                    ArrayList<String> attorneyGeneralCands = new ArrayList<>();
                    ArrayList<String> auditorCands = new ArrayList<>();
                    ArrayList<String> commissionerOfAgricultorCands = new ArrayList<>();
                    ArrayList<String> commissionerOfInsuranceCands = new ArrayList<>();
                    ArrayList<String> commissionerOfLaborCands = new ArrayList<>();
                    ArrayList<String> secretaryOfStateCands = new ArrayList<>();
                    ArrayList<String> treasurerCands = new ArrayList<>();
                    for (String[] tmp : namePartyOfficeMasterSplit) {
                        if (tmp[2].equals("President")) {
                            presidentCands.add(tmp[0] + ", " + tmp[1]);
                        }
                        if (tmp[2].equals("Representative")) {
                            representativeCands.add(tmp[0] + ", " + tmp[1]);
                        }
                        if (tmp[2].equals("Senate")) {
                            senateCands.add(tmp[0] + ", " + tmp[1]);
                        }
                        if (tmp[2].equals("Governor")) {
                            governorCands.add(tmp[0] + ", " + tmp[1]);
                        }
                        if (tmp[2].equals("Lieutenant Governor")) {
                            lieutenantGovernorCands.add(tmp[0] + ", " + tmp[1]);
                        }
                        if (tmp[2].equals("Attorney General")) {
                            attorneyGeneralCands.add(tmp[0] + ", " + tmp[1]);
                        }
                        if (tmp[2].equals("Auditor")) {
                            auditorCands.add(tmp[0] + ", " + tmp[1]);
                        }
                        if (tmp[2].equals("Commissioner Of Agriculture")) {
                            commissionerOfAgricultorCands.add(tmp[0] + ", " + tmp[1]);
                        }
                        if (tmp[2].equals("Commissioner Of Insurance")) {
                            commissionerOfInsuranceCands.add(tmp[0] + ", " + tmp[1]);
                        }
                        if (tmp[2].equals("Commissioner Of Labor")) {
                            commissionerOfLaborCands.add(tmp[0] + ", " + tmp[1]);
                        }
                        if (tmp[2].equals("Secretary Of State")) {
                            secretaryOfStateCands.add(tmp[0] + ", " + tmp[1]);
                        }
                        if (tmp[2].equals("Treasurer")) {
                            treasurerCands.add(tmp[0] + ", " + tmp[1]);
                        }
                    }
                    String[] tmp1 = new String[presidentCands.size()];
                    for (int i = 0; i < presidentCands.size(); i++) {
                        tmp1[i] = presidentCands.get(i);
                    }
                    DefaultComboBoxModel<String> modelPres = new DefaultComboBoxModel<>(tmp1);
                    presidentComboBox.setModel(modelPres);
                    String[] tmp2 = new String[representativeCands.size()];
                    for (int i = 0; i < representativeCands.size(); i++) {
                        tmp2[i] = representativeCands.get(i);
                    }
                    DefaultComboBoxModel<String> modelRepresentative = new DefaultComboBoxModel<>(tmp2);
                    representativeComboBox.setModel(modelRepresentative);
                    String[] tmp3 = new String[senateCands.size()];
                    for (int i = 0; i < senateCands.size(); i++) {
                        tmp3[i] = senateCands.get(i);
                    }
                    DefaultComboBoxModel<String> modelSenate = new DefaultComboBoxModel<>(tmp3);
                    senateComboBox.setModel(modelSenate);
                    String[] tmp4 = new String[governorCands.size()];
                    for (int i = 0; i < governorCands.size(); i++) {
                        tmp4[i] = governorCands.get(i);
                    }
                    DefaultComboBoxModel<String> modelGovernor = new DefaultComboBoxModel<>(tmp4);
                    governorComboBox.setModel(modelGovernor);
                    String[] tmp5 = new String[lieutenantGovernorCands.size()];
                    for (int i = 0; i < lieutenantGovernorCands.size(); i++) {
                        tmp5[i] = lieutenantGovernorCands.get(i);
                    }
                    DefaultComboBoxModel<String> modelLieutenantGovernor = new DefaultComboBoxModel<>(tmp5);
                    lieutenantGovernorComboBox.setModel(modelLieutenantGovernor);
                    String[] tmp6 = new String[attorneyGeneralCands.size()];
                    for (int i = 0; i < attorneyGeneralCands.size(); i++) {
                        tmp6[i] = attorneyGeneralCands.get(i);
                    }
                    DefaultComboBoxModel<String> modelAttorneyGeneral = new DefaultComboBoxModel<>(tmp6);
                    attorneyGeneralComboBox.setModel(modelAttorneyGeneral);
                    String[] tmp7 = new String[auditorCands.size()];
                    for (int i = 0; i < auditorCands.size(); i++) {
                        tmp7[i] = auditorCands.get(i);
                    }
                    DefaultComboBoxModel<String> modelAuditor = new DefaultComboBoxModel<>(tmp7);
                    auditorComboBox.setModel(modelAuditor);
                    String[] tmp8 = new String[commissionerOfAgricultorCands.size()];
                    for (int i = 0; i < commissionerOfAgricultorCands.size(); i++) {
                        tmp8[i] = commissionerOfAgricultorCands.get(i);
                    }
                    DefaultComboBoxModel<String> modelCommissionerOfAgriculture = new DefaultComboBoxModel<>(tmp8);
                    commissionerOfAgricultureComboBox.setModel(modelCommissionerOfAgriculture);
                    String[] tmp9 = new String[commissionerOfInsuranceCands.size()];
                    for (int i = 0; i < commissionerOfInsuranceCands.size(); i++) {
                        tmp9[i] = commissionerOfInsuranceCands.get(i);
                    }
                    DefaultComboBoxModel<String> modelCommissionerOfInsurance = new DefaultComboBoxModel<>(tmp9);
                    commissionerOfInsuranceComboBox.setModel(modelCommissionerOfInsurance);
                    String[] tmp10 = new String[commissionerOfLaborCands.size()];
                    for (int i = 0; i < commissionerOfLaborCands.size(); i++) {
                        tmp10[i] = commissionerOfLaborCands.get(i);
                    }
                    DefaultComboBoxModel<String> modelCommissionerOfLabor = new DefaultComboBoxModel<>(tmp10);
                    commissionerOfLaborComboBox.setModel(modelCommissionerOfLabor);
                    String[] tmp11 = new String[secretaryOfStateCands.size()];
                    for (int i = 0; i < secretaryOfStateCands.size(); i++) {
                        tmp11[i] = secretaryOfStateCands.get(i);
                    }
                    DefaultComboBoxModel<String> modelSecretaryOfState = new DefaultComboBoxModel<>(tmp11);
                    secretaryOfStateComboBox.setModel(modelSecretaryOfState);
                    String[] tmp12 = new String[treasurerCands.size()];
                    for (int i = 0; i < treasurerCands.size(); i++) {
                        tmp12[i] = treasurerCands.get(i);
                    }
                    DefaultComboBoxModel<String> modelTreasurer = new DefaultComboBoxModel<>(tmp12);
                    treasurerComboBox.setModel(modelTreasurer);
                } else {
                    System.out.println("state and county not in list");
                }
            }
        });
        this.add(getCandidatesButton);

        errorLabel = new JLabel(">>> AN ERROR WOULD GO HERE <<<");
        errorLabel.setFont(new Font("Verdana", Font.ITALIC, 12));
        errorLabel.setForeground(Color.RED);
        errorLabel.setBounds(550, 130, 450, 20);
        this.add(errorLabel);

        returnToLoginButton = new JButton("Return to Login Screen");
        returnToLoginButton.setFont(new Font("Verdana", Font.BOLD, 12));
        returnToLoginButton.setBounds(20, 690, 250, 25);
        this.add(returnToLoginButton);

    }

    /**
     * Sets the Social Security number in the corner to identify who's currently voting
     * @param SSN social security number
     */
    public void setSSN(int SSN) {
        String SSNString = String.valueOf(SSN);
        digitalBallotSSN.setText("Digital Ballot for SSN *****" + SSNString.charAt(5) + SSNString.charAt(6) + SSNString.charAt(7) + SSNString.charAt(8));
    }

    /**
     * Used when testing to make sure data is accurate to the SQL database
     */
    private void printMasterTable() {

        Connection connection = null; // manages connection
        Statement statement = null; // query statement
        ResultSet resultSet = null; // manages results

        try {

            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM MASTER");

            ResultSetMetaData metaData = resultSet.getMetaData();
            int numColumns = metaData.getColumnCount();
            System.out.println("Voter Information Database");

            for (int i = 1; i <= numColumns; i++) {
                System.out.printf("%-8s\t", metaData.getColumnName(i));
            }
            System.out.println();

            while (resultSet.next()) {
                for (int i = 1; i <= numColumns; i++) {
                    System.out.printf("%-8s\t", resultSet.getObject(i));
                }
                System.out.println();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**
     * To check that the county exists in the master table
     * @param state state citizen is voting in
     * @param county county citizen is voting in
     * @return true for in table, false for not
     */
    private boolean isCountyInMaster(String state, String county) {

        Connection connection = null; // manages connection
        Statement statement = null; // query statement
        ResultSet resultSet = null; // manages results

        ArrayList<String> stateList = new ArrayList<>();
        ArrayList<String> countyList = new ArrayList<>();

        try {

            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM MASTER");

            ResultSetMetaData metaData = resultSet.getMetaData();
            int numColumns = metaData.getColumnCount();

            while (resultSet.next()) {
                stateList.add(String.valueOf(resultSet.getObject(1)));
                countyList.add(String.valueOf(resultSet.getObject(2)));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if (stateList.contains(state) && countyList.contains(county)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Get the string containing all of the nominees
     * @param state state it's in
     * @param county county it's in
     * @return the string containing all of the nominees
     */
    private String getNamePartyOffice(String state, String county) {

        Connection connection = null; // manages connection
        Statement statement = null; // query statement
        ResultSet resultSet = null; // manages results

        try {

            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM MASTER");

            ResultSetMetaData metaData = resultSet.getMetaData();
            int numColumns = metaData.getColumnCount();

            while (resultSet.next()) {
                if (String.valueOf(resultSet.getObject(1)).equals(state) && String.valueOf(resultSet.getObject(2)).equals(county)) {
                    return String.valueOf(resultSet.getObject(3));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return "";

    }

    /**
     * Get the string containing vote count information
     * @param state state of interest
     * @param county county of interest
     * @return string containing vote count information
     */
    private String getVoteCount(String state, String county) {

        Connection connection = null; // manages connection
        Statement statement = null; // query statement
        ResultSet resultSet = null; // manages results

        try {

            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM MASTER");

            ResultSetMetaData metaData = resultSet.getMetaData();
            int numColumns = metaData.getColumnCount();

            while (resultSet.next()) {
                if (String.valueOf(resultSet.getObject(1)).equals(state) && String.valueOf(resultSet.getObject(2)).equals(county)) {
                    return String.valueOf(resultSet.getObject(4));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return "";

    }

}
