package no.ntnu.message;

import no.ntnu.tv.SmartTv;

/**
 * A command to increase the channel number by one.
 */
public class ChannelUpCommand extends Command {
    @Override
    public Message execute(SmartTv logic) {
        Message response;
        try {
            int channel = logic.channelUp();
            response = new ChannelMessage(channel);
        } catch (IllegalStateException e) {
            response = new ErrorMessage(e.getMessage());
        }
        return response;
    }
}
