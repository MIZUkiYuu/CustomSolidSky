package me.mizukiyuu.customsolidsky.render.color;

import java.util.Arrays;
import java.util.List;

public enum Colors {
    TRANSPARENT (new Color(0, 0, 0, 0)),
    WHITE (new Color(255, 255, 255, 1.0f)),
    BLACK (new Color(0, 0, 0, 1.0f)),
    GRAY (new Color(64, 64, 64, 1.0f)),
    RED (new Color(255, 0, 0, 1.0f)),
    GREEN (new Color(0, 255, 0, 1.0f)),
    BLUE (new Color(0, 0, 255, 1.0f)),
    YELLOW (new Color(255, 255, 0, 1.0f)),
    MAGENTA (new Color(255, 0, 255, 1.0f)),
    CYAN (new Color(0, 255, 255, 1.0f));

    public final Color color;
    public static final List<String> COLORS_STRING_LIST = Arrays.stream(Colors.values()).map(c -> c.name().toLowerCase()).toList();

    Colors(Color color){
        this.color = color;
    }
}
