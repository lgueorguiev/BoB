import React, { Component } from "react";
import Navbar from "react-bootstrap/Navbar";
import Location from "./Location.js";

class App extends Component {
  render() {
    return (
      <div className="App">
        <Navbar bg="dark" variant="dark">
          <Navbar.Brand>Black Owned Businesses</Navbar.Brand>
        </Navbar>
        <div class="Body">
          <Location />
        </div>
      </div>
    );
  }
}

export default App;
