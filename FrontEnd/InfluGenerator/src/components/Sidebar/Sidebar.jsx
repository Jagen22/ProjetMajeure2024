import React from 'react';
import './Sidebar.css';
import { Link } from 'react-router-dom';

const Sidebar = ({ isOpen, onCloseSidebar, onMenuItemClick }) => {
  const handleClick = (e) => {
    e.stopPropagation();
  };

  return (
    <div className={`sidebar ${isOpen ? 'open' : ''}`} onClick={onCloseSidebar}>
      <div onClick={handleClick}>
        <Link to="/create-profile" onClick={() => onMenuItemClick('create')}>
          Create a Profile
        </Link>
        <a href="#" onClick={() => onMenuItemClick('my')}>
          My Profiles
        </a>
      </div>
    </div>
  );
};

export default Sidebar;
