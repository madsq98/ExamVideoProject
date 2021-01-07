package main.gui.editCategory;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.be.Category;
import main.bll.CategoryManager;
import main.util.UserError;

public class EditCategoryController {
    public TextField txtCategory;
    public Button btnCancelEditCategory;
    private CategoryManager cMan;
    public Category selectedCategory;
    private String errorHeader = "Something went wrong!";

    public void handleCancel(ActionEvent actionEvent) {closeWin();    }

    public void handleSave(ActionEvent actionEvent) {
        if(!txtCategory.getText().isEmpty()) {
            selectedCategory.setName(txtCategory.getText());
        }
        else {
            UserError.showError(errorHeader,"Please provide a title for the category!");
            return;
        }

    }

    public void setManager(CategoryManager cMan) {
        this.cMan = cMan;
    }


    private void closeWin(){
        Stage stage = (Stage) btnCancelEditCategory.getScene().getWindow();
        stage.close();
    }

    public Category getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(Category selectedCategory) {
        this.selectedCategory = selectedCategory;
    }
}
