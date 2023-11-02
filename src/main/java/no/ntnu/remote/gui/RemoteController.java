package no.ntnu.remote.gui;

public class RemoteController {
    private RemoteModel model;
    private RemoteView view;

    public RemoteController(RemoteModel model, RemoteView view) {
        this.model = model;
        this.view = view;
    }

    public void turnOnTV() {
        //model.turnOnTV();
    }

    public void turnOffTV() {
        // Communicate with model to turn off the TV
        // Update the view's responseArea with the server's response
    }

    public void channelUp() {
        // Communicate with model to increase channel
        // Update the view's responseArea with the server's response
    }

    public void channelDown() {
        // Communicate with model to decrease channel
        // Update the view's responseArea with the server's response
    }

    public void setChannel() {
        // Retrieve the channel number from the view's channelField
        // Communicate with model to set the channel to that number
        // Update the view's responseArea with the server's response
    }
}
