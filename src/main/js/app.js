const ReactDOM = require('react-dom');
const client = require('./client');
import React, { useState, useEffect } from 'react';

function App() {
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [items, setItems] = useState([]);

    useEffect(() => {
        fetch('http://localhost:8080/api/employees', {
            method: 'GET',
            headers: {
                'Accept': 'application/hal+json'
            }
        })
            .then(res => res.json())
            .then(
                (result) => {
                    setIsLoaded(true);
                    setItems(result._embedded.employees);
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
            // <div>items: {items.employees}</div>
            <EmployeeList employees={items}/>
        );
    }
}

// class App extends React.Component {
//
//     constructor(props) {
//         super(props);
//         this.state = {employees: []};
//     }
//
//     componentDidMount() {
//         fetch('http://localhost:8080/api/employees', {
//             method: 'GET',
//             headers: {
//                 'Accept': 'application/hal+json'
//             }
//         }).then(res => res.json())
//             .then((result) => {this.setState({employees: result._embedded.employees})});
//     }
//
//     render() {
//         return (
//             <EmployeeList employees={this.state.employees}/>
//         )
//     }
// }

class EmployeeList extends React.Component {
    render() {
        const employees = this.props.employees.map(employee =>
            <Employee key={employee._links.self.href} employee={employee}/>
            );
        return (
            <table>
                <tbody>
                <tr>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Description</th>
                </tr>
                {employees}
                </tbody>
            </table>
        )
    }
}

class Employee extends React.Component {
    render() {
        return (
            <tr>
                <td>{this.props.employee.firstName}</td>
                <td>{this.props.employee.lastName}</td>
                <td>{this.props.employee.description}</td>
            </tr>
        )
    }
}

ReactDOM.render(
    <App />,
    document.getElementById('react')
)
