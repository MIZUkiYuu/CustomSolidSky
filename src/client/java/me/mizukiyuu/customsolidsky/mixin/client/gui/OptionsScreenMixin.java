package me.mizukiyuu.customsolidsky.mixin.client.gui;

import me.mizukiyuu.customsolidsky.client.CustomsolidskyClient;
import me.mizukiyuu.customsolidsky.render.screen.CustomSolidSkyOptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin extends Screen {

    @Unique
    private static final Text SCREEN_TITLE_TEXT = Text.translatable("customsolidsky.options.screen.title");

    @Unique
    private static final Tooltip TITLE_TOOLTIP = Tooltip.of(SCREEN_TITLE_TEXT);

    @Unique
    private static final Identifier BTN_SKY_COLOR_ICON = Identifier.of(CustomsolidskyClient.MOD_ID, "icon/sky_color_button");

    @Unique
    TextIconButtonWidget customSolidSkyOptionButton =
            TextIconButtonWidget.builder(
                            SCREEN_TITLE_TEXT,
                            button -> {
                                CustomsolidskyClient.SKY_OPTIONS.hideHudAndPlayer = true;
                                this.client.setScreen(new CustomSolidSkyOptionsScreen(SCREEN_TITLE_TEXT, this));
                            },
                            true
                    )
                    .width(20)
                    .texture(BTN_SKY_COLOR_ICON, 20, 20)
                    .build();

    protected OptionsScreenMixin(Text title) {
        super(title);
    }

    @Inject(
            method = "init",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/option/OptionsScreen;initTabNavigation()V")
    )
    private void addSkyColorOptionButton(CallbackInfo ci){
        customSolidSkyOptionButton.setTooltip(TITLE_TOOLTIP);
        addDrawableChild(customSolidSkyOptionButton);
    }

    @Inject(
            method = "initTabNavigation",
            at = @At("TAIL")
    )
    private void initSkyColorOptionButtonPos(CallbackInfo ci){
        customSolidSkyOptionButton.setPosition(this.width / 2 - 180, 115);
    }
}
