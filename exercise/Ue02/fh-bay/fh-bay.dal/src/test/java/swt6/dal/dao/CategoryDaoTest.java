package swt6.dal.dao;

import org.junit.jupiter.api.Test;
import swt6.dal.domain.Category;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CategoryDaoTest extends BaseTest {

    @Test
    public void insert_withValidData_insertsCategory() {

        var categoryDao = DaoFactory.getCategoryDao();

        Category category = new Category("TVs", new Category("Electronics", null));

        category = categoryDao.insert(category);

        var result = categoryDao.findById(category.getId());
        assertNotNull(result);
        assertNotNull(result.getParent());
    }

    @Test
    public void delete_withExistingCategory_deletesCategory() {
        var categoryDao = DaoFactory.getCategoryDao();

        Category category = new Category("TVs", new Category("Electronics", null));

        category = categoryDao.insert(category);

        categoryDao.delete(category.getId());

        // Parent should not be deleted
        assertNull(categoryDao.findById(category.getId()));
        assertNotNull(categoryDao.findById(category.getParent().getId()));
    }

    @Test
    public void delete_withNoExistingCategory_throwsException() {
        CategoryDao dao = DaoFactory.getCategoryDao();
        assertThrows(Exception.class, () -> dao.delete(1));
    }

    @Test
    public void findById_withNoExistingCategory_returnsNull() {
        CategoryDao dao = DaoFactory.getCategoryDao();
        assertNull(dao.findById(1));
    }

    @Test
    public void findById_withExistingCategory_returnsCategory() {
        var categoryDao = DaoFactory.getCategoryDao();

        Category category = new Category("TVs", new Category("Electronics", null));

        category = categoryDao.insert(category);

        assertNotNull(categoryDao.findById(category.getId()));
    }
}
