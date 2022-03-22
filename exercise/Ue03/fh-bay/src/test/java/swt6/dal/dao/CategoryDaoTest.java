package swt6.dal.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import swt6.dal.domain.Category;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Rollback
public class CategoryDaoTest {

    @Autowired
    private CategoryDao categoryDao;

    @Test
    public void insert_withValidData_insertsCategory() {

        Category category = new Category("TVs", new Category("Electronics", null));

        category = categoryDao.save(category);

        var result = categoryDao.findById(category.getId());
        assertNotNull(result);
        assertNotNull(result.map(Category::getParent).orElse(null));
    }

    @Test
    public void delete_withExistingCategory_deletesCategory() {
        Category category = new Category("TVs", new Category("Electronics", null));

        category = categoryDao.save(category);

        categoryDao.delete(category);

        // Parent should not be deleted
        assertFalse(categoryDao.findById(category.getId()).isPresent());
    }

    @Test
    public void findById_withNoExistingCategory_returnsNull() {
        assertNull(categoryDao.findById(1L).orElse(null));
    }

    @Test
    public void findById_withExistingCategory_returnsCategory() {
        Category category = new Category("TVs", new Category("Electronics", null));

        category = categoryDao.save(category);

        assertNotNull(categoryDao.findById(category.getId()));
    }
}
