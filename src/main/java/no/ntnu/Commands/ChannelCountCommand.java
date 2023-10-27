package no.ntnu.Commands;

import no.ntnu.SmartTv;

/**
 * A command asking for the number of the channels.
 */
public class ChannelCountCommand extends Command {
    @Override
    public Message execute(SmartTv logic) {
        Message response;
        try {
            int channelCount = logic.getNumberOfChannels();
            response = new ChannelCountMessage(channelCount);
        } catch (IllegalStateException e) {
            response = new ErrorMessage(e.getMessage());
        }
        return response;
    }
}
