package ruleautomation.com.services

import ruleautomation.com.RELogger
import ruleautomation.com.entities.Fact

import scala.collection.mutable
import scala.collection.mutable.Map

// Collection of Facts and Data values used and modified by Rule Conditions and Actions
// Currently all values are Strings that need conversion for numerical comparisons
// Future: All values will be Fact Objects with Type information
object FactData {
	private var factDataMap: mutable.Map[String, Fact] = mutable.Map[String, Fact]()

	def initFactData(factData: mutable.Map[String, Fact]): Unit = factDataMap = factData

	def updateValue(dataElement: String, dataValue: Fact): Unit = factDataMap(dataElement) = dataValue

	def lookupValue(dataElement: String): Fact = {
		var factData: Fact = null
		try {
			factData = factDataMap(dataElement)
		} catch {
			case e: Exception => RELogger.log(s"Exception: Element Not Found: $dataElement", 5, "ERRR")
			case _: Throwable => RELogger.log(s"Exception: Unknown Error: $dataElement", 5, "ERRR")
		}
		finally {
			if (factData == null) factData = new Fact()
		}
		factData
	}
}


object HardCodedDataSets {

	def createDataSet(): mutable.Map[String, String] = {
		var factDataMap = mutable.Map[String, String]()
		factDataMap += ("PlayerDailySpend" -> "7500")
		factDataMap
	}


}