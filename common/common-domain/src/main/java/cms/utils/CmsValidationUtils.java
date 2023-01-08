package cms.utils;

public class CmsValidationUtils {

    public static void illegalArgumentException(boolean isValidationError, String message) {
        if(isValidationError) {
            throw new IllegalArgumentException(message);
        }
    }
}
