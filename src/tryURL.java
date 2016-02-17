
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
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
public class tryURL {
    
    public static void ReadURL(String url) throws Exception{
         URL oracle = new URL(url);
        BufferedReader in = new BufferedReader(
        new InputStreamReader(oracle.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }
    
    public static void main(String[] args)  {
        
        String url = "https://web111.secure-secure.co.uk/maryayi.com/messenger_service/request_service.php/request_service.php?service=CONNECT&user=user02&nonce=234&hash=8fa095625f481c909d7607218cac902262301b50&port=2015&private_ip=10.32.12.123";
        
        try {
            ReadURL(url);
        } catch (Exception ex) {
            Logger.getLogger(tryURL.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
