package com.github.fabricservertools.deltalogger.testmod;

import com.github.fabricservertools.deltalogger.testmod.command.ClientInspectCommand;
import com.github.fabricservertools.deltalogger.testmod.network.InspectHandler;
import net.fabricmc.api.ClientModInitializer;

public class TestmodInit implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        InspectHandler.register();
        ClientInspectCommand.register();
    }
}
