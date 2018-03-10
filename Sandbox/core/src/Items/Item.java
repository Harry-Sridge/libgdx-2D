package Items;

import com.badlogic.gdx.graphics.Texture;
import com.sandbox.game.Enums;

/**
 * Created by Southridge on 2018-03-03.
 */
public class Item {

    String name;
    String tooltip;
    Enums.itemType itemType;

    Texture texture;
    int textureSize;
    int price;

    public Item(int price)
    {
        this.price = price;
        textureSize = 5;
    }
}
