import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JFrame implements ActionListener {


    private JLayeredPane pane;
    private JLabel backgroundLabel, leafsLabel, baseLabel;
    public String player1, player2, player3, player4, playerColor, playerTurn;
    private ImageIcon hooop, leafsBg,baseBg;
    private int leafCount = 1, playerCount;
    private String[] playerNames,playerColors = {"Blue", "Yellow","Red","Purple"},playerColors2 = {"Blue","Red"}, baseLeaves = {"leaf 23","leaf 15","leaf 3","leaf 11"}, baseLeaves2 = {"leaf 23","leaf 3"};
    private JButton selectedButton = null;
    private Font fontStyle1, fontStyle2, fontStyle3;
    private static int currentIndex = 0;
    private JTextArea turnDisplay;
    private static String turn;
    private JButton[] blueFrogs, yellowFrogs, redFrogs, purpleFrogs;
    private Bridge [] removedHorBridges, removedVerBridges;
    private Frog selectedFrog = null;
    private ActionCard selectedCard= null;
    private java.util.List<Bridge> bridges = new java.util.ArrayList<>();
    private java.util.List<Leaf> leaves = new java.util.ArrayList<>();
    private boolean isHorizontal(JComponent c) { return c.getWidth() > c.getHeight(); }
    private boolean parachuteMode = false;
    private ActionCard[] player1Cards, player2Cards, player3Cards, player4Cards;


    public Board(String[] players, int playerCount) {

        JFrame frame = new JFrame();
        playerNames = new String[playerCount];
        playerNames = players;
        this.playerCount = playerCount;
        turn = players[0];
        playerColor = playerColors[currentIndex];

        fontStyle1 = FontLoader.load("./Assets/Whipsnapper W05 Black.ttf",40f);
        fontStyle2 = FontLoader.load("./Assets/Whipsnapper W05 Black.ttf",52f);
        fontStyle3 = FontLoader.load("./Assets/Whipsnapper W05 Black.ttf", 86f);

        turnDisplay = new JTextArea("It's " + turn +"'s Turn ");
        turnDisplay.setEditable(false);
        turnDisplay.setFont(fontStyle1);
        turnDisplay.setBounds(25, 50, 400, 100);
        turnDisplay.setOpaque(false);
        turnDisplay.setForeground(Color.WHITE);

        removedHorBridges = new Bridge[42];
        removedVerBridges = new Bridge[42];
        blueFrogs = new JButton[3];
        redFrogs = new JButton[3];
        yellowFrogs = new JButton[3];
        purpleFrogs = new JButton[3];


        player1Cards = new ActionCard[4];
        player2Cards = new ActionCard[4];
        player3Cards = new ActionCard[4];
        player4Cards = new ActionCard[4];
        

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

        backgroundLabel = new ScaledImageLabel(gameBg.getImage());
        baseLabel = new ScaledImageLabel(baseBg.getImage());
        leafsLabel = new ScaledImageLabel(leafsBg.getImage());

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

        for (int i=0;i<3;i++) {
            Frog frog = new Frog("Blue");
            frog.setBounds(1041+100*i, 983,72, 77);
            frog.addActionListener(this);
            frog.setEnabled(true);
            frog.setActionCommand("blueFrog" + i);
            blueFrogs[i] = frog;
            pane.add(frog, Integer.valueOf(5));
        }

        if (playerCount>2){
            for (int i=0;i<3;i++) {
                Frog frog = new Frog("Yellow");
                frog.setPlayer2();
                frog.setBounds(1412, 200+100*i,72, 77);
                frog.addActionListener(this);
                frog.setActionCommand("yellowFrog" + i);
                yellowFrogs[i] = frog;

                pane.add(frog, Integer.valueOf(5));
            }
            for (int i=0;i<3;i++) {
                Frog frog = new Frog("Purple");
                frog.setPlayer4();
                frog.setBounds(437, 200+100*i,72, 77);
                frog.addActionListener(this);
                purpleFrogs[i] = frog;
                frog.setActionCommand("purpleFrog" + i);
                pane.add(frog, Integer.valueOf(5));
            }
            for(int i=0;i<4;i++){
                    ActionCard card = new ActionCard(playerNames[1]);
                    card.setBounds(1412,565+99*i,72, 88);
                    pane.add(card, Integer.valueOf(5));
                    card.setFocusPainted(false);
                    card.addActionListener(this);
                    card.setActionCommand("ActionCard" + i);
                    player2Cards[i]=card;
                    card.nextCard();
                    card.setEnabled(false);
                }
            for(int i=0;i<4;i++){
                    ActionCard card = new ActionCard(playerNames[3]);
                    card.setBounds(1412-975,565+99*i,72, 88);
                    pane.add(card, Integer.valueOf(5));
                    card.setFocusPainted(false);
                    card.addActionListener(this);
                    card.setActionCommand("ActionCard" + i);
                    player4Cards[i]=card;
                    card.nextCard();
                    card.setEnabled(false);
                }
            
        
        }

        for (int i=0;i<3;i++) {
            Frog frog = new Frog("Red");
            frog.setPlayer3();
            frog.setBounds(1041+100*i, 26,72, 77);
            frog.addActionListener(this);
            redFrogs[i] = frog;
            frog.setActionCommand("redFrog" + i);
            pane.add(frog, Integer.valueOf(5));
        }
        for(int i=0;i<4;i++){
                ActionCard card = new ActionCard(playerNames[0]);
                card.setBounds(592 + 82*i,980,72, 88);
                pane.add(card, Integer.valueOf(5));
                card.setFocusPainted(false);
                card.addActionListener(this);
                card.setActionCommand("ActionCard" + i);
                player1Cards[i]=card;
                card.nextCard();
            }
        if (playerCount ==4){
                for(int i=0;i<4;i++){
                ActionCard card = new ActionCard(playerNames[2]);
                card.setBounds(592 + 82*i,980-958,72, 88);
                pane.add(card, Integer.valueOf(5));
                card.setFocusPainted(false);
                card.addActionListener(this);
                card.setActionCommand("ActionCard" + i);
                player3Cards[i]=card;
                card.nextCard();
                card.setEnabled(false);
            }
        }

        else{
            for(int i=0;i<4;i++){
                ActionCard card = new ActionCard(playerNames[1]);
                card.setBounds(592 + 82*i,980-958,72, 88);
                pane.add(card, Integer.valueOf(5));
                card.setFocusPainted(false);
                card.addActionListener(this);
                card.setActionCommand("ActionCard" + i);
                player3Cards[i]=card;
                card.nextCard();
                card.setEnabled(false);
            }
        }
        

        
        pane.add(backgroundLabel, Integer.valueOf(0));
        pane.add(turnDisplay, Integer.valueOf(7));
        pane.add(baseLabel, Integer.valueOf(1));
        pane.add(leafsLabel, Integer.valueOf(6));
        frame.add(pane);

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
    private int cx(JComponent c) { 
        return c.getX() + c.getWidth()/2; 
    }
    private int cy(JComponent c) { 
        return c.getY() + c.getHeight()/2; 
    }
    static int counter = 0;
    public void switchFrogs(){

        if (playerCount ==4){
            for (int i=0;i<3;i++){
            
            if (counter == 0){
                yellowFrogs[i].setEnabled(true);
                blueFrogs[i].setEnabled(false);
            }
            if (counter == 1){
                redFrogs[i].setEnabled(true);
                yellowFrogs[i].setEnabled(false);
            }
            if (counter == 2){
                redFrogs[i].setEnabled(false);
                purpleFrogs[i].setEnabled(true);
            }
            if (counter == 3){
                purpleFrogs[i].setEnabled(false);
                blueFrogs[i].setEnabled(true);
            }
        }
        }
        else{
            
            for (int i=0;i<2;i++){
            
            if (counter == 0){
                redFrogs[i].setEnabled(true);
                blueFrogs[i].setEnabled(false);
            }
            if (counter == 1){
                blueFrogs[i].setEnabled(true);
                redFrogs[i].setEnabled(false);
            }
        }
        }
        counter++;
        if(counter == 4){
            counter=0;
        }
        if(counter ==2){
            counter = 0;
        }
    }
    public void turnSwitch() {
        currentIndex++;
        if (playerCount ==2){
            if (currentIndex == 2){
            currentIndex = 0;
            }
                if(currentIndex ==1){
                for (int i=0; i<4;i++){
                    player3Cards[i].setEnabled(true);
                    player1Cards[i].setEnabled(false);
                }
            }
            if(currentIndex ==0){
                for (int i=0; i<4;i++){
                    player1Cards[i].setEnabled(true);
                    player3Cards[i].setEnabled(false);
                }
            }
        }
        if (playerCount == 4){

            if (currentIndex == 4){
                currentIndex = 0;
            }
            
            if(currentIndex ==1){
            for (int i=0; i<4;i++){
                player2Cards[i].setEnabled(true);
                player1Cards[i].setEnabled(false);
            }
        }
        if(currentIndex ==2){
            for (int i=0; i<4;i++){
                player3Cards[i].setEnabled(true);
                player2Cards[i].setEnabled(false);
            }
        }
        if(currentIndex ==3){
            for (int i=0; i<4;i++){
                player4Cards[i].setEnabled(true);
                player3Cards[i].setEnabled(false);
            }
        }
        if(currentIndex ==0){
            for (int i=0; i<4;i++){
                player1Cards[i].setEnabled(true);
                player4Cards[i].setEnabled(false);
            }
        }
            
        }

        if (playerCount ==4){
            playerColor = playerColors[currentIndex];
            System.out.println(playerColor);
        }
        else{
            playerColor = playerColors2[currentIndex];
        }
        turn = playerNames[currentIndex];
        switchFrogs();
        turnDisplay.setText("It's " + turn +"'s Turn ");
        

    }

    
    

    int removedHorBridgesCounter;
    int removedVerBridgesCounter;

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
                removedHorBridges[removedHorBridgesCounter] =b;
                removedHorBridgesCounter++;
                b.setEnabled(false);
                return;
            }
        } else {
            if (Math.abs(bx - midX) <= TOL && Math.abs(by - midY) <= TOL) {
                b.setVisible(false);
                removedVerBridges[removedVerBridgesCounter] =b;
                removedVerBridgesCounter++;
                b.setEnabled(false);
                return;
            }
        }
    }
    
    
}
private boolean hasBridgeBetween(Leaf from, Leaf to) {
    int fx = cx(from), fy = cy(from);
    int tx = cx(to),   ty = cy(to);

    int midX = (fx + tx) / 2;
    int midY = (fy + ty) / 2;

    // Slightly increase tolerance based on your 173–170 grid spacing
    final int TOL = 60;

    for (Bridge b : bridges) {
        if (!b.isVisible()) continue;

        int bx = cx(b);
        int by = cy(b);

        if (isHorizontal(b)) {
            // Bridges connect horizontally aligned leaves (same row)
            if (Math.abs(fy - ty) < 60 && Math.abs(bx - midX) < TOL && Math.abs(by - midY) < TOL)
                return true;
        } else {
            // Bridges connect vertically aligned leaves (same column)
            if (Math.abs(fx - tx) < 60 && Math.abs(bx - midX) < TOL && Math.abs(by - midY) < TOL)
                return true;
        }
    }

    return false;
}



@Override
public void actionPerformed(ActionEvent e) {
    Object src = e.getSource();
    String command = e.getActionCommand();


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


    if (src instanceof Leaf && selectedFrog != null && selectedFrog.getPlayerColor().equals(playerColor)) {
        Leaf targetLeaf = (Leaf) src;
        if (selectedFrog.isOnLeaf()){
            if (!targetLeaf.isOccupied()) {
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
            if (hasBridgeBetween(currentLeaf, targetLeaf)){
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
            parachuteMode = false;
            turnSwitch();

            }
        }
        }

    else{
        if(playerCount ==4){
            if (!targetLeaf.isOccupied() && (command.equals(baseLeaves[currentIndex]))) {
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
            selectedFrog.setOnLeaf();

            if (currentLeaf != null) {
                currentLeaf.clearOccupied();
                removeBridgeBetween(currentLeaf, targetLeaf);
            }

            selectedFrog = null;
            parachuteMode = false;
            turnSwitch();


            }

        }
        else{
            if (!targetLeaf.isOccupied() && (command.equals(baseLeaves2[currentIndex]))) {
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
            selectedFrog.setOnLeaf();

            if (currentLeaf != null) {
                currentLeaf.clearOccupied();
                removeBridgeBetween(currentLeaf, targetLeaf);
            }

            selectedFrog = null;
            parachuteMode = false;
            turnSwitch();


            }
        }
        

        }
    }

    if (src instanceof ActionCard ){
        selectedCard = (ActionCard) src;


        if (selectedCard.getCardName() == "Extra Bridge" && selectedCard.getPlayerName().equals(turn) && removedHorBridgesCounter!= 0 || removedVerBridgesCounter!= 0){
            for(int i= 0; i<removedHorBridgesCounter;i++){
                if (removedHorBridges[i] != null){
                    removedHorBridges[i].setEnabled(true);
                    removedHorBridges[i].setIcon(new ImageIcon(""));
                    removedHorBridges[i].setActionCommand("Removed Horizontal Bridge");
                    removedHorBridges[i].setVisible(true);
                }
            }
            for(int i= 0; i<removedVerBridgesCounter;i++){
                if (removedVerBridges[i] != null) {
                    removedVerBridges[i].setEnabled(true);
                    removedVerBridges[i].setIcon(new ImageIcon(""));
                    removedVerBridges[i].setActionCommand("Removed Vertical Bridge");
                    removedVerBridges[i].setVisible(true);
                }
            }
            }

        }
    if (command.equals("Removed Horizontal Bridge")){
        ((Bridge)src).placeHorBridge();

        for(int i= 0; i<42;i++){
            if (((Bridge)src) == removedHorBridges[i]){
                removedHorBridges[i] = null;
                break;
            }
        }
        for(int i= 0; i<42;i++){
            if (removedHorBridges[i]!= null){
                removedHorBridges[i].setVisible(false);
                removedHorBridges[i].setEnabled(false);
            }
        }
        for(int i= 0; i<42;i++){
            if (removedVerBridges[i]!= null){
                removedVerBridges[i].setVisible(false);
                removedVerBridges[i].setEnabled(false);
            }
        }
        turnSwitch();
    }
    else if (command.equals("Removed Vertical Bridge")){
        ((Bridge)src).placeVerBridge();

        for(int i= 0; i<42;i++){
            if (((Bridge)src) == removedVerBridges[i]){
                removedVerBridges[i] = null;
                break;
            }
        }
        for(int i= 0; i<42;i++){
            if (removedVerBridges[i]!= null){
                removedVerBridges[i].setVisible(false);
                removedVerBridges[i].setEnabled(false);
            }
        }
        for(int i= 0; i<42;i++){
            if (removedHorBridges[i]!= null){
                removedHorBridges[i].setVisible(false);
                removedHorBridges[i].setEnabled(false);
            }
        }
        turnSwitch();
    }

}
    

    public static void main(String[] args) {
        Board window = new Board(new String[]{"Hamzeh", "Zach", "Kiara","Sean"},4);
        window.setSize(1920,1080);
        



    }
    
}
