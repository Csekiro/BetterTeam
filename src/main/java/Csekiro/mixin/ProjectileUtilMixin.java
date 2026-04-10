package Csekiro.mixin;

import Csekiro.util.TeamHooks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.function.Predicate;

@Mixin(net.minecraft.entity.projectile.ProjectileUtil.class)
public abstract class ProjectileUtilMixin {
    @Redirect(
            method = "getEntityCollision(Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;F)Lnet/minecraft/util/hit/EntityHitResult;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getOtherEntities(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;)Ljava/util/List;"
            )
    )
    private static List<Entity> betterteam$filterEntityCollisionPredicate(
            World world,
            Entity entity,
            Box box,
            Predicate<Entity> predicate
    ) {
        return world.getOtherEntities(entity, box, betterteam$excludeTeammates(predicate, entity));
    }

    @Redirect(
            method = "raycast(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;D)Lnet/minecraft/util/hit/EntityHitResult;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getOtherEntities(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;)Ljava/util/List;"
            )
    )
    private static List<Entity> betterteam$filterRaycast(
            World world,
            Entity entity,
            Box box,
            Predicate<Entity> predicate
    ) {
        return world.getOtherEntities(entity, box, betterteam$excludeTeammates(predicate, entity));
    }

    private static Predicate<Entity> betterteam$excludeTeammates(Predicate<Entity> predicate, Entity entity) {
        if (!(entity instanceof ProjectileEntity projectile)) {
            return predicate;
        }

        Predicate<Entity> base = predicate != null ? predicate : target -> true;
        return target -> base.test(target) && !TeamHooks.shouldProjectileIgnore(projectile, target);
    }
}
