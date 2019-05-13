package logging;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class LogCollector {
	private static LogCollector instance=null;
	private static Object syncObj=new Object();
	
	public static LogCollector getInstance(LogPublisher publisher, LogItemMerger merger) {
		if(instance==null) {
			synchronized(syncObj) {
				if(instance==null) {
					//default and only publisher for this scope
					instance=new LogCollector(publisher, merger);
				}
			}
		}
		return instance;
	}
	
	private LogCollector(LogPublisher publisher, LogItemMerger merger) {
		this.publisher=publisher;
		this.merger=merger;
	}
	
	private ConcurrentHashMap<String,List<String>> collector=new ConcurrentHashMap<String,List<String>>();
	private LogPublisher publisher;
	private LogItemMerger merger;
	
	public void append(String key, String value) {
		// Since there could be very complicated merge logic, need to record all history for a key instead of only the last merged one.
		collector.compute(key, new BiFunction<String, List<String>, List<String>>(){

			@Override
			public List<String> apply(String arg0, List<String> arg1) {
				List<String> ret=null;
				if(arg1==null) {
					 ret=new ArrayList<String>();
					ret.add(value);
				}
				else {
					arg1.add(value);
					ret=arg1;
				}
				
				List<LogItem> collected=new ArrayList<LogItem>(ret.size());
				for(String logStr:ret) {
					collected.add(LogItem.parse(logStr));
				}
				LogItem merged=new LogItem();
				merger.merge(collected, merged);
				publisher.publish(merged.getLogString());
				
				return ret;
			}
		});
	}
	
	public List<String> get(String key) {
		//Ensure thread safe
		List<String> detachedList=new ArrayList<String>();
		collector.compute(key, new BiFunction<String, List<String>, List<String>>(){

			@Override
			public List<String> apply(String arg0, List<String> arg1) {
				if(arg1==null) {
					return detachedList;
				}
				for(String value : arg1) {
					detachedList.add(value);
				}
				
				return arg1;
			}
			
		});
					
		return detachedList;
	}
}
