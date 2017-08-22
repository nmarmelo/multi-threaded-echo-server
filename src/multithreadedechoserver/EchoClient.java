package multithreadedechoserver;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Steve
 */
public class EchoClient 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        InputStream serverInput = null;
        OutputStream serverOutput = null;
        Scanner scan = null;
        OutputStreamWriter osw = null;
        
        String serverAddress = JOptionPane.showInputDialog("Please"
                + " enter a Server to connect (DNS name or IP)");
        try
        {
            //Create connection to server
            Socket socket = new Socket(serverAddress, 5021);
            //Get input and output streams and attach them
            //to proper classes for reading and writing
            serverOutput = socket.getOutputStream();
            serverInput = socket.getInputStream();
            scan = new Scanner(serverInput);
            osw = new OutputStreamWriter(serverOutput);
            
            //Read welcome message from the server
            String message = scan.nextLine();
            //Display welcome message to user
            System.out.println(message);
             
            
            //Now keep getting messages from the user, send them
            //to the server and display the replies
            Scanner keyboard = new Scanner(System.in);
            
            while (true)
            {
                System.out.println("please enter a message (Exit to end)");
                message = keyboard.next();
                osw.write(message + "\r\n");
                osw.flush();
                if (message.equals("Exit"))
                    break;
                message = scan.nextLine();
                System.out.println(message);
            }
            
        }
        catch (IOException e)
        {
            System.out.println("error connecting to Server");
        }
        
    }
}