import React from 'react';
import { Routes, Route } from 'react-router-dom';
import { Toaster } from 'react-hot-toast';

import Navbar from './components/layout/Navbar'; // Import Navbar
import Home from './pages/Home';
import Login from './pages/auth/Login';
import Register from './pages/auth/Register';
// Import other pages as they are created

function App() {
  return (
    <div className="App">
      <Toaster position="top-right" reverseOrder={false} />
      <Navbar /> {/* Add Navbar here */}
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        {/* Add more routes here as needed */}
      </Routes>
    </div>
  );
}

export default App;
