package me.mizukiyuu.customsolidsky.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import me.mizukiyuu.customsolidsky.client.CustomsolidskyClient;
import me.mizukiyuu.customsolidsky.client.SkyColorSetting;
import me.mizukiyuu.customsolidsky.render.color.Color;
import me.mizukiyuu.customsolidsky.render.color.Colors;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class MutableColorArgumentType implements ArgumentType<Color> {
    private static final Collection<String> EXAMPLES = Arrays.asList("1 1 1", "64 64 64", "gray", "#66ccff");

    private final int minimum;
    private final int maximum;

    private MutableColorArgumentType(int minimum, int maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public static MutableColorArgumentType mutableColorArg(final int min, final int max) {
        return new MutableColorArgumentType(min, max);
    }

    public static Color getColor(final CommandContext<?> context, final String name) {
        return context.getArgument(name, Color.class);
    }

    @Override
    public Color parse(StringReader reader) throws CommandSyntaxException {
        String s = readUnquotedString(reader);
        int i;

        try {
            i = Integer.parseInt(s);
        } catch (NumberFormatException ignored) {
            if (s.isEmpty()) {
                Text warning = Text.translatable("customsolidsky.commands.skycolor.empty");
                throw new CommandSyntaxException(new SimpleCommandExceptionType(warning), warning);
            }

            if (s.startsWith("#") && s.length() == 7) {
                return new Color(s);
            } else if (Colors.COLORS_STRING_LIST.contains(s)) {
                return Enum.valueOf(Colors.class, s.toUpperCase()).color;
            } else {
                Text warning = Text.translatable("customsolidsky.commands.skycolor.no_match", s);
                throw new CommandSyntaxException(new SimpleCommandExceptionType(warning), warning);
            }
        }

        if (i < minimum) {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.integerTooLow().createWithContext(reader, i, minimum);
        }
        if (i > maximum) {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.integerTooHigh().createWithContext(reader, i, maximum);
        }

        return new Color(i, CustomsolidskyClient.SKY_COLOR_SETTING.skyColor.getGreen(), CustomsolidskyClient.SKY_COLOR_SETTING.skyColor.getBlue());
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        Colors.COLORS_STRING_LIST.forEach(builder::suggest);
        builder.suggest("128 128 128");
        builder.suggest("#66ccff");
        return builder.buildFuture();
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    private String readUnquotedString(StringReader reader) {
        final int start = reader.getCursor();
        while (reader.canRead() && isAllowedInUnquotedString(reader.peek())) {
            reader.skip();
        }
        return reader.getString().substring(start, reader.getCursor());
    }

    private boolean isAllowedInUnquotedString(char c) {
        return c >= '0' && c <= '9'
                || c >= 'A' && c <= 'Z'
                || c >= 'a' && c <= 'z'
                || c == '.' || c == '#';
    }
}
