
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;
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
public class ur {
    
    public static byte[] hash(byte[] hashThis) {
 try {
 byte[] hash = new byte[20];
 MessageDigest md = MessageDigest.getInstance("SHA-1");
hash = md.digest(hashThis);
 return hash;
} catch (NoSuchAlgorithmException nsae) {
System.err.println("SHA-1 algorithm is not available...");
System.exit(2);
}
return null;
}
    
    public static String bytesToHexString(byte[] bytes){ 
    StringBuilder sb = new StringBuilder(); 
    for(byte b : bytes){ 
        sb.append(String.format("%02x", b&0xff));
    } 
    return sb.toString(); 
    }
    
    public static void ReadFromURL(String url) throws Exception{
        URL oracle = new URL(url);
        BufferedReader in = new BufferedReader(
        new InputStreamReader(oracle.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    
}
    
    
    public static void main(String[] args) throws UnknownHostException {
        
        Random rdm = new Random();
        String user="user20";
        String password="test";
        String nonce=String.valueOf(rdm.nextInt(1000));
        String n ="482";
        String h = password+n;
        
        String hash1= "";
        byte[] array = hash(h.getBytes());
        hash1 = bytesToHexString(array);
        System.out.println("hash: " + hash1);
        
       
        
        String port="4040";
        InetAddress addr = InetAddress.getLocalHost();
        String ip=addr.getHostAddress();
       // System.out.println(ip);
        
        
        String ejemplo = "https://web111.securesecure.co.uk/maryayi.com/messenger_service/request_service.php/request_service.php?service=CONNECT&user=user01&nonce=234&hash=8fa095625f481c909d7607218cac902262301b50&port=2015";
        
        String url = "https://web111.securesecure.com.uk/maryayi.com/messenger_service/request_service.php/request_service.php?service=CONNECT&user="+user+"&nonce="+nonce+"&hash="+hash1+"&port="+port;
        
        System.out.println(url);
        
        try {
            ReadFromURL(ejemplo);
        } catch (Exception ex) {
            Logger.getLogger(ur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
