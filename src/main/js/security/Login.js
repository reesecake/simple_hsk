import React, {useEffect} from 'react';
// import '../../../../node_modules/bootstrap/dist/css/bootstrap.min.css';
import {BrowserRouter as Router, Switch, Route, Link, BrowserRouter} from "react-router-dom";
import RegisterForm from "./RegisterForm";
import LoginForm from "./LoginForm";
import * as ReactDOM from "react-dom";

function Login() {

    return (
        <Router basename="/auth">
            <div className="App">
                <div className="auth-wrapper">
                    <div className="auth-inner">
                        <Switch>
                            <Route exact path="/login">
                                <LoginForm />
                            </Route>
                            <Route path="/register">
                                <RegisterForm />
                            </Route>
                        </Switch>
                    </div>
                </div>
            </div>
        </Router>
    );
}

ReactDOM.render(
    <BrowserRouter>
        <Login />
    </BrowserRouter>,
    document.getElementById("react-login")
);
