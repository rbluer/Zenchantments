package zedly.zenchantments.arrows.enchanted;

import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;
import zedly.zenchantments.arrows.EnchantedArrow;

public class LevelArrow extends EnchantedArrow {
    public LevelArrow(@NotNull Arrow entity, int level, double power) {
        super(entity, level, power);
    }

    @Override
    public void onKill(@NotNull EntityDeathEvent event) {
        this.die(true);
    }

    @Override
    public void onImpact() {
        this.die(false);
    }
}