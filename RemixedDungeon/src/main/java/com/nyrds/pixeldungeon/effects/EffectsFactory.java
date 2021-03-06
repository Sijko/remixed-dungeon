package com.nyrds.pixeldungeon.effects;

import com.nyrds.android.util.JsonHelper;
import com.nyrds.android.util.ModError;
import com.nyrds.android.util.ModdingMode;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EffectsFactory {

    private static Map<String, JSONObject> effects = new HashMap<>();

    static {
        for(String effectFile: ModdingMode.listResources("effects", (dir, name) -> name.endsWith(".json"))) {
            effects.put(effectFile.replace(".json",""),JsonHelper.readJsonFromAsset("effects/"+effectFile));
        }
    }

    public static CustomClipEffect getEffectByName(String name) {

        CustomClipEffect effect = new CustomClipEffect();
        try {
            effect.setupFromJson(effects.get(name));
        } catch (JSONException e) {
            throw new ModError(name, e);
        }

        return effect;
    }

    public static boolean isValidEffectName(String zapEffect) {
        return effects.containsKey(zapEffect);
    }
}
