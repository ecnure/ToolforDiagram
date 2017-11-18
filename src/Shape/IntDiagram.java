package Shape;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.LinkedList;

public class IntDiagram implements Serializable {
	
	/****************************************************/
	private static final long serialVersionUID = -6036556398408475676L;
	/****************************************************/
	private LinkedList changjing = new LinkedList();
	private LinkedList jiaohu = new LinkedList();
	private String title;
	private int biaohao;

	public int getBiaohao() {
		return this.biaohao;
	}

	public IntDiagram(String title, int biaohao) {
		this.title = title;
		this.biaohao = biaohao;
	}

	public LinkedList getJiaohu() {
		return this.jiaohu;
	}

	public LinkedList getChangjing() {
		return this.changjing;
	}

	public boolean check1() {
		for (int i = 0; i <= this.changjing.size() - 1; i++) {
			Changjing tmp_c = (Changjing) this.changjing.get(i);
			Jiaohu tmp_1 = tmp_c.getFrom();
			Jiaohu tmp_2 = tmp_c.getTo();
			if (tmp_c.getState() == 3) {
				if ((tmp_1.getState() == 1) && (tmp_2.getState() == 1)) {
					continue;
				}
				UI.Main.errmes = "Interaction" + tmp_1.getNumber()
						+ " and Interaction" + tmp_2.getNumber()
						+ " have a wrong relationship!";

				UI.Main.errstate = 1;
				return false;
			}

			if (tmp_c.getState() == 1) {
				if ((tmp_1.getState() == 0) && (tmp_2.getState() == 0)) {
					continue;
				}
				UI.Main.errmes = "Interaction" + tmp_1.getNumber()
						+ " and Interaction" + tmp_2.getNumber()
						+ " have a wrong relationship!";

				UI.Main.errstate = 1;
				return false;
			}

			if (tmp_c.getState() == 0) {
				if ((tmp_1.getState() == 0) && (tmp_2.getState() == 1)) {
					continue;
				}
				UI.Main.errmes = "Interaction" + tmp_1.getNumber()
						+ " and Interaction" + tmp_2.getNumber()
						+ " have a wrong relationship!";

				UI.Main.errstate = 1;
				return false;
			}

			if (tmp_c.getState() == 4) {
				if ((tmp_1.getState() == 1) && (tmp_2.getState() == 0)) {
					continue;
				}
				UI.Main.errmes = "Interaction" + tmp_1.getNumber()
						+ " and Interaction" + tmp_2.getNumber()
						+ " have a wrong relationship!";

				UI.Main.errstate = 1;
				return false;
			}

			if (tmp_c.getState() == 2) {
				if (((tmp_1.getState() == 1) && (tmp_2.getState() == 0))
						|| ((tmp_1.getState() == 0) && (tmp_2.getState() == 1))) {
					if (tmp_1.getNumber() == tmp_2.getNumber()) {
						continue;
					}
					UI.Main.errmes = "Interaction" + tmp_1.getNumber()
							+ " and Interaction" + tmp_2.getNumber()
							+ " have a wrong relationship!";

					UI.Main.errstate = 1;
					return false;
				}

				UI.Main.errmes = "Interaction" + tmp_1.getNumber()
						+ " and Interaction" + tmp_2.getNumber()
						+ " have a wrong relationship!";

				UI.Main.errstate = 1;
				return false;
			}
		}

		return true;
	}

	public boolean check2() {
		boolean re = true;
		for (int i = 0; i <= this.jiaohu.size() - 1; i++) {
			Jiaohu tmp_j = (Jiaohu) this.jiaohu.get(i);

			if (tmp_j.getState() == 1) {
				boolean zhaodao = false;
				for (int j = 0; j <= this.changjing.size() - 1; j++) {
					Changjing tmp_c = (Changjing) this.changjing.get(j);
					if ((tmp_c.getTo().equals(tmp_j))
							&& (tmp_c.getState() == 0)) {
						zhaodao = true;
						break;
					}
					if ((tmp_c.getFrom().equals(tmp_j))
							&& (tmp_c.getState() == 4)) {
						zhaodao = true;
						break;
					}
					if ((tmp_c.getFrom().equals(tmp_j))
							&& (tmp_c.getState() == 2)) {
						zhaodao = true;
						break;
					}
					if ((tmp_c.getTo().equals(tmp_j))
							&& (tmp_c.getState() == 2)) {
						zhaodao = true;
						break;
					}
				}
				if (!zhaodao) {
					UI.Main.errmes = "Interaction" + tmp_j.getNumber()
							+ "(r) does not confirm!";

					UI.Main.errstate = 2;
					return false;
				}
			}
		}
		return true;
	}

	public void addChangjing(Changjing cj) {
		this.changjing.add(cj);
	}

	public void addJiaohu(Jiaohu jh) {
		this.jiaohu.add(jh);
	}

	public void setTitle(String s) {
		this.title = s;
	}

	public String getTitle() {
		return this.title;
	}

	public void draw(Graphics g) {
		for (int i = 0; i <= this.jiaohu.size() - 1; i++) {
			Jiaohu jh = (Jiaohu) this.jiaohu.get(i);
			jh.draw(g);
		}
		for (int i = 0; i <= this.changjing.size() - 1; i++) {
			Changjing cj = (Changjing) this.changjing.get(i);
			cj.draw(g);
		}
	}

	public Changjing which(int x, int y) {
		for (int i = 0; i <= this.changjing.size() - 1; i++) {
			Changjing tmp_cj = (Changjing) this.changjing.get(i);
			if (tmp_cj.in(x, y)) {
				return tmp_cj;
			}
		}
		return null;
	}

	public Changjing getSelected(int x, int y) {
		for (int i = 0; i <= this.changjing.size() - 1; i++) {
			Changjing tmp_cj = (Changjing) this.changjing.get(i);
			int s = tmp_cj.getSelected(x, y);
			if (s < tmp_cj.getDian().size()) {
				return tmp_cj;
			}
		}
		return null;
	}

	public Jiaohu getSelecte(int x, int y) {
		for (int i = 0; i <= this.jiaohu.size() - 1; i++) {
			Jiaohu tmp_jh = (Jiaohu) this.jiaohu.get(i);
			if (tmp_jh.isIn(x, y)) {
				return tmp_jh;
			}
		}
		return null;
	}

	public void deletecj(Changjing cj) {
		for (int i = 0; i <= this.changjing.size() - 1; i++)
			if (cj.equals((Changjing) this.changjing.get(i))) {
				this.changjing.remove(i);
				break;
			}
	}

	public void deletejh(Jiaohu jh) {
		for (int i = 0; i <= this.jiaohu.size() - 1; i++) {
			Jiaohu jht = (Jiaohu) this.jiaohu.get(i);
			if (jh.equals(jht)) {
				this.jiaohu.remove(i);
				for (int j = 0; j <= this.changjing.size() - 1; j++) {
					Changjing cj = (Changjing) this.changjing.get(j);
					if ((cj.getFrom().equals(jht)) || (cj.getTo().equals(jht))) {
						this.changjing.remove(j);
						j--;
					}
				}
				break;
			}
		}
	}

	public void follow(Jiaohu jh) {
		for (int i = 0; i <= this.changjing.size() - 1; i++) {
			Changjing tmp_cj = (Changjing) this.changjing.get(i);
			if ((jh.equals(tmp_cj.getFrom())) || (jh.equals(tmp_cj.getTo())))
				tmp_cj.refresh();
		}
	}
}