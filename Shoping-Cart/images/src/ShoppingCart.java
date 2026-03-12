import java.util.ArrayList;

public class ShoppingCart {

    private ArrayList<CartItem> items = new ArrayList<>();

    public void addProduct(Product product, int quantity) {

        for (CartItem item : items) {

            if (item.getProduct().getName().equals(product.getName())) {

                item.setQuantity(item.getQuantity() + quantity);
                return;
            }

        }

        items.add(new CartItem(product, quantity));
    }

    public void removeProduct(int index) {

        if (index >= 0 && index < items.size()) {
            items.remove(index);
        }

    }

    public void clear() {
        items.clear();
    }

    public ArrayList<CartItem> getItems() {
        return items;
    }

    public double getTotalPrice() {

        return items.stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();

    }

    public int getItemCount() {

        return items.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();

    }

}