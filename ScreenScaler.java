import java.awt.*;
import javax.swing.*;

public class ScreenScaler {

    // You can change this if your design was built for a different reference size
    private static final Dimension BASE_RESOLUTION = new Dimension(1920, 1080);

    /**
     * Scales the given JFrame and all its components to fit the user's screen.
     * Works best with null (absolute) layouts.
     */
    public static void scaleFrame(JFrame frame) {
        if (frame == null) return;

        // Get current screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        double scaleX = screenSize.getWidth() / BASE_RESOLUTION.getWidth();
        double scaleY = screenSize.getHeight() / BASE_RESOLUTION.getHeight();

        // Scale contents
        scaleComponent(frame.getContentPane(), scaleX, scaleY);
        scaleFont(frame.getContentPane(), Math.min(scaleX, scaleY));

        // Resize the frame itself
        int newWidth = (int) (frame.getWidth() * scaleX);
        int newHeight = (int) (frame.getHeight() * scaleY);
        frame.setSize(newWidth, newHeight);
    }

    /** Recursively scales all components’ bounds */
    private static void scaleComponent(Component comp, double scaleX, double scaleY) {
    if (comp == null) return;

    // If container, recurse through children
    if (comp instanceof Container container) {
        LayoutManager layout = container.getLayout();

        // Scale bounds for components with null layout
        if (layout == null) {
            int newX = (int) (comp.getX() * scaleX);
            int newY = (int) (comp.getY() * scaleY);
            int newW = (int) (comp.getWidth() * scaleX);
            int newH = (int) (comp.getHeight() * scaleY);
            comp.setBounds(newX, newY, newW, newH);
        } else {
            // Even if layout is not null (like BorderLayout),
            // we should still resize the container itself.
            int newW = (int) (comp.getWidth() * scaleX);
            int newH = (int) (comp.getHeight() * scaleY);
            comp.setPreferredSize(new Dimension(newW, newH));
        }

        // Now recursively scale all child components
        for (Component child : container.getComponents()) {
            scaleComponent(child, scaleX, scaleY);
        }
    }

    // For JLabel with ImageIcon (background scaling)
    if (comp instanceof JLabel lbl) {
        Icon icon = lbl.getIcon();
        if (icon instanceof ImageIcon imgIcon) {
            int w = (int) (lbl.getWidth());
            int h = (int) (lbl.getHeight());
            if (w > 0 && h > 0) {
                Image scaledImg = imgIcon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
                lbl.setIcon(new ImageIcon(scaledImg));
            }
        }
    }
}

    

    /** Recursively scales all fonts */
    private static void scaleFont(Component comp, double scale) {
        Font f = comp.getFont();
        if (f != null) {
            float newSize = (float) (f.getSize2D() * scale);
            comp.setFont(f.deriveFont(newSize));
        }

        if (comp instanceof Container container) {
            for (Component child : container.getComponents()) {
                scaleFont(child, scale);
            }
        }
        
    }
    
}
