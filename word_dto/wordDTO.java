package word_dto;

public class wordDTO {
	private int num;
	private String kor;
	private String eng;
	private String indate;
	
	@Override
	public String toString() {
		return "wordDTO [num=" + num + ", kor=" + kor + ", eng=" + eng + ", indate=" + indate + "]";
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getIndate() {
		return indate;
	}
	public void setIndate(String indate) {
		this.indate = indate;
	}
	public String getKor() {
		return kor;
	}
	public void setKor(String kor) {
		this.kor = kor;
	}
	public String getEng() {
		return eng;
	}
	public void setEng(String eng) {
		this.eng = eng;
	}
}
