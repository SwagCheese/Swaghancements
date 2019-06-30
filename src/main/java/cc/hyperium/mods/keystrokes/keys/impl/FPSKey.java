/*
 * Copyright (C) 2019  Hyperium <https://hyperium.cc/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package cc.hyperium.mods.keystrokes.keys.impl;

import cc.hyperium.mods.keystrokes.KeystrokesMod;
import cc.hyperium.mods.keystrokes.keys.IKey;
import net.minecraft.client.Minecraft;

import java.awt.*;

public class FPSKey extends IKey {

    public FPSKey(KeystrokesMod mod, int xOffset, int yOffset) {
        super(mod, xOffset, yOffset);
    }

    @Override
    public void renderKey(int x, int y) {
        int yOffset = this.yOffset;

        if (mod.getSettings().isShowingCPSOnButtons() || !mod.getSettings().isShowingCPS()) {
            yOffset -= 18;
        }

        if (!mod.getSettings().isShowingSpacebar()) {
            yOffset -= 18;
        }

        if (!mod.getSettings().isShowingSneak()) {
            yOffset -= 18;
        }

        if (!mod.getSettings().isShowingMouseButtons()) {
            yOffset -= 24;
        }

        int textColor = getColor();

        if (mod.getSettings().isKeyBackgroundEnabled()) {
            drawRect(x + xOffset, y + yOffset, x + xOffset + 60, y + yOffset + 16, new Color(0, 0, 0, 120).getRGB());
        }

        String name = (Minecraft.getDebugFPS()) + " FPS";

        if (mod.getSettings().isChroma()) {
            drawChromaString(name, ((x + (xOffset + 70) / 2) - mc.fontRendererObj.getStringWidth(name) / 2), y + (yOffset + 4));
        } else {
            drawCenteredString(name, x + ((xOffset + 70) / 2), y + (yOffset + 4), textColor);
        }
    }
}