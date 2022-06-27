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

package cc.hyperium.gui;

import cc.hyperium.Hyperium;
import cc.hyperium.config.Settings;
import cc.hyperium.gui.hyperium.HyperiumMainGui;
import cc.hyperium.purchases.PurchaseApi;
import cc.hyperium.utils.JsonHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.server.MinecraftServer;

import java.io.IOException;

public class GuiHyperiumScreenIngameMenu extends GuiScreen {

    @Override
    public void initGui() {
        super.initGui();

        buttonList.clear();
        int i = -16;

        buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 120 + i, I18n.format("menu.returnToMenu")));

        /* If Client is on server, add disconnect button */
        if (!mc.isIntegratedServerRunning()) (buttonList.get(0)).displayString = I18n.format("menu.disconnect");

        /* Add initial buttons */
        buttonList.add(new GuiButton(4, width / 2 - 100, height / 4 + 24 + i, I18n.format("menu.returnToGame")));
        buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 96 + i, 98, 20, I18n.format("menu.options")));

        GuiButton guibutton = new GuiButton(7, width / 2 + 2, height / 4 + 96 + i, 98, 20, I18n.format("menu.shareToLan"));
        buttonList.add(guibutton);
        buttonList.add(new GuiButton(5, width / 2 - 100, height / 4 + 48 + i, 98, 20, I18n.format("gui.achievements")));
        buttonList.add(new GuiButton(6, width / 2 + 2, height / 4 + 48 + i, 98, 20, I18n.format("gui.stats")));

        guibutton.enabled = mc.isSingleplayer() && !mc.getIntegratedServer().getPublic();

        buttonList.add(new GuiButton(9, width / 2 - 100, height / 4 + 56, 98, 20, I18n.format("button.ingame.hyperiumsettings")));
        buttonList.add(new GuiButton(8, width / 2 + 2, height / 4 + 56, 98, 20, I18n.format("button.ingame.hyperiumcredits")));

        WorldClient theWorld = Minecraft.getMinecraft().theWorld;

        // Used to detect if the player is on a singleplayer world or a multiplayer world.
        MinecraftServer integratedServer = Minecraft.getMinecraft().getIntegratedServer();
        if (theWorld != null && (integratedServer == null)) {
            GuiButton oldButton = buttonList.remove(3);
            GuiButton newButton = new GuiButton(10, oldButton.xPosition, oldButton.yPosition, oldButton.getButtonWidth(), 20,
                I18n.format("button.ingame.serverlist"));
            buttonList.add(newButton);
        }

    }

    @Override
    public void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        switch (button.id) {
            case 0:
                mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
                break;

            case 1:
                if (Settings.CONFIRM_DISCONNECT) {
                    Hyperium.INSTANCE.getHandlers().getGuiDisplayHandler().setDisplayNextTick(new GuiConfirmDisconnect());
                } else {
                    boolean integratedServerRunning = mc.isIntegratedServerRunning();
                    button.enabled = false;
                    mc.theWorld.sendQuittingDisconnectingPacket();
                    mc.loadWorld(null);
                    Hyperium.INSTANCE.getHandlers().getGuiDisplayHandler().setDisplayNextTick(integratedServerRunning ? new GuiMainMenu() : new GuiMultiplayer(new GuiMainMenu()));
                }

                break;

            case 4:
                mc.displayGuiScreen(null);
                mc.setIngameFocus();
                break;

            case 5:
                Hyperium.INSTANCE.getHandlers().getGuiDisplayHandler().setDisplayNextTick(new GuiAchievements(this, mc.thePlayer.getStatFileWriter()));
                break;

            case 6:
                Hyperium.INSTANCE.getHandlers().getGuiDisplayHandler().setDisplayNextTick(new GuiStats(this, mc.thePlayer.getStatFileWriter()));
                break;

            case 7:
                Hyperium.INSTANCE.getHandlers().getGuiDisplayHandler().setDisplayNextTick(new GuiShareToLan(this));
                break;

            case 8:
                Hyperium.INSTANCE.getHandlers().getGuiDisplayHandler().setDisplayNextTick(new GuiHyperiumCredits(Minecraft.getMinecraft().currentScreen));
                break;

            case 9:
                HyperiumMainGui.INSTANCE.show();
                break;

            case 10:
                Hyperium.INSTANCE.getHandlers().getGuiDisplayHandler().setDisplayNextTick(new GuiIngameMultiplayer(Minecraft.getMinecraft().currentScreen));
                break;

            default:
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        if (PurchaseApi.getInstance() != null && PurchaseApi.getInstance().getSelf() != null) {
            JsonHolder response = PurchaseApi.getInstance().getSelf().getResponse();
            int credits = response.optInt("remaining_credits");

            /* Render player credits count and username */
            fontRendererObj.drawStringWithShadow(Minecraft.getMinecraft().getSession().getUsername(), 3, 5, 0xFFFFFF);
            fontRendererObj.drawStringWithShadow(I18n.format("menu.profile.credits", credits), 3, 15, 0xFFFF00);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
