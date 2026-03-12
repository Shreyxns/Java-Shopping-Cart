import javax.swing.SwingUtilities;

public class ShoppingCartApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ShoppingCartGUI());
    }

}