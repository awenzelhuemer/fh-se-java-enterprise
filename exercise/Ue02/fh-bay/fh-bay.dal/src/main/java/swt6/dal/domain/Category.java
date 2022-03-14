package swt6.dal.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category extends BaseEntity {

    private String name;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Category parent;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "parent")
    private Set<Category> subCategories = new HashSet<>();

    @ManyToMany
    private Set<Article> articles = new HashSet<>();

    public Category() {
    }

    public Category(String name, Category parent) {
        this.name = name;
        this.parent = parent;
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

    public void addSubCategory(Category subCategory) {
        if(subCategory.getParent() != null) {
            subCategory.getParent().getSubCategories().remove(subCategory);
        }

        this.subCategories.add(subCategory);
        subCategory.setParent(this);
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public void attachParent(Category parent) {
        if(this.parent != null) {
            this.parent.getSubCategories().remove(this);
        }
        this.parent = parent;
        this.parent.getSubCategories().add(this);
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        if(getParent() != null) {
            return getParent() + " - " + getName();
        } else {
            return getName();
        }
    }
}
