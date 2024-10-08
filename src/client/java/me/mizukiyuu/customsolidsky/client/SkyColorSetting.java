package me.mizukiyuu.customsolidsky.client;

import me.mizukiyuu.customsolidsky.render.color.Color;
import me.mizukiyuu.customsolidsky.util.math.Vec2f;

public class SkyColorSetting {
    public static Color DEFAULT_SKY_COLOR = Color.of(Color.GRAY);
    public Color skyColor = Color.of(DEFAULT_SKY_COLOR);
    public boolean enable = false;

    // value
    public float hueBarValue = 0.5f;
    public Vec2f BSMapValue = new Vec2f(1.0f, 0.0f);
    public boolean isBSMapExtended = false;

    // details
    public Boolean canRenderSky = true;
    public Boolean canRenderFog = true;

    // gui
    public Boolean hideHudAndPlayer = false;

    public void setEnable(boolean b) {
        enable = b;
        canRenderSky = !b;
        canRenderFog = !b;
    }

    public void resetSkyColor() {
        skyColor = new Color(DEFAULT_SKY_COLOR);
    }
}
