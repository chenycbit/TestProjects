package logging;

import java.util.ArrayList;
import java.util.List;

public class ConsolePublisher implements LogPublisher{
	List<String> cacheBuffer=new ArrayList<String>();
	@Override
	public void publish(String logString) {
		cacheBuffer.add(logString);
		System.out.println(logString);		
	}
	
	public List<String> debugPublishHistoryRecent(){
		List<String> ret=new ArrayList<String>(cacheBuffer.size());
		ret.addAll(cacheBuffer);
		return ret;
	}
}
