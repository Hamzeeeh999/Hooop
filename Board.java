import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;

public class Board extends JFrame implements ActionListener {


    private JLayeredPane pane,popupPanel;
    private JLabel backgroundLabel, leafsLabel, baseLabel, popupLabel;
    public String player1, player2, player3, player4, playerColor, playerTurn;
    private ImageIcon hooop, leafsBg,baseBg,popup;
    private int leafCount = 1, playerCount;
    private String[] playerNames,playerColors = {"Blue", "Yellow","Red","Purple"},playerColors2 = {"Blue","Red"}, baseLeaves = {"leaf 23","leaf 15","leaf 3","leaf 11"}, baseLeaves2 = {"leaf 23","leaf 3"};
    private JButton selectedButton = null, MoveAFrog, PlaceABridge,popupButton;
    private Font fontStyle1, fontStyle2, fontStyle3;
    private static int currentIndex = 0;
    private JTextArea turnDisplay;
    private static String turn;
    private JButton[] blueFrogs, yellowFrogs, redFrogs, purpleFrogs;
    private Bridge [] removedHorBridges, removedVerBridges;
    private Frog selectedFrog = null, pushedFrog = null;
    private ActionCard selectedCard= null;
    private java.util.List<Bridge> bridges = new java.util.ArrayList<>();
    private java.util.List<Leaf> leaves = new java.util.ArrayList<>();
    private boolean isHorizontal(JComponent c) { return c.getWidth() > c.getHeight(); }
    private boolean parachuteMode = false, moved = false;
    private ActionCard[] player1Cards, player2Cards, player3Cards, player4Cards;
    public boolean extraJumpActivated = false, moveFrog = false, placeBridge= false, secondMove= false;
    private JFrame frame;
    private HashMap<Leaf, Frog> leafFrogMap = new HashMap<>();
    private JPanel popupOverlay;




    public Board(String[] players, int playerCount) {

        frame = new JFrame();
        popupPanel = new JLayeredPane() {
        @Override
        protected void paintComponent(Graphics g) {
        }};
        popupPanel.setOpaque(false);
        
        popupPanel.setLayout(null);
        popupPanel.setBounds(0, 0, 200, 200);
        popup = new ImageIcon("./Assets/Popup.png");
        popupLabel = new ScaledImageLabel(popup.getImage());
        popupLabel.setOpaque(false);
        popupLabel.setBounds(0,0,420,90);
        playerNames = new String[playerCount];
        playerNames = players;
        this.playerCount = playerCount;
        turn = players[0];
        playerColor = playerColors[currentIndex];

        fontStyle1 = FontLoader.load("./Assets/Whipsnapper W05 Black.ttf",25f);
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


        popupOverlay = new JPanel(null) {
    @Override
    protected void paintComponent(Graphics g) {
        // Draw popup background (PNG with transparency)
        g.drawImage(popup.getImage(), 750, 495, null);
    }
};

// allow mouse events (default GlassPane blocks them)
popupOverlay.setOpaque(false);
popupOverlay.setVisible(false);
popupOverlay.setPreferredSize(new Dimension(
    popup.getIconWidth(),
    popup.getIconHeight()
));

// IMPORTANT: enable mouse input
popupOverlay.setEnabled(true);
popupOverlay.setFocusable(true);

frame.setGlassPane(popupOverlay);

        
        
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
                card.setEnabled(true);
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
        //pane.add(turnDisplay, Integer.valueOf(7));
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
        popup();
        showPopUp();
    }
    private int cx(JComponent c) { 
        return c.getX() + c.getWidth()/2; 
    }
    private int cy(JComponent c) { 
        return c.getY() + c.getHeight()/2; 
    }

public void switchFrogs() {
    if (playerCount == 2) {
            for (int i = 0; i < 3; i++) {
        blueFrogs[i].setEnabled(false);
        redFrogs[i].setEnabled(false);
    }
        if (currentIndex == 0) {
            for (int i = 0; i < 3; i++) blueFrogs[i].setEnabled(true);
        } else if (currentIndex == 1) {
            for (int i = 0; i < 3; i++) redFrogs[i].setEnabled(true);
        }
    } 
    else if (playerCount == 4) {
            for (int i = 0; i < 3; i++) {
        blueFrogs[i].setEnabled(false);
        redFrogs[i].setEnabled(false);
        yellowFrogs[i].setEnabled(false);
        purpleFrogs[i].setEnabled(false);
    }
        switch (currentIndex) {
            case 0 -> { for (int i = 0; i < 3; i++) blueFrogs[i].setEnabled(true); }
            case 1 -> { for (int i = 0; i < 3; i++) yellowFrogs[i].setEnabled(true); }
            case 2 -> { for (int i = 0; i < 3; i++) redFrogs[i].setEnabled(true); }
            case 3 -> { for (int i = 0; i < 3; i++) purpleFrogs[i].setEnabled(true); }
        }
    }

        
}
public void turnSwitch() {
        currentIndex++;

        // This checks if the extra jump card is activated and gives the current player two turns.
        if (extraJumpActivated == true) {
            currentIndex--;
            extraJumpActivated = false;
        }

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
        }
        else{
            playerColor = playerColors2[currentIndex];
        }
        turn = playerNames[currentIndex];
        moveFrog = false;
        placeBridge = false;
        moved = false;
        extraJumpActivated = false;
        secondMove = false;

        switchFrogs();
        showPopUp();
        turnDisplay.setText("It's " + turn +"'s Turn ");
    }

public void popup() {
    MoveAFrog = new JButton("Move a frog");
    PlaceABridge = new JButton("Place a bridge");

    styleButton(MoveAFrog);
    styleButton(PlaceABridge);
    MoveAFrog.setFont(fontStyle1);
    PlaceABridge.setFont(fontStyle1);

    MoveAFrog.setBounds(770, 515, 185, 50);
    PlaceABridge.setBounds(963, 515, 185, 50);

    popupOverlay.add(MoveAFrog);
    popupOverlay.add(PlaceABridge);
}


public void showPopUp(){
        popupOverlay.setVisible(true);
    popupOverlay.requestFocusInWindow();

    }
public void hidePopup(){
        popupOverlay.setVisible(false);
    }
private void styleButton (JButton b){
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setForeground(Color.WHITE);
        b.addActionListener(this);
    }

private void disableAllFrogs() {
    for (int i = 0; i<3;i++){
        blueFrogs[i].setEnabled(false);
    }
    for (int i = 0; i<3;i++){
        yellowFrogs[i].setEnabled(false);
    }
    for (int i = 0; i<3;i++){
        redFrogs[i].setEnabled(false);
    }
    for (int i = 0; i<3;i++){
        purpleFrogs[i].setEnabled(false);
    }
} 
    

int removedHorBridgesCounter;
int removedVerBridgesCounter;
static int removedBridges;

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
                removedBridges++;
                b.setEnabled(false);
                return;
            }
        } else {
            if (Math.abs(bx - midX) <= TOL && Math.abs(by - midY) <= TOL) {
                b.setVisible(false);
                removedVerBridges[removedVerBridgesCounter]=b;
                removedVerBridgesCounter++;
                removedBridges++;
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

    final int TOL = 60;

    for (Bridge b : bridges) {
        if (!b.isVisible()) continue;

        int bx = cx(b);
        int by = cy(b);

        if (isHorizontal(b)) {
            if (Math.abs(fy - ty) < 60 && Math.abs(bx - midX) < TOL && Math.abs(by - midY) < TOL)
                return true;
        } else {
            if (Math.abs(fx - tx) < 60 && Math.abs(bx - midX) < TOL && Math.abs(by - midY) < TOL)
                return true;
        }
    }

    return false;  
}

private boolean isSmallestGapInRow(int y, int gap) {
    int smallest = Integer.MAX_VALUE;

    for (Leaf leaf : leaves) {
        int ly = cy(leaf);

        if (Math.abs(ly - y) < 20) {
            for (Leaf other : leaves) {
                if (leaf == other) continue;
                if (Math.abs(cy(other) - y) < 20) {
                    int dx = Math.abs(cx(leaf) - cx(other));
                    if (dx > 0 && dx < smallest)
                        smallest = dx;
                }
            }
        }
    }

    return Math.abs(gap - smallest) < 20;
}

private boolean isSmallestGapInColumn(int x, int gap) {
    int smallest = Integer.MAX_VALUE;

    for (Leaf leaf : leaves) {
        int lx = cx(leaf);

        if (Math.abs(lx - x) < 20) {
            for (Leaf other : leaves) {
                if (leaf == other) continue;
                if (Math.abs(cx(other) - x) < 20) {
                    int dy = Math.abs(cy(leaf) - cy(other));
                    if (dy > 0 && dy < smallest)
                        smallest = dy;
                }
            }
        }
    }

    return Math.abs(gap - smallest) < 20;
}

private boolean isAdjacent(Leaf from, Leaf to) {
    int fx = cx(from), fy = cy(from);
    int tx = cx(to),   ty = cy(to);

    final int ALIGN_TOL = 20;

    if (Math.abs(fy - ty) < ALIGN_TOL) {              
        int dx = Math.abs(fx - tx);
        if (dx > 0) {
            return isSmallestGapInRow(fy, dx);
        }
    }

    if (Math.abs(fx - tx) < ALIGN_TOL) {              
        int dy = Math.abs(fy - ty);
        if (dy > 0) {
            return isSmallestGapInColumn(fx, dy);
        }
    }

    return false;
}

private void firstMovement(Object src, Leaf targetLeaf){
                selectedFrog.moveFrog(targetLeaf.getX() + targetLeaf.getWidth() / 2,targetLeaf.getY() + targetLeaf.getHeight() / 2);
                leafFrogMap.put(targetLeaf, selectedFrog);
                targetLeaf.setOccupied();
                selectedFrog.unHighlightFrog();
                selectedFrog.setOnLeaf();
                selectedFrog = null;
                parachuteMode = false;
            }

private void movement(Object src, Leaf targetLeaf){
    Leaf currentLeaf = null;
            for (Leaf leaf : leaves) {
                int frogCenterX = selectedFrog.getX() + selectedFrog.getWidth() / 2;
                int frogCenterY = selectedFrog.getY() + selectedFrog.getHeight() / 2;
                if (Math.abs(frogCenterX - (leaf.getX() + leaf.getWidth() / 2)) < 40 &&
                    Math.abs(frogCenterY - (leaf.getY() + leaf.getHeight() / 2)) < 40) {
                    currentLeaf = leaf;
                    leafFrogMap.put(currentLeaf, selectedFrog);
                    break;
                }
            }
            if (isAdjacent(currentLeaf, targetLeaf) && (hasBridgeBetween(currentLeaf, targetLeaf) || parachuteMode == true)){
                selectedFrog.moveFrog(
                targetLeaf.getX() + targetLeaf.getWidth() / 2,targetLeaf.getY() + targetLeaf.getHeight() / 2);
                leafFrogMap.put(targetLeaf, selectedFrog);
                moved = true;

            targetLeaf.setOccupied();
            selectedFrog.unHighlightFrog();

            if (leafFrogMap.containsKey(currentLeaf)) {
                leafFrogMap.keySet().remove(currentLeaf);
                removeBridgeBetween(currentLeaf, targetLeaf);
            }

            selectedFrog = null;
            parachuteMode = false;

            }
}
Leaf previousLeaf = null;
private void movePushed(Object src, Leaf targetLeaf, Leaf currentLeaf){
            if (isAdjacent(currentLeaf, targetLeaf) && (hasBridgeBetween(currentLeaf, targetLeaf) || parachuteMode == true)){
                selectedFrog.moveFrog(
                targetLeaf.getX() + targetLeaf.getWidth() / 2,targetLeaf.getY() + targetLeaf.getHeight() / 2);
                leafFrogMap.put(targetLeaf, selectedFrog);
                removeBridgeBetween(currentLeaf, targetLeaf);
                turnSwitch();

            targetLeaf.setOccupied();
            selectedFrog.unHighlightFrog();
            


            

            selectedFrog = null;
            parachuteMode = false;

            }

}
private void pushFrog(Frog f){
    f.setLocation(200,200);
    pushedFrog =f;
    f.setEnabled(true);
    f.addActionListener(this);

}
int bridgesPlaces;
@Override
public void actionPerformed(ActionEvent e) {
    Object src = e.getSource();
    Object src2 = e.getSource();
    String command = e.getActionCommand();
    
    if(command.equals("Move a frog")){
        hidePopup();
        moveFrog= true;
    }
    if(command.equals("Place a bridge") && removedBridges >=1){
        hidePopup();
        placeBridge= true;
    }
    if (command.equals("Place a bridge") && removedBridges <1){
        System.out.println("There's no bridges to be placed so you will have to move a frog");
        moveFrog = true;
        placeBridge = false;
        hidePopup();
    }
    if (src.equals(pushedFrog)){
        selectedFrog = pushedFrog;
        selectedFrog.setEnabled(true);
    }
    if (src instanceof Leaf && selectedFrog == pushedFrog){
        Leaf targetLeaf = (Leaf) src;
        movePushed(src, targetLeaf, previousLeaf);
        pushedFrog = null;
    }

    if (src instanceof Frog && moveFrog) {
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
    
    //movement past the first time
    if (src instanceof Leaf && selectedFrog != null && selectedFrog.getPlayerColor().equals(playerColor) && moveFrog && !extraJumpActivated) {
        Leaf targetLeaf = (Leaf) src;
        if (selectedFrog.isOnLeaf()){
            if (!leafFrogMap.containsKey(targetLeaf)) {
            movement(src, targetLeaf);
            if (moved){
                turnSwitch();
            }
            else{
                System.out.println("Please select a leaf that has bridge!");
                movement(src, targetLeaf);
            }
        }
            else{
                Frog f = leafFrogMap.get(targetLeaf);
                pushFrog(f);
                selectedFrog.setEnabled(false);
                previousLeaf = targetLeaf;
                movement(src, targetLeaf);
            }
        }

    else{
        if(playerCount ==4){
            if (!leafFrogMap.containsKey(targetLeaf) && (command.equals(baseLeaves[currentIndex])) && moveFrog) {
                firstMovement(src, targetLeaf);
                turnSwitch();
            }
            if (leafFrogMap.containsKey(targetLeaf) && (command.equals(baseLeaves[currentIndex])) && moveFrog) {
            Frog f = leafFrogMap.get(targetLeaf);
                pushFrog(f);
                selectedFrog.setEnabled(false);
                previousLeaf = targetLeaf;
                firstMovement(src, targetLeaf);
        }
        }

        else{
            if (!leafFrogMap.containsKey(targetLeaf) && (command.equals(baseLeaves2[currentIndex]))&& moveFrog) {
                firstMovement(src, targetLeaf);
                turnSwitch();
            }
            else{
            Frog f = leafFrogMap.get(targetLeaf);
                pushFrog(f);
                selectedFrog.setEnabled(false);
                previousLeaf = targetLeaf;
                firstMovement(src, targetLeaf);
        }
        }
    }
}
if (extraJumpActivated){
     if (src instanceof Leaf && selectedFrog != null && selectedFrog.getPlayerColor().equals(playerColor) && moveFrog) {
            Leaf targetLeaf = (Leaf) src;
            if (selectedFrog.isOnLeaf()){
                if (!leafFrogMap.containsKey(targetLeaf)) {
                movement(src, targetLeaf);
                    if (moved){
                        turnSwitch();
                        hidePopup();
                        moveFrog = true;
                }
                else{
                    System.out.println("Please select a leaf that has bridge!");
                    movement(src, targetLeaf);
                }
                selectedCard.doneCard();
        }     

        }
    
    }
}


if (src instanceof ActionCard ){
        selectedCard = (ActionCard) src;
        
        // Allows a frog to jump to any adjacent leaf with or without a bridge
        if (selectedCard != null && selectedCard.getCardName() == "Parachute" && selectedCard.getPlayerName().equals(turn)){
            parachuteMode = true;
            selectedCard.doneCard();
        }

        // Changed this to work with the Extra Jump Card
        if (removedBridges < 2 && selectedCard.getCardName() == "Extra Bridge"){
            System.out.println("There's not enough bridges to place");
            selectedCard = null;
        }

        if (selectedCard!= null && selectedCard.getCardName() == "Extra Jump" && selectedCard.getPlayerName().equals(turn)){
            extraJumpActivated = true;
}
    if (selectedCard.getCardName() == "Parachute"){
        System.out.println(removedBridges);

    }
        
        // There was missing brackets so I added them, still works as intended.
        if (selectedCard!= null && selectedCard.getCardName() == "Extra Bridge" && selectedCard.getPlayerName().equals(turn) && (removedHorBridgesCounter>=2 || removedVerBridgesCounter >=2)){
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

        //Variable "extraJumpActivated" allows the SwitchTurn function to give the player two turns. Implementation is not finished yet, still need to add some restrictions on each turn.

    if (command.equals("Removed Horizontal Bridge")){
        ((Bridge)src).placeHorBridge();
        bridgesPlaces++;
        removedBridges--;


        for(int i= 0; i<42;i++){
            if (((Bridge)src) == removedHorBridges[i]){
                removedHorBridges[i] = null;
                break;
            }
        }
        if (bridgesPlaces ==2){
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
        if (bridgesPlaces == 2){
            bridgesPlaces = 0;
        }
        selectedCard.doneCard();
            
        }
        
    }
    else if (command.equals("Removed Vertical Bridge")){
        ((Bridge)src).placeVerBridge();
        bridgesPlaces++;
        removedBridges--;

        for(int i= 0; i<42;i++){
            if (((Bridge)src) == removedVerBridges[i]){
                removedVerBridges[i] = null;
                break;
            }
        }
        if (bridgesPlaces ==2){
        
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
        if (bridgesPlaces == 2){
            bridgesPlaces = 0;
        }
        selectedCard.doneCard();
    }
}

if (command.equals("Place a bridge") && removedBridges>=1){
    for(int i= 0; i<removedHorBridgesCounter;i++){
                if (removedHorBridges[i] != null){
                    removedHorBridges[i].setEnabled(true);
                    removedHorBridges[i].setIcon(new ImageIcon(""));
                    removedHorBridges[i].setActionCommand("Removed Hor Bridge");
                    removedHorBridges[i].setVisible(true);
                }
            }
    for(int i= 0; i<removedVerBridgesCounter;i++){
                if (removedVerBridges[i] != null) {
                    removedVerBridges[i].setEnabled(true);
                    removedVerBridges[i].setIcon(new ImageIcon(""));
                    removedVerBridges[i].setActionCommand("Removed Ver Bridge");
                    removedVerBridges[i].setVisible(true);
                }
            }

}
    if (command.equals("Removed Hor Bridge")){
        ((Bridge)src).placeHorBridge();
        removedBridges--;


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
        hidePopup();
        turnSwitch();
    }
    else if (command.equals("Removed Ver Bridge")){
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
    removedBridges--;
    hidePopup();
    turnSwitch();
}
}

}


public static void main(String[] args) {
        Board window = new Board(new String[]{"Hamzeh", "Zach", "Kiara","Sean"},4);
        window.setSize(1920,1080);
        



    }
    
}
