package me.jadenp.notbounties.features.settings.integrations.external_api;

import de.bluecolored.bluemap.api.BlueMapAPI;
import me.jadenp.notbounties.NotBounties;
import me.jadenp.notbounties.bounty_events.BountyClaimEvent;
import me.jadenp.notbounties.bounty_events.BountyRemoveEvent;
import me.jadenp.notbounties.bounty_events.BountySetEvent;
import me.jadenp.notbounties.utils.BountyManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class BlueMapClass implements Listener {

    public BlueMapClass() {
        BlueMapAPI.onEnable(api -> {
            // When BlueMap enables, hide all players that currently have bounties
            BountyManager.getAllBounties(-1).forEach(bounty ->
                api.getWebApp().setPlayerVisibility(bounty.getUUID(), false)
            );
        });
    }

    public static void updatePlayerVisibility(UUID uuid) {
        BlueMapAPI.getInstance().ifPresent(api ->
            api.getWebApp().setPlayerVisibility(uuid, !BountyManager.hasBounty(uuid))
        );
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBountySet(BountySetEvent event) {
        updatePlayerVisibility(event.getBounty().getUUID());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBountyRemove(BountyRemoveEvent event) {
        UUID uuid = event.getBounty().getUUID();
        // Delay by 1 tick so the bounty is fully removed before checking
        NotBounties.getServerImplementation().global().runDelayed(() -> updatePlayerVisibility(uuid), 1);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBountyClaim(BountyClaimEvent event) {
        UUID uuid = event.getBounty().getUUID();
        NotBounties.getServerImplementation().global().runDelayed(() -> updatePlayerVisibility(uuid), 1);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        updatePlayerVisibility(event.getPlayer().getUniqueId());
    }
}
