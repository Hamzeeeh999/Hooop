import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Game extends JFrame implements ActionListener{

    private JPanel panel;
    private JLabel backgroundLabel;
    private JButton startGame;
    private JTextField name1, name2, name3, name4;
    private ImageIcon gameBg;
    private int NumberOfPlayers;

    public Game(int numberOfPlayers) {
        this.NumberOfPlayers = numberOfPlayers;

        JFrame frame = new JFrame();

        Font fontStyle2 = FontLoader.load("./Assets/Whipsnapper W05 Black.ttf",52f);
        Font fontStyle3 = FontLoader.load("./Assets/Whipsnapper W05 Black.ttf", 86f);

        if(numberOfPlayers == 4) {
            gameBg = new ImageIcon("./Assets/Main-Menu-4.jpg");
        } 
        else {
            gameBg = new ImageIcon("./Assets/Main-Menu-3.jpg");
        }

        backgroundLabel = new ScaledImageLabel(gameBg.getImage());
        name1 = new JTextField("");
        name1.setFont(fontStyle2);
        name1.setBounds(200, 470,575,80);
        name1.setHorizontalAlignment(JTextField.CENTER);
        name1.setOpaque(false);
        name1.setForeground(Color.WHITE);
        name1.setBorder(null);
        name2 = new JTextField("");
        name2.setFont(fontStyle2);
        name2.setBounds(1125, 470,575,80);
        name2.setHorizontalAlignment(JTextField.CENTER);
        name2.setOpaque(false);
        name2.setForeground(Color.WHITE);
        name2.setBorder(null);
        backgroundLabel.add(name1);
        backgroundLabel.add(name2);

        if(numberOfPlayers == 4) {
            name3 = new JTextField("");
            name3.setFont(fontStyle2);
            name3.setBounds(200, 680,575,80);
            name3.setHorizontalAlignment(JTextField.CENTER);
            name3.setOpaque(false);
            name3.setForeground(Color.WHITE);
            name3.setBorder(null);
            name1 = new JTextField("");
    
            name4 = new JTextField("");
            name4.setFont(fontStyle2);
            name4.setBounds(1125, 680,575,80);
            name4.setHorizontalAlignment(JTextField.CENTER);
            name4.setOpaque(false);
            name4.setForeground(Color.WHITE);
            name4.setBorder(null);
            backgroundLabel.add(name3);
            backgroundLabel.add(name4);

        }

        startGame = new JButton("Start Game");
        startGame.setFont(fontStyle3);
        startGame.setBounds(660, 840,595,135);
        startGame.setOpaque(false);
        startGame.setContentAreaFilled(false);
        startGame.setBorderPainted(false);
        startGame.setFocusPainted(false);
        startGame.setForeground(Color.WHITE);
        startGame.addActionListener(this);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        panel.add(backgroundLabel);
        backgroundLabel.add(startGame);
        frame.add(panel);


        frame.setSize(1920,1080);
        ScreenScaler.scaleFrame(frame);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
	    frame.setResizable(false);
	    frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    
    
}
public void actionPerformed(ActionEvent e) {

        Player p1 = new Player(name1.getText());
        Player p2 = new Player(name2.getText());

    if (NumberOfPlayers >2){
        Player p3 = new Player(name3.getText());
        Player p4 = new Player(name4.getText());
    }
        if (e.getSource()==startGame) {
            // Start the game with the selected number of players
            this.dispose();
            Board Board = new Board(p1.getPlayers(), NumberOfPlayers);
            // You can add code here to transition to the game screen
        }
    }
}


