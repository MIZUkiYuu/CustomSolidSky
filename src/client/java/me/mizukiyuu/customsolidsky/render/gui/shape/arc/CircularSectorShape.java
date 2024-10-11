package me.mizukiyuu.customsolidsky.render.gui.shape.arc;

import me.mizukiyuu.customsolidsky.render.color.Color;
import me.mizukiyuu.customsolidsky.util.math.MathM;
import me.mizukiyuu.customsolidsky.util.render.RenderUtil;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class CircularSectorShape extends ArcShape<CircularSectorShape> {
    public CircularSectorShape(float x, float y, float radius, int degree_start, int degree_end, List<Color> colorList) {
        super(x, y, radius, degree_start,degree_end, colorList);
    }

    public CircularSectorShape(float x, float y, float radius, int degree_start, int degree_end, Color color) {
        super(x, y, radius, degree_start, degree_end, color);
    }

    @Override
    public CircularSectorShape clone() {
        return new CircularSectorShape(x, y, radius, degree_start, degree_end, cloneColorList());
    }

    @Override
    public void enableStroke() {
        previousRadius = radius;
        previousColor = getColor();

        setRadius(radius + strokeSize).setColor(strokeColor);
    }

    @Override
    public void disableStroke() {
        setRadius(previousRadius).setColor(previousColor);
    }

    @Override
    public boolean inside(float x, float y) {
        return false;
    }

    private final List<Float> horizontalList = new ArrayList<>();
    private final List<Float> verticalList = new ArrayList<>();
    @Override
    public void updateBoundingRect() {
        horizontalList.add(x);
        verticalList.add(y);

        horizontalList.add(MathM.COS[degree_start] * radius + x);
        verticalList.add(MathM.SIN[degree_start] * radius + y);

        horizontalList.add(MathM.COS[degree_end] * radius + x);
        verticalList.add(MathM.SIN[degree_end] * radius + y);

        for (int a = (int) (Math.ceil(degree_start * 4 / 360.0f)) * 90; a < degree_end; a += 90)
        {
            horizontalList.add(MathM.COS[a] * radius + x);
            verticalList.add(MathM.SIN[a] * radius + y);
        }

        horizontalList.sort(Comparator.naturalOrder());
        verticalList.sort(Comparator.naturalOrder());

        boundingRect.set(horizontalList.getFirst(), verticalList.getFirst(), horizontalList.getLast() - horizontalList.getFirst(), verticalList.getLast() - verticalList.getFirst());

        horizontalList.clear();
        verticalList.clear();
    }

    @Override
    public Consumer<DrawContext> defaultRenderConsumer() {
        return drawContext -> {
            vertexConsumer = drawContext.getVertexConsumers().getBuffer(RenderUtil.GUI_TRIANGLE_FAN);
            matrix4f = drawContext.getMatrices().peek().getPositionMatrix();

            RenderUtil.buildArcVertex(vertexConsumer, matrix4f, x, y, depth, degree_start, degree_end, radius, getColor(), true);
            RenderUtil.buildPointVertex(vertexConsumer, matrix4f, x, y, depth, getColor());
        };
    }
}
