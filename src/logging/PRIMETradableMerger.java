package logging;

import java.util.List;

public class PRIMETradableMerger extends LogItemMergerDecorator{

	public PRIMETradableMerger(LogItemMerger merger) {
		super(merger);
	}

	@Override
	public void continueMerge(List<LogItem> newCome, LogItem result) {
		String tradable=null;
		String tradableSource=null;
		for(LogItem logItem : newCome) {
			if(logItem.hasField(LogItem.TRADABLE)) {
				if(logItem.getFieldSource(LogItem.TRADABLE).equals("PRIME") || tradable==null||!tradableSource.equals("PRIME")) {
					tradable=logItem.getField(LogItem.TRADABLE);
					tradableSource=logItem.getFieldSource(LogItem.TRADABLE);
				}
			}
		}
		
		if(tradable!=null) {
			result.setField(LogItem.TRADABLE, tradable, tradableSource);
		}
	}
	

}
