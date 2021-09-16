import React from "react";
import {Link} from "react-router-dom";

export default function LoginForm() {

    return (
        <form method={"post"} action={"/auth/login"}>
            <h3>Sign In</h3>

            <div className="form-group">
                <label>Username</label>
                <input type="text" id="username" name="username" className="form-control" placeholder="Enter username" required/>
            </div>

            <div className="form-group">
                <label>Password</label>
                <input type="password" id="password" name="password" className="form-control" placeholder="Enter password" required/>
            </div>

            <div className="form-group">
                <div className="custom-control custom-checkbox">
                    <input type="checkbox" className="custom-control-input" id="customCheck1" />
                    <label className="custom-control-label" htmlFor="customCheck1">Remember me</label>
                </div>
                <p className="forgot-password text-right">
                    Forgot <a href="#">password?</a>
                </p>
            </div>

            <button type="submit" className="btn btn-primary btn-block">Submit</button>
            <p className="forgot-password text-right">
                Don't have an account? <Link to={"/auth/register"} replace>Sign-up</Link>
            </p>
        </form>
    );
}

// import React from "react";
// import { MDBContainer, MDBRow, MDBCol, MDBCard, MDBCardBody, MDBInput, MDBBtn, MDBIcon, MDBModalFooter } from 'mdbreact';
//
// function LoginForm() {
//     return (
//         <MDBContainer>
//             <MDBRow>
//                 <MDBCol md="6">
//                     <MDBCard>
//                         <MDBCardBody className="mx-4">
//                             <div className="text-center">
//                                 <h3 className="dark-grey-text mb-5">
//                                     <strong>Sign in</strong>
//                                 </h3>
//                             </div>
//                             <MDBInput
//                                 label="Your email"
//                                 group
//                                 type="email"
//                                 validate
//                                 error="wrong"
//                                 success="right"
//                             />
//                             <MDBInput
//                                 label="Your password"
//                                 group
//                                 type="password"
//                                 validate
//                                 containerClass="mb-0"
//                             />
//                             <p className="font-small blue-text d-flex justify-content-end pb-3">
//                                 Forgot
//                                 <a href="#!" className="blue-text ml-1">
//
//                                     Password?
//                                 </a>
//                             </p>
//                             <div className="text-center mb-3">
//                                 <MDBBtn
//                                     type="button"
//                                     gradient="blue"
//                                     rounded
//                                     className="btn-block z-depth-1a"
//                                 >
//                                     Sign in
//                                 </MDBBtn>
//                             </div>
//                             <p className="font-small dark-grey-text text-right d-flex justify-content-center mb-3 pt-2">
//
//                                 or Sign in with:
//                             </p>
//                             <div className="row my-3 d-flex justify-content-center">
//                                 <MDBBtn
//                                     type="button"
//                                     color="white"
//                                     rounded
//                                     className="mr-md-3 z-depth-1a"
//                                 >
//                                     <MDBIcon fab icon="facebook-f" className="blue-text text-center" />
//                                 </MDBBtn>
//                                 <MDBBtn
//                                     type="button"
//                                     color="white"
//                                     rounded
//                                     className="mr-md-3 z-depth-1a"
//                                 >
//                                     <MDBIcon fab icon="twitter" className="blue-text" />
//                                 </MDBBtn>
//                                 <MDBBtn
//                                     type="button"
//                                     color="white"
//                                     rounded
//                                     className="z-depth-1a"
//                                 >
//                                     <MDBIcon fab icon="google-plus-g" className="blue-text" />
//                                 </MDBBtn>
//                             </div>
//                         </MDBCardBody>
//                         <MDBModalFooter className="mx-5 pt-3 mb-1">
//                             <p className="font-small grey-text d-flex justify-content-end">
//                                 Not a member?
//                                 <a href="#!" className="blue-text ml-1">
//
//                                     Sign Up
//                                 </a>
//                             </p>
//                         </MDBModalFooter>
//                     </MDBCard>
//                 </MDBCol>
//             </MDBRow>
//         </MDBContainer>
//     );
// }
//
// export default LoginForm;
