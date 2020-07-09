import React, { Component } from "react";
import Navbar from "react-bootstrap/Navbar";

class App extends Component {
  render() {
    return (
      <div className="App">
        <Navbar bg="dark" variant="dark">
          <Navbar.Brand>Black Owned Businesses</Navbar.Brand>
        </Navbar>
        <div class="Body">
          <h1>Hello World</h1>
        </div>
      </div>
    );
  }
}

export default App;
