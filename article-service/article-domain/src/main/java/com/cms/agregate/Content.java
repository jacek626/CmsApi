package com.cms.agregate;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
//@ValueObject
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Content {
    private String text;
    private String title;
    private Language language;

  /*  public static Content of(String title, String text, Language language) {
        illegalArgumentException(isBlank(text) || text.length() < 25, "Article text must contains more than 25 characters");
        illegalArgumentException(isBlank(title) || title.length() < 25 || title.length() > 100
                , "Article title must contains between  5 and 100 characters");
        illegalArgumentException(isNull(language), "Language is required");

        return new Content(text, title, language);
    }*/
}
