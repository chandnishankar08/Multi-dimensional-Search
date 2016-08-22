
import java.util.*;
import java.io.*;

/**
 * Sample driver code for Project LP4. Modify as needed. Do not change
 * input/output formats.
 * 
 * @author rbk
 */
public class LP4Driver {
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in;
		if (args.length > 0) {
			in = new Scanner(new File(args[0]));
		} else {
			in = new Scanner(System.in);
			System.out.println("Enter Input: ");
		}
		String s;
		double rv = 0;
		Timer timer = new Timer();
		MDS mds = new MDS();

		while (in.hasNext()) {
			s = in.next();
			if (s.charAt(0) == '#') {
				s = in.nextLine();
				continue;
			}
			if (s.equals("Insert")) {
				long id = in.nextLong();
				double price = in.nextDouble();
				long des = in.nextLong();
				TreeSet<Long> desc  = new TreeSet<>();
				
				while(des != 0 ){
					desc.add(des);
					des = in.nextInt();
				}
				rv += mds.insert2(id, price, desc);
			} else if (s.equals("Find")) {
				long id = in.nextLong();
				rv += mds.find(id);
			} else if (s.equals("Delete")) {
				long id = in.nextLong();
				rv += mds.delete(id);
			} else if (s.equals("FindMinPrice")) {
				long des = in.nextLong();
				rv += mds.findMinPrice(des);
			} else if (s.equals("FindMaxPrice")) {
				long des = in.nextLong();
				rv += mds.findMaxPrice(des);
			} else if (s.equals("FindPriceRange")) {
				long des = in.nextLong();
				double lowPrice = in.nextDouble();
				double highPrice = in.nextDouble();
				rv += mds.findPriceRange(des, lowPrice, highPrice);
			} else if (s.equals("PriceHike")) {
				long minid = in.nextLong();
				long maxid = in.nextLong();
				double rate = in.nextDouble();
				rv += mds.priceHike(minid, maxid, rate);
			} else if (s.equals("Range")) {
				double lowPrice = in.nextDouble();
				double highPrice = in.nextDouble();
				rv += mds.range(lowPrice, highPrice);
			} else if (s.equals("SameSame")) {
				rv += mds.samesame();
			} else if (s.equals("End")) {
				break;
			} else {
				System.out.println("Houston, we have a problem.\nUnexpected line in input: " + s);
				System.exit(0);
			}
		}
		System.out.printf("%.2f", rv);
		System.out.println();
		System.out.println(timer.end());
	}
}