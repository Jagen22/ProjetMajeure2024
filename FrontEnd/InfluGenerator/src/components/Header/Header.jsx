// Header.jsx
import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUser } from '@fortawesome/free-solid-svg-icons';
import './Header.css';

const Header = ({ onMenuToggle }) => {
  const navigate = useNavigate();

  const handleLogoClick = () => {
    // Navigate to the home page ("/")
    navigate('/');
  };

  const handleAuthClick = () => {
    // Navigate to the authentication page ("/auth")
    navigate('/auth');
  };

  return (
    <div className="header">
      <div className="menu-icon" onClick={onMenuToggle}>
        &#9776; <span className="menu-text">Menu</span>
      </div>
      <div className="header-logo" onClick={handleLogoClick} style={{ cursor: 'pointer' }}>
        InfluGenerator
      </div>
      <div className="auth-icon" onClick={handleAuthClick} style={{ cursor: 'pointer' }}>
        <FontAwesomeIcon icon={faUser} /> Auth
      </div>
    </div>
  );
};

export default Header;
