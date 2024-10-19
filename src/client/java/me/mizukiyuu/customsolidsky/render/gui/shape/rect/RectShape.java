package me.mizukiyuu.customsolidsky.render.gui.shape.rect;

import me.mizukiyuu.customsolidsky.render.IRenderBuffer;
import me.mizukiyuu.customsolidsky.render.color.Color;
import me.mizukiyuu.customsolidsky.render.gui.shape.Shape;
import me.mizukiyuu.customsolidsky.render.gui.shape.ShapeRenderer;
import me.mizukiyuu.customsolidsky.util.math.Vec2f;
import net.minecraft.client.render.VertexFormat;
import org.joml.Vector4f;

import java.util.Arrays;
import java.util.List;

public class RectShape extends Shape<RectShape> {

    protected float width;
    protected float height;
    protected float cornerRadius;

    public RectShape(float x, float y, float width, float height, float cornerRadius, List<Color> colorList) {
        super(x, y, colorList);
        this.width =width;
        this.height = height;
        this.cornerRadius = cornerRadius;
        updateBoundingRect();
    }

    public RectShape(float x, float y, float width, float height, float cornerRadius, Color color) {
        this(x, y, width, height, cornerRadius, Arrays.asList(color, color));
    }


    //region getter and setter ----------------------------------------------------------------------------------------------------

    public float getWidth() {
        return width;
    }

    public RectShape setWidth(float width) {
        this.width = width;
        updateBoundingRect();
        return this;
    }

    public float getHeight() {
        return height;
    }

    public RectShape setHeight(float height) {
        this.height = height;
        updateBoundingRect();
        return this;
    }

    public Vec2f getDim(){
        return new Vec2f(width, height);
    }

    public RectShape setDim(float width, float height){
        this.width = width;
        this.height = height;
        updateBoundingRect();
        return this;
    }

    public float getCornerRadius() {
        return cornerRadius;
    }

    public RectShape setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
        return this;
    }

    // endregion ----------------------------------------------------------------------------------------------------


    @Override
    public RectShape clone() {
        return new RectShape(x, y, width, height, cornerRadius, cloneColorList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isMouseInShape(float mouseX, float mouseY) {
        return getX() <= mouseX && mouseX <= getX() + width
                && getY() <= mouseY && mouseY <= getY() + height;
    }

    @Override
    protected void setOutline() {
        this.x -= outlineSize;
        this.y -= outlineSize;
        this.width += outlineSize * 2;
        this.height += outlineSize * 2;
    }

    @Override
    protected void resetOutline() {
        this.x += outlineSize;
        this.y += outlineSize;
        this.width -= outlineSize * 2;
        this.height -= outlineSize * 2;
    }

    @Override
    protected void updateBoundingRect() {
        boundingRect.set(x - outlineSize, y - outlineSize,width + outlineSize * 2, height + outlineSize * 2);
    }

    @Override
    public IRenderBuffer defaultRenderBuffer() {
        return matrix4f -> {
            if (cornerRadius == 0) {
                buffer = getBuffer(VertexFormat.DrawMode.TRIANGLE_STRIP);

                float tmp = 0;
                List<Color> colorList = getColorList();
                float interval = width / (colorList.size() - 1);

                // 横向渲染矩形, 支持多色
                // Render a rectangle horizontally, support multi colors
                for (Color color : colorList) {
                    ShapeRenderer.buildLineVertex(buffer, matrix4f, x + tmp, y, x + tmp, y + height, depth, color.getRed(), color.getGreen(), color.getBlue(), color.getAlphaInt());
                    tmp += interval;
                }
            } else {
                // 圆角矩形. 只能绘制单色
                // rounded rect. Can only be drawn in one color
                buffer = getBuffer(VertexFormat.DrawMode.TRIANGLE_FAN);

                Color color = getColor();

                ShapeRenderer.buildArcVertex(buffer, matrix4f, x + cornerRadius, y + cornerRadius, depth, 90, 180, cornerRadius, color, true);
                ShapeRenderer.buildArcVertex(buffer, matrix4f, x + cornerRadius, y + height - cornerRadius, depth, 180, 270, cornerRadius, color, false);
                ShapeRenderer.buildArcVertex(buffer, matrix4f, x + width - cornerRadius, y + height - cornerRadius, depth, 270, 360, cornerRadius, color, false);
                ShapeRenderer.buildArcVertex(buffer, matrix4f, x + width - cornerRadius, y + cornerRadius, depth, 0, 90, cornerRadius, color, false);
                ShapeRenderer.buildPointVertex(buffer, matrix4f, x + cornerRadius, y, depth, color);
            }
            return buffer;
        };
    }

    public IRenderBuffer quadColorsGradientRectRenderConsumer() {
        return matrix4f -> {
            buffer = getBuffer(VertexFormat.DrawMode.TRIANGLE_STRIP);

            List<Color> colorList = getColorList();

            Color colorTop = new Color(colorList.get(0));
            Color colorDown = new Color(colorList.get(1));

            Vector4f invColorTop = Color.subtractToVec4f(colorList.get(3), colorTop).div(width);
            Vector4f invColorDown = Color.subtractToVec4f(colorDown, colorList.get(2)).div(width);

            float pos_x;
            for (int i = 0; i <= width; i++) {
                pos_x = x + i;
                buffer.vertex(matrix4f, pos_x, y, depth).color((int) (colorTop.getRed() + i * invColorTop.x), (int) (colorTop.getGreen() + i * invColorTop.y), (int) (colorTop.getBlue() + i * invColorTop.z), (int) (colorTop.getAlpha() + i * invColorTop.w) * 255);
                buffer.vertex(matrix4f, pos_x, y + height, depth).color((int) (colorDown.getRed() + i * invColorDown.x), (int) (colorDown.getGreen() + i * invColorDown.y), (int) (colorDown.getBlue() + i * invColorDown.z), (int) (colorDown.getAlpha() + i * invColorDown.w) * 255);
            }
            return buffer;
        };
    }
}
