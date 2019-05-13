package logging;

public class MainTest {
    public static void main(String [] args) {
    	test1();
    	System.out.println();
    	test2();
    	System.out.println();
    	test3();
    }
    public static void test1() {
    	LogItemMerger merger=new OtherFieldMerger(new LMETradingDateMerger(new PRIMETradableMerger(new BasicMerger())));
    	LogPublisher publisher=new ConsolePublisher();
    	LogCollector collector=LogCollector.getInstance(publisher, merger);
		collector.append("P3-2018", "Source=LME;Market=PB-LME;LastTradingDate=2019-03-29;DeliveryDate=2019-03-31;Label=FB_CMM_CN;Tradable=FALSE");
		collector.append("P3-2018", "Source=LME;Market=PB-LME;LastTradingDate=2019-04-03;DeliveryDate=2019-04-05;Label=FB_CMM_US;Tradable=FALSE");
		collector.append("P3-2018", "Source=PRIME;Market=PB-DCG;LastTradingDate=2019-03-09;DeliveryDate=2019-03-11;Label=FB_CMM_UK;Tradable=TRUE");
		collector.append("P3-2018", "Source=LME;Market=PB-NPC;LastTradingDate=2019-04-07;DeliveryDate=2019-04-11;Label=FB_CMM_CA;Tradable=FALSE");
    }
    
    public static void test2() {
    	LogItemMerger merger=new OtherFieldMerger(new LMETradingDateMerger(new PRIMETradableMerger(new BasicMerger())));
    	LogPublisher publisher=new ConsolePublisher();
    	LogCollector collector=LogCollector.getInstance(publisher, merger);
		collector.append("P3-2018", "Source=LME;Market=PB-LME;LastTradingDate=2019-03-29;Label=FB_CMM_CN;Tradable=FALSE");
		collector.append("P3-2018", "Source=LME;Market=PB-LME;LastTradingDate=2019-04-03;Label=FB_CMM_US;Tradable=FALSE");
		collector.append("P3-2018", "Source=PRIME;Market=PB-DCG;LastTradingDate=2019-03-09;DeliveryDate=2019-03-11;Label=FB_CMM_UK;Tradable=FALSE");
		collector.append("P3-2018", "Source=PRIME;Market=PB-NPC;LastTradingDate=2019-04-07;DeliveryDate=2019-04-11;Tradable=TRUE");
    }
    
    public static void test3() {
    	LogItemMerger merger=new OtherFieldMerger(new LMETradingDateMerger(new PRIMETradableMerger(new BasicMerger())));
    	LogPublisher publisher=new ConsolePublisher();
    	LogCollector collector=LogCollector.getInstance(publisher, merger);
    	
    	Runnable r1 = new Runnable() {

			@Override
			public void run() {
				collector.append("P3-2018", "Source=LME;Market=PB-LME;LastTradingDate=2019-03-29;DeliveryDate=2019-03-31;Label=FB_CMM_CN;Tradable=FALSE");
				collector.append("P3-2018", "Source=LME;Market=PB-LME;LastTradingDate=2019-04-03;DeliveryDate=2019-04-05;Label=FB_CMM_US;Tradable=FALSE");
				collector.append("P3-2018", "Source=PRIME;Market=PB-DCG;LastTradingDate=2019-03-09;DeliveryDate=2019-03-11;Label=FB_CMM_UK;Tradable=TRUE");
				collector.append("P3-2018", "Source=LME;Market=PB-NPC;LastTradingDate=2019-04-07;DeliveryDate=2019-04-11;Label=FB_CMM_CA;Tradable=FALSE");				
			}
    	};
    	
    	Runnable r2=new Runnable() {

			@Override
			public void run() {
				collector.append("P3-2018", "Source=LME;Market=PB-LME;LastTradingDate=2019-03-29;Label=FB_CMM_CN;Tradable=FALSE");
				collector.append("P3-2018", "Source=LME;Market=PB-LME;LastTradingDate=2019-04-03;Label=FB_CMM_US;Tradable=FALSE");
				collector.append("P3-2018", "Source=PRIME;Market=PB-DCG;LastTradingDate=2019-03-09;DeliveryDate=2019-03-11;Label=FB_CMM_UK;Tradable=FALSE");
				collector.append("P3-2018", "Source=PRIME;Market=PB-NPC;LastTradingDate=2019-04-07;DeliveryDate=2019-04-11;Tradable=TRUE");
			}    		
    	};
    	
    	Thread t1=new Thread(r1);
    	Thread t2=new Thread(r2);
    	
    	t1.start();
    	t2.start();
    	try {
	    	t1.join();
	    	t2.join();
    	}
    	catch(Exception e) {
    		System.out.println(e.getStackTrace());
    	}
    }
}
