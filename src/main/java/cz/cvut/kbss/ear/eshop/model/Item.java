package cz.cvut.kbss.ear.eshop.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public class Item extends AbstractEntity {
    @Basic(optional = false)
    @Column(nullable = false)
    private int amount;

    @ManyToOne
    private Product product;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
