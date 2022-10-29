package in.HCL.sanjib.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//JDK 8  (static methods + default methods allowed)
public interface MyCollectionsUtil {
	
public static Map<Long, String> convertListToMap(List<Object[]> list){
		
		//Java 8 Stream API
		
			return	list
				.stream()
				.collect(
						Collectors.toMap(
						ob->Long.valueOf(ob[0].toString()),//MapKey <--Object[]-Index#0
								
						ob->ob[1].toString() //Map value --Object[]#index#1
						));
		
		
	}


}
