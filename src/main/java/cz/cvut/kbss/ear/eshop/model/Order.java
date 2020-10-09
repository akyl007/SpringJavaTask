package cz.cvut.kbss.ear.eshop.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Order extends AbstractEntity {
    @Basic(optional = false)
    @Column(nullable = false)
    private LocalDateTime created;

    @ManyToOne
    private User customer;

    @OneToMany
    @JoinColumn(name = "ORDER_ITEM")
    private List<OrderItem> items;

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
