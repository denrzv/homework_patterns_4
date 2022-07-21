import com.github.denrzv.shop.Cart;
import com.github.denrzv.shop.Menu;
import com.github.denrzv.shop.Product;
import com.github.denrzv.shop.Shop;

import java.util.*;
import java.util.stream.Collectors;


public class ShopImpl implements Shop {
    private String shopName;
    private List<Product> products;
    private boolean isShopOpen = false;
    private final CartImpl cart;
    private MenuImpl currentMenu;
    private final int HIGH_RATING = 3;

    public ShopImpl(String shopName) {
        setShopName(shopName);
        products = new ArrayList<>();
        cart = new CartImpl();
    }

    private void buildMenu() {
        Menu mainMenu = new MenuImpl("Добро пожаловать в " + this, null);
        currentMenu = (MenuImpl) mainMenu;
        Menu productsMenu = new ProductsMenu("Список доступных продуктов, " +
                "для выбора введите номер продукта и количество через пробел", mainMenu, this);
        Menu cartMenu = new CartMenu("Просмотр корзины", mainMenu, cart);
        Menu topProductsMenu = new TopProductsMenu("Топ продукты", mainMenu, this);
        Menu orderMenu = new MenuImpl("Оформление заказа", mainMenu);
        Menu clearProductsMenu = new MenuImpl("Удаление продуктов из корзины", mainMenu);

        mainMenu.addItem("Выход", new MenuImpl("Выход", null));
        mainMenu.addItem("Вывести список доступных продуктов", productsMenu);
        mainMenu.addItem("Просмотр корзины", cartMenu);
        mainMenu.addItem("Топ продукты по рейтингу", topProductsMenu);

        productsMenu.addItem("Перейти в корзину", cartMenu);
        cartMenu.addItem("Оформить заказ", orderMenu);
        cartMenu.addItem("Вывести список продуктов в корзине", cartMenu);
        cartMenu.addItem("Очистить корзину", clearProductsMenu);
    }

    public String getShopName() {
        return shopName;
    }

    public List<ProductImpl> getTopProducts() {
        List<Product> products = (List<Product>) getProducts();

        List<ProductImpl> topProducts = products.parallelStream()
                        .map(product -> (ProductImpl)product)
                                .collect(Collectors.toList());

        if (topProducts.size() > 0) {
            topProducts = topProducts.parallelStream()
                    .filter(product -> product.getRating() >= HIGH_RATING)
                    .collect(Collectors.toList());
        }
        return topProducts;
    }

    @Override
    public void setShopName(String shopName) {
        if (shopName == null || shopName.isEmpty() || shopName.isBlank()) {
            throw new IllegalArgumentException("Некорректно задано название магазина: " + shopName);
        } else {
            this.shopName = shopName;
        }
    }

    @Override
    public void addProduct(Product product) {
        products.add(product);
    }

    @Override
    public void removeProduct(Product product) {
        products.remove(product);
    }

    @Override
    public void updateProduct(Product product) {
        products = products.parallelStream()
                .filter(product_ -> product_.equals(product))
                .map(product_ -> product_ = product)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Product> getProducts() {
        return products;
    }

    @Override
    public boolean isShopOpen() {
        return isShopOpen;
    }

    @Override
    public void runShop() {
        buildMenu();
        setShopStatus(true);
        System.out.println(shopName + " открыт!");
        Scanner scanner = new Scanner(System.in);
        String input;
        Optional<Menu> optionalMenu;
        while (isShopOpen()) {
            printMenu(currentMenu);
            input = scanner.nextLine();
            optionalMenu = Optional.ofNullable(currentMenu.runMenu(input));
            optionalMenu.ifPresentOrElse(
                    (value) -> currentMenu = (MenuImpl) value,
                    () -> setShopStatus(false)
            );
        }
    }

    private void printMenu(Menu menu) {
        if (menu == null) {
            throw new IllegalArgumentException("Некорректно задано меню");
        } else {
            System.out.println(menu);
        }
    }

    @Override
    public void closeShop() {
        setShopStatus(false);
        System.out.println(shopName + " закрыт!");
    }

    @Override
    public Cart getCart() {
        return cart;
    }

    private void setShopStatus(boolean isShopOpen) {
        if (this.isShopOpen && isShopOpen) {
            System.err.println("Магазин уже открыт");
        } else if (!this.isShopOpen && !isShopOpen) {
            System.err.println("Магазин уже закрыт");
        } else {
            this.isShopOpen = isShopOpen;
        }
    }

    @Override
    public String toString() {
        return getShopName();
    }
}
