package main.gui.editCategory;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.be.Category;
import main.bll.CategoryManager;

public class EditCategoryController {
    public TextField txtCategory;
    public Button btnCancelEditCategory;

    private CategoryManager cMan;

    public void handleCancel(ActionEvent actionEvent) {
        closeWin();
    }

    public void handleSave(ActionEvent actionEvent) {
    }

    public void setManager(CategoryManager cMan) {this.cMan = cMan;    }

    private void closeWin(){
        Stage stage = (Stage) btnCancelEditCategory.getScene().getWindow();
        stage.close();
    }
}
