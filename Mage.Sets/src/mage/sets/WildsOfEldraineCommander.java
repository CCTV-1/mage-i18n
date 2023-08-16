package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class WildsOfEldraineCommander extends ExpansionSet {

    private static final WildsOfEldraineCommander instance = new WildsOfEldraineCommander();

    public static WildsOfEldraineCommander getInstance() {
        return instance;
    }

    private WildsOfEldraineCommander() {
        super("Wilds of Eldraine Commander", "WOC", ExpansionSet.buildDate(2023, 9, 8), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;
    }
}