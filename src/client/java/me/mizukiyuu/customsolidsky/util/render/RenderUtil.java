package me.mizukiyuu.customsolidsky.util.render;

import com.mojang.blaze3d.systems.RenderSystem;
import me.mizukiyuu.customsolidsky.render.color.Color;
import me.mizukiyuu.customsolidsky.render.gui.shape.Rect;
import me.mizukiyuu.customsolidsky.render.gui.shape.Shape;
import me.mizukiyuu.customsolidsky.util.math.MathM;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.util.function.Consumer;

public class RenderUtil {

    public static final RenderLayer GUI_TRIANGLE_STRIP = RenderLayer.of(
            "gui_triangle_strip",
            VertexFormats.POSITION_COLOR,
            VertexFormat.DrawMode.TRIANGLE_STRIP,
            786432,
            RenderLayer.MultiPhaseParameters.builder().program(RenderPhase.GUI_PROGRAM).transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY).depthTest(RenderPhase.LEQUAL_DEPTH_TEST).build(false)
    );

    public static final RenderLayer GUI_TRIANGLE_FAN = RenderLayer.of(
            "gui_triangle_fan",
            VertexFormats.POSITION_COLOR,
            VertexFormat.DrawMode.TRIANGLE_FAN,
            786432,
            RenderLayer.MultiPhaseParameters.builder().program(RenderPhase.GUI_PROGRAM).transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY).depthTest(RenderPhase.LEQUAL_DEPTH_TEST).build(false)
    );


    public static void buildPointVertex(VertexConsumer vertexConsumer, Matrix4f matrix4f, float x, float y, float z, Color color){
        vertexConsumer.vertex(matrix4f, x, y, z).color(color.getRed(), color.getGreen(), color.getBlue(), (int) (color.getAlpha() * 255));
    }

    public static void buildLineVertex(VertexConsumer vertexConsumer, Matrix4f matrix4f, float x1, float y1, float x2, float y2, int depth, int r, int g, int b, int a){
        vertexConsumer.vertex(matrix4f, x1, y1, depth).color(r, g, b, a);
        vertexConsumer.vertex(matrix4f, x2, y2, depth).color(r, g, b, a);
    }

    /**
     * @param degree_start  between 0 and 720, must be smaller than degree_end
     * @param degree_end    between 0 and 720
     * @param isCenterNeeded Whether to render the circle center.
     */
    public static void buildArcVertex(VertexConsumer vertexConsumer, Matrix4f matrix4f, float x, float y, int depth, int degree_start, int degree_end, float radius, Color color, boolean isCenterNeeded){
        if(isCenterNeeded){
            buildPointVertex(vertexConsumer, matrix4f, x, y, depth, color);
        }

        for (int i = degree_start; i <= degree_end; i++) {
            buildPointVertex(vertexConsumer, matrix4f, MathM.COS[i] * radius + x, - MathM.SIN[i] * radius + y, depth, color);
        }
    }
    
    public static void renderShape(DrawContext drawContext, Shape<?> shape){
        shape.getRenderConsumer().accept(drawContext);
        drawContext.draw();
    }

    public static void renderShapeWithStencil(DrawContext drawContext, Shape<?> shape) {
        renderShapeWithStencil(drawContext, shape, drawContext1 -> {
            shape.enableStroke();
            shape.getRenderConsumer().accept(drawContext1);
            shape.disableStroke();
        });
    }

    public static void renderShapeWithStencil(DrawContext drawContext, Shape<?> shape, Shape<?> larger_shape) {
        renderShapeWithStencil(drawContext, shape, drawContext1 -> larger_shape.getRenderConsumer().accept(drawContext1));
    }

    public static void renderShapeWithStencil(DrawContext drawContext, Shape<?> shape, Consumer<DrawContext> larger_shape_consumer){

        GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);

        RenderSystem.enableDepthTest();
        GL11.glDepthFunc(GL11.GL_LESS); // must be GL_LESS

        GL11.glEnable(GL11.GL_STENCIL_TEST);
        RenderSystem.stencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);
        RenderSystem.stencilFunc(GL11.GL_ALWAYS, 1, 0xFF);
        RenderSystem.stencilMask(0xFF);

        // render main shape
        shape.getRenderConsumer().accept(drawContext);
        drawContext.getVertexConsumers().draw();

        RenderSystem.stencilFunc(GL11.GL_NOTEQUAL, 1, 0xFF);
        RenderSystem.stencilMask(0x00);
        RenderSystem.disableDepthTest();

        // render large shape
        larger_shape_consumer.accept(drawContext);
        drawContext.getVertexConsumers().draw();

        // reset
        RenderSystem.stencilMask(0xFF);
        RenderSystem.stencilFunc(GL11.GL_ALWAYS, 0, 0xFF);
        GL11.glDisable(GL11.GL_STENCIL_TEST);
        RenderSystem.enableDepthTest();
        GL11.glDepthFunc(GL11.GL_LEQUAL); // 515, default value in minecraft
    }

    public static void renderRectWireframe(DrawContext drawContext, Rect rect, Color color){
        VertexConsumer vertexConsumer = drawContext.getVertexConsumers().getBuffer(RenderLayer.getDebugLineStrip(1));
        Matrix4f matrix4f = drawContext.getMatrices().peek().getPositionMatrix();

        float x = rect.getX();
        float y = rect.getY();
        float width = rect.getWidth();
        float height = rect.getHeight();
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int a = color.getAlphaInt();

        vertexConsumer.vertex(matrix4f, x, y, 0).color(r, g, b, a);
        vertexConsumer.vertex(matrix4f, x, y + height, 0).color(r, g, b, a);
        vertexConsumer.vertex(matrix4f, x + width, y + height, 0).color(r, g, b, a);
        vertexConsumer.vertex(matrix4f, x + width, y, 0).color(r, g, b, a);
        vertexConsumer.vertex(matrix4f, x, y, 0).color(r, g, b, a);

        drawContext.draw();
    }
}
