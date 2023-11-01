package no.ntnu.message;

import no.ntnu.tv.SmartTv;

/**
 * A command requesting to turn off the TV.
 */
public class TurnOffCommand extends Command {
    @Override
    public Message execute(SmartTv logic) {
        logic.turnOff();
        return new TvStateMessage(logic.isTvOn());
    }
}
