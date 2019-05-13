package logging;

import java.util.List;

public class BasicMerger implements LogItemMerger{

	@Override
	public void merge(List<LogItem> newCome, LogItem result) {
		// simply do nothing		
	} 
}
