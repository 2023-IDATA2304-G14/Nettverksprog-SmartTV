package no.ntnu.message;

import no.ntnu.tv.SmartTv;

/**
 * A command requesting to turn on the TV.
 */
public class TurnOnCommand extends Command {
    @Override
    public Message execute(SmartTv logic) {
        logic.turnOn();
        return new TvStateMessage(logic.isTvOn());
    }
}
