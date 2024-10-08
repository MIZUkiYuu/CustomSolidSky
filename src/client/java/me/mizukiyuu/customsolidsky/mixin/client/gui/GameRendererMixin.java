package me.mizukiyuu.customsolidsky.mixin.client.gui;

import me.mizukiyuu.customsolidsky.client.CustomsolidskyClient;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Redirect(
            method = "renderWorld",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z")
    )
    private boolean renderHoldItem(GameRenderer gr){
        return !CustomsolidskyClient.SKY_OPTIONS.hideHudAndPlayer;
    }
}
