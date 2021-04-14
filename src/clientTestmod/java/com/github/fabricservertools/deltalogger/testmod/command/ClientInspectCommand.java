package com.github.fabricservertools.deltalogger.testmod.command;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.text.LiteralText;

import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.literal;

public final class ClientInspectCommand {
    public static boolean isEnabled = false;

    private ClientInspectCommand() {

    }

    public static void register() {
        ClientCommandManager.DISPATCHER.register(
            literal("dltestmod")
                .then(literal("inspect")
                    .executes(ClientInspectCommand::run))
        );
    }

    private static int run(CommandContext<FabricClientCommandSource> ctx) {
        isEnabled = !isEnabled;
        if (isEnabled) {
            ctx.getSource().sendFeedback(new LiteralText("Enabled client-side inspect mode."));
        } else {
            ctx.getSource().sendFeedback(new LiteralText("Disabled client-side inspect mode."));
        }
        return 1;
    }
}
