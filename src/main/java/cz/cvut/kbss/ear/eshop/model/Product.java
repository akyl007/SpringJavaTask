package cz.cvut.kbss.ear.eshop.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product extends AbstractEntity{
    @Basic(optional = false)
    @Column(nullable = false)
    private String name;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer amount;
    @Basic(optional = false)
    @Column(nullable = false)
    private Double price;
    @ManyToMany
    @JoinColumn(name = "CATEGORY_COLUMN")
    private List<Category> categories;

    public Product(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
