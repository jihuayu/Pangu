package cn.mccraft.pangu.core.client.ui.builtin;

import cn.mccraft.pangu.core.client.ui.Component;
import cn.mccraft.pangu.core.client.ui.Container;
import cn.mccraft.pangu.core.client.ui.Screen;
import cn.mccraft.pangu.core.util.font.DefaultFontProvider;
import lombok.Getter;
import lombok.Setter;

public class ScrollingContainer extends Scrolling {
    @Getter
    @Setter
    protected Container container;

    public ScrollingContainer(Container container, float height) {
        super(container.getWidth(), height);
        this.container = container;
    }

    @Override
    public float getContentHeight() {
        return container.getHeight();
    }

    @Override
    public void onContentPressed(int mouseButton, float mouseListX, float mouseListY) {
        container.onMousePressed(mouseButton, (int) (mouseListX), (int) (mouseListY));
    }

    @Override
    public void onContentReleased(float mouseListX, float mouseListY) {
        container.onMouseReleased((int) (mouseListX), (int) (mouseListY));
    }

    @Override
    public void onContentDraw(float ticks, float baseY, float mouseListX, float mouseListY) {
        container.setOffset(getX(), baseY);
        container.onDraw(ticks, (int) (mouseListX + getX()), (int) (baseY + mouseListY));
    }

    @Override
    public Component setScreen(Screen screen) {
        container.setScreen(screen);
        return super.setScreen(screen);
    }
}
