package Model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Method {

	
	public   String returnDate() {
		SimpleDateFormat f = new SimpleDateFormat("dd/MM/YYYY");
		return f.format(new Date());
	}
}
