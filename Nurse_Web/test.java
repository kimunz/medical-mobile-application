package practice.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class test {
	
	public void getList() {
		try{
			URL url = new URL("http://hope2017.esy.es/test2.php");
			URLConnection conn = url.openConnection();
			
			InputStream is = conn.getInputStream();
			Scanner scan = new Scanner(is);
			
			int line = 1;
			while (scan.hasNext()){
				String str = scan.nextLine();
				System.out.println((line++) + ":" + str);
			}
			scan.close();
		} catch (MalformedURLException e){
			System.out.println("The URL address is incorrect.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("It can't connect to the web page.");
		}
	}
}
