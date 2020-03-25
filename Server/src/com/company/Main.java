package com.company;

// Server2 class that
// receives data and sends data

import java.io.*;
import java.net.*;

class Server {
    public static Socket s;
    public static void main(String args[]) throws Exception
    {
        System.out.println("Waiting for Client Connection !!!");
        // Create server Socket
        ServerSocket ss = new ServerSocket(888);

        // connect it to client socket
        s = ss.accept();
        System.out.println("Connection established");

        // server executes continuously
        //Communication Block
       {
            //Starting Thread to Receive Messages

            ClientMessages cm = new ClientMessages();
            cm. start();

            //Starting Thread to send messages
            SendMessages sm = new SendMessages();
            sm.start();

           while(s.isConnected()){}

        }

        // close connection
        ss.close();

        // terminate application
        System.exit(0);
    }
}

class ClientMessages extends  Thread{
    public void run(){
        try {
            String str;

            //To Read Data from Client
            BufferedReader br
                    = new BufferedReader(
                    new InputStreamReader(
                            Server.s.getInputStream()));

            //Receive Messages till client Sends null
            while (((str = br.readLine()) != null) && Server.s.isConnected()) {
                System.out.println(str);
            }

            br.close();
            Server.s.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Client Messages Exception: " + e.getMessage());
        }
        finally {
            System.exit(0);
        }
    }
}


class SendMessages extends Thread{
    public void run(){
        String str1;
        try{
            //To send Data to client
            PrintStream ps
                    = new PrintStream(Server.s.getOutputStream());

            //To Read Data from Keyboard
            BufferedReader kb
                    = new BufferedReader(
                    new InputStreamReader(System.in));

            //Send Messages
            while((!(str1 = kb.readLine()).equals("exit")) && Server.s.isConnected()) {
                // send to client
                ps.println(str1);
            }

            ps.close();
            kb.close();
            Server.s.close();
        }
        catch(Exception e){
            System.out.println("Sending Messages Exception: " + e.getMessage());
        }
        finally {
            System.exit(0);
        }
    }
}


