import React, { useState, useEffect } from 'react';
import CustomPaginationActionsTable from "./hsk_table";

const ReactDOM = require('react-dom');

const root = '/api'

function HSK(props) {
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
        return <div>Loading...</div>;
    } else {
        return (
            <div>
                <CustomPaginationActionsTable vocabs={items} level={props.level}/>
            </div>
        );
    }
}

// https://stackoverflow.com/questions/21591512/pass-props-from-template-into-react-js-root-node
let container = document.getElementById('react-hsk');
ReactDOM.render(
    <HSK level={container.getAttribute('level')}/>,
    container
);
