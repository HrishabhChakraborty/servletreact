import { useState } from 'react';

function CreateUser() {
    const [user, setUser] = useState({
        firstName: '',
        lastName: '',
        email: ''
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setUser(prevState => ({ ...prevState, [name]: value }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        fetch('/users', {
            method: 'POST',
            body: new URLSearchParams(user)
        }).then(() => {
            // Reset the form or navigate to home after success
            setUser({
                firstName: '',
                lastName: '',
                email: ''
            });
        });
    };

    return (
        <form onSubmit={handleSubmit}>
            <input name="firstName" value={user.firstName} onChange={handleChange} placeholder="First Name" />
            <input name="lastName" value={user.lastName} onChange={handleChange} placeholder="Last Name" />
            <input name="email" value={user.email} onChange={handleChange} placeholder="Email" />
            <button type="submit">Add User</button>
        </form>
    );
}

export default CreateUser;
