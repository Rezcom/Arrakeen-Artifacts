package rezcom.arrakis.stylus;

import org.bukkit.Sound;
import org.bukkit.entity.Stray;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rezcom.arrakis.Main;

public class GesseritMobDeath implements Listener {

	public static boolean hollowDeathDebug = true;
	@EventHandler
	void onGesseritDeath(EntityDeathEvent event){
		if (!(event.getEntity() instanceof Stray)){
			return;
		}
		Main.sendDebugMessage("Stray died",hollowDeathDebug);
		Stray stray = (Stray) event.getEntity();
		ItemStack bow = stray.getEquipment().getItemInMainHand();
		if (!bow.hasItemMeta()){
			return;
		}
		ItemMeta meta = bow.getItemMeta();

		if (meta.hasLore() && meta.lore().contains(GesseritMobCommand.customBowIdentifier)){
			Main.sendDebugMessage("Was custom bow",hollowDeathDebug);
			event.getDrops().clear();
			event.getDrops().add(stylusFunctions.stylusItemStack);
		} else {
			Main.sendDebugMessage("Wasn't custom bow or doesn't contain lore",hollowDeathDebug);
		}

	}
}
