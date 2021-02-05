package ruleautomation.com.entities

import ruleautomation.com._
import ruleautomation.com.services.FactData

// A single Condition is composed of a Comparison Value, a Comparison Operator and an Operand
// The comparison value is the unique name of a value known to the RuleEngine
// It can take the form of:  object.attribute  or  object::attribute  or simply:  elementName
class RuleCondition(val compElement: String, val compOperator: String, val compOperand: String) {
	override def toString: String = s"$compElement $compOperator $compOperand"

	// Log some info for now - remove when done
	RELogger.log(s"RuleCondition: $this", msgCategory = "INIT")

	def evaluate(): Boolean = {
		var eval = false
		// Retrieve the matching Fact with the a value or NULL if the element is not found
		val factValue = FactData.lookupValue(compElement)
		eval = factValue.factStructureType match {
			case SIMPLE => true   // Checks for the existence of the named Fact otherwise NULL
			case ELEMENT => evalElement(factValue)
			case OBJ_ATTR => evalElement(factValue)
			case NULL | _ => false
		}
		// Log the Rule Evaluation
		RELogger.log(s"RuleCondition.Evaluate: $this => $eval", 5, msgCategory = "EVAL")
		eval
	}

	def evalElement(factValue: Fact): Boolean = {
		var eval = false
		val compElValue = factValue.factDataValue.toInt
		val compOpValue = compOperand.toInt
		eval = compOperator match {
			case "<" => compElValue < compOpValue
			case "<=" => compElValue <= compOpValue
			case "==" => compElValue == compOpValue
			case ">=" => compElValue >= compOpValue
			case ">" => compElValue > compOpValue
			case "" | _ => false
		}
		eval
	}
}

/// Future support will include Object/Attribute splits for identifying named values
//class RuleCondition(val compVal: String, val valAtt: String, val compOperator: String, val operand: String ) {
