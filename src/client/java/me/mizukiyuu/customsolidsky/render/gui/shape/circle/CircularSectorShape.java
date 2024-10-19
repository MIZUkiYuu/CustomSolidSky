package me.mizukiyuu.customsolidsky.render.gui.shape.circle;

import me.mizukiyuu.customsolidsky.render.IRenderBuffer;
import me.mizukiyuu.customsolidsky.render.color.Color;
import me.mizukiyuu.customsolidsky.util.math.MathM;
import me.mizukiyuu.customsolidsky.render.gui.shape.ShapeRenderer;
import net.minecraft.client.render.VertexFormat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isMouseInShape(float mouseX, float mouseY) {
        return false;
    }

    @Override
    public void setOutline() {
        this.radius += outlineSize;
    }

    @Override
    public void resetOutline() {
        this.radius -= outlineSize;
    }

    @Override
    public void updateBoundingRect() {
        List<Float> horizontalList = new ArrayList<>();
        List<Float> verticalList = new ArrayList<>();
        float largeRadius = radius + outlineSize;

        horizontalList.add(x);
        verticalList.add(y);

        horizontalList.add(MathM.COS[degree_start] * largeRadius + x);
        verticalList.add(y - MathM.SIN[degree_start] * largeRadius);

        horizontalList.add(MathM.COS[degree_end] * largeRadius + x);
        verticalList.add(y - MathM.SIN[degree_end] * largeRadius);

        for (int a = (int) (Math.ceil(degree_start * 4 / 360.0f)) * 90; a < degree_end; a += 90)
        {
            horizontalList.add(MathM.COS[a] * largeRadius + x);
            verticalList.add(y - MathM.SIN[a] * largeRadius);
        }

        horizontalList.sort(Comparator.naturalOrder());
        verticalList.sort(Comparator.naturalOrder());

        boundingRect.set(horizontalList.getFirst(), verticalList.getFirst(), horizontalList.getLast() - horizontalList.getFirst(), verticalList.getLast() - verticalList.getFirst());
    }

    @Override
    public IRenderBuffer defaultRenderBuffer() {
        return matrix4f -> {
            buffer = getBuffer(VertexFormat.DrawMode.TRIANGLE_FAN);

            ShapeRenderer.buildArcVertex(buffer, matrix4f, x, y, depth, degree_start, degree_end, radius, getColor(), true);

            return buffer;
        };
    }
}
