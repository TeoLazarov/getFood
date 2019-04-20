package teodorlazarov.getfood.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import teodorlazarov.getfood.domain.entities.User;
import teodorlazarov.getfood.domain.models.binding.UserProfileBindingModel;
import teodorlazarov.getfood.repository.UserRepository;

import static teodorlazarov.getfood.validation.ValidationConstants.*;

@Component
public class UserProfileEditValidator implements org.springframework.validation.Validator {

    private final UserRepository userRepository;

    @Autowired
    public UserProfileEditValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserProfileBindingModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserProfileBindingModel user = (UserProfileBindingModel) target;

        if (this.userRepository.findByEmail(user.getEmail()).isPresent()){
            User userModel = this.userRepository.findByEmail(user.getEmail()).orElse(null);
            if (userModel != null && !userModel.getUsername().equals(user.getUsername())){
                errors.rejectValue("email", EMAIL_ALREADY_EXISTS_ERROR, EMAIL_ALREADY_EXISTS_ERROR);
            }
        }

        if ((user.getPassword().length() > 0 || user.getConfirmPassword().length() > 0) && !user.getPassword().equals(user.getConfirmPassword())){
            errors.rejectValue("password", PASSWORDS_DONT_MATCH_ERROR, PASSWORDS_DONT_MATCH_ERROR);
            errors.rejectValue("confirmPassword", PASSWORDS_DONT_MATCH_ERROR, PASSWORDS_DONT_MATCH_ERROR);
        }

    }
}
