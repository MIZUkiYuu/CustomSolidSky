package me.mizukiyuu.customsolidsky.render.gui.widget;

import me.mizukiyuu.customsolidsky.render.color.Color;
import me.mizukiyuu.customsolidsky.render.gui.shape.rect.Rect;
import net.minecraft.client.gui.Drawable;

public abstract class Widget extends Rect implements Drawable {

    Color color;

    public Widget(float x, float y, float width, float height) {
        super(x, y, width, height);
        init();
    }

    public abstract void init();
}
