
/*
 * Long Project 4
 * Input Data for Multi-dimensional search
 */
import java.util.TreeSet;

public class Data {

	long id; // ID of the Item
	double price; // Price of the Item
	TreeSet<Long> description = new TreeSet<>(); // To store list of long
													// integers in the
													// description

	Data(long newId, double newPrice, TreeSet<Long> newDescription) {
		this.id = newId;
		this.price = newPrice;
		this.description = newDescription;
	}
}
