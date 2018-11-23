import React from 'react'
import axios from 'axios'

class RegisrationComponent extends React.Component{
    constructor(){
        super();
        this.state={
            username: '',
            password:'',
            roleType: '',
            title:'',
            firstName: '',
            lastName: '',
            address:''

        }
    }

    onChange = (e) => {
        alert('jjllj');
        console.log("ffdgfgdg");
        this.setState({[e.target.name]:e.target.value})
    }

    onSubmit = (e) => {
        console.log('hiiii');
        e.preventDefault();
        const {username,password,roleType,title,firstName,lastName,address} = this.state;
        console.log('asif');
        axios.post("http://localhost:8001/registration/api/v1.0/registration",  {
            id:"1",
            username:username,
            password:password,
            roleType:roleType,
            title:title,
            firstName:firstName,
            lastName:lastName,
            address: address
        } )
            .then(function(response){
            console.log("response : "+response);
            })
            .catch(function(error){
                console.log("error : "+error);
            })

    }
    render(){
        const {username, password, roleType, title, firstName, lastName, address} = this.state;
        return(
            <div>
                <form>
                 username
                    <input type="text" name="username" value={username} onChange={this.onChange}/>
                    <br />
                password
                    <input type="text" name="password" value={password} onChange={this.onChange}/>
                    <br />
                roletype
                    <input type="text" name="roleType" value={roleType} onChange={this.onChange}/>
                    <br />
                title
                    <input type="title" name="title" value={title} onChange={this.onChange}/>
                    <br />
                firstname
                    <input type="text" name="firstName" value={firstName} onChange={this.onChange}/>
                    <br />
                lastname
                    <input type="text" name="lastName" value={lastName} onChange={this.onChange}/>
                    <br />
                address
                    <input type="text" name="address" value={address} onChange={this.onChange}/>
                    <br />
                    <button type="submit" onClick={this.onSubmit}>Submit</button>
            </form>

            </div>

        )
    }
}

export default RegisrationComponent