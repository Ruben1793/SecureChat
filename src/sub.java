/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author RubenGuillermo
 */
public class sub {
    public static void main(String[] args) {
        String q = "OK/192.168.43.129:4040";
        String [] h = q.split("/");
        System.out.println(h[0]);
        System.out.println(h[1]);
        System.out.println("");
        String [] partes = h[1].split(":");
        System.out.println(partes[0]);
        System.out.println(partes[1]);
    }
    
}
