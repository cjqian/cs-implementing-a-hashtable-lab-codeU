/**
 * 
 */
package com.flatironschool.javacs;

import java.util.List;
import java.util.Map;

/**
 * Implementation of a HashMap using a collection of MyLinearMap and
 * resizing when there are too many entries.
 * 
 * @author downey
 * @param <K>
 * @param <V>
 *
 */
public class MyHashMap<K, V> extends MyBetterMap<K, V> implements Map<K, V> {
	
	// average number of entries per map before we rehash
	protected static final double FACTOR = 1.0;

	@Override
	public V put(K key, V value) {
		// check if the number of elements per map exceeds the threshold
		if (size() + 1 > maps.size() * FACTOR) {
			rehash();
		}
        
        //this should speed up a little since we don't need to add twice
		V oldValue = super.put(key, value);

		return oldValue;
	}

	/**
	 * Doubles the number of maps and rehashes the existing entries.
	 */
	/**
	 * 
	 */
	protected void rehash() {
        //collect the entries in the table
        List<MyLinearMap<K, V>> prevMaps = maps; 

        //resize the table
        int k = maps.size() * 2;
        makeMaps(k);

        //put the entries back in
        for (MyLinearMap<K, V> map : prevMaps){
            for (Map.Entry<K,V> entry : map.getEntries()){
                put(entry.getKey(), entry.getValue());
            }
        }
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, Integer> map = new MyHashMap<String, Integer>();
		for (int i=0; i<10; i++) {
			map.put(new Integer(i).toString(), i);
		}
		Integer value = map.get("3");
		System.out.println(value);
	}
}
