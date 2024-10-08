package me.mizukiyuu.customsolidsky.util.math;

import java.text.MessageFormat;

public class Vec2f {
    public static final Vec2f ZERO = new Vec2f(0.0f, 0.0f);
    public static final Vec2f ONE = new Vec2f(1.0f, 1.0f);
    public static final Vec2f ZERO_ONE = new Vec2f(0.0f, 1.0f);
    public static final Vec2f ONE_ZERO = new Vec2f(1.0f, 0.0f);

    public float x;
    public float y;

    public Vec2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vec2f(Vec2f v) {
        this.x = v.x;
        this.y = v.y;
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vec2f v) {
        this.x = v.x;
        this.y = v.y;
    }

    public void add(float a, float b){
        this.x += a;
        this.y += b;
    }

    public Vec2f add(Vec2f v){
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    public Vec2f multiply(float f){
        return new Vec2f(this.x * f, this.y * f);
    }

    public Vec2f multiply(Vec2f v){
        return new Vec2f(this.x * v.x, this.y * v.y);
    }

    public Vec2f div(float f){
        float d = 1 / f;
        return new Vec2f(this.x * f, this.y * f);
    }

    public float dotProduct(Vec2f v){
        return this.x * v.x + this.y + v.y;
    }

    public static float sqrDistance(Vec2f v1, Vec2f v2){
        float a = v2.x - v1.x;
        float b = v2.y - v1.y;
        return a * a + b * b;
    }

    public static float fastInverseSqrtDistance(Vec2f v1, Vec2f v2){
        float d = sqrDistance(v1, v2);
        float f = 0.5f * d;
        int i = Float.floatToIntBits(d);
        i = 1597463007 - (i >> 1);
        d = Float.intBitsToFloat(i);
        d *= 1.5f - f * d * d;
        return d;
    }

    public boolean equals(Vec2f other) {
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public String toString() {
        return MessageFormat.format("({0}, {1})", this.x, this.y);
    }

    public String toSimpleString() {
        return MessageFormat.format("{0},{1}", this.x, this.y);
    }
}
