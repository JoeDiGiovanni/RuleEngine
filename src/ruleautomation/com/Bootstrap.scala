package ruleautomation.com

import ruleautomation.com.entities.RuleEngine
import ruleautomation.com.services.RuleRepositoryService

/* Entrypoint to RuleEngine application */
/* Accepts name of RuleSet(s) to execute and performs load and execution of rules */
object Bootstrap {

	def main(args: Array[String]): Unit = {
		if (args.length < 2) {
			RELogger.log("KnowledgeBase name and DataSet name need to be provided", 5, "ERRR")
		}
		else if (args.length > 2) {
			RELogger.log("Only one KnowledgeBase and DataSet can be executed at a time", 5, "ERRR")
		} else {
			val kbName = args(0)
			val dataSetName = args(1)

			// Load the KnowledgeBase from the Rule Repository and pass it to the RuleEngine for execution
			val loadOK = RuleRepositoryService.getKnowledgeBase(kbName)
			// Two RuleSets: QualifyPlayers AwardComps
			RELogger.log("KnowledgeBase Loaded:")

			// Init the RuleEngine, load data and Run the RuleEngine
			//var ruleEngine = new RuleEngine()
			RuleEngine.initRuleEngine(kbName)
			RuleEngine.loadData(dataSetName)
			RuleEngine.evaluate()

			RELogger.log("RuleEngine Complete")
			RELogger.showLogs()
		}
	}
}
