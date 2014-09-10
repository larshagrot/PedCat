/*
 * Copyright (c) Pedcat.
 *
 * All Rights Reserved. Reproduction in whole or in part is prohibited
 * without the written consent of the copyright owner.
 */

package se.pedcat.framework.common.model;

import java.io.*;



// TODO: Auto-generated Javadoc
/**
 * General base class for all Key classes.
 */
public interface Key
{
     
     /**
      * Gets the object id.
      *
      * @return the object id
      */
     public int getObjectId();
     
     /**
      * Gets the object id as string.
      *
      * @return the object id as string
      */
     public String getObjectIdAsString();
     
     /**
      * Gets the object id as int.
      *
      * @return the object id as int
      */
     public int getObjectIdAsInt();
     
     /**
      * Equals.
      *
      * @param object the object
      * @return true, if successful
      */
     public boolean equals(final Object object);
     
     /**
      * To string.
      *
      * @return the string
      */
     public String toString();
     
     /**
      * Hash code.
      *
      * @return the int
      */
     public int hashCode();
     
     /**
      * Checks if is null.
      *
      * @return true, if is null
      */
     public boolean isNull();
     
     /**
      * Checks if is string.
      *
      * @return true, if is string
      */
     public boolean isString();
     
     /**
      * Checks if is int.
      *
      * @return true, if is int
      */
     public boolean isInt();
}
