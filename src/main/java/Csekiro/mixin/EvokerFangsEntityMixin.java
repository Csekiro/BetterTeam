package Csekiro.mixin;

import Csekiro.util.TeamHooks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EvokerFangsEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EvokerFangsEntity.class)
public abstract class EvokerFangsEntityMixin {
    @Inject(method = "damage(Lnet/minecraft/entity/LivingEntity;)V", at = @At("HEAD"), cancellable = true)
    private void betterteam$cancelFriendlyFangs(LivingEntity target, CallbackInfo ci) {
        if (TeamHooks.sameTeam((Entity) (Object) this, target)) {
            ci.cancel();
        }
    }
}
