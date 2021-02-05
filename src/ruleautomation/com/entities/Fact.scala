package ruleautomation.com.entities

class Fact (factObj: String, factAttr: String, dataVal: Any, val factST: FactStructType, val factDT: FactDataType) {
	val factNotFound: String = ""
	val factObjectName: String = factObj
	val factAttributeName: String = factAttr
	val factDataValue: String = dataVal.toString
	val factStructureType: FactStructType = factST
	val factDataType: FactDataType = factDT

	// Simple Flat Fact
	def this() = {
		this("", "", "", NULL, NONE)
	}

	// Simple Flat Fact
	def this(simpleFact: String) = {
		this(simpleFact, "", "", SIMPLE, STRING)
	}

	// Data Element Name and Data Value
	def this(dataElement: String, dataValue: String) = {
		this(dataElement, "", "", ELEMENT, STRING)
	}
	def this(dataElement: String, dataValue: Int) = {
		this(dataElement, "", "", ELEMENT, NUMBER)
	}

	// Object Attribute Pair and Data Value
	def this(factObj: String, factAttr: String, dataValue: String) = {
		this(factObj, factAttr, dataValue, OBJ_ATTR, STRING)
	}
	def this(factObj: String, factAttr: String, dataValue: Int) = {
		this(factObj, factAttr, dataValue, OBJ_ATTR, NUMBER)
	}
}

sealed trait FactStructType
case object NULL extends FactStructType
case object SIMPLE extends FactStructType
case object ELEMENT extends FactStructType
case object OBJ_ATTR extends FactStructType


sealed trait FactDataType
case object NONE extends FactDataType
case object STRING extends FactDataType
case object NUMBER extends FactDataType
