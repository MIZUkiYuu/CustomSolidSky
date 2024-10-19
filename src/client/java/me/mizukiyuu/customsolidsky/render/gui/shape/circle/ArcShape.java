package me.mizukiyuu.customsolidsky.render.gui.shape.circle;

import me.mizukiyuu.customsolidsky.render.color.Color;
import me.mizukiyuu.customsolidsky.render.gui.shape.Shape;

import java.util.Arrays;
import java.util.List;

public abstract class ArcShape<T extends ArcShape<T>> extends Shape<T> {

    protected float radius;

    /**
     * between 0 and 720, must be smaller than degree_end. 起始角：角度范围为 [0, 720), 比 终止角 小
     */
    protected int degree_start;

    /**
     * between 0 and 720. 终止角：角度范围为 (0, 720]
     */
    protected int degree_end;


    public ArcShape(float x, float y, float radius, int degree_start, int degree_end, List<Color> colorList) {
        super(x, y, colorList);
        this.radius = radius;
        this.degree_start = degree_start;
        this.degree_end = degree_end;
        updateBoundingRect();
    }

    public ArcShape(float x, float y, float radius, int degree_start, int degree_end, Color color) {
        this(x, y, radius, degree_start, degree_end, Arrays.asList(color, color));
    }


    //region getter and setter ----------------------------------------------------------------------------------------------------

    public float getRadius() {
        return radius;
    }

    public T setRadius(float radius) {
        this.radius = radius;
        updateBoundingRect();
        return (T) this;
    }

    public int getDegree_start() {
        return degree_start;
    }

    public T setDegree_start(int degree_start) {
         if(degree_start < degree_end){
             this.degree_start = degree_start;
             updateBoundingRect();
         }
        return (T) this;
    }

    public int getDegree_end() {
        return degree_end;
    }

    public T setDegree_end(int degree_end) {
        if(degree_end > degree_start){
            this.degree_end = degree_end;
            updateBoundingRect();
        }
        return (T) this;
    }

    // endregion ----------------------------------------------------------------------------------------------------


}
