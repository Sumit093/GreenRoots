import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext'; // We will create this later
import Button from '../common/Button';

const Navbar = () => {
  const { user, logout } = useAuth(); // Assume useAuth provides user and logout function
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav className="bg-green-800 p-4 text-white shadow-md">
      <div className="container mx-auto flex justify-between items-center">
        <Link to="/" className="text-2xl font-bold text-white">Eco-Scholars</Link>
        <div className="space-x-4">
          {!user ? (
            <>
              <Link to="/login" className="hover:text-green-200">Login</Link>
              <Link to="/register" className="hover:text-green-200">Register</Link>
            </>
          ) : (
            <>
              {user.roles.includes('STUDENT') && <Link to="/student/dashboard" className="hover:text-green-200">My Plant</Link>}
              {user.roles.includes('SCHOOL_ADMIN') && <Link to="/school-admin/dashboard" className="hover:text-green-200">School Dashboard</Link>}
              {user.roles.includes('ADMIN') && <Link to="/admin/dashboard" className="hover:text-green-200">Admin Dashboard</Link>}
              {user.roles.includes('SUPER_ADMIN') && <Link to="/super-admin/dashboard" className="hover:text-green-200">Super Admin</Link>}
              <span className="text-green-200">Welcome, {user.firstName || user.username}!</span>
              <Button onClick={handleLogout} variant="outline" className="text-white border-white hover:bg-green-700">Logout</Button>
            </>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
