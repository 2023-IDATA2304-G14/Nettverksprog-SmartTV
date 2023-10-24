package main.java.no.ntnu;

public class RemoteApp {
    public static void main(String[] args) {
        RemoteControl model = new RemoteControl();
        RemoteView view = new RemoteView();
        RemoteController controller = new RemoteController(model, view);
    }
}
