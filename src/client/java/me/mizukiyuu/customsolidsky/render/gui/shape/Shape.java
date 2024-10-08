package me.mizukiyuu.customsolidsky.render.gui.shape;

import me.mizukiyuu.customsolidsky.render.color.Color;
import me.mizukiyuu.customsolidsky.util.math.Vec2f;
import me.mizukiyuu.customsolidsky.util.render.RenderUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import org.joml.Matrix4f;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public abstract class Shape<T extends  Shape<T>> {

    private List<Color> colorList;

    protected float x;
    protected float y;

    protected int depth;
    protected Rect boundingRect = new Rect(0, 0, 0, 0);

    // stroke
    protected float strokeSize;
    protected Color strokeColor;
    protected Color previousColor;

    // specify rendering process
    protected Consumer<DrawContext> renderConsumer;

    // cache variables
    protected Matrix4f matrix4f;
    protected VertexConsumer vertexConsumer;


    public Shape(float x, float y, List<Color> colorList){
        this.x = x;
        this.y = y;
        this.colorList = colorList;
        this.renderConsumer = defaultRenderConsumer();
        updateBoundingRect();
    }

    public Shape(float x, float y, Color color) {
        this(x, y, Arrays.asList(color, color));
    }


    //region getter and setter ----------------------------------------------------------------------------------------------------

    public float getX() {
        return x;
    }

    public T setX(float x) {
        this.x = x;
        updateBoundingRect();
        return (T) this;
    }

    public float getY() {
        return y;
    }

    public T setY(float y) {
        this.y = y;
        updateBoundingRect();
        return (T) this;
    }

    public Vec2f getPos(){
        return new Vec2f(x, y);
    }

    public T setPos(float x, float y){
        this.x = x;
        this.y = y;
        updateBoundingRect();
        return (T) this;
    }

    public Color getColor(){
        return colorList.getFirst();
    }

    public T setColor(Color color) {
        colorList.set(0, color);
        return (T) this;
    }

    public List<Color> getColorList() {
        return colorList;
    }

    public T setColorList(List<Color> colorList) {
        this.colorList = colorList;
        return (T) this;
    }

    public int getDepth() {
        return depth;
    }

    public T setDepth(int depth) {
        this.depth = depth;
        return (T) this;
    }

    public Rect getBoundingRect() {
        return boundingRect;
    }

    public float getStrokeSize() {
        return strokeSize;
    }

    public T setStrokeSize(float strokeSize) {
        this.strokeSize = strokeSize;
        return (T) this;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public T setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
        return (T) this;
    }

    public Consumer<DrawContext> getRenderConsumer() {
        return renderConsumer;
    }

    public T setRenderConsumer(Consumer<DrawContext> renderConsumer) {
        this.renderConsumer = renderConsumer;
        return (T) this;
    }

    // endregion ----------------------------------------------------------------------------------------------------


    public abstract T clone();
    public abstract void enableStroke();
    public abstract void disableStroke();
    public abstract boolean inside(float x, float y);
    public abstract void updateBoundingRect();
    public abstract Consumer<DrawContext> defaultRenderConsumer();


    /**
     * *** This method must be called to enable stroke. ***
     * @param size the size of the stroke
     * @param color the color of the stroke
     */
    public T stroke(float size, Color color){
        setStrokeSize(size);
        setStrokeColor(color);
        return (T) this;
    }

    public void render(DrawContext drawContext){
        if(strokeSize == 0){
            RenderUtil.renderShape(drawContext, this);
        }else {
            RenderUtil.renderShapeWithStencil(drawContext, this);
        }
    }

    public void renderBoundingRect(DrawContext drawContext, Color color){
        vertexConsumer = drawContext.getVertexConsumers().getBuffer(RenderLayer.getDebugLineStrip(2));
        matrix4f = drawContext.getMatrices().peek().getPositionMatrix();

        float x = boundingRect.x;
        float y = boundingRect.y;
        float width = boundingRect.width;
        float height = boundingRect.height;
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int a = color.getAlphaInt();

        vertexConsumer.vertex(matrix4f, x, y, 0).color(r, g, b, a);
        vertexConsumer.vertex(matrix4f, x, y + height, 0).color(r, g, b, a);
        vertexConsumer.vertex(matrix4f, x + width, y + height, 0).color(r, g, b, a);
        vertexConsumer.vertex(matrix4f, x + width, y, 0).color(r, g, b, a);

        drawContext.draw();
    }

    public List<Color> cloneColorList(){
        return Color.cloneColorListOf(colorList);
    }
}

