package swt6.dal.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category extends BaseEntity {

    private String name;

    @JoinTable(name = "Category_Relations", joinColumns = @JoinColumn(name = "Parent_Id"), inverseJoinColumns = @JoinColumn(name = "Child_Id"))
    @OneToMany(cascade = CascadeType.MERGE)
    private Set<Category> subCategories = new HashSet<>();

    @ManyToMany
    private Set<Article> articles = new HashSet<>();

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(Set<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public void addSubCategory(Category category) {
        this.subCategories.add(category);
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return getName();
    }
}
