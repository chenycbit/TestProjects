package logging;

import java.util.List;

public abstract class LogItemMergerDecorator implements LogItemMerger{
    private LogItemMerger merger;
    
    public LogItemMergerDecorator(LogItemMerger merger) {
    	this.merger=merger;
    }
    
    @Override
    public void merge(List<LogItem> newCome, LogItem result) {
    	merger.merge(newCome, result);
    	continueMerge(newCome, result);
    }
    
    public abstract void continueMerge(List<LogItem> newCome, LogItem result);
}
