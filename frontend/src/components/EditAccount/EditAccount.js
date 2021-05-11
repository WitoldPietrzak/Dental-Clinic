import React from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "./EditAccount.css";
import Switch from "react-switch";

export default class EditAccount extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            isDisabled: true,
            text: "Edit",
            login: "",
            email: "",
            password: "",
            repeatedPassword: "",
            firstName: "",
            lastName: "",
            phoneNumber: "",
            pesel: "",
            darkMode: false,
        }
    }

    validateForm(t) {
        // Todo: zrobić walidację taką jaką wymaga projekt
        function loginCorrect() {
            return t.state.login.length > 0;
        }

        function emailCorrect() {
            return t.state.email.length > 0;
        }

        function passwordCorrect() {
            return t.state.password.length > 0;
        }

        function firstNameCorrect() {
            return t.state.firstName.length > 0;
        }

        function lastNameCorrect() {
            return t.state.lastName.length > 0;
        }

        function phoneNumberCorrect() {
            return t.state.phoneNumber.length > 0;
        }

        // TODO: przypadek obcokrajowca wymusza że peselu może nie być ale nadal warto by go zwalidowac, tylko jak?
        function peselCorrect() {
            return true;
        }

        return emailCorrect() && passwordCorrect() && loginCorrect() && firstNameCorrect() && lastNameCorrect() && phoneNumberCorrect() && peselCorrect();
    }


    // Todo: prawdopodobnie wysyłać zapytanie do backendu tutaj, chciałbym zrobić tak jak w vue się da żeby jeśli odpalam w trybie debug front to łącze z localhostem, narazie nie ruszam.
    handleSubmit(event) {
        event.preventDefault();
    }

    handleOnClick(t) {
        if (this.state.isDisabled === true) {
            this.setEditable()
        } else {
            this.setNotEditable(t)
        }
    }

    setEditable() {
        this.setState({
            isDisabled: false,
            text: "Save"
        });
    }

    setNotEditable(t) {
        this.validateForm(t)
        this.setState({
            isDisabled: true,
            text: "Edit"
        });
    }

    // todo: Czy dodawać tutaj też język do wyboru z en / pl? W dto go nie ma
    render() {
        return (
            <div className="EditAccount">
                <Form onSubmit={this.handleSubmit}>
                    <Form.Group size="lg" controlId="login">
                        <Form.Label>Login</Form.Label>
                        <Form.Control
                            autoFocus
                            type="login"
                            value={this.state.login}
                            disabled={true}
                            onChange={(e) => this.setState({login: e.target.value})}
                        />
                    </Form.Group>
                    <Form.Group size="lg" controlId="email">
                        <Form.Label>Email</Form.Label>
                        <Form.Control
                            autoFocus
                            type="email"
                            value={this.state.email}
                            disabled={this.state.isDisabled}
                            onChange={(e) => this.setState({email: e.target.value})}
                        />
                    </Form.Group>
                    <Form.Group size="lg" controlId="firstName">
                        <Form.Label>First Name</Form.Label>
                        <Form.Control
                            type="text"
                            value={this.state.firstName}
                            disabled={this.state.isDisabled}
                            onChange={(e) => this.setState({firstName: e.target.value})}
                        />
                    </Form.Group>
                    <Form.Group size="lg" controlId="lastName">
                        <Form.Label>Last Name</Form.Label>
                        <Form.Control
                            type="text"
                            value={this.state.lastName}
                            disabled={this.state.isDisabled}
                            onChange={(e) => this.setState({lastName: e.target.value})}
                        />
                    </Form.Group>
                    <Form.Group size="lg" controlId="phoneNumber">
                        <Form.Label>Phone Number</Form.Label>
                        <Form.Control
                            type="text"
                            value={this.state.phoneNumber}
                            disabled={this.state.isDisabled}
                            onChange={(e) => this.setState({phoneNumber: e.target.value})}
                        />
                    </Form.Group>
                    {/*Todo: co z peselem dla obcokrajowca? Nic czy coś innnego? Narazie zrobiłem że może być pusty*/}
                    <Form.Group size="lg" controlId="pesel">
                        <Form.Label>Pesel</Form.Label>
                        <Form.Control
                            type="text"
                            value={this.state.pesel}
                            disabled={this.state.isDisabled}
                            onChange={(e) => this.setState({pesel: e.target.value})}
                        />
                    </Form.Group>
                    <Form.Group style={{marginTop: "20px"}}>
                        <Form.Label style={{marginRight: "10px", transform: "translateY(-30%)"}}>Dark mode</Form.Label>
                        <Switch
                            onChange={(checked: boolean) => {
                                this.setState({darkMode: checked})
                            }}
                            disabled={this.state.isDisabled}
                            checked={this.state.darkMode}/>
                    </Form.Group>
                    <Button block size="lg" type="submit"
                            onClick={() => this.handleOnClick(this)}>
                        {this.state.text}
                    </Button>
                </Form>
            </div>
        );
    }
}