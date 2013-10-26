package com.devsquare.cc.log;

import java.io.PrintStream;

import com.devsquare.cc.interfaces.Constants;

public class Log {

	static PrintStream out = System.out;
	static LogMsg DEBUG = new Empty();
	static LogMsg INFO = DEBUG;
	static LogMsg WARN = INFO;
	static LogMsg ERROR = WARN;

	static {

		switch (Constants.LOG_LEVEL) {
		case 1:
			DEBUG = new Debug();
		case 2:
			INFO = new Info();
		case 3:
			WARN = new Warning();
		case 4:
			ERROR = new Error();
			break;
		default:
			DEBUG =INFO=WARN=ERROR = new Debug();
		}

	}

	public static void debug(String msg) {
		DEBUG.log(msg);
	}

	public static void info(String msg) {
		INFO.log(msg);
	}

	public static void warn(String msg) {
		WARN.log(msg);
	}

	public static void error(String msg) {
		ERROR.log(msg);
	}

	private interface LogMsg {

		void log(String msg);

		void log(Throwable t, String msg);

		void log(Throwable t);

	}

	private static class Empty implements LogMsg {

		@Override
		public void log(String msg) {
		}

		@Override
		public void log(Throwable t, String msg) {
		}

		@Override
		public void log(Throwable t) {
		}

	}

	private static class Debug extends Empty {

		public void log(String msg) {
			out.println(msg);
		}

	}

	private static class Info extends Debug {
	}

	private static class Warning extends Info {
	}

	private static class Error extends Warning {
	}

	public static void main(String args[]) {
		Log.debug("debug");
		Log.info("Info");
		Log.warn("warn");
		Log.error("Error");
		
	}

}
