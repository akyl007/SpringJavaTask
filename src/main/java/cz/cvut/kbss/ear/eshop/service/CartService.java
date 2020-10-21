package cz.cvut.kbss.ear.eshop.service;

import cz.cvut.kbss.ear.eshop.model.Cart;
import cz.cvut.kbss.ear.eshop.model.CartItem;
import cz.cvut.kbss.ear.eshop.model.Item;
import org.springframework.transaction.annotation.Transactional;

public class CartService {

    public CartService() {
    }

    /**
     * Adds the specified item into the specified cart.
     * <p>
     * Note that the item probably does not exist in the database, but thanks to the model cascading strategy, it will
     * be persisted automatically.
     * <p>
     * If an item with the same product already exists in the cart, its amount is updated and no new instance is
     * persisted.
     *
     * @param cart  Target cart
     * @param toAdd Item to add
     */
    public void addItem(Cart cart, CartItem toAdd) {
    }

    /**
     * Removes the specified item from the cart.
     * <p>
     * If the amount to remove is less than the amount of the existing item, it is just updated and no instance is
     * removed from the actual cart.
     *
     * @param cart     Target cart
     * @param toRemove Item to remove
     */
    @Transactional
    public void removeItem(Cart cart, Item toRemove) {
    }
}
