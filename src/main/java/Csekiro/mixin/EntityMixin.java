package Csekiro.mixin;

import Csekiro.util.TeamHooks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin extends Entity {
    public EntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void betterteam$cancelFriendlyDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        Entity self = (Entity) (Object) this;

        if (self.getEntityWorld().isClient()) {
            return;
        }

        if (amount <= 0.0F) {
            return;
        }

        if (TeamHooks.shouldCancelDamage(self, source)) {
            cir.setReturnValue(false);
        }
    }
}