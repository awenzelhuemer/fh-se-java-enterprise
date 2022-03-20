package swt6.dal.dao.impl;

import org.springframework.stereotype.Component;
import swt6.dal.dao.CategoryDao;
import swt6.dal.domain.Category;

@Component
public class CategoryDaoImpl extends BaseDaoImpl<Category> implements CategoryDao {
    @Override
    protected Class<Category> getEntityType() {
        return Category.class;
    }
}
