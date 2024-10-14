package word_dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import word_dto.wordDTO;
import word_load.oracleload;

public class wordDAO extends oracleload {
	
	public static wordDAO worddao = null;
	
	private wordDAO() {
		load();
	}
	
	public static wordDAO getInstance(){	// 싱글톤 설계 완료
		if(worddao==null) {
			worddao = new wordDAO();
		}
		return worddao;
	}
	
	public void insert(wordDTO worddto) {
		if(conn()) {
			try {
				String sql = "insert into wordlove values(wordseq.nextval,?,?,default)";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, worddto.getKor());
				psmt.setString(2, worddto.getEng());
				
				int result = psmt.executeUpdate();
				
				if(result > 0) {
					conn.commit();
					System.out.println("해당 내용으로 단어 등록이 완료되었습니다.");
				}else {
					conn.rollback();
					System.err.println("단어 등록에 실패하였습니다. --에러");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else {		// conn() 메소드 실행결과 false를 리턴받았다면 == 커넥션 자원을 가져오지 못함을 의미
			System.err.println("데이터 커넥션을 실패하였습니다. 다시 시도하세요.");
		}
	}
	
	public void update(String kor, String kor1, String eng) {
		if(conn()) {
			try {
				String sql = "update wordlove set kor = ?, eng = ? where kor = ?";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, kor1);
				psmt.setString(2, eng);
				psmt.setString(3, kor);
				
				int result = psmt.executeUpdate();
				
				if(result>0) {
					conn.commit();
					System.out.println("해당 내용으로 단어 수정이 완료되었습니다.");
				}else {
					conn.rollback();
					System.err.println("단어 수정에 실패하였습니다. --에러");
				}
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else {		// conn() 메소드 실행결과 false를 리턴받았다면 == 커넥션 자원을 가져오지 못함을 의미
			System.err.println("데이터 커넥션을 실패하였습니다. 다시 시도하세요.");
		}
	}
	
	
	public void delete(String kor) {
		if(conn()) {
			try {
				String sql = "delete from idea where kor = ?";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, kor);
				
				int result = psmt.executeUpdate();
				
				if(result > 0) {
					conn.commit();
					System.out.println("해당 단어가 삭제되었습니다.");
				}else {
					conn.rollback();
					System.err.println("삭제에 실패하였습니다. --에러");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else {		// conn() 메소드 실행결과 false를 리턴받았다면 == 커넥션 자원을 가져오지 못함을 의미
			System.err.println("데이터 커넥션을 실패하였습니다. 다시 시도하세요.");
		}
	}
	
	public ArrayList<wordDTO> selectall() {
		ArrayList<wordDTO> worddtolist = new ArrayList<>();
		
		if(conn()) {
			try {
				String sql = "select * from wordlove";
				PreparedStatement psmt = conn.prepareStatement(sql);

				ResultSet rs = psmt.executeQuery();
				
				while(rs.next()) {
					wordDTO worddto = new wordDTO();
					worddto.setNum(rs.getInt("num"));
					worddto.setKor(rs.getString("kor"));
					worddto.setEng(rs.getString("eng"));
					worddto.setIndate(rs.getString("indate"));
					worddtolist.add(worddto);
				}
				
				return worddtolist;
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else {
			System.err.println("데이터 커넥션을 실패하였습니다. 다시 시도하세요.");
		}
		return null;
	}

}

