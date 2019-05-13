package logging;

import java.util.List;

public class LMETradingDateMerger extends LogItemMergerDecorator {

	public LMETradingDateMerger(LogItemMerger merger) {
		super(merger);
	}

	@Override
	public void continueMerge(List<LogItem> newCome, LogItem result) {
		String lastTradingDate=null;
		String lastTradingDateSource=null;
		for(LogItem logItem : newCome) {
			if(logItem.hasField(LogItem.LAST_TRADING_DATE)) {
				if(logItem.getFieldSource(LogItem.LAST_TRADING_DATE).equals("LME") || lastTradingDate==null||!lastTradingDateSource.equals("LME")) {
					lastTradingDate=logItem.getField(LogItem.LAST_TRADING_DATE);
					lastTradingDateSource=logItem.getFieldSource(LogItem.LAST_TRADING_DATE);
				}
			}
		}
		
		String deliveryDate=null;
		String deliveryDateSource=null;
		for(LogItem logItem : newCome) {
			if(logItem.hasField(LogItem.DELIVERY_DATE)) {
				if(logItem.getFieldSource(LogItem.DELIVERY_DATE).equals("LME") || deliveryDate==null||!deliveryDateSource.equals("LME")) {
					deliveryDate=logItem.getField(LogItem.DELIVERY_DATE);
					deliveryDateSource=logItem.getFieldSource(LogItem.DELIVERY_DATE);
				}
			}
		}
		
		if(lastTradingDate!=null) {
		    result.setField(LogItem.LAST_TRADING_DATE, lastTradingDate, lastTradingDateSource);
		}
		
		if(deliveryDate!=null) {
			result.setField(LogItem.DELIVERY_DATE, deliveryDate, deliveryDateSource);
		}
	}
	
}
