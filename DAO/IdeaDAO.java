package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.IdeaDTO;

public class IdeaDAO {
	private String username = "system";
	private String password = "11111111";
	private String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	private String driverName = "oracle.jdbc.driver.OracleDriver";
	private Connection conn = null;		// 커넥션 자원 변수
	public static IdeaDAO ideadao = null;		// 자기자신의 객체 주소 변수(public static)
	
	private IdeaDAO() {
		init();		// 객체 한번만 만들때 실행하는 메소드 -> 즉 드라이버 로드 한번만 시행  
	}
	
	public static IdeaDAO getInstance() {
		if(ideadao == null) {
			ideadao = new IdeaDAO();
		}
		return ideadao;
	}
	
	private void init(){
		try {
			Class.forName(driverName);
			System.out.println("오라클 드라이버 로드 성공");	// 빌드가 정확하게 됐을 때 이 문구가 출력될 것임.
			// 이 문구가 제대로 출력된다면, 오라클사에서 배포한 라이브러리를 사용할 준비가 완료된것을 의미함.
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean conn() {		// 커넥션 자원 받는 메소드
		try {
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("커넥션 획득");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public void insert(IdeaDTO ideadto) {
		if(conn()) {
			try {
				String sql = "insert into idea values(user_seq.nextval,?,?,?)";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, ideadto.getTitle());
				psmt.setString(2, ideadto.getMemo());
				psmt.setString(3, ideadto.getName());
				
				int result = psmt.executeUpdate();
				
				if(result > 0) {
					conn.commit();
					System.out.println("해당 내용으로 아이디어 등록이 완료되었습니다.");
				}else {
					conn.rollback();
					System.err.println("아이디어 등록에 실패하였습니다. --에러");
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
	
	public void update(int no, String title, String memo) {
		if(conn()) {
			try {
				String sql = "update idea set title = ?, memo = ? where no = ?";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, title);
				psmt.setString(2, memo);
				psmt.setInt(3, no);
				
				int result = psmt.executeUpdate();
				
				if(result>0) {
					conn.commit();
					System.out.println("해당 내용으로 아이디어 수정이 완료되었습니다.");
				}else {
					conn.rollback();
					System.err.println("아이디어 수정에 실패하였습니다. --에러");
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
	
	public IdeaDTO modone(int no) {		// 수정 진행하기전 선택한 항목의 내용을 한번더 보여주는 형식
		if(conn()) {
			try {
				String sql = "select * from idea where no = ?";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setInt(1, no);
				
				ResultSet rs = psmt.executeQuery();
				
				while(rs.next()) {
					IdeaDTO ideadto = new IdeaDTO();
					ideadto.setNum(rs.getInt("no"));
					ideadto.setTitle(rs.getString("title"));
					ideadto.setMemo(rs.getString("memo"));
					ideadto.setName(rs.getString("name"));
					return ideadto;
				}
			} catch (Exception e) {
				// TODO: handle exception
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
	
	public void delete(int no) {
		if(conn()) {
			try {
				String sql = "delete from idea where no = ?";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setInt(1, no);
				
				int result = psmt.executeUpdate();
				
				if(result > 0) {
					conn.commit();
					System.out.println("해당 목록이 삭제되었습니다.");
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
	
	public ArrayList<IdeaDTO> selectall() {
		ArrayList<IdeaDTO> ideadtolist = new ArrayList<>();
		
		if(conn()) {
			try {
				String sql = "select * from idea";
				PreparedStatement psmt = conn.prepareStatement(sql);

				ResultSet rs = psmt.executeQuery();
				
				while(rs.next()) {
					IdeaDTO ideadto = new IdeaDTO();
					ideadto.setNum(rs.getInt("no"));
					ideadto.setTitle(rs.getString("title"));
					ideadto.setMemo(rs.getString("memo"));
					ideadto.setName(rs.getString("name"));
					ideadtolist.add(ideadto);
				}
				
				return ideadtolist;
				
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
	
	public ArrayList<IdeaDTO> selectone(String title) {
		ArrayList<IdeaDTO> ideadtolist = new ArrayList<>();
		if(conn()) {
			try {
				String sql = "select * from idea where INSTR(title, ?) > 0";
				// 검색어가 제목에 포함되어있는 항목을 모두 검색하기 위하여 INSTR 함수 사용
				// INSTR은 특정 문자의 위치(자릿수)를 출력하는 함수임
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, title);
				
				ResultSet rs = psmt.executeQuery();
				
				while(rs.next()) {
					IdeaDTO ideadto = new IdeaDTO();
					ideadto.setNum(rs.getInt("no"));
					ideadto.setTitle(rs.getString("title"));
					ideadto.setMemo(rs.getString("memo"));
					ideadto.setName(rs.getString("name"));
					ideadtolist.add(ideadto);
				}
				return ideadtolist;
				
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
