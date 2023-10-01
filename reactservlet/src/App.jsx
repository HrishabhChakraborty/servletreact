import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import HomePage from './components/HomePage';
import CreateUser from './components/CreateUser';
import UpdateUser from './components/UpdateUser';


function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/create" element={<CreateUser />} />
                <Route path="/update/:userId" element={<UpdateUser />} />
            </Routes>
        </Router>
    );
}

export default App;