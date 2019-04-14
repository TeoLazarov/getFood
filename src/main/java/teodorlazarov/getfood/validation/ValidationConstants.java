package teodorlazarov.getfood.validation;

final class ValidationConstants {

    //Product Validation Constants
    static final Long MAX_IMAGE_SIZE = 500000L;
    static final String PRODUCT_ALREADY_EXISTS_ERROR = "Product with this name already exists.";
    static final String SELECT_AN_IMAGE_ERROR = "Please select an image.";
    static final String IMAGE_MAX_FILE_SIZE_ERROR = String.format("Maximum file size allowed is %dKB.", MAX_IMAGE_SIZE/1000);

    //User Validation Constants
    static final String USERNAME_ALREADY_EXISTS_ERROR = "Username already exists!";
    static final String EMAIL_ALREADY_EXISTS_ERROR = "Email already exists!";
    static final String PASSWORDS_DONT_MATCH_ERROR = "Passwords don't match.";
}
