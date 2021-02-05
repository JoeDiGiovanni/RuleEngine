package ruleautomation.com.entities

import ruleautomation.com.RELogger

// Collection of one or more RuleSets - KnowledgeBase is evaluated and results are stored
class KnowledgeBase(kbName: String, ruleSets: List[RuleSet]) {
	override def toString: String = s"$kbName -> "
	private var kbStatus: String = "INIT"
	private val ruleSetsList: List[RuleSet] = ruleSets

	def getStatus: String = kbStatus
	def setStatus(status:String) {
		kbStatus = status
	}

	def getRuleSets: List[RuleSet] = ruleSetsList

	// ??? in RuleEngine
	// Begin the execution of the Rules of the loaded KnowledgeBase
	// Could add some parameters:  maxRulesToRun
	def evaluate(): Unit = {
		// currentKB will have one or more RuleSets
		// Evaluate one RuleSet at a time, building an agenda of Rules to execute
		for (ruleSet <- getRuleSets) {
			RELogger.log(s"Evaluating RuleSet: ${ruleSet.ruleSetName}")
			ruleSet.evaluate()
		}
	}

	// Check for activated Rules and execute them
	def evaluateRuleSets(): Unit = {
		// Evaluate Rule Conditions to determine if any qualify to be activated
		for (ruleSet <- ruleSetsList) {
			RELogger.log(s"Evaluating RuleSet: ${ruleSet.ruleSetName}")
			ruleSet.evaluate()
		}
		// Check for activated Rules and execute them
	}
}
