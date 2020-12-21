import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.Map;

/**
 * Class which represents Help panel. Provides information where if one is having problem with GUI
 */
public class HelpPanel extends JPanel {
    private JLabel headerLabel;
    private JLabel loginHelpLabel;
    private JTextArea loginHelpInfoTextArea;
    private JLabel auditorHelpLabel;
    private JTextArea auditorHelpInfoTextArea;
    private JLabel votingHelpLabel;
    private JTextArea votingHelpInfoTextArea;
    public JButton backToLoginButton;
    private JLabel customerSupportLabel;

    /**
     * Constructor which is used to add everything to the JPanel
     */
    public HelpPanel() {
        setPreferredSize(new Dimension(1000, 800)); // set dimensions of JPanel
        this.setLayout(null); // I want to create my own layout

        headerLabel = new JLabel("Help Page");
        headerLabel.setFont(new Font("Verdana",Font.BOLD, 25));
        //used for creating an underline. got code from: https://www.studyon.minte9.com/java-se/swing/jlabel-underline-i1047
//        Font headerFont = headerLabel.getFont();
//        Map headerAttributes = headerFont.getAttributes();
//        headerAttributes.put(TextAttribute.UNDERLINE,TextAttribute.UNDERLINE_ON);
//        headerLabel.setFont(headerFont.deriveFont(headerAttributes));

        loginHelpLabel = new JLabel("Login Help:");
        loginHelpLabel.setFont(new Font("Verdana",Font.BOLD, 25));
        //used for creating an underline. got code from: https://www.studyon.minte9.com/java-se/swing/jlabel-underline-i1047
//        Font loginHelpFont = loginHelpLabel.getFont();
//        Map loginHelpAttributes = loginHelpFont.getAttributes();
//        loginHelpAttributes.put(TextAttribute.UNDERLINE,TextAttribute.UNDERLINE_ON);
//        loginHelpLabel.setFont(loginHelpFont.deriveFont(headerAttributes));

        loginHelpInfoTextArea = new JTextArea();
        loginHelpInfoTextArea.setBackground(getBackground());
        loginHelpInfoTextArea.setText("Question: I am having trouble logging in as a voting portal, what is the issue? \nAns: This could be for a two reasons. If you have already" +
                " voted or entered your social security number incorrectly then you will not be able to log in. \n        If the problems happens to be on our end, " +
                "we are currently working on getting the issue resolved.\n \nQuestion: I am having trouble logging into the auditor portal, what is the issue? \nAns: The only reason for " +
                "that is that you are entering your social security number incorrectly. Ifthe problem happens to be on our end, we are\n         currently working on " +
                "getting the issue resolved.");
        loginHelpInfoTextArea.setEditable(false);


        auditorHelpLabel = new JLabel("Auditor Help:");
        auditorHelpLabel.setFont(new Font("Verdana",Font.BOLD, 25));
        //used for creating an underline. got code from: https://www.studyon.minte9.com/java-se/swing/jlabel-underline-i1047
//        Font auditorHelpFont = auditorHelpLabel.getFont();
//        Map auditorHelpAttributes = auditorHelpFont.getAttributes();
//        auditorHelpAttributes.put(TextAttribute.UNDERLINE,TextAttribute.UNDERLINE_ON);
//        auditorHelpLabel.setFont(headerFont.deriveFont(auditorHelpAttributes));

        auditorHelpInfoTextArea = new JTextArea();
        auditorHelpInfoTextArea.setBackground(getBackground());
        auditorHelpInfoTextArea.setText("Question: How do I add more than one person to a ballot? \nAns: There will be an add button under the module where you entered " +
                "the persons name. \n \nQuestion: How do I remove someone if I accidently the wrong name or mistyped? \nAns: You can remove someone by pressing the minus button. It is " +
                "located under the module where you entered the persons name. \n \nQuesiton: Is there a limit as to how many people I can add? \nAns: No, you have the ability to add as " +
                "many as you like.");
        auditorHelpInfoTextArea.setEditable(false);

        votingHelpLabel = new JLabel("Voting Help:");
        votingHelpLabel.setFont(new Font("Verdana",Font.BOLD, 25));
        //used for creating an underline. got code from: https://www.studyon.minte9.com/java-se/swing/jlabel-underline-i1047
//        Font votingHelpFont = votingHelpLabel.getFont();
//        Map votingHelpAttributes = votingHelpFont.getAttributes();
//        votingHelpAttributes.put(TextAttribute.UNDERLINE,TextAttribute.UNDERLINE_ON);
//        votingHelpLabel.setFont(votingHelpFont.deriveFont(votingHelpAttributes));

        votingHelpInfoTextArea = new JTextArea();
        votingHelpInfoTextArea.setBackground(getBackground());
        votingHelpInfoTextArea.setText("Question: Can I change my ballot once I have already submitted it? \nAns: No, once you have voted you cannot change your ballot.\n \nQuestion: " +
                "If I fill out my ballot but click the 'Return to Login Screen' button, will my ballot be submitted? \nAns: No, your ballot is only submitted if you press the 'Finish Ballot and " +
                "Return to Audit' button.");
        votingHelpInfoTextArea.setEditable(false);

        backToLoginButton = new JButton("Return to Login Screen");
        backToLoginButton.setFont(new Font("Verdana", Font.BOLD, 12));

        customerSupportLabel = new JLabel("Call (563) 508-5156 for Customer Support");
        customerSupportLabel.setFont(new Font("Verdana",Font.ITALIC, 20));

        headerLabel.setBounds(430,40,300,50);
        loginHelpLabel.setBounds(50,100,200,50);
        loginHelpInfoTextArea.setBounds(50,150,950,115);
        auditorHelpLabel.setBounds(50,270,200,50);
        auditorHelpInfoTextArea.setBounds(50,320,950,200);
        votingHelpLabel.setBounds(50,460,200,50);
        votingHelpInfoTextArea.setBounds(50,520,950,80);
        backToLoginButton.setBounds(50, 690, 250, 25);
        customerSupportLabel.setBounds(50, 620, 500, 50);



        this.add(headerLabel);
        this.add(loginHelpLabel);
        this.add(loginHelpInfoTextArea);
        this.add(auditorHelpLabel);
        this.add(votingHelpLabel);
        this.add(auditorHelpInfoTextArea);
        this.add(votingHelpInfoTextArea);
        this.add(backToLoginButton);
        this.add(customerSupportLabel);

    }
}
