package ruleautomation.com.entities

import ruleautomation.com.services.RuleRepositoryService
import ruleautomation.com.{Agenda, RELogger}

// Single Instance (Singleton) of a RuleEngine object - NO LONGER
// RuleEngine maintains the current KnowledgeBase under evaluation
// Pass a KnowledgeBase (collection of RuleSets) for evaluation and execution of Rules */
//class RuleEngine() extends RuleRepositoryService {
object RuleEngine {
	var currentKB:KnowledgeBase = _

	def initRuleEngine(kbName: String): Boolean = {
		var status:String = ""
		// Load the specific KnowledgeBase into the Rule Engine
		currentKB = RuleRepositoryService.getKnowledgeBase(kbName)
		// If the KnowledgeBase cannot be found then the init fails
		status = currentKB.getStatus
		RELogger.log(s"currentKB.Status: $status", 5, "STAT")
		status == "LOADED"
	}

	def loadData(dataSetName: Any): Boolean = {
		var loadOK = true
		loadOK
	}

	// Begin the execution of the Rules of the loaded KnowledgeBase
	// Could add some parameters:  kbName, maxRulesToRun
	def evaluate(): Unit = {
		// Evaluate complete KnowledgeBase composed of one or more RuleSets
		currentKB.evaluate()
	}

	// ################################################################################
	// ################################################################################
	// Running the Rule Engine can be controlled completely in the RuleEngine object
	// or it can be handed off to each contained structure to encapsulate the functions
	//
	// Encapsulating the functions limits the ability to jump RuleSets, however,
	// if the Rule Engine controls all Rule Executions, it could easily activate
	// other RuleSets based on a RuleAction, thus supporting flow control rules
	// ################################################################################
	// ################################################################################

	// Begin the execution of the Rules of the loaded KnowledgeBase
	// Could add some parameters:  kbName, maxRulesToRun
	def evaluateKB(): Unit = {
		// currentKB will have one or more RuleSets
		// Evaluate one RuleSet at a time, building an agenda of Rules to execute
		for (ruleSet <- currentKB.getRuleSets) {
			evaluateRuleSet(ruleSet)
		}
	}

	def evaluateRuleSet(ruleSet: RuleSet): Unit = {
		// Evaluate Rules to determine if any qualify to be activated
		for (rule <- ruleSet.rules) {
			evaluateRule(rule)
		}
		// Check for activated Rules and execute them
	}

	def evaluateRule(rule:Rule): Unit = {
		val evalOK = rule.evaluateConditions()
		if (evalOK) {
			activateRule(rule)
		}
	}

	// Add to the Agenda for Execution
	def activateRule(rule: Rule): Unit = {
		Agenda.activateRule(rule)
	}

	// Execute the Rule Actions for a Rule off the Agenda
	def executeRule(rule: Rule): Boolean = {
		true
	}
}
