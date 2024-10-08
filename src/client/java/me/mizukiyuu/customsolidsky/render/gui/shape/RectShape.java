package me.mizukiyuu.customsolidsky.render.gui.shape;

import me.mizukiyuu.customsolidsky.render.color.Color;
import me.mizukiyuu.customsolidsky.util.math.Vec2f;
import me.mizukiyuu.customsolidsky.util.render.RenderUtil;
import net.minecraft.client.gui.DrawContext;
import org.joml.Vector4f;

import java.util.List;
import java.util.function.Consumer;

public class RectShape extends Shape<RectShape> {

    protected float width;
    protected float height;
    protected float cornerRadius;


    public RectShape(float x, float y, float width, float height, float cornerRadius, List<Color> colorList) {
        super(x, y, colorList);
        this.width = width;
        this.height = height;
        this.cornerRadius = cornerRadius;
    }

    public RectShape(float x, float y, float width, float height, float cornerRadius, Color color) {
        super(x, y, color);
        this.width = width;
        this.height = height;
        this.cornerRadius = cornerRadius;
    }


    //region getter and setter ----------------------------------------------------------------------------------------------------

    public float getWidth() {
        return width;
    }

    public RectShape setWidth(float width) {
        this.width = width;
        return this;
    }

    public float getHeight() {
        return height;
    }

    public RectShape setHeight(float height) {
        this.height = height;
        return this;
    }

    public Vec2f getDim(){
        return new Vec2f(x, y);
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

    @Override
    public void enableStroke() {
        setPos(x - strokeSize, y - strokeSize).setDim(width + strokeSize * 2, height + strokeSize * 2);
        previousColor = getColor();
        setColor(strokeColor);
    }

    @Override
    public void disableStroke() {
        setPos(x + strokeSize, y + strokeSize).setDim(width - strokeSize * 2, height - strokeSize * 2);
        setColor(previousColor);
    }

    @Override
    public boolean inside(float x, float y) {
        return getX() <= x && x <= getX() + width
                && getY() <= y && y <= getY() + height;
    }

    @Override
    public void updateBoundingRect() {
        boundingRect.set(x, y, width, height);
    }

    @Override
    public Consumer<DrawContext> defaultRenderConsumer() {
        return drawContext -> {
            matrix4f = drawContext.getMatrices().peek().getPositionMatrix();

            int depth = getDepth();
            float x = getX();
            float y = getY();
            float width = getWidth();
            float height = getHeight();
            float cornerRadius = getCornerRadius();

            if (cornerRadius == 0) {
                vertexConsumer = drawContext.getVertexConsumers().getBuffer(RenderUtil.GUI_TRIANGLE_STRIP);

                float tmp = 0;
                List<Color> colorList = getColorList();
                float interval = width / (colorList.size() - 1);

                for (Color color : colorList) {
                    RenderUtil.buildLineVertex(vertexConsumer, matrix4f, x + tmp, y, x + tmp, y + height, depth, color.getRed(), color.getGreen(), color.getBlue(), color.getAlphaInt());
                    tmp += interval;
                }
            } else {
                // rounded corner shape
                vertexConsumer = drawContext.getVertexConsumers().getBuffer(RenderUtil.GUI_TRIANGLE_FAN);

                Color color = getColor();

                RenderUtil.buildArcVertex(vertexConsumer, matrix4f, x + cornerRadius, y + cornerRadius, depth, 90, 180, cornerRadius, color, true);
                RenderUtil.buildArcVertex(vertexConsumer, matrix4f, x + cornerRadius, y + height - cornerRadius, depth, 180, 270, cornerRadius, color, false);
                RenderUtil.buildArcVertex(vertexConsumer, matrix4f, x + width - cornerRadius, y + height - cornerRadius, depth, 270, 360, cornerRadius, color, false);
                RenderUtil.buildArcVertex(vertexConsumer, matrix4f, x + width - cornerRadius, y + cornerRadius, depth, 0, 90, cornerRadius, color, false);
                RenderUtil.buildPointVertex(vertexConsumer, matrix4f, x + cornerRadius, y, depth, color);
            }
        };
    }

    public Consumer<DrawContext> quadColorsGradientRectRenderConsumer() {
        return drawContext -> {
            vertexConsumer = drawContext.getVertexConsumers().getBuffer(RenderUtil.GUI_TRIANGLE_STRIP);
            matrix4f = drawContext.getMatrices().peek().getPositionMatrix();

            float x = getX();
            float y = getY();
            float width = getWidth();
            float height = getHeight();
            int depth = getDepth();
            List<Color> colorList = getColorList();

            Color colorTop = new Color(colorList.get(0));
            Color colorDown = new Color(colorList.get(1));

            Vector4f invColorTop = Color.subtractToVec4f(colorList.get(3), colorTop).div(width);
            Vector4f invColorDown = Color.subtractToVec4f(colorDown, colorList.get(2)).div(width);

            float pos_x;
            for (int i = 0; i <= width; i++) {
                pos_x = x + i;
                vertexConsumer.vertex(matrix4f, pos_x, y, depth).color((int) (colorTop.getRed() + i * invColorTop.x), (int) (colorTop.getGreen() + i * invColorTop.y), (int) (colorTop.getBlue() + i * invColorTop.z), (int) (colorTop.getAlpha() + i * invColorTop.w) * 255);
                vertexConsumer.vertex(matrix4f, pos_x, y + height, depth).color((int) (colorDown.getRed() + i * invColorDown.x), (int) (colorDown.getGreen() + i * invColorDown.y), (int) (colorDown.getBlue() + i * invColorDown.z), (int) (colorDown.getAlpha() + i * invColorDown.w) * 255);
            }
        };
    }
}
