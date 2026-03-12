import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.*;

public class ShoppingCartGUI extends JFrame {

    private ShoppingCart cart = new ShoppingCart();
    private ArrayList<Product> productCatalog = new ArrayList<>();

    private JList<Product> productList;
    private JList<CartItem> cartItemList;

    private JLabel totalPriceLabel;
    private JLabel productImageLabel;

    private JTextArea productDetailsArea;

    private JSpinner quantitySpinner;

    private DefaultListModel<Product> model;

    public ShoppingCartGUI() {

        initializeCatalog();
        setupUI();

    }

    private void initializeCatalog() {

        productCatalog.add(new Product("T-Shirt", 499.99,
        "Comfortable cotton T-shirt in various sizes.",
        "../images/tshirt.jpg"));

productCatalog.add(new Product("Headphones", 1299.00,
        "Wireless over-ear headphones with noise cancellation.",
        "../images/headphones.jpg"));

productCatalog.add(new Product("Coffee Mug", 299.50,
        "Ceramic mug with 350ml capacity.",
        "../images/mug.jpg"));
    }

    private void setupUI() {

        setTitle("🛒 Online Shopping Cart");

        setSize(1000, 650);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        JPanel catalogPanel = new JPanel(new BorderLayout());

        model = new DefaultListModel<>();

        productCatalog.forEach(model::addElement);

        productList = new JList<>(model);

        catalogPanel.add(new JScrollPane(productList), BorderLayout.WEST);

        JPanel detailsPanel = new JPanel(new BorderLayout());

        productDetailsArea = new JTextArea();

        productDetailsArea.setEditable(false);

        productImageLabel = new JLabel("Image", SwingConstants.CENTER);

        productImageLabel.setPreferredSize(new Dimension(180, 150));

        detailsPanel.add(productImageLabel, BorderLayout.WEST);

        detailsPanel.add(new JScrollPane(productDetailsArea), BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();

        controlPanel.add(new JLabel("Quantity:"));

        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

        controlPanel.add(quantitySpinner);

        JButton addToCartBtn = new JButton("Add to Cart");

        controlPanel.add(addToCartBtn);

        detailsPanel.add(controlPanel, BorderLayout.SOUTH);

        catalogPanel.add(detailsPanel, BorderLayout.CENTER);

        JPanel cartPanel = new JPanel(new BorderLayout());

        DefaultListModel<CartItem> cartModel = new DefaultListModel<>();

        cartItemList = new JList<>(cartModel);

        cartPanel.add(new JScrollPane(cartItemList), BorderLayout.CENTER);

        totalPriceLabel = new JLabel("Total: ₹0.00");

        JButton removeBtn = new JButton("Remove Item");

        JButton clearBtn = new JButton("Clear Cart");

        JButton checkoutBtn = new JButton("Checkout");

        JPanel cartFooter = new JPanel(new GridLayout(4,1));

        cartFooter.add(removeBtn);
        cartFooter.add(clearBtn);
        cartFooter.add(totalPriceLabel);
        cartFooter.add(checkoutBtn);

        cartPanel.add(cartFooter, BorderLayout.SOUTH);

        productList.addListSelectionListener(e -> {

            Product p = productList.getSelectedValue();

            if (p != null) {

                productDetailsArea.setText(
                        "Name: " + p.getName()
                                + "\nPrice: ₹"
                                + new DecimalFormat("0.00").format(p.getPrice())
                                + "\nDescription: "
                                + p.getDescription()
                );

                try {

                    ImageIcon icon = new ImageIcon(p.getImageUrl());

                    Image scaled =
                            icon.getImage()
                                    .getScaledInstance(
                                            150,
                                            120,
                                            Image.SCALE_SMOOTH
                                    );

                    productImageLabel.setIcon(new ImageIcon(scaled));

                } catch (Exception ex) {

                    productImageLabel.setText("Image not found");

                }

            }

        });

        addToCartBtn.addActionListener(e -> {

            Product selected = productList.getSelectedValue();

            if (selected != null) {

                int qty = (Integer) quantitySpinner.getValue();

                cart.addProduct(selected, qty);

                refreshCart(cartModel);

            }

        });

        removeBtn.addActionListener(e -> {

            int index = cartItemList.getSelectedIndex();

            if (index != -1) {

                cart.removeProduct(index);

                refreshCart(cartModel);

            }

        });

        clearBtn.addActionListener(e -> {

            cart.clear();

            refreshCart(cartModel);

        });

        checkoutBtn.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                    this,
                    "Total: ₹" + cart.getTotalPrice()
            );

            cart.clear();

            refreshCart(cartModel);

        });

        splitPane.setLeftComponent(catalogPanel);

        splitPane.setRightComponent(cartPanel);

        panel.add(splitPane);

        add(panel);

        setVisible(true);

    }

    private void refreshCart(DefaultListModel<CartItem> model) {

        model.clear();

        for (CartItem item : cart.getItems()) {

            model.addElement(item);

        }

        totalPriceLabel.setText(
                "Total: ₹" +
                        new DecimalFormat("0.00")
                                .format(cart.getTotalPrice())
        );

    }

}