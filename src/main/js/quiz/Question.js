import React, { useState, useEffect} from "react";
import {FormControl, FormControlLabel, FormHelperText, FormLabel, Radio, RadioGroup, rgbToHex} from "@material-ui/core";
import {makeStyles} from "@material-ui/core/styles";
import CheckIcon from '@material-ui/icons/Check';

const useStyles = value => makeStyles((theme) => ({
    formControl: {
        margin: theme.spacing(3),
        "& .Mui-error": {
            color: acquireHelperTextColor(value)
        },
        "& .MuiFormHelperText-root": {
            color: acquireHelperTextColor(value)
        }
    },
    formLabel: {
        fontSize: '1.2rem',
        color: acquireFormLabelColor(value),
    },
    button: {
        margin: theme.spacing(1, 1, 0, 0),
    },
}));

const acquireFormLabelColor = message => {
    switch (message) {
        case "Correct!":
            return "green";
        case "Sorry, wrong answer":
            return "#f44336";
        default:
            return "rgba(0, 0, 0, 0.87)";
    }
};

const acquireHelperTextColor = message => {
    switch (message) {
        case "Correct!":
            return "green";
        case "Sorry, wrong answer":
            return "#f44336";
        default:
            return "rgba(0, 0, 0, 0.54)";
    }
};

const questionTypes = {
    meaning: 'wordSimplified',
    wordSimplified: 'meaning',
    wordTraditional: 'meaning',
};

export default function Question(props) {
    const { question, answerType, values, setValues, errors, helperTexts, needsReload} = props;
    const classes = useStyles(helperTexts[question.id])();

    const [questionType, setQuestionType] = useState(questionTypes[answerType]);

    useEffect(() => {
        if (!needsReload) {
            setQuestionType(questionTypes[answerType]);
        }
    }, [answerType, needsReload]);

    const handleAnswerRadioChange = (event) => {
        setValues({
            ...values,
            [question.id]: event.target.value,
        });
    };

    return (
        <FormControl component="fieldset" error={errors[question.id]} className={classes.formControl}>
            <FormLabel component="legend" className={classes.formLabel}>
                {question[questionType]}
                {helperTexts[question.id] === "Correct!" && <CheckIcon style={{"marginLeft": "24px", "fontSize": "1.2rem"}}/>}
            </FormLabel>
            <RadioGroup aria-label="quiz" name="quiz" value={values[question.id]} onChange={handleAnswerRadioChange}>
                {question.answers.map((option) => (
                    <FormControlLabel key={question[questionType] + ": " + option}
                                      value={option}
                                      control={<Radio />}
                                      label={option}
                    />
                ))}
            </RadioGroup>
            <FormHelperText>{helperTexts[question.id]}</FormHelperText>
        </FormControl>
    );
}
