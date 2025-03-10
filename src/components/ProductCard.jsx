import React from 'react';

const ProductCard = ({ product }) => {
    return (
        <div className="card">
            <div className="card-header">
                <h3>{product.name}</h3>
            </div>
            <div className="card-body">
                <p>Category: {product.category}</p>
                <p>Price: ${product.price}</p>
            </div>
        </div>
    );
};

export default ProductCard;