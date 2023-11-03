package no.ntnu.message;

import no.ntnu.tv.SmartTv;

/**
 * A command asking for the number of the channels.
 */
public class ChannelCountCommand implements GetCommand {
    @Override
    public Message execute(SmartTv logic) {
        Message response;
        try {
            int channelCount = logic.getChannelCount();
            response = new ChannelCountMessage(channelCount);
        } catch (IllegalStateException e) {
            response = new ErrorMessage(e.getMessage());
        }
        return response;
    }
}
