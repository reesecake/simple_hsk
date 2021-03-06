import React, {useEffect, useState} from 'react';
import {makeStyles} from "@material-ui/core/styles";
import {
    Button, CircularProgress,
    Grid
} from "@material-ui/core";
import {green} from "@material-ui/core/colors";
import clsx from "clsx";
import _ from 'underscore';
import Question from "./Question";
import QuizLevelSelector from "./QuizLevelSelector";
import QuizQuestionLimiter from "./QuizQuestionLimiter";
import QuizOptions from "./QuizOptions";

/**
 * Generates an array of objects sampled from API. Adds a new key called answers with an array of the correct meaning
 * and three random meanings as incorrect answers.
 * @param vocabs - an array of Vocab objects fetched from the API
 * @param numQuestions - number of questions to display
 * @param includePinyin - bool to prepend pinyin to the meaning in an answer
 * @param answerType - String of the question attribute to use as the answers
 * @returns {*}
 */
function MakeQuestions(vocabs, includePinyin, answerType, numQuestions = 10) {

    let newQuestions = _.sample(vocabs, numQuestions);

    // console.log("sampled: ", newQuestions);

    newQuestions = newQuestions.map((ques) => {
        let answers;
        if (answerType === "meaning") {
            answers = includePinyin ? [ques.pinyin + " - " + ques.meaning] : [ques.meaning];
        } else {
            answers = [ques[answerType]];
        }
        let sampleMeanings = _.sample(vocabs.filter(vocab => vocab.id !== ques.id), 3);
        for (const sampleMeaning of sampleMeanings) {
            if (answerType === "meaning") {
                answers.push(includePinyin
                    ? String(sampleMeaning.pinyin + " - " + sampleMeaning.meaning)
                    : sampleMeaning.meaning);
            } else {
                answers.push(sampleMeaning[answerType]);
            }
        }
        return {
            ...ques,
            meaning: includePinyin ? ques.pinyin + " - " + ques.meaning : ques.meaning,
            answers: _.shuffle(answers),
        };
    });

    // console.log("edited: ", newQuestions);
    return newQuestions;
}

function MakeValues(questions) {
    const values = {}
    questions.forEach(question => {
        values[question.id] = '';
    });

    return values;
}

function MakeErrors(questions) {
    let errors = {}
    questions.forEach(question => {
        errors[question.id] = false;
    });

    return errors;
}

function MakeHelperTexts(questions) {
    let helperTexts = {}
    questions.forEach(question => {
        helperTexts[question.id] = 'Choose wisely';
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
    const { vocabs, level, handleLevelChange, isCumulative, setCumulative } = props;
    const classes = useStyles();

    // options:
    const [includePinyin, setIncludePinyin] = useState(true);
    const [answerType, setAnswerType] = useState("meaning");
    // global quiz variables:
    const [questions, setQuestions] = useState(() => { return MakeQuestions(vocabs, includePinyin, answerType) });
    const [score, setScore] = useState(0);
    const [numQuestions, setNumQuestions] = useState(10);
    // button with loading:
    const [loading, setLoading] = useState(false);
    const [success, setSuccess] = useState(false);
    const [needsReload, setNeedsReload] = useState(false);
    const timer = React.useRef();
    // radio:
    const [values, setValues] = useState(() => { return MakeValues(questions) });
    const [errors, setErrors] = useState(() => { return MakeErrors(questions) });
    const [helperTexts, setHelperTexts] = useState(() => { return MakeHelperTexts(questions) });
    const [isSubmitted, setIsSubmitted] = useState(false);
    clsx({
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

    const handleReloadButtonClick = () => {
        if (!loading) {
            setSuccess(false);
            setLoading(true);
            setQuestions(MakeQuestions(vocabs, includePinyin, answerType, numQuestions));  // refresh for new questions
            setScore(0);  // reset score counter
            setIsSubmitted(false);
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
            let correctAnswer = ques[answerType];
            results = {
                ...results,
                [ques.id]: values[ques.id] !== correctAnswer,
            };
            tmpHelperTexts = {
                ...tmpHelperTexts,
                [ques.id]: values[ques.id] !== correctAnswer ? 'Sorry, wrong answer' : 'Correct!',
            };

            if (values[ques.id] === correctAnswer) tmpScore++;
        }

        setErrors(results);
        setHelperTexts(tmpHelperTexts);
        setScore(tmpScore);
        setIsSubmitted(true);
    };

    return (
        <div className={classes.root}>
            <Grid container spacing={3}>
                <Grid container item xs={6} direction={"row"}>
                    <Grid item>
                        <QuizLevelSelector level={level} updateQuizLevel={handleLevelChange} />
                    </Grid>
                </Grid>
                <Grid container item
                      xs={6}
                      direction={"row"}
                      spacing={2}
                      justifyContent={"flex-end"}
                      alignItems={"center"}>
                    <Grid item>
                        <QuizQuestionLimiter
                            vocabs={vocabs}
                            numQuestions={numQuestions}
                            setNumQuestions={setNumQuestions}
                            needsReload={needsReload}
                            setNeedsReload={setNeedsReload}
                        />
                    </Grid>

                    <Grid item>
                        <QuizOptions
                            setNeedsReload={setNeedsReload}
                            isCumulative={isCumulative}
                            setCumulative={setCumulative}
                            includePinyin={includePinyin}
                            setIncludePinyin={setIncludePinyin}
                            answerType={answerType}
                            setAnswerType={setAnswerType}
                        />
                    </Grid>

                    <Grid item>
                        <div className={classes.wrapper}>
                            <Button
                                variant={needsReload ? "contained" : "outlined"}
                                color="secondary"
                                // className={buttonClassname}
                                disabled={loading}
                                onClick={handleReloadButtonClick}
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
                                <div key={`${index}:${question.wordSimplified}`} style={{"marginTop": "24px"}}>{index + 1}.</div>
                                <Question key={`${index}:${question.wordSimplified}:${question.id}`}
                                          question={question}
                                          answerType={answerType}
                                          values={values}
                                          setValues={setValues}
                                          errors={errors}
                                          helperTexts={helperTexts}
                                          needsReload={needsReload}
                                          isSubmitted={isSubmitted}
                                />
                            </Grid>
                        ))}

                        <Button type="submit" variant="outlined" color="primary" className="submitQuiz" disabled={isSubmitted}>
                            Check Answers
                        </Button>
                    </form>
                </Grid>
            </Grid>
        </div>
    );
}
