import React, {useEffect} from "react";
import {Card, CardContent, Typography} from "@material-ui/core";
import * as ReactDOM from "react-dom";

function WelcomeSplash(props) {
    const { loggedInUser } = props;

    useEffect(() => {
        console.log(loggedInUser.valueOf());
    });

    return(
        <Card>
            <CardContent>
                <Typography>
                    {loggedInUser}
                </Typography>
            </CardContent>
        </Card>
    );
}

let container = document.getElementById("react-welcome");
ReactDOM.render(
    <WelcomeSplash
        loggedInUser={container.getAttribute("authentication")}
    />,
    container
)
