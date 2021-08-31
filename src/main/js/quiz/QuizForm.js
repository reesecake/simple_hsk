import React, {useEffect, useState} from 'react';
import {makeStyles} from "@material-ui/core/styles";
import {Button, CircularProgress} from "@material-ui/core";
import {green} from "@material-ui/core/colors";
import clsx from "clsx";
import _ from 'underscore';
import Question from "./Question";

/**
 * Generates an array of objects sampled from API. Adds a new key called answers with an array of the correct meaning
 * and three random meanings as incorrect answers.
 * @param vocabs - an array of Vocab objects fetched from the API
 * @param meanings - an array of Strings derived from the meanings of Vocabs fetched from the API.
 * @returns {*}
 */
function MakeQuestions(vocabs, meanings) {

    let newQuestions = _.sample(vocabs, 10);

    // console.log("sampled: ", newQuestions);

    let wrongAnswers = _.sample(meanings, 3);

    newQuestions = newQuestions.map((ques) => {
        let answers = [ques.meaning];
        answers.push(...wrongAnswers);
        return {
            ...ques,
            answers: _.shuffle(answers),
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

    // console.log("setting values: ", values);

    return values;
}

function MakeErrors(questions) {
    let errors = {}
    questions.forEach(question => {
        errors[question.wordSimplified] = false;
    });

    return errors;
}

function MakeHelperTexts(questions) {
    let helperTexts = {}
    questions.forEach(question => {
        helperTexts[question.wordSimplified] = 'Choose wisely';
    });

    return helperTexts;
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
    const { vocabs, level , meanings } = props;
    const classes = useStyles();
    const [questions, setQuestions] = useState(() => { return MakeQuestions(vocabs, meanings) });
    // button with loading:
    const [loading, setLoading] = React.useState(false);
    const [success, setSuccess] = React.useState(false);
    const timer = React.useRef();
    // radio:
    const [values, setValues] = React.useState(() => { return MakeValues(questions) });
    const [errors, setErrors] = React.useState(() => { return MakeErrors(questions) });
    const [helperTexts, setHelperTexts] = React.useState(() => { return MakeHelperTexts(questions) });

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
        setHelperTexts(MakeHelperTexts(questions));
        return () => {
            clearTimeout(timer.current);
        };
    }, [questions]);

    // useEffect(() => {
    //     console.log("errors: ", errors);
    // }, [errors]);
    //
    // useEffect(() => {
    //     console.log("helperTexts: ", helperTexts);
    // }, [helperTexts]);

    const handleButtonClick = () => {
        if (!loading) {
            setSuccess(false);
            setLoading(true);
            setQuestions(MakeQuestions(vocabs, meanings));  // refresh for new questions
            timer.current = window.setTimeout(() => {
                setSuccess(true);
                setLoading(false);
            }, 2000);
        }
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        // console.log("values:", values);
        console.log("questions: ", questions);

        let results = {};
        let tmpHelperTexts = {};

        for (const ques of questions) {
            results = {
                ...results,
                [ques.wordSimplified]: values[ques.wordSimplified] !== ques.meaning,
            };
            tmpHelperTexts = {
                ...tmpHelperTexts,
                [ques.wordSimplified]: values[ques.wordSimplified] !== ques.meaning ? 'Sorry, wrong answer' : 'Correct!',
            };
        }

        setErrors(results);
        setHelperTexts(tmpHelperTexts);
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
                    <Question key={question._links.self.href}
                              question={question}
                              values={values}
                              setValues={setValues}
                              errors={errors}
                              helperTexts={helperTexts} />
                ))}

                <Button type="submit" variant="outlined" color="primary" className="submitQuiz">
                    Check Answers
                </Button>
            </form>
        </div>
    );
}
