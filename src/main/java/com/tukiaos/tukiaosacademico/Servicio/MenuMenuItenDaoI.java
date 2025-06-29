package com.tukiaos.tukiaosacademico.Servicio;


import com.tukiaos.tukiaosacademico.dto.MenuMenuItenTO;

import java.util.List;
import java.util.Properties;

public interface MenuMenuItenDaoI {
    public List<MenuMenuItenTO> listaAccesos(String perfil, Properties idioma);
}
