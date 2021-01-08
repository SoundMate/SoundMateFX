package it.soundmate.view.main;
import it.soundmate.model.Solo;
import it.soundmate.model.User;
import it.soundmate.view.UIUtils;
import it.soundmate.view.profiles.solo.SoloProfileSoloView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProfileView extends Pane {

    private final VBox profileVBox;
    private static final Logger logger = LoggerFactory.getLogger(ProfileView.class);

    public ProfileView(User user){
        this.profileVBox = new VBox();
        UIUtils.setBackgroundPane("#232323", this.profileVBox);
        switch (user.getUserType()) {
            case SOLO:
                this.profileVBox.getChildren().add(new SoloProfileSoloView((Solo) user, this));
                break;
            case ROOM_RENTER:
            case BAND_MANAGER:
                break;
            default:
                logger.error("Error in profile view constructor");
        }
    }


    public VBox getProfileVBox(){
        return this.profileVBox;
    }

    public void setProfilePage(Pane profilePage) {
        this.profileVBox.getChildren().set(0, profilePage);
        logger.info("Profile Page Set");
    }

}
