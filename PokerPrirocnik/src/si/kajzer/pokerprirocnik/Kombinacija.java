package si.kajzer.pokerprirocnik;

public class Kombinacija {
	
	private String ime;
	public String getIme() {
		return ime;
	}
	
	Kombinacija(String ime, String karte) {
		this.ime = ime;
		this.karte = karte;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getKarte() {
		return karte;
	}
	public void setKarte(String karte) {
		this.karte = karte;
	}
	private String karte;
	
}
