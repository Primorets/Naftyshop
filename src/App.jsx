import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import ProductsPage from './pages/ProductsPage';

const App = () => {
    return (
        <Router>
            <Switch>
                <Route path="/" exact component={ProductsPage} />
            </Switch>
        </Router>
    );
};

export default App;