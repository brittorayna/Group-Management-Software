/**
Copyright (c) 2024 Sami Menik, PhD. All rights reserved.

This is a project developed by Dr. Menik to give the students an opportunity to apply database concepts learned in the class in a real world project. Permission is granted to host a running version of this software and to use images or videos of this work solely for the purpose of demonstrating the work to potential employers. Any form of reproduction, distribution, or transmission of the software's source code, in part or whole, without the prior written consent of the copyright owner, is strictly prohibited.
*/
package uga.menik.cs4370.models;

/**
 * Represents a user of the micro blogging platform.
 */
public class User {

    /**
     * Unique identifier for the user.
     */
    private final String userId;

    /**
     * First name of the user.
     */
    private final String firstName;

    /**
     * Last name of the user.
     */
    private final String lastName;

   
    /**
     * Constructs a User with specified details.
     *
     * @param userId           the unique identifier of the user
     * @param firstName        the first name of the user
     * @param lastName         the last name of the user
     */
    public User(String userId, String firstName, String lastName) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }


      /**
     * Returns the string of user ID.
     *
     * @return the string of user ID
     */
    public String getStrUserId() {
        return userId;
    }

    /**
     * Returns the int of user ID.
     *
     * @return the int of user ID
     */
    public int getUserId() {
        return Integer.parseInt(userId);
    }

    /**
     * Returns the first name of the user.
     *
     * @return the first name of the user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name of the user.
     *
     * @return the last name of the user
     */
    public String getLastName() {
        return lastName;
    }
  
}
