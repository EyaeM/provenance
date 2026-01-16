package net.mc3699.provenance.network;

import net.mc3699.provenance.Provenance;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record OpenScreenPayload(String menuID) implements CustomPacketPayload {

    public static final Type<OpenScreenPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Provenance.MODID,"open_screen"));

    public static final StreamCodec<FriendlyByteBuf, OpenScreenPayload> CODEC =
            StreamCodec.of((buf, payload) -> buf.writeUtf(payload.menuID),
                    (buf) -> new OpenScreenPayload(buf.readUtf()));

    public static void handle(OpenScreenPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            //var modGUI = TestMenu.createUI();
            //Minecraft.getInstance().setScreen(new ModularUIScreen(modGUI, Component.empty()));
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
