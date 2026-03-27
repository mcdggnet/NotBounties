package me.jadenp.notbounties.features.settings.integrations.external_api;

import me.gypopo.economyshopgui.api.EconomyShopGUIHook;
import me.gypopo.economyshopgui.objects.ShopItem;
import org.bukkit.inventory.ItemStack;

public class EconomyShopGUIClass {

    private EconomyShopGUIClass(){}

    /**
     * Gets the price of an item stack. Returns -1 if the item does not have a price.
     * @param itemStack The item to get the price of.

     * @return The price of the item.
     */
    public static double getSellPrice(ItemStack itemStack) {
        ShopItem shopItem = EconomyShopGUIHook.getShopItem(itemStack);
        if (shopItem == null)
            return -1.0;
        return EconomyShopGUIHook.getItemSellPrice(shopItem, itemStack);
    }
}
