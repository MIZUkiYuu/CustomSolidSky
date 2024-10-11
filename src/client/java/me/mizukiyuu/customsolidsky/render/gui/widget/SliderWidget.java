package me.mizukiyuu.customsolidsky.render.gui.widget;

import me.mizukiyuu.customsolidsky.render.color.Color;
import me.mizukiyuu.customsolidsky.render.gui.shape.arc.CircularSectorShape;
import me.mizukiyuu.customsolidsky.util.math.Vec2f;
import net.minecraft.client.gui.DrawContext;

import java.util.Arrays;
import java.util.List;

public class SliderWidget extends Widget {

    private double value;
    private Vec2f valuePos;
    private CircularSectorShape shape;


    private static final List<Color> SLIDER_COLORS = Arrays.asList(Color.of(Color.WHITE), Color.of(Color.BLACK), Color.of(Color.BLACK), Color.of(Color.RED));

    public SliderWidget(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public void init() {
        shape = new CircularSectorShape(x, y, 80, 0, 90, Color.of(Color.WHITE).setAlpha(0.6f))
                .stroke(8, new Color(Color.RED).setAlpha(0.6f));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        shape.render(context);
        shape.renderBoundingRect(context, Color.BLUE);
    }
}
