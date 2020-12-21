import javax.print.attribute.standard.Media;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Handles switching between panels
 */
public class GUIDriver {

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException { new GUIDriver(); }

    private final JFrame main = new JFrame("A 21st Century National Voting System by MZN Technologies LLC.");

    private final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    private final Clip audioClip;

    private static final String DATABASE_URL = "jdbc:mysql://s-l112.engr.uiowa.edu:3306/engr_class012";
    private static final String USERNAME = "engr_class012";
    private static final String PASSWORD = "zafarmikenihaal";

    /**
     * Constructor
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     */
    public GUIDriver() throws IOException, UnsupportedAudioFileException, LineUnavailableException {

        //printUserTable();

        WelcomePanel welPanel = new WelcomePanel();
        VotingPanel digitalBallotPanel = new VotingPanel();
        AuditorPanel auditPanel = new AuditorPanel();
        HelpPanel help = new HelpPanel();
        ResultsPanel resPanel = new ResultsPanel();

        // https://www.codejava.net/coding/how-to-play-back-audio-in-java-with-examples
        // File audioFile = new File("TeamProject/moneyMachine.wav");
        File audioFile = new File("TeamProject/patriotic.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        AudioFormat format = audioStream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        audioClip = (Clip) AudioSystem.getLine(info);
        audioClip.open(audioStream);
        audioClip.start();
        audioClip.loop(10);

        // TODO TEMPORARY BECAUSE MUSIC IS ANNOYING
        FloatControl volume = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
        float range = volume.getMaximum() - volume.getMinimum();
        float gain = (range * 0f) + volume.getMinimum();
        volume.setValue(gain);

        // add panels to frame and show only welcome panel on frame
        main.add(welPanel);
        main.pack();
        main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        main.setVisible(true);

        // switch to voting/audit panel if SSN is valid
        welPanel.enterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (welPanel.auditorSSNPasswordField.getPassword().length == 0 && welPanel.votingSSNPasswordField.getPassword().length == 0) { // both fields empty
                    JOptionPane.showMessageDialog(welPanel, "Both SSN fields are empty.", "Login Error", JOptionPane.ERROR_MESSAGE);
                } else if (welPanel.auditorSSNPasswordField.getPassword().length != 0 && welPanel.votingSSNPasswordField.getPassword().length != 0) { // both fields filled in
                    JOptionPane.showMessageDialog(welPanel, "Both SSN fields are filled in, please only enter one.", "Login Error", JOptionPane.ERROR_MESSAGE);
                } else if (welPanel.auditorSSNPasswordField.getPassword().length == 0 && welPanel.votingSSNPasswordField.getPassword().length > 0) { // only voting ssn is filled in
                    if (welPanel.votingSSNPasswordField.getPassword().length != 9) {
                        JOptionPane.showMessageDialog(welPanel, "SSN length must be nine digits long.", "Login Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        if (isPasswordValidSSN(welPanel.votingSSNPasswordField.getPassword())) { // if password typed in text field is a valid SSN
                            if (getSSNList().contains(String.valueOf(welPanel.votingSSNPasswordField.getPassword()))) { // if password entered is in list of SSNs
                                if (!getAlreadyVotedSSNList().contains(String.valueOf(welPanel.votingSSNPasswordField.getPassword()))) {
                                    welPanel.setVisible(false);
                                    main.add(digitalBallotPanel);
                                    digitalBallotPanel.setSSN(Integer.parseInt(String.valueOf(welPanel.votingSSNPasswordField.getPassword())));
                                    digitalBallotPanel.setVisible(true);
                                    welPanel.votingSSNPasswordField.setText("");
                                } else {
                                    JOptionPane.showMessageDialog(welPanel, "You already voted.", "Login Error", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(welPanel, "SSN is not on the list.", "Login Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(welPanel, "Invalid SSN.", "Login Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else if (welPanel.auditorSSNPasswordField.getPassword().length > 0 && welPanel.votingSSNPasswordField.getPassword().length == 0) { // only auditor ssn is filled in
                    if (welPanel.auditorSSNPasswordField.getPassword().length != 9) {
                        JOptionPane.showMessageDialog(welPanel, "SSN length must be nine digits long.", "Login Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        if (isPasswordValidSSN(welPanel.auditorSSNPasswordField.getPassword())) { // if password typed in text field is a valid SSN
                            if (getAuditorSSNList().contains(String.valueOf(welPanel.auditorSSNPasswordField.getPassword()))) { // if password entered is in the list of auditor SSNs
                                welPanel.setVisible(false);
                                main.add(auditPanel);
                                auditPanel.setVisible(true);
                                welPanel.auditorSSNPasswordField.setText("");
                            } else {
                                JOptionPane.showMessageDialog(welPanel, "SSN is not on the list.", "Login Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(welPanel, "Invalid SSN.", "Login Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        // switch back to welcome panel when return to login button is clicked in voting panel
        digitalBallotPanel.returnToLoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                digitalBallotPanel.setVisible(false);
                main.add(welPanel);
                welPanel.setVisible(true);
            }
        });

        // switch back to welcome panel when return to login button is clicked in audit panel
        auditPanel.backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                auditPanel.setVisible(false);
                main.add(welPanel);
                welPanel.setVisible(true);
            }
        });

        welPanel.helpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                welPanel.setVisible(false);
                main.add(help);
                help.setVisible(true);
            }
        });

        // switch back to welcome panel when return to login button is clicked in help panel
        help.backToLoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                help.setVisible(false);
                main.add(welPanel);
                welPanel.setVisible(true);
            }
        });

        welPanel.votingResultsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                welPanel.setVisible(false);
                main.add(resPanel);
                resPanel.setVisible(true);
            }
        });

        resPanel.backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resPanel.setVisible(false);
                main.add(welPanel);
                welPanel.setVisible(true);
            }
        });

        welPanel.volumeSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider)e.getSource();
                if (!source.getValueIsAdjusting()) {
                    float volume = (float) source.getValue() / 100;
                    changeVolume(volume);
                }
            }
        });

    }

    /**
     * Verifies that a password is a valid SSN by checking every individual character and assuring that it's a digit
     * @param password password fetched from text field to be validated as SSN
     * @return true for valid, false for invalid "SSN"
     */
    private boolean isPasswordValidSSN(char[] password) {
        boolean valid;
        for (char character : password) {
            valid = false;
            for (char digit : DIGITS) {
                if (character == digit) {
                    valid = true;
                    break;
                }
            }
            if (!valid) {
                return false;
            }
        }
        return true;
    }

    /**
     * Changes volume of patriotic music
     * @param volumeFloat float in terms of proportion of sound out of one
     */
    private void changeVolume(float volumeFloat) {
        FloatControl volume = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
        float range = volume.getMaximum() - volume.getMinimum();
        float gain = (range * volumeFloat) + volume.getMinimum();
        volume.setValue(gain);
    }

    /**
     * Prints database table, used in testing
     */
    private void printUserTable() {

        Connection connection = null; // manages connection
        Statement statement = null; // query statement
        ResultSet resultSet = null; // manages results

        try {

            // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM USER");

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
     * Retreives list of valid SSNs
     * @return list of valid SSNs
     */
    private ArrayList<String> getSSNList() {

        ArrayList<String> SSNList = new ArrayList<>();

        Connection connection = null; // manages connection
        Statement statement = null; // query statement
        ResultSet resultSet = null; // manages results

        try {

            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM USER");

            while (resultSet.next()) {
                SSNList.add(String.valueOf(resultSet.getObject(1)));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return SSNList;

    }

    /**
     * Retreives list of SSNs that have already voted
     * @return list of SSNs that have already voted
     */
    private ArrayList<String> getAlreadyVotedSSNList() {

        ArrayList<String> alreadyVotedSSNList = new ArrayList<>();

        Connection connection = null; // manages connection
        Statement statement = null; // query statement
        ResultSet resultSet = null; // manages results

        try {

            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM USER");

            while (resultSet.next()) {
                if (String.valueOf(resultSet.getObject(3)).equals("1")) { // if has_voted is set to 1, AKA true
                    alreadyVotedSSNList.add(String.valueOf(resultSet.getObject(1)));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return alreadyVotedSSNList;

    }

    /**
     * Retreive list of SSNs pertaining to the auditors
     * @return list of SSNs for auditors
     */
    private ArrayList<String> getAuditorSSNList() {

        ArrayList<String> AuditorSSNList = new ArrayList<>();

        Connection connection = null; // manages connection
        Statement statement = null; // query statement
        ResultSet resultSet = null; // manages results

        try {

            connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM USER");

            while (resultSet.next()) {
                if (String.valueOf(resultSet.getObject(2)).equals("1")) { // if is_auditor is set to 1, AKA true
                    AuditorSSNList.add(String.valueOf(resultSet.getObject(1)));
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return AuditorSSNList;

    }

}
