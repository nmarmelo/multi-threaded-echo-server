package multithreadedechoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Steve
 */
public class ClientConnectionHandler extends Thread
{
    private Socket connection;
    private InputStream clientInput;
    private OutputStream clientOutput;
    private Scanner scanner;
    private OutputStreamWriter osw;
    
    public ClientConnectionHandler(Socket conn)
    {
        connection = conn;
        
        try
        {
        
            /*Get input and output streams from the client
              connection and attach them to proper classes 
              for reading and writing*/
            clientInput = connection.getInputStream();
            clientOutput = connection.getOutputStream();
            scanner = new Scanner(clientInput);
            osw = new OutputStreamWriter(clientOutput);
        }
        catch(IOException e)
        {
            System.out.println("Error reading/writing from/to client");
        }
            
    }
    
    public void closeConnection() throws IOException
    {
         osw.close();
         clientInput.close();
         connection.close();
    }
    
    @Override
    public void run()
    {
        
        try
        {
            osw.write("Welcome to Server\r\n");
            osw.flush();
                    
                    
            //Keep reading messages from the client
            //and echoing them back
            while( true )
            {
                String message = scanner.nextLine();
                if (!( message.equals("Exit")))
                {
                    osw.write(message + "\r\n");
                    osw.flush();
                }
                else
                {
                    //If the "Exit" keyword is read
                    //then close this client's connection
                    closeConnection();
                    break;
                }
            }
        }
        catch(IOException e)
        {
            System.out.println("Error reading/writing from/to client");
        }
    }
            
    
}
