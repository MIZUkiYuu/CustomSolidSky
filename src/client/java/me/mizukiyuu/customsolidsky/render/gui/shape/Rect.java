package me.mizukiyuu.customsolidsky.render.gui.shape;

import me.mizukiyuu.customsolidsky.util.math.MathM;
import me.mizukiyuu.customsolidsky.util.math.Vec2f;

public class Rect {
    protected float x;
    protected float y;
    protected float width;
    protected float height;

    public Rect(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    //region default getter and setter

    public Rect set(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
        return  this;
    }

    public float getY() {
        return y;
    }

    public Rect setY(float y) {
        this.y = y;
        return  this;
    }

    public float getWidth() {
        return width;
    }

    public Rect setWidth(float width) {
        this.width = width;
        return this;
    }

    public float getHeight() {
        return height;
    }

    public Rect setHeight(float height) {
        this.height = height;
        return this;
    }

    //endregion

    public Vec2f getPos(){
        return new Vec2f(x, y);
    }

    public Rect setPos(float x, float y){
        this.x = x;
        this.y = y;
        return this;
    }

    public Vec2f getDimension(){
        return new Vec2f(width, height);
    }

    public Rect setDim(float width, float height){
        this.width = width;
        this.height = height;
        return  this;
    }

    public Vec2f getCenterPos(){
        return new Vec2f(x + width / 2, y + height / 2);
    }

    public void swapPos(){
        MathM.swap(x, y);
    }

    public void swapDimension(){
        MathM.swap(width, height);
    }


}
