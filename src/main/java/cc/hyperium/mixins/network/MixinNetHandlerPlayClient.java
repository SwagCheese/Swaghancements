package cc.hyperium.mixins.network;

import cc.hyperium.config.Settings;
import io.netty.buffer.Unpooled;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author isxander
 */
@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {
    @Shadow
    @Final
    private NetworkManager netManager;

    @Inject(
        method = {"handleJoinGame"},
        at = {@At("RETURN")}
    )
    private void injectHandleJoinGame(CallbackInfo callbackInfo) {
        if (Settings.LUNAR_SPOOF) {
            this.netManager.sendPacket(new C17PacketCustomPayload("REGISTER", new PacketBuffer(Unpooled.buffer().writeBytes("Lunar-Client".getBytes()))));
        }
    }
}
