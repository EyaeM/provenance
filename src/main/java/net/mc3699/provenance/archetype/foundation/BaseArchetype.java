package net.mc3699.provenance.archetype.foundation;

import net.mc3699.provenance.ability.foundation.AmbientAbility;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.Set;

public abstract class BaseArchetype {

    public abstract Component getName();

    public abstract List<Component> getDescription();

    public abstract Set<ResourceLocation> getGrantedAbilities();

    public abstract List<AmbientAbility> getAmbientAbilities();
}
