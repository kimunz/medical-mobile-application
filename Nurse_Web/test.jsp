<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<% request.setCharacterEncoding("utf-8"); %>

<%@ page import="java.io.IOException" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.net.MalformedURLException" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.net.URLConnection" %>
<%@ page import="java.util.Scanner" %>

<%

	//받기
 	try{
 		int p_id, p_room, age; 
		String p_name, gen, disease, forbid_food, birth, drug;
		
		URL url = new URL("http://hope2017.esy.es/getPatient.php");
		URLConnection conn = url.openConnection();
		
		InputStream is = conn.getInputStream();
		Scanner scan = new Scanner(is);
		
		int line = 1;
		while (scan.hasNext()){
			p_id = Integer.parseInt(scan.nextLine());
			p_name = scan.nextLine();
			gen = scan.nextLine();
			p_room = Integer.parseInt(scan.nextLine());
			disease = scan.nextLine();
			age = Integer.parseInt(scan.nextLine());
			forbid_food = scan.nextLine();
			birth = scan.nextLine();
			drug = scan.nextLine();
			
			System.out.println(p_id + p_name + gen + p_room + disease + age + forbid_food + birth + drug);
		}
		scan.close();
		
	} catch (MalformedURLException e){
		System.out.println("The URL address is incorrect.");
		e.printStackTrace();
	} catch (IOException e) {
		System.out.println("It can't connect to the web page.");
	} 
	//보내기
/* 	try{
		URL url = new URL("http://hope2017.esy.es/getParameter.php?test=hello");
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
	}  */
%>