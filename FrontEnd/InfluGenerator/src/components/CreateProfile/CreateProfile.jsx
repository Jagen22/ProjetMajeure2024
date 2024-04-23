import React, { useState } from 'react';
import './CreateProfile.css';
import ProfileService from '../../services/ProfileService';

const CreateProfile = () => {
  const [activityDescription, setActivityDescription] = useState('');
  const [physicalDescription, setPhysicalDescription] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    await ProfileService.createProfile(activityDescription, physicalDescription);
  };

  return (
    <div className="create-profile">
      <h2>Create Profile</h2>
      <form onSubmit={handleSubmit}>
        <label>
          Activity Description:
          <textarea
            value={activityDescription}
            onChange={(e) => setActivityDescription(e.target.value)}
            required
          />
        </label>
        <label>
          Physical Description:
          <textarea
            value={physicalDescription}
            onChange={(e) => setPhysicalDescription(e.target.value)}
            required
          />
        </label>
        <div className="submit-button">
          <button type="submit">Submit Profile Creation</button>
        </div>
      </form>
    </div>
  );
};

export default CreateProfile;
