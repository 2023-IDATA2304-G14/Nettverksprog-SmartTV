package no.ntnu.message;

import no.ntnu.tv.SmartTv;

/**
 * A command sent from the client to the server (from remote to TV).
 */
public interface Command extends Message {
    /**
     * Execute the command.
     *
     * @param logic The TV logic to be affected by this command
     * @return The message which contains the output of the command
     */
    public abstract Message execute(SmartTv logic);
}