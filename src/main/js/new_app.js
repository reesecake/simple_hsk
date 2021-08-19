import React, { useState, useEffect } from 'react';
import { FormControl, FormLabel, RadioGroup, FormControlLabel, Radio } from '@material-ui/core';

const ReactDOM = require('react-dom');

const root = '/api'

function HSK(props) {
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [items, setItems] = useState([]);
    const [display, setDisplay] = useState([]);
    const [value, setValue] = useState("cumulative");

    useEffect(() => {
        fetch('http://localhost:8080/api/vocabs/search/findVocabByLevelIsLessThanEqual?level=HSK' + props.level, {
            method: 'GET',
            headers: {
                'Accept': 'application/hal+json'
            }
        })
            .then(res => res.json())
            .then(
                (result) => {
                    // console.log(result);
                    setIsLoaded(true);
                    setItems(result._embedded.vocabs);
                    setDisplay(result._embedded.vocabs);
                },
                (error) => {
                    setIsLoaded(true);
                    setError(error);
                }
            )
    }, [])

    function handleChange(e) {
        console.log(e.target.value);
        if (e.target.value === "cumulative") {
            setDisplay(items);
        } else {
            setDisplay(items.filter(vocab => vocab.level === "HSK" + props.level));
        }
        setValue(e.target.value);
    }

    if (error) {
        return <div>Error: {error.message}</div>;
    } else if (!isLoaded) {
        return <div>Loading...</div>;
    } else {
        // console.log(items);
        // console.log(display);
        return (
            <div>
                <FormControl component="fieldset">
                    <FormLabel component="legend">Setting</FormLabel>
                    <RadioGroup row aria-label="Settings" name="settings1" value={value} onChange={handleChange}>
                        <FormControlLabel value="cumulative" control={<Radio />} label="Cumulative" />
                        <FormControlLabel value="non-cumulative" control={<Radio />} label="Non-cumulative" />
                    </RadioGroup>
                </FormControl>

                <VocabList vocabs={display}/>
            </div>
        );
    }
}

function VocabList(props) {
    const vocabs = props.vocabs.map(vocab =>
        <Vocab key={vocab._links.self.href} vocab={vocab}/>
    );

    return (
        <table>
            <tbody>
            <tr>
                <th>Word</th>
                <th>pinyin</th>
                <th>Meaning</th>
                <th>Level</th>
            </tr>
            {vocabs}
            </tbody>
        </table>
    )
}

function Vocab(props) {
    return (
        <tr>
            <td>{props.vocab.word}</td>
            <td>{props.vocab.pinyin}</td>
            <td>{props.vocab.meaning}</td>
            <td>{props.vocab.level}</td>
        </tr>
    )
}

// https://stackoverflow.com/questions/21591512/pass-props-from-template-into-react-js-root-node
let container = document.getElementById('react-hsk');
ReactDOM.render(
    <HSK level={container.getAttribute('level')}/>,
    container
);
