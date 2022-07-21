import com.github.denrzv.shop.Menu;
import com.github.denrzv.shop.Shop;

import java.util.List;

public class TopProductsMenu extends MenuImpl{
    private final ShopImpl shop;
    public TopProductsMenu(String menuAnnounce, Menu previousMenu, Shop shop) {
        super(menuAnnounce, previousMenu);
        this.shop = (ShopImpl) shop;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        List<ProductImpl> productList = shop.getTopProducts();
        productList.parallelStream()
                .forEach(product -> sb.append(product).append("\n"));
        return sb.toString();
    }
}
