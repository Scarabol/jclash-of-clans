package coc.protocol;

public class MessageManager {

    Stream recv = new InboundStream();
    Stream send = new OutboundStream();

    public void recv(byte[] dataFromSerer) {
        recv(dataFromSerer, dataFromSerer.length);
    }

    public void recv(byte[] dataFromServer, int lengh) {
        recv.update(dataFromServer, lengh);
    }

    public void send(byte[] dataToServer) {
        send(dataToServer, dataToServer.length);
    }

    public void send(byte[] dataToServer, int lengh) {
        send.update(dataToServer, lengh);
    }

    public boolean init() {
        return true;
    }

    public void run() {
        recv.queue();
        send.queue();
    }

}
