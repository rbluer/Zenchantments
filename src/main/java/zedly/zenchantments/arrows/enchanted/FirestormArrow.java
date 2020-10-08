package zedly.zenchantments.arrows.enchanted;

import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import zedly.zenchantments.Storage;
import zedly.zenchantments.Utilities;
import zedly.zenchantments.arrows.EnchantedArrow;

public class FirestormArrow extends EnchantedArrow {

    public FirestormArrow(Arrow entity, int level, double power) {
        super(entity, level, power);
    }

    public void onImpact() {
        Utilities.displayParticle(
            Utilities.getCenter(this.getArrow().getLocation()),
            Particle.FLAME,
            100 * this.getLevel(),
            0.1f,
            this.getLevel(),
            1.5f,
            this.getLevel()
        );

        double radius = 1 + this.getLevel() * this.getPower();
        for (Entity entity : this.getArrow().getNearbyEntities(radius, radius, radius)) {
            if (!(entity instanceof LivingEntity)
                || entity.equals(this.getArrow().getShooter())
                || !Storage.COMPATIBILITY_ADAPTER.attackEntity((LivingEntity) entity, (Player) this.getArrow().getShooter(), 0)
            ) {
                continue;
            }

            entity.setFireTicks((int) Math.round(this.getLevel() * this.getPower() * 100));
        }

        this.die();
    }
}