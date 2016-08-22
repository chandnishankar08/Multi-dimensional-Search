
/*
 * Long Project 4
 * Class to implement different operations for Multi-dimensional search
 * 
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.TreeSet;

public class MDS {

	TreeMap<Long, Data> idMap; // Map with 'id' as key and complete data as the
								// value
	TreeMap<Long, ArrayList<Long>> descMap; // Map with description number as
											// the
											// key and set of IDs as Value

	MDS() {
		idMap = new TreeMap<>();
		descMap = new TreeMap<>();
	}

	int insert2(long id, double price, TreeSet<Long> description) {

		TreeSet<Long> desc = new TreeSet<>(); // List of numbers in the
												// description
		if (idMap.containsKey(id)) {
			Data oldData = idMap.get(id);

			// Changing the description if description is updated
			if (!description.isEmpty()) {
				desc = description;
				// Deleting IDs from the old description numbers
				for (Long descValue : oldData.description) {
					if (descMap.get(descValue).size() == 1)
						descMap.remove(descValue);
					else
						descMap.get(descValue).remove(id);
				}
				// Adding IDs to Description map
				for (Long descValue : desc) {
					addToDesc(descValue, id);
				}
				oldData.description = desc;
			}

			oldData.price = price;
			idMap.put(id, oldData);
		} else {
			// Inserting new id to the Map
			if (!description.isEmpty()) {
				desc = description;
				// Adding IDs to Description map
				for (Long desValue : desc) {
					addToDesc(desValue, id);
				}
			}
			Data newData = new Data(id, price, desc);
			idMap.put(id, newData);

			return 1;
		}

		return 0;
	}

	// Helper function adding id to Description Map
	void addToDesc(Long desc, Long id) {
		if (descMap.containsKey(desc)) {
			descMap.get(desc).add(id);
		} else {
			ArrayList<Long> temp = new ArrayList<>(); // Temporary ArrayList to
														// store id
			temp.add(id);
			descMap.put(desc, temp);
		}
	}

	// This function returns the Price of the ID if found, else 0
	double find(long id) {

		if (idMap.containsKey(id))
			return idMap.get(id).price;
		else
			return 0;
	}

	/*
	 * This function deletes the ID from descMap and also from the idMap Returns
	 * the sum of description numbers of the ID
	 */
	long delete(long id) {

		if (idMap.containsKey(id)) {
			Data oldData = idMap.get(id);
			Long sumDesc = 0L;
			// Deleting ID from Description Map
			for (Long descValue : oldData.description) {
				sumDesc += descValue;
				if (descMap.get(descValue).size() == 1)
					descMap.remove(descValue);
				else
					descMap.get(descValue).remove(id);
			}
			idMap.remove(id);
			return sumDesc;
		} else
			return 0;
	}

	/*
	 * Returns minimum price among the IDs have description 'des'
	 */
	double findMinPrice(long des) {

		ArrayList<Long> ids = new ArrayList<>();
		Double minPrice = Double.MAX_VALUE;
		Double price;
		if (descMap.containsKey(des)) {
			ids = descMap.get(des);
			for (Long id : ids) {
				price = idMap.get(id).price;
				if (price < minPrice)
					minPrice = price;
			}
			return minPrice;
		}
		return 0;
	}

	/*
	 * Returns maximum price among the IDs have description 'des'
	 */
	double findMaxPrice(long des) {

		ArrayList<Long> ids = new ArrayList<>();
		Double maxPrice = 0.0;
		Double price;
		if (descMap.containsKey(des)) {
			ids = descMap.get(des);
			for (Long id : ids) {
				price = idMap.get(id).price;
				if (price > maxPrice)
					maxPrice = price;
			}
			return maxPrice;
		}
		return 0;
	}

	/*
	 * Returns number of IDs having description 'des' and the price of the item
	 * is in between 'lowPrice' and 'highPrice'
	 */
	int findPriceRange(long des, double lowPrice, double highPrice) {

		ArrayList<Long> ids = new ArrayList<>();
		Double price;
		int count = 0;
		if (descMap.containsKey(des)) {
			ids = descMap.get(des);
			for (Long id : ids) {
				price = idMap.get(id).price;
				if (price <= highPrice && price >= lowPrice)
					count++;
			}
			return count;
		}
		return 0;
	}

	/*
	 * Increases the price of the IDs ranging from 'minid' to 'maxid' by rate%.
	 * Returns the net increase of all the IDs
	 */
	double priceHike(long minid, long maxid, double rate) {

		if (rate == 0.0)
			return 0;
		rate = rate / 100;
		NavigableMap<Long, Data> nMap = idMap.subMap(minid, true, maxid, true);

		Double newPrice;
		Double incPrice;
		Double netIncrease = 0.0;
		double E = 0.00001;
		for (Data d : nMap.values()) {
			incPrice = d.price * rate;
			incPrice = Math.floor((incPrice + E) * 100) / 100;

			netIncrease += incPrice;
			newPrice = d.price + incPrice;
			newPrice = Math.floor((newPrice + E) * 100) / 100;

			d.price = newPrice;
		}
		netIncrease = Math.floor((netIncrease + E) * 100) / 100;
		return netIncrease;
	}

	/*
	 * Returns number of items with price in between 'lowPrice' and 'highPrice'
	 */
	int range(double lowPrice, double highPrice) {

		int count = 0;
		for (Data d : idMap.values()) {
			if (d.price <= highPrice && d.price >= lowPrice)
				count++;
		}
		return count;
	}

	/*
	 * Returns number of items that satisfy all of the following conditions: 1.
	 * The description of the item contains 8 or more numbers. 2. The
	 * description of the item contains exactly the same set of numbers as
	 * another item.
	 */
	int samesame() {

		TreeMap<TreeSet<Long>, Integer> descSet = new TreeMap<>(new Comparator<TreeSet<Long>>() {

			@Override
			public int compare(TreeSet<Long> set1, TreeSet<Long> set2) {

				Iterator<Long> itr1 = set1.iterator();
				Iterator<Long> itr2 = set2.iterator();

				Long des1, des2;
				while (itr1.hasNext() && itr2.hasNext()) {
					des1 = itr1.next();
					des2 = itr2.next();
					if (des1 > des2)
						return 1;
					else if (des1 < des2)
						return -1;
				}
				if (itr1.hasNext() && (!itr2.hasNext()))
					return 1;
				else if (itr2.hasNext() && (!itr1.hasNext()))
					return -1;
				else
					return 0;
			}
		});

		Integer count;
		for (Data d : idMap.values()) {
			if (d.description.size() >= 8) {

				count = descSet.get(d.description);
				if (count == null) {
					descSet.put(d.description, 1);
				} else {
					descSet.put(d.description, count + 1);
				}
			}
		}

		int finalCount = 0;
		for (Integer c : descSet.values()) {
			if (c > 1)
				finalCount += c;
		}
		return finalCount;
	}
}