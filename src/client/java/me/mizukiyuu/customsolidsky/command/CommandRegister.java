package me.mizukiyuu.customsolidsky.command;

import com.mojang.brigadier.Command;
import me.mizukiyuu.customsolidsky.client.CustomsolidskyClient;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

import static com.mojang.brigadier.arguments.BoolArgumentType.bool;
import static com.mojang.brigadier.arguments.BoolArgumentType.getBool;
import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static me.mizukiyuu.customsolidsky.command.MutableColorArgumentType.getColor;
import static me.mizukiyuu.customsolidsky.command.MutableColorArgumentType.mutableColorArg;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class CommandRegister {
    static final String SKY_COLOR = "skyColor";

    static final String ENABLE = "enable";
    static final String DISABLE = "disable";

    static final String SET = "set";
    static final String COLOR_R_OR_STRING = "color_R_or_string";
    static final String COLOR_G = "color_G";
    static final String COLOR_B = "color_B";

    static final String FOG = "fog";

    static final String RESET = "reset";

    public static void register(){
        ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
            dispatcher.register(
                    literal(SKY_COLOR)
                            .then(literal(ENABLE)
                                    .executes(c -> {
                                        CustomsolidskyClient.SKY_OPTIONS.setEnable(true);
                                        return Command.SINGLE_SUCCESS;
                                    })
                            )
                            .then(literal(DISABLE)
                                    .executes(c -> {
                                        CustomsolidskyClient.SKY_OPTIONS.setEnable(false);
                                        return Command.SINGLE_SUCCESS;
                                    })
                            )
                            .then(literal(SET)
                                    .then(argument(COLOR_R_OR_STRING, mutableColorArg(0, 255))
                                            .executes(c -> {
                                                CustomsolidskyClient.SKY_OPTIONS.skyColor.set(getColor(c, COLOR_R_OR_STRING));
                                                return Command.SINGLE_SUCCESS;
                                            })
                                            .then(argument(COLOR_G, integer(0, 255))
                                                    .executes(c -> {
                                                        CustomsolidskyClient.SKY_OPTIONS.skyColor
                                                                .setRed(getColor(c, COLOR_R_OR_STRING).getRed())
                                                                .setGreen(getInteger(c, COLOR_G));
                                                        return Command.SINGLE_SUCCESS;
                                                    })
                                                    .then(argument(COLOR_B, integer(0, 255))
                                                            .executes(c -> {
                                                                CustomsolidskyClient.SKY_OPTIONS.skyColor.set(getColor(c, COLOR_R_OR_STRING).getRed(), getInteger(c, COLOR_G), getInteger(c, COLOR_B));
                                                                return Command.SINGLE_SUCCESS;
                                                            })
                                                    )
                                            )
                                    )
                            )
                            .then(literal(FOG)
                                    .then(argument(FOG, bool())
                                            .executes(c -> {
                                                CustomsolidskyClient.SKY_OPTIONS.canRenderFog = getBool(c, FOG);
                                                return Command.SINGLE_SUCCESS;
                                            })
                                    )
                            )
                            .then(literal(RESET)
                                    .executes((c) -> {
                                        CustomsolidskyClient.SKY_OPTIONS.resetSkyColor();
                                        return Command.SINGLE_SUCCESS;
                                    })
                            )
            );
        }));
    }
}
