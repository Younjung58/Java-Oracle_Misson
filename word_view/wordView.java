package word_view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import word_dao.wordDAO;
import word_dto.wordDTO;

public class wordView extends JFrame implements ActionListener{
	wordDAO worddao = wordDAO.getInstance();
	
	private JPanel title_p = new JPanel();	//	컴포넌트 & 컨테이너
	// 기본 레이아웃이 flow 레이아웃.. 가운데부터 하나씩 정렬
	private JLabel t = new JLabel("단어장 프로그램");
	private JPanel center_p = new JPanel();
	
	// panel은 컨테이너이면서 컴포넌트이다. 기본 레이아웃이 flowLayout이다.
	private JPanel centerP1 = new JPanel();
	private JPanel centerP2 = new JPanel();
	private JPanel centerP3 = new JPanel();
	
	Container con = this.getContentPane();
	
	JButton c1btn;
	JButton c22btn;
	JButton c33btn;
	JLabel c2;
	JLabel c3;
	JTextField j1;
	JTextField j2;
	List c22list;
	
	public wordView(){
		// 컨테이너 -기본 borderlayout
		this.setBounds(100,100,500,150);
		title_p.add(t);
		center_p.setBackground(Color.yellow);
		this.add(title_p,"North");
		this.add(center_p,"Center");	
		
		centerP1.setBackground(Color.red);
		centerP2.setBackground(Color.cyan);
		centerP3.setBackground(Color.green);
	
		
		// 위 3개의 패널을 center_p 에 추가시키는데
		center_p.setLayout(new GridLayout());
		center_p.add(centerP1);
		center_p.add(centerP2);
		center_p.add(centerP3);
		
		// centerP1 작업
		JLabel c1 = new JLabel("단어입력");
		c1btn = new JButton("저장");
//		JPanel c1w = new JPanel();
		JPanel c1c = new JPanel();
//		c1w.setBackground(Color.gray);
		c1c.setBackground(Color.lightGray);
		c1c.setLayout(new GridLayout(2,2));
		// 컬럼 2개, 행 2개인 그리드 레이아웃
		c2 = new JLabel("한글");
		c3 = new JLabel("영어");
		j1 = new JTextField();
		j2 = new JTextField();
		c1c.add(c2);
		c1c.add(j1);
		c1c.add(c3);
		c1c.add(j2);
		
		centerP1.setLayout(new BorderLayout());
		centerP1.add(c1,"North");
		centerP1.add(c1btn,"South");
//		centerP1.add(c1w,"West");
		centerP1.add(c1c,"Center");
		
		
		// centerP2 작업
		JPanel c22 = new JPanel();
		c22.setLayout(new BorderLayout());
		JLabel c221 = new JLabel("단어리스트");
		c22list = new List();
		c22btn = new JButton("선택단어삭제");
		c22.add(c221,"North");
		c22.add(c22list,"Center");
		c22.add(c22btn,"South");
		centerP2.setLayout(new BorderLayout());
		centerP2.add(c22,"Center");
		
		// centerP3 작업
		JLabel c4 = new JLabel("단어수정");
		JButton c33btn = new JButton("수정");
//				JPanel c1w = new JPanel();
		JPanel c4c = new JPanel();
//				c1w.setBackground(Color.gray);
		c4c.setBackground(Color.lightGray);
		c4c.setLayout(new GridLayout(2,2));
		// 컬럼 2개, 행 2개인 그리드 레이아웃
		JLabel c5 = new JLabel("한글");
		JLabel c6 = new JLabel("영어");
		JTextField j3 = new JTextField();
		JTextField j4 = new JTextField();
		c4c.add(c5);
		c4c.add(j3);
		c4c.add(c6);
		c4c.add(j4);
		
		centerP3.setLayout(new BorderLayout());
		centerP3.add(c4,"North");
		centerP3.add(c33btn,"South");
		centerP3.add(c4c,"Center");
		
		// 등록 리스너 등록
		c1btn.addActionListener(this);
		j1.addActionListener(this);
		j2.addActionListener(this);
		
		// 삭제 리스너 등록
		c22btn.addActionListener(this);
		c22list.addActionListener(this);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		loadDB();
		
	}

	private void loadDB() {
		// TODO Auto-generated method stub
		ArrayList<wordDTO> worddto = worddao.selectall();
		for(wordDTO w : worddto) {
			c22list.add(w.getKor()+" - "+w.getEng());
//			System.out.println(w.toString());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==c1btn || e.getSource()== j1 || e.getSource()== j2) {
			System.out.println("버튼이 클릭됨");
			String kor = j1.getText();
			String eng = j2.getText();
			j1.setText("");
			j2.setText("");
			c22list.add(kor+" - "+eng);
			System.out.println(c22list);
			
			wordDTO dto = new wordDTO();
			dto.setKor(kor);
			dto.setEng(eng);
			worddao.insert(dto);
		}
		if(e.getSource()==c22btn || e.getSource()==c22list) {
			
//			List<Map<String, Object, String>> listMap = c22list;
//			String kor = listMap
			
			worddao.selectall();
//			System.out.println(s);
//			worddao.delete(s);
//			c22list.remove(s);
		}
	}
}
