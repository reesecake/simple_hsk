import React, {useEffect, useState} from 'react';
import {makeStyles} from "@material-ui/core/styles";
import {
    Button, ButtonBase,
    CircularProgress,
    Divider,
    FormControl,
    FormHelperText,
    Grid,
    Input,
    InputLabel
} from "@material-ui/core";
import {green} from "@material-ui/core/colors";
import clsx from "clsx";
import _ from 'underscore';
import Question from "./Question";
import QuizLevelSelector from "./QuizLevelSelector";
import QuizQuestionLimiter from "./QuizQuestionLimiter";

/**
 * Generates an array of objects sampled from API. Adds a new key called answers with an array of the correct meaning
 * and three random meanings as incorrect answers.
 * @param vocabs - an array of Vocab objects fetched from the API
 * @param meanings - an array of Strings derived from the meanings of Vocabs fetched from the API.
 * @param numQuestions - number of questions to display
 * @returns {*}
 */
function MakeQuestions(vocabs, meanings, numQuestions = 10) {

    let newQuestions = _.sample(vocabs, numQuestions);

    // console.log("sampled: ", newQuestions);

    newQuestions = newQuestions.map((ques) => {
        let answers = [ques.meaning];
        let wrongAnswers = _.sample(meanings, 3);
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
    const { vocabs, level, handleLevelChange, meanings } = props;
    const classes = useStyles();
    const [questions, setQuestions] = useState(() => { return MakeQuestions(vocabs, meanings) });
    const [score, setScore] = useState(0);
    const [numQuestions, setNumQuestions] = useState(10);
    // button with loading:
    const [loading, setLoading] = React.useState(false);
    const [success, setSuccess] = React.useState(false);
    const [needsReload, setNeedsReload] = React.useState(false);
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

    const handleButtonClick = () => {
        if (!loading) {
            setSuccess(false);
            setLoading(true);
            setQuestions(MakeQuestions(vocabs, meanings, numQuestions));  // refresh for new questions
            setScore(0);  // reset score counter
            setNeedsReload(false);
            timer.current = window.setTimeout(() => {
                setSuccess(true);
                setLoading(false);
            }, 2000);
        }
    };

    const handleSubmit = (event) => {
        event.preventDefault();

        let results = {};
        let tmpHelperTexts = {};
        let tmpScore = 0;

        for (const ques of questions) {
            results = {
                ...results,
                [ques.wordSimplified]: values[ques.wordSimplified] !== ques.meaning,
            };
            tmpHelperTexts = {
                ...tmpHelperTexts,
                [ques.wordSimplified]: values[ques.wordSimplified] !== ques.meaning ? 'Sorry, wrong answer' : 'Correct!',
            };

            if (values[ques.wordSimplified] === ques.meaning) tmpScore++;
        }

        setErrors(results);
        setHelperTexts(tmpHelperTexts);
        setScore(tmpScore);
    };

    return (
        <div className={classes.root}>
            <Grid container spacing={3}>
                <Grid container item direction={"row"} alignItems={"center"}>
                    <Grid item xs={4}>
                        <QuizLevelSelector level={level} updateQuizLevel={handleLevelChange} />
                    </Grid>

                    <Grid item xs={4}>
                        <QuizQuestionLimiter
                            numQuestions={numQuestions}
                            setNumQuestions={setNumQuestions}
                            needsReload={needsReload}
                            setNeedsReload={setNeedsReload}
                        />
                    </Grid>

                    <Grid container item xs={4} justifyContent={"flex-end"}>
                        <div className={classes.wrapper}>
                            <Button
                                variant={needsReload ? "contained" : "outlined"}
                                color="secondary"
                                // className={buttonClassname}
                                disabled={loading}
                                onClick={handleButtonClick}
                            >
                                Reload
                            </Button>
                            {loading && <CircularProgress size={24} className={classes.buttonProgress} />}
                        </div>
                    </Grid>
                </Grid>

                <Grid item xs={12}>
                    <div style={{"fontSize": "1.2rem"}}>Score: {score} / {numQuestions}</div>
                </Grid>

                <Grid container item xs={12} alignItems={"flex-start"}>
                    <form onSubmit={handleSubmit} className={classes.form}>
                        {questions.map((question, index) => (
                            <Grid container item xs={12} direction={"row"} justifyContent={"flex-start"} alignItems={"flex-start"}
                                  key={index}>
                                <div key={index} style={{"marginTop": "24px"}}>{index + 1}.</div>
                                <Question key={question._links.self.href}
                                          question={question}
                                          values={values}
                                          setValues={setValues}
                                          errors={errors}
                                          helperTexts={helperTexts} />
                            </Grid>
                        ))}

                        <Button type="submit" variant="outlined" color="primary" className="submitQuiz">
                            Check Answers
                        </Button>
                    </form>
                </Grid>
            </Grid>
        </div>
    );
}
