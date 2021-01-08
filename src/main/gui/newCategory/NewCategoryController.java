package main.gui.newCategory;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.be.Category;
import main.be.Video;
import main.bll.CategoryManager;
import main.util.UserError;

import java.sql.SQLException;

public class NewCategoryController {

    public TextField txtCategory;
    public Button btnCancelNewCategory;
    private CategoryManager cMan;

    private String errorHeader = "Something went wrong";

    public void setManager(CategoryManager cMan) {
        this.cMan = cMan;
    }

    public void handleCancel(ActionEvent actionEvent) {
        closeWin();
    }

    public void handleSave(ActionEvent actionEvent) {
        if(!txtCategory.getText().isEmpty()) {
            Category newCategory = new Category(txtCategory.getText());
            try {
                cMan.add(newCategory);
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

    private void closeWin(){
        Stage stage = (Stage) btnCancelNewCategory.getScene().getWindow();
        stage.close();
    }
}
