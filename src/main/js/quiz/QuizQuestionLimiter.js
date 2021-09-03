import {FormControl, FormHelperText, Input, InputLabel} from "@material-ui/core";
import React, {useEffect, useState} from "react";
import {makeStyles} from "@material-ui/core/styles";

const useStyles = makeStyles((theme) => ({
    limitForm: {
        height: "70px",  // 'allocate' space for helperText to not shift other elements up on render
    },
}));

export default function QuizQuestionLimiter(props) {
    const { numQuestions, setNumQuestions, needsReload, setNeedsReload } = props;
    const classes = useStyles();

    const [newQuestionLimit, setNewQuestionLimit] = useState(numQuestions.valueOf());

    useEffect(() => {
        // only set newQuestionLimit when quiz is reloaded and changes are 'applied'
        if (!needsReload) {
            setNewQuestionLimit(numQuestions);
        }
    }, [needsReload])

    const handleNewQuestionLimitChange = (event) => {
        setNumQuestions(event.target.value);
        setNeedsReload(Number.parseInt(event.target.value) !== Number(newQuestionLimit));
    };

    return (
        <form className={classes.limitForm}>
            <FormControl>
                <InputLabel htmlFor="question-input">Question limit</InputLabel>
                <Input id="question-input"
                       autoComplete={"off"}
                       value={numQuestions}
                       onChange={handleNewQuestionLimitChange}
                />
                {needsReload && <FormHelperText id="question-input-helper-text">Reload for changes to take effect</FormHelperText>}
            </FormControl>
        </form>
    );
}
