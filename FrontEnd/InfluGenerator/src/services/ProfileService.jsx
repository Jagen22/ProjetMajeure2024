class ProfileService {
      async createProfile(activityDescription, physicalDescription) {
      try {
        const response = await fetch('/CreateProfile/new', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify([activityDescription, physicalDescription]),
        });
  
        if (response.ok) {
          console.log('Profile created successfully');
        } else {
          console.error('Failed to create profile');
        }
      } catch (error) {
        console.error('Error while creating profile:', error);
      }
    }
  }
  
  export default new ProfileService;
  