import React, { createContext, useState, useEffect, useContext } from 'react';
import api from '../../services/api'; // Import the custom API instance
import { jwtDecode } from "jwt-decode";

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem('jwtToken');
    if (token) {
      try {
        const decodedUser = jwtDecode(token);
        // You might want to validate token expiration here
        if (decodedUser.exp * 1000 > Date.now()) {
          setUser({ ...decodedUser, roles: decodedUser.roles });
        } else {
          localStorage.removeItem('jwtToken');
        }
      } catch (error) {
        console.error("Failed to decode JWT token:", error);
        localStorage.removeItem('jwtToken');
      }
    }
    setLoading(false);
  }, []);

  const login = async (usernameOrEmail, password) => {
    const response = await api.post('/auth/login', { usernameOrEmail, password }); // Use api.post
    const { token } = response.data;
    localStorage.setItem('jwtToken', token);
    const decodedUser = jwtDecode(token);
    setUser({ ...decodedUser, roles: decodedUser.roles });
  };

  const logout = () => {
    localStorage.removeItem('jwtToken');
    setUser(null);
  };

  const value = { user, login, logout, loading };

  return (
    <AuthContext.Provider value={value}>
      {!loading && children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
