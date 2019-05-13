package logging;

import java.util.List;
import java.util.Set;

public class OtherFieldMerger extends LogItemMergerDecorator{

	public OtherFieldMerger(LogItemMerger merger) {
		super(merger);
	}

	@Override
	public void continueMerge(List<LogItem> newCome, LogItem result) {
		for(LogItem logItem : newCome) {
			Set<String> fields=logItem.getAllFieldNames();
			for(String key : fields) {
				if(key.equals(LogItem.DELIVERY_DATE)||key.equals(LogItem.LAST_TRADING_DATE)||key.equals(LogItem.TRADABLE)){
					continue;
				}
				//Use the latest value
				result.setField(key, logItem.getField(key), logItem.getFieldSource(key));
			}
		}
	}    
}
