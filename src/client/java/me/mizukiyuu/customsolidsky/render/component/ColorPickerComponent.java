package me.mizukiyuu.customsolidsky.render.component;

import me.mizukiyuu.customsolidsky.render.color.Color;
import me.mizukiyuu.customsolidsky.render.gui.widget.SliderWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;

import java.util.Arrays;
import java.util.List;

public class ColorPickerComponent implements Drawable, Element, Selectable {

    private final float x;
    private final float y;
    private final float width;

    // slider
    SliderWidget slider;
    private static final List<Color> HUE_BAR_COLORS = Arrays.asList(Color.RED, Color.MAGENTA, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.RED);

    /**
     * @param x x coordinate of the slider center point
     * @param y y coordinate of the slider center point
     * @param width the width of the slider
     */
    public ColorPickerComponent(float x, float y, float width) {
        this.x = x;
        this.y = y;
        this.width = width;
        init();
    }

    public void init(){
        slider = new SliderWidget(x - width / 2, y - 3 / 2f, width, 3);

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        slider.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void setFocused(boolean focused) {

    }

    @Override
    public boolean isFocused() {
        return false;
    }

    @Override
    public SelectionType getType() {
        return SelectionType.NONE;
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {

    }
}
