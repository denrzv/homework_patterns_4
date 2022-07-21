import com.github.denrzv.shop.Cart;
import com.github.denrzv.shop.Product;


import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class CartImpl implements Cart {
    private final List<Product> products;
    private final List<Integer> quantity;
    public CartImpl() {
        products = new ArrayList<>();
        quantity = new ArrayList<>();
    }

    @Override
    public void addProduct(Product product, int quantity) {
        if(isProductExists(product) && quantity > 0) {
            products.add(product);
            this.quantity.add(quantity);
            System.out.println(product.getName() + " успешно добавлен в корзину");
        } else {
            throw new IllegalArgumentException("Ошибка добавления в корзину. Некорректно задан товар или количество.");
        }
    }

    public List<Product> getProducts() {
        return products;
    }
    
    public List<Integer> getQuantity() {
        return quantity;
    }

    @Override
    public void removeProduct(Product product, int quantity) {
        if(isProductExists(product) && quantity > 0) {
            products.remove(product);
        } else {
            throw new IllegalArgumentException("Ошибка удаления из корзины. Некорректно задан товар.");
        }
    }

    public void removeAllProducts() {
        List<Product> products = getProducts();
        List<Integer> quantity = getQuantity();
        products.removeAll(products);
        quantity.removeAll(quantity);
    }

    @Override
    public void submitOrder() {
        System.out.println("Оформлен заказ.");
        System.out.println(this);
        removeAllProducts();
        System.out.println(this);
    }

    private boolean isProductExists(Product product) {
        AtomicBoolean isExists = new AtomicBoolean(false);
        Optional<Product> optionalProduct = Optional.of(product);
        optionalProduct.ifPresentOrElse(
                value -> isExists.set(true),
                () -> isExists.set(false)
        );
        return isExists.get();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getProducts().size() ; i++) {
            sb.append(getProducts().get(i))
                    .append(" количество")
                    .append(" - ")
                    .append(getQuantity().get(i))
                    .append("\n");
        }
        return sb.toString();
    }
}
