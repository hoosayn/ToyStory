import React, { Fragment }  from 'react';
import axios from 'axios';
import CaptchaComponent from '../CaptchaComponent/CaptchaComponent';

class LoginComponent extends React.Component {

    constructor(){
        super();
        this.state={
           username:'',
           password:'',
           basicAuth:''
        }
    }


    onSubmit = (e) => {
        const{username,password} = this.state;
        e.preventDefault()
        this.doLogin();
    }

    onChange = (e) =>{
        this.setState({[e.target.name]:e.target.value})
        console.log(this.state)
    }


    doLogin = () => {
        {/*https://stormpath.com/blog/crud-application-react-spring-boot-user-authentication*/}
        {/*asif  how to submit form data*/}
        console.log("basic auth called ...");
        const{username,password} = this.state;
        console.log("username : "+username);
        console.log("password : "+password)
        let bodyFormData = new FormData();
        bodyFormData.set('username', username);
        bodyFormData.set('password', password);
        axios({
            method: 'post',
            url: 'http://localhost:8765/registrationui/login',
            headers: {
                'Content-Type': 'multipart/form-data',
                "Access-Control-Allow-Origin": "*"
            },
            withCredentials: true,
            data: bodyFormData
        }).then((response) => {
                console.log(response.headers.authorization)
                let authHeader=response.headers.authorization

                console.log(authHeader)
                axios({
                    method: 'get',
                    url: 'http://localhost:8765/registrationui/registration/api/v1.0/members/common',
                    headers: {
                        'Content-Type': 'application/json;charset=UTF-8',
                        "Access-Control-Allow-Origin": "*",
                         "Authorization": authHeader
                    },
                    withCredentials: true
                }).then((response) => {
                    console.log("data : "+response.data);
                    console.log("headers : "+response.headers);
                    console.log("status : "+response.status);
                    console.log("cookie : "+response.headers['set-cookies'])
                }, (error) => {

                })

            }, (error) => {

            })
    }






    render(){
        const{username,password} = this.state;
        return(
            <Fragment>
                <div>
                    <form>
                        <span className="control-span">
                            <label htmlFor="username">
                               Username :
                            </label>
                            <input type="text" name="username" id="username" value={username}
                                   onChange={this.onChange}></input>
                        </span>
                        <span className="control-span">
                            <label className="control-label" htmlFor="password">
                                Password :
                            </label>
                            <input type="text" name="password" id="password" value={password}
                                   onChange={this.onChange}></input>
                        </span>

                        <span className="control-span">
                            {/*<CaptchaComponent/>*/}

                        </span>
                        <button type="submit" onClick={this.onSubmit}>Submit</button>



                    </form>
                </div>
            </Fragment>
        )
    }
}

export default LoginComponent
