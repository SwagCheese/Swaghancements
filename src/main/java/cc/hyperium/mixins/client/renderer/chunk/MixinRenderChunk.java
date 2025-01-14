/*
 *       Copyright (C) 2018-present Hyperium <https://hyperium.cc/>
 *
 *       This program is free software: you can redistribute it and/or modify
 *       it under the terms of the GNU Lesser General Public License as published
 *       by the Free Software Foundation, either version 3 of the License, or
 *       (at your option) any later version.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU Lesser General Public License for more details.
 *
 *       You should have received a copy of the GNU Lesser General Public License
 *       along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package cc.hyperium.mixins.client.renderer.chunk;

import cc.hyperium.mixinsimp.client.renderer.chunk.HyperiumRenderChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/*
 * Created by Cubxity on 12/1/2018
 */

@Mixin(RenderChunk.class)
public class MixinRenderChunk {

    private final HyperiumRenderChunk hyperiumRenderChunk = new HyperiumRenderChunk((RenderChunk) (Object) this);

    @SuppressWarnings("MixinAnnotationTarget")
    @Inject(method = "setPosition", at = @At("INVOKE")) // not sure why this injection works
    private void setPosition(BlockPos bp, CallbackInfo ci) {
        hyperiumRenderChunk.setPosition(bp);
    }
}
