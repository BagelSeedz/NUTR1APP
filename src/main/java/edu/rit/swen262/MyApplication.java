package edu.rit.swen262;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import edu.rit.swen262.controller.FoodController;
import edu.rit.swen262.controller.PersonalHistoryController;
import edu.rit.swen262.controller.TeamController;
import edu.rit.swen262.controller.UndoCaretaker;
import edu.rit.swen262.controller.UserController;
import edu.rit.swen262.controller.WorkoutController;
import edu.rit.swen262.model.DateTracker;
import edu.rit.swen262.model.FormatAdapter;
import edu.rit.swen262.model.IngredientDatabase;
import edu.rit.swen262.model.JsonAdapter;
import edu.rit.swen262.model.PersonalHistoryModel;
import edu.rit.swen262.model.User;
import edu.rit.swen262.persistence.UserDataPersistence;
import edu.rit.swen262.ui.FileView;
import edu.rit.swen262.ui.FoodView;
import edu.rit.swen262.ui.GuestView;
import edu.rit.swen262.ui.HistoryView;
import edu.rit.swen262.ui.MainView;
import edu.rit.swen262.ui.ShoppingListView;
import edu.rit.swen262.ui.StockView;
import edu.rit.swen262.ui.UserView;
import edu.rit.swen262.ui.WorkoutView;

@SpringBootApplication
public class MyApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyApplication.class, args);
	}
}

@Configuration
@Profile("!test")
class SampleCommandLineRunner implements CommandLineRunner {

	@Autowired
	SampleCommandLineRunner() {
		// TODO: 
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("\nNUTRiAPP\n");
		
		FormatAdapter jsonAdapter = new JsonAdapter();
		PersonalHistoryController phc = new PersonalHistoryController(jsonAdapter);

		UserDataPersistence userDataPersistence = new UserDataPersistence(phc);
		UserController uc = new UserController(); 

		Map<String, User> loadedUsers = userDataPersistence.loadUsers();
		if (loadedUsers != null && !loadedUsers.isEmpty()) {
			uc.setUsers(loadedUsers);  
			System.out.println("These are the users: " + uc.getUsers().keySet());
		} else {
			System.out.println("No users found");
		}
		
		UserView uv = new UserView(uc); 
		
		GuestView gv = new GuestView(uv, uc, phc);
		PersonalHistoryModel phm = gv.showMenu();
		// if(phm==null){
		// 	dateTracker = new DateTracker(LocalDate.now());
		// 	uc.addUser(user);
		// 	phm = new PersonalHistoryModel(user, dateTracker, null, dateTracker);

		// }else{
		// 	uc.addUser(phm.getUser());
		// 	dateTracker = user.get(phm.getCurrentDate());
		// }
		DateTracker dateTracker = phm.getCurrentDate();
		phc.setCurrentPersonalHistoryModel(phm);
		
		
		FoodController fc = new FoodController(phc, uc); 
		UndoCaretaker uct = new UndoCaretaker(phc, uc, fc);
		uv.setUndoCaretaker(uct);

		TeamController tc = new TeamController(dateTracker); 

		HistoryView hv = new HistoryView(phc, uc, tc, dateTracker, uct); 

		WorkoutController wc = new WorkoutController(uc, phc); 
		WorkoutView wv = new WorkoutView(wc, uc); 

		IngredientDatabase idb = new IngredientDatabase("src/main/java/edu/rit/swen262/data/ingredients.csv"); 
		FoodView fv = new FoodView(idb, fc, uc, uct); 

		ShoppingListView slv = new ShoppingListView(idb, uc, uct);

		StockView sv = new StockView(uc);

		FileView filev = new FileView(phc);

		MainView mainView = new MainView(hv, wv, uv, fv, slv, sv, phm, filev, uc, uct, userDataPersistence); 
		mainView.showMenu(); 
	}
}