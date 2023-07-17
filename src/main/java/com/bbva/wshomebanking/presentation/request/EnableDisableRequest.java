package com.bbva.wshomebanking.presentation.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnableDisableRequest {
    private int id;
    private boolean active;
}
