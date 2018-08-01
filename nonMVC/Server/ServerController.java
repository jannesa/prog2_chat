package Server;

public class ServerController {

    private Server server;
    private ServerThread thread;
    private ServerView view;

    public ServerController(Server server, ServerThread thread, ServerView view) {

        this.server = server;
        this.thread = thread;
        this.view = view;
    }




    public void updateGUI(){

        if (server.isRemoved()) {

            view.showMessage("\n" + thread.getUsername() + "(" + server.getServerSocket().getInetAddress().getHostAddress() + ") is offline");

        }

        if(thread.isOnline()) {

            view.showMessage("\n" + thread.getUsername() + "(" + server.getSocket().getInetAddress().getHostAddress() + ") is online");
        }


    }


}
