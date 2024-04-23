class UserService {
  static async authenticate(username, password) {
    try {
      const response = await fetch('/api/users/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password }),
        credentials: 'include',
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(`Authentication failed: ${errorData.message}`);
      }

      const user = await response.json();
      return user;
    } catch (error) {
      console.error('Error during authentication:', error);
      throw error;
    }
  }

  static async register(user) {
    try {
      const response = await fetch('/api/users/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(user),
        credentials: 'include',
      });
      
      console.log("JSON envoyé:", JSON.stringify(user));

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(`Registration failed: ${errorData.message}`);
      }

      const registeredUser = await response.json();
      return registeredUser;
    } catch (error) {
      console.error('Error during registration:', error);
      throw error;
    }
  }
}

export default UserService;
