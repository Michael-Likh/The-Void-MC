package com.mihey.voidmod.world.dimension;

import com.mihey.voidmod.TheVoid;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;


public class ModDimensions {
    public static final RegistryKey<World> VOID_DIMENSION_KEY = RegistryKey.of(
            RegistryKeys.WORLD,
            Identifier.of(TheVoid.MOD_ID, "void")
    );
    public static final RegistryKey<DimensionType> VOID_TYPE_KEY = RegistryKey.of(
            RegistryKeys.DIMENSION_TYPE,
            VOID_DIMENSION_KEY.getValue()
    );

    public static void register() {
        TheVoid.LOGGER.debug("Registering Dimension");
    }


}
