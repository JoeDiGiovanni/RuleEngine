package ruleautomation.com.entities

import scala.util.control.Breaks.{break, breakable}

// Atomic structure combining the Conditions and Actions for evaluation and executing
// Rules are composed of a Left-Hand Side (LHS) and a Right-Hand Side (RHS)
// The LHS is a list of one or more Conditions that must evaluate to TRUE
// in order for the Rule to activate and subsequently fire the RHS list of Actions
class Rule(val ruleName: String, val conditions: List[RuleCondition], val actions: List[RuleAction]) {
	override def toString: String = s"$ruleName -> "
	private val conditionsList: List[RuleCondition] = conditions
	private val actionsList: List[RuleAction] = actions

	def evaluate(): Boolean = {
		val evalOK = evaluateConditions()
		if (evalOK) {
			// if all Conditions evaluate to true, the Rule is added to the Agenda
			RuleEngine.activateRule(this)
		}
		evalOK
	}

	def execute(): Boolean = {
		executeActions()
	}

	// Evaluate Rule Conditions to determine if any Rules qualify to be activated
	// Currently it will stop at the first False Condition as all Conditions are AND
	def evaluateConditions(): Boolean = {
		var evalOK = true
		breakable {
			for (condition <- conditionsList) {
				// Evaluate all the Conditions, stop at the first False Condition
				evalOK = evalOK && condition.evaluate()
				//RELogger.log(s"Evaluating Condition: $condition => $evalOK")
				if (!evalOK) break
			}
		}
		evalOK
	}

	def executeActions(): Boolean = {
		var execOK = false
		for (action <- actionsList) {
			// Add exception handling to execution errors
			action.execute()
		}
		execOK
	}
}
