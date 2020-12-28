package zedly.zenchantments.enchantments;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import zedly.zenchantments.*;

import java.util.Set;

import static org.bukkit.Material.*;

public final class Gluttony extends Zenchantment {
    public static final String KEY = "gluttony";

    private static final String                             NAME        = "Gluttony";
    private static final String                             DESCRIPTION = "Automatically eats for the player";
    private static final Set<Class<? extends Zenchantment>> CONFLICTING = ImmutableSet.of();
    private static final Hand                               HAND_USE    = Hand.NONE;

    private final NamespacedKey key;

    public Gluttony(
        final @NotNull ZenchantmentsPlugin plugin,
        final @NotNull Set<Tool> enchantable,
        final int maxLevel,
        final int cooldown,
        final double power,
        final float probability
    ) {
        super(plugin, enchantable, maxLevel, cooldown, power, probability);
        this.key = new NamespacedKey(plugin, KEY);
    }

    @Override
    @NotNull
    public NamespacedKey getKey() {
        return this.key;
    }

    @Override
    @NotNull
    public String getName() {
        return NAME;
    }

    @Override
    @NotNull
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    @NotNull
    public Set<Class<? extends Zenchantment>> getConflicting() {
        return CONFLICTING;
    }

    @Override
    @NotNull
    public Hand getHandUse() {
        return HAND_USE;
    }

    @Override
    public boolean onScan(@NotNull Player player, int level, boolean usedHand) {
        for (int i = 0; i < Storage.COMPATIBILITY_ADAPTER.GluttonyFoodItems().length; i++) {
            Material foodMaterial = Storage.COMPATIBILITY_ADAPTER.GluttonyFoodItems()[i];
            int foodLevel = Storage.COMPATIBILITY_ADAPTER.GluttonyFoodLevels()[i];

            if (!player.getInventory().containsAtLeast(new ItemStack(foodMaterial), 1)
                || player.getFoodLevel() > 20 - foodLevel
            ) {
                continue;
            }

            Utilities.removeMaterialsFromPlayer(player, foodMaterial, 1);

            player.setFoodLevel(player.getFoodLevel() + foodLevel);
            player.setSaturation((float) (player.getSaturation() + Storage.COMPATIBILITY_ADAPTER.GluttonySaturations()[i]));

            if (foodMaterial == RABBIT_STEW
                || foodMaterial == MUSHROOM_STEW
                || foodMaterial == BEETROOT_SOUP
            ) {
                player.getInventory().addItem(new ItemStack(BOWL));
            }
        }

        return true;
    }
}