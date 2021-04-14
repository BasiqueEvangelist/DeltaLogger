package com.github.fabricservertools.deltalogger.testmod.mixins;

import com.github.fabricservertools.deltalogger.testmod.command.ClientInspectCommand;
import com.github.fabricservertools.deltalogger.testmod.network.InspectHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {
	@Inject(
		method = "interactBlock",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/network/ClientPlayNetworkHandler;sendPacket(Lnet/minecraft/network/Packet;)V"
		),
		cancellable = true
	)
	private void onInteractBlock(ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
		if (!ClientInspectCommand.isEnabled)
			return;

		cir.setReturnValue(ActionResult.SUCCESS);
		InspectHandler.sendInspectPacket(hitResult.getBlockPos(), (short) 10);
	}
}