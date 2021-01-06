package main.gui.editCategory;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import main.bll.CategoryManager;

public class EditCategoryController {
    public TextField txtCategory;
    private CategoryManager cMan;

    public void handleCancel(ActionEvent actionEvent) {
    }

    public void handleSave(ActionEvent actionEvent) {
    }

    public void setManager(CategoryManager cMan) {
        this.cMan = cMan;
    }
}
