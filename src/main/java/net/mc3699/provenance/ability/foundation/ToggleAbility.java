package net.mc3699.provenance.ability.foundation;

import net.mc3699.provenance.ProvenanceDataHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public abstract class ToggleAbility extends BaseAbility {

    public void tick(ServerPlayer serverPlayer) {
        float ap = ProvenanceDataHandler.getAP(serverPlayer);
        if (ap >= getUseCost()) {
            ProvenanceDataHandler.changeAP(serverPlayer, -getUseCost());
        } else {
            ResourceLocation id = ProvenanceDataHandler.getIdForAbility(serverPlayer, this);
            if (id != null) {
                setEnabled(serverPlayer, id, false);
            }
        }
    }

    protected void onToggle(ServerPlayer player, boolean enabled) {}


    public boolean isEnabled(ServerPlayer player, ResourceLocation id) {
        return ProvenanceDataHandler.isAbilityEnabled(player, id);
    }

    public void setEnabled(ServerPlayer player, ResourceLocation id, boolean enabled) {
        boolean prev = ProvenanceDataHandler.isAbilityEnabled(player, id);
        ProvenanceDataHandler.setAbilityEnabled(player, id, enabled);
        if (prev != enabled) {
            onToggle(player, enabled);
        }
    }

    protected final void disableSelf(ServerPlayer player) {
        ResourceLocation id =
                ProvenanceDataHandler.getIdForAbility(player, this);
        if (id != null) {
            setEnabled(player, id, false);
        }
    }


}
