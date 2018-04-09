package Shape;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.LinkedList;

import UI.IntPane;
import UI.Main;

public class Diagram implements Serializable {
	public LinkedList components;
	String title;
	private String interactionDescription = "";

	public String getTitle() {
		return this.title;
	}

	public Diagram(String title) {
		this.title = title;
		this.components = new LinkedList();
	}

	public void add(Shape component) {
		for (int i = 0; i <= this.components.size() - 1; i++) {
			Shape tmp_s = (Shape) this.components.get(i);
			if (tmp_s.equals(component)) {
				return;
			}
		}
		this.components.add(component);
	}

	public void deletePhenomenon(String name, String state) {
		for (int i = 0; i <= this.components.size() - 1; i++) {
			Shape tmp_s = (Shape) this.components.get(i);
			if (tmp_s.shape == 2) {
				Line tmp_l = (Line) tmp_s;
				for (int j = 0; j <= tmp_l.phenomenons.size() - 1; j++) {
					Phenomenon tmp_p = (Phenomenon) tmp_l.phenomenons.get(j);
					if ((tmp_p.getName().equals(name))
							&& (tmp_p.getState().equals(state))) {
						tmp_l.phenomenons.remove(j);
						System.out.println(32);
						j--;
					}
				}
			}
		}
	}

	public LinkedList getPhenomenon() {
		LinkedList ll = new LinkedList();
		for (int i = 0; i <= this.components.size() - 1; i++) {
			Shape tmp_s = (Shape) this.components.get(i);
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
					}
				}
			}
		}
		return ll;
	}

	public LinkedList getReference() {
		LinkedList ll = new LinkedList();
		for (int i = 0; i <= this.components.size() - 1; i++) {
			Shape tmp_s = (Shape) this.components.get(i);
			if (tmp_s.shape == 2) {
				Line tmp_l = (Line) tmp_s;
				if (tmp_l.getState() == 0) {
					continue;
				}
				for (int j = 0; j <= tmp_l.phenomenons.size() - 1; j++) {
					ll.add((Phenomenon) tmp_l.phenomenons.get(j));
				}
			}
		}

		return ll;
	}

	public void addInterface(int i) {
		Line line = new Line((Shape) this.components.get(0),
				(Shape) this.components.get(1), i);

		setLineName();
		this.components.addFirst(line);
	}

	public Rect getMachine() {
		for (int i = 0; i < this.components.size(); i++) {
			if (((Shape) this.components.get(i)).shape == 0) {
				Rect rr = (Rect) this.components.get(i);
				if (rr.state == 2) {
					return rr;
				}
			}
		}
		return null;
	}

	public int hasMachine() {
		int count = 0;
		for (int i = 0; i < this.components.size(); i++) {
			Shape tmp_s = (Shape) this.components.get(i);
			if (tmp_s.shape == 0) {
				Rect tmp_r = (Rect) tmp_s;
				if (tmp_r.state == 2) {
					count++;
				}
			}
		}
		return count;
	}

	public int hasInterface() {
		int count = 0;
		for (int i = 0; i < this.components.size(); i++) {
			Shape tmp_s = (Shape) this.components.get(i);
			if (tmp_s.shape == 2) {
				Line tmp_r = (Line) tmp_s;
				if (tmp_r.getState() == 0) {
					count++;
				}
			}
		}
		return count;
	}

	public  void setLineName() {
		int j = 0;
		for (int i = 0; i < this.components.size(); i++) {
			Shape tmp = (Shape) this.components.get(i);
			if (tmp.shape == 2) {
				String s = "" + (char) (97 + j);
				j++;
				((Line) tmp).name = s;
			}
		}
	}

	public LinkedList getRequirements() {
		LinkedList ll = new LinkedList();
		for (int i = 0; i < this.components.size(); i++) {
			Shape tmp = (Shape) this.components.get(i);
			if (tmp.shape == 1) {
				ll.add(tmp);
			}
		}
		return ll;
	}

	public Oval getRequirement(int num) {
		for (int i = 0; i < this.components.size(); i++) {
			Shape tmp = (Shape) this.components.get(i);
			if (tmp.shape == 1) {
				Oval tmp_o = (Oval) tmp;
				if (tmp_o.getBiaohao() == num) {
					return tmp_o;
				}
			}
		}
		return null;
	}

	public int hasProDom() {
		int count = 0;
		for (int i = 0; i < this.components.size(); i++) {
			Shape tmp_s = (Shape) this.components.get(i);
			if (tmp_s.shape == 0) {
				Rect tmp_r = (Rect) tmp_s;
				if ((tmp_r.state == 1) || (tmp_r.state == 0)) {
					count++;
				}
			}
		}
		return count;
	}

	public int hasReqCon() {
		int count = 0;
		for (int i = 0; i < this.components.size(); i++) {
			Shape tmp_s = (Shape) this.components.get(i);
			if (tmp_s.shape == 2) {
				Line tmp_r = (Line) tmp_s;
				if (tmp_r.getState() == 2) {
					count++;
				}
			}
		}
		return count;
	}

	public int hasReqRef() {
		int count = 0;
		for (int i = 0; i < this.components.size(); i++) {
			Shape tmp_s = (Shape) this.components.get(i);
			if (tmp_s.shape == 2) {
				Line tmp_r = (Line) tmp_s;
				if (tmp_r.getState() == 1) {
					count++;
				}
			}
		}
		return count;
	}

	public int hasReq() {
		int count = 0;
		for (int i = 0; i < this.components.size(); i++) {
			Shape tmp_s = (Shape) this.components.get(i);
			if (tmp_s.shape == 1) {
				count++;
			}
		}
		return count;
	}

	public void follow(Shape a) {
		if (a.shape == 2) {
			return;
		}
		for (int i = 0; i < this.components.size(); i++) {
			Shape tmp = (Shape) this.components.get(i);

			if ((tmp.shape == 1) || (tmp.shape == 0)) {
				continue;
			}
			Line tmp1 = (Line) tmp;
			if ((tmp1.from.equals(a)) || (tmp1.to.equals(a)))
				tmp1.refresh();
		}
	}

	public void draw(Graphics g) {
		int n = 0;
		for (int i = 0; i < this.components.size(); i++) {
			Shape tmp = (Shape) this.components.get(i);

			if (tmp.shape == 2) {
				Line tmpLine = (Line) tmp;

				if (tmpLine.selected) {
					g.setColor(Color.red);
				}

				String biao = tmpLine.name;
				g.drawString(biao, (tmpLine.x1 + tmpLine.x2) / 2,
						(tmpLine.y1 + tmpLine.y2) / 2);

				g.setColor(Color.black);
			}
			tmp.draw(g);
		}
	}

	public String getInteractionDescription() {
		this.interactionDescription = "";
		for (int i = 0; i < this.components.size(); i++) {
			Shape tmp = (Shape) this.components.get(i);
			if (tmp.shape == 2) {
				Line tmp_l = (Line) tmp;
				this.interactionDescription = (this.interactionDescription
						+ tmp_l.getDescription() + "\n");
			}
		}

		this.interactionDescription += "\n";
		for (int i = 0; i < this.components.size(); i++) {
			Shape tmp = (Shape) this.components.get(i);
			if (tmp.shape == 1) {
				Oval tmp_l = (Oval) tmp;
				this.interactionDescription = (this.interactionDescription
						+ "req" + tmp_l.getBiaohao() + ":" + tmp_l.getText() + "\n");
			}

		}

		return this.interactionDescription;
	}

	public boolean find(Shape a, Shape b) {
		for (int i = 0; i < this.components.size(); i++) {
			if (((Shape) this.components.get(i)).shape == 2) {
				Line line = (Line) this.components.get(i);
				if ((line.from.equals(a)) && (line.to.equals(b))) {
					return true;
				}
				if ((line.from.equals(b)) && (line.to.equals(a))) {
					return true;
				}
			}
		}
		return false;
	}

	public void rule() {
		Rect machine = getMachine();
		if (machine == null) {
			return;
		}
		for (int i = 0; i < this.components.size(); i++)
			if (((Shape) this.components.get(i)).shape == 0) {
				Rect rr = (Rect) this.components.get(i);
				if ((rr.state == 2) || (find(machine, rr)))
					continue;
				add(new Line(machine, rr, 0));
				setLineName();
			}
	}

	public Shape whichSelected(int x, int y) {
		for (int i = 0; i < this.components.size(); i++) {
			Shape tmp = (Shape) this.components.get(i);
			if (tmp.isIn(x, y)) {
				return tmp;
			}
		}
		return null;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Diagram convertToProblemDiagram(String name) {
		int maxx = 0;
		int miny = 0;
		int maxy = 0;
		Diagram tmp_d = new Diagram("");
		Persist.save(new File("tmp"), this);
		tmp_d = Persist.load(new File("tmp"));
		tmp_d.setTitle(name);

		for (int i = 0; i < tmp_d.components.size(); i++) {
			Shape tmp = (Shape) tmp_d.components.get(i);
			if (tmp.shape == 2) {
				Line tmpLine = (Line) tmp;
				tmpLine.des = 1;
			} else {
				Rect tmp1 = (Rect) tmp;
				if (tmp1.x1 + tmp1.x2 > maxx) {
					maxx = tmp1.x1 + tmp1.x2;
				}
				if (tmp1.y1 < miny) {
					miny = tmp1.y1;
				}
				if (tmp1.y1 + tmp1.y2 > maxy)
					maxy = tmp1.y1 + tmp1.y2;
			}
		}
		Oval tmp_oval = new Oval(maxx + 100, (maxy + miny) / 2);

		for (int i = 0; i < tmp_d.components.size(); i++) {
			Shape tmp = (Shape) tmp_d.components.get(i);
			if (tmp.shape == 2) {
				continue;
			}
			Rect tmp1 = (Rect) tmp;
			if (tmp1.state != 2) {
				tmp_d.add(new Line(tmp_oval, tmp1, 1));
				setLineName();
			}
		}

		tmp_d.add(tmp_oval);
		return tmp_d;
	}

	public void delete(Shape shape) {
		if (shape.shape == 2) {
			for (int i = 0; i < this.components.size(); i++) {
				Shape tmp = (Shape) this.components.get(i);
				if (shape.equals(tmp))
					this.components.remove(i);
			}
		} else {
			boolean k = false;
			for (int i = 0; i < this.components.size(); i++) {
				Shape tmp = (Shape) this.components.get(i);
				if (tmp.shape == 2) {
					Line tmpLine = (Line) tmp;
					if ((tmpLine.from.equals(shape))
							|| (tmpLine.to.equals(shape))) {
						this.components.remove(i);
						i--;
						if (i == -1) {
							k = true;
							i++;
						}
					}
				}
				if (tmp.equals(shape)) {
					this.components.remove(i);
					i--;
					if (i == -1) {
						k = true;
						i++;
					}
				}
			}
			if (k) {
				if (this.components.size() == 0) {
					return;
				}
				Shape tmp = (Shape) this.components.get(0);
				if (tmp.shape == 2) {
					Line tmpLine = (Line) tmp;
					if ((tmpLine.from.equals(shape))
							|| (tmpLine.to.equals(shape))) {
						this.components.remove(0);
					}
				}
				if (tmp.equals(shape))
					this.components.remove(0);
			}
		}
	}

	public static void main(String[] args) {
		Diagram dd = new Diagram("ee");
		Rect machine = new Rect(0, 0);
		machine.setState(2);
		machine.setText("MM");
		Rect[] r = new Rect[4];
		r[0] = new Rect(0, 0);
		r[0].setState(0);
		r[0].setText("0");

		r[1] = new Rect(0, 0);
		r[1].setState(0);
		r[1].setText("1");

		r[2] = new Rect(0, 0);
		r[2].setState(0);
		r[2].setText("2");

		r[3] = new Rect(0, 0);
		r[3].setState(0);

		Oval re = new Oval(0, 0);
		dd.add(machine);
		dd.add(re);
		for (int i = 0; i <= 3; i++) {
			dd.add(r[i]);
			dd.add(new Line(machine, r[i], 0));
			dd.add(new Line(re, r[i], 1));
		}
	}

	public void dayin() {
		for (int i = 0; i < this.components.size(); i++) {
			Shape tmp_s = (Shape) this.components.get(i);
			if (tmp_s.shape == 0) {
				System.out.println(((Rect) tmp_s).getText());
			}
			if (tmp_s.shape == 1) {
				System.out.println(((Oval) tmp_s).getText());
			}
			if (tmp_s.shape == 2)
				System.out.println(((Line) tmp_s).name);
		}
	}

	public boolean match1() {
		if (this.components.size() != 5) {
			return false;
		}
		Rect machine = null;
		Rect domain = null;
		Oval requirement = null;
		Line line1 = null;
		Line line2 = null;
		for (int i = 0; i < this.components.size(); i++) {
			Shape tmp = (Shape) this.components.get(i);
			if (tmp.shape == 1) {
				requirement = (Oval) tmp;
			}
			if (tmp.shape == 0) {
				Rect tmp_rect = (Rect) tmp;
				if (tmp_rect.state == 2) {
					machine = tmp_rect;
				}
				if (((tmp_rect.state != 1) && (tmp_rect.state != 0))
						|| (tmp_rect.cxb != 'C'))
					continue;
				domain = tmp_rect;
			}

		}

		if ((machine == null) || (domain == null) || (requirement == null)) {
			return false;
		}
		line1 = findALine(machine, domain);
		if (line1 == null) {
			line1 = findALine(domain, machine);
		}
		if (line1 == null) {
			return false;
		}
		if (line1.getState() != 0) {
			return false;
		}
		line2 = findALine(requirement, domain);
		if (line2 == null) {
			return false;
		}
		if (line2.getState() != 2) {
			return false;
		}
		line1.setCore("C1");
		line1.setCore1("C2");
		line2.setCore1("C3");
		return true;
	}

	public boolean match2() {
		if (this.components.size() != 8) {
			return false;
		}
		Rect machine = null;
		Rect domain_c = null;
		Rect domain_b = null;
		Oval re = null;
		Line line1 = null;
		Line line2 = null;
		Line line3 = null;
		Line line4 = null;
		for (int i = 0; i < this.components.size(); i++) {
			Shape tmp = (Shape) this.components.get(i);
			if (tmp.shape == 0) {
				Rect tmp_r = (Rect) tmp;
				if (tmp_r.state == 2) {
					machine = tmp_r;
				} else {
					if (tmp_r.cxb == 'C') {
						domain_c = tmp_r;
					}
					if (tmp_r.cxb == 'B') {
						domain_b = tmp_r;
					}
				}
			}
			if (tmp.shape == 1) {
				re = (Oval) tmp;
			}
		}
		if ((machine == null) || (domain_c == null) || (domain_b == null)
				|| (re == null)) {
			return false;
		}
		line1 = findALine(machine, domain_c);
		if (line1 == null) {
			line1 = findALine(domain_c, machine);
		}
		if (line1 == null) {
			return false;
		}
		if (line1.getState() != 0) {
			return false;
		}
		line2 = findALine(machine, domain_b);
		if (line2 == null) {
			line2 = findALine(domain_b, machine);
		}
		if (line2 == null) {
			return false;
		}
		if (line2.getState() != 0) {
			return false;
		}
		line3 = findALine(re, domain_c);
		if (line3 == null) {
			return false;
		}
		if (line3.getState() != 2) {
			return false;
		}
		line4 = findALine(re, domain_b);
		if (line4 == null) {
			return false;
		}
		if (line4.getState() != 1) {
			return false;
		}

		line1.core = "C1";
		line1.core1 = "C2";
		line2.core1 = "E4";
		line3.core1 = "C3";
		line4.core1 = "E4";
		return true;
	}

	public boolean match3(boolean pat) {
		if (this.components.size() != 8) {
			return false;
		}
		Rect machine = null;
		Rect domain1 = null;
		Rect domain2 = null;
		Oval re = null;
		Line line1 = null;
		Line line2 = null;
		Line line3 = null;
		Line line4 = null;
		boolean k = true;
		for (int i = 0; i < this.components.size(); i++) {
			Shape tmp = (Shape) this.components.get(i);
			if (tmp.shape == 1) {
				re = (Oval) tmp;
			}
			if (tmp.shape == 0) {
				Rect tmp_r = (Rect) tmp;
				if (tmp_r.state == 2) {
					machine = tmp_r;
				} else if (tmp_r.cxb == 'C') {
					if (k) {
						domain1 = tmp_r;
						k = false;
					} else {
						domain2 = tmp_r;
					}
				}
			}
		}

		if ((machine == null) || (domain1 == null) || (domain2 == null)
				|| (re == null)) {
			return false;
		}
		if (pat) {
			line1 = findALine(machine, domain1);
			if (line1 == null) {
				return false;
			}
			if (line1.getState() != 0) {
				return false;
			}

			line2 = findALine(machine, domain2);
			if (line2 == null) {
				return false;
			}
			if (line2.getState() != 0) {
				return false;
			}

			line3 = findALine(re, domain1);
			if (line3 == null) {
				return false;
			}
			if (line3.getState() != 1) {
				return false;
			}

			line4 = findALine(re, domain2);
			if (line4 == null) {
				return false;
			}
			if (line4.getState() != 2) {
				return false;
			}

			line1.core1 = "C1";
			line2.core = "E2";
			line3.core1 = "C3";
			line4.core1 = "Y4";
			return true;
		}

		line1 = findALine(machine, domain2);
		if (line1 == null) {
			line1 = findALine(domain2, machine);
		}
		if (line1 == null) {
			return false;
		}
		if (line1.getState() != 0) {
			return false;
		}

		line2 = findALine(machine, domain1);
		if (line2 == null) {
			line1 = findALine(domain1, machine);
		}
		if (line2 == null) {
			return false;
		}
		if (line2.getState() != 0) {
			return false;
		}

		line3 = findALine(re, domain2);
		if (line3 == null) {
			return false;
		}
		if (line3.getState() != 1) {
			return false;
		}

		line4 = findALine(re, domain1);
		if (line4 == null) {
			return false;
		}
		if (line4.getState() != 2) {
			return false;
		}

		line1.core1 = "C1";
		line2.core = "E2";
		line3.core1 = "C3";
		line4.core1 = "Y4";
		return true;
	}

	public boolean match4() {
		if (this.components.size() != 8) {
			return false;
		}
		Rect machine = null;
		Rect domain_x = null;
		Rect domain_b = null;
		Oval re = null;
		Line line1 = null;
		Line line2 = null;
		Line line3 = null;
		Line line4 = null;
		for (int i = 0; i < this.components.size(); i++) {
			Shape tmp = (Shape) this.components.get(i);
			if (tmp.shape == 0) {
				Rect tmp_r = (Rect) tmp;
				if (tmp_r.state == 2) {
					machine = tmp_r;
				} else {
					if (tmp_r.cxb == 'X') {
						domain_x = tmp_r;
					}
					if (tmp_r.cxb == 'B') {
						domain_b = tmp_r;
					}
				}
			}
			if (tmp.shape == 1) {
				re = (Oval) tmp;
			}
		}
		if ((machine == null) || (domain_x == null) || (domain_b == null)
				|| (re == null)) {
			return false;
		}
		line1 = findALine(machine, domain_x);
		if (line1 == null) {
			line1 = findALine(domain_x, machine);
		}
		if (line1 == null) {
			return false;
		}
		if (line1.getState() != 0) {
			return false;
		}
		line2 = findALine(machine, domain_b);
		if (line2 == null) {
			line2 = findALine(domain_b, machine);
		}
		if (line2 == null) {
			return false;
		}
		if (line2.getState() != 0) {
			return false;
		}
		line3 = findALine(re, domain_x);
		if (line3 == null) {
			return false;
		}
		if (line3.getState() != 2) {
			return false;
		}
		line4 = findALine(re, domain_b);
		if (line4 == null) {
			return false;
		}
		if (line4.getState() != 1) {
			return false;
		}
		line1.core = "E1";
		line1.core1 = "Y2";
		line2.core1 = "E3";
		line3.core1 = "Y4";
		line4.core1 = "E3";
		return true;
	}

	public boolean match5(boolean pat) {
		if (this.components.size() != 8) {
			return false;
		}
		Rect machine = null;
		Rect domain1 = null;
		Rect domain2 = null;
		Oval re = null;
		Line line1 = null;
		Line line2 = null;
		Line line3 = null;
		Line line4 = null;
		boolean k = true;
		for (int i = 0; i < this.components.size(); i++) {
			Shape tmp = (Shape) this.components.get(i);
			if (tmp.shape == 1) {
				re = (Oval) tmp;
			}
			if (tmp.shape == 0) {
				Rect tmp_r = (Rect) tmp;
				if (tmp_r.state == 2) {
					machine = tmp_r;
				} else if (tmp_r.cxb == 'X') {
					if (k) {
						domain1 = tmp_r;
						k = false;
					} else {
						domain2 = tmp_r;
					}
				}
			}
		}

		if ((machine == null) || (domain1 == null) || (domain2 == null)
				|| (re == null)) {
			return false;
		}
		if (pat) {
			line1 = findALine(machine, domain1);
			if (line1 == null) {
				return false;
			}
			if (line1.getState() != 0) {
				return false;
			}

			line2 = findALine(machine, domain2);
			if (line2 == null) {
				return false;
			}
			if (line2.getState() != 0) {
				return false;
			}

			line3 = findALine(re, domain1);
			if (line3 == null) {
				return false;
			}
			if (line3.getState() != 1) {
				return false;
			}
			line4 = findALine(re, domain2);
			if (line4 == null) {
				return false;
			}
			if (line4.getState() != 2) {
				return false;
			}
			line1.core = "Y2";
			line2.core1 = "Y1";
			line3.core1 = "Y4";
			line4.core1 = "Y3";
			return true;
		}

		line1 = findALine(machine, domain2);
		if (line1 == null) {
			return false;
		}
		if (line1.getState() != 0) {
			return false;
		}

		line2 = findALine(machine, domain1);
		if (line2 == null) {
			return false;
		}
		if (line2.getState() != 0) {
			return false;
		}

		line3 = findALine(re, domain2);
		if (line3 == null) {
			return false;
		}
		if (line3.getState() != 1) {
			return false;
		}

		line4 = findALine(re, domain1);
		if (line4 == null) {
			return false;
		}
		if (line4.getState() != 2) {
			return false;
		}
		line1.core = "Y2";
		line2.core1 = "Y1";
		line3.core1 = "Y4";
		line4.core1 = "Y3";

		return true;
	}

	public Diagram refreshContextDiagram() {
		Diagram tmp_d = new Diagram("");
		Persist.save(new File("tmp"), this);
		tmp_d = Persist.load(new File("tmp"));
		tmp_d.setTitle("ContextDiagram");
		while (true) {
			boolean zhaodao = false;
			for (int i = 0; i <= tmp_d.components.size() - 1; i++) {
				Shape tmp_s = (Shape) tmp_d.components.get(i);
				if (tmp_s.shape == 1) {
					Oval tmp_o = (Oval) tmp_s;
					tmp_d.delete(tmp_o);
					zhaodao = true;
					break;
				}
			}
			if (!zhaodao)
				return tmp_d;
		}
	}

	
	

	
	

/*public Diagram refreshDiagram() {
		Diagram tmp_d = new Diagram("");
		Persist.save(new File("tmp"), this);
		tmp_d = Persist.load(new File("tmp"));
		tmp_d.setTitle("ProblemDiagram2");
		while (true) {
			boolean zhaodao = false;
			for (int i = 0; i <= tmp_d.components.size() - 1; i++) {
				Shape tmp_s = (Shape) tmp_d.components.get(i);
				if (tmp_s.shape == 1) {
					Oval tmp_o = (Oval) tmp_s;
					int j=tmp_o.getBiaohao()-1;
					tmp_o.setBiaohao(j);
					//tmp_d.delete(tmp_o);
					zhaodao = true;
					break;
				}
			}
				return tmp_d;
		}
	}

	*/

	
	
	public Line findALine(Shape r1, Shape r2) {
		Line re = null;
		for (int i = 0; i < this.components.size(); i++) {
			Shape tmp1 = (Shape) this.components.get(i);
			if (tmp1.shape == 2) {
				Line tmp_line = (Line) tmp1;
				if ((Data.same(tmp_line.from, r1))
						&& (Data.same(tmp_line.to, r2))) {
					re = tmp_line;
					break;
				}
			}
		}
		return re;
	}
	
	
	
	
	
	
	
	
	
	
	
}