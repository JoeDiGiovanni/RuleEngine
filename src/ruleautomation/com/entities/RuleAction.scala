package ruleautomation.com.entities

import ruleautomation.com.RELogger
import ruleautomation.com.entities.RuleActionTypes.RuleActionTypes

import scala.collection.mutable.ListBuffer

// RuleAction is composed of an ActionType and Parameters used to execute the action
class RuleAction(val action: String, val parameters: String*) {
	val actionType: RuleActionTypes = RuleActionTypes.convStringToType(action)
	var actionParameters = new ListBuffer[String]()
	for (p <- parameters) {
		actionParameters += p
	}

	// Log some info for now - remove when done
	RELogger.log("RuleAction: " + actionType, msgCategory = "INIT")
	for (p <- actionParameters) {
		RELogger.log("Parameter: " + p, msgCategory = "INIT")
	}

	def execute(): Unit = {
		// Log the Rule Execution
		RELogger.log("RuleAction.execute: " + actionType, 5,  msgCategory = "EXEC")
	}

}

// Enumerated List of Rule Action Types the RuleEngine supports
object RuleActionTypes extends Enumeration {
	type RuleActionTypes = Value
	val SetValue, Print, Log, Unknown = Value

	def convStringToType(actionName: String): Value =
		values.find(_.toString.toLowerCase == actionName.toLowerCase()).getOrElse(Unknown)
}
