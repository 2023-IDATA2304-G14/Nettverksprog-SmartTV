package no.ntnu;

public class RemoteController {
    private RemoteControl model;
    private RemoteView view;

    public RemoteController(RemoteControl model, RemoteView view) {
        this.model = model;
        this.view = view;

        this.view.powerOnButton.setOnAction(e -> turnOnTV());
        this.view.powerOffButton.setOnAction(e -> turnOffTV());
        this.view.channelUpButton.setOnAction(e -> channelUp());
        this.view.channelDownButton.setOnAction(e -> channelDown());
        this.view.setChannelButton.setOnAction(e -> setChannel());
    }

    private void turnOnTV() {
        // Communicate with model to turn on the TV
        // Update the view's responseArea with the server's response
    }

    private void turnOffTV() {
        // Communicate with model to turn off the TV
        // Update the view's responseArea with the server's response
    }

    private void channelUp() {
        // Communicate with model to increase channel
        // Update the view's responseArea with the server's response
    }

    private void channelDown() {
        // Communicate with model to decrease channel
        // Update the view's responseArea with the server's response
    }

    private void setChannel() {
        // Retrieve the channel number from the view's channelField
        // Communicate with model to set the channel to that number
        // Update the view's responseArea with the server's response
    }
}
