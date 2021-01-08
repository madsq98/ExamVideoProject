package main.gui.editCategory;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.be.Category;
import main.bll.CategoryManager;
import main.util.UserError;

import java.sql.SQLException;

public class EditCategoryController {
    public TextField txtCategory;
    public Button btnCancelEditCategory;
    private CategoryManager cMan;
    public Category selectedCategory;
    private String errorHeader = "Something went wrong!";

    public void handleCancel(ActionEvent actionEvent) {
        closeWin();
    }

    public void handleSave(ActionEvent actionEvent) {
        if(!txtCategory.getText().isEmpty()) {
            Category newCategory = new Category(txtCategory.getText());
            newCategory.setId(selectedCategory.getId());
            selectedCategory.setName(txtCategory.getText());
            try {
                cMan.replace(selectedCategory, newCategory);
                closeWin();
            } catch(SQLException e) {
                UserError.showError(errorHeader,e.getMessage());
            }
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

    public void setSelectedCategory(Category selectedCategory) {
        this.selectedCategory = selectedCategory;
        txtCategory.setText(selectedCategory.getName());
    }
}
