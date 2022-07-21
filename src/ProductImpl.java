import com.github.denrzv.shop.Product;

public class ProductImpl implements Product {
    private String name;
    private int rating;
    private int price;
    private final int MIN_RATING = 0;
    private final int MAX_RATING = 5;
    private final String RATING_SYMBOL = "*";

    public ProductImpl(String name, int price) {
        setName(name);
        setPrice(price);
    }

    @Override
    public String getName() {
        return name;
    }

    public int getMinRating() {
        return MIN_RATING;
    }

    public int getMaxRating() {
        return MAX_RATING;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public void setName(String name) {
        if (name == null || name.isBlank() || name.isEmpty()) {
            throw new IllegalArgumentException("Некорректно задано название товара: " + name);
        } else {
            this.name = name;
        }
    }

    @Override
    public void setPrice(int price) {
        if (price > 0) {
            this.price = price;
        } else {
            throw new NumberFormatException("Некорректно задана цена товара: " + price);
        }
    }

    @Override
    public int getPrice() {
        return price;
    }

    public void setRating(int rating) {
        if (rating >= MIN_RATING && rating <= MAX_RATING) {
            this.rating = rating;
        } else {
            throw new NumberFormatException("Некорректно задан рейтинг: " + rating);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductImpl product = (ProductImpl) o;

        return getName().equals(product.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public String toString() {
        return getName() + " - " + getPrice() + " руб/шт." + " - " + "оценка покупателей " +
                (getRating() == MIN_RATING ? "- " : getStars(getRating()));
    }

    private String getStars(int quantity) {
        return RATING_SYMBOL.repeat(quantity);
    }
}
