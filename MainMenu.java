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
        begin.setOpaque(false);
        begin.setContentAreaFilled(false);
        //begin.setBorderPainted(false);
        begin.setFocusPainted(false);
        begin.setForeground(Color.WHITE);
        begin.addActionListener(this);

        loadGame = new JButton("Load game"); 
        loadGame.setFont(fontStyle4);
        loadGame.setBounds(1011, 800,495,115);
        loadGame.setOpaque(false);
        loadGame.setContentAreaFilled(false);
        //loadGame.setBorderPainted(false);
        loadGame.setFocusPainted(false);
        loadGame.setForeground(Color.WHITE);
        loadGame.addActionListener(this);


        twoPlayers = new JButton("2 PLAYERS");
        twoPlayers.setFont(fontStyle2);
        twoPlayers.setBounds(550, 907,365,85);
        twoPlayers.setOpaque(false);
        twoPlayers.setContentAreaFilled(false);
        twoPlayers.setBorderPainted(false);
        twoPlayers.setFocusPainted(false);
        twoPlayers.setForeground(Color.WHITE);
        twoPlayers.addActionListener(this);

        fourPlayers = new JButton("4 PLAYERS");
        fourPlayers.setFont(fontStyle2);
        fourPlayers.setBounds(1000, 907,365,85);
        fourPlayers.setOpaque(false);
        fourPlayers.setContentAreaFilled(false);
        fourPlayers.setBorderPainted(false);
        fourPlayers.setFocusPainted(false);
        fourPlayers.setForeground(Color.WHITE);
        fourPlayers.addActionListener(this);

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