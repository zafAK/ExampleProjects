import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Class which represents the Welcome Panel, it is the first panel the user see's
 */
public class WelcomePanel extends JPanel {
    private JLabel imageLabel;
    private BufferedImage logoImage;
    private JPanel imagePanel;
    private ImageIcon logoImageIcon;

    private JLabel headerLabel;

    private JLabel auditorLabel;
    private JLabel votingLabel;


    private JLabel auditorSSNLabel;
    private JLabel votingSSNLabel;

    public JPasswordField auditorSSNPasswordField;
    public JPasswordField votingSSNPasswordField;

    private JCheckBox auditorShowPasswordCheckbox;
    private JCheckBox votingShowPasswordCheckbox;

    public JButton enterButton;
    public JButton votingResultsButton;
    public JButton helpButton;

    private JLabel ghandiLabel;
    private JPanel ghandiPanel;
    private BufferedImage ghandiImage;
    private ImageIcon ghandiImageIcon;

    private JLabel ghandiQuoteLabel;
    private JLabel ghandiSignLabel;

    public JSlider volumeSlider;
    private JLabel volumeLabel;

    /**
     * Constructor which is used to add everything to the JPanel
     */
    public WelcomePanel() {
        //sets the size of the panel
        setPreferredSize(new Dimension(1000, 800));
        this.setLayout(null);

        //logo
        imagePanel = new JPanel();
        try {
            logoImage = ImageIO.read(new File("TeamProject/logo.jpg"));
        }
        catch (IOException e) {
            System.out.println("ERROR: Image is not being read.");
        }

        logoImageIcon = new ImageIcon(logoImage);
        imageLabel = new JLabel(logoImageIcon);

//        imagePanel.setBorder(BorderFactory.createEmptyBorder(100,100,50,100));
        imagePanel.setLayout(new GridLayout(0,1));
        imagePanel.setSize(200,50);
        imagePanel.add(imageLabel);

        // ghandi
        ghandiPanel = new JPanel();
        try {
            ghandiImage = ImageIO.read(new File("TeamProject/ghandi.jpg"));
        } catch (IOException e) {
            System.out.println("ERROR: Image is not being read.");
        }

        ghandiImageIcon = new ImageIcon(ghandiImage);
        ghandiLabel = new JLabel(ghandiImageIcon);

//        ghandiPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        ghandiPanel.setLayout(new GridLayout(0, 1));
        ghandiPanel.setSize(200, 50);
        ghandiPanel.add(ghandiLabel);

        ghandiQuoteLabel = new JLabel("\"Pokémon GO to the polls\"");
        ghandiQuoteLabel.setFont(new Font("Verdana", Font.ITALIC,15));

        ghandiSignLabel = new JLabel("- Ghandi, 2016");
        ghandiSignLabel.setFont(new Font("Verdana", Font.BOLD,15));

        //header
        headerLabel = new JLabel("A 21st Century National Voting System");
        headerLabel.setFont(new Font("Verdana",Font.BOLD, 36));

        //auditor label
        auditorLabel = new JLabel("Auditor");
        auditorLabel.setFont(new Font("Verdana", Font.BOLD, 25));

        //voting label
        votingLabel = new JLabel("Voter");
        votingLabel.setFont(new Font("Verdana", Font.BOLD, 25));

        //auditor ssn label with textfield
        auditorSSNLabel = new JLabel("SSN:");
        auditorSSNLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
        auditorSSNPasswordField = new JPasswordField(15);

        //voting ssn label with textfield
        votingSSNLabel = new JLabel("SSN:");
        votingSSNLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
        votingSSNPasswordField = new JPasswordField(15);

        //checkbox
        auditorShowPasswordCheckbox = new JCheckBox("Show Password");
        auditorShowPasswordCheckbox.setFont(new Font("Verdana",Font.PLAIN,15));
        auditorShowPasswordCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(auditorShowPasswordCheckbox.isSelected()) {
                    auditorSSNPasswordField.setEchoChar((char)0);
                }
                else {
                    auditorSSNPasswordField.setEchoChar('*');
                }
            }
        });

        votingShowPasswordCheckbox = new JCheckBox("Show Password");
        votingShowPasswordCheckbox.setFont(new Font("Verdana",Font.PLAIN,15));
        votingShowPasswordCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(votingShowPasswordCheckbox.isSelected()) {
                    votingSSNPasswordField.setEchoChar((char)0);
                }
                else {
                    votingSSNPasswordField.setEchoChar('*');
                }
            }
        });

        //all buttons
        enterButton = new JButton("ENTER");
        enterButton.setFont(new Font("Verdana", Font.BOLD,15));

        votingResultsButton = new JButton("View Voting Results");
        votingResultsButton.setFont(new Font("Verdana", Font.BOLD,15));

        helpButton = new JButton("Help?");
        helpButton.setFont(new Font("Verdana", Font.BOLD,15));

        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 1);
        volumeSlider.setValue(25);

        volumeLabel = new JLabel("Volume");
        volumeLabel.setFont(new Font("Verdana", Font.BOLD,12));

        //sets bounds where everything will be
        imagePanel.setBounds(250,-50,500,240);
        ghandiPanel.setBounds(720, 540, 300, 300);
        headerLabel.setBounds(95,160,1000,100);
        auditorLabel.setBounds(220,300,150,25);
        votingLabel.setBounds(680,300,100,25);
        auditorSSNLabel.setBounds(142,350,50,25);
        auditorSSNPasswordField.setBounds(180,350,200,25);
        votingSSNLabel.setBounds(580,350,50,25);
        votingSSNPasswordField.setBounds(620,350,200,25);
        auditorShowPasswordCheckbox.setBounds(200,380,250,25);
        votingShowPasswordCheckbox.setBounds(650,380,250,25);
        enterButton.setBounds(360,450,250,25);
        votingResultsButton.setBounds(360,490,250,25);
        helpButton.setBounds(50,700,250,25);
        ghandiQuoteLabel.setBounds(520, 650, 350, 50);
        ghandiSignLabel.setBounds(540, 680, 300, 50);
        volumeSlider.setBounds(840, 5, 150, 30);
        volumeLabel.setBounds(770, 3, 100, 30);

        add(imagePanel);
        add(ghandiPanel);
        add(headerLabel);
        add(auditorLabel);
        add(votingLabel);
        add(auditorSSNLabel);
        add(auditorSSNPasswordField);
        add(votingSSNLabel);
        add(votingSSNPasswordField);
        add(auditorShowPasswordCheckbox);
        add(votingShowPasswordCheckbox);
        add(enterButton);
        add(votingResultsButton);
        add(helpButton);
        add(ghandiQuoteLabel);
        add(ghandiSignLabel);
        add(volumeSlider);
        add(volumeLabel);
        this.repaint();
        setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawLine(485,250,485,425);
    }
}
