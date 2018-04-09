package Shape;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.LinkedList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
public class Persist {
	public static Diagram load(File file) {
		ObjectInputStream input = null;
		Diagram a = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			input = new ObjectInputStream(fis);
			a = (Diagram) input.readObject();
			input.close();
		} catch (EOFException endofFileException) {
			System.out.println(endofFileException.getMessage());
		} catch (ClassNotFoundException classNotFoundException) {
			System.out.println(classNotFoundException.getMessage());
		} catch (IOException ioException) {
			System.out.println(ioException.getMessage());
		}
		return a;
	}

	public static IntDiagram load1(File file) {
		ObjectInputStream input = null;
		IntDiagram a = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			input = new ObjectInputStream(fis);
			a = (IntDiagram) input.readObject();
			input.close();
		} catch (EOFException endofFileException) {
			System.out.println(endofFileException.getMessage());
		} catch (ClassNotFoundException classNotFoundException) {
			System.out.println(classNotFoundException.getMessage());
		} catch (IOException ioException) {
			System.out.println(ioException.getMessage());
		}
		return a;
	}
	public static String createXML(File file,Diagram th){
		String strXML=null;
		Document document = DocumentHelper.createDocument();
		th.setTitle("problemdiagram");
		Element diagram = document.addElement("diagram");
		Element title =diagram.addElement("title");
		
		title.setText(th.getTitle());
		if(th.getReference().size()==0) System.out.print("referencenotfound!!");
		if(th.getPhenomenon().size()==0) System.out.print("phenomenonnotfound!!");
		if(th.getRequirements().size()==0) {System.out.print("requirenotfound!!");}
		//else{
			//for(int i=0;i<1;i++)
		//	{  
			//	req.append(this.getRequirement(i).getText());
				
			//}		}
		Element nodes=diagram.addElement("Machine");
		//System.out.print(this.components.size());
		nodes.addAttribute("machine",th.getMachine().toString());
		nodes.addAttribute("machine_name",th.getMachine().getText());
		nodes.addAttribute("machine_shortname",th.getMachine().getShortName());
		nodes.addAttribute("machine_cxb",String.valueOf("none"));
		nodes.addAttribute("machine_des",String.valueOf(th.getMachine().des));
		nodes.addAttribute("machine_state",String.valueOf(th.getMachine().getState()));
		nodes.addAttribute("machine_class",th.getMachine().getClass().toString());
		
		 
        
		StringBuilder s1=new StringBuilder();
		s1.append(th.findALine(th.getMachine(), getProDom2(th).getFirst()).x1);
		s1.append(",");
		s1.append(th.findALine(th.getMachine(), getProDom2(th).getFirst()).y1);
		s1.append(",");
		s1.append(th.findALine(th.getMachine(), getProDom2(th).getFirst()).x2);
		s1.append(",");
		s1.append(th.findALine(th.getMachine(), getProDom2(th).getFirst()).y2);
		StringBuffer re=new StringBuffer();
		re.append(th.getMachine().x1);
		re.append(",");
		re.append(th.getMachine().y1);
		re.append(",");
		re.append(th.getMachine().x2);
		re.append(",");
		re.append(th.getMachine().y2);
		nodes.addAttribute("machine_locality",re.toString());
		
		
		String []requirements=th.getRequirements().toString().split(";");
		String []requirements_text=getReq(th).split(";");
		String []requirements_biaohao=getReq2(th).split(";");
		String []requirements_des=getReq4(th).split(";");
		String []requirements_locality=getReq3(th).split(";");
		
		//nodes.addAttribute("requirements",th.getRequirements().toString());
		//nodes.addAttribute("requirements_text",getReq(th).toString());
		//nodes.addAttribute("requirements_biaohao",getReq2(th).toString());
		//nodes.addAttribute("requirements_des",getReq4(th).toString());
		//nodes.addAttribute("requirements_class",getReqclass(th).toString());
		//nodes.addAttribute("requirements_locality",getReq3(th).toString());
		
		for(int i=0;i<requirements_text.length;i++){
			Element req=diagram.addElement("Requirement");
			req.addAttribute("requirement_No",String.valueOf(i+1));
		//nodes.addAttribute("requirements_"+String.valueOf(i+1),requirements[i]);
		req.addAttribute("requirement_text",requirements_text[i]);
		req.addAttribute("requirement_biaohao",requirements_biaohao[i]);
		req.addAttribute("requirement_des",requirements_des[i]);
		req.addAttribute("requirement_shape","Oval");
		//nodes.addAttribute("requirements_class",getReqclass(th).toString());
		req.addAttribute("requirement_locality",requirements_locality[i]);
		}
		
		
		
		String [] prodomain=getProDom(th).toString().split(";");
		String []prodomain_name=getProDom(th).toString().split(";");
		String []prodomain_shortname=getProDom4(th).toString().split(";");
		String []prodomain_cxb=getProDom6(th).toString().split(";");
		String []prodomain_locality=getProDom5(th).split(";");
		
		
		for(int i=0;i<prodomain.length;i++){
			Element pro=diagram.addElement("Problemdomain");
			pro.addAttribute("problemdomain_No",String.valueOf(i+1));
		pro.addAttribute("problemdomain", prodomain[i]);
		pro.addAttribute("problemdomain_name",prodomain_name[i]);
		pro.addAttribute("problemdomain_shortname", prodomain_shortname[i]);
		pro.addAttribute("problemdomain_cxb",prodomain_cxb[i]);
		pro.addAttribute("problemdomain_shape","Rect");
		//nodes.addAttribute("prodomain_des", getProDomdes(th).toString());
	    pro.addAttribute("problemdomain_locality", prodomain_locality[i]);
		}
		//Element edge=diagram.addElement("edges");
		//Element interactiondescpt=diagram.addElement("interactiondescription");
		
		//edge.addAttribute("reference",th.getReference().toString());
		//edge.addAttribute("phenomenon",th.getPhenomenon().toString());
		
		
		//edge.addAttribute("phenomenon_constrain",getPhenomenonconstrain(th).toString());
		//edge.addAttribute("phenomenon_state",getPhenomenonstate(th).toString());
		//edge.addAttribute("phenomenon_name",getPhenomenonName(th).toString());
		//edge.addAttribute("phenomenon_state",getPhenomenonstate(th).toString());
		//edge.addAttribute("phenomenon_from_to",getPhenomenonfromto(th).toString());
		//edge.addAttribute("phenomenon_requirement",getPhenomenonreq(th).toString());
		
		String []line1=getline1x(th).split(";");
		String []line1_description=getline1(th).split(";");
		String []line1_state=getline1state(th).split(";");
		String []line1_locality=getline1weizhi(th).split(";");
		String []line1_tofrom=getline1laiqu(th).split(";");
		for(int i=0;i<line1.length;i++){
			Element ed=diagram.addElement("Interface");
		ed.addAttribute("line1",line1[i]);
		ed.addAttribute("line1_description",line1_description[i]);
		ed.addAttribute("line1_No",String.valueOf(i+1));
		//edge.addAttribute("line1_description111",getline1(th).toString());
	//	ed.addAttribute("line1_state_"+String.valueOf(i+1),line1_state[i]);
		ed.addAttribute("line1_locality",line1_locality[i]);
		ed.addAttribute("line1_tofrom",line1_tofrom[i]);
		}
		
		
		
		String []line2=getline2(th).split(";");
		String []line2_description=getline2desc(th).split(";");
		String []line2_state=getline2state(th).split(";");
		String []line2_locality=getline2weizhi(th).split(";");
		String []line2_constraint=getline2constraint(th).split(";");
		String []line2_tofrom=getline2laiqu(th).split(";");
		for(int i=0;i<line2.length;i++){
			System.out.print(line2_state[i]);
			if(line2_state[i].equals("constrain")){
			Element eg=diagram.addElement("Constraint");
			eg.addAttribute("line2",line2[i]);
			eg.addAttribute("line2_No",String.valueOf(i+1));
			eg.addAttribute("line2_description",line2_description[i]);
		//	eg.addAttribute("line2_state_"+String.valueOf(i+1),line2_state[i]);
     		eg.addAttribute("line2_constraint",line2_constraint[i]);
			eg.addAttribute("line2_locality",line2_locality[i]);
			eg.addAttribute("line2_tofrom",line2_tofrom[i]);
			}
			else if(line2_state[i].equals("reference")){
				Element e=diagram.addElement("Reference");
				e.addAttribute("line2",line2[i]);
				e.addAttribute("line2_No",String.valueOf(i+1));
				e.addAttribute("line2_description",line2_description[i]);
			//	e.addAttribute("line2_state_"+String.valueOf(i+1),line2_state[i]);
	     		e.addAttribute("line2_constraint",line2_constraint[i]);
				e.addAttribute("line2_locality",line2_locality[i]);
				e.addAttribute("line2_tofrom",line2_tofrom[i]);
				
			}
			}
		
		//edge.addAttribute("line2",getline2(th));
        //edge.addAttribute("line2description",getline2desc(th));
        //edge.addAttribute("line2_state",getline2state(th));
        //edge.addAttribute("line2_locality",getline2weizhi(th));
        //edge.addAttribute("line2_tofrom",getline2laiqu(th));
		
	   // interactiondescpt.addText(th.getInteractionDescription());
		
		System.out.print(th.getReference().toString());
		StringWriter strWtr = new StringWriter();
		 OutputFormat format = OutputFormat.createPrettyPrint();
		 format.setEncoding("UTF-8");
		 XMLWriter xmlWriter =new XMLWriter(strWtr, format);
		try {
			xmlWriter.write(document);
		} catch (IOException e1){
		// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 strXML = strWtr.toString();
	//--------

	//-------
	//strXML=document.asXML();
	//------

	//-------------
			
			if (file.exists()){
				file.delete();}
			try{
				file.createNewFile();
				XMLWriter out = new XMLWriter(new FileWriter(file));
				out.write(document);
				out.flush();
				out.close();
				} catch (IOException e) {
	//TODO Auto-generated catch block
					e.printStackTrace();
				}
				//--------------
			System.out.print(strXML);
	         return strXML;
	    }
	public static String getProDom(Diagram th) { /////wrong unsolved
		int count = 0;
		StringBuffer re=new StringBuffer();
		//re.add(String.valueOf(th.hasProDom()));
		for (int i = 0; i < th.components.size(); i++) {
			if (((Shape) th.components.get(i)).shape == 0) {
				Rect rr = (Rect) th.components.get(i);
				if (rr.state == 1||rr.state==0) {
					re.append(rr.getText());
                    re.append(';');
				}
			}		
	     }
		return re.toString();
	}
	
	public static String getReq(Diagram th) {
		StringBuffer ll = new StringBuffer();
		for (int i = 0; i < th.components.size(); i++) {
			if (((Shape) th.components.get(i)).shape ==1) {
				Oval rr = (Oval) th.components.get(i);
					ll.append(rr.getText());
					ll.append(';');
			}		
	     }
		return ll.toString();
	}
	
	
	public static LinkedList<Oval> getReqOval(Diagram th) {
		LinkedList<Oval> ll = new LinkedList<>();
		for (int i = 0; i < th.components.size(); i++) {
			Shape tmp = (Shape) th.components.get(i);
			if (tmp.shape == 1) {
				Oval re=(Oval)tmp;
				ll.add(re);
			}
	     }
		return ll;
	}
	
	
	public static String getReq2(Diagram th) {
		StringBuffer ll = new StringBuffer();
		for (int i = 0; i < th.components.size(); i++) {
			if (((Shape) th.components.get(i)).shape ==1) {
				Oval rr = (Oval) th.components.get(i);
					ll.append(String.valueOf(rr.getBiaohao()));
					ll.append(';');
			}		
	     }
		return ll.toString();
	}

	public static LinkedList<String> getReqclass(Diagram th) { /////?
		LinkedList<String> ll = new LinkedList<>();
		for (int i = 0; i < th.components.size(); i++) {
			if (((Shape) th.components.get(i)).shape ==1) {
				Oval rr = (Oval) th.components.get(i);
					ll.add(String.valueOf(rr.getClass()));
			}		
	     }
		return ll;
	}
	
	public static String getReq3(Diagram th) {
		StringBuffer ll = new StringBuffer();
		for (int i = 0; i < th.components.size(); i++) {
			if (((Shape) th.components.get(i)).shape ==1) {
				Oval rr = (Oval) th.components.get(i);
					ll.append(String.valueOf(rr.x1));
					ll.append(',');
					ll.append(String.valueOf(rr.y1));
					ll.append(',');
					ll.append(String.valueOf(rr.x2));
					ll.append(',');
					ll.append(String.valueOf(rr.y2));
					ll.append(';');
			}		
	     }
		return ll.toString();
	}
	
	public static String getReq4(Diagram th) {
		StringBuffer ll = new StringBuffer();
		for (int i = 0; i < th.components.size(); i++) {
			if (((Shape) th.components.get(i)).shape ==1) {
				Oval rr = (Oval) th.components.get(i);
					ll.append(String.valueOf(rr.des));
					ll.append(';');
			}		
	     }
		return ll.toString();
	}
	


	public static LinkedList<Rect> getProDom2(Diagram th) { /////wrong unsolved
		int count = 0;
		LinkedList<Rect> re=new LinkedList<>();
		//re.add(String.valueOf(th.hasProDom()));
		for (int i = 0; i < th.components.size(); i++) {
			if (((Shape) th.components.get(i)).shape == 0) {
				Rect rr = (Rect) th.components.get(i);
				if (rr.state == 1||rr.state==0) {
					re.add(rr);
				
				}
			}		
	     }
		return re;
	}
	public static String getProDom3(Diagram th) { 
		int count = 0;
		StringBuffer re=new StringBuffer();
		//re.add(String.valueOf(th.hasProDom()));
		for (int i = 0; i < th.components.size(); i++) {
			if (((Shape) th.components.get(i)).shape == 0) {
				Rect rr = (Rect) th.components.get(i);
				if (rr.state == 1||rr.state==0) {
					re.append(rr.getText());
					re.append(';');
				}
			}		
	     }
		return re.toString();
	}
	public static String getProDom4(Diagram th) { /////wrong unsolved
		int count = 0;
		StringBuffer re=new StringBuffer();
		//re.add(String.valueOf(th.hasProDom()));
		for (int i = 0; i < th.components.size(); i++) {
			if (((Shape) th.components.get(i)).shape == 0) {
				Rect rr = (Rect) th.components.get(i);
				if (rr.state == 1||rr.state==0) {
					re.append(rr.getShortName());
					re.append(';');
				}
			}		
	     }
		return re.toString();
	}
	public static LinkedList<String> getProDomdes(Diagram th) { /////wrong unsolved
		int count = 0;
		LinkedList<String> re=new LinkedList<>();
		//re.add(String.valueOf(th.hasProDom()));
		for (int i = 0; i < th.components.size(); i++) {
			if (((Shape) th.components.get(i)).shape == 0) {
				Rect rr = (Rect) th.components.get(i);
				if (rr.state == 1||rr.state==0) {
					re.add(String.valueOf(rr.des));
				}
			}		
	     }
		return re;
	}
	
	public static String getProDom5(Diagram th) { /////wrong unsolved
		int count = 0;
		StringBuffer re=new StringBuffer();
		//re.add(String.valueOf(th.hasProDom()));
		for (int i = 0; i < th.components.size(); i++) {
			if (((Shape) th.components.get(i)).shape == 0) {
				Rect rr = (Rect) th.components.get(i);
				if (rr.state == 1||rr.state==0) {
					re.append(String.valueOf(rr.x1));
					re.append(',');
					re.append(String.valueOf(rr.y1));
					re.append(',');
					re.append(String.valueOf(rr.x2));
					re.append(',');
					re.append(String.valueOf(rr.y2));
					re.append(';');
					
				}
			}		
	     }
		return re.toString();
	}
	
	public static String getProDom6(Diagram th) { /////wrong unsolved
		int count = 0;
	    StringBuffer re=new StringBuffer();
		//re.add(String.valueOf(th.hasProDom()));
		for (int i = 0; i < th.components.size(); i++) {
			if (((Shape) th.components.get(i)).shape == 0) {
				Rect rr = (Rect) th.components.get(i);
				if (rr.state == 1||rr.state==0) {
					re.append(String.valueOf(rr.getCxb()));
					re.append(';');
				}
			}		
	     }
		return re.toString();
	}
	
	
	public static String getline1weizhi(Diagram th) {
		Rect machine = null;
		Rect domain = null;
		LinkedList<Rect> domlist=new LinkedList<>();
		Line line1 = null;
		StringBuilder x=new StringBuilder();
		for (int i = 0; i < th.components.size(); i++) {
			Shape tmp = (Shape) th.components.get(i);
			if (tmp.shape == 0) {
				Rect tmp_rect = (Rect) tmp;
				if (tmp_rect.state == 2) {
					machine = tmp_rect;
				  break;
				}
			
			}
			 
		}
		StringBuffer all=new StringBuffer();
		domlist=getProDom2(th);
			
		
	 for(int i=0;i<domlist.size();i++){
		Rect n=domlist.get(i);
		System.out.print(n.getText());
		line1 = th.findALine(machine, n);
		StringBuffer linexy=new StringBuffer();
		linexy.append(line1.x1);
		linexy.append(",");
		linexy.append(line1.x2);
		linexy.append(",");
		linexy.append(line1.y1);
		linexy.append(",");
		linexy.append(line1.y2);
		linexy.append(';');
		all.append(linexy);
		//if (line1 == null) {
			//line1 = th.findALine(domain, machine);
		//}
		}
		
		return all.toString();
	}
	
	
	
	
	public  static String getPhenomenonconstrain(Diagram th) {
		LinkedList ll = new LinkedList();
		StringBuffer lc = new StringBuffer();
		for (int i = 0; i <= th.components.size() - 1; i++) {
			Shape tmp_s = (Shape) th.components.get(i);
			if (tmp_s.shape == 2) {
				Line tmp_l = (Line) tmp_s;
				for (int j = 0; j <= tmp_l.phenomenons.size() - 1; j++) {
					Phenomenon tmp_p = (Phenomenon) tmp_l.phenomenons.get(j);
					boolean jia = true;
					for (int k = 0; k <= ll.size() - 1; k++) {
						Phenomenon tmp1 = (Phenomenon) ll.get(k);
						if ((!tmp1.getName().equals(tmp_p.getName()))
								|| (!tmp1.getState().equals(tmp_p.getState())))
							continue;
						jia = false;
					}

					if (jia) {
						ll.add(tmp_p);
						lc.append(tmp_p.getConstraining());
                        lc.append(';');					
					}
				}
			}
		}
		return lc.toString();
	}
	
	
	public  static  LinkedList getPhenomenonName(Diagram th) {
		LinkedList ll = new LinkedList();
		LinkedList lc = new LinkedList();
		for (int i = 0; i <= th.components.size() - 1; i++) {
			Shape tmp_s = (Shape) th.components.get(i);
			if (tmp_s.shape == 2) {
				Line tmp_l = (Line) tmp_s;
				for (int j = 0; j <= tmp_l.phenomenons.size() - 1; j++) {
					Phenomenon tmp_p = (Phenomenon) tmp_l.phenomenons.get(j);
					boolean jia = true;
					for (int k = 0; k <= ll.size() - 1; k++) {
						Phenomenon tmp1 = (Phenomenon) ll.get(k);
						if ((!tmp1.getName().equals(tmp_p.getName()))
								|| (!tmp1.getState().equals(tmp_p.getState())))
							continue;
						jia = false;
					}

					if (jia) {
						ll.add(tmp_p);
						lc.add(tmp_p.getName());
					}
				}
			}
		}
		return lc;
	}
	
	
	
	
	public  static  LinkedList getPhenomenonstate(Diagram th) {
		LinkedList ll = new LinkedList();
		LinkedList lc = new LinkedList();
		for (int i = 0; i <= th.components.size() - 1; i++) {
			Shape tmp_s = (Shape) th.components.get(i);
			if (tmp_s.shape == 2) {
				Line tmp_l = (Line) tmp_s;
				for (int j = 0; j <= tmp_l.phenomenons.size() - 1; j++) {
					Phenomenon tmp_p = (Phenomenon) tmp_l.phenomenons.get(j);
					boolean jia = true;
					for (int k = 0; k <= ll.size() - 1; k++) {
						Phenomenon tmp1 = (Phenomenon) ll.get(k);
						if ((!tmp1.getName().equals(tmp_p.getName()))
								|| (!tmp1.getState().equals(tmp_p.getState())))
							continue;
						jia = false;
					}

					if (jia) {
						ll.add(tmp_p);
						lc.add(tmp_p.getState());
					}
				}
			}
		}
		return lc;
	}
	
	
	public  static  LinkedList getPhenomenonfromto(Diagram th) {
		LinkedList ll = new LinkedList();
		LinkedList lc = new LinkedList();
		for (int i = 0; i <= th.components.size() - 1; i++) {
			Shape tmp_s = (Shape) th.components.get(i);
			if (tmp_s.shape == 2) {
				Line tmp_l = (Line) tmp_s;
				for (int j = 0; j <= tmp_l.phenomenons.size() - 1; j++) {
					Phenomenon tmp_p = (Phenomenon) tmp_l.phenomenons.get(j);
					boolean jia = true;
					for (int k = 0; k <= ll.size() - 1; k++) {
						Phenomenon tmp1 = (Phenomenon) ll.get(k);
						if ((!tmp1.getName().equals(tmp_p.getName()))
								|| (!tmp1.getState().equals(tmp_p.getState())))
							continue;
						jia = false;
					}

					if (jia) {
						ll.add(tmp_p);
						lc.add(tmp_p.getFrom());
						lc.add(tmp_p.getTo());
						lc.add(';');
					}
				}
			}
		}
		return lc;
	}
	
	
	public  static  LinkedList getPhenomenonreq(Diagram th) {
		LinkedList ll = new LinkedList();
		LinkedList lc = new LinkedList();
		
		for (int i = 0; i <= th.components.size() - 1; i++) {
			Shape tmp_s = (Shape) th.components.get(i);
			if (tmp_s.shape == 2) {
				Line tmp_l = (Line) tmp_s;
				for (int j = 0; j <= tmp_l.phenomenons.size() - 1; j++) {
					Phenomenon tmp_p = (Phenomenon) tmp_l.phenomenons.get(j);
					boolean jia = true;
					for (int k = 0; k <= ll.size() - 1; k++) {
						Phenomenon tmp1 = (Phenomenon) ll.get(k);
						if ((!tmp1.getName().equals(tmp_p.getName()))
								|| (!tmp1.getState().equals(tmp_p.getState())))
							continue;
						jia = false;
					}

					if (jia) {
						ll.add(tmp_p);
						lc.add(tmp_p.getRequirement());
					}
				}
			}
		}
		return lc;
	}
	
	
	
	
	
	
	public static String getline1(Diagram th) {  //getline1description
		Rect machine = null;
		Rect domain = null;
		LinkedList<Rect> domlist=new LinkedList<>();
		Line line1 = null;
		StringBuilder x=new StringBuilder();
		for (int i = 0; i < th.components.size(); i++) {
			Shape tmp = (Shape) th.components.get(i);
			if (tmp.shape == 0) {
				Rect tmp_rect = (Rect) tmp;
				if (tmp_rect.state == 2) {
					machine = tmp_rect;
				  break;
				}
			
			}
			 
		}
		//StringBuffer all=new StringBuffer();
		domlist=getProDom2(th);
			
	StringBuffer linexy=new StringBuffer();
	 for(int i=0;i<domlist.size();i++){
		Rect n=domlist.get(i);
		System.out.print(n.getText());
		line1 = th.findALine(machine, n);
	
		linexy.append(line1.getDescription());
		linexy.append(";");
		}
		
		return linexy.toString();
	}
	public static String getline1x(Diagram th) {
		Rect machine = null;
		Rect domain = null;
		LinkedList<Rect> domlist=new LinkedList<>();
		Line line1 = null;
		StringBuilder x=new StringBuilder();
		for (int i = 0; i < th.components.size(); i++) {
			Shape tmp = (Shape) th.components.get(i);
			if (tmp.shape == 0) {
				Rect tmp_rect = (Rect) tmp;
				if (tmp_rect.state == 2) {
					machine = tmp_rect;
				  break;
				}
			
			}
			 
		}
	
		domlist=getProDom2(th);
			
		StringBuffer linexy=new StringBuffer();
		
	 for(int i=0;i<domlist.size();i++){
		Rect n=domlist.get(i);
		System.out.print(n.getText());
		line1 = th.findALine(machine, n);
		
		linexy.append(line1);
		linexy.append(';');
		}
		
		return linexy.toString();
	}
	
	
	public static String getline1state(Diagram th) {
		Rect machine = null;
		Rect domain = null;
		LinkedList<Rect> domlist=new LinkedList<>();
		Line line1 = null;
		StringBuilder x=new StringBuilder();
		for (int i = 0; i < th.components.size(); i++) {
			Shape tmp = (Shape) th.components.get(i);
			if (tmp.shape == 0) {
				Rect tmp_rect = (Rect) tmp;
				if (tmp_rect.state == 2) {
					machine = tmp_rect;
				  break;
				}
			
			}
			 
		}
	
		domlist=getProDom2(th);
			
		StringBuffer linexy=new StringBuffer();
		
	 for(int i=0;i<domlist.size();i++){
		Rect n=domlist.get(i);
		System.out.print(n.getText());
		line1 = th.findALine(machine, n);
		int rn=line1.getState();
		 if(rn==0)
		 {
		 linexy.append("interface");
		 linexy.append(';');
		 }
		 else  if(rn==1)
		 {
		 linexy.append("reference");
		 linexy.append(';');
		 }
		 else
			 if(rn==2)
		 {
			 linexy.append("requirement");
			 linexy.append(';');
		 }
		
		}
		
		return linexy.toString();
	}
	
	
	public static String getline1laiqu(Diagram th) {
		Rect machine = null;
		Rect domain = null;
		LinkedList<Rect> domlist=new LinkedList<>();
		Line line1 = null;
		StringBuilder x=new StringBuilder();
		for (int i = 0; i < th.components.size(); i++) {
			Shape tmp = (Shape) th.components.get(i);
			if (tmp.shape == 0) {
				Rect tmp_rect = (Rect) tmp;
				if (tmp_rect.state == 2) {
					machine = tmp_rect;
				  break;
				}
			
			}
			 
		}
	
		domlist=getProDom2(th);
			
		StringBuffer linexy=new StringBuffer();
		
	 for(int i=0;i<domlist.size();i++){
		Rect n=domlist.get(i);
		System.out.print(n.getText());
		line1 = th.findALine(machine, n);
		
		linexy.append(((Rect)line1.to).getShortName());
		linexy.append(',');
		linexy.append(((Rect)line1.from).getShortName());
		linexy.append(';');
		}
		
		return linexy.toString();
	}
	
	public static String getline2(Diagram th) {
		LinkedList<Rect> domlist=new LinkedList<>();
		Line line2 = null;
		Line line3=null;
		domlist=getProDom2(th);
		LinkedList<Oval> reqlist=new LinkedList<>();	
		reqlist=getReqOval(th);
		StringBuffer linexy=new StringBuffer();
	 for(int i=0;i<domlist.size();i++){
		 for(int j=0;j<reqlist.size();j++){
		Rect n=domlist.get(i);
		Oval m=reqlist.get(j);
		line2 = th.findALine(n, m);
		line3=th.findALine(m, n);
		if(line2==null)System.out.print("xx f ");
		else
		{
		 linexy.append(line2);
		 linexy.append(';');
		}
		if(line3==null)System.out.print("222 ");
		else
		{
		linexy.append(line3);
		linexy.append(';');
	    }
	 }
	 }
		return String.valueOf(linexy);
	}
	
	
	
	
	
	public static String getline2constraint(Diagram th) {
		LinkedList<Rect> domlist=new LinkedList<>();
		Line line2 = null;
		Line line3=null;
		domlist=getProDom2(th);
		LinkedList<Oval> reqlist=new LinkedList<>();	
		reqlist=getReqOval(th);
		StringBuffer linexy=new StringBuffer();
	 for(int i=0;i<domlist.size();i++){
		 for(int j=0;j<reqlist.size();j++){
		Rect n=domlist.get(i);
		Oval m=reqlist.get(j);
		line2 = th.findALine(n, m);
		if(line2!=null)
		if(line2.phenomenons!=null){
		    linexy.append(line2.name);
		    linexy.append(':');
			for(int k=0;k<line2.phenomenons.size();k++)
			{
				Phenomenon tmp_p=(Phenomenon)line2.phenomenons.get(k);
			    linexy.append(tmp_p.getName());
			    linexy.append(':');
			    linexy.append(tmp_p.getConstraining());
			    linexy.append('|');
			}
			linexy.append(';');
		
		}
	
			
			
			line3=th.findALine(m, n);
		if(line3!=null)
		if(line3.phenomenons!=null){
			linexy.append(line3.name);
			linexy.append(':');
		for(int k=0;k<line3.phenomenons.size();k++)
			{
				Phenomenon tmp_p=(Phenomenon)line3.phenomenons.get(k);
			    linexy.append(tmp_p.getName());
			    linexy.append(':');
			    linexy.append(tmp_p.getConstraining());
			    linexy.append('|');
			}
		linexy.append(';');
	 }
		 }
	 }
		return String.valueOf(linexy);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static String getline2state(Diagram th) {
		LinkedList<Rect> domlist=new LinkedList<>();
		Line line2 = null;
		Line line3=null;
		domlist=getProDom2(th);
		LinkedList<Oval> reqlist=new LinkedList<>();	
		reqlist=getReqOval(th);
		StringBuffer linexy=new StringBuffer();
	 for(int i=0;i<domlist.size();i++){
		 for(int j=0;j<reqlist.size();j++){
		Rect n=domlist.get(i);
		Oval m=reqlist.get(j);
		line2 = th.findALine(n, m);
		line3=th.findALine(m, n);
		if(line2==null)System.out.print("xx f ");
		else
		{ 
		 int rn=line2.getState();
		// boolean r=((Phenomenon)line2.phenomenons).
		 if(rn==0)
		 {
		 linexy.append("interface");
		 linexy.append(';');
		 }
		 else  if(rn==1)
		 {
		 linexy.append("reference");
		 linexy.append(';');
		 }
		 else
			 if(rn==2)
		 {
			 linexy.append("constrain");
			 linexy.append(';');
		 }
		}
		if(line3==null)System.out.print("222 ");
		else
		{
			 int rn=line3.getState();
			 if(rn==0)
			 {
			 linexy.append("interface");
			 linexy.append(';');
			 }
			 else  if(rn==1)
			 {
			 linexy.append("reference");
			 linexy.append(';');
			 }
			 else
				 if(rn==2)
			 {
				 linexy.append("constrain");
				 linexy.append(';');
			 }
	    }
	 }
	 }
		return String.valueOf(linexy);
	}
	
	
	public static String getline2stateword(Diagram th) {
		LinkedList<Rect> domlist=new LinkedList<>();
		Line line2 = null;
		Line line3=null;
		domlist=getProDom2(th);
		LinkedList<Oval> reqlist=new LinkedList<>();	
		reqlist=getReqOval(th);
		StringBuffer linexy=new StringBuffer();
	 for(int i=0;i<domlist.size();i++){
		 for(int j=0;j<reqlist.size();j++){
		Rect n=domlist.get(i);
		Oval m=reqlist.get(j);
		line2 = th.findALine(n, m);
		line3=th.findALine(m, n);
		if(line2==null)System.out.print("xx f ");
		else
		{
			
		 linexy.append(line2.getState());
		 linexy.append(';');
		}
		if(line3==null)System.out.print("222 ");
		else
		{
		linexy.append(line3.getState());
		linexy.append(';');
	    }
	 }
	 }
		return String.valueOf(linexy);
	}
	
	
	
	
	
	public static String getline2laiqu(Diagram th) {
		LinkedList<Rect> domlist=new LinkedList<>();
		Line line2 = null;
		Line line3=null;
		domlist=getProDom2(th);
		LinkedList<Oval> reqlist=new LinkedList<>();	
		reqlist=getReqOval(th);
		StringBuffer linexy=new StringBuffer();
	 for(int i=0;i<domlist.size();i++){
		 for(int j=0;j<reqlist.size();j++){
		Rect n=domlist.get(i);
		Oval m=reqlist.get(j);
		line2 = th.findALine(n, m);
		line3=th.findALine(m, n);
		if(line2==null)System.out.print("xx f ");
		else
		{
		 linexy.append(((Oval)line2.to).getText());
		 linexy.append(',');
		 linexy.append(((Rect)line2.from).getShortName());
		 linexy.append(';');
		}
		if(line3==null)System.out.print("222 ");
		else
		{
			linexy.append(((Rect)line3.to).getShortName());
			 linexy.append(',');
			 linexy.append(((Oval)line3.from).getText());
			 linexy.append(';');
	    }
	 }
	 }
		return String.valueOf(linexy);
	}
	
	
	
	public static String getline2desc(Diagram th) {
		LinkedList<Rect> domlist=new LinkedList<>();
		Line line2 = null;
		Line line3=null;
		domlist=getProDom2(th);
		LinkedList<Oval> reqlist=new LinkedList<>();	
		reqlist=getReqOval(th);
		StringBuffer linexy=new StringBuffer();
	 for(int i=0;i<domlist.size();i++){
		 for(int j=0;j<reqlist.size();j++){
		Rect n=domlist.get(i);
		Oval m=reqlist.get(j);
		line2 = th.findALine(n, m);
		line3=th.findALine(m, n);
		if(line2==null)System.out.print("xx f ");
		else
		{
		linexy.append(line2.getDescription());
		linexy.append(';');
		}
		if(line3==null)System.out.print("222 ");
		else
		{
		linexy.append(line3.getDescription());
		linexy.append(';');
		}
		}
	 }
	
		return String.valueOf(linexy);
	}
	
	
	
	public static String[] getline2core(Diagram th) {
		LinkedList<Rect> domlist=new LinkedList<>();
		Line line2 = null;
		Line line3=null;
		domlist=getProDom2(th);
		LinkedList<Oval> reqlist=new LinkedList<>();	
		reqlist=getReqOval(th);
		StringBuffer linexy=new StringBuffer();
	 for(int i=0;i<domlist.size();i++){
		 for(int j=0;j<reqlist.size();j++){
		Rect n=domlist.get(i);
		Oval m=reqlist.get(j);
		line2 = th.findALine(n, m);
		line3=th.findALine(m, n);
		if(line2==null)System.out.print("xx f ");
		else
		{
		if(!line2.core.equals(""))
		{
		 linexy.append(line2.core);
		 linexy.append(';');
		}
		else 
			System.out.print("m");
			
		}
		if(line3==null)System.out.print("222 ");
		else
		{
			if(!line3.core.equals("")){
		linexy.append(line3.core);
		linexy.append(';');
		}

		else
			{
			System.out.print("ll");
			}
			}
		}

	 }
		return linexy.toString().split(";");
	}
	
	
	
	
	public static String[] getline2core1(Diagram th) {
		LinkedList<Rect> domlist=new LinkedList<>();
		Line line2 = null;
		Line line3=null;
		domlist=getProDom2(th);
		LinkedList<Oval> reqlist=new LinkedList<>();	
		reqlist=getReqOval(th);
		StringBuffer linexy=new StringBuffer();
	 for(int i=0;i<domlist.size();i++){
		 for(int j=0;j<reqlist.size();j++){
		Rect n=domlist.get(i);
		Oval m=reqlist.get(j);
		line2 = th.findALine(n, m);
		line3=th.findALine(m, n);
		if(line2==null)System.out.print("xx f ");
		else
		{
			if(!line2.core1.equals("")){
		linexy.append(line2.core1);
		linexy.append(';');}
			else{
				int bbb=0;
				}
		}
		if(line3==null)System.out.print("222 ");
		else
		{
			if(!line3.core1.equals("")){
		linexy.append(line3.core1);
		linexy.append(';');
		}

		else {
			int u=0;
		}
			}
	 }
	 }
		return linexy.toString().split(";");
				}
	
	
	public static String getline2core1s(Diagram th) {
		LinkedList<Rect> domlist=new LinkedList<>();
		Line line2 = null;
		Line line3=null;
		domlist=getProDom2(th);
		LinkedList<Oval> reqlist=new LinkedList<>();	
		reqlist=getReqOval(th);
		StringBuffer linexy=new StringBuffer();
	 for(int i=0;i<domlist.size();i++){
		 for(int j=0;j<reqlist.size();j++){
		Rect n=domlist.get(i);
		Oval m=reqlist.get(j);
		line2 = th.findALine(n, m);
		line3=th.findALine(m, n);
		if(line2==null)System.out.print("xx f ");
		else
		{
			if(!line2.core1.equals("")){
		linexy.append(line2.core1);
		linexy.append(';');}
			else{
				int bbb=0;
				}
		}
		if(line3==null)System.out.print("222 ");
		else
		{
			if(!line3.core1.equals("")){
		linexy.append(line3.core1);
		linexy.append(';');
		}

		else {
			int u=0;
		}
			}
	 }
	 }
		return linexy.toString();
				}
	
	
	
	
	
	
	public static String getline2weizhi(Diagram th) {
		LinkedList<Rect> domlist=new LinkedList<>();
		Line line2 = null;
		Line line3=null;
		domlist=getProDom2(th);
		LinkedList<Oval> reqlist=new LinkedList<>();	
		reqlist=getReqOval(th);
		StringBuffer linexy=new StringBuffer();
	 for(int i=0;i<domlist.size();i++){
		 for(int j=0;j<reqlist.size();j++){
		Rect n=domlist.get(i);
		Oval m=reqlist.get(j);
		line2 = th.findALine(n, m);
		line3=th.findALine(m, n);
		if(line2==null)System.out.print("wu qubian");
		else
		{
			linexy.append(line2.x1);
			linexy.append(',');
			linexy.append(line2.y1);
			linexy.append(',');
			linexy.append(line2.x2);
			linexy.append(',');
			linexy.append(line2.y2);
			linexy.append(';');
		
		}
		if(line3==null)System.out.print("wu huibian ");
		else
		{
			linexy.append(line3.x1);
			linexy.append(',');
			linexy.append(line3.y1);
			linexy.append(',');
			linexy.append(line3.x2);
			linexy.append(',');
			linexy.append(line3.y2);
			linexy.append(';');
		}
		 }
	 }
	
		return String.valueOf(linexy);
	}
	
	
	

	public static String createXML1(File file,Diagram th){
	String strXML=null;
	Document document = DocumentHelper.createDocument();
	th.setTitle("contextdiagram");
	Element diagram = document.addElement("diagram");
	Element title =diagram.addElement("title");
	Element components=diagram.addElement("components");
	Element interactiondescpt=diagram.addElement("interactiondescription");
	title.setText(th.getTitle());
	//if(this.getReference().size()==0) System.out.print("referencenotfound!!");
	if(th.getPhenomenon().size()==0) System.out.print("phenomenonnotfound!!");
	System.out.print(th.components.size());
	components.addAttribute("machine",th.getMachine().getText());
	StringBuffer re=new StringBuffer();
	re.append(th.getMachine().x1);
	re.append(",");
	re.append(th.getMachine().x2);
	re.append(",");
	re.append(th.getMachine().y1);
	re.append(",");
	re.append(th.getMachine().y2);
	components.addAttribute("machinelocality",re.toString());
	components.addAttribute("phenomenon",th.getPhenomenon().toString());
	interactiondescpt.addText(th.getInteractionDescription());


	StringWriter strWtr = new StringWriter();
	 OutputFormat format = OutputFormat.createPrettyPrint();
	 format.setEncoding("UTF-8");
	 XMLWriter xmlWriter =new XMLWriter(strWtr, format);
	try {
		xmlWriter.write(document);
	} catch (IOException e1){
	// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	 strXML = strWtr.toString();
	//--------

	//-------
	//strXML=document.asXML();
	//------

	//-------------
		
		if (file.exists()){
			file.delete();}
		try{
			file.createNewFile();
			XMLWriter out = new XMLWriter(new FileWriter(file));
			out.write(document);
			out.flush();
			out.close();
			} catch (IOException e) {
	//TODO Auto-generated catch block
				e.printStackTrace();
			}
			//--------------
		System.out.print(strXML);
	     return strXML;
	}
	
	public static boolean save(File file, Diagram dd) {
		if (file == null) {
			return false;
		}
		file.canWrite();
		file.delete();
		FileOutputStream fos = null;
		ObjectOutputStream output = null;
		try {
			fos = new FileOutputStream(file);
			output = new ObjectOutputStream(fos);
			output.writeObject(dd);
			output.flush();
			output.close();
			fos.close();
			return true;
		} catch (IOException e) {
		}
		return false;
	}

	public static boolean save1(File file, IntDiagram dd) {
		if (file == null) {
			return false;
		}
		file.canWrite();
		file.delete();
		FileOutputStream fos = null;
		ObjectOutputStream output = null;
		try {
			fos = new FileOutputStream(file);
			output = new ObjectOutputStream(fos);
			output.writeObject(dd);
			output.flush();
			output.close();
			fos.close();
			return true;
		} catch (IOException e) {
		}
		return false;
	}

	public static boolean save(String title, String path) {
		File file = new File(path);
		FileOutputStream fos = null;
		ObjectOutputStream output = null;
		try {
			fos = new FileOutputStream(file);
			output = new ObjectOutputStream(fos);
			Prj prj = new Prj(title, Data.first, Data.firstq);
			output.writeObject(prj);
			output.flush();
			output.close();
			fos.close();
			return true;
		} catch (IOException e) {
			System.out.println(e.getStackTrace());
		}
		return false;
	}

	public static String load(String path) {
		ObjectInputStream input = null;
		Prj a = null;
		File file = new File(path);
		try {
			FileInputStream fis = new FileInputStream(file);
			input = new ObjectInputStream(fis);
			a = (Prj) input.readObject();
			input.close();
		} catch (EOFException endofFileException) {
			System.out.println(endofFileException.getMessage());
		} catch (ClassNotFoundException classNotFoundException) {
			System.out.println(classNotFoundException.getMessage());
		} catch (IOException ioException) {
			System.out.println(ioException.getMessage());
		} catch (Exception e) {
		}
		Data.first = a.first;
		Data.firstq = a.firstq;
		return a.title;
	}

	public static void main(String[] args) {
		Persist pe = new Persist();
		/*Diagram dd = load(new File("D:/tmp"));
		System.out.println(dd.getTitle());
	*/}
}