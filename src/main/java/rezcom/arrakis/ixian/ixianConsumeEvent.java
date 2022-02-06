package rezcom.arrakis.ixian;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import rezcom.arrakis.stillsuit.stillsuitFunctions;

public class ixianConsumeEvent implements Listener {

	public static final Component playerMessage =
			Component.text("Inner memories have cleansed an item.").color(TextColor.color(0x0CCF2A)).decoration(TextDecoration.ITALIC,false);

	@EventHandler
	void onPlayerDrink(PlayerItemConsumeEvent event){
		Player player = event.getPlayer();
		ItemStack foodItem = event.getItem();

		if (ixianFunctions.isWearingIxian(player) && stillsuitFunctions.replenishFoods.contains(foodItem.getType()) && player.getLevel() >= 10){
			// Player is wearing ixian probe
			ItemStack leftHand = player.getInventory().getItemInOffHand();
			ItemMeta leftMeta = leftHand.getItemMeta();

			ItemStack rightHand = player.getInventory().getItemInMainHand();
			ItemMeta rightMeta = rightHand.getItemMeta();
			if (leftMeta.hasEnchant(Enchantment.MENDING)){
				Damageable damageable = (Damageable) leftMeta;
				damageable.setDamage(Math.min(damageable.getDamage() - 710,0));
				player.setLevel(player.getLevel() - 10);
				player.sendMessage(playerMessage);
			} else if (rightMeta.hasEnchant(Enchantment.MENDING)){
				Damageable damageable = (Damageable) rightMeta;
				damageable.setDamage(Math.min(damageable.getDamage() - 710,0));
				player.setLevel(player.getLevel() - 10);
				player.sendMessage(playerMessage);
			}
		}
	}
}
