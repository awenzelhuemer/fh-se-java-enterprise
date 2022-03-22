package swt6.dal.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swt6.dal.domain.Category;

@Repository
public interface CategoryDao extends JpaRepository<Category, Long> {
}
