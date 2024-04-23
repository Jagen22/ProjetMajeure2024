import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './AuthForm.css';
import UserService from '../../services/UserService';
import RegisterForm from '../RegisterForm/RegisterForm';


const AuthForm = ({ onLogin }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      // Call the authentication service
      const user = await UserService.authenticate(username, password);
      setIsLoggedIn(true);
      onLogin(user.username);
    } catch (error) {
      console.error('Authentication failed:', error); 
    }
  };

  const handleRegisterClick = () => {
    navigate('/register');  
  };

  return (
    <div className={`auth-form ${isLoggedIn ? 'logged-in' : ''}`}>
      {isLoggedIn ? (
        <div>
          <p>Welcome, you are logged in as {username}</p>
        </div>
      ) : (
        <form onSubmit={handleLogin}>
          <label>
            Username:
            <input
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
          </label>
          <br />
          <label>
            Password:
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </label>
          <br />
          <div className="auth-buttons">
            <button type="submit">
              Login
            </button>
            <Link to="/register">
              <button type="button" onClick={handleRegisterClick}>
                Register
              </button>
            </Link>
          </div>
        </form>
      )}
    </div>
  );
};

export default AuthForm;
