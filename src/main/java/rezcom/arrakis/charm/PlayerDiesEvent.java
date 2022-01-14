package rezcom.arrakis.charm;

import com.comphenix.protocol.PacketType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import rezcom.arrakis.Main;

public class PlayerDiesEvent implements Listener {

    public static boolean charmDebug = false;

    private static final TextComponent charmDeathMessage = Component.text("It occurred to her that mercy was the ability to stop, if only for a moment. There was no mercy where there could be no stopping.").decoration(TextDecoration.ITALIC,true).color(TextColor.color(0x00f539));

    @EventHandler
    void onPlayerDies(PlayerDeathEvent event){
        Player player = event.getPlayer();

        // Check for left hand
        ItemStack leftStack = player.getInventory().getItemInOffHand();
        ItemMeta leftMeta = leftStack.getItemMeta();
        if (leftMeta != null && leftMeta.hasLore()){
            if (leftMeta.lore().contains(charmFunctions.charmIdentifier)){
                leftStack.subtract(1);
                PlayerKeepsInventory(player, event);
                return;
            }
        }

        for (ItemStack item : player.getInventory().getContents()){
            if (item != null){
                ItemMeta itemMeta = item.getItemMeta();
                if (itemMeta != null && itemMeta.hasLore() && itemMeta.lore().contains(charmFunctions.charmIdentifier)){
                    item.subtract(1);
                    PlayerKeepsInventory(player, event);
                    return;
                }
            }
        }

    }

    void PlayerKeepsInventory(Player player, PlayerDeathEvent event){
        // Call this to let the player keep their inventory
        event.getDrops().clear();
        player.sendMessage(Component.text("'The concept of progress acts as a protective mechanism to shield us from the future'").decoration(TextDecoration.ITALIC,true).color(TextColor.color(0xffb73b))
                .append(Component.text(" - Excerpt from 'Collected Sayings of Muad'Dib'").color(TextColor.color(0xff623b))));
        event.deathMessage(charmDeathMessage);
        event.setKeepInventory(true);
        event.setKeepLevel(false);
        event.setShouldDropExperience(true);
    }
}
