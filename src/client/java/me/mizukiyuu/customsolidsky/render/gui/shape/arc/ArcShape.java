package me.mizukiyuu.customsolidsky.render.gui.shape.arc;

import me.mizukiyuu.customsolidsky.render.color.Color;
import me.mizukiyuu.customsolidsky.render.gui.shape.Shape;

import java.util.List;

public abstract class ArcShape<T extends ArcShape<T>> extends Shape<T> {
    protected float radius;
    protected int degree_start;
    protected int degree_end;

    public ArcShape(float x, float y, float radius, int degree_start, int degree_end, List<Color> colorList) {
        super(x, y, colorList);
        this.radius = radius;
        this.degree_start = degree_start;
        this.degree_end = degree_end;
    }

    public ArcShape(float x, float y, float radius, int degree_start, int degree_end, Color color) {
        super(x, y, color);
        this.radius = radius;
        this.degree_start = degree_start;
        this.degree_end = degree_end;
    }


    //region getter and setter ----------------------------------------------------------------------------------------------------

    public float getRadius() {
        return radius;
    }

    public T setRadius(float radius) {
        this.radius = radius;
        return (T) this;
    }

    public int getDegree_start() {
        return degree_start;
    }

    public T setDegree_start(int degree_start) {
         if(degree_start < degree_end){
             this.degree_start = degree_start;
         }
        return (T) this;
    }

    public int getDegree_end() {
        return degree_end;
    }

    public T setDegree_end(int degree_end) {
        if(degree_end > degree_start){
            this.degree_end = degree_end;
        }
        return (T) this;
    }

    // endregion ----------------------------------------------------------------------------------------------------


}
