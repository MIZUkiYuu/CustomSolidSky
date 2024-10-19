package me.mizukiyuu.customsolidsky.render.gui.shape.rect;

import me.mizukiyuu.customsolidsky.render.color.Color;
import me.mizukiyuu.customsolidsky.render.gui.shape.ShapeRenderer;
import me.mizukiyuu.customsolidsky.util.math.MathM;
import me.mizukiyuu.customsolidsky.util.math.Vec2f;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;

public class Rect {
    protected float x;
    protected float y;
    protected float width;
    protected float height;

    /**
     * <blockquote><pre>
     * points[0] -> upper left      0 —————— 3
     * points[1] -> lower left      │        │
     * points[2] -> lower right     │        │
     * points[3] -> upper right     1 —————— 2
     * </pre></blockquote>
     */
    protected final Vec2f[] points = new Vec2f[]{new Vec2f(Vec2f.ZERO), new Vec2f(Vec2f.ZERO), new Vec2f(Vec2f.ZERO), new Vec2f(Vec2f.ZERO)};


    public Rect(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setPoints();
    }

    // region default getter and setter ----------------------------------------------------------------------------------------------------

    public Rect set(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setPoints();
        return this;
    }

    public Rect set(Rect rect){
        return set(rect.x, rect.y, rect.width, rect.height);
    }

    public float getX() {
        return x;
    }

    public Rect setX(float x) {
        this.x = x;
        setPoints();
        return  this;
    }

    public float getY() {
        return y;
    }

    public Rect setY(float y) {
        this.y = y;
        setPoints();
        return this;
    }

    public float getWidth() {
        return width;
    }

    public Rect setWidth(float width) {
        this.width = width;
        setPoints();
        return this;
    }

    public float getHeight() {
        return height;
    }

    public Rect setHeight(float height) {
        this.height = height;
        setPoints();
        return this;
    }

    public Vec2f[] getPoints() {
        return points;
    }

    public Rect setPoints() {
        points[0].set(x, y);
        points[1].set(x, y + height);
        points[2].set(x + width, y + height);
        points[3].set(x + width, y);
        return this;
    }

    // endregion ----------------------------------------------------------------------------------------------------

    public Vec2f getPos(){
        return new Vec2f(x, y);
    }

    public Rect setPos(float x, float y){
        this.x = x;
        this.y = y;
        setPoints();
        return this;
    }

    public Vec2f getDim(){
        return new Vec2f(width, height);
    }

    public Rect setDim(float width, float height){
        this.width = width;
        this.height = height;
        setPoints();
        return this;
    }

    public Vec2f getCenterPos(){
        return new Vec2f(x + width / 2, y + height / 2);
    }

    public void swapPos(){
        MathM.swap(x, y);
        setPoints();
    }

    public void swapDimension(){
        MathM.swap(width, height);
        setPoints();
    }

    public boolean isIn(float pointX, float pointY){
        return points[0].x <= pointX && pointX <= points[2].x
                && points[0].y <= pointY && pointY <= points[2].y;
    }

    public void renderRectWireframe(DrawContext drawContext, Color color){
        ShapeRenderer.render(
                drawContext,
                matrix4f -> {
                    BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);

                    int r = color.getRed();
                    int g = color.getGreen();
                    int b = color.getBlue();
                    int a = color.getAlphaInt();

                    buffer.vertex(matrix4f, x, y, 0).color(r, g, b, a);
                    buffer.vertex(matrix4f, x, y + height, 0).color(r, g, b, a);
                    buffer.vertex(matrix4f, x + width, y + height, 0).color(r, g, b, a);
                    buffer.vertex(matrix4f, x + width, y, 0).color(r, g, b, a);
                    buffer.vertex(matrix4f, x, y, 0).color(r, g, b, a);

                    return buffer;
                });
    }
}
