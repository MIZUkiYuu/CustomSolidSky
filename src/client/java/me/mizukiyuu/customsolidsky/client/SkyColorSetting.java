package me.mizukiyuu.customsolidsky.client;

import me.mizukiyuu.customsolidsky.render.color.Color;
import me.mizukiyuu.customsolidsky.render.color.Colors;
import me.mizukiyuu.customsolidsky.util.math.Vec2f;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.CloudRenderMode;

public class SkyColorSetting {

    public static Color DEFAULT_SKY_COLOR = Color.of(Colors.GRAY);
    public Color skyColor = Color.of(DEFAULT_SKY_COLOR);
    public boolean enable = false;

    // value
    public float hueBarValue = 0.5f;
    public Vec2f BSMapValue = new Vec2f(1.0f, 0.0f);
    public boolean isBSMapExtended = false;

    // details
    public boolean canRenderSky = true;
    public boolean canRenderFog = true;
    public boolean canDisplayVignette = false;

    // gui
    public boolean hideHudAndPlayer = false;

    private CloudRenderMode cloudRenderMode;


    public void enable() {
        if(enable) return;

        enable = true;
        canRenderSky = false;
        canRenderFog = false;
        canDisplayVignette = false;

        cloudRenderMode = MinecraftClient.getInstance().options.getCloudRenderModeValue();
        MinecraftClient.getInstance().options.getCloudRenderMode().setValue(CloudRenderMode.OFF);
    }

    public void disable(){
        if(!enable) return;

        enable = false;
        canRenderSky = true;
        canRenderFog = true;
        canDisplayVignette = true;
        MinecraftClient.getInstance().options.getCloudRenderMode().setValue(cloudRenderMode);
    }

    public void resetSkyColor() {
        skyColor = new Color(DEFAULT_SKY_COLOR);
    }
}
