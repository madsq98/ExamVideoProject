package main.gui.newCategory;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.bll.CategoryManager;

public class NewCategoryController {

    public TextField txtCategory;
    public Button btnCancelNewCategory;
    private CategoryManager cMan;

    public void setManager(CategoryManager cMan) {
        this.cMan = cMan;
    }

    public void handleCancel(ActionEvent actionEvent) {
        closeWin();
    }

    public void handleSave(ActionEvent actionEvent) {
    }

    private void closeWin(){
        Stage stage = (Stage) btnCancelNewCategory.getScene().getWindow();
        stage.close();
    }
}
