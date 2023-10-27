package no.ntnu.Commands;

import no.ntnu.SmartTv;

/**
 * A message requesting that the channel is switched on a TV.
 */
public class SetChannelCommand extends Command {
    private final int channel;

    /**
     * Create a new command for setting channel for a TV.
     *
     * @param channel The desired channel to set
     */
    public SetChannelCommand(int channel) {
        this.channel = channel;
    }

    @Override
    public Message execute(SmartTv logic) {
        try {
            logic.setChannel(channel);
            return new CurrentChannelMessage(channel);
        } catch (Exception e) {
            return new ErrorMessage(e.getMessage());
        }
    }

    /**
     * Get the desired channel.
     *
     * @return The channel which this command should set
     */
    public int getChannel() {
        return channel;
    }
}
