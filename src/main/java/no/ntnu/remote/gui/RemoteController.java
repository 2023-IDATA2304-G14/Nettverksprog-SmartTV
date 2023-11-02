package no.ntnu.remote.gui;

public class RemoteController {
    private RemoteModel model;
    private RemoteView view;

    public RemoteController(RemoteModel model, RemoteView view) {
        this.model = model;
        this.view = view;
    }

    public void turnOnTV() {
        model.turnOnTV();
    }

    public void turnOffTV() {
        model.turnOffTV();
    }

    public void channelUp() {
        model.channelUp();
    }

    public void channelDown() {
        model.channelDown();
    }
}
