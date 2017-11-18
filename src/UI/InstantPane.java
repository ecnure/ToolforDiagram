package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import foundation.Checker;

import Shape.Changjing;
import Shape.Diagram;
import Shape.InstantRelation;
import Shape.Jiaohu;
import Shape.Line;
import Shape.Persist;
import Shape.Rect;
import Shape.Shape;

public class InstantPane extends FatherPane implements MouseMotionListener,
		MouseListener, ActionListener {

	// int state=9797;
	// InstantGraph ig;
	LinkedList<InstantGraph> igs = new LinkedList<InstantGraph>();
	// LinkedList<InstantGraph> comIgs=new LinkedList<InstantGraph>();

	static Diagram myProblemDiagram;
	boolean dragged = false;// 拖拽必备，标识拖拽状态量
	Jiaohu nowSelected = null;// 当前选择的交互

	Jiaohu from = null;
	Jiaohu to = null;
	int relation = 0;
	LinkedList<InstantRelation> relations = new LinkedList<InstantRelation>();
	LinkedList<InstantRelation> cRelations = new LinkedList<InstantRelation>();
	// 当前鼠标的位置
	int nowx = 0;
	int nowy = 0;
	// 拖拽前位置
	int previousX = 0;
	int previousY = 0;

	private int count = 1;// 图的数目

	JPopupMenu popupMenu = new JPopupMenu();
	JMenuItem add = new JMenuItem("Add Instant Constraint...");
	JMenuItem coi = new JMenuItem("Coincidence");
	JMenuItem pre = new JMenuItem("Precedence");
	JMenuItem str_pre = new JMenuItem("Strict Precedence");
	JMenuItem add_clock_cons = new JMenuItem("Add Clock Constraint...");
	JMenuItem combine = new JMenuItem("Clock Construction...");
	JMenuItem check = new JMenuItem("Check with NuSMV");

	ConstraintPane south = new ConstraintPane();

	private boolean isDraw = false;

	public InstantPane(InstantGraph ig) {
		this.type = 1;
		this.setBackground(Color.white);
		igs.add(ig);
		this.setLayout(new BorderLayout());
		this.add(south, BorderLayout.SOUTH);

		this.popupMenu.add(this.add);
		this.popupMenu.add(this.add_clock_cons);
		this.popupMenu.add(this.coi);
		this.popupMenu.add(this.pre);
		this.popupMenu.add(this.str_pre);
		this.popupMenu.add(this.combine);
		this.popupMenu.add(this.check);
		this.add.addActionListener(this);
		this.coi.addActionListener(this);
		this.pre.addActionListener(this);
		this.str_pre.addActionListener(this);
		this.add_clock_cons.addActionListener(this);
		this.combine.addActionListener(this);
		this.check.addActionListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public InstantPane(Rect domain, Clock clock) {
		this.type = 1;
		this.setBackground(Color.white);
		InstantGraph ig = new InstantGraph(domain, clock);
		igs.add(ig);
		this.setLayout(new BorderLayout());
		this.add(south, BorderLayout.SOUTH);

		this.popupMenu.add(this.add);
		this.popupMenu.add(this.add_clock_cons);
		this.popupMenu.add(this.coi);
		this.popupMenu.add(this.pre);
		this.popupMenu.add(this.str_pre);
		this.popupMenu.add(this.combine);
		this.popupMenu.add(this.check);
		this.add.addActionListener(this);
		this.coi.addActionListener(this);
		this.pre.addActionListener(this);
		this.str_pre.addActionListener(this);
		this.add_clock_cons.addActionListener(this);
		this.combine.addActionListener(this);
		this.check.addActionListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void addGraph(InstantGraph newIg) {

		for (int m = 0; m < igs.size(); m++) {

			if (null == igs.get(m).getDomain()) {
				continue;
			}

			if (igs.get(m).getDomain().equals(newIg.getDomain())) {
				igs.remove(m);
				// count--;
				igs.add(m, newIg);
				return;
			}
		}
		/*
		 * int index = count; for (int i = 0; i < igs.size() && i <= index; i++)
		 * { if (igs.get(i).getClock().getName()
		 * .equals(newIg.getClock().getName())) index = i + 1; }
		 */
		newIg.setPosition(20, count * 60 + 60);/*
												 * 定位每个图的坐标；i*60+60；
												 * 第一个60是两图间距，第二个60是第一个图的Y坐标
												 */
		igs.add(newIg);
		count++;

		this.repaint();
	}

	public void addGraph(Rect domain, Clock clock) {

		for (int m = 0; m < igs.size(); m++) {

			if (null == igs.get(m).getDomain()) {
				continue;
			}

			if (igs.get(m).getDomain().equals(domain)) {
				igs.get(m).setClock(clock);
				return;
			}
		}
		// Group instant graphs by clock name
		/*
		 * int index = count; for (int i = 0; i < igs.size() && i <= index; i++)
		 * { if (igs.get(i).getClock().getName()
		 * .equals(newIg.getClock().getName())) index = i + 1; }
		 */
		InstantGraph ig = new InstantGraph(domain, clock);
		igs.add(ig);

		ig.setPosition(20, count * 60 + 60);
		// igs.add(index, newIg);
		count++;

		this.repaint();
	}

	public void addConstructionGraph(int index, LinkedList<String> domains,
			String name, Clock clock) {
		int count = getCount(index, domains);
		InstantGraph ig = new InstantGraph(count, name, clock);
		ig.setType(InstantGraph.TYPE_CONSTRUCTION);
		this.addGraph(ig);
		// Add relations
		if (index == 0) {
			String domain = domains.get(0);
			InstantGraph graph0 = getIG(domain);
			int _index = 0;
			char[] filter = domains.get(1).toCharArray();
			for (int i = 0; i < graph0.getJiaohu().size(); i++) {
				if (filter[i % filter.length] == '1') {
					InstantRelation new_ir = new InstantRelation(graph0
							.getJiaohu().get(i), ig.getJiaohu().get(_index++),
							0);
					this.cRelations.add(new_ir);
				}
			}
		} else if (index == 1) {
			String domain0 = domains.get(0);
			InstantGraph graph0 = getIG(domain0);
			LinkedList<Jiaohu> jiaohus = graph0.getJiaohu();
			for (int i = 1; i < domains.size(); i++) {
				InstantGraph temp = this.getIG(domains.get(i));
				jiaohus.addAll(temp.getJiaohu());
			}
			Collections.sort(jiaohus);
			for (int j = 0; j < count; j++) {
				InstantRelation new_ir = new InstantRelation(jiaohus.get(j), ig
						.getJiaohu().get(j), 0);
				this.cRelations.add(new_ir);
			}
		} else {
			for (int i = 0; i < domains.size(); i++) {
				for (int j = 0; j < count; j++) {
					InstantGraph temp = this.getIG(domains.get(i));
					if (index == 2) {
						InstantRelation new_ir = new InstantRelation(temp
								.getJiaohu().get(j), ig.getJiaohu().get(j), 1);
						this.cRelations.add(new_ir);
					} else {
						InstantRelation new_ir = new InstantRelation(ig
								.getJiaohu().get(j), temp.getJiaohu().get(j), 1);
						this.cRelations.add(new_ir);
					}
				}
			}
		}
	}

	private int getCount(int index, LinkedList<String> domains) {
		int count = 0;
		if (index == 0) {
			String domain = domains.get(0);
			char[] filter = domains.get(1).toCharArray();
			InstantGraph graph0 = getIG(domain);
			if (graph0 != null) {
				for (int i = 0; i < graph0.getJiaohu().size(); i++) {
					if (filter[i % filter.length] == '1')
						count++;
				}
			}
		} else if (index == 1) {
			for (int j = 0; j < domains.size(); j++) {
				InstantGraph graph1 = getIG(domains.get(j));
				count += graph1.getJiaohu().size();
			}
		} else {
			String example = domains.get(0);
			InstantGraph graph2 = getIG(example);
			count = graph2.getJiaohu().size();
		}
		return count;
	}

	private InstantGraph getIG(String shortName) {
		InstantGraph result = null;
		for (int i = 0; i < igs.size(); i++) {
			if (null == igs.get(i).getDomain())
				continue;
			if (igs.get(i).getDomain().getShortName().equals(shortName)) {
				result = igs.get(i);
				break;
			}
		}
		return result;
	}

	public LinkedList<InstantGraph> getGraphs() {
		return this.igs;
	}

	public void paint(Graphics g) {
		super.paint(g);
		if (this.igs != null) {
			// igs.get(0).draw(g);
			for (int i = 0; i < count; i++) {
				// igs.get(i).setPosition(20, i * 60 + 60);
				igs.get(i).draw(g);
			}
		}
		if (this.relations != null) {
			for (int i = 0; i < relations.size(); i++) {
				relations.get(i).draw(g);
			}
		}

		if (this.cRelations != null) {
			for (int i = 0; i < cRelations.size(); i++) {
				cRelations.get(i).draw(g);
			}
		}
	}

	public void setDraw(boolean draw) {
		this.isDraw = draw;
		if (!isDraw)
			setCursor(Cursor.getDefaultCursor());
		else
			setCursor(Cursor.getPredefinedCursor(1));
	}

	public static void main(String[] args) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("Add Instant Constraint...")) {
			/*
			 * String s = JOptionPane.showInputDialog(this,
			 * "Please input the description of the constraint!", "Input", 2);
			 * 
			 * if ((s == null) || (s.equals(""))) { return; }
			 * south.addConstraint(s); south.repaint(); ConstraintDialog cd=
			 */
			new ConstraintDialog(this.igs);
		} else if (e.getActionCommand().equals("Coincidence")) {
			System.out.println("Coincidence");
			this.setDraw(true);
			relation = 0;
		} else if (e.getActionCommand().equals("Precedence")) {
			System.out.println("Precedence");
			this.setDraw(true);
			relation = 1;
		} else if (e.getActionCommand().equals("Strict Precedence")) {
			System.out.println("Strict Precedence");
			this.setDraw(true);
			relation = 2;
		} else if (e.getActionCommand().equals("Add Clock Constraint...")) {
			System.out.println("Add Clock Constraint");
			LinkedList<Clock> clocks = new LinkedList<Clock>();
			for (int i = 0; i < this.igs.size(); i++) {
				clocks.add(this.igs.get(i).getClock());
			}
			new ClockConsDialog(this.igs);
		} else if (e.getActionCommand().equals("Clock Construction...")) {
			LinkedList<String> clocks = new LinkedList<String>();
			for (int j = 0; j < igs.size(); j++) {
				Rect domain = igs.get(j).getDomain();
				if (null != domain)
					clocks.add(domain.getShortName());
			}
			new CConstructionDialog(clocks);
		} else if (e.getActionCommand().equals("Check with NuSMV")) {
			Checker checker = new Checker(igs, relations, cRelations);
			checker.check();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		requestFocus();
		Point p = e.getPoint();

		if (this.nowSelected != null) {
			this.nowSelected.selected = false;
			repaint();
		}
		this.nowSelected = null;
		if (e.getButton() == 3) {
			this.popupMenu.show(this, e.getX(), e.getY());
			return;
		}

		if (this.isDraw) {
			if (this.relation < 0 || this.relation > 2) {
				return;
			}
			if (this.from == null) {
				Jiaohu tmpFrom = null;
				for (int i = 0; i < igs.size(); i++) {
					tmpFrom = igs.get(i).whichSelected(p.x, p.y);
					if (tmpFrom != null)
						break;
				}
				if (tmpFrom == null) {
					this.setDraw(false);
					return;
				}

				this.from = tmpFrom;
				return;
			}

			Jiaohu tmpTo = null;
			for (int i = 0; i < igs.size(); i++) {
				tmpTo = igs.get(i).whichSelected(p.x, p.y);
				if (tmpTo != null)
					break;
			}
			if (tmpTo != null) {

				String con = "<";

				if (this.relation == 0)
					con = "=";
				else if (this.relation == 1)
					con = "≤";

				/*
				 * if(this.from || tmpTo belongs to construction Graph){
				 * InstantRelation new_cj = new InstantRelation(this.from,
				 * tmpTo, this.relation); this.cRelations.add(new_cj); }
				 */

				if (this.whichGraph(this.from).getType() == InstantGraph.TYPE_CONSTRUCTION
						|| this.whichGraph(tmpTo).getType() == InstantGraph.TYPE_CONSTRUCTION) {
					InstantRelation new_cj = new InstantRelation(this.from,
							tmpTo, this.relation);
					this.cRelations.add(new_cj);
				} else if (this.checkConstraint(
						this.from.getName() + this.from.getNumber(), con,
						tmpTo.getName() + tmpTo.getNumber())) {

					InstantRelation new_cj = new InstantRelation(this.from,
							tmpTo, this.relation);

					this.relations.add(new_cj);
				} else
					JOptionPane.showMessageDialog(this, Main.errmes);

				this.setDraw(false);
				this.from = null;
				repaint();

				return;
			}
			repaint();
			return;
		} else {
			Jiaohu tmp = null;
			for (int i = 0; i < igs.size(); i++) {
				tmp = igs.get(i).whichSelected(p.x, p.y);
				if (tmp != null)
					break;
			}
			if (tmp != null)
				System.out.println(tmp.toString());
			if (tmp == null) {
				if (this.nowSelected != null) {
					this.nowSelected.selected = false;
					this.nowSelected = null;
				}
				repaint();
			} else {
				if (this.nowSelected != null) {
					this.nowSelected.selected = false;
					this.nowSelected = null;
				}
				this.nowSelected = tmp;
				this.previousX = e.getX();
				this.previousY = e.getY();
				tmp.selected = true;
				this.dragged = true;
				this.nowx = e.getX();
				this.nowy = e.getY();
				repaint();
				return;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
		if (this.dragged && !check()) {
			this.nowSelected.moveTo(this.previousX - x, 0);

			for (int i = 0; i <= this.relations.size() - 1; i++) {

				InstantRelation tmp_cj = this.relations.get(i);

				if ((this.nowSelected.equals(tmp_cj.getFrom()))
						|| (this.nowSelected.equals(tmp_cj.getTo())))

					tmp_cj.refresh();
			}
			for (int i = 0; i <= this.cRelations.size() - 1; i++) {

				InstantRelation tmp_cj = this.cRelations.get(i);

				if ((this.nowSelected.equals(tmp_cj.getFrom()))
						|| (this.nowSelected.equals(tmp_cj.getTo())))

					tmp_cj.refresh();
			}
			repaint();
			JOptionPane.showMessageDialog(this, Main.errmes);
		}
		for (int j = 0; j < this.igs.size(); j++) {
			igs.get(j).refresh();
		}
		this.dragged = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if (!this.dragged) {
			return;
		}
		int x = e.getX();
		int y = e.getY();
		this.nowSelected.moveTo(x - this.nowx, 0);// 第二个参数设置为0保证其只在坐标轴上移动
		this.nowx = x;
		this.nowy = y;

		for (int i = 0; i <= this.relations.size() - 1; i++) {
			InstantRelation tmp_cj = this.relations.get(i);
			if ((this.nowSelected.equals(tmp_cj.getFrom()))
					|| (this.nowSelected.equals(tmp_cj.getTo())))
				tmp_cj.refresh();
		}
		for (int i = 0; i <= this.cRelations.size() - 1; i++) {
			InstantRelation tmp_cj = this.cRelations.get(i);
			if ((this.nowSelected.equals(tmp_cj.getFrom()))
					|| (this.nowSelected.equals(tmp_cj.getTo())))
				tmp_cj.refresh();
		}
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	private boolean check() {
		// TODO Auto-generated method stub
		int igs_size = igs.size();
		InstantGraph tempIG = null;

		for (int i = 0; i < igs_size; i++) {
			if (igs.get(i).contains(nowSelected)) {
				if (null == igs.get(i).getDomain()) {
					System.out.println("Combine");
					return true;
				}
				tempIG = igs.get(i);
				break;
			}
		}

		LinkedList<Jiaohu> tempIG_jiaohu = tempIG.getJiaohu();
		int tempIG_jiaohu_size = tempIG_jiaohu.size();

		if (tempIG != null) {
			Hashtable<Integer, String> weight = tempIG.getOrder();

			for (int m = 0; m < tempIG_jiaohu_size; m++) {

				double value1 = this.getValue(weight, tempIG_jiaohu.get(m)
						.getNumber());
				double value2 = this.getValue(weight, nowSelected.getNumber());

				if (tempIG_jiaohu.get(m).getMiddleX() < this.nowSelected
						.getMiddleX() && value1 > value2) {

					UI.Main.errmes = nowSelected.getName()
							+ nowSelected.getNumber() + " must before "
							+ tempIG_jiaohu.get(m).getName()
							+ tempIG_jiaohu.get(m).getNumber() + " !";

					UI.Main.errstate = 1;

					return false;
				}

				if (tempIG_jiaohu.get(m).getMiddleX() > this.nowSelected
						.getMiddleX() && value1 < value2) {

					UI.Main.errmes = nowSelected.getName()
							+ nowSelected.getNumber() + " must after "
							+ tempIG_jiaohu.get(m).getName()
							+ tempIG_jiaohu.get(m).getNumber() + " !";

					UI.Main.errstate = 1;

					return false;
				}

				if (tempIG_jiaohu.get(m).getMiddleX() == this.nowSelected
						.getMiddleX()
						&& tempIG_jiaohu.get(m).getNumber() != this.nowSelected
								.getNumber()) {

					UI.Main.errmes = nowSelected.getName()
							+ nowSelected.getNumber()
							+ " can't happen at the same time of "
							+ tempIG_jiaohu.get(m).getName()
							+ tempIG_jiaohu.get(m).getNumber() + " !";

					UI.Main.errstate = 1;

					return false;
				}

			}
		}
		return true;
	}

	private double getValue(Hashtable<Integer, String> p_weight, int key) {

		String weight_str = p_weight.get(key);

		double m_weight = Double.valueOf(weight_str.substring(weight_str
				.indexOf(',') + 1));

		return m_weight;
	}

	public void addConstraint(String from, String cons, String to, String num) {
		// int fromNum = Integer.valueOf(from.substring(3));
		// int toNum = Integer.valueOf(to.substring(3));
		if (checkConstraint(from, cons, to))
			this.south.addConstraint(from + cons + to + num);
		else
			JOptionPane.showMessageDialog(this, Main.errmes);
	}

	public boolean checkConstraint(String fName, String con, String tName) {

		int from = Integer.valueOf(fName.substring(3));
		int to = Integer.valueOf(tName.substring(3));

		int igs_size = this.igs.size();
		int state = 0;

		if (con.equals("≤")) {
			state = 1;
		} else if (con.equals("<") || con.equals("=")) {
			state = 2;
		}

		for (int i = 0; i < igs_size; i++) {

			Hashtable<Integer, String> weight = igs.get(i).getOrder();

			if (weight.get(from) != null && weight.get(to) != null) {

				String group_str = weight.get(from);
				int from_group = Integer.valueOf(group_str.substring(0, 1));

				String group_str2 = weight.get(from);
				int to_group = Integer.valueOf(group_str2.substring(0, 1));

				if (from_group != to_group) {

					return true;

				} else {

					double from_value = this.getValue(weight, from);
					double to_value = this.getValue(weight, to);

					if (from_value > to_value) {

						UI.Main.errmes = tName + " must before " + fName + " !";

						UI.Main.errstate = 1;

						return false;
					} else if (from_value < to_value && state != 0)
						return true;
					else if ((from_value - to_value) < 0.1 && state != 0) {

						UI.Main.errmes = fName
								+ " can't happen at the same time of " + tName
								+ " !";

						UI.Main.errstate = 1;

						return false;
					}
				}
			}
		}
		return true;
	}

	public InstantGraph whichGraph(Jiaohu o) {
		InstantGraph result = null;
		for (int i = 0; i < this.igs.size(); i++) {
			if (this.igs.get(i).getJiaohu().contains(o)) {
				result = this.igs.get(i);
			}
		}
		return result;
	}
}
