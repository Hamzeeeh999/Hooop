import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JFrame implements ActionListener {

    private JLayeredPane pane;
    private JLabel backgroundLabel, leafsLabel, baseLabel;
    public String player1, player2, player3, player4, playerColor;
    private ImageIcon hooop, leafsBg,baseBg;
    private int leafCount = 1, playerCount;
    private String[] playerNames,playerColors = {"Blue", "Yellow","Red","Purple"},playerColors2 = {"Blue","Red"};
    private JButton selectedButton = null, moveFrog, placeBridge, actionCard;
    private Font fontStyle1, fontStyle2, fontStyle3;
    private static int currentIndex = 0;
    private JTextArea turnDisplay;
    private static String turn;
    private JButton[] blueFrogs, yellowFrogs, redFrogs, purpleFrogs;
    private Frog selectedFrog = null;
    private java.util.List<Bridge> bridges = new java.util.ArrayList<>();
    private java.util.List<Leaf> leaves = new java.util.ArrayList<>();
    private boolean isHorizontal(JComponent c) { return c.getWidth() > c.getHeight(); }

    public Board(String[] players, int playerCount) {

        JFrame frame = new JFrame();
        playerNames = new String[playerCount];
        playerNames = players;
        for (int i=0; i<playerCount;i++)
        {
            System.out.println(playerNames[i]);
        }
        this.playerCount = playerCount;
        turn = players[0];
        playerColor = playerColors[currentIndex];

        fontStyle1 = FontLoader.load("./Assets/Whipsnapper W05 Black.ttf",40f);
        fontStyle2 = FontLoader.load("./Assets/Whipsnapper W05 Black.ttf",52f);
        fontStyle3 = FontLoader.load("./Assets/Whipsnapper W05 Black.ttf", 86f);
        // Implement the turn and make it switch once any player is done with their move.

        // Image Imports
        turnDisplay = new JTextArea("It's " + turn +"'s Turn ");
        turnDisplay.setEditable(false);
        turnDisplay.setFont(fontStyle1);
        turnDisplay.setBounds(25, 50, 400, 100);
        turnDisplay.setOpaque(false);
        turnDisplay.setForeground(Color.WHITE);

        blueFrogs = new JButton[3];
        redFrogs = new JButton[3];
        yellowFrogs = new JButton[3];
        purpleFrogs = new JButton[3];

        ImageIcon gameBg = new ImageIcon("./Assets/Main-Menu-5.jpg");
        

        if(playerCount==2){
            baseBg = new ImageIcon("./Assets/Main-Menu-5-4.png");
        }
        else{
            baseBg = new ImageIcon("./Assets/Main-Menu-5-2.png");
        }

        if(playerCount==2){
            leafsBg = new ImageIcon("./Assets/Main-Menu-5-5.png");
        }
        else{
            leafsBg = new ImageIcon("./Assets/Main-Menu-5-3.png");
        }


        // Background Labels
        backgroundLabel = new JLabel(gameBg);
        baseLabel = new JLabel(baseBg);
        leafsLabel = new JLabel(leafsBg);

        moveFrog = new JButton("Move a Frog");
        moveFrog.setBounds(25,100,150,50);
        moveFrog.addActionListener(this);
        placeBridge = new JButton("Place a Bridge");
        placeBridge.setBounds(25,150,150,50);
        placeBridge.addActionListener(this);
        actionCard = new JButton("Play an Action Card");
        actionCard.setBounds(25,200,150,50);
        actionCard.addActionListener(this);

        // Enabling the drag and drop functionality
        //final Point[] mouseOffset = {null};

        // Layered panel which allows more images to be stacked on top of each other.
        pane = new JLayeredPane();
        backgroundLabel.setBounds(0, 0, 1920, 1080);
        baseLabel.setBounds(2, 3, 1920, 1080);
        leafsLabel.setBounds(10,10,1920, 1080);
        // for loop to add all the leafs in their desired position (once everything is implements we can enable the setBorderPainted to false )

        for(int i=0;i<5;i++) {
            for (int j=0;j<5;j++) {
                Leaf leaf = new Leaf();
                leaf.setBounds(550+171*j,140+166*i,154,155) ;
                leaf.addActionListener(this);
                leaf.setActionCommand("leaf " + leafCount);
                leaves.add(leaf);
                leafCount++;
                pane.add(leaf, Integer.valueOf(3));
            }
        }
        // for loop to add the horizontal bridges
        for(int i=0;i<5;i++) {
            for (int j=0;j<4;j++) {
                Bridge bridge = new Bridge(); 
                bridge.setBounds(668+173*j, 193+168*i,79, 32);
                bridge.addActionListener(this);
                bridge.setActionCommand("Bridge");
                bridges.add(bridge);
                pane.add(bridge, Integer.valueOf(4));
            }
        }
        // for loop to add the Vertical bridges
        for(int i=0;i<4;i++) {
            for (int j=0;j<5;j++) {
                Bridge bridge = new Bridge();
                bridge.changeImage();
                bridge.setBounds(607+173*j, 247+170*i,32, 79);
                bridge.addActionListener(this);
                bridge.setActionCommand("Bridge");
                bridges.add(bridge);
                pane.add(bridge, Integer.valueOf(4));
            }
        }
        // for loop to add the blue frogs and number them for player 1
        for (int i=0;i<3;i++) {
            Frog frog = new Frog("Blue");
            frog.setBounds(1041+100*i, 983,72, 77);
            frog.addActionListener(this);
            frog.setActionCommand("blueFrog" + i);
            blueFrogs[i] = frog;
            //frog.disbaled();
            pane.add(frog, Integer.valueOf(5));
        }
        
        // for loop for the yellow frogs - player 2
        if (playerCount>2){
            for (int i=0;i<3;i++) {
                Frog frog = new Frog("Yellow");
                frog.setPlayer2();
                frog.setBounds(1412, 200+100*i,72, 77);
                frog.addActionListener(this);
                frog.setActionCommand("yellowFrog" + i);
                yellowFrogs[i] = frog;
                //frog.disbaled();
                pane.add(frog, Integer.valueOf(5));
            }
            for (int i=0;i<3;i++) {
                Frog frog = new Frog("Purple");
                frog.setPlayer4();
                frog.setBounds(437, 200+100*i,72, 77);
                frog.addActionListener(this);
                purpleFrogs[i] = frog;
                //frog.disbaled();
                frog.setActionCommand("purpleFrog" + i);
                pane.add(frog, Integer.valueOf(5));
            }
            
            for(int j=0;j<2;j++){
                for(int i=0;i<4;i++){
                    ActionCard card = new ActionCard();
                    card.setBounds(1412-975*j,565+99*i,72, 88);
                    pane.add(card, Integer.valueOf(5));
                    card.setFocusPainted(false);
                    card.addActionListener(this);
                    card.setActionCommand("ActionCard" + i);
                    card.nextCard();
                }
            }
        }
        // for loop for the red frogs - player 3
        for (int i=0;i<3;i++) {
            Frog frog = new Frog("Red");
            frog.setPlayer3();
            frog.setBounds(1041+100*i, 26,72, 77);
            frog.addActionListener(this);
            redFrogs[i] = frog;
            //frog.disbaled();
            frog.setActionCommand("redFrog" + i);
            pane.add(frog, Integer.valueOf(5));
        }
        // for loop for the purple frogs - player 

        // for loop for the first and third player's Action cards
        for(int j=0;j<2;j++){
            for(int i=0;i<4;i++){
                ActionCard card = new ActionCard();
                card.setBounds(592 + 82*i,980-958*j,72, 88);
                pane.add(card, Integer.valueOf(5));
                card.setFocusPainted(false);
                card.addActionListener(this);
                card.setActionCommand("ActionCard" + i);
                card.nextCard();
            }
        }
        // for loop for the second and fourth player's Action cards
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        
        // adding the backgrounds and images to the layered panels and the last part is their layer number, stacked layers.
        pane.add(backgroundLabel, Integer.valueOf(0));
        pane.add(turnDisplay, Integer.valueOf(7));
        pane.add(baseLabel, Integer.valueOf(1));
        pane.add(leafsLabel, Integer.valueOf(6));
        pane.add(moveFrog, Integer.valueOf(8));
        pane.add(placeBridge, Integer.valueOf(8));
        pane.add(actionCard, Integer.valueOf(8));
        frame.add(pane);
        
        // fonts defined
        //Font fontStyle2 = new Font("WhipsnapperW05-Black", Font.BOLD, 52);
        //Font fontStyle3 = new Font("WhipsnapperW05-Black", Font.BOLD, 86);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
		frame.setResizable(false);
		frame.setVisible(true);
        frame.setSize(screenWidth, screenHeight);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    private int cx(JComponent c) { 
        return c.getX() + c.getWidth()/2; 
    }
    private int cy(JComponent c) { 
        return c.getY() + c.getHeight()/2; 
    }
    public void turnSwitch() {
        currentIndex++;
        if (playerCount ==2){
            if (currentIndex == 2){
            currentIndex = 0;
            playerColor = playerColors2[currentIndex];
        }
        }
        else if (currentIndex == 4){
            currentIndex = 0;
            playerColor = playerColors[currentIndex];
        }
        turn = playerNames[currentIndex];
        turnDisplay.setText("It's " + turn +"'s Turn ");
    }
    

    private void removeBridgeBetween(Leaf from, Leaf to) {
    int fx = cx(from), fy = cy(from);
    int tx = cx(to),   ty = cy(to);

    int midX = (fx + tx) / 2;
    int midY = (fy + ty) / 2;

    final int TOL = 50;

    for (Bridge b : bridges) {
        int bx = cx(b);
        int by = cy(b);

        if (isHorizontal(b)) {
            if (Math.abs(by - midY) <= TOL && Math.abs(bx - midX) <= TOL) {
                b.setVisible(false);
                b.setEnabled(false);
                return;
            }
        } else {
            if (Math.abs(bx - midX) <= TOL && Math.abs(by - midY) <= TOL) {
                b.setVisible(false);
                b.setEnabled(false);
                return;
            }
        }
    }
    
}

    @Override
    public void actionPerformed(ActionEvent e) {
    Object src = e.getSource();

    if (src instanceof Frog) {
        if (selectedFrog == null) {
            selectedFrog = (Frog) src;
            selectedFrog.highlightFrog();
        } else {
            selectedFrog.unHighlightFrog();
            selectedFrog = (Frog) src;
            selectedFrog.highlightFrog();
        }
        return;
    }

    if (src instanceof Leaf && selectedFrog != null && selectedFrog.getPlayerColor() == playerColor) {
        Leaf targetLeaf = (Leaf) src;
        if (!targetLeaf.isOccupied()) {
            // find the closest leaf the frog is on
            Leaf currentLeaf = null;
            for (Leaf leaf : leaves) {
                int frogCenterX = selectedFrog.getX() + selectedFrog.getWidth() / 2;
                int frogCenterY = selectedFrog.getY() + selectedFrog.getHeight() / 2;
                if (Math.abs(frogCenterX - (leaf.getX() + leaf.getWidth() / 2)) < 40 &&
                    Math.abs(frogCenterY - (leaf.getY() + leaf.getHeight() / 2)) < 40) {
                    currentLeaf = leaf;
                    break;
                }
            }

            selectedFrog.moveFrog(
                targetLeaf.getX() + targetLeaf.getWidth() / 2,
                targetLeaf.getY() + targetLeaf.getHeight() / 2
            );

            targetLeaf.setOccupied();
            selectedFrog.unHighlightFrog();

            if (currentLeaf != null) {
                currentLeaf.clearOccupied();
                removeBridgeBetween(currentLeaf, targetLeaf);
            }

            selectedFrog = null;
            turnSwitch();
        }
    }
    if (!(src instanceof Leaf && selectedFrog == null)) {
        System.out.println("It's not your turn!");
        selectedFrog.unHighlightFrog();
    }
}


    public static void main(String[] args) {
        Board window = new Board(new String[]{"Hamzeh", "Zach", "Kiara","Sean"},4);
        window.setSize(1920,1080);



    }
    
}


/*
if (selected instanceof Leaf && selectedButton == null) {
                ((Leaf) selected).actionPerformed(e);
            }
        if (selected instanceof Bridge && selectedButton == null) {
                ((Bridge) selected).actionPerformed(e);
            }
        if (selected instanceof ActionCard && selectedButton == null) {
                ((ActionCard) selected).actionPerformed(e);
            }

if (selected instanceof ActionCard) {

            ((ActionCard) selected).doneCard();
            //System.out.println(((ActionCard) selected).getCardName());

        }
            
        
if (selectedButton == null) {

            selectedButton = (Frog) selected;
            ((Frog) selectedButton).highlightFrog();
            
        }
        else {
             Object targetLeaf = selected;
             if(selectedButton != targetLeaf){
                System.out.println("Moving frog to new position");
                ((Frog) selectedButton).moveFrog(((JButton) targetLeaf).getX() + ((JButton) targetLeaf).getWidth()/2, ((JButton) targetLeaf).getY() + ((JButton) targetLeaf).getHeight()/2);
                selectedButton.setOpaque(false);
                selectedButton = null;
             }
        }
             
        if (selected.equals(moveFrog) && turn == players[0]){
            for(int i=0;i<3;i++){
                blueFrogs[i].setEnabled(true);
            }
        }
        if (selected.equals(moveFrog) && turn == players[1]){
            for(int i=0;i<3;i++){
                yellowFrogs[i].setEnabled(true);
            }
        }
        if (selected.equals(moveFrog) && turn == players[2]){
            for(int i=0;i<3;i++){
                redFrogs[i].setEnabled(true);
            }
        }
        if (selected.equals(moveFrog) && turn == players[3]){
            for(int i=0;i<3;i++){
                purpleFrogs[i].setEnabled(true);
            }
        }

        if (selectedButton == null) {

            selectedButton = (Frog) selected;
            ((Frog) selectedButton).highlightFrog();
        }
        else {
            if(selected instanceof Leaf) {
                Leaf targetLeaf = (Leaf) selected;
                if(selectedButton != targetLeaf && ((Leaf) targetLeaf).isOccupied() == false){
                    ((Frog) selectedButton).moveFrog(((Leaf) targetLeaf).getX() + ((Leaf) targetLeaf).getWidth()/2, ((Leaf) targetLeaf).getY() + ((Leaf) targetLeaf).getHeight()/2);
                    ((Leaf)targetLeaf).setOccupied();
                    ((Frog)selectedButton).unHighlightFrog();
                    turnSwitch();
                    selectedButton =null;
                }
            }
        }
*/