package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import DAO.IdeaDAO;
import DTO.IdeaDTO;

public class MainFrame extends JFrame implements ActionListener, MouseListener {	// 자바에서 제공하는 GUI 사용가능한 class(JFrame) 상속받기 -> view에 관련된 코드를 분리 시도(is a 관계)
							// -> JFrame에 화면구현에 관련된 기능이 존재 => JFrame을 상속받아서 이 프로그램의 view를 만들겠다.
							// implement -> 기능을 구현하기 위해서 사용한다.(ActionListener의 기능을 구현받았다.)(인터페이스구현 - 기능처리 위해서)
	                        // 이벤트의 기능이 필요하면, implement로 기능을 구현받아서 사용할 수 있고, 그러한 기능으로 어떤 서비스를 정의할 것인가를 결정
		// MainFrame은 JFrame의 class를 상속받았으며(extend), ActionListener와 MouseListener 2개의 interface을 구현받음(implement)
	private JLabel title = new JLabel("IdeaBank");
	private JTextField input = new JTextField();
	private JButton btn = new JButton("save");
	private List wordList = new List(5,true);
	private JButton btn1 = new JButton("East");
	private JButton btn2 = new JButton("West");
	// JLabel, JTextField, JButton, List -> 컴포넌트
	// JFrame -> 컨테이너
	private JPanel centerP = new JPanel();
	// panel은 컨테이너이면서 컴포넌트이다. 기본 레이아웃이 flowLayout이다.
	
	private IdeaDAO ideaDao = IdeaDAO.getInstance();

	public MainFrame() {
		this.setBounds(100,100,300,500);	// 상속 이용
					//  x,  y, 가로,세로
		// 컨테이너는 컴포넌트를 배치시킨다. 컨테이너는 레이아웃이 있다.
		// JFrame은 컨테이너이고, 기본 레이아웃은 border layout이다.
		// border layout은 하나의 공간에 하나의 컴포넌트만 가능하다.
		this.add(title,"North");	// 배치의 위치 설정
		this.add(btn,"South");
//		this.add(wordList,"Center");
//		this.add(btn1,"East");
//		this.add(btn2,"West");
//		this.add(btn,"Center");		=> 이런식으로하면 두번째것으로 Center가 갱신됨 => 패널을 이용하여 한 공간에 두가지 배치 가능
		// 가운데 패널
		centerP.setLayout(new BorderLayout());
		centerP.setBackground(Color.red);
		centerP.add(wordList,"Center");
		centerP.add(input,"South");
		this.add(centerP,"Center");		
		
		// 리스너 등록
		btn.addActionListener(this);		// 버튼이 클릭되는 동작을 알려주는 메소드 사용
		input.addActionListener(this);
		this.setVisible(true);	// 화면에 띄워라
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); // X버튼 누르면 프로그램 자동 종료해라
		
		// 객체를 보고 '컴포넌트: 화면구성요소' , '컨테이너: 요소를배치하는개체'로 구분

		loadDB();
	}
	
	private void loadDB() {
		// TODO Auto-generated method stub
		ArrayList<IdeaDTO> ideadto = ideaDao.selectall();
		for(IdeaDTO i : ideadto) {
			wordList.add(i.getNum()+" : "+i.getTitle());
		}
		
	}

	@Override		// 메서드 재정의
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==btn || e.getSource()==input) {
			System.out.println("버튼이 클릭 됨");
			String t = input.getText();
			System.out.println("입력하신 글은 : "+t);
			input.setText("");
			wordList.add(t);
			
			IdeaDTO dto = new IdeaDTO();
			dto.setTitle(t);
			ideaDao.insert(dto);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("클릭됨");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
