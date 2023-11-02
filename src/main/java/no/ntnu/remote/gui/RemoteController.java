package no.ntnu.remote.gui;

/**
 * The RemoteController represents the controller component fo the Remote MVC architecture.
 * It is responsible for taking the user input and change the model of the view.
 *
 * @author Lars Barstad & Daniel neset
 * @version 02.11.2023
 */
public class RemoteController {
    private RemoteModel model;
    private RemoteView view;

    /**
     * Construct an RemoteController object with the specified model and view.
     *
     * @param model The RemoteModel associated with the controller.
     * @param view The RemoteView associated with the controller.
     */
    public RemoteController(RemoteModel model, RemoteView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Turn on the tv.
     */
    public void turnOnTV() {
        model.turnOnTV();
    }

    /**
     * Turn off the tv.
     */
    public void turnOffTV() {
        model.turnOffTV();
    }

    /**
     * Change the channel up by one.
     */
    public void channelUp() {
        model.channelUp();
    }

    /**
     * Change the channel down by one.
     */
    public void channelDown() {
        model.channelDown();
    }
}
