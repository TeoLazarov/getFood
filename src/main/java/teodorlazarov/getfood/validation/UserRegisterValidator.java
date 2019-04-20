package teodorlazarov.getfood.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import teodorlazarov.getfood.domain.entities.User;
import teodorlazarov.getfood.domain.models.binding.UserRegisterBindingModel;
import teodorlazarov.getfood.repository.UserRepository;

import static teodorlazarov.getfood.validation.ValidationConstants.*;

@Component
public class UserRegisterValidator implements org.springframework.validation.Validator {

    private final UserRepository userRepository;

    @Autowired
    public UserRegisterValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegisterBindingModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegisterBindingModel userRegisterBindingModel = (UserRegisterBindingModel) target;

        if (this.userRepository.findUserByUsername(userRegisterBindingModel.getUsername()).isPresent()){
            errors.rejectValue("username", USERNAME_ALREADY_EXISTS_ERROR, USERNAME_ALREADY_EXISTS_ERROR);
        }

        if (this.userRepository.findByEmail(userRegisterBindingModel.getEmail()).isPresent()){
            errors.rejectValue("email", EMAIL_ALREADY_EXISTS_ERROR, EMAIL_ALREADY_EXISTS_ERROR);
        }

        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())){
            errors.rejectValue("password", PASSWORDS_DONT_MATCH_ERROR, PASSWORDS_DONT_MATCH_ERROR);
            errors.rejectValue("confirmPassword", PASSWORDS_DONT_MATCH_ERROR, PASSWORDS_DONT_MATCH_ERROR);
        }
    }
}
