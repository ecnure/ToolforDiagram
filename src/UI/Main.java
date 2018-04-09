package UI;

import Shape.*;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;

public class Main extends JFrame implements ActionListener {
	public static String errmes = "";
	public static int errstate = 0;// 0û���� 1�ߴ��� 2���󽻻�����
	JMenuBar menuBar = new JMenuBar();
	JMenu file = new JMenu("File");
	JMenu help = new JMenu("Help");
	JMenu ontology = new JMenu("Ontology");
	JMenuItem save = new JMenuItem("Save");
	JMenuItem open = new JMenuItem("Open");
	JMenuItem news = new JMenuItem("New");
	JMenuItem exit = new JMenuItem("Exit");
	JMenuItem load = new JMenuItem("Load");
	JMenuItem check = new JMenuItem("Check");
	JMenuItem show = new JMenuItem("Show");
	JMenuItem about = new JMenuItem("About");
	JMenu redo = new JMenu("Redo");
	JMenuItem redoMachine=new JMenuItem("redoMachine");
	JMenuItem redoProdomain=new JMenuItem("redoProdomain");
	JMenuItem redoIntersaction=new JMenuItem("redoIntersaction");
	JMenuItem redoRequirement=new JMenuItem("redoRequirement");
	JMenuItem redoReference=new JMenuItem("redoReference");
	DrawPane myDrawPane = new DrawPane();
	InfoPane myInfoPane = new InfoPane();
	public IntPane nowIntPane;
	public ClockDiagram myClockDiagram;          /////////////
	public LinkedList intPane_l = new LinkedList();
	public LinkedList subpd_l = new LinkedList();
	/*************************************************************************/
	ClockDiagram cd;
	public LinkedList<IntDiagram> myIntDiagram = new LinkedList<IntDiagram>();
	public InstantPane instantPane = null;
	/*************************************************************************/
	DisplayPane myDisplayPane = new DisplayPane();
	JSplitPane all = new JSplitPane();
	JSplitPane rightp = new JSplitPane();
	JToolBar toolbar = new JToolBar();
	JToggleButton b_machine;
	JToggleButton undomachine;
	JToggleButton b_givendomain;
	JToggleButton b_designeddomain;
	JToggleButton b_requirement;
	JToggleButton b_interface;
	JToggleButton b_requirementconstraint;
	JToggleButton b_requirementreference;
	//JToggleButton b_hong;
	//JToggleButton b_lan;
	//JToggleButton b_lv;
	//JToggleButton b_cheng;
	//JToggleButton b_zi;
	//JToggleButton b_cheng_y;
	static Main win;
	JFileChooser chooser;
	String nameOfProject;
	static String pathOfProject;
	public Diagram myContextDiagram;
	public Diagram myProblemDiagram;

	public static void main(String[] args) {
		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			UIManager.put("RootPane.setupButtonVisible", false);
		} catch (Exception e) {
			// TODO exception
		}
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException ex) {
		} catch (IllegalAccessException ex) {
		} catch (InstantiationException ex) {
		} catch (ClassNotFoundException ex) {
		}
		win = new Main();
		win.setVisible(true);
		win.setVisible(false);
		new WelcomeWindow().open();
		win.setVisible(true);
		win.setExtendedState(6);
		win.addWindowListener(new MyAdapter(win));
	}

	private void event_save() {
		Persist.save(this.nameOfProject, pathOfProject + "/"
				+ this.nameOfProject + ".yb");

		if (this.myContextDiagram != null) {
			Persist.save(
					new File(pathOfProject + "/"
							+ this.myContextDiagram.getTitle()),
					this.myContextDiagram);
			File file = new File("C:\\Users\\13652\\Desktop\\newcontextdiagram.xml");
			Persist.createXML1(file,this.myContextDiagram);
		}
		
		//////////////////////////////////////////////////////
		if (this.myClockDiagram != null) {
			Persist.save(
					new File(pathOfProject + "/"
							+ this.myClockDiagram.getTitle()),
					this.myClockDiagram);
		}
		
		
		

		System.out.println(this.myContextDiagram.getTitle());
		if (this.myProblemDiagram != null) {
			Persist.save(
					new File(pathOfProject + "/"
							+ this.myProblemDiagram.getTitle()),
					this.myProblemDiagram);
			File file = new File("C:\\Users\\13652\\Desktop\\newProblemdiagram.xml");
			Persist.createXML(file,this.myProblemDiagram);
		}

		for (int i = 0; i < this.intPane_l.size(); i++) {
			IntPane kk = (IntPane) this.intPane_l.get(i);
			if (kk.dd.getTitle().length() > 14) {
				continue;
			}
			Persist.save1(new File(pathOfProject + "/" + kk.dd.getTitle()),
					kk.dd);
		}

		for (int i = 0; i <= this.subpd_l.size() - 1; i++) {
			MyPane kk = (MyPane) this.subpd_l.get(i);
			if (kk.dd.getTitle().length() > 14) {
				continue;
			}
			Persist.save(new File(pathOfProject + "/" + kk.dd.getTitle()),
					kk.dd);
		}
	}

	public void projection() {
		for (int i = 0; i <= this.intPane_l.size() - 1; i++) {
			IntPane ip = (IntPane) this.intPane_l.get(i);
			Diagram dd = projection1(ip);
			if (dd == null) {
				continue;
			}
			dd.setTitle("SubProblemDiagram" + (i + 1));
			MyPane xin = new MyPane(dd);
			xin.setSub(true);
			this.subpd_l.add(xin);
			win.myDisplayPane.addPane(xin, "SubProblemDiagram" + (i + 1));
			win.myInfoPane.treeAdd(3, i + 1, "req" + (i + 1));
		}
	}

	private Diagram projection1(IntPane ip) {
		LinkedList shanchu = new LinkedList();
		Persist.save(new File("tmp"), this.myProblemDiagram);
		Diagram tmp_d = Persist.load(new File("tmp"));
		IntDiagram dd = ip.dd;
		String s = dd.getTitle();
		int number = new Integer(s.substring(13)).intValue();
		LinkedList jiaohu = dd.getJiaohu();
		LinkedList ll = win.myProblemDiagram.getPhenomenon();
		for (int i = 0; i <= tmp_d.components.size() - 1; i++) {
			Shape tmp_s = (Shape) tmp_d.components.get(i);
			if (tmp_s.shape == 0) {
				Rect tmp_r = (Rect) tmp_s;
				if (tmp_r.getState() != 2) {
					boolean zhaodao = false;
					for (int j = 0; j <= jiaohu.size() - 1; j++) {
						Jiaohu tmp_jh = (Jiaohu) jiaohu.get(j);
						int num = tmp_jh.getNumber();
						Phenomenon tmp_ph = null;
						for (int k = 0; k <= ll.size() - 1; k++) {
							Phenomenon tttt = (Phenomenon) ll.get(k);
							if (tttt.getBiaohao() == num) {
								tmp_ph = tttt;
							}
						}
						if (tmp_ph == null) {
							return null;
						}
						if ((!tmp_ph.getFrom().getText()
								.equals(tmp_r.getText()))
								&& (!tmp_ph.getTo().getText()
										.equals(tmp_r.getText())))
							continue;
						zhaodao = true;
						break;
					}

					if (!zhaodao) {
						shanchu.add(tmp_r);
					}
				}
			}
			if (tmp_s.shape == 2) {
				Line tmp_l = (Line) tmp_s;
				for (int j = 0; j <= tmp_l.phenomenons.size() - 1; j++) {
					Phenomenon tmp_ph = (Phenomenon) tmp_l.phenomenons.get(j);
					boolean zhaodao = false;
					for (int k = 0; k <= jiaohu.size() - 1; k++) {
						Jiaohu tmp_jh = (Jiaohu) jiaohu.get(k);
						int num = tmp_jh.getNumber();
						Phenomenon tmp = null;
						for (int h = 0; h <= ll.size() - 1; h++) {
							Phenomenon tttt = (Phenomenon) ll.get(h);
							if (tttt.getBiaohao() == num) {
								tmp = tttt;
							}
						}
						if (tmp == null) {
							System.out.println(num);
							return null;
						}
						if (tmp.getName().equals(tmp_ph.getName())) {
							zhaodao = true;
							break;
						}
					}
					if (!zhaodao) {
						tmp_l.phenomenons.remove(j);
						j--;
					}
				}
			}
			if (tmp_s.shape == 1) {
				Oval tmp_o = (Oval) tmp_s;
				String title = "req" + tmp_o.getBiaohao();
				int ii = new Integer(title.substring(3)).intValue();
				if (ii != number) {
					shanchu.add(tmp_o);
				}
			}
		}
		for (int i = 0; i <= shanchu.size() - 1; i++) {
			Shape tmp_r = (Shape) shanchu.get(i);
			for (int j = 0; j <= tmp_d.components.size() - 1; j++) {
				Shape tmp_s = (Shape) tmp_d.components.get(j);
				if (tmp_r.equals(tmp_s)) {
					tmp_d.delete(tmp_r);
					break;
				}
			}
		}
		return tmp_d;
	}

	private void clear() {
		smooth();
		this.intPane_l = new LinkedList();
		this.subpd_l = new LinkedList();
		errmes = "";
		errstate = 0;
		this.myContextDiagram = null;
		this.myProblemDiagram = null;
		this.nowIntPane = null;
		this.myDisplayPane.desk.removeAll();
		this.myInfoPane.desk.removeAll();
	}

	private void event_open() {
		
		/********************************************************************/
		instantPane=null;
		/********************************************************************/
		
		this.chooser.setFileSelectionMode(0);
		this.chooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return (f.getName().endsWith(".yb")) || (f.isDirectory());
			}

			public String getDescription() {
				return "�����ļ�(*.yb)";
			}
		});
		int r = this.chooser.showOpenDialog(this);
		if (r == 0) {
			File f = this.chooser.getSelectedFile();
			if ((f == null) || (f.getName().equals(""))) {
				return;
			}
			clear();
			String path = f.getPath();
			String name = Persist.load(path);
			if ((name == null) || (name.equals(""))) {
				return;
			}
			this.nameOfProject = name;
			pathOfProject = path.substring(0, path.length() - 1 - name.length()
					- 3);

			setTitle(this.nameOfProject);
			Diagram t = Persist
					.load(new File(pathOfProject + "/ContextDiagram"));

			this.myInfoPane.treeInit();
			if (t == null) {
				this.myDrawPane.setState(0);
				String ss = "ContextDiagram";
				Diagram dd = new Diagram(ss);
				MyPane xin = new MyPane(dd);
				win.myDisplayPane.addPane(xin, ss);
				win.myContextDiagram = dd;
				win.myInfoPane.treeAdd(0, 0, "");
				return;
			}

			this.myContextDiagram = t;
			this.myDisplayPane.addPane(new MyPane(t), t.getTitle());

			/********************************************************************/
			cd = new ClockDiagram(t);
			/********************************************************************/

			this.myInfoPane.treeAdd(0, 0, "");

			t = Persist.load(new File(pathOfProject + "/ProblemDiagram"));

			if (t == null) {
				this.myDrawPane.setState(0);
				return;
			}

			this.myProblemDiagram = t;
			this.myDisplayPane.addPane(new MyPane(t), t.getTitle());
			this.myInfoPane.treeAdd(1, 0, "");
			this.myDrawPane.setState(4);

			/*************************************************************************/
			myIntDiagram.clear();
			/*************************************************************************/
			File file = new File(pathOfProject);
			String[] filelist = file.list();
		/*	for (int j = 0; j < filelist.length; j++) {
				if ((!filelist[j].startsWith("ScenarioGraph"))
						|| (filelist[j].length() > 14)) {
					continue;
				}
				File readfile = new File(pathOfProject + "/" + filelist[j]);
				if (!readfile.isDirectory()) {
					IntDiagram tt = Persist.load1(new File(pathOfProject + "/"
							+ filelist[j]));

					myIntDiagram.add(tt);

					IntPane kd = new IntPane(tt, 1, win.myProblemDiagram);

					this.myDisplayPane.addPane(kd, tt.getTitle());

					this.intPane_l.add(kd);
					this.myInfoPane.treeAdd(2,
							new Integer(filelist[j].substring(13)).intValue(),
							"req" + tt.getBiaohao());

					this.myDrawPane.setState(7);
				}
			}*/

			for (int j = 0; j < filelist.length; j++) {
				if ((!filelist[j].startsWith("SubProblemDiagram"))
						|| (filelist[j].length() > 18)) {
					continue;
				}
				File readfile = new File(pathOfProject + "/" + filelist[j]);
				if (!readfile.isDirectory()) {
					Diagram ddd = Persist.load(new File(pathOfProject + "/"
							+ filelist[j]));
					MyPane xin = new MyPane(ddd);
					xin.setSub(true);
					this.subpd_l.add(xin);
					win.myDisplayPane.addPane(xin, filelist[j]);
					int num = new Integer(filelist[j].substring(17)).intValue();
					win.myInfoPane.treeAdd(3, num, "req" + num);
					this.myDrawPane.setState(11);
				}
			}
			/********************************************************************/
			//this.myDisplayPane.addPane(new MyPane(cd), cd.getTitle());
			/********************************************************************/
		}
	}

	private void event_new() {
		String s = JOptionPane.showInputDialog(this,
				"Please input the name of the project!", "Input", 2);

		if ((s == null) || (s.equals(""))) {
			return;
		}
		this.chooser.setFileSelectionMode(1);
		int r = this.chooser.showSaveDialog(this);
		if (r == 0) {
			clear();
			this.nameOfProject = s;
			setTitle(s);
			String ss = this.chooser.getSelectedFile().getPath();
			pathOfProject = ss;
			Persist.save(this.nameOfProject, pathOfProject + "/"
					+ this.nameOfProject + ".yb");
		} else {
			return;
		}
		// Shape.
		Data.first = 1;
		// Shape.
		Data.firstq = 1;
		setButtonState(0);
		this.myInfoPane.treeInit();
		this.myDrawPane.setState(0);
		String ss = "ContextDiagram";
		Diagram dd = new Diagram(ss);
		MyPane xin = new MyPane(dd);
		win.myDisplayPane.addPane(xin, ss);
		win.myContextDiagram = dd;
		win.myInfoPane.treeAdd(0, 0, "");
	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Exit")) {
			System.exit(0);
		}
		if (e.getActionCommand().equals("Draw_GivenDomain")) {
			this.myDisplayPane.setState(1);
		}
		if (e.getActionCommand().equals("Draw_DesignedDomain")) {
			this.myDisplayPane.setState(2);
		}
		if (e.getActionCommand().equals("Draw_Machine")) {
			this.myDisplayPane.setState(3);
		}
		if (e.getActionCommand().equals("ReDraw_Machine")) {
			
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
			
			
			
			
			//this.myProblemDiagram.refreshContextDiagram();
		  
		   
			this.myDrawPane.setState(31); 
			//this.myProblemDiagram=null;
			//this.myDisplayPane.desk.remove(this.myDisplayPane.getMyPane("problemdiagram"));
			JOptionPane.showMessageDialog(null, "���޸�������ͼ��"); //////////////////////////////////////////////
		}
		if (e.getActionCommand().equals("Draw_Requirement")) {
			this.myDisplayPane.setState(4);
		}
		if (e.getActionCommand().equals("Draw_Interface")) {
			this.myDisplayPane.setState(5);
		}
		if (e.getActionCommand().equals("Draw_RequirementConstraint")) {
			this.myDisplayPane.setState(7);
		}
		if (e.getActionCommand().equals("Draw_RequirementReference")) {
			this.myDisplayPane.setState(6);
		}
		//if (e.getActionCommand().equals("Cheng_y")) {
			//if (this.nowIntPane == null) {
				//this.b_hong.setSelected(false);
				//return;
			//}

//			this.nowIntPane.setHua(1);
	//	}

		//if (e.getActionCommand().equals("Hong")) {
			//if (this.nowIntPane == null) {
				//this.b_hong.setSelected(false);
				//return;
			//}

			//this.nowIntPane.setLeixing(0);
		//}

		//if (e.getActionCommand().equals("Lan")) {
			//if (this.nowIntPane == null) {
				//this.b_lan.setSelected(false);
				//return;
			//}

//			this.nowIntPane.setLeixing(1);
	//	}

		//if (e.getActionCommand().equals("Lv")) {
			//if (this.nowIntPane == null) {
				//this.b_lv.setSelected(false);
				//return;
			//}

//			this.nowIntPane.setLeixing(2);
	//	}

	//	if (e.getActionCommand().equals("Cheng")) {
		//	if (this.nowIntPane == null) {
			//	this.b_cheng.setSelected(false);
			//	return;
		//	}

//			this.nowIntPane.setLeixing(3);
	//	}

		//if (e.getActionCommand().equals("Zi")) {
		//	if (this.nowIntPane == null) {
			//	this.b_zi.setSelected(false);
			//	return;
		//	}

			//this.nowIntPane.setLeixing(4);
	//	}

		if (e.getActionCommand().equals("New")) {
			event_new();
		}
		if (e.getActionCommand().equals("Open")) {
			event_open();
		}
		if (e.getActionCommand().equals("redoMachine")) {
			//this.myDrawPane.setState(0);
			this.myContextDiagram.refreshContextDiagram();
			JOptionPane.showMessageDialog(null, "�ĺ��ˣ���");
		}
		if (e.getActionCommand().equals("redoProdomain")) {
			this.myDrawPane.setState(1);
			JOptionPane.showMessageDialog(null, "��problemdomains��");
		}
		if (e.getActionCommand().equals("redoIntersaction")) {
			this.myDrawPane.setState(2);
			JOptionPane.showMessageDialog(null, "��Intersaction����");
		}
		if (e.getActionCommand().equals("redoRequirement")) {
			this.myDrawPane.setState(4);
			JOptionPane.showMessageDialog(null, "��Requirements��");
		}
		if (e.getActionCommand().equals("redoReference")) {
			this.myDrawPane.setState(5);
			JOptionPane.showMessageDialog(null, "��Reference��");
		}
		if (e.getActionCommand().equals("Save")) {
			event_save();
		}
		if (e.getActionCommand().equals("Load")) {
			this.myDrawPane.event_load();
			this.show.setEnabled(true);
		}
		if (e.getActionCommand().equals("Check")) {
			this.myDrawPane.event_check();
		}
		if (e.getActionCommand().equals("Show")) {
			new OntologyShow().show();
		}
		//if (e.getActionCommand().equals("UndoDom")) {
			//this.myContextDiagram.delete(Persist.getProDom2(1));
	//	}
		if (e.getActionCommand().equals("About")) {
			String s = "DPTool Version1.0\n1.It is a graphical supporting tool for describing and analyzing software problem and performing the problem projection.\n2.It provides guidance of problem description and projection.\n3.It brings scenario into PF for conducting a scenario based problem projection and implements scenario-extended problem description and automated problem projection.\n4.Some checking techniques are included for ensuring the quality of the requirements document.\n";
			s = s
					+ "\nCopyright 2010 Academy of Mathematics and Systems Science, Chinese Academy of Sciences All Rights Reserved";
			s = s + "\nAuthor:Zhi Jin,Bin Yin,Xiaohong Chen";
			JOptionPane.showMessageDialog(this, s, "About PFTool", 1);
		}
	}

	public void setButtonState(int i) {
		this.undomachine.setEnabled(false);
		if (i == -2) {
			buttonClear();
			this.load.setEnabled(true);
		}
		if (i == -1) {
			buttonClear();
			this.news.setEnabled(true);
			this.open.setEnabled(true);
			this.load.setEnabled(true);
		}
		if (i == 0) {
			buttonClear();
			this.load.setEnabled(true);
			this.news.setEnabled(true);
			this.open.setEnabled(true);
			this.save.setEnabled(true);
			this.check.setEnabled(true);
			this.myDrawPane.jb1.setEnabled(true);
			this.b_machine.setEnabled(true);
			
		}
		if (i == 1) {
			buttonClear();
			this.load.setEnabled(true);
			this.news.setEnabled(true);
			this.open.setEnabled(true);
			this.save.setEnabled(true);
			this.check.setEnabled(true);
			this.myDrawPane.jb1.setEnabled(true);
			this.b_givendomain.setEnabled(true);
		}
		if (i == 2) {
			buttonClear();
			this.load.setEnabled(true);
			this.news.setEnabled(true);
			this.open.setEnabled(true);
			this.save.setEnabled(true);
			this.check.setEnabled(true);
			this.myDrawPane.jb1.setEnabled(true);
			this.b_interface.setEnabled(true);
			this.undomachine.setEnabled(true);
		}
		if (i == 3) {
			buttonClear();
			this.load.setEnabled(true);
			this.news.setEnabled(true);
			this.open.setEnabled(true);
			this.save.setEnabled(true);
			this.check.setEnabled(true);
			this.undomachine.setEnabled(true);
			this.myDrawPane.jb1.setEnabled(true);
		}
		if ((i == 4) || (i == 5)) {
			buttonClear();
			this.load.setEnabled(true);
			this.news.setEnabled(true);
			this.open.setEnabled(true);
			this.save.setEnabled(true);
			this.check.setEnabled(true);
			this.myDrawPane.jb1.setEnabled(true);
			this.undomachine.setEnabled(true);
			this.b_requirement.setEnabled(true);
		}
		if (i == 5) {
			buttonClear();
			this.load.setEnabled(true);
			this.news.setEnabled(true);
			this.open.setEnabled(true);
			this.save.setEnabled(true);
			this.check.setEnabled(true);
			this.myDrawPane.jb1.setEnabled(true);
			this.b_requirementconstraint.setEnabled(true);
			this.undomachine.setEnabled(true);
			this.b_requirementreference.setEnabled(true);
		}
		if (i == 6) {
			buttonClear();
			this.load.setEnabled(true);
			this.undomachine.setEnabled(true);
			this.news.setEnabled(true);
			this.open.setEnabled(true);
			this.save.setEnabled(true);
			this.check.setEnabled(true);
			this.myDrawPane.jb1.setEnabled(true);
		}
		if (i == 7) {
			buttonClear();
			//this.b_cheng.setEnabled(true);
			//this.b_cheng_y.setEnabled(true);
			//this.b_hong.setEnabled(true);
			//this.b_lan.setEnabled(true);
			//this.b_lv.setEnabled(true);
			//this.b_zi.setEnabled(true);
			this.load.setEnabled(true);
			this.news.setEnabled(true);
			this.open.setEnabled(true);
			this.save.setEnabled(true);
			this.undomachine.setEnabled(true);
			this.check.setEnabled(true);
			this.myDrawPane.jb1.setEnabled(true);
		}
		if (i == 8) {
			buttonClear();
			this.load.setEnabled(true);
			this.news.setEnabled(true);
			this.open.setEnabled(true);
			this.save.setEnabled(true);
			this.check.setEnabled(true);
			this.undomachine.setEnabled(true);
			this.myDrawPane.jb1.setEnabled(true);
		}
	}

	public void buttonClear() {
		//this.b_cheng.setEnabled(false);
		//this.b_cheng_y.setEnabled(false);
		this.b_designeddomain.setEnabled(false);
		this.b_givendomain.setEnabled(false);
		//this.b_hong.setEnabled(false);
		this.b_interface.setEnabled(false);
		//this.b_lan.setEnabled(false);
		//this.b_lv.setEnabled(false);
		this.b_machine.setEnabled(false);
		this.undomachine.setEnabled(false);
		this.b_requirement.setEnabled(false);
		this.b_requirementconstraint.setEnabled(false);
		this.b_requirementreference.setEnabled(false);
		//this.b_zi.setEnabled(false);
	}

	public void smooth() {
		this.b_machine.setSelected(false);
		this.undomachine.setSelected(false);
		this.b_givendomain.setSelected(false);
		this.b_designeddomain.setSelected(false);
		this.b_requirement.setSelected(false);
		this.b_interface.setSelected(false);
		this.b_requirementconstraint.setSelected(false);
		this.b_requirementreference.setSelected(false);
		//this.b_lv.setSelected(false);
		//this.b_hong.setSelected(false);
		//this.b_cheng.setSelected(false);
		//this.b_zi.setSelected(false);
		//this.b_lan.setSelected(false);
		//this.b_cheng_y.setSelected(false);
	}

	private void toolBarInit() {
		this.b_givendomain = new JToggleButton(new ImageIcon("E:/PF related mater/TimePF/Peast/src/icons/rect.jpg"));
		this.b_givendomain.addActionListener(this);
		this.b_givendomain.setToolTipText("GivenDomain");
		this.b_givendomain.setActionCommand("Draw_GivenDomain");

		this.b_designeddomain = new JToggleButton(new ImageIcon(
				"E:/PF related mater/TimePF/Peast/src/icons/drect.jpg"));
		this.b_designeddomain.addActionListener(this);
		this.b_designeddomain.setToolTipText("DesignedDomain");
		this.b_designeddomain.setActionCommand("Draw_DesignedDomain");

		this.b_machine = new JToggleButton(new ImageIcon("E:/PF related mater/TimePF/Peast/src/icons/machine.jpg"));
		this.b_machine.addActionListener(this);
		this.b_machine.setToolTipText("Machine");
		this.b_machine.setActionCommand("Draw_Machine");

		this.undomachine=new JToggleButton(new ImageIcon("E:/PF related mater/TimePF/Peast/src/icons/ch.png"));
		this.undomachine.addActionListener(this);
		this.undomachine.setToolTipText("RedoFromStart");
		this.undomachine.setActionCommand("ReDraw_Machine");
		
		this.b_requirement = new JToggleButton(new ImageIcon("E:/PF related mater/TimePF/Peast/src/icons/r.jpg"));
		this.b_requirement.addActionListener(this);
		this.b_requirement.setToolTipText("Requirement");
		this.b_requirement.setActionCommand("Draw_Requirement");

		this.b_interface = new JToggleButton(new ImageIcon("E:/PF related mater/TimePF/Peast/src/icons/i.jpg"));
		this.b_interface.addActionListener(this);
		this.b_interface.setToolTipText("Interface");
		this.b_interface.setActionCommand("Draw_Interface");

		this.b_requirementconstraint = new JToggleButton(new ImageIcon(
				"E:/PF related mater/TimePF/Peast/src/icons/rc.jpg"));
		this.b_requirementconstraint.addActionListener(this);
		this.b_requirementconstraint.setToolTipText("RequirementConstraint");
		this.b_requirementconstraint
				.setActionCommand("Draw_RequirementConstraint");

		this.b_requirementreference = new JToggleButton(new ImageIcon(
				"E:/PF related mater/TimePF/Peast/src/icons/rr.jpg"));
		this.b_requirementreference.addActionListener(this);
		this.b_requirementreference.setToolTipText("RequirementReference");
		this.b_requirementreference
				.setActionCommand("Draw_RequirementReference");

		//this.b_hong = new JToggleButton(new ImageIcon("E:/PF related mater/TimePF/Peast/src/icons/hong.jpg"));
		//this.b_hong.addActionListener(this);
		//this.b_hong.setActionCommand("Hong");
		//this.b_hong.setToolTipText("behEna");

		//this.b_lan = new JToggleButton(new ImageIcon("E:/PF related mater/TimePF/Peast/src/icons/lan.jpg"));
		//this.b_lan.addActionListener(this);
		//this.b_lan.setActionCommand("Lan");
		//this.b_lan.setToolTipText("behOrd");

		//this.b_lv = new JToggleButton(new ImageIcon("E:/PF related mater/TimePF/Peast/src/icons/lv.jpg"));
		//this.b_lv.addActionListener(this);
		//this.b_lv.setActionCommand("Lv");
		//this.b_lv.setToolTipText("synchrocity");

		//this.b_cheng = new JToggleButton(new ImageIcon("E:/PF related mater/TimePF/Peast/src/icons/cheng.jpg"));
		//this.b_cheng.addActionListener(this);
		//this.b_cheng.setActionCommand("Cheng");
		//this.b_cheng.setToolTipText("expOrd");

		//this.b_zi = new JToggleButton(new ImageIcon("E:/PF related mater/TimePF/Peast/src/icons/zi.jpg"));
		//this.b_zi.addActionListener(this);
		//this.b_zi.setActionCommand("Zi");
		//this.b_zi.setToolTipText("reqEna");

		//this.b_cheng_y = new JToggleButton(new ImageIcon("E:/PF related mater/TimePF/Peast/src/icons/y_cheng.jpg"));
		//this.b_cheng_y.addActionListener(this);
		//this.b_cheng_y.setActionCommand("Cheng_y");
		//this.b_cheng_y.setToolTipText("Int");

		this.toolbar.add(this.b_givendomain);
		this.toolbar.add(this.b_designeddomain);
		this.toolbar.add(this.b_machine);
		//this.toolbar.add(this.undomachine);
		this.toolbar.add(this.b_requirement);
		this.toolbar.add(this.b_interface);
		this.toolbar.add(this.b_requirementreference);
		this.toolbar.add(this.b_requirementconstraint);
		this.toolbar.addSeparator();
		this.toolbar.add(this.undomachine);
		//this.toolbar.add(this.b_hong);
		//this.toolbar.add(this.b_lan);
		//this.toolbar.add(this.b_lv);
		//this.toolbar.add(this.b_cheng);
		//this.toolbar.add(this.b_zi);
		//this.toolbar.add(this.b_cheng_y);
		this.toolbar.setFloatable(false);
		this.toolbar.setOrientation(0);
		getContentPane().add(this.toolbar, "North");
	}

	public Main() {
		super(
				"DPTool: A Tool for supporting the Problem Description and Projection");

		this.chooser = new JFileChooser();
		setJMenuBar(this.menuBar);
		this.menuBar.add(this.file);
		this.menuBar.add(this.ontology);
		this.menuBar.add(this.help);
		this.menuBar.add(this.redo);
		this.ontology.add(this.load);
		this.ontology.add(this.show);
		this.ontology.add(this.check);
		this.file.add(this.news);
		this.file.add(this.open);
		this.file.add(this.save);
		this.file.add(this.exit);
		this.redo.add(this.redoMachine);
		this.redo.add(this.redoProdomain);
		this.redo.add(this.redoIntersaction);
		this.redo.add(this.redoRequirement);
		this.redo.add(this.redoReference);
		this.help.add(this.about);
		this.news.addActionListener(this);
		this.open.addActionListener(this);
		this.redoMachine.addActionListener(this);
		this.redoProdomain.addActionListener(this);
		this.redoIntersaction.addActionListener(this);
		this.redoRequirement.addActionListener(this);
		this.redoReference.addActionListener(this);
		this.save.addActionListener(this);
		this.load.addActionListener(this);
		this.check.addActionListener(this);
		this.exit.addActionListener(this);
		this.about.addActionListener(this);
		this.show.addActionListener(this);

		this.show.setEnabled(false);
		this.news.setEnabled(false);
		this.open.setEnabled(false);
		this.redoMachine.setEnabled(true);
		this.redoProdomain.setEnabled(true);
		this.redoIntersaction.setEnabled(true);
		this.redoRequirement.setEnabled(true);
		this.redoReference.setEnabled(true);
		this.save.setEnabled(false);
		this.load.setEnabled(false);
		this.check.setEnabled(false);
		this.exit.setEnabled(true);
		this.all.setRightComponent(this.rightp);
		/********************************************************/
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		/********************************************************/
		this.all.setLeftComponent(this.myDrawPane);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		double width = d.getWidth();
		double height = d.getHeight();

		this.all.setDividerLocation((int) (width / 5.0D));
		this.rightp.setDividerLocation((int) (4.0D * width / 7.0D));
		this.rightp.setLeftComponent(this.myDisplayPane);
		this.rightp.setRightComponent(this.myInfoPane);
		getContentPane().add(this.all);
		toolBarInit();
		setButtonState(-2);
	}
}
