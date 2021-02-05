package ruleautomation.com

import ruleautomation.com.entities.Rule

import scala.collection.mutable.ListBuffer

// Manage list of Activated Rules that are queued up to be executed
object Agenda {
	var activatedRules: ListBuffer[Rule] = new ListBuffer[Rule]
	RELogger.log("Agenda Created")

	def activateRule(rule:Rule): Unit = {
		activatedRules += rule
		RELogger.log("Rule: $rule")
	}
}
