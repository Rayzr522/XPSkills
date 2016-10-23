/**
 *
 */
package com.perceivedev.xpskills;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.perceivedev.perceivecore.gui.GUI;
import com.perceivedev.perceivecore.gui.component.Label;
import com.perceivedev.xpskills.skills.Skill;

/**
 * @author Rayzr
 */
public class CommandHandler implements CommandExecutor {

    @SuppressWarnings("unused")
    private XPSkills plugin;
    private GUI      gui;

    public CommandHandler(XPSkills plugin) {
        this.plugin = plugin;
        gui = new GUI("Skills", 6);

        List<Skill> skills = new ArrayList<>(plugin.getSkillManager().getSkills().values());
        for (int i = 0; i < skills.size(); i++) {
            Skill skill = skills.get(i);
            gui.addComponent(new Label("&cSkill: &a" + skill.getName(), "Blah blah blah", "Some text here"), i % 9, i / 9);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.
     * CommandSender, org.bukkit.command.Command, java.lang.String,
     * java.lang.String[])
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            // TODO: Tell them they can't do that
            return true;
        }

        Player p = (Player) sender;

        if (args.length < 1) {
            gui.open(p);
            return true;
        }

        return true;

    }

}
