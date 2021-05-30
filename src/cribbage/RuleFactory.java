package cribbage;

public class RuleFactory {

    private static RuleFactory factory;
	public static RuleFactory getInstance() {
		if(factory == null) factory = new RuleFactory();
		return factory;
	}

}
