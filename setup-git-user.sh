#!/bin/bash

# Git User Configuration Script
# Replace the values below with your actual information

echo "======================================"
echo "Git User Configuration"
echo "======================================"
echo ""

# REPLACE THESE WITH YOUR ACTUAL INFORMATION
YOUR_NAME="Your Name"
YOUR_EMAIL="your.email@example.com"

echo "Setting up Git user configuration..."
echo "Name: $YOUR_NAME"
echo "Email: $YOUR_EMAIL"
echo ""

# Configure Git user (locally for this repository only)
git config user.name "$YOUR_NAME"
git config user.email "$YOUR_EMAIL"

echo "✅ Git user configured!"
echo ""
echo "Verifying configuration:"
git config user.name
git config user.email
echo ""
echo "======================================"
echo "Configuration complete!"
echo "======================================"

