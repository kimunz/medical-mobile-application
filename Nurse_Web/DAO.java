package practice.test;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DAO {
	public void test() {
		String d_id = "1000";
		try {
			URL url = new URL("http://hope2017.esy.es/test.php?d_ID=" + d_id);
			URLConnection conn = url.openConnection();

			InputStream is = conn.getInputStream();
			Scanner scan = new Scanner(is);

			while (scan.hasNext()) {
				System.out.println(scan.nextLine());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<patient> getPaList(String value) {
		ArrayList<patient> list = new ArrayList<patient>();

		try {
			String d_id, d_name = "";
			URL url = new URL("http://hope2017.esy.es/getPatient.php?value=" + value);
			URLConnection conn = url.openConnection();

			InputStream is = conn.getInputStream();
			Scanner scan = new Scanner(is);

			while (scan.hasNext()) {
				patient pa = new patient();

				pa.setP_id(scan.nextLine());
				d_id = scan.nextLine();
				pa.setP_name(scan.nextLine());
				pa.setGen(scan.nextLine());
				pa.setP_room(scan.nextLine());
				pa.setDisease(scan.nextLine());
				pa.setAge(scan.nextLine());
				pa.setForbid_food(scan.nextLine());
				pa.setBirth(scan.nextLine());

				URL url2 = new URL("http://hope2017.esy.es/getDoctorName.php?d_id=" + d_id);
				URLConnection conn2 = url2.openConnection();
				InputStream is2 = conn2.getInputStream();
				Scanner scan2 = new Scanner(is2);
				while (scan2.hasNext())
					d_name = scan2.nextLine();
				pa.setD_name(d_name);

				list.add(pa);
			}
			scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<patient> getPaList_search(String room, String name) {
		ArrayList<patient> list = new ArrayList<patient>();
		try {
			String d_id, d_name = "";
			if (room.equals("")) {
				URL url = new URL("http://hope2017.esy.es/getPatient.php?value=name&name=" + name);
				URLConnection conn = url.openConnection();
				InputStream is = conn.getInputStream();
				Scanner scan = new Scanner(is);
				while (scan.hasNext()) {
					patient pa = new patient();

					pa.setP_id(scan.nextLine());
					d_id = scan.nextLine();
					pa.setD_id(d_id);
					pa.setP_name(scan.nextLine());
					pa.setGen(scan.nextLine());
					pa.setP_room(scan.nextLine());
					pa.setDisease(scan.nextLine());
					pa.setAge(scan.nextLine());
					pa.setForbid_food(scan.nextLine());
					pa.setBirth(scan.nextLine());

					URL url2 = new URL("http://hope2017.esy.es/getDoctorName.php?d_id=" + d_id);
					URLConnection conn2 = url2.openConnection();
					InputStream is2 = conn2.getInputStream();
					Scanner scan2 = new Scanner(is2);
					while (scan2.hasNext())
						d_name = scan2.nextLine();
					pa.setD_name(d_name);

					list.add(pa);
				}
				scan.close();
			} else if (name.equals("")) {
				URL url = new URL("http://hope2017.esy.es/getPatient.php?value=room&room=" + room);
				URLConnection conn = url.openConnection();

				InputStream is = conn.getInputStream();
				Scanner scan = new Scanner(is);

				while (scan.hasNext()) {
					patient pa = new patient();

					pa.setP_id(scan.nextLine());
					d_id = scan.nextLine();
					pa.setP_name(scan.nextLine());
					pa.setGen(scan.nextLine());
					pa.setP_room(scan.nextLine());
					pa.setDisease(scan.nextLine());
					pa.setAge(scan.nextLine());
					pa.setForbid_food(scan.nextLine());
					pa.setBirth(scan.nextLine());

					URL url2 = new URL("http://hope2017.esy.es/getDoctorName.php?d_id=" + d_id);
					URLConnection conn2 = url2.openConnection();
					InputStream is2 = conn2.getInputStream();
					Scanner scan2 = new Scanner(is2);
					while (scan2.hasNext())
						d_name = scan2.nextLine();
					pa.setD_name(d_name);

					list.add(pa);
				}
				scan.close();
			} else {
				URL url = new URL("http://hope2017.esy.es/getPatient.php?value=search&room=" + room + "&name=" + name);
				URLConnection conn = url.openConnection();

				InputStream is = conn.getInputStream();
				Scanner scan = new Scanner(is);

				while (scan.hasNext()) {
					patient pa = new patient();

					pa.setP_id(scan.nextLine());
					d_id = scan.nextLine();
					pa.setP_name(scan.nextLine());
					pa.setGen(scan.nextLine());
					pa.setP_room(scan.nextLine());
					pa.setDisease(scan.nextLine());
					pa.setAge(scan.nextLine());
					pa.setForbid_food(scan.nextLine());
					pa.setBirth(scan.nextLine());

					URL url2 = new URL("http://hope2017.esy.es/getDoctorName.php?d_id=" + d_id);
					URLConnection conn2 = url2.openConnection();
					InputStream is2 = conn2.getInputStream();
					Scanner scan2 = new Scanner(is2);
					while (scan2.hasNext())
						d_name = scan2.nextLine();
					pa.setD_name(d_name);

					list.add(pa);
				}
				scan.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<patient> getPa_search(String p_id) {
		ArrayList<patient> list = new ArrayList<patient>();
		try {
			URL url = new URL("http://hope2017.esy.es/getPatient_admin.php?p_id=" + p_id);
			URLConnection conn = url.openConnection();
			InputStream is = conn.getInputStream();
			Scanner scan = new Scanner(is);
			while (scan.hasNext()) {
				patient pa = new patient();

				pa.setP_id(scan.nextLine());
				pa.setD_id(scan.nextLine());
				pa.setP_name(scan.nextLine());
				pa.setGen(scan.nextLine());
				pa.setP_room(scan.nextLine());
				pa.setDisease(scan.nextLine());
				pa.setAge(scan.nextLine());
				pa.setForbid_food(scan.nextLine());
				pa.setBirth(scan.nextLine());

				list.add(pa);
			}
			scan.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean id_check(String id, String pw) {
		boolean result = false;

		try {
			URL url = new URL("http://hope2017.esy.es/n_login.php?id=" + id + "&pw=" + pw);
			URLConnection conn = url.openConnection();

			InputStream is = conn.getInputStream();
			Scanner scan = new Scanner(is);

			while (scan.hasNext()) {
				String text = scan.nextLine();
				if (text.equals("Success"))
					result = true;
			}
			scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<patientConditionDate> getPatientConditionDate(String id, String carte) {
		ArrayList<patientConditionDate> list = new ArrayList<patientConditionDate>();

		try {
			URL url = new URL("http://hope2017.esy.es/getPatientConditionDate.php?id=" + id + "&carte=" + carte);
			URLConnection conn = url.openConnection();

			InputStream is = conn.getInputStream();
			Scanner scan = new Scanner(is);

			while (scan.hasNext()) {
				patientConditionDate pa = new patientConditionDate();

				pa.setReg_date(scan.nextLine());

				list.add(pa);

				// String str;
				// str = scan.nextLine();
				// System.out.println(str);
			}
			scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<patientCondition> getPatientCondition(String id, String carte, String p_date) {
		ArrayList<patientCondition> list = new ArrayList<patientCondition>();

		try {
			URL url = new URL("http://hope2017.esy.es/getPatientCondition.php?id=" + id + "&carte=" + carte
					+ "&reg_date=" + p_date);
			URLConnection conn = url.openConnection();

			InputStream is = conn.getInputStream();
			Scanner scan = new Scanner(is);

			while (scan.hasNext()) {
				patientCondition pa = new patientCondition();

				pa.setReg_date(scan.nextLine());
				pa.setReg_time(scan.nextLine());
				pa.setCarte(scan.nextLine());
				pa.setVolume(scan.nextLine());

				list.add(pa);
			}
			scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public int getEmergencyCount() {
		int count = 0;

		try {
			URL url = new URL("http://hope2017.esy.es/getEmergencyCount.php");
			URLConnection conn = url.openConnection();

			InputStream is = conn.getInputStream();
			Scanner scan = new Scanner(is);

			while (scan.hasNext()) {
				count = Integer.parseInt((scan.nextLine()));
			}
			scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public List<emergency> getEmergency() {
		ArrayList<emergency> list = new ArrayList<emergency>();
		try {
			URL url = new URL("http://hope2017.esy.es/getEmergency.php");
			URLConnection conn = url.openConnection();

			InputStream is = conn.getInputStream();
			Scanner scan = new Scanner(is);

			while (scan.hasNext()) {
				emergency pa = new emergency();

				pa.setP_name(scan.nextLine());
				pa.setP_room(scan.nextLine());
				pa.setDisease(scan.nextLine());

				list.add(pa);
			}
			scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<medication> getMedTime(String p_id) {
		ArrayList<medication> list = new ArrayList<medication>();

		try {
			URL url = new URL("http://hope2017.esy.es/getMedTime.php?p_id=" + p_id);
			URLConnection conn = url.openConnection();

			InputStream is = conn.getInputStream();
			Scanner scan = new Scanner(is);

			while (scan.hasNext()) {
				medication pa = new medication();

				pa.setP_id(scan.nextLine());
				pa.setP_name(scan.nextLine());
				pa.setP_room(scan.nextLine());
				pa.setDisease(scan.nextLine());
				pa.setMed_time(scan.nextLine());

				list.add(pa);
			}
			scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void delMedication(String p_id, String med_time) {
		try {
			URL url = new URL(
					"http://hope2017.esy.es/delMedication.php?p_id=" + p_id + "&med_time=" + med_time);
			URLConnection conn = url.openConnection();

			InputStream is = conn.getInputStream();
			Scanner scan = new Scanner(is);

			while (scan.hasNext()) {
				scan.nextLine();
			}
			scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setMedication(String p_id, String hour, String min) {
		try {
			URL url = new URL(
					"http://hope2017.esy.es/setMedication.php?p_id=" + p_id + "&hour=" + hour + "&min=" + min);
			URLConnection conn = url.openConnection();

			InputStream is = conn.getInputStream();
			Scanner scan = new Scanner(is);

			while (scan.hasNext()) {
				System.out.println(scan.nextLine());
			}
			scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setMedTemp(String med_time) {

		try {
			URL url = new URL("http://hope2017.esy.es/setMedTemp.php?med_time=" + med_time);
			URLConnection conn = url.openConnection();

			InputStream is = conn.getInputStream();
			Scanner scan = new Scanner(is);

			while (scan.hasNext()) {
				System.out.println(scan.nextLine());
			}
			
			scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<medication> getMedTemp() {

		ArrayList<medication> list = new ArrayList<medication>();
		try {
			URL url = new URL("http://hope2017.esy.es/getMedTemp.php");
			URLConnection conn = url.openConnection();

			InputStream is = conn.getInputStream();
			Scanner scan = new Scanner(is);

			while (scan.hasNext()) {
				medication pa = new medication();

				pa.setP_id(scan.nextLine());
				pa.setP_name(scan.nextLine());
				pa.setP_room(scan.nextLine());
				pa.setDisease(scan.nextLine());
				pa.setMed_time(scan.nextLine());

				list.add(pa);
			}
			
			scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public String pa_update(String p_id, String d_id, String p_room, String forbid_food) {
		try {
			String result = "";
			
			URL url = new URL("http://hope2017.esy.es/updatePatient.php?p_id=" + p_id + "&d_id=" + d_id + "&p_room="
					+ p_room + "&forbid_food=" + forbid_food);
			URLConnection conn = url.openConnection();

			InputStream is = conn.getInputStream();
			Scanner scan = new Scanner(is);

			
			while (scan.hasNext()) {
				result = scan.nextLine();
				System.out.println(result);
			}
			scan.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public void pa_del(String p_id, String d_id) {
		try {			
			URL url = new URL("http://hope2017.esy.es/delPatient.php?p_id=" + p_id + "&d_id=" + d_id);
			URLConnection conn = url.openConnection();

			InputStream is = conn.getInputStream();
			Scanner scan = new Scanner(is);
			
			scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String pa_insert(String p_id, String p_name, String d_id, String p_room, String gen, String disease, String birth, String forbid_food) {
		String result = "Success";
		String temp = "";
		try {			
			URL url = new URL("http://hope2017.esy.es/insertPatient.php?p_id="+p_id+"&p_name="+p_name+"&gen="+gen+"&birth="+birth+"&disease="+disease+"&p_room="+p_room+"&d_id="+d_id+"&forbid_food="+forbid_food);
			URLConnection conn = url.openConnection();

			InputStream is = conn.getInputStream();
			Scanner scan = new Scanner(is);
			
			while (scan.hasNext()) {
				temp = scan.nextLine();
				if(temp.equals(result)) {
					result = "true";
				}
				System.out.println(temp);
			}
			
			scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<doctor> getDoctor() {
		ArrayList<doctor> list = new ArrayList<doctor>();
		try {
			URL url = new URL("http://hope2017.esy.es/getDoctor.php");
			URLConnection conn = url.openConnection();

			InputStream is = conn.getInputStream();
			Scanner scan = new Scanner(is);

			while (scan.hasNext()) {
				doctor pa = new doctor();

				pa.setD_id(scan.nextLine());
				pa.setD_name(scan.nextLine());
				
				list.add(pa);
			}
			scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
