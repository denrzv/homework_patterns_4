import com.github.denrzv.shop.Cart;
import com.github.denrzv.shop.Menu;

public class CartMenu extends MenuImpl{
    private final CartImpl cart;

    public CartMenu(String menuAnnounce, Menu previousMenu, Cart cart) {
        super(menuAnnounce, previousMenu);
        this.cart = (CartImpl) cart;
    }

    @Override
    public Menu runMenu(String input) {
        MenuImpl menu = this;
        String[] options = parseInput(input);
        if (options.length == 1) {
            menu = (MenuImpl) validateSingleOption(input);
            if (menu == this) {
                System.out.println(cart);
            } else if (menu.getLabel().equals("Оформление заказа")) {
                cart.submitOrder();
            } else if (menu.getLabel().equals("Очистить корзину")) {
                cart.removeAllProducts();
            }
        }
        return menu;
    }
}
