package me.mizukiyuu.customsolidsky.mixin.client.gui;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.mizukiyuu.customsolidsky.client.CustomsolidskyClient;
import me.mizukiyuu.customsolidsky.client.SkyColorSetting;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.swing.text.html.parser.Entity;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(
            method = "render",
            at = @At("HEAD"),
            cancellable = true
    )
    private void renderHud(CallbackInfo ci){
        if(CustomsolidskyClient.SKY_COLOR_SETTING.hideHudAndPlayer){
            ci.cancel();
        }
    }

    // vignette
    @ModifyExpressionValue(
            method = "renderMiscOverlays",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/MinecraftClient;isFancyGraphicsOrBetter()Z"
            )
    )
    private boolean displayVignette(boolean bl) {
        return bl && CustomsolidskyClient.SKY_COLOR_SETTING.canDisplayVignette;
    }
}
