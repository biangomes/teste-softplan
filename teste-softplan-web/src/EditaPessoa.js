import {Component} from "react";

class EditaPessoa extends Component {
    itemVazio = {
        nome: '',
        email: '',
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.itemVazio
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }
}

export default withRouter(EditaPessoa);