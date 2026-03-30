package Csekiro.mixin;

import Csekiro.util.TeamHooks;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Inject(method = "handleAttack", at = @At("HEAD"), cancellable = true)
    private void betterteam$cancelFriendlyMelee(Entity attacker, CallbackInfoReturnable<Boolean> cir) {
        Entity self = (Entity) (Object) this;

        if (self.getEntityWorld().isClient()) {
            return;
        }

        if (TeamHooks.sameTeam(self, attacker)) {
            cir.setReturnValue(true);
        }
    }
}
