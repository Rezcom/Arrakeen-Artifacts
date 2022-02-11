package rezcom.arrakis.bosses.TreacherousWatcher;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ElderGuardian;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import rezcom.arrakis.Main;
import rezcom.arrakis.bosses.VagrantSpirit.VagrantSpiritCommand;

import java.util.logging.Level;

public class TreacherousWatcherCommand implements CommandExecutor {

	public static final Component watcherName =
			Component.text("Treacherous Watcher").color(TextColor.color(0x1c2fbd)).decoration(TextDecoration.ITALIC,false);

	public TreacherousWatcherCommand(){

	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args){
		if (!(sender instanceof Player)){
			sender.sendMessage("Only players can execute this command.");
			return false;
		}
		Player player = (Player) sender;
		if (!(player.hasPermission("arrakis.treacherouswatcher"))){
			player.sendMessage("You don't have permission");
			return false;
		}

		LivingEntity mob = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), EntityType.ELDER_GUARDIAN);
		ElderGuardian elderGuardian = (ElderGuardian) mob;

		elderGuardian.customName(watcherName);
		elderGuardian.setCustomNameVisible(false);
		elderGuardian.setCanPickupItems(false);
		elderGuardian.setPersistent(true);
		elderGuardian.setCollidable(false);
		elderGuardian.setRemoveWhenFarAway(false);
		elderGuardian.setAI(true);

		try {
			elderGuardian.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(80);
			elderGuardian.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(1.0);
			elderGuardian.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(34.5);

		} catch (NullPointerException e){
			Main.logger.log(Level.WARNING,"Could not set GENERIC_FOLLOW_RANGE for Elder Guardian because the attribute returned null!");
		}

		EntityEquipment entityEquipment = elderGuardian.getEquipment();

		PotionEffect resist = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,Integer.MAX_VALUE,3);
		PotionEffect speed = new PotionEffect(PotionEffectType.SPEED,Integer.MAX_VALUE,2);


		return true;
	}
}
