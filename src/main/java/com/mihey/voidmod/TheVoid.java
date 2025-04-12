package com.mihey.voidmod;

import com.mihey.voidmod.item.ModItems;
import com.mihey.voidmod.world.dimension.ModDimensions;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Comparator;
import java.io.File;
import java.nio.file.*;

public class TheVoid implements ModInitializer {
	public static final String MOD_ID = "thevoid";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static boolean WorldRegeneration = true;
	public static final CustomPayload.Id<TeleportPayload> TELEPORT_PACKET_ID =
			new CustomPayload.Id<>(Identifier.of("thevoid", "teleport"));

	@Override
	public void onInitialize() {
		LOGGER.info("The_Void initialized!");

		if (WorldRegeneration) {
			LOGGER.warn("VOID DIMENSION REGENERATION IS ACTIVE! THIS IS A DEVELOPER TOOL!");
			Path dir = Paths.get("run/saves/New World/dimensions/thevoid");
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



		ModItems.registerModItems();
		ModDimensions.register();




		// Register payload type
		PayloadTypeRegistry.playC2S().register(TeleportPayload.ID, TeleportPayload.CODEC);

		ServerPlayNetworking.registerGlobalReceiver(TeleportPayload.ID, (payload, context) -> {
			ServerPlayerEntity player = context.player();
			ServerWorld targetWorld = player.getServer().getWorld(World.END);

			player.teleport(
					targetWorld,
					100.5,
					60,
					200.5,
					player.getYaw(),
					player.getPitch()
			);
		});
	}
}
// Add payload definition
record TeleportPayload() implements CustomPayload {
	public static final CustomPayload.Id<TeleportPayload> ID =
			new CustomPayload.Id<>(Identifier.of("thevoid", "teleport"));

	public static final PacketCodec<RegistryByteBuf, TeleportPayload> CODEC =
			PacketCodec.unit(new TeleportPayload());

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}



// NOTES:
//directionless V
//soul
//pacts
//patrons
//layers
//