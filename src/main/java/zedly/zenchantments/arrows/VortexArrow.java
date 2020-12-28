package zedly.zenchantments.arrows;

import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;
import zedly.zenchantments.ZenchantmentsPlugin;

public final class VortexArrow extends ZenchantedArrow {
    public VortexArrow(@NotNull ZenchantmentsPlugin plugin, @NotNull Arrow entity) {
        super(plugin, entity);
    }

    @Override
    public void onKill(final @NotNull EntityDeathEvent event) {
        this.die();
    }
}