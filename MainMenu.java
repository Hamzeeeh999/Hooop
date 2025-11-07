import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainMenu extends JFrame implements ActionListener {
    private JPanel panel;
    private JLabel firstLabel, secondLabel;
    private JButton begin,twoPlayers, fourPlayers,startGame,loadGame;
    public String player1, player2, player3, player4;


    public MainMenu()
    {

    
        JFrame frame = new JFrame("");
        Font fontStyle = FontLoader.load("./Assets/Whipsnapper W05 Black.ttf",102f);
        Font fontStyle2 = FontLoader.load("./Assets/Whipsnapper W05 Black.ttf",52f);
        Font fontStyle4 = FontLoader.load("./Assets/Whipsnapper W05 Black.ttf",78f);
        Font fontStyle3 = FontLoader.load("./Assets/Whipsnapper W05 Black.ttf", 86f);
        ImageIcon firstBg = new ImageIcon("./Assets/Main-Menu.jpg");
        firstLabel = new JLabel();
        firstLabel.setIcon(firstBg);


        ImageIcon secondBg = new ImageIcon("./Assets/Main-Menu-2.jpg");
        secondLabel = new JLabel();
        secondLabel.setIcon(secondBg);
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        begin = new JButton("Start");
        begin.setFont(fontStyle3); 
        begin.setBounds(405, 800,495,115);
        styleButton(begin);

        loadGame = new JButton("Load game"); 
        loadGame.setFont(fontStyle4);
        loadGame.setBounds(1011, 800,495,115);
        styleButton(loadGame);


        twoPlayers = new JButton("2 PLAYERS");
        twoPlayers.setFont(fontStyle2);
        twoPlayers.setBounds(550, 907,365,85);
        styleButton(twoPlayers);

        fourPlayers = new JButton("4 PLAYERS");
        fourPlayers.setFont(fontStyle2);
        fourPlayers.setBounds(1000, 907,365,85);
        styleButton(fourPlayers);

        panel.add(firstLabel);
        firstLabel.add(begin);
        firstLabel.add(loadGame);
        frame.add(panel);
    
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
		frame.setResizable(false);
		frame.setVisible(true);
        frame.setSize(1920,1080);

    }
    private void styleButton (JButton b){
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setForeground(Color.WHITE);
        b.addActionListener(this);
    }


    public void actionPerformed(ActionEvent e) {

        Object clicked = e.getSource();

        if (clicked.equals(begin)){
            panel.add(secondLabel);
            firstLabel.setVisible(false);
            secondLabel.add(twoPlayers);
            secondLabel.add(fourPlayers);

            if (e.getSource()==twoPlayers) {
                int numberOfPlayers = 2;
                Game demo = new Game(numberOfPlayers);
            // Add logic for 2 players
            } 
            else if (e.getSource()==fourPlayers) {
                int numberOfPlayers = 4;
                Game demo = new Game(numberOfPlayers);

            }

        }

        if (clicked.equals(loadGame)){

            System.out.println("Hello");
        }
    

    }
}