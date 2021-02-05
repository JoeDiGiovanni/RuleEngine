package ruleautomation.com

import ruleautomation.com.RELogger.logCategories

import scala.collection.mutable.ListBuffer

// Logger class with Log Level support and default config and message levels
// Log Message Levels:  5 = Highest Level  0 = Lowest Level  3 = Default
object RELogger {
	private val printAllMsgs: Boolean = true
	private val logLevel: Int = 3
	private val logList: ListBuffer[LogMsg] = new ListBuffer[LogMsg]
	private var logCategories = Map("MAIN" -> true, "INIT" -> false, "STAT" -> true, "EVAL" -> true, "EXEC" -> true)

	def log(msg: String, msgLevel: Int = 3, msgCategory: String = "MAIN"): Unit = {
		val logMsg: LogMsg = new LogMsg(msg, msgLevel, msgCategory)
		if (msgLevel >= logLevel) {
			logList.append(logMsg)
		}
		// add any new categories to avoid error in showLogs
		if (!logCategories.contains(msgCategory)) logCategories += (msgCategory -> true)
		// real-time output for debugging
		if (printAllMsgs) println(s"Log: $logMsg")
	}

	def showLogs(msgLevelShow: Int = 5, showCategory: String = "ALL"): Unit = {
		for (logMsg <- logList) {
			if (msgLevelShow >= logMsg.logLevel) {
				if ((showCategory == "ALL") || (logMsg.msgCategory == showCategory) || logCategories(showCategory)) {
					println(logMsg)
				}
			}
		}
	}

	// Log Message storage of text of log message and its log level
	private class LogMsg(val msg: String, val logLevel: Int, val msgCategory: String = "MAIN") {
		override def toString: String = s"$msgCategory: [$logLevel] $msg"
	}
}

