<% request.setCharacterEncoding("utf-8"); %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, practice.test.*, java.text.SimpleDateFormat, java.util.Calendar"%>
   
<jsp:useBean id="dao" class="practice.test.DAO"></jsp:useBean>
<jsp:useBean id="patient" class="practice.test.patient"></jsp:useBean>
<jsp:setProperty property="*" name="patient"></jsp:setProperty>


<%
	switch(request.getParameter("action")) {
	
	case "login_req" :
		response.sendRedirect("login.jsp");
		break;
		
	case "login" :
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		try {
			if (dao.id_check(id, pw)) {
				response.sendRedirect("control.jsp?action=pa_list_req&value=down_1");
			}
			else response.sendRedirect("login_error.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		break;
		
	case "pa_list_req" :
		try {
			String name = request.getParameter("name");
			String room = request.getParameter("room");
			
			if(name != null || room != null) {
				ArrayList<patient> pa_list_ser = (ArrayList<patient>) dao.getPaList_search(room, name);
				request.setAttribute("list", pa_list_ser);
				pageContext.forward("pa_list.jsp");
			}
			else {
				String value = request.getParameter("value");
				
				ArrayList<patient> pa_list = (ArrayList<patient>) dao.getPaList(value);
				request.setAttribute("list", pa_list);
				
				pageContext.forward("pa_list.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		break;
		
	case "pa_rec_req" :
		String name = request.getParameter("p_name");
		id = request.getParameter("p_id");
		String sel = request.getParameter("sel");
		String p_date = request.getParameter("p_date");
		String unit = "";
		
		if(sel != null) { 
			if(sel.equals("소변량") || sel.equals("수분섭취량")) unit = "mL";
			if(sel.equals("배설량")) unit = "회";
		}

		if(sel != null) {
			ArrayList<patientConditionDate> date_list = (ArrayList<patientConditionDate>) dao.getPatientConditionDate(id, sel);
			request.setAttribute("date_list", date_list);
		}
		
		ArrayList<patientCondition> pa_con_list = (ArrayList<patientCondition>) dao.getPatientCondition(id, sel, p_date);
		request.setAttribute("pa_con_list", pa_con_list);
		
		request.setAttribute("p_name", name);
		request.setAttribute("p_id", id);
		request.setAttribute("sel", sel);
		request.setAttribute("unit", unit);
		pageContext.forward("pa_rec.jsp");
		break;
	
	case "emer_req" :
		String str = "";
		ArrayList<emergency> emer_list = (ArrayList<emergency>) dao.getEmergency();
		for(emergency em : emer_list) {
			str += "병실 : "+em.getP_room()+", 이름 : "+em.getP_name()+", 병명 : "+em.getDisease()+"\n";
		}
		System.out.println(str);
		request.setAttribute("emer_list", str);
		pageContext.forward("alert_emergency.jsp");
		break;
	
	case "med_req" :
		Calendar calendar = Calendar.getInstance();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH");
		String str_hour = dateFormat.format(calendar.getTime());
		
		dateFormat = new SimpleDateFormat("mm");	
		String str_min = dateFormat.format(calendar.getTime());
		
		int hour = Integer.parseInt(str_hour);
		int min = Integer.parseInt(str_min);
		
 		int temp = min/15;
		
		if(temp==0) str_min = "00";
		else if(temp==1) str_min = "15";
		else if(temp==2) str_min = "30";
		else str_min = "45";
		
		String med_time = str_hour+":"+str_min;
		dao.setMedTemp(med_time);
			
		//getMedTemp.php 부르기
		ArrayList<medication> med_temp = (ArrayList<medication>) dao.getMedTemp();
		
		request.setAttribute("med_list", med_temp);
		request.setAttribute("med_time", med_time);
		pageContext.forward("medication.jsp");
		
		break;
		
	case "med_admin_req" :
		String p_id = request.getParameter("sel_id");
		String text = request.getParameter("sel_text");
		ArrayList<patient> pa_list = (ArrayList<patient>) dao.getPaList("down_1");
		System.out.println("pid는"+p_id);
		if(p_id != null) {
			ArrayList<medication> pa_med_list = (ArrayList<medication>) dao.getMedTime(p_id);
			request.setAttribute("pa_med_list", pa_med_list);
		}
		request.setAttribute("sel_text", text);
		request.setAttribute("pa_list", pa_list);
		pageContext.forward("med_admin.jsp");
		break;
		
	case "med_insert" :
		p_id = request.getParameter("sel_id");
		str_hour = request.getParameter("sel_hour");
		str_min = request.getParameter("sel_min");
		text = request.getParameter("sel_text");
		
		hour = Integer.parseInt(str_hour);
		min = Integer.parseInt(str_min);
		
 		if(hour/10 == 0) str_hour = "0"+str_hour;
 		if(min/10 == 0) str_min = "0"+str_min;
 		
		dao.setMedication(p_id, str_hour, str_min);
		
		System.out.println("잉?");
		
		request.setAttribute("sel_text", text);
		request.setAttribute("sel_id", p_id);
		pageContext.forward("control.jsp?action=med_admin_req");
		break;
		
	case "med_del" :
		p_id = request.getParameter("sel_id");
		med_time = request.getParameter("sel_time");
		
		dao.delMedication(p_id, med_time);
		
		request.setAttribute("sel_id", p_id);
		pageContext.forward("control.jsp?action=med_admin_req");
		break;
		 
	case "forbidfood_admin_req" :
		p_id = request.getParameter("sel_id");
		pa_list = (ArrayList<patient>) dao.getPaList("down_1");
		
		//if(p_id != null) {
			//ArrayList<medication> pa_med_list = (ArrayList<medication>) dao.getMedication(p_id);
			//request.setAttribute("pa_med_list", pa_med_list);
		//}
		request.setAttribute("pa_list", pa_list);
		pageContext.forward("forbidfood_admin.jsp");
		break;
		
	case "pa_admin_req" :
		id = request.getParameter("p_id");
		String re = request.getParameter("re");
		
		System.out.println("결과"+re);
		pa_list = (ArrayList<patient>) dao.getPa_search(id);
		ArrayList<doctor> d_list = (ArrayList<doctor>) dao.getDoctor();
		
		request.setAttribute("re", re);
		request.setAttribute("p_id", id);
		request.setAttribute("d_list", d_list);
		request.setAttribute("pa_list", pa_list);
		pageContext.forward("pa_admin.jsp");
		break;
		
	case "pa_update" :
		p_id = request.getParameter("p_id");
		String d_id = request.getParameter("sel_did");
		String forbid_food = request.getParameter("forbid_food");
		String p_room = request.getParameter("p_room");
		
		System.out.println("did는!"+d_id);
		
		re = dao.pa_update(p_id, d_id, p_room, forbid_food);

		request.setAttribute("p_id", p_id);
		pageContext.forward("control.jsp?action=pa_admin_req&re="+re);
		break;
		
	case "pa_del" :
		p_id = request.getParameter("p_id");
		d_id = request.getParameter("del_d_id");
		
		dao.pa_del(p_id, d_id);
		
		response.sendRedirect("control.jsp?action=pa_list_req&value=down_1");
		break;
		
	case "pa_insert_req" :
		calendar = Calendar.getInstance();
		dateFormat = new SimpleDateFormat("yyyy");	
		String str_year = dateFormat.format(calendar.getTime());
		
		d_list = (ArrayList<doctor>) dao.getDoctor();
		String result = request.getParameter("re");
		
		request.setAttribute("re", result);
		request.setAttribute("str_year", str_year);
		request.setAttribute("d_list", d_list);
		pageContext.forward("pa_insert.jsp");
		break;
	
	case "pa_insert" :
		p_id = request.getParameter("p_id");
		String p_name = request.getParameter("p_name");
		String gen = request.getParameter("sel_gen");
		String birth = request.getParameter("sel_birth");
		String disease = request.getParameter("disease");
		p_room = request.getParameter("p_room");
		String sel_did = request.getParameter("sel_did");
		forbid_food = request.getParameter("forbid_food");
		
		System.out.println(p_id +p_name +gen +birth +disease+ p_room +sel_did +forbid_food);
		result = dao.pa_insert(p_id, p_name, sel_did, p_room, gen, disease, birth, forbid_food);

		response.sendRedirect("control.jsp?action=pa_insert_req&re="+result);
		break;
	}
%>