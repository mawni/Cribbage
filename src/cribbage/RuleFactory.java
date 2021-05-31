package cribbage;

public class RuleFactory {

	//singleton instance reference
    private static RuleFactory factory;

    public RuleFactory(){};

    //method to access the singleton
	public static RuleFactory getInstance() {
		if(factory == null){
			factory = new RuleFactory();
		}
		return factory;
	}

	//factory method to create the rule class
	public ScoreRule getRule(String ruleType){
		//sanity check
		if (ruleType == null){
			System.out.println("Invalid ruleType provided");
			return null;
		}

		//actual creation of rule classes
		if (ruleType.equalsIgnoreCase(FifteensRule.TYPE)) {
			return new FifteensRule();
		} else if (ruleType.equalsIgnoreCase(FlushRule.TYPE)) {
			return new FlushRule();
		} else if (ruleType.equalsIgnoreCase(JackRule.TYPE)) {
			return new JackRule();
		} else if (ruleType.equalsIgnoreCase(PairsRule.TYPE)) {
			return new PairsRule();
		} else if (ruleType.equalsIgnoreCase(RunsRule.TYPE)) {
			return new RunsRule();
		} else if (ruleType.equalsIgnoreCase(StarterRule.TYPE)) {
			return new StarterRule();
		} else {
			System.out.println("Invalid ruleType provided");
			return null;
		}
	}

	/* List of the rules we have
	FifteensRule.java
	FlushRule.java
	JackRule.java
	PairsRule.java
	RunsRule.java
	StarterRule.java
	 */

}
