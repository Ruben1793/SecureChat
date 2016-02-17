/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author RubenGuillermo
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;




public class Chat extends javax.swing.JFrame {
    
    
     public Socket socket = null;
    public ServerSocket servidor = null;
    static OutputStream salida =  null;
    static InputStream entrada = null;
    public int size=0;
    public static String qa = "2426697107";
    public static String aa = "17123207";
    public static long xa;
    public static long xb;
    public  static long llaveEnviar;
    public static long key;
    JFileChooser fc = null;
    File filetobesend;
    static String pathy = " ";
    static int fileSize= 0;
    static String fileName= "";
    static double noParts = 0;
    static byte [] fil;
    static byte []  arregloBytes;
    static byte[] arregloFinal;
    static double bytesRestantes;
     static ByteArrayOutputStream out = new ByteArrayOutputStream( );
     static double cont;

    /**
     * Creates new form Chat
     */
     
     public static long diffie_hellman(long base, long exp, long q){
        
        if(exp==0){ 
            return 1;
        }
        else{
        if(exp%2 ==0){
        return diffie_hellman(base*base %q, exp/2,q);
        } else{
        return base*diffie_hellman(base,exp-1,q)%q;
        }
        }    
    }
    
    
   
public static byte[] encriptar(String llave, byte[] datos) throws Exception {
byte[] keyBytes = llave.getBytes();
SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
IvParameterSpec ivSpec = new IvParameterSpec( new byte[8]);
Cipher cipher = null;
cipher = Cipher.getInstance("DES/CBC/NoPadding");
cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
return cipher.doFinal(datos);
}

public static byte[] decriptar(String llave, byte[] datos) throws Exception {
byte[] keyBytes = llave.getBytes();
SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
IvParameterSpec ivSpec = new IvParameterSpec( new byte[8]);
Cipher cipher = null;
cipher = Cipher.getInstance("DES/CBC/NoPadding");
cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
return cipher.doFinal(datos);
}
    
public static void splitFile (String x) throws Exception// Convierte el file en un arreglo de bytes
	{
		File file = new File(x);
		RandomAccessFile f = new RandomAccessFile(file, "r");
		byte[] b = new byte[(int)f.length()];
		f.read(b);
		arregloBytes=b;
		
		System.out.println("Path:: "+x);
		
		fileSize=b.length;
		System.out.println("fileSize::"+fileSize);
		fil=b;
		
		fileName=file.getName();
		System.out.println("fileName:: "+fileName);	
		
		double p = fileSize/236f;
                bytesRestantes = fileSize%236;
                
		noParts = Math.ceil(p);
		
		System.out.println("noParts:: "+noParts);
		
		
	} 
     public static byte[] getArrayPart (int parte)
	{
		
		byte[] arregloCompleto = new byte[236];
		byte [] lastArray = new byte[(int) bytesRestantes];
		int c=0;
		
		if(parte>noParts)
		{
			System.out.println("Estas poniendo mas partes de las que el arreglo se puede dividir");
		}
		
		
		if (parte==noParts)
		{
			for (int k = 0; k<bytesRestantes;k++)
			{
				lastArray[k]=arregloBytes[((parte-1)*236) + k];
				//System.out.println(lastArray[k]);
				
			}
			
			return lastArray;
			
		}
		else
		{
			for (int k =(236*(parte-1)); k<(236*parte); k++)
			{
				arregloCompleto[c]=arregloBytes[k]; // aqui esta marcando excepcion array out of bounds -236
				//System.out.println((c+1) + " " + arregloCompleto[c]);
				c++;	
                                
                                
			}
		}
		return arregloCompleto;

		
	}

     
     public static void byteToFile (byte[] array,String filename) throws Exception
	{
		FileOutputStream fos = new FileOutputStream(filename);
		fos.write(array);
		fos.close();
	}



    
    class Client implements Runnable{
    private Socket client;
    private JTextArea textArea;
    InputStream entrada;
    OutputStream salida;

    Client(Socket client, JTextArea textArea, InputStream in, OutputStream out) {
        this.client = client;
        this.textArea = textArea;
        this.entrada = in;
        this.salida = out;
        
    }

    @Override
    public void run() {

        byte [] paquete = new byte[256];
        byte [] bytes3;
        
        CMessage m4 = new CMessage();
        try {
          
            Chat.entrada = client.getInputStream();
            Chat.salida = client.getOutputStream();
            
            this.textArea.append("Coneccion recibida:: " + client.getInetAddress().getHostAddress() + "\n");
        } catch (IOException e) {
            this.textArea.append("entrada y salida fallaron \n");
            return;
        }

        while (true) {
           try {
                   for(int buffer=0; buffer<256; buffer++){     
                   paquete[buffer] = (byte)Chat.entrada.read();
                    }
               
               
                if(paquete[10]==(byte)2){
                   StringBuilder caracter1 = new StringBuilder();
                    int t;
                    for(int i=20;i<20+paquete[9];i++){
                     t = paquete[i];
                    caracter1.append(new Character((char) t ).toString());
                    }
                   
                    String renglon = caracter1.toString();  
                    
                    String [] variables = renglon.split(",");
                    String sq = variables[0].substring(2);
                      // System.out.println("S recibida es: "+sq);
                    String sa = variables[1].substring(2);  //aqui es donde me marca el error
                      //System.out.println("ALPHA RECIBIDA ES: " +sa);
                    String sy = variables[2].substring(2);
                       // System.out.println(sy);
                    long randomX = Math.round(Math.random()*100);
                    Chat.xb = randomX;
                    long llave = diffie_hellman(Long.valueOf(sy),Chat.xb,Long.valueOf(sq)); 
                   // System.out.println("primera llave antes de if: " + llave);
                    
                    if (llave<10000000){
			String num = String.format(String.format("%08d", llave));
			long llavec= Long.valueOf(num);
                        Chat.key = llavec;
                        //key = llavec;
                        //System.out.println("Chat.key es igual a" +Chat.key);
                    }
		
		if (llave>99999999){
			String num = Long.toString(llave);
                        long llaveC = Long.valueOf(num.substring(0,8));
                        Chat.key = llaveC;
                        //key = llaveC;
                        //System.out.println("Chat.key es igual a " + Chat.key);
		}
                
                   // System.out.println("primera llave es: "+ Chat.key);
                    
                  /// Chat.llaveEnviar = Chat.key; // aqui cambie otra cosa
                    //llaveEnviar = key;
                    
                    System.out.println(" llave Despues de asignar a variable static: " + Chat.key); // agregue chat
                  
                     long a = Long.parseLong(sa);
                     long q= Long.parseLong(sq);
                    // long x = Math.round(Math.random()*100);
                      
                     
                     long diffie = diffie_hellman(a,xb,q);
                     
                     String y = Long.toString(diffie);
                    //System.out.println("Segunda Y: " + y);

                     String mensaje = "q="+sq+","+"a="+sa+","+"y="+y;
                    // System.out.println("Variables para enviar: " + mensaje);
                     m4.setDatos(m4.StringtoASCII(mensaje));
                     m4.setFunction((byte)3);
                     
                     bytes3 = m4.getMessage();
                    // System.out.println("bytes3 es: "+bytes3);
                    
                      for(int buffer=0; buffer<256; buffer++){
                          if(Chat.salida == null){System.out.println("salida es null");}
                         // if(bytes3 == null){System.out.println("Bytes3 es igual a null");}
                          //if(buffer == null){System.out.println("");}
                          
                      Chat.salida.write(bytes3[buffer]); //aqui marca error null      
                    }
                      Chat.salida.flush();
                    
               }
               
                if (paquete[10] == (byte)3){ 
                     StringBuilder caracter = new StringBuilder();   
                     int t;
                     for(int i=20; i<20+paquete[9];i++){
                     t=paquete[i];
                     caracter.append(new Character((char) t ).toString());
                     }
                    
                     String enunciado = caracter.toString();
                     String [] variables = enunciado.split(",");
                     String sq = variables[0].substring(2);
                     String sa = variables[1].substring(2);
                     String sy = variables[2].substring(2);
                     long randomX = Math.round(Math.random()*100);
                     xa = randomX;
                     long llave = diffie_hellman(Long.valueOf(sy),xa,Long.valueOf(sq));
                     //System.out.println("segunda llave antes de if: "+llave);
                     
                     if (llave<10000000){
			String num = String.format(String.format("%08d", llave));
			long llaveC = Long.valueOf(num);
                       Chat.key = llaveC;
                        //key = llaveC;
                        //if(Chat.key == 0){System.out.println("llave esta en null aqui");}
                    }
		
                    if (llave>99999999){
			String num = Long.toString(llave);
                        long llaveC = Long.valueOf(num.substring(0,8));
                        Chat.key = llaveC;
                        
                    }
                   Chat.llaveEnviar = Chat.key; // aqui agregue chat
                    //llaveEnviar = key;
                  
                    System.out.println("Segunda llave:" + Chat.key);
                    //System.out.println("Segunda llave despues e asignar" + Chat.llaveEnviar);
                     }
                
                
              
                
                
               //paquete = decriptar(String.valueOf(Chat.key),paquete); 
                 paquete = decriptar(String.valueOf(Chat.key),paquete);
                
                 
                 if(paquete[10] == (byte)4){
                    StringBuilder caracter = new StringBuilder();
                    int t;
                    for(int i=20;i<20+paquete[9];i++){
                     t = paquete[i];
                    caracter.append(new Character((char) t ).toString());
                    }
                   
                    String renglon = caracter.toString();   
                        System.out.println("ESTO ES LA METADATA:: " + renglon );
                    String [] variables = renglon.split(",");
                    Chat.fileName = variables[0];
                        System.out.println("fileName recibida: "+ fileName);
                    Chat.fileSize = Integer.valueOf(variables[1]);
                        System.out.println("fileSize recibida: "+ fileSize);
                    Chat.noParts = Double.valueOf(variables[2]);
                        System.out.println("noParts recibida: " + noParts);
                      
                    }
                    
                    if(paquete[10] == (byte)5){
                        Chat.arregloFinal = new byte[Chat.fileSize];
                        byte[] mensaje = Arrays.copyOfRange(paquete, 20, paquete.length);
                        Chat.out.write(mensaje);
                        Chat.cont++;
                        if(Chat.cont == Chat.noParts){
                            Chat.arregloFinal = Chat.out.toByteArray();
                            byteToFile(Chat.arregloFinal,Chat.fileName);
                        }
                        
                    }
                 
              
                if(paquete[10]==(byte)1){
               
               StringBuilder caracter1 = new StringBuilder();
               int t;
               for(int i=20;i<20+paquete[9];i++){
                 t = paquete[i];
                 caracter1.append(new Character((char) t ).toString());
                 caracter1.trimToSize();}
               
                 String ip = client.getInetAddress().getHostAddress();
  
                this.textArea.append( ip + " envio:: " + caracter1 + "\n");
               }
               
                
                    
            
               
               
               
           } catch (IOException ex) {
               Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
           } catch (Exception ex) {
                Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
  }
     
     
     
     
     
     
    public Chat() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ip = new javax.swing.JTextField();
        puerto = new javax.swing.JTextField();
        llave = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        mensajes = new javax.swing.JTextArea();
        message = new javax.swing.JTextField();
        listen = new javax.swing.JButton();
        connect = new javax.swing.JButton();
        send = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        buscar = new javax.swing.JButton();
        listenport = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 153, 153));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFocusTraversalPolicyProvider(true);
        setMinimumSize(new java.awt.Dimension(500, 350));
        setPreferredSize(new java.awt.Dimension(520, 400));
        getContentPane().setLayout(null);

        ip.setMinimumSize(new java.awt.Dimension(8, 20));
        getContentPane().add(ip);
        ip.setBounds(70, 60, 80, 30);

        puerto.setMinimumSize(new java.awt.Dimension(8, 20));
        getContentPane().add(puerto);
        puerto.setBounds(70, 100, 80, 30);
        getContentPane().add(llave);
        llave.setBounds(200, 10, 90, 30);

        jLabel1.setText("ListenPort");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 30, 60, 14);

        jLabel2.setText("IP");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(30, 70, 20, 14);

        jLabel3.setText("Port");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(30, 110, 30, 14);

        jLabel4.setText("Key");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(170, 20, 30, 14);

        mensajes.setColumns(20);
        mensajes.setRows(5);
        jScrollPane1.setViewportView(mensajes);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(180, 130, 250, 180);
        getContentPane().add(message);
        message.setBounds(10, 150, 150, 30);

        listen.setText("Listen To");
        listen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listenActionPerformed(evt);
            }
        });
        getContentPane().add(listen);
        listen.setBounds(190, 50, 80, 23);

        connect.setText("Connect");
        connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectActionPerformed(evt);
            }
        });
        getContentPane().add(connect);
        connect.setBounds(320, 50, 80, 23);

        send.setText("Send");
        send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendActionPerformed(evt);
            }
        });
        getContentPane().add(send);
        send.setBounds(10, 200, 57, 23);

        jCheckBox1.setText("Error");
        getContentPane().add(jCheckBox1);
        jCheckBox1.setBounds(90, 210, 49, 23);

        jLabel5.setText("Messages  ");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(270, 100, 70, 20);

        buscar.setText("browse");
        buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarActionPerformed(evt);
            }
        });
        getContentPane().add(buscar);
        buscar.setBounds(10, 250, 67, 23);
        getContentPane().add(listenport);
        listenport.setBounds(70, 20, 80, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void listenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listenActionPerformed
        try {
            int port = Integer.parseInt(listenport.getText());
            servidor = new ServerSocket(port);
            mensajes.append("Escuchando al puerto:: " + port + "\n");
        } catch (IOException ex) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
            mensajes.append("No se pudo escuchar al puerto\n");
        }
        
        new Thread(new Runnable(){
            @Override
            public void run(){
                Client c;
                
                try {
                    socket = servidor.accept();
                    c = new Client(socket,mensajes,entrada,salida);
                    Thread t = new Thread(c);
                    t.start();
                    salida = c.salida;
                } catch (IOException ex) {
                    Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
                    mensajes.append("fallo al aceptar la coneccion\n");
                }
            } 
        }
        ).start();
    }//GEN-LAST:event_listenActionPerformed

    private void connectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectActionPerformed
         new Thread(new Runnable(){
         
          byte[] paquete = new byte[256];
          CMessage m = new CMessage(); 
          CMessage m2 = new CMessage();
          CMessage m3 = new CMessage();
          
         @Override
         public void run(){
             try {
                 int port = Integer.parseInt(puerto.getText());
                 socket = new Socket(ip.getText(),port);
               
                 salida = socket.getOutputStream();
                 entrada = socket.getInputStream();
         
                 mensajes.append("Conectado al puerto :: " + puerto.getText() + "\n");
                 
                     long a = Long.parseLong(aa);
                     long q = Long.parseLong(qa);
                     long x = Math.round(Math.random()*100);
                     xa = x;
                     long diffie = diffie_hellman(a,xa,q);
                     //System.out.println("primer Y: ");
                     
                     String y =Long.toString(diffie);
                     System.out.println(y);

                     String s = "q="+qa+","+"a="+aa+","+"y="+y;
                     //System.out.println("String s es " +s);
                     m2.setDatos(m2.StringtoASCII(s));
                     m2.setFunction((byte)2);
                     
                     byte[] bytes = m2.getMessage();
                     
                     
                      for(int buffer=0; buffer <256; buffer++){
                    salida.write(bytes[buffer]);
                    
                    }
                      salida.flush();
                 
                 
                 while(true){ 
                        
                    for(int buffer=0; buffer<256;buffer++){
                    paquete[buffer] = (byte)entrada.read();
                    }
                       
                    
                    if(paquete[10]==(byte)2){
                    StringBuilder caracter1 = new StringBuilder();
                    int t;
                    for(int i=20;i<20+paquete[9];i++){
                     t = paquete[i];
                    caracter1.append(new Character((char) t ).toString());
                    }
                   
                    String renglon = caracter1.toString();       
                    String [] variables = renglon.split(",");
                    String sq = variables[0].substring(2);
                    String sa = variables[1].substring(2);
                    String sy = variables[2].substring(2);
                    
                    long randomX = Math.round(Math.random());
                    xb = randomX;
                    
                    long llave=diffie_hellman(Long.valueOf(sy),xb,Long.valueOf(sq)); 
                        //System.out.println("primera llave antes de if: " + llave);
                    
                    if (llave<10000000){
			String num = String.format(String.format("%08d", llave));
			long llaveC = Long.valueOf(num);
                        key = llaveC;
                    }
		
		if (llave>99999999){
			String num = Long.toString(llave);
                        long llaveC = Long.valueOf(num.substring(0,8));
                        key = llaveC;
		}
                       // System.out.println("primera llave antes de if: " + String.valueOf(key));
                        
                        //llaveEnviar = key;
                    
                     long alpha = Long.parseLong(sa);
                     long qaa= Long.parseLong(sq);
                     long xaa = Math.round(Math.random()*10000);
                      xa=xaa;
                     
                     long diffiehellman = diffie_hellman(alpha,xa,qaa);
                        //System.out.println("segunda Y: " + diffiehellman);
                     String ya = Long.toString(diffiehellman);
//                   
                     String yb = "q="+sq+","+"a="+sa+","+"y="+ya;
                     m3.setDatos(m3.StringtoASCII(yb));
                     m3.setFunction((byte)3);
                     
                     byte[] mensaje = m3.getMessage();
                     
                      for(int buffer=0; buffer <256; buffer++){
                    salida.write(mensaje[buffer]);
                    }
                      salida.flush();
                    
                    }
                    
                    if (paquete[10]==(byte)3){
                     StringBuilder caracter = new StringBuilder();   
                     int t;
                     for(int i=20; i<20+paquete[9];i++){
                     t=paquete[i];
                     caracter.append(new Character((char) t ).toString());
                      }
                    
                     String enunciado = caracter.toString();
                     String [] variables = enunciado.split(",");
                     String sq = variables[0].substring(2);
                        //System.out.println("sq = "+sq);
                    // String sa = variables[1];
                     String sy = variables[2].substring(2);
                       //System.out.println("sy = " + sy);
                     
                     //long randomX = Math.round(Math.random()*100);
                     
                     long llave = diffie_hellman(Long.valueOf(sy),xa,Long.valueOf(sq));
                       // System.out.println("segunda llave antes de if : "+ llave);
                     
                     if (llave<10000000){
			String num = String.format(String.format("%08d", llave));
			long llaveC = Long.valueOf(num);
                        key= llaveC;
                        
                    }
		
                    if (llave>99999999){
			String num = Long.toString(llave);
                        long llaveC = Long.valueOf(num.substring(0,8));
                        key = llaveC;   
                    }
                   
                     //llaveEnviar = key;
                        //System.out.println("segunda llave: " +String.valueOf(key)); // aqui puse llaveEnviar
                }
                    
                    
                   
                    //System.out.println("Esta es mi llave Global desencriptar: "+ String.valueOf(key) ); // aqui se queda al enviar archivo
                   // se repite constantemente esta parte al enviar archivo
                    
                    paquete = decriptar(String.valueOf(key),paquete); //null
                    
                    if(paquete[10] == (byte)4){
                    StringBuilder caracter = new StringBuilder();
                    int t;
                    for(int i=20;i<20+paquete[9];i++){
                     t = paquete[i];
                    caracter.append(new Character((char) t ).toString());
                    }
                   
                    String renglon = caracter.toString();   
                        System.out.println("ESTO ES LA METADATA RECIBIDA:: " + renglon );
                    String [] variables = renglon.split(",");
                    fileName = variables[0];
                        System.out.println("fileName recibida: "+ fileName);
                    fileSize = Integer.valueOf(variables[1]);
                        System.out.println("fileSize recibida: "+ fileSize);
                    noParts = Double.valueOf(variables[2]);
                        System.out.println("noParts recibida: " + noParts);
                    //String partes = variables[2];
                   // fileSize = Integer.parseInt(tamano);
                   // noParts = Integer.parseInt(partes);    
                    }
                    
                    if(paquete[10] == (byte)5){
                        byte b;
                        arregloFinal = new byte[fileSize];
                        byte[] mensaje = Arrays.copyOfRange(paquete, 20, paquete.length);
                        out.write(mensaje);
                        cont++;
                        if(cont == noParts){
                            arregloFinal = out.toByteArray();
                            byteToFile(arregloFinal,fileName);
                        }
                        
                    }
                     
                    if(paquete[10] == 1){
               
                    StringBuilder caracter2 = new StringBuilder();
                    int t;
                    for(int i=20;i<20+paquete[9];i++){
                         t = paquete[i];
                        caracter2.append(new Character((char) t ).toString());
                        caracter2.trimToSize();
                    }
                     
                     String ip = socket.getInetAddress().getHostAddress(); 
                    mensajes.append(ip + " envio:: " + caracter2+ "\n");
                    
                    }
                    
                    
                    
                    
                    
                 }
                } catch (UnknownHostException e) {
                    mensajes.append("Host Desconocido \n");
                } catch (IOException e) {
                    mensajes.append("No hay Input/Output \n");
                } catch (Exception ex) {
                  Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
              }
            }
        } ).start();
    }//GEN-LAST:event_connectActionPerformed

    private void sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendActionPerformed
        byte[] bytes;
        try {
            CMessage m = new CMessage();
          
            m.setDatos(m.StringtoASCII(this.message.getText()));
            m.setFunction((byte)1);
            
            bytes = m.getMessage();
           
            System.out.println("esta es la llave que uso al encriptar: " + key);
            byte[] paquete = encriptar(String.valueOf(key),bytes); //aqui me sale un error 
            
            
            for(int buffer=0; buffer <256; buffer++){
            salida.write(paquete[buffer]);
            }
            
         
            salida.flush();
            
        } catch (IOException ex) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
        }
        
            mensajes.append(" Usted envio:: " + message.getText() + "\n");
            message.setText("");
    }//GEN-LAST:event_sendActionPerformed

    private void buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarActionPerformed
         try {
             browse();
         } catch (Exception ex) {
             Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
         }
    }//GEN-LAST:event_buscarActionPerformed

    
    private void browse() throws Exception {   
    	int x= fc.showOpenDialog(null);
        CMessage m = new CMessage();
        CMessage men = new CMessage();
    	if (x == JFileChooser.APPROVE_OPTION) {
    		
    		filetobesend = fc.getSelectedFile();
            
    		 pathy = filetobesend.getAbsolutePath();
    		splitFile(pathy);
                 String metadata = fileName+","+String.valueOf(fileSize)+","+String.valueOf(noParts);
                 System.out.println("METADATA ANTES DE ENVIAR:: " + metadata);
                 m.setDatos(m.StringtoASCII(metadata));
                 m.setFunction((byte)4);
                 byte[] bytes = m.getMessage();
                 byte[] paquete = encriptar(String.valueOf(key), bytes); 
                for(int buffer=0; buffer <256; buffer++){
                    salida.write(paquete[buffer]);
                }
                salida.flush();
                //byte[]parte5=getArrayPart(5);
                for(int i=1; i<=noParts; i++){
                   
                    byte[] mensaje = getArrayPart(i);
//                    mensaje = encriptar(String.valueOf(key),mensaje);
                    men.setDatos(mensaje);
                    men.setFunction((byte)5);
                    //byte[] enviar = men.getMessage();
                    byte []enviar = encriptar(String.valueOf(key), men.getMessage());
                    for(int buffer=0; buffer <256; buffer++){
                    salida.write(enviar[buffer]);
                    }
//                    salida.write(men.getMessage());
                    salida.flush();
                    
                    
                    
                }
                
    	}
		
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Chat().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buscar;
    private javax.swing.JButton connect;
    private javax.swing.JTextField ip;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton listen;
    private javax.swing.JTextField listenport;
    private javax.swing.JTextField llave;
    private javax.swing.JTextArea mensajes;
    private javax.swing.JTextField message;
    private javax.swing.JTextField puerto;
    private javax.swing.JButton send;
    // End of variables declaration//GEN-END:variables
}
