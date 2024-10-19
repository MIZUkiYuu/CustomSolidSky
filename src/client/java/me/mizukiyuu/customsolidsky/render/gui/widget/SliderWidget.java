package me.mizukiyuu.customsolidsky.render.gui.widget;

import me.mizukiyuu.customsolidsky.render.color.Color;
import me.mizukiyuu.customsolidsky.render.color.Colors;
import me.mizukiyuu.customsolidsky.render.gui.shape.circle.CircularSectorShape;
import me.mizukiyuu.customsolidsky.util.math.Vec2f;
import net.minecraft.client.gui.DrawContext;

import java.util.Arrays;
import java.util.List;

public class SliderWidget extends Widget {

    private double value;
    private Vec2f valuePos;
    private CircularSectorShape shape;


    private static final List<Color> SLIDER_COLORS = Arrays.asList(Color.of(Colors.WHITE), Color.of(Colors.BLACK), Color.of(Colors.BLACK), Color.of(Colors.RED));

    public SliderWidget(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public void init() {
        shape = new CircularSectorShape(x, y, 40, 80, 160, Color.of(Colors.WHITE).setAlpha(0.6f))
                .outline(8, Color.of(Colors.RED).setAlpha(0.6f));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        shape.render(context);
        shape.renderBoundingRect(context, Colors.BLUE.color);
    }
}
