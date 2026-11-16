/*
 * Author: Vihanga
 * Group: 2 | Project: P2 Bank Account System
 * File: Transactable.java
 */

/** Contract for anything that can receive and release money. */
public interface Transactable {

    /** Adds money; returns false if the amount is not positive. */
    boolean deposit(double amount);

    /** Removes money; returns false if the rules do not allow it. */
    boolean withdraw(double amount);
}
