package Csekiro.mixin;

import Csekiro.util.TeamHooks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin {
    @Inject(method = "canHit", at = @At("HEAD"), cancellable = true)
    private void betterteam$ignoreTeammates(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (TeamHooks.shouldProjectileIgnore((FishingBobberEntity) (Object) this, entity)) {
            cir.setReturnValue(false);
        }
    }
}