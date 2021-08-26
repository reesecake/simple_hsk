import React, { useState, useEffect} from "react";
import {FormControl, FormControlLabel, FormHelperText, FormLabel, Radio, RadioGroup} from "@material-ui/core";
import {makeStyles} from "@material-ui/core/styles";

const useStyles = makeStyles((theme) => ({
    formControl: {
        margin: theme.spacing(3),
    },
    button: {
        margin: theme.spacing(1, 1, 0, 0),
    },
}));

export default function Question(props) {
    const { question, values, setValues, errors } = props;
    const classes = useStyles();

    const [helperText, setHelperText] = React.useState('Choose wisely');

    const handleRadioChange = (event) => {
        setValues({
            ...values,
            [question.wordSimplified]: event.target.value,
        });
    };

    return (
        <FormControl component="fieldset" error={errors[question.wordSimplified]} className={classes.formControl}>
            <FormLabel component="legend">{question.wordSimplified}</FormLabel>
            <RadioGroup aria-label="quiz" name="quiz" value={values[question.wordSimplified]} onChange={handleRadioChange}>
                {question.answers.map((option) => (
                    <FormControlLabel value={option} control={<Radio />} label={option} />
                ))}
            </RadioGroup>
            <FormHelperText>{helperText}</FormHelperText>
        </FormControl>
    );
}
