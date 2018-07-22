package cn.mccraft.pangu.core.client.gui;

import net.minecraft.util.ResourceLocation;

public enum Icons {
    ;
    private final ResourceLocation texture;
    private final int offsetX, offsetY, size;

    Icons(ResourceLocation texture, int offsetX, int offsetY, int size) {
        this.texture = texture;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.size = size;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public int getSize() {
        return size;
    }
}
