package logging;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class LogItem {
	public static final String LAST_TRADING_DATE="LastTradingDate";
	public static final String DELIVERY_DATE="DeliveryDate";
	public static final String MARKET="Market";
	public static final String TRADABLE="Tradable";
	public static final String LABEL="Label";
	
    private HashMap<String,String> keyValues=new HashMap<String,String>();
    private HashMap<String,String> sources=new HashMap<String,String>();
    
    private void fromString(String line) {
    	String [] parts=line.split(";");
    	String source="";
    	for(int i=0;i<parts.length;i++) {
    		String part=parts[i];
    		int index=part.indexOf('=');
    		if(index==-1) {
    			continue;
    		}
    		String key=part.substring(0, index);
    		String value=part.substring(index+1);
    		
    		if(key.equals("Source")) {
    			source=value;
    			continue;
    		}
    		
    		keyValues.put(key, value);
    	}
    	
    	for(String key : keyValues.keySet()) {
    		sources.put(key, source);
    	}
    }
    
    public String getField(String key) {
    	return keyValues.get(key);
    }
    
    public String getFieldSource(String key) {
    	return sources.get(key);
    }
    
    public Set<String> getAllFieldNames(){
    	return keyValues.keySet();
    }
    
    public void setField(String key, String value, String source) {
    	keyValues.put(key, value);
        sources.put(key, source);
    }
    
    public boolean hasField(String key) {
    	return keyValues.containsKey(key);
    }
    
    public String getLogString() {
    	boolean first=true;
    	StringBuilder sb=new StringBuilder();
    	for(Map.Entry<String, String> e: keyValues.entrySet()) {
    	    if(first) {
    	    	first=false;
    	    }
    	    else {
    	    	sb.append(' ');
    	    }
    	    sb.append(e.getKey()).append('=').append(e.getValue());
    	}
    	return sb.toString();
    }
    
    public static LogItem parse(String line) {
    	LogItem ret=new LogItem();
    	ret.fromString(line);
    	return ret;
    }
}
