
package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ErtaiTheCorrupted extends CardImpl {

    public ErtaiTheCorrupted(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {U}, {tap}, Sacrifice a creature or enchantment: Counter target spell.
        Ability ability = new SimpleActivatedAbility(new CounterTargetEffect(), new ManaCostsImpl<>("{U}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE_OR_ENCHANTMENT));
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);
    }

    private ErtaiTheCorrupted(final ErtaiTheCorrupted card) {
        super(card);
    }

    @Override
    public ErtaiTheCorrupted copy() {
        return new ErtaiTheCorrupted(this);
    }
}
