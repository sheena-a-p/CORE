package com.core.system.util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import java.util.Locale;

/**
 *
 * @author Libeesh
 */
public class LanguageUtil {

    @Autowired
    private MessageSource messageSource;

    public String getTranslatedText(String msgKey, Object[] obj, String lang) {
        return messageSource.getMessage(msgKey, obj, Locale.ENGLISH);
    }
}