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
        console.log("value : "+e.target.value);
        console.log("name : "+e.target.name);
        this.setState({[e.target.name]:e.target.value})
        console.log(this.state.roleType);
        console.log(this.state.username);
    }

    onSubmit = (e) => {
        console.log('hiiii');
        e.preventDefault();
        const {username,password,roleType,title,firstName,lastName,address} = this.state;
        console.log("roleType : "+roleType);
        axios.post("http://localhost:8765/registrationui/registration/api/v1.0/registration",  {
            id:"3",
            username:username,
            password:password,
            roleType:roleType,
            title:title,
            firstName:firstName,
            lastName:lastName,
            address: address
        } ).then(function(response){
                console.log("response : "+response.data);
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
                    <span className="control-span">
                        <label htmlFor="username">
                            Username :
                        </label>
                        <input type="text" name="username" value={username} onChange={this.onChange}/>
                    </span>
                    <span className="control-span">
                        <label htmlFor="password">
                            Password :
                        </label>
                        <input type="text" name="password" value={password} onChange={this.onChange}/>
                    </span>
                    <span className="control-span">
                        <label htmlFor="roletype">
                            Role Type :
                        </label>
                        <select name="roleType" id="roleType" value={this.state.value} onChange={this.onChange}>
                            <option value="0">--select--</option>
                            <option value="101">Admin</option>
                            <option value="102">Donor</option>
                        </select>
                    </span>
                    <span className="control-span">
                        <label htmlFor="title">
                            Title :
                        </label>
                        <input type="title" name="title" value={title} onChange={this.onChange}/>
                    </span>
                    <span className="control-span">
                        <label htmlFor="firstname">
                            First Name :
                        </label>
                        <input type="text" name="firstName" value={firstName} onChange={this.onChange}/>
                    </span>
                    <span className="control-span">
                        <label htmlFor="lastname">
                            Last Name :
                        </label>
                        <input type="text" name="lastName" value={lastName} onChange={this.onChange}/>
                    </span>
                    <span className="control-span">
                        <label htmlFor="address">
                            Address :
                        </label>
                        <input type="text" name="address" value={address} onChange={this.onChange}/>
                    </span>
                    <span className="control-span">
                        <button type="submit" onClick={this.onSubmit}>Submit</button>
                    </span>
            </form>

            </div>

        )
    }
}

export default RegisrationComponent