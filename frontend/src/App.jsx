import './App.css'
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import Navbar from "./components/Navbar"
import Home from './components/Home';
import NotFound from './components/NotFound';
import User from './components/UserList';
import AddEditUser from './components/AddEditUser';
import SimulationCredit from './components/SimulationCredit';
import CreditsList from './components/CreditsList';
import AddCredit from './components/AddCredit';

function App() {
  return (
      <Router>
          <div className="container">
          <Navbar></Navbar>
            <Routes>
              <Route path="/home" element={<Home/>} />
              <Route path="/user/list" element={<User/>} />
              <Route path="/user/add" element={<AddEditUser/>} />
              <Route path="/user/simulation" element={<SimulationCredit/>} />
              <Route path="/credit/list" element={<CreditsList/>} />
              <Route path="/credit/add/:id" element={<AddCredit/>} />
              <Route path="*" element={<NotFound/>} />
            </Routes>
          </div>
      </Router>
  );
}

export default App

