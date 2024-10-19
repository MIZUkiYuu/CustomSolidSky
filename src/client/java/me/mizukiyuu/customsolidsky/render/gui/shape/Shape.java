package me.mizukiyuu.customsolidsky.render.gui.shape;

import me.mizukiyuu.customsolidsky.render.IRenderBuffer;
import me.mizukiyuu.customsolidsky.render.color.Color;
import me.mizukiyuu.customsolidsky.render.gui.shape.rect.Rect;
import me.mizukiyuu.customsolidsky.util.math.Vec2f;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;

import java.util.List;

public abstract class Shape<T extends  Shape<T>> {

    protected float x;
    protected float y;
    private List<Color> colorList;

    protected int depth;
    protected Rect boundingRect = new Rect(0, 0, 0, 0);

    // outline
    protected float outlineSize;
    protected Color outlineColor;

    // specify rendering process
    protected IRenderBuffer renderBuffer;

    // cache variables
    protected Color previousColor;
    protected Tessellator tessellator = Tessellator.getInstance();
    protected BufferBuilder buffer;


    public Shape(float x, float y, List<Color> colorList){
        this.x = x;
        this.y = y;
        this.colorList = colorList;
        this.renderBuffer = defaultRenderBuffer();
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

    public float getOutlineSize() {
        return outlineSize;
    }

    public T setOutlineSize(float outlineSize) {
        this.outlineSize = outlineSize;
        updateBoundingRect();
        return (T) this;
    }

    public Color getOutlineColor() {
        return outlineColor;
    }

    public T setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
        return (T) this;
    }

    public IRenderBuffer getRenderBuffer() {
        return renderBuffer;
    }

    public T setRenderBuffer(IRenderBuffer IRenderBuffer) {
        this.renderBuffer = IRenderBuffer;
        return (T) this;
    }

    public BufferBuilder getBuffer(VertexFormat.DrawMode drawMode){
        return tessellator.begin(drawMode, VertexFormats.POSITION_COLOR);
    }

    // endregion ----------------------------------------------------------------------------------------------------


    public abstract T clone();

    /**
     * 检查鼠标指针是否在图形内部 </br>
     * Check if the mouse pointer is inside the shape
     * @param mouseX the X coordinate of the mouse pointer. 鼠标指针的 X 坐标
     * @param mouseY the Y coordinate of the mouse pointer. 鼠标指针的 Y 坐标
     */
    public abstract boolean isMouseInShape(float mouseX, float mouseY);

    protected abstract void setOutline();
    protected abstract void resetOutline();
    protected abstract void updateBoundingRect();

    public abstract IRenderBuffer defaultRenderBuffer();

    public List<Color> cloneColorList(){
        return Color.cloneColorListOf(colorList);
    }

    /**
     * 检查鼠标指针是否在矩形包裹框内部 </br>
     * Check if the mouse pointer is inside the bounding rect of the shape
     * @param mouseX the X coordinate of the mouse pointer. 鼠标指针的 X 坐标
     * @param mouseY the Y coordinate of the mouse pointer. 鼠标指针的 Y 坐标
     */
    public boolean isMouseInBoundingRect(float mouseX, float mouseY){
        return boundingRect.isIn(mouseX, mouseY);
    }

    /**
     * 调用此方法以渲染描边 </br>
     * This method must be called to render outline
     * @param size the size of the outline. 描边大小
     * @param color the color of the outline. 描边颜色
     */
    public T outline(float size, Color color){
        setOutlineSize(size);
        setOutlineColor(color);
        return (T) this;
    }

    public void enableOutline(){
        setOutline();
        previousColor = getColor();
        setColor(outlineColor);
    }

    public void disableOutline(){
        resetOutline();
        setColor(previousColor);
    }

    public void render(DrawContext drawContext){
        if(outlineSize == 0){
            ShapeRenderer.render(drawContext, renderBuffer);
        }else {
            ShapeRenderer.renderShapeWithStencil(
                    drawContext,
                    renderBuffer,
                    matrix4f -> {
                        enableOutline();
                        renderBuffer.build(matrix4f);
                        disableOutline();
                        return buffer;
                    }
            );
        }
    }

    public void renderBoundingRect(DrawContext drawContext, Color color){
        boundingRect.renderRectWireframe(drawContext, color);
    }
}

