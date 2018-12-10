import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import LoginComponent from './components/login/LoginComponent'
import LayoutComponent from './components/LayoutComponent/LayoutComponent'
import RegistrationComponent from './components/RegistrationComponent/RegistrationComponent'
import { BrowserRouter as Router, Switch, Route} from 'react-router-dom'

const Routes = props =>  (
    <div>
    <LayoutComponent />
      <Switch>
          <Route exact path='/' component={LoginComponent}/>
          <Route path='/login' component={LoginComponent}/>
          <Route path='/home' component={Home}/>
          <Route path='/registration' component={RegistrationComponent}/>
      </Switch>
    </div>
)



const App = props => (
    <Router>
        <div>
        <Route path='/' component={Routes}/>
        </div>
    </Router>
)

const Home = () => (
    <div>
        <h1>Welcome to the Tornadoes Website!</h1>
    </div>
)
export default App;
