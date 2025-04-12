package com.mihey.voidmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class TheVoidClient implements ClientModInitializer {

    private long lastTeleportTime = 0; // Track time for the 2-second delay
    private boolean waitingForTeleport = false;

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
                if (minecraftClient.player != null) {
                    ClientPlayNetworking.send(new TeleportPayload());

                }

                // Set the timer to wait 2 seconds before the next teleport
                lastTeleportTime = System.currentTimeMillis();
                waitingForTeleport = true;


                if (TheVoid.WorldRegeneration) {
                    TheVoid.LOGGER.warn("VOID DIMENSION REGENERATION IS ACTIVE! THIS IS A DEVELOPER TOOL!");
                    Path dir = Paths.get("/home/Chronos/code/Java/The Void/run/saves/New World/dimensions/thevoid");
                    try {
                        Files.walk(dir)
                                .sorted(Comparator.reverseOrder()) // Delete contents first
                                .map(Path::toFile)
                                .forEach(File::delete);
                    } catch (IOException e) {
                        System.err.println("An error occurred while deleting the directory: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
            if (waitingForTeleport && (System.currentTimeMillis() - lastTeleportTime >= 2000)) {
                //tp
                waitingForTeleport = false; // Reset after teleporting
            }
        });
    }


}
