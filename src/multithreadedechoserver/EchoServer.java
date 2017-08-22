package multithreadedechoserver;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 *
 * @author Steve
 */
public class EchoServer 
{
    private ServerSocket server;
    private Socket clientConnection;
   
    private int portNumber;
        
    public EchoServer(int portNumber)
    {
        server = null;
        clientConnection = null;
        this.portNumber = portNumber;
    }
    
    public void start() throws IOException
    { 
        server = new ServerSocket(portNumber); 
    }
    
    public void acceptConnection() throws IOException
    {
        clientConnection = server.accept();
        ClientConnectionHandler cch = new ClientConnectionHandler(clientConnection);
        cch.start();
        
    }
    
    public void terminate() 
    {
        try
        {
            server.close();
        }
        catch(IOException e)
        {
            System.out.println("Error terminating server connection");
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
       
        
        int portNumber = 5021;
        EchoServer server = new EchoServer(portNumber);
        
        try
        {  
            //Make the server listen on the given port 
            server.start();
            
            while (true)
            {
                //Wait until a client connects
                server.acceptConnection();     

            }
            
        }
         catch (IOException e)
         {

             System.out.println("Unable to establish "
                        + "server connection");
         }
        finally
        {
            server.terminate();
        }
        
    }
    
}


