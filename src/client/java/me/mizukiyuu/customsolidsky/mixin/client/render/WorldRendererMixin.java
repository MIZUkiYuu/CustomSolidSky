package me.mizukiyuu.customsolidsky.mixin.client.render;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.mojang.blaze3d.systems.RenderSystem;
import me.mizukiyuu.customsolidsky.client.CustomsolidskyClient;
import me.mizukiyuu.customsolidsky.client.SkyColorSetting;
import me.mizukiyuu.customsolidsky.render.color.Color;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    @Shadow public abstract void renderSky(Matrix4f matrix4f, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean thickFog, Runnable fogCallback);

    // fog
    @WrapWithCondition(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/BackgroundRenderer;applyFog(Lnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/BackgroundRenderer$FogType;FZF)V"
            )
    )
    private boolean applyFogRedirect(Camera c, BackgroundRenderer.FogType ft, float f1, boolean b, float f2) {
        return CustomsolidskyClient.SKY_COLOR_SETTING.canRenderFog;
    }

    // sky bg Color
    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/systems/RenderSystem;clear(IZ)V"
            )
    )
    private void setSkyBackgroundColor(CallbackInfo ci) {
        if (CustomsolidskyClient.SKY_COLOR_SETTING.canRenderSky) return;

        Color color = CustomsolidskyClient.SKY_COLOR_SETTING.skyColor;

        RenderSystem.clearColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 1.0f);
    }

    // sky renderer: sky, stars, sun and moon, part of fog
    @WrapWithCondition(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/WorldRenderer;renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V")
    )
    private boolean canRenderSky(WorldRenderer wr, Matrix4f m, Matrix4f m2, float td, Camera c, boolean t, Runnable f){
        return CustomsolidskyClient.SKY_COLOR_SETTING.canRenderSky;
    }

}
