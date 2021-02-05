package ruleautomation.com.services

import ruleautomation.com.RELogger
import ruleautomation.com.entities._

// Stores and Retrieves KnowledgeBases and RuleSets to/from persistent storage
//class RuleRepositoryService extends Repository {
object RuleRepositoryService {
	private var kbMap = scala.collection.mutable.Map[String, KnowledgeBase]()
	RELogger.log("RuleRepository Created", msgCategory = "INIT")

	// Return a copy of the requested KnowledgeBase from the repository
	// If the KnowledgeBase was not loaded yet, then it loads it from persistent storage
	// If the KnowledgeBase does not exist in persistent storage, it returns an empty KnowledgeBase
	def getKnowledgeBase(kbName: String): KnowledgeBase = {
		var kb: KnowledgeBase = null
		// Check if the KnowledgeBase is available
		if (!kbMap.contains(kbName)) {
			// Load the KnowledgeBase
			kb = loadKnowledgeBase(kbName)
			// Store the KnowledgeBase in the KB Map
			kbMap.put(kbName, kb)
		}
		kb = kbMap(kbName)
		// Return the KnowledgeBase
		kb
	}

	// Retrieve the requested KnowledgeBase from persistent storage into the repository
	// Return KnowledgeBase if it was found and loaded - else create a new KnowledgeBase
	private def loadKnowledgeBase(kbName: String): KnowledgeBase = {
		var kb: KnowledgeBase = null
		// Load the KnowledgeBase
		var loadOK: Boolean = false
		/* THE LOADING OF THE KNOWLEDGE BASE FROM EXTERNAL SOURCE HAPPENS HERE */
		// Look at adding exception handling for load errors
		if (!loadOK) {
			kb = HardCodedRepository.createKnowledgeBase()
		}
		kb.setStatus("LOADED")
		// Return the KnowledgeBase
		kb
	}
}

object HardCodedRepository {

	def createKnowledgeBase(): KnowledgeBase = {
		val name = "PlayerComps"
		val rs1 = createRuleSet()
		val rsList: List[RuleSet] = List( rs1 )
		val kb = new KnowledgeBase(name, rsList)
		kb    // return the created KnowledgeBase
	}

	def createRuleSet(): RuleSet = {
		val name = "PlayerEvaluation"
		val r1 = createRule1()
		val ruleList: List[Rule] = List( r1 )
		val ruleSet = new RuleSet(name, ruleList)
		ruleSet  // return the created RuleSet
	}

	def createRule1(): Rule = {
		val name = "RecentPlayHighSpend"
		val c1 = new RuleCondition("PlayerDailySpend", ">", "5000")
		val c2 = new RuleCondition("PlayerLastPlayed", "<", "30")
		val cList: List[RuleCondition] = List( c1, c2 )
		val a1 = new RuleAction("SetValue", "PlayerStatus", "->", "Gold")
		val a2 = new RuleAction("SetValue", "PlayerOffer", "->", "TRUE")
		val aList: List[RuleAction] = List( a1, a2 )
		val rule = new Rule(name, cList, aList)
		rule  // return the created Rule
	}
}
