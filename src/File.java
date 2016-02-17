/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author RubenGuillermo
 */

import java.io.*;
import java.nio.*;



public class File 
{

		
	static byte [] fil;
	
	
	public static void main (String Args[]) throws Exception
	{       String nombre = "file.txt";
		splitFile();
		byteToFile(nombre);
		
	}
	
	
	public static void splitFile () throws Exception
	{
		RandomAccessFile f = new RandomAccessFile("SamplePairs.txt", "r");
		byte[] b = new byte[(int)f.length()];
		f.read(b);
		
		
		for (int k =0 ; k<b.length;k++)
		{
			//System.out.println(b[k]);
		}	
		System.out.println(b.length);
		fil=b;
	}
	
	public static void byteToFile (String nombre) throws Exception
	{
		FileOutputStream fos = new FileOutputStream(nombre);
		fos.write(fil);
		fos.close();
	}
}
