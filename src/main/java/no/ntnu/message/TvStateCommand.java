package no.ntnu.message;

import no.ntnu.tv.SmartTv;

public class TvStateCommand implements GetCommand {
    @Override
    public Message execute(SmartTv logic) {
        Message response;
        try {
            boolean tvState = logic.isTvOn();
            response = new TvStateMessage(tvState);
        } catch (Exception e) {
            response = new ErrorMessage(e.getMessage());
        }
        return response;
    }
}
