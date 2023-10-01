import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

function UpdateUser() {
    const { userId } = useParams();

    const [user, setUser] = useState({
        firstName: '',
        lastName: '',
        email: ''
    });

    useEffect(() => {
        fetch(`/users?id=${userId}`)
            .then(response => response.json())
            .then(data => {
                // Ensure data is not undefined or null
                if (data) {
                    setUser({
                        firstName: data.firstName || '',
                        lastName: data.lastName || '',
                        email: data.email || ''
                    });
                }
            });
    }, [userId]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setUser(prevState => ({ ...prevState, [name]: value }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        fetch(`/users?id=${userId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        })
        .then(response => response.text())
        .then(data => {
            console.log("Response from server:", data);
        })
        .catch(error => {
            console.error("There was an error updating the user:", error);
        });
    };
    
   

    return (
        <form onSubmit={handleSubmit}>
            <input name="firstName" value={user.firstName || ''} onChange={handleChange} placeholder="First Name" />
            <input name="lastName" value={user.lastName || ''} onChange={handleChange} placeholder="Last Name" />
            <input name="email" value={user.email || ''} onChange={handleChange} placeholder="Email" />
            <button type="submit">Update User</button>
        </form>
    );
}

export default UpdateUser;
