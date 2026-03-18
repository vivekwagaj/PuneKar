import React, { useState } from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
  ActivityIndicator,
  Image,
  Alert,
  StatusBar,
} from 'react-native';
import { useAuth } from '../context/AuthContext';

export default function LoginScreen() {
  const { signInWithGoogle } = useAuth();
  const [loading, setLoading] = useState(false);

  const handleGoogleSignIn = async () => {
    try {
      setLoading(true);
      await signInWithGoogle();
      // Navigation to Home is driven by AuthContext state change in App.js
    } catch (error) {
      console.error('Sign-in error', error);
      Alert.alert(
        'Sign In Failed',
        error?.message || 'Something went wrong. Please try again.',
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <View style={styles.container}>
      <StatusBar barStyle="light-content" backgroundColor="#0A0A1A" />

      {/* Hero section */}
      <View style={styles.hero}>
        <Text style={styles.appIcon}>🏙️</Text>
        <Text style={styles.title}>PuneKar</Text>
        <Text style={styles.subtitle}>Viman Nagar</Text>
        <Text style={styles.tagline}>
          Report · Prioritise · Verify · Track
        </Text>
      </View>

      {/* Card */}
      <View style={styles.card}>
        <Text style={styles.cardTitle}>Your Ward, Your Voice</Text>
        <Text style={styles.cardBody}>
          Sign in with Google to report civic issues, vote on what matters, and
          help your neighbourhood thrive.
        </Text>

        <TouchableOpacity
          style={[styles.googleButton, loading && styles.googleButtonDisabled]}
          onPress={handleGoogleSignIn}
          disabled={loading}
          activeOpacity={0.8}
        >
          {loading ? (
            <ActivityIndicator color="#1a1a2e" size="small" />
          ) : (
            <>
              <Text style={styles.googleIcon}>G</Text>
              <Text style={styles.googleText}>Continue with Google</Text>
            </>
          )}
        </TouchableOpacity>

        <Text style={styles.disclaimer}>
          By signing in, you agree to our community guidelines. Your data is
          never sold.
        </Text>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#0A0A1A',
    justifyContent: 'space-between',
    paddingHorizontal: 24,
    paddingVertical: 60,
  },
  hero: {
    alignItems: 'center',
    flex: 1,
    justifyContent: 'center',
  },
  appIcon: {
    fontSize: 72,
    marginBottom: 12,
  },
  title: {
    fontSize: 46,
    fontWeight: '800',
    color: '#FFFFFF',
    letterSpacing: 1,
  },
  subtitle: {
    fontSize: 18,
    color: '#6C63FF',
    fontWeight: '600',
    marginTop: 4,
    letterSpacing: 3,
    textTransform: 'uppercase',
  },
  tagline: {
    marginTop: 16,
    fontSize: 13,
    color: '#888',
    letterSpacing: 1.5,
    textAlign: 'center',
  },
  card: {
    backgroundColor: '#14142B',
    borderRadius: 24,
    padding: 28,
    borderWidth: 1,
    borderColor: '#2a2a4a',
  },
  cardTitle: {
    fontSize: 22,
    fontWeight: '700',
    color: '#FFFFFF',
    marginBottom: 10,
  },
  cardBody: {
    fontSize: 14,
    color: '#999',
    lineHeight: 22,
    marginBottom: 28,
  },
  googleButton: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#FFFFFF',
    borderRadius: 12,
    paddingVertical: 15,
    gap: 10,
  },
  googleButtonDisabled: {
    opacity: 0.6,
  },
  googleIcon: {
    fontSize: 18,
    fontWeight: '700',
    color: '#4285F4',
  },
  googleText: {
    fontSize: 16,
    fontWeight: '700',
    color: '#1a1a2e',
  },
  disclaimer: {
    fontSize: 11,
    color: '#555',
    textAlign: 'center',
    marginTop: 16,
    lineHeight: 18,
  },
});
