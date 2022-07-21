import com.github.denrzv.shop.Cart;
import com.github.denrzv.shop.Menu;
import com.github.denrzv.shop.Product;
import com.github.denrzv.shop.Shop;

import java.util.List;

public class ProductsMenu extends MenuImpl{
    private final Shop shop;

    public ProductsMenu(String menuAnnounce, Menu previousMenu, Shop shop) {
        super(menuAnnounce, previousMenu);
        this.shop = shop;
    }

    @Override
    public Menu runMenu(String input) {
        Menu menu = this;
        String[] options = parseInput(input);
        if (options.length == 1) {
            menu = validateSingleOption(input);
        } else if (options.length == 2) {
            String product = options[0];
            String quantity = options[1];
            if (product.matches("\\d+") && quantity.matches("\\d+")) {
                int productId = Integer.parseInt(product);
                int qty = Integer.parseInt(quantity);
                List<Product> products = (List<Product>) shop.getProducts();
                int productsSize = products.size();
                int labelsSize = labels.size();
                if (productId >= labelsSize && productId < (labelsSize + productsSize)) {
                    Cart cart = shop.getCart();
                    Product productToAdd = products.get(productId - labelsSize);
                    cart.addProduct(productToAdd, qty);
                }
            } else {
                throw new IllegalArgumentException("Некорректно указан продукт или количество");
            }
        }
        return menu;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(super.toString());
        List<Product> products = (List<Product>) shop.getProducts();
        int labelsSize = labels.size();
        for (int i = labelsSize; i < (products.size() + labelsSize); i++) {
            builder.append(i)
                    .append(" - ")
                    .append(products.get(i - labelsSize))
                    .append("\n");
        }
        return builder.toString();
    }
}
