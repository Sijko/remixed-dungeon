package com.nyrds.pixeldungeon.items.food;

import com.nyrds.pixeldungeon.ml.R;
import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.actors.hero.Hero;

abstract public class Mosscap extends Mushroom {
	{
		image = 3;
		message = Game.getVar(R.string.Mushroom_Eat_Message);
	}

	@Override
	protected void applyEffect(Hero hero){
		//TODO: + 1 str.  А называется он Жизневик Кудрявый
	}
}
