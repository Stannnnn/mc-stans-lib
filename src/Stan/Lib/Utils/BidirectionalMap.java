//Version 1.0 (22-7-2016)
package Stan.Lib.Utils;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BidirectionalMap<KeyType, ValueType> {
	private final Map<KeyType, ValueType> keyToValueMap = new ConcurrentHashMap<KeyType, ValueType>();
	private final Map<ValueType, KeyType> valueToKeyMap = new ConcurrentHashMap<ValueType, KeyType>();

	synchronized public void put(KeyType key, ValueType value) {
		keyToValueMap.put(key, value);
		valueToKeyMap.put(value, key);
	}

	synchronized public ValueType removeByKey(KeyType key) {
		ValueType removedValue = keyToValueMap.remove(key);
		valueToKeyMap.remove(removedValue);
		return removedValue;
	}

	synchronized public KeyType removeByValue(ValueType value) {
		KeyType removedKey = valueToKeyMap.remove(value);
		keyToValueMap.remove(removedKey);
		return removedKey;
	}

	public boolean containsKey(KeyType key) {
		return keyToValueMap.containsKey(key);
	}

	public boolean containsValue(ValueType value) {
		return valueToKeyMap.containsKey(value);
	}

	public KeyType getKey(ValueType value) {
		return valueToKeyMap.get(value);
	}

	public ValueType getValue(KeyType key) {
		return keyToValueMap.get(key);
	}

	public Set<KeyType> keys() {
		return keyToValueMap.keySet();
	}

	public Set<ValueType> values() {
		return valueToKeyMap.keySet();
	}
}