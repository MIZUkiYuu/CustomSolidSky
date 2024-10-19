package me.mizukiyuu.customsolidsky.render.color;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import org.joml.Vector4f;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Color implements Cloneable{

    int red;
    int green;
    int blue;
    float alpha;

    public Color(int r, int g, int b, float a) {
        this.red = r;
        this.green = g;
        this.blue = b;
        this.alpha = a;
    }

    public Color(int r, int g, int b){
        this(r, g, b, 1.0f);
    }

    public Color(Color color){
        this.red = color.red;
        this.green = color.green;
        this.blue = color.blue;
        this.alpha = color.alpha;
    }

    public Color(String hex){
        setColorInHEX(hex);
    }

    public static Color of(Color color){
        return new Color(color);
    }

    public static Color of(Colors colors){
        return new Color(colors.color);
    }

    public static Color of(int r, int g, int b, float a){
        return new Color(r, g, b, a);
    }

    public static Color of(int r, int g, int b){
        return new Color(r, g, b, 1.0f);
    }


    //region getter and setter ----------------------------------------------------------------------------------------------------

    public void set(Color color){
        this.red = color.red;
        this.green = color.green;
        this.blue = color.blue;
        this.alpha = color.alpha;
    }

    public void set(int r, int g, int b, float a) {
        this.red = r;
        this.green = g;
        this.blue = b;
        this.alpha = a;
    }

    public void set(int r, int g, int b) {
        set(r, g, b, 1.0f);
    }


    public void setColorInHEX(String hex) {
        int i = Integer.decode(hex);
        set((i >> 16) & 0xFF, (i >> 8) & 0xFF, i & 0xFF, 1.0f);
    }

    public int getRed() {
        return red;
    }

    public Color setRed(int red) {
        this.red = red;
        return this;
    }

    public int getGreen() {
        return green;
    }

    public Color setGreen(int green) {
        this.green = green;
        return this;
    }

    public int getBlue() {
        return blue;
    }

    public Color setBlue(int blue) {
        this.blue = blue;
        return this;
    }

    public float getAlpha() {
        return alpha;
    }

    public int getAlphaInt(){
        return (int) (alpha * 255);
    }

    public Color setAlpha(float alpha) {
        this.alpha = alpha;
        return this;
    }

    // endregion ----------------------------------------------------------------------------------------------------


    public static List<Color> cloneColorListOf(List<Color> colorList){
        return colorList.stream().map(Color::clone).collect(Collectors.toList());
    }

    public Color add(int r, int g, int b, int a){
        this.red += r;
        this.green += g;
        this.blue += b;
        this.alpha += a;
        return this;
    }

    public static Color addToNew(Color color, int r, int g, int b, int a){
        return new Color(color.getRed() + r, color.getGreen() + b, color.getBlue() + b, color.getAlpha() + a);
    }

    public static Vec3i subtractToVec3i(Color c1, Color c2) {
        return new Vec3i(c1.getRed() - c2.getRed(), c1.getGreen() - c2.getGreen(), c1.getBlue() - c2.getBlue());
    }

    public static Vector4f subtractToVec4f(Color c1, Color c2) {
        return new Vector4f(c1.getRed() - c2.getRed(), c1.getGreen() - c2.getGreen(), c1.getBlue() - c2.getBlue(), c1.getAlpha() - c2.getAlpha());
    }

    public static Vec3d subtractToVec3d(Color c1, Color c2) {
        return new Vec3d(c1.getRed() - c2.getRed(), c1.getGreen() - c2.getGreen(), c1.getBlue() - c2.getBlue());
    }

    public static Vec3d divideToVec3d(Color c, int i) {
        return new Vec3d((double) c.getRed() / i, (double) c.getGreen() / i, (double) c.getBlue() / i);
    }

    public static int maxDifference(Color c1, Color c2) {
        return Math.max(Math.max(Math.abs(c1.getRed() - c2.getRed()), Math.abs(c1.getGreen() - c2.getGreen())), Math.abs(c1.getBlue() - c2.getBlue()));
    }

    @Override
    public Color clone() {
        try {
            return (Color) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
