package dev.ftb.mods.ftbchunks.core.mixin;

import dev.ftb.mods.ftbchunks.data.ClaimedChunkManagerImpl;
import dev.ftb.mods.ftblibrary.math.ChunkDimPos;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkMap.class)
public abstract class ChunkMapMixin {
	@Shadow
	@Final
	ServerLevel level;

	@Inject(method = "anyPlayerCloseEnoughForSpawning", at = @At("RETURN"), cancellable = true)
	private void anyPlayerCloseEnoughForSpawningFTBC(ChunkPos pos, CallbackInfoReturnable<Boolean> ci) {
		if (!ci.getReturnValue() && ClaimedChunkManagerImpl.getInstance().isChunkForceLoaded(new ChunkDimPos(level.dimension(), pos))) {
			ci.setReturnValue(true);
		}
	}
}
