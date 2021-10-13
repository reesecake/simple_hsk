import React, {useEffect, useState} from "react";
import { Link } from "react-router-dom";

const Regex = RegExp(/^\s?[A-Z0–9]+[A-Z0–9._+-]{0,}@[A-Z0–9._+-]+\.[A-Z0–9]{2,4}\s?$/i);

const initState = {
    username : '',
    email : '',
    password : '',
}

export default function RegisterForm() {
    const [userDetails, setUserDetails] = useState(initState);

    useEffect(() => {
        console.log(userDetails);
    }, [userDetails]);

    const handleChange = (event) => {
        event.preventDefault();
        setUserDetails({
            ...userDetails,
            [event.target.name]: event.target.value,
        });
    }

    const handleSubmit = (event) => {
        alert('Something was submitted');
        event.preventDefault();
        console.log("Submitted: ", JSON.stringify(userDetails));

        fetch(
            '/auth/register/process', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(userDetails)
            })
            .then(res => {
                console.log(res.json())
            })
            .catch(error => {
                console.log(error.json());
            });
    }

    return (
        <form onSubmit={handleSubmit}>
            <h3>Sign Up</h3>

            <div className="form-group">
                <label>Username</label>
                <input type="text" name="username" className="form-control" placeholder="Enter username" onChange={handleChange}/>
            </div>

            <div className="form-group">
                <label>Email address</label>
                <input type="email" name="email" className="form-control" placeholder="Enter email" onChange={handleChange}/>
            </div>

            <div className="form-group">
                <label>Password</label>
                <input type="password" name="password" className="form-control" placeholder="Enter password" onChange={handleChange}/>
            </div>

            <div className="form-group">
                <label>Re-enter Password</label>
                <input type="password" className="form-control" placeholder="" />
            </div>

            {/*<input type="submit" value="Submit" />*/}
            <button className="btn btn-primary btn-block">Sign Up</button>

            <p className="forgot-password text-right">
                Already registered? <Link to={"/login"} replace>Sign-in</Link>
            </p>
        </form>
    );
}
