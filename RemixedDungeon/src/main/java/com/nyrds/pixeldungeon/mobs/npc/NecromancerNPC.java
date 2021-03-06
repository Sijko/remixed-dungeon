package com.nyrds.pixeldungeon.mobs.npc;

import com.nyrds.pixeldungeon.ml.R;
import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.actors.hero.Hero;
import com.watabou.pixeldungeon.items.keys.SkeletonKey;
import com.watabou.pixeldungeon.levels.RegularLevel;
import com.watabou.pixeldungeon.levels.Room;
import com.watabou.pixeldungeon.levels.Terrain;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.utils.GLog;
import com.watabou.pixeldungeon.windows.WndQuest;
import com.watabou.utils.Bundle;

public class NecromancerNPC extends ImmortalNPC {

	private static final String NODE       = "necromancernpc";
	private static final String INTRODUCED = "introduced";

	private boolean introduced = false;

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);

		Bundle node = new Bundle();
		node.put(INTRODUCED, introduced);

		bundle.put(NODE, node);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);

		Bundle node = bundle.getBundle(NODE);

		if(node.isNull()){
			return;
		}

		introduced = node.optBoolean(INTRODUCED, false);
	}

	@Override
	public boolean reset() {
		return true;
	}

	public static void spawn(RegularLevel level, Room room) {
		NecromancerNPC npc = new NecromancerNPC();
			int cell;
			do {
				cell = room.random(level);
			} while (level.map[cell] == Terrain.LOCKED_EXIT);
			npc.setPos(cell);
			level.spawnMob(npc);
	}

	@Override
	public boolean interact(final Hero hero) {
		getSprite().turnTo(getPos(), hero.getPos());

		if (!introduced) {
			GameScene.show(new WndQuest(this, Game.getVar(R.string.NecromancerNPC_Intro2)));
			introduced = true;

			SkeletonKey key = new SkeletonKey();

			if (key.doPickUp( Dungeon.hero )) {
				GLog.i( Hero.getHeroYouNowHave(), key.name() );
			} else {
				Dungeon.level.drop( key, Dungeon.hero.getPos() ).sprite.drop();
			}

		} else {

			sayRandomPhrase(R.string.NecromancerNPC_Message1,
							R.string.NecromancerNPC_Message2,
							R.string.NecromancerNPC_Message3,
							R.string.NecromancerNPC_Message4);
		}
		return true;
	}
}
