package no.ntnu;

import no.ntnu.remote.RemoteControl;
import no.ntnu.remote.gui.RemoteController;
import no.ntnu.remote.gui.RemoteView;

public class RemoteApp {
    public static void main(String[] args) {
        RemoteControl model = new RemoteControl();
        RemoteView view = new RemoteView();
        RemoteController controller = new RemoteController(model, view);
    }
}
