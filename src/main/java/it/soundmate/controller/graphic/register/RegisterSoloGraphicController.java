/*
 * Copyright (c) 2021.
 * Created by Lorenzo Pantano on 13/01/21, 18:14
 * Last edited: 13/01/21, 18:14
 */

package it.soundmate.controller.graphic.register;
import it.soundmate.bean.registerbeans.RegisterBean;
import it.soundmate.bean.registerbeans.RegisterSoloBean;
import it.soundmate.controller.logic.RegisterController;
import it.soundmate.model.User;

public class RegisterSoloGraphicController implements RegisterGraphicController {

    RegisterController registerController = new RegisterController();

    @Override
    public User registerUser(RegisterBean registerBean) {
        return registerController.registerSolo((RegisterSoloBean) registerBean);
    }
}
