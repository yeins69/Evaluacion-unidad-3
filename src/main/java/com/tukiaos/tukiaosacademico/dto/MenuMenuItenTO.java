package com.tukiaos.tukiaosacademico.dto;

import lombok.Data;

@Data
public class MenuMenuItenTO {
    public String menunombre, menuitemnombre, nombreObj;

    public MenuMenuItenTO(String menunombre, String menuitemnombre, String nombreObj) {
        this.menunombre = menunombre;
        this.menuitemnombre = menuitemnombre;
        this.nombreObj = nombreObj;
    }
    public MenuMenuItenTO() {
    }
}
