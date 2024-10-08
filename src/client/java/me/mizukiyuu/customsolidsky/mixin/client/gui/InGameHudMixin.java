package me.mizukiyuu.customsolidsky.mixin.client.gui;

import me.mizukiyuu.customsolidsky.client.CustomsolidskyClient;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(
            method = "render",
            at = @At("HEAD"),
            cancellable = true
    )
    private void renderHud(CallbackInfo ci){
        if(CustomsolidskyClient.SKY_OPTIONS.hideHudAndPlayer){
            ci.cancel();
        }
    }
}
