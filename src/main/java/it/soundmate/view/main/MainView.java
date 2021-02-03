package it.soundmate.view.main;

import it.soundmate.exceptions.NavigationException;
import it.soundmate.model.User;
import it.soundmate.view.uicomponents.NavigationItem;
import it.soundmate.view.uicomponents.NavigationPane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class MainView extends Pane {


    private final BorderPane borderPane;
    private final NavigationPane navigationPane = NavigationPane.getInstance();
    private final NavigationAction navigationAction = new NavigationAction();
    private final User loggedUser;


    public MainView(User loggedUser) {
        this.loggedUser = loggedUser;
        this.borderPane = new BorderPane();
        this.borderPane.setLeft(navigationPane.getvBox());
        this.borderPane.setCenter(new ProfileView(loggedUser).getProfileVBox());
        this.setNavigationAction();
    }

    private void setNavigationAction() {
        for (NavigationItem item : this.navigationPane.getNavigationItemList()) {
            item.setOnMouseClicked(navigationAction);
        }
    }

    protected void navigateToPage(NavigationItem item) {
        switch (item.getType()) {
            case HOME:
                this.borderPane.setCenter(new HomeView(this.loggedUser).getContentVBox());
                break;
            case SEARCH:
                this.borderPane.setCenter(new SearchView(this.loggedUser).getContentVBox());
                break;
            case MESSAGES:
                this.borderPane.setCenter(new MessagesView(this.loggedUser).getContentVBox());
                break;
            case PROFILE:
                this.borderPane.setCenter(new ProfileView(this.loggedUser).getProfileVBox());
                break;
            case SETTINGS:
                this.borderPane.setCenter(new SettingsView().getSettingsVBox());
                break;
            default:
                throw new NavigationException("Unable to determine navigation page");
        }
    }

    public BorderPane getBorderPane() {
        return this.borderPane;
    }

    private class NavigationAction implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            for (NavigationItem item : navigationPane.getNavigationItemList()) {
                if (event.getSource() == item) {
                    if (!item.isSelected()) {
                        item.setSelected(true);
                        navigateToPage(item);
                    }
                } else {
                    item.setSelected(false);
                }
            }
        }
    }
}
