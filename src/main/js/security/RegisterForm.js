import React from "react";
import { Link } from "react-router-dom";

// TODO: make this work
export default function RegisterForm() {
    return (
        <form>
            <h3>Sign Up</h3>
            <h3>Does not work!!!</h3>  {/* TODO: remove this once working */}

            <div className="form-group">
                <label>First name</label>
                <input type="text" className="form-control" placeholder="First name" />
            </div>

            <div className="form-group">
                <label>Last name</label>
                <input type="text" className="form-control" placeholder="Last name" />
            </div>

            <div className="form-group">
                <label>Email address</label>
                <input type="email" className="form-control" placeholder="Enter email" />
            </div>

            <div className="form-group">
                <label>Password</label>
                <input type="password" className="form-control" placeholder="Enter password" />
            </div>

            <button type="submit" className="btn btn-primary btn-block">Sign Up</button>
            <p className="forgot-password text-right">
                Already registered? <Link to={"/login"} replace>Sign-in</Link>
            </p>
        </form>
    );
}
