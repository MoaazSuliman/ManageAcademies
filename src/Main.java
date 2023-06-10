import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Database.*;
import Model.Student;
import View.Intro;
import View.LoginView;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		Intro intro = new Intro();
		intro.setVisible(true);
		Thread.sleep(5000);
		intro.dispose();
		LoginView login = new LoginView();
		login.setVisible(true);


	}

}
