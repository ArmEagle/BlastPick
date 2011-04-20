package me.pwnage.bukkit.BlastPick;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

public class bpbl extends PlayerListener
{
    private bpm plugin;

    public bpbl(bpm plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if(!event.getAction().equals(event.getAction().RIGHT_CLICK_BLOCK))
            return;

        Player p = event.getPlayer();
        if (p.getItemInHand().getTypeId() == 278)
        {
            boolean canUse = false;
            if (this.plugin.perms != null)
            {
                canUse = this.plugin.ph.has(p, "blastpick.usepick");
            }
            else canUse = event.getPlayer().isOp();

            if ((canUse) && (this.plugin.playersUsing.containsKey(p.getName())))
            {
                int x = (int)p.getLocation().getPitch();
                Location bl = event.getClickedBlock().getLocation();
                if (x >= 35)
                {
                    for (int pos = 0; pos < ((Integer)this.plugin.playersUsing.get(p.getName())).intValue(); pos++)
                    {
                        Block nb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX(), bl.getBlockY() - pos, bl.getBlockZ()));
                        replaceBlock(nb);
                    }
                } else if (x > -55)
                {
                    int dir = (int)p.getLocation().getYaw();
                    if (dir < 0)
                    {
                        dir *= -1;
                    }
                    dir %= 360;
                    if ((dir >= 300) || ((dir >= 0) && (dir <= 60)))
                    {
                        for (int pos = 0; pos < ((Integer)this.plugin.playersUsing.get(p.getName())).intValue(); pos++)
                        {
                            Block nb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX(), bl.getBlockY(), bl.getBlockZ() + pos));
                            replaceBlock(nb);
                        }
                    }
                    if ((dir > 60) && (dir <= 120))
                    {
                        for (int pos = 0; pos < ((Integer)this.plugin.playersUsing.get(p.getName())).intValue(); pos++)
                        {
                            Block nb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX() - pos, bl.getBlockY(), bl.getBlockZ()));
                            replaceBlock(nb);
                        }
                    }
                    if ((dir > 120) && (dir <= 210))
                    {
                        for (int pos = 0; pos < ((Integer)this.plugin.playersUsing.get(p.getName())).intValue(); pos++)
                        {
                            Block nb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX(), bl.getBlockY(), bl.getBlockZ() - pos));
                            replaceBlock(nb);
                        }
                    }
                    if ((dir > 210) && (dir <= 300))
                    {
                        for (int pos = 0; pos < ((Integer)this.plugin.playersUsing.get(p.getName())).intValue(); pos++)
                        {
                            Block nb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX() + pos, bl.getBlockY(), bl.getBlockZ()));
                            replaceBlock(nb);
                        }
                    }
                } else if (x <= -35)
                {
                    for (int pos = 0; pos < ((Integer)this.plugin.playersUsing.get(p.getName())).intValue(); pos++)
                    {
                        Block nb = p.getWorld().getBlockAt(new Location(p.getWorld(), bl.getBlockX(), bl.getBlockY() + pos, bl.getBlockZ()));
                        replaceBlock(nb);
                    }
                }
            }
        }
    }

    public void replaceBlock(Block b)
    {
        switch (b.getType())
        {
            case FURNACE:
            case CHEST:
            case WORKBENCH:
                return;
            default:
                b.setType(Material.AIR);
        }
    }
}