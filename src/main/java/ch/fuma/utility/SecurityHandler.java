package ch.fuma.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * This class handles some basic security measures to prevent abuse.
 */
public class SecurityHandler {
    private static final int MAX_ATTEMPTS = 10;
    private static HashMap<String, Integer> ipAddressesAndCalls = new HashMap<>();
    private static HashMap<String, String> lockedDate = new HashMap<>();


    /**
     * This method resets the user lock, when the day has changed, based on the users IP.
     * Each user is allowed to have 10 attempts per day, before getting locked.
     * @param ipAddr
     */
    public static void resetLocksIfNewDay(String ipAddr) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (lockedDate.containsKey(ipAddr) && !dtf.format(LocalDate.now()).equals(lockedDate.get(ipAddr))) {
            lockedDate.remove(ipAddr);
            ipAddressesAndCalls.put(ipAddr, 1);
        }

    }

    /**
     * This method updates the attempts made per day, for each ip.
     * @param ipAddr
     */
    public static void updateCounterForIp(String ipAddr) {
        if ( ipAddressesAndCalls.containsKey(ipAddr)) {
            ipAddressesAndCalls.put(ipAddr, ipAddressesAndCalls.get(ipAddr) +1);
        } else {
            ipAddressesAndCalls.put(ipAddr,1);
        }
    }

    /**
     * CHecks if the user is below his max attempts and is still eligible to do more attempts.
     * @param ipAddr
     * @return
     */
    public static boolean isBelowMaxAttempts(String ipAddr) {
        return ipAddressesAndCalls.get(ipAddr) < MAX_ATTEMPTS;
    }

    /**
     * Locks the user for the current day.
     * @param ipAddr
     */
    public static void lockUser(String ipAddr) {
        if ( !lockedDate.containsKey(ipAddr)) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String newEntryPoint = dtf.format(LocalDate.now());
            lockedDate.put(ipAddr, newEntryPoint);
        }
    }
}
