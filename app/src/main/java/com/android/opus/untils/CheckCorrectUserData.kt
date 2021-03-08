package com.android.opus.untils

import android.util.Patterns
import java.util.regex.Pattern

class CheckCorrectUserData {
    companion object {
        fun isValidUserName(userName: String): Boolean {
            val patternLatin: Pattern = Pattern.compile("^[A-Z][a-z]{1,}$")
            val patterCyrillic: Pattern = Pattern.compile("^[А-Я][а-я]{1,}$")

            return  userName.isNotEmpty() && (patternLatin.matcher(userName).matches()
                    || patterCyrillic.matcher(userName).matches())
        }

        fun isValidEmail(email: String) = email.isNotEmpty() && Patterns.EMAIL_ADDRESS
            .matcher(email).matches()

        fun isValidPassword(password: String): Boolean {
            val pattern: Pattern = Pattern.compile("^[A-Za-z0-9]{6,}$")
            return password.isNotEmpty() && pattern.matcher(password).matches()
        }

        fun isConfirmedPassword(firstPassword: String, secondPassword: String) = firstPassword
            .isNotEmpty()
                && firstPassword == secondPassword
    }
}