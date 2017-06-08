/*
 *  Copyright 2014 eccentric_nz.
 */
package me.eccentric_nz.gamemodeinventories;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

/**
 *
 * @author eccentric_nz
 */
public class GameModeInventoriesPhysicsListener implements Listener {

    private final GameModeInventories plugin;
    private final List<Material> willdrop = new ArrayList<>();
    private final List<Material> doors = new ArrayList<>();
    private final List<Material> plates = new ArrayList<>();

    public GameModeInventoriesPhysicsListener(GameModeInventories plugin) {
        this.plugin = plugin;
        this.willdrop.add(Material.ACACIA_DOOR);
        this.willdrop.add(Material.ACTIVATOR_RAIL);
        this.willdrop.add(Material.BANNER);
        this.willdrop.add(Material.BIRCH_DOOR);
        this.willdrop.add(Material.BROWN_MUSHROOM);
        this.willdrop.add(Material.CARPET);
        this.willdrop.add(Material.CROPS);
        this.willdrop.add(Material.DARK_OAK_DOOR);
        this.willdrop.add(Material.DETECTOR_RAIL);
        this.willdrop.add(Material.DIODE_BLOCK_OFF);
        this.willdrop.add(Material.DIODE_BLOCK_ON);
        this.willdrop.add(Material.DOUBLE_PLANT);
        this.willdrop.add(Material.GOLD_PLATE);
        this.willdrop.add(Material.FLOWER_POT);
        this.willdrop.add(Material.IRON_DOOR_BLOCK);
        this.willdrop.add(Material.IRON_PLATE);
        this.willdrop.add(Material.IRON_TRAPDOOR);
        this.willdrop.add(Material.JUNGLE_DOOR);
        this.willdrop.add(Material.LADDER);
        this.willdrop.add(Material.LEVER);
        this.willdrop.add(Material.NETHER_WARTS);
        this.willdrop.add(Material.PAINTING);
        this.willdrop.add(Material.RAILS);
        this.willdrop.add(Material.REDSTONE_COMPARATOR_OFF);
        this.willdrop.add(Material.REDSTONE_COMPARATOR_ON);
        this.willdrop.add(Material.REDSTONE_TORCH_OFF);
        this.willdrop.add(Material.REDSTONE_TORCH_ON);
        this.willdrop.add(Material.RED_MUSHROOM);
        this.willdrop.add(Material.RED_MUSHROOM);
        this.willdrop.add(Material.RED_ROSE);
        this.willdrop.add(Material.SAPLING);
        this.willdrop.add(Material.SAPLING);
        this.willdrop.add(Material.SIGN_POST);
        this.willdrop.add(Material.SPRUCE_DOOR);
        this.willdrop.add(Material.STANDING_BANNER);
        this.willdrop.add(Material.STONE_BUTTON);
        this.willdrop.add(Material.STONE_PLATE);
        this.willdrop.add(Material.SUGAR_CANE_BLOCK);
        this.willdrop.add(Material.TRAP_DOOR);
        this.willdrop.add(Material.TRIPWIRE_HOOK);
        this.willdrop.add(Material.VINE);
        this.willdrop.add(Material.WALL_BANNER);
        this.willdrop.add(Material.WALL_SIGN);
        this.willdrop.add(Material.WOODEN_DOOR);
        this.willdrop.add(Material.WOOD_BUTTON);
        this.willdrop.add(Material.WOOD_PLATE);
        this.willdrop.add(Material.YELLOW_FLOWER);
        this.doors.add(Material.ACACIA_DOOR);
        this.doors.add(Material.BIRCH_DOOR);
        this.doors.add(Material.DARK_OAK_DOOR);
        this.doors.add(Material.IRON_DOOR_BLOCK);
        this.doors.add(Material.JUNGLE_DOOR);
        this.doors.add(Material.SPRUCE_DOOR);
        this.doors.add(Material.WOODEN_DOOR);
        this.plates.add(Material.GOLD_PLATE);
        this.plates.add(Material.IRON_PLATE);
        this.plates.add(Material.STONE_PLATE);
        this.plates.add(Material.WOOD_PLATE);
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onBlockPhysics(BlockPhysicsEvent event) {
        if (!willdrop.contains(event.getBlock().getType())) {
            return;
        }
        if (!plugin.getConfig().getBoolean("track_creative_place.enabled") || !plugin.getConfig().getBoolean("track_creative_place.attached_block")) {
            return;
        }
        Block block = event.getBlock();
        if (!plugin.getConfig().getStringList("track_creative_place.worlds").contains(block.getWorld().getName())) {
            return;
        }
        if (doors.contains(block.getType()) && plates.contains(event.getChangedType())) {
            return;
        }
        if (willdrop.contains(block.getType())) {
            String gmiwc = block.getWorld().getName() + "," + block.getChunk().getX() + "," + block.getChunk().getZ();
            if (!plugin.getCreativeBlocks().containsKey(gmiwc)) {
                return;
            }
            // check if the block was placed in creative
            if (plugin.getCreativeBlocks().get(gmiwc).contains(event.getBlock().getLocation().toString())) {
                event.setCancelled(true);
            }
        }
    }
}
