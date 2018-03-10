package Entities;

import Items.Item;

/**
 * Created by Southridge on 2018-03-03.
 */
public class Chest extends Entity{

    private Item[] items;
    boolean locked;

    public Chest()
    {
        items = new Item[20];
    }

    @Override
    public void Interact(Entity entity)
    {
        if(!locked)
            DisplayItems();
    }

    private void DisplayItems()
    {

    }
}
