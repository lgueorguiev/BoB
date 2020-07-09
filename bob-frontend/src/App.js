import React, { Component } from "react";
import Navbar from "react-bootstrap/Navbar";
import Form from "react-bootstrap/Form";

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      results: [],
      isFoodChecked: false,
      isSalonChecked: false,
    };
  }

  handleFoodCheck() {
    this.setState({ isFoodChecked: !this.state.isFoodChecked }, () => {
      if (this.state.isFoodChecked) {
        fetch("http://localhost:8080/bobAPI/webapi/business?tag=food")
          .then((response) => response.json())
          .then((json) => this.setState({ results: json.businesses }));
      } else {
        this.setState({ results: [] });
      }
    });
  }

  handleSalonCheck() {
    this.setState({ isSalonChecked: !this.state.isSalonChecked });
  }

  render() {
    const results = this.state.results;
    return (
      <div className="App">
        <Navbar bg="dark" variant="dark">
          <Navbar.Brand>Black Owned Businesses</Navbar.Brand>
        </Navbar>
        <div className="Body">
          <h3>Filter:</h3>
          <Form>
            <Form.Group controlId="formBasicCheckbox">
              <Form.Check
                type="checkbox"
                label="food"
                onChange={() => this.handleFoodCheck()}
              />
            </Form.Group>
            <Form.Group controlId="formBasicCheckbox">
              <Form.Check
                type="checkbox"
                label="salon"
                onChange={() => this.handleSalonCheck()}
              />
            </Form.Group>
          </Form>
          <ul>
            {results.map((result) => (
              <li>
                <h1>{result.name}</h1>
              </li>
            ))}
          </ul>
        </div>
      </div>
    );
  }
}

export default App;
