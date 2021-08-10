import React, { useState, useEffect } from 'react';
const ReactDOM = require('react-dom');

function HSK(props) {
    return <h1>{props.level}</h1>
}

let container = document.getElementById('react-hsk');
ReactDOM.render(
    <HSK level={container.getAttribute('level')}/>,
    container
);
