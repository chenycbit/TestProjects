package logging;

import java.util.List;

public interface LogItemMerger {
    public void merge(List<LogItem> newCome, LogItem result);
}
