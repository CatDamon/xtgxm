package ssm.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author dxgong
 *
 */
public class ListUtil {

	public static <T> boolean isBlank(List<T> list) {
		return (list == null || list.isEmpty());
	}

	
	public static <T> boolean isNotBlank(List<T> list) {
		return !isBlank(list);
	}

	public static <T> List<T> cloneList(List<T> list) {
		List<T> temp = new ArrayList<T>();
		if (list == null) {
			return temp;
		}
		temp.addAll(list);
		return temp;
	}

	public static <T> void clearList(List<T> list) {
		if (list != null) {
			list.clear();
		}
	}

	public static <T> List<List<T>> splitList(List<T> list, int maxSize) {
		List<List<T>> temp = new ArrayList<List<T>>();
		if (list.size() == 0 || maxSize <= 0) {
			return temp;
		}
		int i = 0;
		while (i < list.size()) {
			int toIndex = (i + maxSize);
			if (toIndex > list.size()) {
				toIndex = toIndex - maxSize + (list.size() - i);
			}
			temp.add(new ArrayList<T>(list.subList(i, toIndex)));
			i += maxSize;
		}
		return temp;
	}
	
	public static <T> List<T> map2List(Collection<T> vals){
		List<T> ts = new ArrayList<T>();
		if(vals == null){
			return ts;
		}
		Iterator<T> it = vals.iterator();
		while(it.hasNext()){
			ts.add(it.next());
		}
		return ts;
	}
}
