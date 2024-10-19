package me.mizukiyuu.customsolidsky.render.screen;

import me.mizukiyuu.customsolidsky.client.CustomsolidskyClient;
import me.mizukiyuu.customsolidsky.client.SkyColorSetting;
import me.mizukiyuu.customsolidsky.render.component.ColorPickerComponent;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class CustomSolidSkyOptionsScreen extends Screen {
    final Screen parent;

    // component
    ColorPickerComponent colorPicker;

    public CustomSolidSkyOptionsScreen(Text title, Screen parent) {
        super(title);
        this.parent = parent;
    }

    @Override
    protected void init() {
        colorPicker = new ColorPickerComponent(width / 2.0f, height / 2.0f, 240);

        addDrawableChild(colorPicker);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
    }

    @Override
    public void close() {
        CustomsolidskyClient.SKY_COLOR_SETTING.hideHudAndPlayer = false;
        this.client.setScreen(parent);
    }
}
