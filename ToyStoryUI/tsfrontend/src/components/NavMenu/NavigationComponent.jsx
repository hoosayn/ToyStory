import React from 'react'
import { NavLink, Link  } from 'react-router-dom'


class NavigationComponent extends React.Component{
   /* renderLink = link => (
        <NavLink exact={link.path === '/login'} to={link.path}>
            {link.icon}
            {link.name}
        </NavLink>
    )*/

    render(){
        return(
            <div>
                <ul>
                    <li>
                        <Link to={`/`}>Home</Link>
                    </li>
                    <li>
                       <Link to={`/login`}>Rendering with React</Link>
                    </li>
                    <li>
                        <Link to={`/registration`}>Registrration</Link>
                    </li>
                </ul>
            </div>
        )
    }
}

export default NavigationComponent