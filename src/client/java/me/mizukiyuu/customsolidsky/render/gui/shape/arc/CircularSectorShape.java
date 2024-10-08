package me.mizukiyuu.customsolidsky.render.gui.shape.arc;

import me.mizukiyuu.customsolidsky.render.color.Color;
import me.mizukiyuu.customsolidsky.render.gui.shape.Rect;
import me.mizukiyuu.customsolidsky.util.math.MathM;
import me.mizukiyuu.customsolidsky.util.math.Vec2f;
import me.mizukiyuu.customsolidsky.util.render.RenderUtil;
import net.minecraft.client.gui.DrawContext;
import org.joml.Vector4f;

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

    }

    @Override
    public void disableStroke() {

    }

    @Override
    public boolean inside(float x, float y) {
        return false;
    }

    @Override
    public void updateBoundingRect() {
        boundingRect.set(calculateMiniBoundingRect());
    }

    @Override
    public Consumer<DrawContext> defaultRenderConsumer() {
        return drawContext -> {
            vertexConsumer = drawContext.getVertexConsumers().getBuffer(RenderUtil.GUI_TRIANGLE_FAN);
            matrix4f = drawContext.getMatrices().peek().getPositionMatrix();

            float x = getX();
            float y = getY();
            int depth = getDepth();
            Color color = getColor();

            RenderUtil.buildArcVertex(vertexConsumer, matrix4f, x, y, depth, degree_start, degree_end, radius, color, true);
            RenderUtil.buildPointVertex(vertexConsumer, matrix4f, x, y, depth, color);
        };
    }

    private Rect calculateMiniBoundingRect(){
        List<Float> horizontal  = new ArrayList<>();
        List<Float> vertical  = new ArrayList<>();

        horizontal.add(x);
        horizontal.add(MathM.COS[degree_start] * radius + x);
        horizontal.add(MathM.COS[degree_end] * radius + x);

        vertical.add(y);
        vertical.add(MathM.SIN[degree_start] * radius + y);
        vertical.add(MathM.SIN[degree_end] * radius + y);

        int i = 3;
        for (int a = (int) (Math.ceil(degree_start * 4 / 360.0f)) * 90; a < degree_end; a += 90, i++)
        {
            horizontal.add(MathM.COS[a] * radius + x);
            vertical.add(MathM.SIN[a] * radius + y);
        }

        horizontal.sort(Comparator.naturalOrder());
        vertical.sort(Comparator.naturalOrder());

        return new Rect(horizontal.getFirst(), vertical.getFirst(), horizontal.getLast() - horizontal.getFirst(), vertical.getLast() - vertical.getFirst());
    }
}
