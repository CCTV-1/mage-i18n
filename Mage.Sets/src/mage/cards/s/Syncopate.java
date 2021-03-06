package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Syncopate extends CardImpl {

    public Syncopate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}");

        // Counter target spell unless its controller pays {X}. If that spell is countered this way, exile it instead of putting it into its owner's graveyard.
        this.getSpellAbility().addEffect(new SyncopateCounterUnlessPaysEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private Syncopate(final Syncopate card) {
        super(card);
    }

    @Override
    public Syncopate copy() {
        return new Syncopate(this);
    }
}

class SyncopateCounterUnlessPaysEffect extends OneShotEffect {

    public SyncopateCounterUnlessPaysEffect() {
        super(Outcome.Detriment);
    }

    public SyncopateCounterUnlessPaysEffect(final SyncopateCounterUnlessPaysEffect effect) {
        super(effect);
    }

    @Override
    public SyncopateCounterUnlessPaysEffect copy() {
        return new SyncopateCounterUnlessPaysEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject spell = game.getStack().getStackObject(targetPointer.getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if ((spell instanceof Spell) && sourceObject != null) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                // can be zero cost
                Cost cost = ManaUtil.createManaCost(ManacostVariableValue.instance, game, source, this);
                if (!cost.pay(source, game, source, spell.getControllerId(), false)) {
                    StackObject stackObject = game.getStack().getStackObject(source.getFirstTarget());
                    if (stackObject != null && !game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.COUNTER, source.getFirstTarget(), source, stackObject.getControllerId()))) {
                        game.informPlayers(sourceObject.getIdName() + ": cost wasn't payed - countering " + stackObject.getName());
                        game.rememberLKI(source.getFirstTarget(), Zone.STACK, stackObject);
                        controller.moveCards((Spell) spell, Zone.EXILED, source, game);
                        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.COUNTERED, source.getFirstTarget(), source, stackObject.getControllerId()));
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Counter target spell unless its controller pays {X}. If that spell is countered this way, exile it instead of putting it into its owner's graveyard";
    }

}
