package me.mizukiyuu.customsolidsky.mixin.client.gui;

import me.mizukiyuu.customsolidsky.client.CustomsolidskyClient;
import me.mizukiyuu.customsolidsky.client.SkyColorSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Redirect(
            method = "renderWorld",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z",
                    opcode = Opcodes.GETFIELD
            )
    )
    private boolean renderHoldItem(GameRenderer gr){
        return ((GameRendererAccessor) MinecraftClient.getInstance().gameRenderer).getRenderHand() && !CustomsolidskyClient.SKY_COLOR_SETTING.hideHudAndPlayer;
    }
}

