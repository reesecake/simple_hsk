import React, { useState, useEffect } from 'react';
import QuizForm from "./quiz/QuizForm";
import {LinearProgress} from "@material-ui/core";

const ReactDOM = require('react-dom');

const root = '/api'

function HSKQuiz(props) {
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [items, setItems] = useState([]);
    const [page, setPage] = useState({});

    useEffect(() => {
        fetch('/api/vocabs/search/findVocabByLevelIsLessThanEqual?size=5000&level=HSK' + props.level, {
            method: 'GET',
            headers: {
                'Accept': 'application/hal+json'
            }
        })
            .then(res => res.json())
            .then(
                (result) => {
                    setItems(result._embedded.vocabs);
                    setPage(result.page);
                    setIsLoaded(true);
                },
                (error) => {
                    setError(error);
                    setIsLoaded(true);
                }
            )
    }, [])

    if (error) {
        return <div>Error: {error.message}</div>;
    } else if (!isLoaded) {
        return <LinearProgress color="secondary" />;
    } else {
        return (
            <QuizForm vocabs={items} level={props.level}/>
        );
    }
}

// https://stackoverflow.com/questions/21591512/pass-props-from-template-into-react-js-root-node
let container = document.getElementById('react-hsk-quiz');
ReactDOM.render(
    <HSKQuiz level={container.getAttribute('level')}/>,
    container
);
