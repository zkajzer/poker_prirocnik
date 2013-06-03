package si.kajzer.pokerprirocnik;

public class Karta {
	private int st;
	public int getSt() {
		return st;
	}
	public void setSt(int st) {
		this.st = st;
	}
	public String getBarva() {
		return barva;
	}
	public void setBarva(String barva) {
		this.barva = barva;
	}
	private String barva;
	
	Karta() {
		this.barva = "def";
		this.st = -1;
	}
}
