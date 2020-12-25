package it.soundmate.view.main;

import it.soundmate.constants.Style;
import it.soundmate.model.User;
import it.soundmate.utils.Cache;
import it.soundmate.view.UIUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProfileView extends Pane {

    private final VBox profileVBox;
    private static final Logger logger = LoggerFactory.getLogger(ProfileView.class);

    public ProfileView(User user){
        this.profileVBox = new VBox();
        UIUtils.setBackgroundPane("#232323", this.profileVBox);
        this.profileVBox.getChildren().add(buildUserInfoHBox(user));
    }

    private HBox buildUserInfoHBox(User user) {
        HBox userInfoHBox = new HBox();
        userInfoHBox.setAlignment(Pos.CENTER);
        userInfoHBox.setPadding(new Insets(50));

        //Profile Pic
        Circle profilePic = new Circle();
        profilePic.setRadius(65);
        Image profileImg;
        if (user.getProfilePic() == null) {
            profileImg = new Image("soundmate/images/user-default.png");
        } else {
            if (Cache.getInstance().getProfilePicFromCache(user) != null){
                profileImg = new Image(Cache.getInstance().getProfilePicFromCache(user));
            } else {
                profileImg = new Image("soundmate/images/user-default.png");
                logger.info("Unable to load from cache");
            }
        }
        profilePic.setFill(new ImagePattern(profileImg));

        //Name
        Label nameLabel = new Label(user.getFirstName() + " " + user.getLastName());
        nameLabel.setStyle(Style.HEADER_TEXT);

        userInfoHBox.getChildren().addAll(profilePic, nameLabel);
        return userInfoHBox;
    }

    public VBox getProfileVBox(){
        return this.profileVBox;
    }
}
