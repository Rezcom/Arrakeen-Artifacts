package rezcom.arrakis.ixian;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import rezcom.arrakis.Main;
import rezcom.arrakis.stillsuit.stillsuitFunctions;

public class ixianConsumeEvent implements Listener {

	public static boolean ixianDebug = false;
	public static final Component playerMessage =
			Component.text("Inner memories have cleansed an item.").color(TextColor.color(0x0CCF2A)).decoration(TextDecoration.ITALIC,false);

	@EventHandler
	void onPlayerDrink(PlayerItemConsumeEvent event){
		Player player = event.getPlayer();
		ItemStack foodItem = event.getItem();
		Main.sendDebugMessage("Ixian proc",ixianDebug);
		if (ixianFunctions.isWearingIxian(player) && stillsuitFunctions.replenishFoods.contains(foodItem.getType()) && player.getLevel() >= 10){
			// Player is wearing ixian probe
			Main.sendDebugMessage("Wearing ixian",ixianDebug);
			ItemStack leftHand = player.getInventory().getItemInOffHand();
			ItemMeta leftMeta = leftHand.getItemMeta();

			ItemStack rightHand = player.getInventory().getItemInMainHand();
			ItemMeta rightMeta = rightHand.getItemMeta();
			if (leftMeta != null && leftMeta.hasEnchant(Enchantment.MENDING)){
				Main.sendDebugMessage("left hand had it",ixianDebug);
				useIxian(player,leftHand);
			} else if (rightMeta != null && rightMeta.hasEnchant(Enchantment.MENDING)){
				Main.sendDebugMessage("right hand had it",ixianDebug);
				useIxian(player, rightHand);
			}
		}
	}

	private static void useIxian(Player player, ItemStack itemStack){
		ItemMeta itemMeta = itemStack.getItemMeta();
		Damageable damageable = (Damageable) itemMeta;
		damageable.setDamage(Math.max(damageable.getDamage() - determineDurabilityGain(player),0));
		itemStack.setItemMeta(damageable);
		player.setLevel(player.getLevel() - 10);
		player.playSound(player.getLocation(), Sound.BLOCK_BELL_USE, 0.8f, 1.5f);
		player.playSound(player.getLocation(), Sound.BLOCK_BELL_RESONATE, 0.75f, 0.55f);
		player.playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE, 0.75f, 0.3f);
		player.sendMessage(playerMessage);
	}

	private static int determineDurabilityGain(Player player){
		int level = player.getLevel();
		if (level < 10){
			return 0;
		}else if (level == 10){
			return 160;
		} else if (level <= 20){
			return 585;
		} else if (level <= 30){
			return 1268;
		} else {
			return 2288;
		}
	}

}
