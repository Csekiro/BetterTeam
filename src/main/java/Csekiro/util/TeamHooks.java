package Csekiro.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;

public final class TeamHooks {
    private TeamHooks() {
    }

    public static boolean shouldCancelDamage(Entity victim, DamageSource source) {
        if (victim == null || source == null) {
            return false;
        }

        Entity direct = source.getSource();
        if (sameTeam(victim, direct)) {
            return true;
        }

        if (direct instanceof LightningEntity lightning && sameTeam(victim, lightning.getChanneler())) {
            return true;
        }

        Entity attacker = source.getAttacker();
        return sameTeam(victim, attacker);
    }

    public static boolean shouldProjectileIgnore(Entity projectile, Entity target) {
        if (projectile == null || target == null) {
            return false;
        }

        Entity owner = getProjectileOwner(projectile);
        return sameTeam(owner, target);
    }

    @Nullable
    private static Entity getProjectileOwner(Entity projectile) {
        try {
            return (Entity) projectile.getClass().getMethod("getOwner").invoke(projectile);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static void syncLightningToChannelerTeam(LightningEntity lightning) {
        if (lightning == null || lightning.getEntityWorld().isClient()) {
            return;
        }

        Entity channeler = lightning.getChanneler();
        if (channeler == null) {
            return;
        }

        Team team = channeler.getScoreboardTeam();
        if (team == null) {
            return;
        }

        if (!(lightning.getEntityWorld() instanceof ServerWorld serverWorld)) {
            return;
        }

        Scoreboard scoreboard = serverWorld.getScoreboard();
        String holder = lightning.getNameForScoreboard();

        Team current = scoreboard.getScoreHolderTeam(holder);
        if (current == team) {
            return;
        }

        if (current != null) {
            scoreboard.clearTeam(holder);
        }

        scoreboard.addScoreHolderToTeam(holder, team);
    }

    public static boolean sameTeam(@Nullable Entity a, @Nullable Entity b) {
        if (a == null || b == null) {
            return false;
        }
        if (a == b) {
            return true;
        }
        return a.isTeammate(b);
    }
}