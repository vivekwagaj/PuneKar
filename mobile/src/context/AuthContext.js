import React, { createContext, useContext, useState, useEffect } from 'react';
import { Platform } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import api from '../api/client';

// ─── Firebase Web Auth (used on Platform.OS === 'web') ───────────────────────
import '../config/firebaseConfig'; // initialise Firebase app
import { getAuth, GoogleAuthProvider, signInWithPopup, signOut as firebaseSignOut } from 'firebase/auth';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Configure native Google Sign-In once on app start (skipped on web)
    if (Platform.OS !== 'web') {
      const { GoogleSignin } = require('@react-native-google-signin/google-signin');
      GoogleSignin.configure({
        // Replace with your actual Web Client ID from Firebase Console
        webClientId: 'YOUR_WEB_CLIENT_ID.apps.googleusercontent.com',
      });
    }
    _restoreSession();
  }, []);


  const _restoreSession = async () => {
    try {
      const stored = await AsyncStorage.getItem('user');
      if (stored) setUser(JSON.parse(stored));
    } catch (e) {
      console.warn('Failed to restore session', e);
    } finally {
      setLoading(false);
    }
  };

  // ── Web: Firebase JS SDK popup ──────────────────────────────────────────────
  const _webSignIn = async () => {
    const auth = getAuth();
    const provider = new GoogleAuthProvider();
    const result = await signInWithPopup(auth, provider);
    const idToken = await result.user.getIdToken();
    return idToken;
  };

  // ── Native: @react-native-google-signin ────────────────────────────────────
  const _nativeSignIn = async () => {
    const { GoogleSignin } = require('@react-native-google-signin/google-signin');
    await GoogleSignin.hasPlayServices();
    const userInfo = await GoogleSignin.signIn();
    return userInfo.idToken;
  };

  const signInWithGoogle = async () => {
    const idToken = Platform.OS === 'web'
      ? await _webSignIn()
      : await _nativeSignIn();

    // Exchange Firebase ID token for our backend app user record
    const response = await api.post('/auth/verify', null, {
      headers: { Authorization: `Bearer ${idToken}` },
    });

    const appUser = response.data;
    await AsyncStorage.setItem('user', JSON.stringify(appUser));
    setUser(appUser);
    return appUser;
  };

  const signOut = async () => {
    if (Platform.OS === 'web') {
      await firebaseSignOut(getAuth());
    } else if (GoogleSignin) {
      await GoogleSignin.signOut();
    }
    await AsyncStorage.removeItem('user');
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, loading, signInWithGoogle, signOut }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);