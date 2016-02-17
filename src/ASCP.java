/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author RubenGuillermo
 */
public class ASCP {
    
    public static final int Signature = 0;
    public static final int Version = 4;
    public static  int TamDatos = 9;
    public static final int Function = 10;
    public static final int Estado = 12;
    public static final int ID = 16;
    public static final int Datos = 20;
    public static final int MAC = 236;
    public static final int Size = 256;
    
    
    private byte message[] = new byte[Size];
    
   
    
    public ASCP(){
        message[Signature] = 65; // A
        message[Signature + 1] = 83; // S
        message[Signature + 2] = 67; // C
        message[Signature + 3] = 80; // P
        
        message[Version] = 0;
        message[Version + 1] = 1;
        
        message[Estado] = 0;
        message[Estado + 1] = 0;
        
        message[ID] = 0;
        message[ID + 1] = 0; 
  
    }
    
    
    public void setDatos(byte datos[]){
        for (int i = 0; i < datos.length; i++)
            message[Datos + i] = datos[i];
        
        Integer s = datos.length;
         
        
        message[TamDatos] = s.byteValue();
    }
    
    public void setMAC(byte datos[]){
        System.arraycopy(datos, 0, message, MAC, datos.length);
    }
    
    
    public void setFunction(byte f){
        message[Function] = f;
    }
    
    public byte[] getMessage(){
    return message;
    }
    
    public byte[] getDATA(){
    byte[] bytes = new byte[236];
     System.arraycopy(message,0,bytes,0,bytes.length);
     return bytes;
    }
     
    public byte[] getMAC(){
    byte[] bytes = new byte[20];
    System.arraycopy(message, MAC, bytes, 0, bytes.length);
    return bytes;
    }
}
