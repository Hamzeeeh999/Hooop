import javax.swing.*;

public class Frog extends JButton {
    private String playerColor;
    private Boolean onLeaf = false;


    Boolean highlighted = false;
    public Frog(String playerColor){
        super(new ImageIcon("./Assets/Blue-Frog.png"));
        this.setDisabledIcon(new ImageIcon("./Assets/Blue-Frog.png"));
        this.playerColor = playerColor;
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setBorder(null);
        setEnabled(false);
        //setDisabledIcon(new ImageIcon("Leaf.png"));

    }
    
    public int getXcoord(JButton button){
        //returns the x coordinate of the center of the leaf button
        int x = button.getX() + button.getWidth()/2;
        return x;
    }
    public void disbaled(){
        this.setEnabled(false);
    }

    public int getYcoord(JButton button){
        //returns the y coordinate of the center of the leaf button
        int y = button.getY() + button.getHeight()/2;
        return y;
    }
    public void setPlayer2(){
        this.setIcon(new ImageIcon("./Assets/Yellow-Frog.png"));
        this.setDisabledIcon(new ImageIcon("./Assets/Yellow-Frog.png"));
    }
    public void setPlayer3(){
        this.setIcon(new ImageIcon("./Assets/Red-Frog.png"));
        this.setDisabledIcon(new ImageIcon("./Assets/Red-Frog.png"));
    }
    public void setPlayer4(){
        this.setIcon(new ImageIcon("./Assets/Purple-Frog.png"));
        this.setDisabledIcon(new ImageIcon("./Assets/Purple-Frog.png"));
    }
    public void highlightFrog(){
        setOpaque(true);
        highlighted = true;
    }
    public void unHighlightFrog(){
        setOpaque(false);
        highlighted = false;
    }
    public void moveFrog(int x, int y){
        this.setLocation(x-this.getWidth()/2, y - this.getHeight()/2);
    }
    public Boolean isHighlighted(){
        return highlighted;
    }
    public void playerTurn()
    {
        setEnabled(true);
    }
    public String getPlayerColor(){
        return playerColor;
    }
    public void setOnLeaf(){
        onLeaf = true;
    }
    public void setNotOnLeaf(){
        onLeaf = false;
    }
    public Boolean isOnLeaf(){
        return onLeaf;
    }
}
