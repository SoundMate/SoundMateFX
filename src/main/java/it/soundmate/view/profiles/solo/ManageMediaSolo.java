package it.soundmate.view.profiles.solo;

import it.soundmate.constants.Style;
import it.soundmate.model.Solo;
import it.soundmate.view.UIUtils;
import it.soundmate.view.main.ProfileView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ManageMediaSolo extends Pane {

    private static final Logger logger = LoggerFactory.getLogger(ManageMediaSolo.class);
    protected final Solo soloUser;

    private VBox manageMediaVBox;
    private Button backBtn;

    public ManageMediaSolo(Solo soloUser) {
        this.soloUser = soloUser;

        this.manageMediaVBox = new VBox();
        this.manageMediaVBox.setPadding(new Insets(25));

        Label title = new Label("Manage Media for "+soloUser.getFirstName() + " "+soloUser.getLastName());
        title.setStyle(Style.PROFILE_NAME);

        this.backBtn = UIUtils.createStyledButton("Back", new BackAction());
        this.manageMediaVBox.getChildren().addAll(title, this.backBtn);

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
}
