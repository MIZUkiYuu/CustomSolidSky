package me.mizukiyuu.customsolidsky.render.gui.shape.circle;

import me.mizukiyuu.customsolidsky.render.IRenderBuffer;
import me.mizukiyuu.customsolidsky.render.color.Color;
import me.mizukiyuu.customsolidsky.render.gui.shape.Shape;
import me.mizukiyuu.customsolidsky.render.gui.shape.ShapeRenderer;
import net.minecraft.client.render.VertexFormat;

import java.util.Arrays;
import java.util.List;

public class CircleShape extends Shape<CircleShape> {

    protected float radius;

    public CircleShape(float x, float y, float radius, List<Color> colorList) {
        super(x, y, colorList);
        this.radius = radius;
        updateBoundingRect();
    }

    public CircleShape(float x, float y, float radius, Color color) {
        this(x, y, radius, Arrays.asList(color, color));
    }


    public float getRadius() {
        return radius;
    }

    public CircleShape setRadius(float radius) {
        this.radius = radius;
        updateBoundingRect();
        return this;
    }


    @Override
    public CircleShape clone() {
        return new CircleShape(x, y, radius, cloneColorList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isMouseInShape(float mouseX, float mouseY) {
        float subX = mouseX - x;
        float subY = mouseY - y;
        float largeR = radius + outlineSize;
        return (subX * subX + subY * subY) <= largeR * largeR;
    }

    @Override
    public void setOutline(){
        this.radius += outlineSize;
    }

    @Override
    public void resetOutline(){
        this.radius -= outlineSize;
    }

    @Override
    public void updateBoundingRect() {
        float edgeWidth = radius * 2 + outlineSize * 2;
        boundingRect.set(x - radius - outlineSize, y - radius - outlineSize, edgeWidth, edgeWidth);
    }

    @Override
    public IRenderBuffer defaultRenderBuffer() {
        return matrix4f -> {
            buffer = getBuffer(VertexFormat.DrawMode.TRIANGLE_FAN);
            ShapeRenderer.buildArcVertex(buffer, matrix4f, x, y, depth, 0, 360, radius, getColor(), false);
            return buffer;
        };
    }
}
