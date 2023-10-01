import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

function HomePage() {
    const [users, setUsers] = useState([]);

    useEffect(() => {
        fetch('/users')
            .then(response => response.json())
            .then(data => setUsers(data));
    }, []);

    const deleteUser = (userId) => {
        fetch(`/users?id=${userId}`, {
            method: 'DELETE'
        }).then(() => {
            // Refresh the list after deletion
            setUsers(users.filter(user => user.id !== userId));
        });
    };

    return (
        <>
        <Link to={`/create`}>Create</Link>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                {users.map(user => (
                    <tr key={user.id}>
                        <td>{user.id}</td>
                        <td>{user.firstName} {user.lastName}</td>
                        <td>{user.email}</td>
                        <td>
                            <button onClick={() => deleteUser(user.id)}>Delete</button>
                            <Link to={`/update/${user.id}`}>Update</Link>
                        </td>
                    </tr>
                ))}
            </tbody>
        </table>
        </>
    );
}

export default HomePage;
