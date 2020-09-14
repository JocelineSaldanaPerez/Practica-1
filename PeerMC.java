/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peer;

/**
 *
 * @author jocel
 */
import java.io.IOException;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import javax.swing.JTextArea;
/*
 * @author Larios
 */
public class PeerMC implements Runnable{
    private MulticastSocket socket;
    private InetAddress host;
    private byte [] mensaje;
    private byte [] msj_Enviado;
    private DatagramPacket paquete;
    private int port;
    private JTextArea area;
    //"230.0.0.4"
    public PeerMC(String host, int port){
        
        try {
            this.socket = new MulticastSocket(port);
            this.host = InetAddress.getByName(host);
            this.port = port;
            this.mensaje = new byte[100];
            this.msj_Enviado = new byte[100];
        } catch (UnknownHostException ex) {
        } catch (IOException ex) {
        }
    }
    public void set_Area(JTextArea area){
        this.area=area;
    }
    public void enviar_Mensaje(String msj){
        this.mensaje = msj.getBytes();
        this.paquete = new DatagramPacket(this.mensaje,this.mensaje.length,host,port);
        try {
            this.socket.send(paquete);
            System.out.println("Mensaje enviado");
        } catch (IOException ex) {
        }
    }
    
    public void run() {
        String mensaje_Recibido="";
        try {
                this.socket.joinGroup(host);
                
                while(true){
                    
                    this.paquete = new DatagramPacket(this.msj_Enviado,this.msj_Enviado.length);
                    this.msj_Enviado = new byte [100];
                    synchronized(this){
                        this.socket.receive(paquete);
                    }
                    mensaje_Recibido+= new String(paquete.getData()) + "\n";
                    //System.out.println("Recibio: "+mensaje_Recibido);
                    area.setText(mensaje_Recibido); 
                    //area.append(paquete.getData() + "\n");
                }
        } catch (IOException ex) {

        }
    }
}


