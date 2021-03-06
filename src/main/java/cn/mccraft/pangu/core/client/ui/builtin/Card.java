package cn.mccraft.pangu.core.client.ui.builtin;

import cn.mccraft.pangu.core.client.ui.Button;
import cn.mccraft.pangu.core.util.font.DefaultFontProvider;
import cn.mccraft.pangu.core.util.image.TextureProvider;
import cn.mccraft.pangu.core.util.render.Rect;
import cn.mccraft.pangu.core.util.resource.PanguResLoc;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Accessors(chain = true)
public class Card extends Button {
    public static final ResourceLocation texture = PanguResLoc.ofGui("card.png");

    @Getter
    @Setter
    private String text;

    @Getter
    @Setter
    private TextureProvider icon;

    @Getter
    @Setter
    private int u, v;

    public Card() {
        this("");
    }

    public Card(String text) {
        this(text, 65, 80,  null, 0, 0);
    }

    public Card(String name, int width, int height, TextureProvider textureProvider, int u, int v) {
        super(width, height);
        this.text = name;
        this.icon = textureProvider;
        this.u = u;
        this.v = v;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onDraw(float partialTicks, int mouseX, int mouseY) {
        Rect.startDrawing();
        if (isDisabled()) GlStateManager.color(1, 1, 1, 0.5F);
        Rect.bind(texture);

        Rect.drawTextured(
                getX(), getY(),
                0, 0,
                width, height);

        if (icon != null && icon.getTexture() != null) {
            Rect.bind(icon.getTexture());

            Rect.drawTextured(
                    getX(), getY(),
                    u, v,
                    width, height);
        }
        DefaultFontProvider.INSTANCE.drawCenteredString(text, getX() + width / 2, getY() + 60, isDisabled()?0x888888:(isHovered()?0x2CC0A7:0xDDDDDD), false);
    }
}
