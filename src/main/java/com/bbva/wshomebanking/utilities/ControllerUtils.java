package com.bbva.wshomebanking.utilities;

import com.bbva.wshomebanking.presentation.response.errors.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ControllerUtils {

    public static boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection authorities = authentication.getAuthorities();
        for (Object authority : authorities) {
            try {
                if (authority.toString().contains(Roles.ADMIN))
                    return true;
            } catch (Exception e) {
                continue;
            }
        }
        return false;
    }

    public static boolean isNotAdmin() {
        return !isAdmin();
    }

    public static String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static ArrayList<String> getErrorList(String... errors) {
        ArrayList<String> errorList = new ArrayList<>();
        errorList.add(Arrays.toString(errors));
        return errorList;
    }

    public static ResponseEntity getBadRequest(Exception e, String error) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponse(e.getMessage(), ControllerUtils.getErrorList(error)));
    }

}
