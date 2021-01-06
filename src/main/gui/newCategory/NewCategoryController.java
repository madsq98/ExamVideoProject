package main.gui.newCategory;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import main.bll.CategoryManager;

public class NewCategoryController {

    public TextField txtCategory;
    private CategoryManager cMan;

    public void setManager(CategoryManager cMan) {
        this.cMan = cMan;
    }

    public void handleCancel(ActionEvent actionEvent) {
    }

    public void handleSave(ActionEvent actionEvent) {
    }
}
