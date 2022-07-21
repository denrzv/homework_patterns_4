import com.github.denrzv.shop.Menu;

import java.util.*;


public class MenuImpl implements Menu {
    protected final String menuAnnounce;
    protected final List<String> labels;
    protected final List<Menu> menus;
    private Menu previousMenu;
    protected static final String DEFAULT_LABEL = "Выход";
    protected static final Menu DEFAULT_MENU = null;

    public MenuImpl(String menuAnnounce, Menu previousMenu) {
        if (menuAnnounce == null || menuAnnounce.isBlank() || menuAnnounce.isEmpty()) {
            throw new IllegalArgumentException("Некорректно задано название меню");
        }
        labels = new ArrayList<>();
        menus = new ArrayList<>();
        Optional<Menu> optionalMenu = Optional.ofNullable(previousMenu);
        optionalMenu.ifPresent(
                menu_ -> {
                    this.previousMenu = menu_;
                    labels.add("Возврат в предыдущее меню");
                    menus.add(previousMenu);
                    }
        );
        this.menuAnnounce = menuAnnounce;

    }

    public String getLabel() {
        return menuAnnounce;
    }

    public Menu runMenu(String input) {
        Menu menu = this;
        String[] options = parseInput(input);
        if ((options != null) && (options.length == 1)) {
            menu = validateSingleOption(options[0]);
        }
        return menu;
    }

    protected Menu validateSingleOption(String option) {
        Menu menu = this;
        int maxOption = getMenuSize() - 1;
        int minOption = 0;
        if (option.matches("[" + minOption + "-" + maxOption + "]")) {
            int menuItem = Integer.parseInt(option);
            String label = labels.get(menuItem);
            if (label.equals(DEFAULT_LABEL)) {
                menu = DEFAULT_MENU;
            } else {
                menu = menus.get(menuItem);
            }
        } else {
            System.err.println("Некорректно указан пункт меню");
        }
        return menu;
    }

    @Override
    public void addItem(String label, Menu nextMenu) {
        if (label == null || label.isEmpty() || label.isBlank()) {
            throw new IllegalArgumentException("Неверно задан пункт меню");
        } else {
            Optional<Menu> optionalMenu = Optional.ofNullable(nextMenu);
            optionalMenu.ifPresentOrElse(
                    menu_ -> {
                        labels.add(label);
                        menus.add(menu_);
                    },
                    () -> {
                        labels.add(DEFAULT_LABEL);
                        menus.add(DEFAULT_MENU);
                    }
            );
        }
    }

    @Override
    public void removeItem(String label) {
        int index = labels.indexOf(label);
        labels.remove(index);
        menus.remove(index);
    }

    public String[] parseInput(String input) {
        String[] result = null;
        if (input == null || input.isBlank() || input.isEmpty()) {
            System.err.println("Некорректно задан пункт меню!");
        } else {
            result = input.split(" ");
        }
        return result;
    }

    public List<String> getLabels() {
        return labels;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public int getMenuSize() {
        return getLabels().size();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(menuAnnounce);
        builder.append("\n");
        for (int i = 0; i < labels.size(); i++) {
            builder.append(i)
                    .append(" - ")
                    .append(labels.get(i))
                    .append("\n");
        }
        return builder.toString();
    }
}
