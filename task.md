# Task

**Maximum**: 1 point

### Issue 006
When I attempt to add a product to a category for the first time, the application throws a `NullPointerException`. However, if the product already belongs to some category, no such issue occurs.

### Issue 007
When I create an order, all the items remain in the cart. As far as I am concerned, creating an order should move the items from cart to the new order and the cart should be empty.

### Notes
* Write suitable tests reproducing the issues (tests should fail initially). For issue **006**, the tests should cover both cases (i.e., when the product does not belong to any category and when it already belongs to some category), as the behavior needs to be consistent.
* Fix the issues
* Verify that the tests pass
* Make sure to find a suitable spot where to tests the behavior. Also look for existing code generating tests data

# Acceptance Criteria
* Tests reproducing the original issues exist (you will need at least three tests).
* The issues are fixed and the tests pass.
