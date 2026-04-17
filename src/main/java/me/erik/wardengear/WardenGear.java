package me.erik.wardengear;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class WardenGear extends JavaPlugin implements Listener {

    private final Map<UUID, Long> cooldown = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onWardenDeath(EntityDeathEvent e) {
        if (e.getEntityType() == EntityType.WARDEN) {
            if (Math.random() <= getConfig().getDouble("warden.drop_chance")) {
                ItemStack heart = new ItemStack(Material.ECHO_SHARD);
                var meta = heart.getItemMeta();
                meta.setDisplayName("§5Warden Heart");
                meta.setLore(List.of("§7Craft OP Gear"));
                heart.setItemMeta(meta);
                e.getDrops().add(heart);
            }
        }
    }

    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (!p.isSneaking()) return;

        long now = System.currentTimeMillis();

        if (cooldown.containsKey(p.getUniqueId()) &&
                cooldown.get(p.getUniqueId()) > now) {
            p.sendMessage("§cCooldown!");
            return;
        }

        Player target = Bukkit.getOnlinePlayers().stream()
                .filter(pl -> !pl.equals(p))
                .min(Comparator.comparingDouble(pl -> pl.getLocation().distance(p.getLocation())))
                .orElse(null);

        if (target == null) return;

        int count = getConfig().getInt("abilities.lightning_count");

        for (int i = 0; i < count; i++) {
            target.getWorld().strikeLightning(target.getLocation());
        }

        cooldown.put(p.getUniqueId(),
                now + getConfig().getInt("abilities.cooldown_seconds") * 1000L);
    }
    }
