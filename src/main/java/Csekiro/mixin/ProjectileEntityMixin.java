package Csekiro.mixin;

import Csekiro.util.TeamHooks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ProjectileEntity.class)
public abstract class ProjectileEntityMixin {
    @Inject(method = "canHit", at = @At("HEAD"), cancellable = true)
    private void betterteam$ignoreTeammates(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (TeamHooks.shouldProjectileIgnore((ProjectileEntity) (Object) this, entity)) {
            cir.setReturnValue(false);
        }
    }
}