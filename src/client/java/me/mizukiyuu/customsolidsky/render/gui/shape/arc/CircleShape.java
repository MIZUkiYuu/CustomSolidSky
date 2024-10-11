package me.mizukiyuu.customsolidsky.render.gui.shape.arc;

import me.mizukiyuu.customsolidsky.render.color.Color;
import me.mizukiyuu.customsolidsky.render.gui.shape.Shape;
import me.mizukiyuu.customsolidsky.util.render.RenderUtil;
import net.minecraft.client.gui.DrawContext;

import java.util.List;
import java.util.function.Consumer;

public class CircleShape extends Shape<CircleShape> {

    protected float radius;

    protected float previousRadius;


    public CircleShape(float x, float y, float radius, List<Color> colorList) {
        super(x, y, colorList);
        this.radius = radius;
    }

    public CircleShape(float x, float y, float radius, Color color) {
        super(x, y, color);
        this.radius = radius;
    }


    public float getRadius() {
        return radius;
    }

    public CircleShape setRadius(float radius) {
        this.radius = radius;
        return this;
    }


    @Override
    public CircleShape clone() {
        return new CircleShape(x, y, radius, cloneColorList());
    }

    @Override
    public void enableStroke(){
        previousRadius = radius;
        previousColor = getColor();

        setRadius(radius + strokeSize).setColor(strokeColor);
    }

    @Override
    public void disableStroke(){
        setRadius(previousRadius).setColor(previousColor);
    }

    float subX, subY;
    @Override
    public boolean inside(float x, float y) {
        subX = x - getX();
        subY = y - getY();
        return (subX * subX + subY * subY) <= radius * radius;
    }

    @Override
    public void updateBoundingRect() {
        boundingRect.set(x - radius - strokeSize, y - radius - strokeSize, radius * 2 + strokeSize * 2, radius * 2 + strokeSize * 2);
    }

    @Override
    public Consumer<DrawContext> defaultRenderConsumer() {
        return drawContext -> {
            vertexConsumer = drawContext.getVertexConsumers().getBuffer(RenderUtil.GUI_TRIANGLE_FAN);
            matrix4f = drawContext.getMatrices().peek().getPositionMatrix();

            RenderUtil.buildArcVertex(vertexConsumer, matrix4f, x, y, depth, 0, 360, radius, getColor(), false);
        };
    }
}
