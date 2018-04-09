package UI;
import Ontology.Ontology_c;
import Shape.Data;
import Shape.Diagram;
import Shape.IntDiagram;
import Shape.Line;
import Shape.Oval;
import Shape.Persist;
import Shape.Phenomenon;
import Shape.Rect;
import Shape.Shape;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DrawPane extends JPanel implements ActionListener {
	JButton jb1 = new JButton();
	public int i = 0;
	Ontology_c ont;
	JLabel[] jLabel = new JLabel[16];
	
	public static void main(String[] args) {
	}

	public void event_load() {
		this.ont = new Ontology_c();
		Object[] bs = { "OK" };
		// if(this.ont.checkConsistence())
		// System.out.println("safsd");
		/*JOptionPane.showOptionDialog(Main.win,
				"Succeed to load the file of the ontology!", "Tip", 0, 1, null,
				bs, null);*/

		Main.win.check.setEnabled(true);
	}

	public void event_check() {
		if (this.ont.checkConsistence()) {
			Object[] bs = { "OK" };

			/*JOptionPane.showOptionDialog(Main.win,
					"The ontology is consistent!", "Tip", 0, 1, null, bs, null);*/
		} else {
			Object[] bs = { "OK" };

			/*JOptionPane.showOptionDialog(this,
					"The ontology is not consistent!", "Tip", 0, 0, null, bs,
					null);*/
		}

		Main.win.setButtonState(-1);
	}

	public DrawPane() {
		setLayout(null);

		int x = 10;
		int y = 10;
		int chang = 250;
		int kuan = 20;
		for (int i = 0; i < this.jLabel.length; i++) {
			this.jLabel[i] = new JLabel();
			add(this.jLabel[i]);
			this.jLabel[i].setEnabled(false);
			this.jLabel[i]
					.setBounds(new Rectangle(x, y + i * kuan, chang, kuan));
			add(this.jLabel[i]);
		}
		this.jb1.setBounds(new Rectangle(20, y + this.jLabel.length * kuan, 81,
				25));
		this.jb1.setText("Execute");
		add(this.jb1);
		this.jb1.setEnabled(false);
		this.jb1.addActionListener(this);
		this.jLabel[0].setText("Step 1:Draw the context diagram");
		this.jLabel[1].setText("   1,Designate machine");
		this.jLabel[2].setText("   2,Identify domains");
		this.jLabel[3].setText("   3,Identify interactions");
		this.jLabel[4].setText("   4,Check context diagram");
		this.jLabel[5].setText("Step 2:Draw the problem diagram");
		this.jLabel[6].setText("   1,Identify requirements");
		this.jLabel[7].setText("   2,Identify references");
		this.jLabel[8].setText("   3,Check problem diagram");
		/*this.jLabel[9].setText("Step 3:Construct the scenario graphs");
		this.jLabel[10].setText("   1,Draw scenario graph");
		this.jLabel[11].setText("   2,Check scenario graph");
		this.jLabel[12].setText("Step 4:Perform the problem projection");
		this.jLabel[13].setText("   1,Check well-formed scenario");
		this.jLabel[14].setText("   2,Perform projection");*/
		this.jLabel[9].setText("Finish");
	}

	private void setallnon() {
		for (int i = 0; i <= this.jLabel.length - 1; i++)
			this.jLabel[i].setEnabled(false);
	}

	public void setState(int i) {
		this.i = i;
		setLabel();
		Main.win.setButtonState(i);
	}

	private void setLabel() {
		if (this.i == 0) {
			setallnon();
			this.jLabel[0].setEnabled(true);
			this.jLabel[1].setEnabled(true);
		}
		if (this.i == 1) {
			setallnon();
			this.jLabel[0].setEnabled(true);
			this.jLabel[2].setEnabled(true);
		}
		if (this.i == 2) {
			setallnon();
			this.jLabel[0].setEnabled(true);
			this.jLabel[3].setEnabled(true);
		}
		if (this.i == 3) {
			setallnon();
			this.jLabel[0].setEnabled(true);
			this.jLabel[4].setEnabled(true);
		}
		if (this.i == 4) {
			setallnon();
			this.jLabel[5].setEnabled(true);
			this.jLabel[6].setEnabled(true);
		}
		if (this.i == 5) {
			setallnon();
			this.jLabel[5].setEnabled(true);
			this.jLabel[7].setEnabled(true);
		}
		if (this.i == 6) {
			setallnon();
			this.jLabel[5].setEnabled(true);
			this.jLabel[8].setEnabled(true);
		}
		if (this.i == 7) {
			setallnon();
			this.jLabel[9].setEnabled(true);
			this.jLabel[10].setEnabled(true);
		}
		if (this.i == 8) {
			setallnon();
			this.jLabel[9].setEnabled(true);
			this.jLabel[11].setEnabled(true);
		}
		if (this.i == 9) {
			setallnon();
			this.jLabel[12].setEnabled(true);
			this.jLabel[13].setEnabled(true);
		}
		if (this.i == 10) {
			setallnon();
			this.jLabel[12].setEnabled(true);
			this.jLabel[14].setEnabled(true);
		}
		if (this.i == 11) {
			setallnon();
			this.jLabel[15].setEnabled(true);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (this.i == 0) {
			Object[] bs = { "Yes", "No" };

			int k = JOptionPane.showOptionDialog(Main.win,
					"Have you finished designate machine?", "Tip", 2, 3, null,
					bs, null);

			if (k == 0) {
				setState(1);
			}
			return;
		}
		if (this.i == 1) {
			Object[] bs = { "Yes", "No" };

			int k = JOptionPane.showOptionDialog(Main.win,
					"Have you finished identifying domains?", "Tip", 2, 3,
					null, bs, null);

			if (k == 0) {
				setState(2);
			}
			return;
		}
		if (this.i == 2) {
			Object[] bs = { "Yes", "No" };

			int k = JOptionPane.showOptionDialog(Main.win,
					"Have you finished identifying interactions?", "Tip", 2, 3,
					null, bs, null);

			if (k == 0) {
				setState(3);
			}
			return;
		}
		if (this.i == 3) {
			//if (checkContextDiagram()) {  ////////////////////////////
			if (checkContextDiagram()) {
			Object[] bs = { "OK" };

				int k = JOptionPane.showOptionDialog(Main.win,
						"The diagram is correct!", "Tip", 0, 1, null, bs, null);

				setState(4);
				Diagram kk = Main.win.myContextDiagram
						.convertToProblemDiagram("ProblemDiagram");

				kk.setLineName();
				Main.win.myProblemDiagram = kk;
				MyPane xin = new MyPane(kk);
				Main.win.myDisplayPane.addPane(xin, "ProblemDiagram");
				Main.win.myInfoPane.treeAdd(1, 0, "");
			} else {
				Object[] bs = { "OK" };

				int k = JOptionPane.showOptionDialog(Main.win,
						"The diagram is wrong!", "Tip", 0, 0, null, bs, null);

				setState(0);
			}
			return;
		}
		
		
		
		if (this.i == 31) {
		/*Line a[]=new Line[20];
			Line b[]=new Line[20];
			int l=0;
			int q=0;
			LinkedList<Rect> domlist=new LinkedList<>();
			Line line2 = null;
			Line line3=null;
			domlist=Persist.getProDom2(Main.win.myProblemDiagram);
			LinkedList<Oval> reqlist=new LinkedList<>();	
			reqlist=Persist.getReqOval(Main.win.myProblemDiagram);
			StringBuffer linexy=new StringBuffer();
		 for(int i=0;i<domlist.size();i++){
			 for(int j=0;j<reqlist.size();j++){
			Rect n=domlist.get(i);
			System.out.println("pppppppp");
			System.out.println(domlist.get(i).getShortName());
			System.out.println("pppppppp");
			Oval m=reqlist.get(j);
			line2 = Main.win.myProblemDiagram.findALine(n, m);
			line3=Main.win.myProblemDiagram.findALine(m, n);
			if(line2!=null) {a[l]=line2; l++;}
			if(line3!=null){a[q]=line3;q++;}
				
			 }}
			
			
			*/	
		if (checkContextDiagram()) {
			Object[] bs = { "OK" };

				int k = JOptionPane.showOptionDialog(Main.win,
						"The diagram is correct!", "Tip", 0, 1, null, bs, null);

				
				Diagram tmp_d = new Diagram("");
				Persist.save(new File("tmp"), Main.win.myProblemDiagram);
				tmp_d = Persist.load(new File("tmp"));
				
				Diagram kk = convertToProblemDiagram2("problemdiagram" );////
                setState(4);
				kk.setLineName();
				Main.win.myProblemDiagram = kk;
				MyPane xin = new MyPane(kk);
			
				Main.win.myDisplayPane.removePane("problemdiagram");/////	
				Main.win.myDisplayPane.addPane(xin, "problemdiagram");
				Main.win.myInfoPane.treeAdd(1, 0, "");
			} else {
				Object[] bs = { "OK" };

				int k = JOptionPane.showOptionDialog(Main.win,
						"The diagram is wrong!", "Tip", 0, 0, null, bs, null);

				setState(0);
			}
			return;
		}
		
		
		
		
		if (this.i == 4) {
			Object[] bs = { "Yes", "No" };

			int k = JOptionPane.showOptionDialog(Main.win,
					"Have you finished identifying requirements?", "Tip", 2, 3,
					null, bs, null);

			if (k == 0) {
				setState(5);
			}
			return;
		}
		if (this.i == 5) {
			Object[] bs = { "Yes", "No" };

			int k = JOptionPane.showOptionDialog(Main.win,
					"Have you finished indentifying references?", "Tip", 2, 3,
					null, bs, null);

			if (k == 0) {
				setState(6);
			}
			return;
		}
		if (this.i == 6) {
			//if (checkProblemDiagram()) {                 //////////////////////////////
		/*	if (checkProblemDiagram()) {//////////////////////////////////// 
				Object[] bs = { "OK" };

				int k = JOptionPane.showOptionDialog(Main.win,
						"The diagram is correct!", "Tip", 0, 1, null, bs, null);

				setState(7);
				LinkedList ll = Main.win.myProblemDiagram.getRequirements();
				for (int j = 0; j <= ll.size() - 1; j++) {
					Oval tmp_o = (Oval) ll.get(j);
					IntDiagram dd = new IntDiagram("ScenarioGraph" + (j + 1),
							tmp_o.getBiaohao());

					IntPane intp = new IntPane(dd, 0, Main.win.myProblemDiagram);
					Main.win.intPane_l.add(intp);
					Main.win.myDisplayPane.addPane(intp, intp.dd.getTitle());
					Main.win.myInfoPane.treeAdd(2, j + 1,
							"req" + dd.getBiaohao());
					setState(7);
				}
			} else {
				Object[] bs = { "OK" };

				int k = JOptionPane.showOptionDialog(Main.win,
						"The diagram is wrong!", "Tip", 0, 0, null, bs, null);

				setState(4);
			}
			return;
		}
		if (this.i == 9) {
			stragety1();
			return;
		}
		if (this.i == 7) {
			Object[] bs = { "Yes", "No" };

			int k = JOptionPane.showOptionDialog(Main.win,
					"Have you finished drawing scenario graphs?", "Tip", 2, 3,
					null, bs, null);

			if (k == 0) {
				setState(8);
				JOptionPane.showMessageDialog(Main.win,
						"You should check each graph by right click!");
			}

			return;
		}
		if (this.i == 8) {
			boolean hao = true;
			for (int i = 0; i <= Main.win.intPane_l.size() - 1; i++) {
				IntPane ip = (IntPane) Main.win.intPane_l.get(i);
				hao = (hao) && (ip.zhengque);
			}
			if (hao) {
				setState(9);
				JOptionPane.showMessageDialog(Main.win,
						"You have checked all the scenario graphs!");

				return;
			}

			JOptionPane.showMessageDialog(Main.win,
					"You should check all the scenariographs!");

			setState(7);
			return;
		}

		if (this.i == 10) {
			setallnon();
			Main.win.projection();
			JOptionPane.showMessageDialog(Main.win, "OK!");

			setState(11);
			return; */
		}
	}

	private void stragety2() {
		Integer s = stragety2OR();
		if (s != null) {
			String message = "Subproblem"
					+ s.intValue()
					+ " has dynamic and static domain at same time!\nSo introduce model domain to seperate two concerns.";
			JOptionPane.showMessageDialog(Main.win, message);

			int i = s.intValue();
			MyPane ip = (MyPane) Main.win.subpd_l.get(i);
			Diagram dd = ip.dd;
			Diagram tmp_d = new Diagram("");
			Persist.save(new File("tmp"), dd);
			tmp_d = Persist.load(new File("tmp"));
			tmp_d.setTitle(dd.getTitle() + "1");
			MyPane xin = new MyPane(tmp_d);
			xin.setSub(true);
			Main.win.subpd_l.add(xin);
			Main.win.myDisplayPane.addPane(xin, tmp_d.getTitle());
			Main.win.myInfoPane.treeAdd1(1, 1, dd.getTitle());

			IntPane mp = (IntPane) Main.win.intPane_l.get(i);
			IntDiagram tt = mp.dd;
			Persist.save1(new File("tmp"), tt);
			IntDiagram dd_i = Persist.load1(new File("tmp"));
			dd_i.setTitle("ScenarioGraph"
					+ new Integer(dd.getTitle().substring(17)).intValue() + "1");

			IntPane intp = new IntPane(dd_i, 1, tmp_d);
			Main.win.intPane_l.add(intp);
			Main.win.myDisplayPane.addPane(intp, intp.dd.getTitle());

			Main.win.myInfoPane.treeAdd1(0, 1, "ScenarioGraph"
					+ new Integer(dd.getTitle().substring(17)).intValue());

			tmp_d = new Diagram(dd.getTitle() + "2");
			MyPane xin1 = new MyPane(tmp_d);
			xin1.setSub(true);
			Main.win.subpd_l.add(xin1);
			Main.win.myDisplayPane.addPane(xin1, tmp_d.getTitle());
			Main.win.myInfoPane.treeAdd1(1, 2, dd.getTitle());

			dd_i = new IntDiagram(
					"ScenarioGraph"
							+ new Integer(dd.getTitle().substring(17)).intValue()
							+ "2", 0);

			intp = new IntPane(dd_i, 1, tmp_d);
			Main.win.intPane_l.add(intp);
			Main.win.myDisplayPane.addPane(intp, intp.dd.getTitle());
			Main.win.myInfoPane.treeAdd1(0, 2, "ScenarioGraph"
					+ new Integer(dd.getTitle().substring(17)).intValue());

			xin.newMypane();
		}
	}

	private boolean testDiagram2(Diagram dd) {
		for (int i = 0; i <= dd.components.size() - 1; i++) {
			Shape tmp_s = (Shape) dd.components.get(i);
			if (tmp_s.shape == 2) {
				Line tmp_l = (Line) tmp_s;
				if (tmp_l.getState() != 0) {
					boolean yin = false;
					boolean yue = false;
					for (int j = 0; j <= tmp_l.phenomenons.size() - 1; j++) {
						Phenomenon tmp_p = (Phenomenon) tmp_l.phenomenons
								.get(j);
						if (tmp_p.getConstraining()) {
							yue = true;
						} else {
							yin = true;
						}
					}
					if ((yin) && (yue)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private Integer stragety2OR() {
		for (int i = 0; i <= Main.win.subpd_l.size() - 1; i++) {
			MyPane ip = (MyPane) Main.win.subpd_l.get(i);
			Diagram dd = ip.dd;
			if (testDiagram2(dd)) {
				return new Integer(i);
			}
		}
		return null;
	}

	private void stragety1() {
		String s = stragety1OR(Main.win.myProblemDiagram);
		if (s == null) {
			JOptionPane.showMessageDialog(Main.win,
					"All of the designed domain are created!");

			setState(10);
			return;
		}

		JOptionPane
				.showMessageDialog(
						Main.win,
						s
								+ " is no created!\nPlease identify another domain to create it!");

		FatherPane kk = Main.win.myDisplayPane.getMyPane("ProblemDiagram");
		if (kk != null) {
			Oval tmp_o = new Oval(300, 300);
			Rect tmp_r = new Rect(150, 300);
			Main.win.myProblemDiagram.add(tmp_o);
			Main.win.myProblemDiagram.add(tmp_r);
			tmp_r.setState(0);
			tmp_r.setText(Data.GD_TEXT);
			int j = Main.win.intPane_l.size();
			IntDiagram dd = new IntDiagram("ScenarioGraph" + (j + 1),
					tmp_o.getBiaohao());

			IntPane intp = new IntPane(dd, 0, Main.win.myProblemDiagram);
			Main.win.intPane_l.add(intp);
			Main.win.myDisplayPane.addPane(intp, intp.dd.getTitle());
			Main.win.myInfoPane.treeAdd(2, j + 1, "req" + dd.getBiaohao());
			Main.win.myDisplayPane.setSelected(kk);
		}
	}

	private String stragety1OR(Diagram dd) {
		for (int i = 0; i <= dd.components.size() - 1; i++) {
			Shape tmp_s = (Shape) dd.components.get(i);
			if (tmp_s.shape == 0) {
				Rect tmp_r = (Rect) tmp_s;
				if (tmp_r.getState() == 1) {
					boolean zhaodao = false;
					for (int j = 0; j <= dd.components.size() - 1; j++) {
						Shape tmps = (Shape) dd.components.get(j);
						if (tmps.shape == 2) {
							Line tmpl = (Line) tmps;
							if ((tmpl.getState() != 2)
									|| (!tmpl.to.equals(tmp_r)))
								continue;
							zhaodao = true;
						}

					}

					if (!zhaodao) {
						return tmp_r.getText();
					}
				}
			}
		}
		return null;
	}

	boolean checkContextDiagram() {
		return new Ontology_c().check(Main.win.myContextDiagram,
				"ContextDiagram");
	}

	boolean checkProblemDiagram() {
		return new Ontology_c().check(Main.win.myProblemDiagram,
				"ProblemDiagram");
	}
	
	
	
	
	
	public Diagram convertToProblemDiagram2(String name) {  /////add---
		String []core=Persist.getline2core(Main.win.myProblemDiagram);
		String []core1=Persist.getline2core1(Main.win.myProblemDiagram);
		String l=Persist.getline2core1s(Main.win.myProblemDiagram);
		System.out.println("0000"+core.length);
		System.out.println("1223"+core1.length);
		System.out.println("kkkkkkkkkk"+l);
		for(int i=0;i<core.length;i++){
			
			System.out.println(core[i]);
			
		}
        for(int i=0;i<core1.length;i++){
			
			System.out.println(core1[i]);
			
		}
		int maxx = 0;
		int miny = 0;
		int maxy = 0;
		Diagram tmp_d = new Diagram("");
		Persist.save(new File("tmp"), Main.win.myContextDiagram);
		tmp_d = Persist.load(new File("tmp"));
		tmp_d.setTitle(name);

		LinkedList ll = Main.win.myProblemDiagram.getRequirements();
		
	
			Oval tmp_o = (Oval) ll.get(0);


			
			
			LinkedList<Rect> domlist=new LinkedList<>();
			domlist=Persist.getProDom2(Main.win.myProblemDiagram);
			LinkedList<Oval> reqlist=new LinkedList<>();	
			reqlist=Persist.getReqOval(Main.win.myProblemDiagram);
			        Line a[]=new Line[20];
					Line b[]=new Line[20];
					int w=0;
					int q=0;
			
					Line line2 = null;
					Line line3=null;		
						
		         Phenomenon []yy=new Phenomenon[10];
		         int con=0;
				 for(int z=0;z<domlist.size();z++){
					 for(int j=0;j<reqlist.size();j++){
					Rect n=domlist.get(z);
					System.out.println("pppppppp");
					System.out.println(domlist.get(z).getShortName());
					System.out.println("pppppppp");
					Oval m=reqlist.get(j);
					line2 = Main.win.myProblemDiagram.findALine(n, m);
					line3=Main.win.myProblemDiagram.findALine(m, n);
					if(line2!=null) {a[w]=line2;w++;}
					if(line3!=null){b[q]=line3;q++;}}	
					 }
			
					
					
					
					
			int k=0;
			int s=0;
			int t=0;
			int h=0;
		    for (int i = 0; i < tmp_d.components.size(); i++) {
				Shape tmp = (Shape) tmp_d.components.get(i);
				if (tmp.shape == 2) {
					continue;
				}
				Rect tmp1 = (Rect) tmp;
				
				if (tmp1.getState() != 2) {
					
					Line temp=new Line(tmp_o, tmp1, 1);
					if(k<core.length){
					temp.core=core[k];
					System.out.println("coooo:"+temp.core);
					k++;}
					if(l!=" "){
					 if(s<core1.length){
						temp.core1=core1[s];
						System.out.println("coooo1:"+temp.core1);
						s++;}}
					
					String []line1=Persist.getline1x(Main.win.myProblemDiagram).split(";");
					int l1=line1.length;
				  
					if(h<Main.win.myProblemDiagram.getPhenomenon().size()-1)
					{temp.phenomenons.add((Phenomenon)Main.win.myProblemDiagram.getPhenomenon().get(h));
					temp.phenomenons.add((Phenomenon)Main.win.myProblemDiagram.getPhenomenon().get(h+1));
					h+=2;}
					tmp_d.add(temp);
					
					
				}
			}

			for (int j2 = 0; j2 <= ll.size() - 1; j2++) {
			Oval o = (Oval) ll.get(j2);
			tmp_d.add(o);
		}	

		//for(int i=0;a[i]!=null;i++)
			//tmp_d.add(a[i]);
			//for(int i=0;b[i]!=null;i++)
				//tmp_d.add(b[i]);
		 // addline2(Main.win.myProblemDiagram,tmp_d);
			//tmp_d.add(tmp_o);
	
		
		
		return tmp_d;
	}

	
	
	
	
	
	
	public static String addline2(Diagram th,Diagram now) {
		
		LinkedList<Rect> domlist=new LinkedList<>();
		Line line2 = null;
		Line line3=null;
		domlist=Persist.getProDom2(Main.win.myProblemDiagram);
		LinkedList<Oval> reqlist=new LinkedList<>();	
		reqlist=Persist.getReqOval(th);
		StringBuffer linexy=new StringBuffer();
	 for(int i=0;i<domlist.size();i++){
		 for(int j=0;j<reqlist.size();j++){
		Rect n=domlist.get(i);
		System.out.println("pppppppp");
		System.out.println(domlist.get(i).getShortName());
		System.out.println("pppppppp");
		Oval m=reqlist.get(j);
		line2 = th.findALine(n, m);
		line3=th.findALine(m, n);
		if(line2==null)System.out.print("mei qu ");
		else
		{
		 linexy.append(line2);
		 now.add(line2);  //��ͼ�����
		 linexy.append(';');
		}
		if(line3==null)System.out.print("mei lai ");
		else
		{
		linexy.append(line3);
		now.add(line3);  //��ͼ�����
		linexy.append(';');
	    }
	 }
	 }
		return String.valueOf(linexy);
	}
	
	
	
	
	
	
	
	
	public  void setLineName() {
		int j = 0;
		for (int i = 0; i < Main.win.myContextDiagram.components.size(); i++) {
			Shape tmp = (Shape) Main.win.myContextDiagram.components.get(i);
			if (tmp.shape == 2) {
				String s = "" + (char) (97 + j);
				j++;
				((Line) tmp).name = s;
				((Line) tmp).core = "jj";
				((Line) tmp).core1 = "kk";
			}
		}
	}
	
	
}