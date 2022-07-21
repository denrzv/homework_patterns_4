import com.github.denrzv.shop.Shop;

import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Shop shop = new ShopImpl("Продуктовый магазин");
        supplyProducts(shop);
        shop.runShop();
    }

    public static void supplyProducts(Shop shop) {
        String[] productNames = new String[] {
                 "Хлеб",
                "Молоко",
                "Капуста",
                "Творог",
                "Индейка",
                "Говядина",
                "Вода",
                "Апельсины",
                "Яблоки",
                "Гречка"
        };

        Arrays.asList(productNames).parallelStream()
                .map(name -> new ProductImpl(name, generatePrice(name)))
                .forEach(product -> {
                    product.setRating(new Random().nextInt(product.getMinRating(), product.getMaxRating()));
                    shop.addProduct(product);
                });
    }

    public static int generatePrice(String name) {
        return Math.abs(1000 - name.charAt(0));
    }
}