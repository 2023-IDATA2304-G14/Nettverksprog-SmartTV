package no.ntnu.Commands;

import no.ntnu.SmartTv;

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
