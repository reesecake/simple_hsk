import React, { useState, useEffect } from 'react';
import QuizForm from "./quiz/QuizForm";
import {LinearProgress} from "@material-ui/core";

const ReactDOM = require('react-dom');

const root = '/api'

function HSKQuiz(props) {
    const [level, setLevel] = useState(props.level);
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [items, setItems] = useState([]);
    const [meanings, setMeanings] = useState([]);

    useEffect(() => {
        fetch('/api/vocabs/search/findVocabsByLevel?level=HSK' + level, {
            method: 'GET',
            headers: {
                'Accept': 'application/hal+json'
            }
        })
            .then(res => res.json())
            .then(
                (result) => {
                    setItems(result._embedded.vocabs);
                    let tmpMeanings = [];
                    for (let vocab of result._embedded.vocabs) {
                        tmpMeanings.push(
                            // vocab.meaning
                            {"meaning": vocab.meaning, "pinyin": vocab.pinyin}
                        );
                    }
                    setMeanings(tmpMeanings);
                    setIsLoaded(true);
                },
                (error) => {
                    setError(error);
                    setIsLoaded(true);
                }
            )
    }, [level]);

    // useEffect(() => {
    //     console.log("meanings: ", meanings);
    // }, [meanings]);

    const handleLevelChange = (event) => {
        setLevel(event.target.value);
        setIsLoaded(false);
    };

    if (error) {
        return <div>Error: {error.message}</div>;
    } else if (!isLoaded) {
        return <LinearProgress color="secondary" />;
    } else {
        return (
            <QuizForm vocabs={items}
                      level={level}
                      handleLevelChange={handleLevelChange}
                      meanings={meanings}
            />
        );
    }
}

// https://stackoverflow.com/questions/21591512/pass-props-from-template-into-react-js-root-node
let container = document.getElementById('react-hsk-quiz');
ReactDOM.render(
    <HSKQuiz level={container.getAttribute('level')}/>,
    container
);
