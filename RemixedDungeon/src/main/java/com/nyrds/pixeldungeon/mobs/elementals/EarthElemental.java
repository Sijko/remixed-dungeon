package com.nyrds.pixeldungeon.mobs.elementals;

import com.nyrds.pixeldungeon.mobs.common.IDepthAdjustable;
import com.nyrds.pixeldungeon.mobs.common.MultiKindMob;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.actors.Char;
import com.watabou.pixeldungeon.actors.blobs.Blob;
import com.watabou.pixeldungeon.actors.blobs.Fire;
import com.watabou.pixeldungeon.actors.blobs.Regrowth;
import com.watabou.pixeldungeon.actors.blobs.ToxicGas;
import com.watabou.pixeldungeon.actors.buffs.Bleeding;
import com.watabou.pixeldungeon.actors.buffs.Paralysis;
import com.watabou.pixeldungeon.actors.buffs.Roots;
import com.watabou.pixeldungeon.levels.Terrain;
import com.watabou.pixeldungeon.levels.TerrainFlags;
import com.watabou.pixeldungeon.plants.Earthroot;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.utils.Random;

import androidx.annotation.NonNull;

public class EarthElemental extends MultiKindMob implements IDepthAdjustable {

	public EarthElemental() {
		adjustStats(Dungeon.depth);

		loot = new Earthroot.Seed();
		lootChance = 0.1f;
	}

	public void adjustStats(int depth) {
		kind = Math.min(depth/5, 4);
		
		hp(ht(depth * 10 + 1));
		defenseSkill = depth / 2 + 1;
		exp = depth + 1;
		maxLvl = depth + 2;
		
		addImmunity(Roots.class);
		addImmunity(Paralysis.class);
		addImmunity(ToxicGas.class);
		addImmunity(Fire.class);
		addImmunity(Bleeding.class);
	}

	@Override
	public float speed() {
		if(TerrainFlags.is(Dungeon.level.map[getPos()], TerrainFlags.LIQUID)) {
			return super.speed() * 0.5f;
		} else {
			return super.speed();
		}
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(hp() / 5, ht() / 5);
	}

	@Override
	public int attackSkill(Char target) {
		return defenseSkill / 2;
	}

	@Override
	public int dr() {
		return exp;
	}

	@Override
	public int attackProc(@NonNull Char enemy, int damage) {

		int cell = enemy.getPos();

		if (Random.Int(2) == 0) {
			int c = Dungeon.level.map[cell];
			if (c == Terrain.EMPTY || c == Terrain.EMBERS
					|| c == Terrain.EMPTY_DECO || c == Terrain.GRASS
					|| c == Terrain.HIGH_GRASS) {
				
				GameScene.add(Blob.seed(cell, Math.max(exp,10) * 15, Regrowth.class));
			}
		}
		return damage;
	}
}
