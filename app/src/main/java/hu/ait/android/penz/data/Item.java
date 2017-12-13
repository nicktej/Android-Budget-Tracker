package hu.ait.android.penz.data;

import java.util.Date;

import hu.ait.android.penz.R;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Item extends RealmObject {
    public enum ItemType {
        GROCERIES(0, R.drawable.android_food),
        ENTERTAINMENT(1, R.drawable.android_entertainment), CLOTHING(2, R.drawable.android_clothing),
        TRANSPORTATION(3, R.drawable.android_transportation), MISC(4, R.drawable.android_misc);

        private int value;
        private int iconId;

        ItemType(int value, int iconId) {
            this.value = value;
            this.iconId = iconId;
        }

        public int getValue() {
            return value;
        }

        public int getIconId() {
            return iconId;
        }

        public static ItemType fromInt(int value) {
            for (ItemType p : ItemType.values()) {
                if (p.value == value) {
                    return p;
                }
            }
            return GROCERIES;
        }
    }

    @PrimaryKey
    private String itemID;

    private String itemName;
    private String description;
    private String price;
    private int itemType;

    public Item() {

    }

    public Item(String itemName, String description, String price, int itemType, boolean flag) {
        this.itemName = itemName;
        this.description = description;
        this.itemType = itemType;
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ItemType getItemType() {
        return ItemType.fromInt(itemType);
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getItemID() {
        return itemID;
    }
}