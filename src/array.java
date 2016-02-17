
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author RubenGuillermo
 */
public class array {
    static ByteArrayOutputStream out = new ByteArrayOutputStream( );
    static byte[] array;
    public static void main(String[] args) {
        try {
            byte arr1[] = {1,2,3,4,5,6,7,8,9,10,11};
            byte arr3 [] = {10,20,30};
            byte[] arreglo1 = Arrays.copyOfRange(arr1, 2,arr1.length );
            System.out.println(Arrays.toString(arreglo1));
            byte[] arreglo3 = Arrays.copyOfRange(arr3, 1, arr3.length);
            System.out.println(Arrays.toString(arreglo3));
            
            
            out.write( arreglo1 );
            out.write( arreglo3 );
            
            if(arreglo1 != null){
                out.write(arr1);
            }
            
            for(int i=0; i<3; i++){
            out.write(arr3);
            }
            
            array = out.toByteArray( );
            
            
            String metadata = "texto.txt,494949,8";
            String [] partes = metadata.split(",");
            String nombre = partes[0];
            String bytes = partes[1];
            String numeroparte = partes[2];
            System.out.println(nombre);
            System.out.println(bytes);
            System.out.println(numeroparte);
            
            System.out.println("");
            System.out.println(Arrays.toString(array));
            
            System.out.println("");
            byte[] arr2=new byte[arr1.length];
            System.arraycopy(arr1,0 , arr2, 0, arr1.length);
            System.out.println(Arrays.toString(arr2));
            
            if(Arrays.equals(arr1, arr2)){
                System.out.println("los arrays 1 y 2 son iguales");
            }
            else {System.out.println("no son iguales");}
            
            
        } catch (IOException ex) {
            Logger.getLogger(array.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
}
