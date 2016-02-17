/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author RubenGuillermo
 */
public class CMessage extends ASCP{
     public CMessage(){
        //this.setFunction((byte)1);
    }
    
   public byte[] StringtoASCII(String s){
       char [] c = s.toCharArray();
       byte b[] = new byte[c.length];
      for(int x =0; x<c.length;x++){
          b[x] = (byte)c[x];
          
      }
      return b;
   }
   
   public void setDatos(String t){
       this.StringtoASCII(t);
   }
   
}
