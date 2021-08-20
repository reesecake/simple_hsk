import React, {useEffect, useState} from "react";
import {FormControl, RadioGroup, FormControlLabel, Radio, InputLabel, Select} from '@material-ui/core';
import ReactDOM from "react-dom";

function HSK_OLD(props) {
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [items, setItems] = useState([]);
    const [display, setDisplay] = useState([]);
    const [value, setValue] = useState("cumulative");
    const [page, setPage] = useState({});

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
                    setItems(result._embedded.vocabs);
                    setDisplay(result._embedded.vocabs);
                    setPage(result.page);
                    setIsLoaded(true);
                },
                (error) => {
                    setError(error);
                    setIsLoaded(true);
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

    function updatePageSize(e) {
        setPage({
            size: parseInt(e.target.value),
            totalElements: page.totalElements,
            totalPages: page.totalPages,
            number: page.number
        });
    }

    if (error) {
        return <div>Error: {error.message}</div>;
    } else if (!isLoaded) {
        return <div>Loading...</div>;
    } else {
        // console.log(items);
        // console.log("display: " + display);
        // console.log(page);
        return (
            <div>
                <FormControl component="fieldset">
                    {/*<FormLabel component="legend">Setting</FormLabel>*/}
                    <RadioGroup row name="settings1" value={value} onChange={handleChange}>
                        <FormControlLabel value="cumulative" control={<Radio />} label="Cumulative" />
                        <FormControlLabel value="non-cumulative" control={<Radio />} label="Non-cumulative" />
                    </RadioGroup>
                </FormControl>

                <PageSizeLimiter page={page} updatePageSize={updatePageSize} />

                <VocabList vocabs={display}/>
            </div>
        );
    }
}

function PageSizeLimiter(props) {
    return (
        <FormControl variant="outlined">
            <InputLabel htmlFor="outlined-pageSize-native-simple">Show</InputLabel>
            <Select
                native
                value={props.page.size}
                onChange={props.updatePageSize}
                label="pageSize"
                inputProps={{
                    name: 'pageSize',
                    id: 'outlined-pageSize-native-simple',
                }}
            >
                <option value={10}>10</option>
                <option value={25}>25</option>
                <option value={50}>50</option>
                <option value={100}>100</option>
                <option value={500} disabled={props.page.totalElements < 500}>500</option>
                <option value={1000} disabled={props.page.totalElements < 1000}>1000</option>
                <option value={5000}>Max</option>
            </Select>
        </FormControl>
    );
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
let container = document.getElementById('react-hsk-OLD');
ReactDOM.render(
    <HSK_OLD level={container.getAttribute('level')}/>,
    container
);
