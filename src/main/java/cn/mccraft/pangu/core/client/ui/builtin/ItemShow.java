package cn.mccraft.pangu.core.client.ui.builtin;

import cn.mccraft.pangu.core.client.ui.Component;
import cn.mccraft.pangu.core.util.ToolTips;
import cn.mccraft.pangu.core.util.render.RenderUtils;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;

public abstract class ItemShow extends Component {
    public ItemShow() {
        width = 16;
        height = 16;
    }

    @Override
    public Component setSize(float width, float height) {
        throw new UnsupportedOperationException("Customizable Width & Height is not available for now!");
    }

    @Override
    public void onDraw(float partialTicks, int mouseX, int mouseY) {
        ItemStack item = getItem();
        if (item == null || item.isEmpty()) return;
        onItemDraw(item);
    }

    public void onItemDraw(ItemStack item) {
        RenderUtils.renderItemIntoGUI(item, (int) getX(), (int) getY(), null);
    }

    @Override
    public List<String> getToolTips() {
        ItemStack item = getItem();
        if (item == null || item.isEmpty()) return super.getToolTips();
        return ToolTips.get(item);
    }

    @Nullable
    public abstract ItemStack getItem();

    public static ItemShow of(final ItemStack itemStack) {
        return new ItemShow() {
            @Nullable
            @Override
            public ItemStack getItem() {
                return itemStack;
            }
        };
    }
}
