package no.ntnu;

import no.ntnu.remote.gui.RemoteController;
import no.ntnu.remote.gui.RemoteModel;
import no.ntnu.remote.gui.RemoteView;

public class RemoteApp {
    public static void main(String[] args) {
//        RemoteControl model = new RemoteControl();
        RemoteModel model = new RemoteModel();
        RemoteView view = new RemoteView();
        RemoteController controller = new RemoteController(model, view);
    }
}
