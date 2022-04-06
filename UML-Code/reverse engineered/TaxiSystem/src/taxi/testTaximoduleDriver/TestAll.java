/*
 * Created on 01.09.2004
 */
package testTaximoduleDriver;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import tools.Randomizer;
/**
 * @author Thomas Misar e0025068@stud3.tuwien.ac.at
 */
public class TestAll {
	public static void main(String[] args) {
		final int MAXPOINTS = 166;
		int successful = 0;
		int failed = 0;
		int totalPoints = 0;
		Hashtable tests = new Hashtable();
		Hashtable points = new Hashtable();
		System.out.println(Randomizer.num);
		/* TESTS WITH ACCEPTANCE */
		/* 1 */
		System.out.println("**** DriverLogin ****");
		try {
			tests.put("DriverLogin", new Boolean(new TestDriverLogin().test()));
			points.put("DriverLogin", new Integer(TestDriverLogin.p));
		}
		catch (Throwable e1) {
			tests.put("DriverLogin", new Boolean(false));
		}
		/* 2 */
		System.out.println("**** DriverStartAlarm ****");
		try {
			tests.put("DriverStartAlarm", new Boolean(new TestDriverStartAlarm().test()));
			points.put("DriverStartAlarm", new Integer(TestDriverStartAlarm.p));
		}
		catch (Throwable e1) {
			tests.put("DriverStartAlarm", new Boolean(false));
		}
		/* 3 */
		System.out.println("**** DriverNewOrder ****");
		try {
			tests.put("DriverNewOrder", new Boolean(new TestDriverNewOrder().test()));
			points.put("DriverNewOrder", new Integer(TestDriverNewOrder.p));
		}
		catch (Throwable e1) {
			tests.put("DriverNewOrder", new Boolean(false));
		}
		/* 4 */
		System.out.println("**** DriverRequestViewDriver ****");
		try {
			tests.put("DriverRequestViewDriver", new Boolean(new TestDriverRequestViewDriver().test()));
			points.put("DriverRequestViewDriver", new Integer(TestDriverRequestViewDriver.p));
		}
		catch (Throwable e1) {
			tests.put("DriverRequestViewDriver", new Boolean(false));
		}
		/* 5 */
		System.out.println("**** DriverRequestZoneInfo ****");
		try {
			tests.put("DriverRequestZoneInfo", new Boolean(new TestDriverRequestZoneInfo().test()));
			points.put("DriverRequestZoneInfo", new Integer(TestDriverRequestZoneInfo.p));
		}
		catch (Throwable e1) {
			tests.put("DriverRequestZoneInfo", new Boolean(false));
		}
		/* 6 */
		System.out.println("**** DriverSendVoice ****");
		try {
			tests.put("DriverSendVoice", new Boolean(new TestDriverSendVoice().test()));
			points.put("DriverSendVoice", new Integer(TestDriverSendVoice.p));
		}
		catch (Throwable e1) {
			tests.put("DriverSendVoice", new Boolean(false));
		}
		/* 7 */
		System.out.println("**** DriverStartVoice ****");
		try {
			tests.put("DriverStartVoice", new Boolean(new TestDriverStartVoice().test()));
			points.put("DriverStartVoice", new Integer(TestDriverStartVoice.p));
		}
		catch (Throwable e1) {
			tests.put("DriverStartVoice", new Boolean(false));
		}
		/* 8 */
		System.out.println("**** DriverStopVoice ****");
		try {
			tests.put("DriverStopVoice", new Boolean(new TestDriverStopVoice().test()));
			points.put("DriverStopVoice", new Integer(TestDriverStopVoice.p));
		}
		catch (Throwable e1) {
			tests.put("DriverStopVoice", new Boolean(false));
		}
		/* 9 */
		System.out.println("**** DriverTerminateVoice ****");
		try {
			tests.put("DriverTerminateVoice", new Boolean(new TestDriverTerminateVoice().test()));
			points.put("DriverTerminateVoice", new Integer(TestDriverTerminateVoice.p));
		}
		catch (Throwable e1) {
			tests.put("DriverTerminateVoice", new Boolean(false));
		}
		/* 10 */
		System.out.println("**** DriverVoiceMessage ****");
		try {
			tests.put("DriverVoiceMessage", new Boolean(new TestDriverVoiceMessage().test()));
			points.put("DriverVoiceMessage", new Integer(TestDriverVoiceMessage.p));
		}
		catch (Throwable e1) {
			tests.put("DriverVoiceMessage", new Boolean(false));
		}
		/* ******************* */
		/* TESTS WITH REJECTANCE */
		/* 11 */
		System.out.println("**** DriverLogin2 ****");
		try {
			tests.put("DriverLogin2", new Boolean(new TestDriverLogin2().test()));
			points.put("DriverLogin2", new Integer(TestDriverLogin2.p));
		}
		catch (Throwable e1) {
			tests.put("DriverLogin2", new Boolean(false));
		}
		/* 23 */
		System.out.println("**** DriverNewOrder2 ****");
		try {
			tests.put("DriverNewOrder2", new Boolean(new TestDriverNewOrder2().test()));
			points.put("DriverNewOrder2", new Integer(TestDriverNewOrder2.p));
		}
		catch (Throwable e1) {
			tests.put("DriverNewOrder", new Boolean(false));
		}
		/* 12 */
		System.out.println("**** DriverStartVoice2 ****");
		try {
			tests.put("DriverStartVoice2", new Boolean(new TestDriverStartVoice2().test()));
			points.put("DriverStartVoice2", new Integer(TestDriverStartVoice2.p));
		}
		catch (Throwable e1) {
			tests.put("DriverStartVoice2", new Boolean(false));
		}
		/* ******************* */
		/* TESTS WITH SIGNALS INITIATED BY CENTRAL */
		/* 13 */
		System.out.println("**** ComLinkNewOrder ****");
		try {
			tests.put("ComLinkNewOrder", new Boolean(new TestComLinkNewOrder().test()));
			points.put("ComLinkNewOrder", new Integer(TestComLinkNewOrder.p));
		}
		catch (Throwable e1) {
			tests.put("ComLinkNewOrder", new Boolean(false));
		}
		/* 14 */
		System.out.println("**** ComLinkStartVoice ****");
		try {
			tests.put("ComLinkStartVoice", new Boolean(new TestComLinkStartVoice().test()));
			points.put("ComLinkStartVoice", new Integer(TestComLinkStartVoice.p));
		}
		catch (Throwable e1) {
			tests.put("ComLinkStartVoice", new Boolean(false));
		}
		/* 15 */
		System.out.println("**** ComLinkSendVoice ****");
		try {
			tests.put("ComLinkSendVoice", new Boolean(new TestComLinkSendVoice().test()));
			points.put("ComLinkSendVoice", new Integer(TestComLinkSendVoice.p));
		}
		catch (Throwable e1) {
			tests.put("ComLinkSendVoice", new Boolean(false));
		}
		/* 16 */
		System.out.println("**** ComLinkStopVoice ****");
		try {
			tests.put("ComLinkStopVoice", new Boolean(new TestComLinkStopVoice().test()));
			points.put("ComLinkStopVoice", new Integer(TestComLinkStopVoice.p));
		}
		catch (Throwable e1) {
			tests.put("ComLinkStopVoice", new Boolean(false));
		}
		/* 17 */
		System.out.println("**** ComLinkTerminateVoice ****");
		try {
			tests.put("ComLinkTerminateVoice", new Boolean(new TestComLinkTerminateVoice().test()));
			points.put("ComLinkTerminateVoice", new Integer(TestComLinkTerminateVoice.p));
		}
		catch (Throwable e1) {
			tests.put("ComLinkTerminateVoice", new Boolean(false));
		}
		/* 18 */
		System.out.println("**** ComLinkVoiceMessage ****");
		try {
			tests.put("ComLinkVoiceMessage", new Boolean(new TestComLinkVoiceMessage().test()));
			points.put("ComLinkVoiceMessage", new Integer(TestComLinkVoiceMessage.p));
		}
		catch (Throwable e1) {
			tests.put("ComLinkVoiceMessage", new Boolean(false));
		}
		/* ******************* */
		/* TESTS WITH SIGNALS INITIATED BY CENTRAL REFUSED BY DRIVER */
		/* 19 */
		System.out.println("**** ComLinkNewOrder2 ****");
		try {
			tests.put("ComLinkNewOrder2", new Boolean(new TestComLinkNewOrder2().test()));
			points.put("ComLinkNewOrder2", new Integer(TestComLinkNewOrder2.p));
		}
		catch (Throwable e1) {
			tests.put("ComLinkNewOrder2", new Boolean(false));
		}
		/* 20 */
		System.out.println("**** ComLinkSendVoice2 ****");
		try {
			tests.put("ComLinkSendVoice2", new Boolean(new TestComLinkSendVoice2().test()));
			points.put("ComLinkSendVoice2", new Integer(TestComLinkSendVoice2.p));
		}
		catch (Throwable e1) {
			tests.put("ComLinkSendVoice2", new Boolean(false));
		}
		/* 21 */
		System.out.println("**** ComLinkStartVoice2 ****");
		try {
			tests.put("ComLinkStartVoice2", new Boolean(new TestComLinkStartVoice2().test()));
			points.put("ComLinkStartVoice2", new Integer(TestComLinkStartVoice2.p));
		}
		catch (Throwable e1) {
			tests.put("ComLinkStartVoice2", new Boolean(false));
		}
		/* 22 */
		System.out.println("**** ComLinkStopVoice2 ****");
		try {
			tests.put("ComLinkStopVoice2", new Boolean(new TestComLinkStopVoice2().test()));
			points.put("ComLinkStopVoice2", new Integer(TestComLinkStopVoice2.p));
		}
		catch (Throwable e1) {
			tests.put("ComLinkStopVoice2", new Boolean(false));
		}
		/* ******************* */
		System.out.println(Randomizer.num);
		Enumeration keys = tests.keys();
		String s = null;
		String result = "********** RESULT **********\n\n";
		while (keys.hasMoreElements()) {
			s = (String) keys.nextElement();
			if (((Boolean) tests.get(s)).equals(new Boolean(true))) {
				totalPoints += ((Integer) points.get(s)).intValue();
				successful++;
				System.out.println(s + " succesful");
				result += s + " succesful\n";
			}
			else {
				failed++;
				System.out.println(s + " failed");
				result += s + " failed\n";
			}
		}
		System.out.println("--------------------------------\n" + successful + " Tests ok\n" + failed + " Tests failed"
							+ "\n\nPoints: " + totalPoints + " / " + MAXPOINTS);
		result += "--------------------------------\n" + successful + " Tests ok\n" + failed + " Tests failed"
					+ "\n\nPoints: " + totalPoints + " / " + MAXPOINTS;
		//write results to file
		File out = new File("results.txt");
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(out));
			bw.write(result);
			bw.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
	}
}