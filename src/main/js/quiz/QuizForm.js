import React, {useEffect, useState} from 'react';
import {makeStyles} from "@material-ui/core/styles";
import {Button, CircularProgress} from "@material-ui/core";
import {green} from "@material-ui/core/colors";
import clsx from "clsx";
import _ from 'underscore';
import Question from "./Question";

function MakeQuestions(vocabs) {

    let newQuestions = _.sample(vocabs, 10);

    // console.log("sampled: ", newQuestions);

    newQuestions = newQuestions.map((ques) => {
        return {
            ...ques,
            answers: [ques.meaning, "wrong1", "wrong2", "wrong3"]
        };
    });

    // console.log("edited: ", newQuestions);
    return newQuestions;
}

function MakeValues(questions) {
    const values = {}
    questions.forEach(question => {
        values[question.wordSimplified] = '';
    });

    console.log("setting values: ", values);

    return values;
}

function MakeErrors(questions) {
    const errors = {}
    questions.forEach(question => {
        errors[question.wordSimplified] = false;
    });

    return errors;
}

const useStyles = makeStyles({
    table: {
        minWidth: 650,
    },

    root: {
        display: 'flex',
        flexDirection: 'column',
    },
    wrapper: {
        position: 'relative',
    },
    form: {
        // direction: 'column',
    },
    buttonSuccess: {
        backgroundColor: green[500],
        '&:hover': {
            backgroundColor: green[700],
        },
    },
    fabProgress: {
        color: green[500],
        position: 'absolute',
        top: -6,
        left: -6,
        zIndex: 1,
    },
    buttonProgress: {
        color: green[500],
        position: 'absolute',
        top: '50%',
        left: '50%',
        marginTop: -12,
        marginLeft: -12,
    },
});

export default function QuizForm(props) {
    const { vocabs, level } = props;
    const classes = useStyles();
    const [questions, setQuestions] = useState(() => { return MakeQuestions(vocabs) });
    // button with loading:
    const [loading, setLoading] = React.useState(false);
    const [success, setSuccess] = React.useState(false);
    const timer = React.useRef();
    // radio:
    const [values, setValues] = React.useState(() => { return MakeValues(questions) });
    const [errors, setErrors] = React.useState(() => { return MakeErrors(questions) });
    const [helperText, setHelperText] = React.useState('Choose wisely');

    const buttonClassname = clsx({
        [classes.buttonSuccess]: success,
    });

    useEffect(() => {
        return () => {
            clearTimeout(timer.current);
        };
    }, []);

    useEffect(() => {
        setSuccess(true);
        setLoading(false);
        setValues(MakeValues(questions));
        setErrors(MakeErrors(questions));
        return () => {
            clearTimeout(timer.current);
        };
    }, [questions]);

    useEffect(() => {
        console.log("errors: ", errors);
    }, [errors]);

    const handleButtonClick = () => {
        if (!loading) {
            setSuccess(false);
            setLoading(true);
            setQuestions(MakeQuestions(vocabs));  // refresh for new questions
            timer.current = window.setTimeout(() => {
                setSuccess(true);
                setLoading(false);
            }, 2000);
        }
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        console.log("values:", values);
        // console.log("questions: ", questions);

        let results = {};

        for (const ques of questions) {
            results = {
                ...results,
                [ques.wordSimplified]: values[ques.wordSimplified] !== ques.meaning,
            };
        }

        setErrors(results);
    };

    return (
        <div className={classes.root}>
            <div className={classes.wrapper}>
                <Button
                    variant="contained"
                    color="primary"
                    className={buttonClassname}
                    disabled={loading}
                    onClick={handleButtonClick}
                >
                    Reload
                </Button>
                {loading && <CircularProgress size={24} className={classes.buttonProgress} />}
            </div>

            <form onSubmit={handleSubmit} className={classes.form}>
                {questions.map((question) => (
                    <Question question={question} values={values} setValues={setValues} errors={errors} />
                ))}

                <Button type="submit" variant="outlined" color="primary" className="submitQuiz">
                    Check Answers
                </Button>
            </form>
        </div>
    );
}
