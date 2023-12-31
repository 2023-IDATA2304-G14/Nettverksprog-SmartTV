package no.ntnu.message;

import no.ntnu.tv.SmartTv;

/**
 * A command requesting to know the current channel of a TV.
 */
public class CurrentChannelCommand implements GetCommand {
    @Override
    public Message execute(SmartTv logic) {
        Message response;
        try {
            int channel = logic.getCurrentChannel();
            response = new CurrentChannelMessage(channel);
        } catch (Exception e) {
            response = new ErrorMessage(e.getMessage());
        }
        return response;
    }
}
