package com.nyrds.pixeldungeon.items;

import com.nyrds.Packable;
import com.nyrds.pixeldungeon.mechanics.LuaScript;
import com.watabou.noosa.StringsManager;
import com.watabou.pixeldungeon.actors.hero.Hero;
import com.watabou.pixeldungeon.items.Item;
import com.watabou.utils.Bundle;

import org.luaj.vm2.LuaTable;

import java.util.ArrayList;

/**
 * Created by mike on 26.05.2018.
 * This file is part of Remixed Pixel Dungeon.
 */
public class CustomItem extends Item {

    @Packable
    private String scriptFile;

    private LuaScript script;

    public CustomItem() {
    }

    public CustomItem(String scriptFile) {
        this.scriptFile = scriptFile;
        initItem();
    }

    private void initItem() {
        script = new LuaScript(scriptFile, this);

        script.runScript("desc",null,null);
        LuaTable desc = script.getResult().checktable();

        image     = desc.rawget("image").checkint();
        imageFile = desc.rawget("imageFile").checkjstring();
        name      = StringsManager.maybeId(desc.rawget("name").checkjstring());
        info      = StringsManager.maybeId(desc.rawget("info").checkjstring());
    }

    @Override
    public void execute(Hero hero, String action) {
        super.execute(hero,action);
        script.runScript("execute", hero, action);
    }

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);

        script.runScript("actions", hero, null);

        if(script.getResult().istable()) {
            LuaTable luaActions = script.getResult().checktable();

            int n = luaActions.rawlen();

            for(int i =1;i<=n;++i){
                actions.add(luaActions.rawget(i).checkjstring());
            }

        }
        return actions;
    }


    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        initItem();
    }
}
