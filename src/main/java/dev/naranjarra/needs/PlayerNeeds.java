package dev.naranjarra.needs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record PlayerNeeds(float bladder, float fun, float social, float energy, float hygiene, int hunger) {

    // CODEC para guardar en el DISCO (Archivo .dat)
    public static final Codec<PlayerNeeds> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("bladder").forGetter(PlayerNeeds::bladder),
            Codec.FLOAT.fieldOf("fun").forGetter(PlayerNeeds::fun),
            Codec.FLOAT.fieldOf("social").forGetter(PlayerNeeds::social),
            Codec.FLOAT.fieldOf("energy").forGetter(PlayerNeeds::energy),
            Codec.FLOAT.fieldOf("hygiene").forGetter(PlayerNeeds::hygiene),
            Codec.INT.fieldOf("hunger").forGetter(PlayerNeeds::hunger)
    ).apply(instance, PlayerNeeds::new));

    // STREAM_CODEC para enviar por RED (Sincronización)
    public static final StreamCodec<RegistryFriendlyByteBuf, PlayerNeeds> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, PlayerNeeds::bladder,
            ByteBufCodecs.FLOAT, PlayerNeeds::fun,
            ByteBufCodecs.FLOAT, PlayerNeeds::social,
            ByteBufCodecs.FLOAT, PlayerNeeds::energy,
            ByteBufCodecs.FLOAT, PlayerNeeds::hygiene,
            ByteBufCodecs.INT, PlayerNeeds::hunger,
            PlayerNeeds::new
    );

    // Instancia por defecto
    public static final PlayerNeeds DEFAULT = new PlayerNeeds(20, 20, 20, 20, 20, 20);
}