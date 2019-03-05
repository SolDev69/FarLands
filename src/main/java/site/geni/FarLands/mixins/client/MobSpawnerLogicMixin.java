package site.geni.FarLands.mixins.client;

import net.minecraft.particle.ParticleTypes;
import net.minecraft.sortme.MobSpawnerLogic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import site.geni.FarLands.utils.Config;

@SuppressWarnings("unused")
@Mixin(MobSpawnerLogic.class)
public abstract class MobSpawnerLogicMixin {
	@Shadow
	private double field_9161;

	@Shadow
	private double field_9159;

	@Shadow
	private int spawnDelay = 20;

	@Shadow
	public abstract BlockPos getPos();

	@Shadow
	public abstract World getWorld();


	@Inject(
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleParameters;DDDDDD)V",
					ordinal = 0
			),
			method = "update",
			cancellable = true
	)
	private void addParticlesProperly(CallbackInfo ci) {
		if (Config.getConfig().fixParticles && Config.getConfig().farLandsEnabled) {
			BlockPos blockPos = this.getPos();
			World world = this.getWorld();

			double x = blockPos.getX() + world.random.nextDouble();
			double y = blockPos.getY() + world.random.nextDouble();
			double z = blockPos.getZ() + world.random.nextDouble();

			world.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0D, 0.0D, 0.0D);
			world.addParticle(ParticleTypes.FLAME, x, y, z, 0.0D, 0.0D, 0.0D);

			if (this.spawnDelay > 0) {
				--this.spawnDelay;
			}

			this.field_9159 = this.field_9161;
			this.field_9161 = (this.field_9161 + (1000.0F / (this.spawnDelay + 200.0F))) % 360.0D;

			ci.cancel();
		}
	}
}