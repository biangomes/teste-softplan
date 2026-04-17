import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from "reactstrap";
import AppNavBar from './AppNavbar';
import { Link } from 'react-router-dom';

class PessoaList extends Component {
    constructor(props) {
        super(props);
        this.state = {pessoas: []};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        fetch('/api/v1/pessoas')
            .then(resp => resp.json())
            .then(data => this.setState({pessoas: data}));
    }
}

export default PessoaList;