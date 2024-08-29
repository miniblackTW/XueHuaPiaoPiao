package me.miniblacktw.xuehuapiaopiao;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("xuehuapiaopiao").setExecutor((sender, command, label, args) -> {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (player.hasPermission("xuehua.piao")) {
                    spawnParticles(player);
                    spawnArmorStand(player);
                } else {
                    player.sendMessage("§cYou do not have permission §8(§bxuehua.piao§8)");
                }
            }
            return true;
        });
    }

    private void spawnParticles(Player player) {
        Location loc = player.getLocation().add(0, 3, 0);
        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                if (ticks >= 100) {
                    this.cancel();
                    return;
                }

                for (int i = 0; i < 5; i++) {
                    loc.getWorld().playEffect(loc.clone().add(Math.random() * 5 - 2.5, 0, Math.random() * 5 - 2.5), Effect.SNOW_SHOVEL, 0);
                }

                ticks++;
            }
        }.runTaskTimer(this, 0L, 1L);
    }

    private void spawnArmorStand(Player player) {
        Location loc = player.getLocation().add(Math.random() * 5 - 2.5, 0, Math.random() * 5 - 2.5);
        ArmorStand armorStand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        armorStand.setVisible(false);

        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwner("oimd");
        skull.setItemMeta(skullMeta);

        armorStand.setHelmet(skull);
        armorStand.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE, 1, (short) 1));
        armorStand.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS, 1, (short) 1));
        armorStand.setBoots(new ItemStack(Material.LEATHER_BOOTS, 1, (short) 0));

        new BukkitRunnable() {
            @Override
            public void run() {
                armorStand.remove();
            }
        }.runTaskLater(this, 100L);
    }
}