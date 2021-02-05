package ruleautomation.com.entities

import ruleautomation.com.RELogger

class RuleSet(val ruleSetName: String, val rules: List[Rule]) {
	override def toString: String = s"$ruleSetName -> "
	private val rulesList: List[Rule] = rules

	// ??? in RuleEngine
	def evaluate(): Unit = {
		// evaluate each of the rules in the RuleSet to determine if any can be activated
		for (rule <- rulesList) {
			RELogger.log(s"Evaluating Rule: ${rule.ruleName}")
			rule.evaluate()
		}
		// Check for activated Rules and execute them
		// execute any Activate Rules on the Agenda
	}
}
