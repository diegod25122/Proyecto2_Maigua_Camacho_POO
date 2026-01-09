package matriculacion.app.Main.services;

import matriculacion.app.Main.CRUD_DATOS.UsuarioDao;
import matriculacion.app.Main.model.Usuario;

public class UsuarioService {

    UsuarioDao dao = new UsuarioDao();

    public Usuario autenticar(String user, String pass) throws Exception {
        return dao.login(user, pass);
    }
}
