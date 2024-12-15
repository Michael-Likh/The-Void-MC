package com.mihey.voidmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class TheVoidClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        System.out.println("The_Void_Client initialized!");

         KeyBinding key_r = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.thevoid.spook",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.thevoid.test")
        );
        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
            while (key_r.wasPressed()){
               minecraftClient.player.sendMessage(Text.literal("key R was pressed"), false);
            }
        });
    }
}
