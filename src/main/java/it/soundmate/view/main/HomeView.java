package it.soundmate.view.main;

import it.soundmate.bean.searchbeans.BandResultBean;
import it.soundmate.bean.searchbeans.RoomRenterResultBean;
import it.soundmate.bean.searchbeans.SoloResultBean;
import it.soundmate.constants.Style;
import it.soundmate.controller.logic.HomeController;
import it.soundmate.database.dbexceptions.RepositoryException;
import it.soundmate.model.User;
import it.soundmate.view.UIUtils;
import it.soundmate.view.search.BandResults;
import it.soundmate.view.search.RenterResults;
import it.soundmate.view.search.SoloResults;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HomeView extends SearchingView {

    public static final String REPOSITORY_EXCEPTION = "Repository Exception: {}";
    public static final String STYLE = "-fx-background-color: #232323; -fx-border-color: #232323";
    private final VBox contentVBox;
    private static final Logger logger = LoggerFactory.getLogger(HomeView.class);
    private final HomeController homeController;

    //UI
    private final SoloResults soloResultBeanListView = new SoloResults(this);
    private final BandResults bandResultBeanListView = new BandResults(this);
    private final RenterResults roomRenterResultBeanListView = new RenterResults(this);

    public HomeView(User user) {
        super(user);
        this.homeController = new HomeController(user);
        this.contentVBox = new VBox();
        this.contentVBox.setPrefHeight(USE_COMPUTED_SIZE);
        UIUtils.setBackgroundPane("#232323", this.contentVBox);
        this.contentVBox.getChildren().add(buildContentVBox(user));
    }

    public VBox buildContentVBox(User user) {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(25));
        vBox.setSpacing(10);
        vBox.setPrefHeight(USE_COMPUTED_SIZE);

        //Welcome Label
        Label welcomeLabel = new Label("Welcome");
        welcomeLabel.setStyle(Style.HEADER_TEXT);
        vBox.getChildren().add(welcomeLabel);

        //Musicians around you
        buildMusiciansAroundYou(user, vBox);
        //Bands you may like
        buildBandsYouMayLike(user, vBox);
        //Room Renters near you
        buildRoomRentersNearYou(user, vBox);

        return vBox;
    }

    private void buildRoomRentersNearYou(User user, VBox vBox) {
        Label rentersLabel = new Label("Room Renters near you");
        rentersLabel.setStyle(Style.MID_LABEL);
        vBox.getChildren().add(rentersLabel);

        this.roomRenterResultBeanListView.setStyle(STYLE);
        try {
            List<RoomRenterResultBean> roomRenterResultBeanList = homeController.searchHomeRenters(user.getCity());
            ObservableList<RoomRenterResultBean> renterResultBeanObservableList = FXCollections.observableArrayList(roomRenterResultBeanList);
            this.roomRenterResultBeanListView.setItems(renterResultBeanObservableList);
            vBox.getChildren().addAll(this.roomRenterResultBeanListView);
        } catch (RepositoryException repositoryException) {
            logger.error(REPOSITORY_EXCEPTION, repositoryException.getMessage());
            vBox.getChildren().add(new Label("Error fetching near bands"));
        }
    }

    private void buildBandsYouMayLike(User user, VBox vBox) {
        Label bandsLabel = new Label("Bands near you");
        bandsLabel.setStyle(Style.MID_LABEL);
        vBox.getChildren().add(bandsLabel);

        this.bandResultBeanListView.setStyle(STYLE);
        try {
            List<BandResultBean> bandResultBeanList = homeController.searchHomeBands(user.getCity());
            ObservableList<BandResultBean> bandResultBeanObservableList = FXCollections.observableArrayList(bandResultBeanList);
            this.bandResultBeanListView.setItems(bandResultBeanObservableList);
            vBox.getChildren().addAll(this.bandResultBeanListView);
        } catch (RepositoryException repositoryException) {
            logger.error(REPOSITORY_EXCEPTION, repositoryException.getMessage());
            vBox.getChildren().add(new Label("Error fetching near bands"));
        }
    }

    public void buildMusiciansAroundYou(User user, VBox vBox) {
        Label musiciansLabel = new Label("Musicians around you");
        musiciansLabel.setStyle(Style.MID_LABEL);
        vBox.getChildren().add(musiciansLabel);

        this.soloResultBeanListView.setStyle(STYLE);
        try {
            List<SoloResultBean> soloResultBeanList = homeController.searchHomeSolos(user.getCity());
            ObservableList<SoloResultBean> soloResultBeanObservableList = FXCollections.observableArrayList(soloResultBeanList);
            this.soloResultBeanListView.setItems(soloResultBeanObservableList);
            vBox.getChildren().addAll(this.soloResultBeanListView);
        } catch (RepositoryException repositoryException) {
            logger.error(REPOSITORY_EXCEPTION, repositoryException.getMessage());
            vBox.getChildren().add(new Label("Error fetching near musicians"));
        }
    }

    @Override
    public VBox getContentVBox() {
        return this.contentVBox;
    }
}
