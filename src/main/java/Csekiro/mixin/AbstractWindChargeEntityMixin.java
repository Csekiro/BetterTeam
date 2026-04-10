package Csekiro.mixin;

import Csekiro.util.TeamHooks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.AbstractWindChargeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractWindChargeEntity.class)
public abstract class AbstractWindChargeEntityMixin {
    @Inject(method = "canHit(Lnet/minecraft/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
    private void betterteam$ignoreTeammates(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (TeamHooks.shouldProjectileIgnore((AbstractWindChargeEntity) (Object) this, entity)) {
            cir.setReturnValue(false);
        }
    }
}
