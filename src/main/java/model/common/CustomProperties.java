package model.common;

import model.util.PropertyUtilities;

/**
 * wrapper of PropertyUtilities.
 *
 * @author skuarch
 */
public class CustomProperties extends PropertyUtilities {

    //==========================================================================
    /**
     * send to the super class the path of the file.
     */
    public CustomProperties() {
        super("configuration/application.properties");
    } // end CustomProperties

} // end class