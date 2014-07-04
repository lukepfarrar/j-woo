
package jWufoo;


public class FormNotFoundException extends Exception {

    public FormNotFoundException(String formId) {
        super("The form "+formId+" could not be found.");
    }

}
