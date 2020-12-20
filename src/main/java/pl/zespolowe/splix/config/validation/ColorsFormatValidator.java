package pl.zespolowe.splix.config.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Tomasz
 */
public class ColorsFormatValidator implements ConstraintValidator<Colors, String> {
    @Override
    public void initialize(Colors constraintAnnotation) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        //TODO Może kiedyś to napiszę XD
        /*String[] lines = s.split("\n");
        if (lines.length != 6) return false;
        for (String line : lines) {
            String[] nums = line.split(";");
            if (nums.length != 6) return false;
            for (String num : nums) {
                if (!num.matches("^#[a-fA-F0-9]{6}")) return false;
            }
        }*/
        return true;
    }
}
