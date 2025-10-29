import React from 'react';

const Home = () => {
  return (
    <div className="min-h-screen bg-gray-100 flex flex-col items-center justify-center p-4">
      <h1 className="text-4xl font-bold text-green-700 mb-4">Welcome to Eco-Scholars</h1>
      <p className="text-lg text-gray-700 text-center mb-8">Track your plant's growth and contribute to a greener future!</p>
      <div className="flex space-x-4">
        <a href="/login" className="px-6 py-3 bg-green-600 text-white rounded-lg shadow-md hover:bg-green-700 transition duration-300">Login</a>
        <a href="/register" className="px-6 py-3 border border-green-600 text-green-600 rounded-lg shadow-md hover:bg-green-50 transition duration-300">Register</a>
      </div>
    </div>
  );
};

export default Home;
