package net.mc3699.provenance.handlers;

import net.mc3699.provenance.ProvenanceDataHandler;
import net.mc3699.provenance.ability.foundation.BaseAbility;
import net.mc3699.provenance.ability.foundation.ToggleAbility;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@EventBusSubscriber
public class AbilityTickHandler {

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Pre event) {
        for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {
            tickAmbientAbilities(player);
            tickToggleAbilities(player);
            decrementCooldowns(player);
            tickBackground(player);
        }
    }

    private static void tickBackground(ServerPlayer player) {
        for (BaseAbility ability : ProvenanceDataHandler.getAbilities(player)) {
            ability.backgroundTick(player);
        }
    }

    private static void tickAmbientAbilities(ServerPlayer player) {
        for (BaseAbility ability : ProvenanceDataHandler.getAmbientAbilities(player)) {
            try {
                if (ability.canExecute(player)) {
                    ability.execute(player);
                }
            } catch (Exception ignored) {
            }
        }
    }

    private static void tickToggleAbilities(ServerPlayer player) {
        for (BaseAbility ability : ProvenanceDataHandler.getAbilities(player)) {
            if (!(ability instanceof ToggleAbility toggle)) continue;

            ResourceLocation id = ProvenanceDataHandler.getIdForAbility(player, ability);
            if (id == null) continue;

            if (toggle.isEnabled(player, id)) {
                toggle.tick(player);
            }
        }
    }

    private static void decrementCooldowns(ServerPlayer player) {
        for (BaseAbility ability : ProvenanceDataHandler.getAbilities(player)) {
            ResourceLocation id = ProvenanceDataHandler.getIdForAbility(player, ability);
            if (id == null) continue;

            int cd = ProvenanceDataHandler.getCooldown(player, id);
            if (cd > 0) {
                ProvenanceDataHandler.setCooldown(player, id, cd - 1);
            }
        }
    }
}
