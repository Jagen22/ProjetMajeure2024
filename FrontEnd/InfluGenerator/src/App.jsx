import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';

import Header from './components/Header/Header';
import Sidebar from './components/Sidebar/Sidebar';
import AuthForm from './components/AuthForm/AuthForm';
import RegisterForm from './components/RegisterForm/RegisterForm';
import CreateProfile from './components/CreateProfile/CreateProfile';
import './App.css';

const HomePage = () => (
  <div className="page-content">
    <div className="welcome-text">
      <span className="main-text">Welcome to InfluGenerator</span>
      <br />
      <span className="subtext">Create and manage your Twitter profile easily</span>
    </div>
  </div>
);

const App = () => {
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);
  const [authenticatedUser, setAuthenticatedUser] = useState(null);

  const toggleSidebar = () => {
    setIsSidebarOpen(!isSidebarOpen);
  };

  const closeSidebar = () => {
    setIsSidebarOpen(false);
  };

  const handleLogin = (username) => {
    setAuthenticatedUser(username);
  };

  const handleRegister = (username) => {
    setAuthenticatedUser(username);
  };

  return (
    <Router>
      <Header onMenuToggle={toggleSidebar} />
      <Sidebar isOpen={isSidebarOpen} onCloseSidebar={closeSidebar} />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/auth" element={<AuthForm onLogin={handleLogin} onRegister={handleRegister} />} />
        <Route path="/register" element={<RegisterForm onRegister={handleRegister} />} />
        <Route path="/create-profile" element={<CreateProfile />} />
      </Routes>
    </Router>
  );
};

export default App;
