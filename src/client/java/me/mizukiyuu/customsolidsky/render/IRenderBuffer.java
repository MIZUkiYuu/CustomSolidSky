package me.mizukiyuu.customsolidsky.render;

import net.minecraft.client.render.BufferBuilder;
import org.joml.Matrix4f;

@FunctionalInterface
public interface IRenderBuffer {
    BufferBuilder build(Matrix4f matrix4f);
}
