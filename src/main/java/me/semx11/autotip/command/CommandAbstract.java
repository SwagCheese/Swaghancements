package me.semx11.autotip.command;

import cc.hyperium.commands.BaseCommand;
import me.semx11.autotip.Autotip;

public abstract class CommandAbstract implements BaseCommand {

    public final Autotip autotip;

    protected CommandAbstract(Autotip autotip) {
        this.autotip = autotip;
    }




}
