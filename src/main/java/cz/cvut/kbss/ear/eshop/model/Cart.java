package cz.cvut.kbss.ear.eshop.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;


@Entity
public class Cart extends AbstractEntity {

    @OneToOne
    private User owner;
    @OneToMany
    @JoinColumn
    private List<CartItem> items;
}
