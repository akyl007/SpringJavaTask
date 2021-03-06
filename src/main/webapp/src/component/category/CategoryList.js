import React from 'react';
import PropTypes from 'prop-types';
import {loadCategories} from "../../action/AsyncActions";
import {connect} from "react-redux";
import {Card, CardBody, CardHeader, ListGroup, ListGroupItem} from "reactstrap";
import {selectCategory} from "../../action/SyncActions";

class CategoryList extends React.Component {

    static propTypes = {
        categories: PropTypes.array,
        loadCategories: PropTypes.func,
        selectCategory: PropTypes.func
    };

    componentDidMount() {
        this.props.loadCategories();
    }

    _selectCategory = (cat, e) => {
        this.props.selectCategory(cat);
        e.preventDefault();
        return false;
    };

    render() {
        return <Card>
            <CardHeader>
                <h5>Categories</h5>
            </CardHeader>
            <CardBody>
                <ListGroup>
                    {this._renderCategories()}
                </ListGroup>
            </CardBody>
        </Card>;
    }

    _renderCategories() {
        return this.props.categories.map(cat => <ListGroupItem key={cat.id} tag='a' href='#'
                                                               onClick={(e) => this._selectCategory(cat, e)}>{cat.name}</ListGroupItem>);
    }
}

export default connect(state => {
    return {
        categories: state.categories
    };
}, dispatch => {
    return {
        loadCategories: () => dispatch(loadCategories()),
        selectCategory: (category) => dispatch(selectCategory(category))
    };
})(CategoryList);