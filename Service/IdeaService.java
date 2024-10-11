package Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import DAO.IdeaDAO;
import DTO.IdeaDTO;

public class IdeaService {
	
	IdeaDAO ideadao = IdeaDAO.getInstance();
	
	public IdeaService() {
		Scanner in = new Scanner(System.in);
		while(true) {
			System.out.println("1. 아이디어 제안");
			System.out.println("2. 아이디어 수정");
			System.out.println("3. 아이디어 삭제");
			System.out.println("4. 아이디어 전체보기");
			System.out.println("5. 아이디어 검색하기");
			System.out.println("6. 종료");
			
			int selNum = in.nextInt();
			in.nextLine();
			
			if(selNum==1) {
				add();
			}else if(selNum==2) {
				mod();
			}else if(selNum==3) {
				del();
			}else if(selNum==4) {
				all();
			}else if(selNum==5) {
				one();
			}else if(selNum==6) {
				break;
			}
			
		}
	}

	private void add() {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		IdeaDTO ideadto = new IdeaDTO();
		
		System.out.println("--- 아이디어 등록을 진행합니다. ---");
		System.out.println("등록할 아이디어 제목을 입력하세요.");
		String title = in.nextLine();
		System.out.println("등록할 아이디어 내용을 입력하세요.");
		String memo = in.nextLine();
		System.out.println("등록할 아이디어 작성자를 입력하세요.");
		String name = in.nextLine();
		
		ideadto.setTitle(title);
		ideadto.setMemo(memo);
		ideadto.setName(name);
		// dao 객체에게 ideadto 객체주소 전달
		ideadao.insert(ideadto);
	}

	private void mod() {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		
		System.out.println("--- 아이디어 수정을 진행합니다. ---");
		ArrayList<IdeaDTO> ideadtolist = new ArrayList<>();
		ideadtolist = ideadao.selectall();
		for (IdeaDTO ideadto : ideadtolist) {
			System.out.println(ideadto.toString2());
		}
		
		System.out.println("위 목록 중 수정을 하고 싶은 번호를 입력하세요.");
		int no = in.nextInt();
		in.nextLine();
		
		IdeaDTO ideadto = ideadao.modone(no);
		System.out.println(ideadto.toString());
		
		System.out.println("-------위 항목의 수정을 진행합니다.-------");
		System.out.println("수정 등록할 아이디어 제목을 입력하세요.");
		String title = in.nextLine();
		System.out.println("수정 등록할 아이디어 내용을 입력하세요.");
		String memo = in.nextLine();
		ideadao.update(no, title, memo);
	}

	private void del() {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		System.out.println("--- 아이디어 삭제를 진행합니다. ---");
		ArrayList<IdeaDTO> ideadtolist = new ArrayList<>();
		ideadtolist = ideadao.selectall();
		for (IdeaDTO ideadto : ideadtolist) {
			System.out.println(ideadto.toString2());
		}
		System.out.println("위 목록 중 삭제를 하고 싶은 번호를 입력하세요.");
		int no = in.nextInt();
		ideadao.delete(no);
		
	}

	private void all() {
		// TODO Auto-generated method stub
		ArrayList<IdeaDTO> ideadtolist = new ArrayList<>();
		System.out.println("--- 아이디어 전체조회를 진행합니다. ---");
		ideadtolist = ideadao.selectall();
		System.out.println("현재 등록되어있는 아이디어는 총 "+ideadtolist.size()+"개 입니다.");
		for (IdeaDTO ideadto : ideadtolist ) {
			System.out.println(ideadto.toString());
		}
	}

	private void one() {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		ArrayList<IdeaDTO> ideadtolist = new ArrayList<>();
		System.out.println("--- 아이디어 검색을 진행합니다. ---");
		System.out.println("검색할 아이디어 제목을 입력하세요.");
		String title = in.nextLine();
		ideadtolist = ideadao.selectone(title);
		System.out.println("해당 내역이 포함된 항목은 총 "+ideadtolist.size()+"개 입니다.");
		for (IdeaDTO ideadto : ideadtolist ) {
			System.out.println(ideadto.toString());
		}
	}
}
