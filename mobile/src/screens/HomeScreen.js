import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  TouchableOpacity,
  StatusBar,
} from 'react-native';
import { useAuth } from '../context/AuthContext';

export default function HomeScreen() {
  const { user, signOut } = useAuth();

  return (
    <View style={styles.container}>
      <StatusBar barStyle="light-content" backgroundColor="#0A0A1A" />
      <View style={styles.header}>
        <Text style={styles.greeting}>
          Welcome, {user?.displayName || 'Citizen'} 👋
        </Text>
        <Text style={styles.ward}>📍 Viman Nagar</Text>
      </View>

      <View style={styles.body}>
        <Text style={styles.placeholder}>
          Issue feed coming soon...
        </Text>
      </View>

      <TouchableOpacity style={styles.signOutButton} onPress={signOut}>
        <Text style={styles.signOutText}>Sign Out</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#0A0A1A',
    paddingHorizontal: 24,
    paddingTop: 60,
  },
  header: {
    marginBottom: 32,
  },
  greeting: {
    fontSize: 26,
    fontWeight: '700',
    color: '#FFFFFF',
  },
  ward: {
    fontSize: 14,
    color: '#6C63FF',
    marginTop: 4,
    fontWeight: '600',
  },
  body: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  placeholder: {
    color: '#555',
    fontSize: 16,
  },
  signOutButton: {
    alignItems: 'center',
    paddingVertical: 16,
    marginBottom: 32,
  },
  signOutText: {
    color: '#FF5555',
    fontSize: 15,
    fontWeight: '600',
  },
});
