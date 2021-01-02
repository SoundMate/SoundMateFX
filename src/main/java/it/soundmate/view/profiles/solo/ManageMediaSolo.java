package it.soundmate.view.profiles.solo;

import it.soundmate.constants.Style;
import it.soundmate.model.Solo;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.ProfileView;
import it.soundmate.view.uicomponents.PhotoUIElement;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

public class ManageMediaSolo extends Pane {

    private static final Logger logger = LoggerFactory.getLogger(ManageMediaSolo.class);
    protected final Solo soloUser;

    private VBox manageMediaVBox;
    private Button backBtn;
    private HBox photosHBox;
    private HBox videosHBox;

    public ManageMediaSolo(Solo soloUser) {
        this.soloUser = soloUser;

        this.manageMediaVBox = new VBox();
        this.manageMediaVBox.setPadding(new Insets(25));

        Label title = new Label("Manage Media for "+soloUser.getFirstName() + " "+soloUser.getLastName());
        title.setStyle(Style.PROFILE_NAME);

        this.photosHBox = buildPhotosHBox(soloUser);
        this.videosHBox = buildVideosHBox(soloUser);

        Label photosTitle = new Label("Photos");
        photosTitle.setStyle(Style.MID_LABEL);
        photosTitle.setPadding(new Insets(50,0,15,0));
        Label videosTitle = new Label("Videos");
        videosTitle.setPadding(new Insets(50,0,15,0));
        videosTitle.setStyle(Style.MID_LABEL);

        this.backBtn = UIUtils.createStyledButton("Back", new BackAction());
        this.manageMediaVBox.getChildren().addAll(title, photosTitle, this.photosHBox, videosTitle, this.videosHBox, this.backBtn);

    }

    private HBox buildVideosHBox(Solo soloUser) {
        HBox videosList = new HBox();
        videosList.setAlignment(Pos.CENTER_LEFT);
        videosList.setSpacing(10);
        //TODO: Pick videos from user's database

        Button addVideosBtn = UIUtils.createStyledButton("Add Video", new AddVideoAction());
        videosList.getChildren().addAll(addVideosBtn);
        return videosList;
    }

    private HBox buildPhotosHBox(Solo soloUser) {

        HBox photosList = new HBox();
        photosList.setAlignment(Pos.CENTER_LEFT);
        photosList.setSpacing(10);

        List<PhotoUIElement> photoUIElementList = buildUIFromPhotos(soloUser);
        if (photoUIElementList.isEmpty()) {
            Label addPhotosLabel = new Label("Add Photos");
            addPhotosLabel.setStyle(Style.LOW_LABEL);
            photosList.getChildren().add(addPhotosLabel);
        }

        Button addPhotoBtn = UIUtils.createStyledButton("Add Photos", new AddPhotoAction());
        UIUtils.addRegion(null, photosList);
        photosList.getChildren().add(addPhotoBtn);
        return photosList;

    }

    private List<PhotoUIElement> buildUIFromPhotos(Solo soloUser) {
       /*
          Molto probabilmente va modificato il tipo di photos nelle classi User Solo ecc..
          da File a InputStream.
          1 perché nel db vengono salvate così
          2 perché così è più facile costruire la Image di javafx che ha un costruttore new Image(inputstream)
       */
        List<PhotoUIElement> photoUIElementList = new ArrayList<>();
        return photoUIElementList;
    }

    public VBox getManageMediaVBox() {
        return manageMediaVBox;
    }

    private class BackAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Back clicked");
            ProfileView.getInstance(soloUser).setProfilePage(SoloProfileSoloView.getInstance(soloUser).getSoloVBox());
        }
    }

    private class AddPhotoAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Add Photo Clicked");
        }
    }

    private class AddVideoAction implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            logger.info("Add Video Clicked");
        }
    }
}
