package me.mizukiyuu.customsolidsky.render.gui.shape;

import com.mojang.blaze3d.systems.RenderSystem;
import me.mizukiyuu.customsolidsky.render.IRenderBuffer;
import me.mizukiyuu.customsolidsky.render.color.Color;
import me.mizukiyuu.customsolidsky.util.math.MathM;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class ShapeRenderer {

    public static void buildPointVertex(BufferBuilder buffer, Matrix4f matrix4f, float x, float y, float z, Color color){
        buffer.vertex(matrix4f, x, y, z).color(color.getRed(), color.getGreen(), color.getBlue(), (int) (color.getAlpha() * 255));
    }

    public static void buildLineVertex(BufferBuilder buffer, Matrix4f matrix4f, float x1, float y1, float x2, float y2, int depth, int r, int g, int b, int a){
        buffer.vertex(matrix4f, x1, y1, depth).color(r, g, b, a);
        buffer.vertex(matrix4f, x2, y2, depth).color(r, g, b, a);
    }

    /**
     * @param degree_start  between 0 and 720, must be smaller than degree_end. 起始角：角度范围为 [0, 720), 比 终止角 小
     * @param degree_end    between 0 and 720. 终止角：角度范围为 (0, 720]
     * @param isCenterNeeded whether to render the circle center. 是否渲染圆心
     */
    public static void buildArcVertex(BufferBuilder buffer, Matrix4f matrix4f, float x, float y, int depth, int degree_start, int degree_end, float radius, Color color, boolean isCenterNeeded){
        if(isCenterNeeded){
            buildPointVertex(buffer, matrix4f, x, y, depth, color);
        }

        for (int i = degree_start; i <= degree_end; i++) {
            buildPointVertex(buffer, matrix4f, MathM.COS[i] * radius + x, y - MathM.SIN[i] * radius, depth, color);
        }
    }

    public static void render(DrawContext drawContext, IRenderBuffer IRenderBuffer){
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        BufferRenderer.drawWithGlobalProgram(IRenderBuffer.build(drawContext.getMatrices().peek().getPositionMatrix()).end());
        RenderSystem.disableBlend();
    }

    public static void renderShapeWithStencil(DrawContext drawContext, Shape<?> mainShape, Shape<?> bgShape) {
        ShapeRenderer.renderShapeWithStencil(
                drawContext,
                matrix4f -> mainShape.getRenderBuffer().build(matrix4f),
                matrix4f -> bgShape.getRenderBuffer().build(matrix4f)
        );
    }

    public static void renderShapeWithStencil(DrawContext drawContext, IRenderBuffer mainRenderBuffer, IRenderBuffer bgRenderBuffer){

        GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);

        RenderSystem.enableDepthTest();
        GL11.glDepthFunc(GL11.GL_LESS); // must be GL_LESS

        GL11.glEnable(GL11.GL_STENCIL_TEST);
        RenderSystem.stencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);
        RenderSystem.stencilFunc(GL11.GL_ALWAYS, 1, 0xFF);
        RenderSystem.stencilMask(0xFF);

        // render main render buffer
        render(drawContext, mainRenderBuffer);

        RenderSystem.stencilFunc(GL11.GL_NOTEQUAL, 1, 0xFF);
        RenderSystem.stencilMask(0x00);

        // render bg render buffer
        render(drawContext, bgRenderBuffer);

        // reset
        RenderSystem.stencilMask(0xFF);
        RenderSystem.stencilFunc(GL11.GL_ALWAYS, 0, 0xFF);
        GL11.glDisable(GL11.GL_STENCIL_TEST);
        RenderSystem.enableDepthTest();
        GL11.glDepthFunc(GL11.GL_LEQUAL); // 515, default value in minecraft
    }
}
