package cribbage;

public class RuleFactory {

	//singleton instance referece
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
		if (ruleType.equalsIgnoreCase("fifteen")) {
			return new FifteensRule();
		} else if (ruleType.equalsIgnoreCase("thirtyone")) {
			return new ThirtyOnesRule();
		} else if (ruleType.equalsIgnoreCase("flush")) {
			return new FlushRule();
		} else if (ruleType.equalsIgnoreCase("jack")) {
			return new JackRule();
		} else if (ruleType.equalsIgnoreCase("lastcard")) {
			return new LastCardRule();
		} else if (ruleType.equalsIgnoreCase("pair")) {
			return new PairsRule();
		} else if (ruleType.equalsIgnoreCase("run")) {
			return new RunsRule();
		} else if (ruleType.equalsIgnoreCase("starter")) {
			return new StarterRule();
		} else if (ruleType.equalsIgnoreCase("total")){
			return new TotalRules();
		} else {
			System.out.println("Invalid ruleType provided");
			return null;
		}
	}

	/* List of the rules we have
	FifteensRule.java
	ThirtyOnesRule.java
	FlushRule.java
	JackRule.java
	LastCardRule.java
	PairsRule.java
	RunsRule.java
	StarterRule.java
	TotalsRule.java
	 */

}
