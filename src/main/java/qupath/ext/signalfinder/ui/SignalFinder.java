package qupath.ext.signalfinder.ui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qupath.fx.dialogs.Dialogs;
import qupath.fx.prefs.annotations.DirectoryPref;
import qupath.fx.prefs.annotations.Pref;
import qupath.fx.prefs.annotations.PrefCategory;
import qupath.fx.prefs.controlsfx.PropertyItemBuilder;
import qupath.fx.prefs.controlsfx.PropertySheetUtils;
import qupath.fx.utils.FXUtils;
import qupath.lib.common.Version;
import qupath.lib.gui.QuPathGUI;
import qupath.lib.gui.extensions.GitHubProject;
import qupath.lib.gui.extensions.QuPathExtension;
import qupath.lib.gui.prefs.PathPrefs;

import java.io.IOException;
import java.util.ResourceBundle;
/**
 * Interface for SignalFinder extension.
 * Adapted from the Instanseg GUI https://github.com/qupath/qupath-extension-instanseg/blob/main/src/main/java/qupath/ext/instanseg/ui/InstanSegExtension.java
 *
 * @author Zachary Klamer; Shashvat Shah
 *
 */
public class SignalFinder_GUI implements QuPathExtension {
	

	private static final String EXTENSION_NAME = "SignalFinder";

	private static final String EXTENSION_DESCRIPTION = "Implementation of the SignalFinder Algorithm in QuPath using Cell Compartment Measurements";

	private static final Version EXTENSION_QUPATH_VERSION = Version.parse("v0.6.0");

	private boolean isInstalled = false;

	public void installExtension(QuPathGUI qupath){
		if (isInstalled) {
			logger.debug("{} is already installed", getName());
			return;
		}
		isInstalled = true;
		addMenuItem(qupath);
	}
	private void addMenuItem(QuPathGUI qupath) {
		var menu = qupath.getMenu("Extensions>" + EXTENSION_NAME, true);
		MenuItem menuItem = new MenuItem("Run SignalFinder");
		//Not sure what these do - they were in the Instaseg example
		menuItem.setOnAction(e -> createStage(qupath));
		menu.getItems().add(menuItem);
	}

	private void createStage(QuPathGUI qupath) {
		if (stage == null) {
			try {
				stage = new Stage();
				var pane = SingalFinderController.createInstance(qupath);
				Scene scene = new Scene(new BorderPane(pane));
				pane.heightProperty().addListener((v, o, n) -> handleStageHeightChange());
				stage.setScene(scene);
				stage.initOwner(QuPathGUI.getInstance().getStage());
				stage.setTitle(resources.getString("title"));
				stage.setResizable(false);
				stage.setOnShown(e -> Watcher.getInstance().start());
				stage.setOnHidden(e -> Watcher.getInstance().stop());
			} catch (IOException e) {
				Dialogs.showErrorMessage("SignalFinder", "GUI loading failed");
				logger.error("Unable to load SignalFinder FXML", e);
			}
		}
		stage.show();
	}

	private void handleStageHeightChange() {
		stage.sizeToScene();
		// This fixes a bug where the stage would migrate to the corner of a screen if it is
		// resized, hidden, then shown again
		if (stage.isShowing() && Double.isFinite(stage.getX()) && Double.isFinite(stage.getY()))
			FXUtils.retainWindowPosition(stage);
	}
	@Override
	public String getName() {
		return EXTENSION_NAME;
	}

	@Override
	public String getDescription() {
		return EXTENSION_DESCRIPTION;
	}
	
	@Override
	public Version getQuPathVersion() {
		return EXTENSION_QUPATH_VERSION;
	}

	@Override
	public GitHubRepo getRepository() {
		return EXTENSION_REPOSITORY;
	}

}