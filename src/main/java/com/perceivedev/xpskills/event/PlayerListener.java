package com.perceivedev.xpskills.event;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import com.perceivedev.perceivecore.particle.effect.shapes.Helix;
import com.perceivedev.perceivecore.particle.effect.shapes.Orientation;
import com.perceivedev.perceivecore.util.TextUtils;
import com.perceivedev.xpskills.XPSkills;
import com.perceivedev.xpskills.api.SkillPointApplyEvent;
import com.perceivedev.xpskills.api.SkillPointGainEvent;
import com.perceivedev.xpskills.managment.PlayerManager;
import com.perceivedev.xpskills.skills.SkillType;

/**
 * Listens to Player related events
 */
public class PlayerListener implements Listener {

    private XPSkills plugin;

    public PlayerListener(XPSkills plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onXPGain(PlayerExpChangeEvent e) {
        Player p = e.getPlayer();

        // if player reaches next level award skill points
        if (p.getExp() * p.getExpToLevel() + e.getAmount() >= p.getExpToLevel()) {
            SkillPointGainEvent event = new SkillPointGainEvent(p, 1);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                // TODO: 24.10.2016 Remove?
                p.sendMessage(TextUtils.colorize("&6You were &cnot &6given &a%d &6skill points!"));
            } else {
                plugin.getPlayerManager().getData(p.getUniqueId()).giveFreeSkillPoints(1);

                // TODO: 23.10.2016 Send message?
                new Helix(Orientation.VERTICAL, 0.25, Particle.END_ROD, 1.2, 2.5, 3.0).display(p.getLocation());
                p.sendMessage(TextUtils.colorize(String.format("&6You were given &a%d &6skill points!", 1)));
            }
        }
    }

    // @EventHandler
    public void onHit(PlayerToggleSneakEvent event) {
        if (event.isSneaking()) {
            PlayerManager.PlayerData data = plugin.getPlayerManager().getData(event.getPlayer().getUniqueId());

            for (SkillType skill : plugin.getSkillManager().getSkills().keySet()) {
                data.setSkill(skill, event.getPlayer().getLevel());
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            System.out.println("Base damage dealt: " + event.getDamage());
        }
    }

    @EventHandler
    public void onSkillLevelUp(SkillPointApplyEvent e) {
        if (e.getPlayer().getName().equals("ZP4RKER")) {
            e.setCancelled(true);
        }
    }

}