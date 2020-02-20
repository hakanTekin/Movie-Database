import java.util.Scanner;

public class DataCreator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		String a;
		while((a = sc.nextLine()) != "anan") {
			int index = a.indexOf("values (");
			index+=8;
			System.out.print(a.substring(0,index));
			System.out.print("m_id_creator.NEXTVAL");
			System.out.println(a.substring(a.indexOf(", '")));
			
			
		}
	}

}
