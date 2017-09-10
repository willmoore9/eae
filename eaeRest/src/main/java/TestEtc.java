import java.util.Calendar;
import java.util.Date;

public class TestEtc {
public static void main(String[] args) {
	Date d = new Date(2017, 1, 12);
	Date d2 = new Date(2017, 1, 20);
	
	Calendar cal = Calendar.getInstance();
	cal.setTime(d);
	
	cal.add(Calendar.DAY_OF_MONTH, 1);
	Date d11 = cal.getTime();
	System.out.println(d11);
	
	cal.add(Calendar.DAY_OF_MONTH, 1);
	Date d22 = cal.getTime();
	System.out.println(d22);
	System.out.println(d11);
	
	System.out.println(cal.after(d22));
}
}
