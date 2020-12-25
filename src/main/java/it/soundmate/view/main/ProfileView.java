package it.soundmate.view.main;
import it.soundmate.controller.ProfileController;
import it.soundmate.model.Solo;
import it.soundmate.model.User;
import it.soundmate.view.UIUtils;
import it.soundmate.view.profiles.SoloProfileSoloView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProfileView extends Pane {

    private final VBox profileVBox;
    private static final Logger logger = LoggerFactory.getLogger(ProfileView.class);

    public ProfileView(User user){
        ProfileController profileController = new ProfileController();
        this.profileVBox = new VBox();
        UIUtils.setBackgroundPane("#232323", this.profileVBox);
        switch (user.getUserType()) {
            case SOLO:
                Solo solo = profileController.getSoloFromUser(user);
                this.profileVBox.getChildren().add(new SoloProfileSoloView(solo).getSoloVBox());
                break;
            case BAND_ROOM_MANAGER:
            case BAND_MANAGER:
                break;
            default:
                logger.error("Error in profile view constructor");
        }
    }


    public VBox getProfileVBox(){
        return this.profileVBox;
    }
}
