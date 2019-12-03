package cn.mccraft.pangu.core.client.ui;

import cn.mccraft.pangu.core.util.render.Rect;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

@Accessors(chain = true)
public abstract class Component implements Cloneable, Comparable<Component> {
    @Getter
    @Setter
    protected Component parent;

    @Getter
    @Setter
    protected Screen screen;

    @Getter
    @Setter
    protected int zLevel = 100;

    @Getter
    protected float height = 0, width = 0;

    @Setter
    private float x = 0, y = 0;

    @Getter
    @Setter
    protected boolean focused = false;

    @Getter
    @Setter
    protected boolean hovered = false, visible = true, disabled = false;

    @Setter
    @Getter
    protected List<String> toolTips;

    public Component() {
    }

    @SideOnly(Side.CLIENT)
    public void onDraw(float partialTicks, int mouseX, int mouseY) {
    }

    public void onMousePressed(int mouseButton, int mouseX, int mouseY) {
    }

    public void onMouseReleased(int mouseX, int mouseY) {
    }

    /**
     * Only if this component is focused ({@link Container#focus(Component)})
     */
    public void onKeyTyped(char typedChar, int keyCode) {
    }

    /**
     * Mouse scrolling.
     */
    public void onMouseInput(int mouseX, int mouseY) {
    }

    public void onUpdate(int mouseX, int mouseY) {
        this.hovered = isHovered(mouseX, mouseY);
    }

    public boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= this.getX()
                && mouseY >= this.getY()
                && mouseX < this.getX() + this.width
                && mouseY < this.getY() + this.height;
    }

    public float getX() {
        if (parent instanceof Container) return getNativeX() + ((Container) parent).getOffsetX();
        else if (parent instanceof TabContainer) return getNativeX() + parent.getX();
        return getNativeX();
    }

    public float getY() {
        if (parent instanceof Container) return getNativeY() + ((Container) parent).getOffsetY();
        else if (parent instanceof TabContainer) return getNativeY() + parent.getY();
        else return getNativeY();
    }

    public float getNativeX() {
        if (screen != null && screen.centerOrigin) return screen.halfWidth + x;
        return x;
    }

    public float getNativeY() {
        if (screen != null && screen.centerOrigin) return screen.halfHeight + y;
        return y;
    }

    public Component setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Component setCenteredPosition(float x, float y) {
        return setPosition(x - width / 2, y - height / 2);
    }

    public Component setSize(float width, float height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public Component getRoot() {
        if (parent == null) return this;
        else return parent.getParent();
    }

    /**
     * Draw a red frame that contains this component
     */
    @SideOnly(Side.CLIENT)
    @Deprecated
    public void drawComponentBox() {
        drawComponentBox(0xFFFF0000);
    }

    /**
     * Draw a frame that contains this component
     */
    @SideOnly(Side.CLIENT)
    public void drawComponentBox(int color) {
        Rect.drawFrameBox(getX(), getY(), getWidth(), getHeight(), 1, color);
    }

    /**
     * Draw tooltips
     */
    @SideOnly(Side.CLIENT)
    public void drawToolTips(List<String> texts, int mouseX, int mouseY) {
        if (getScreen() != null) {
            getScreen().drawHovering(this, texts, mouseX, mouseY);
        } else {
            GuiUtils.drawHoveringText(texts, mouseX, mouseY, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight, -1, Minecraft.getMinecraft().fontRenderer);
            RenderHelper.disableStandardItemLighting();
        }
    }

    @SideOnly(Side.CLIENT)
    public void playPressSound() {
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

    public Component toolTips(String... toolTips) {
        setToolTips(Arrays.asList(toolTips));
        return this;
    }

    @Override
    public int compareTo(Component o) {
        return Integer.compare(this.getZLevel(), o.getZLevel());
    }
}
