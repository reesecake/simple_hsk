import React, { useState, useEffect } from 'react';
const ReactDOM = require('react-dom');

const root = '/api'

function HSK(props) {
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [items, setItems] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/api/vocabs/search/findVocabsByLevel?level=HSK' + props.level, {
            method: 'GET',
            headers: {
                'Accept': 'application/hal+json'
            }
        })
            .then(res => res.json())
            .then(
                (result) => {
                    console.log(result);
                    setIsLoaded(true);
                    setItems(result._embedded.vocabs);
                },
                (error) => {
                    setIsLoaded(true);
                    setError(error);
                }
            )
    }, [])

    if (error) {
        return <div>Error: {error.message}</div>;
    } else if (!isLoaded) {
        return <div>Loading...</div>;
    } else {
        return (
            <div>items: {items._embedded.vocabs}</div>
            // <EmployeeList employees={items}/>
        );
    }
}

// https://stackoverflow.com/questions/21591512/pass-props-from-template-into-react-js-root-node
let container = document.getElementById('react-hsk');
ReactDOM.render(
    <HSK level={container.getAttribute('level')}/>,
    container
);
