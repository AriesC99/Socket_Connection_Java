package com.company;

// Client2 class that
// sends data and receives also

import java.io.*;
import java.net.*;

class Client {
    public static Socket s;
    public static void main(String args[]) throws Exception
    {

        // Create client socket
        s = new Socket("localhost", 888);
        System.out.println("Connection established");

        //Connection Block
        {
            ServerMessages sm = new ServerMessages();
            sm.start();

            SendMessages snm = new SendMessages();
            snm.start();

            while(s.isConnected()){}
        }

        // Terminate Application
        System.exit(0);

    }
}

class ServerMessages extends Thread{
    public void run(){
        try{
            String str1;

            // to read data coming from the server
            BufferedReader br
                    = new BufferedReader(
                    new InputStreamReader(
                            Client.s.getInputStream()));

            // receive from the server
            while(((str1 = br.readLine()) != null) && Client.s.isConnected()) {
                System.out.println(str1);
            }

            br.close();
            Client.s.close();
        }
        catch (Exception e){
            System.out.println("Server Messages Exception: " + e.getMessage());
        }
        finally {
            System.exit(0);
        }
    }
}

class SendMessages extends Thread{
    public void run(){
        try{
            String str;
            // to send data to the server
            DataOutputStream dos
                    = new DataOutputStream(
                    Client.s.getOutputStream());

            // to read data from the keyboard
            BufferedReader kb
                    = new BufferedReader(
                    new InputStreamReader(System.in));

            // repeat as long as exit
            // is not typed at client
            while ((!(str = kb.readLine()).equals("exit")) && Client.s.isConnected()) {
                // send to the server
                dos.writeBytes(str + "\n");
            }

            dos.close();
            kb.close();
            Client.s.close();
        }
        catch (Exception e){
            System.out.println("Sending Messages Exception: " + e.getMessage());
        }
        finally {
            System.exit(0);
        }
    }
}

