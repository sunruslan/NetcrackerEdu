import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class PortScanner {
    private static InetAddress host;
    private static int startPort, endPort;
    public static boolean isValid (String[] args) {
        if (args.length != 3) {
            System.err.println("Usage:\n\tjava PortScanner <ipAddress> <startPort> <endPort>");
            return false;
        }
        try {
            host = InetAddress.getByName(args[0]);
            startPort = Integer.parseInt(args[1]);
            endPort = Integer.parseInt(args[2]);
        } catch (UnknownHostException e) {
            System.err.println("Incorrect ip or domain name.");
            return false;
        } catch (NumberFormatException e) {
            System.err.println("Incorrect ports format");
            return false;
        }
        return true;
    }
    public static boolean checkPort (int port) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(host, port));
            socket.close();
        } catch (Exception e){
            return false;
        }
        return true;
    }
    public static void main (String[] args) {
        if (isValid(args)) {
            System.out.println("IP/DN: " + args[0] + "\nstart: " + args[1] + "\nend: " + args[2]);
            for (int port = startPort; port <= endPort; ++port) {
                System.out.println("PORT " + port + " is " + (checkPort(port)?"opened":"closed"));
            }
        }
    }
}