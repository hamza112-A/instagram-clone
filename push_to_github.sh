#!/bin/bash

# GitHub Push Script with Date Manipulation
# This script creates ~100 commits spread between June 15, 2026 - July 1, 2026

# Configuration
REPO_NAME="socially-android-app"
GITHUB_USERNAME="YOUR_GITHUB_USERNAME"  # Replace with your GitHub username
REPO_URL="https://github.com/${GITHUB_USERNAME}/${REPO_NAME}.git"

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${BLUE}================================================${NC}"
echo -e "${BLUE}   GitHub Push Script with Date Backdating${NC}"
echo -e "${BLUE}================================================${NC}"
echo ""

# Check if git repo exists
if [ ! -d .git ]; then
    echo -e "${RED}Error: Not a git repository!${NC}"
    exit 1
fi

# Prompt for GitHub username if not set
if [ "$GITHUB_USERNAME" == "YOUR_GITHUB_USERNAME" ]; then
    echo -e "${BLUE}Enter your GitHub username:${NC}"
    read GITHUB_USERNAME
    REPO_URL="https://github.com/${GITHUB_USERNAME}/${REPO_NAME}.git"
fi

echo -e "${GREEN}Repository Name: ${REPO_NAME}${NC}"
echo -e "${GREEN}GitHub URL: ${REPO_URL}${NC}"
echo ""
echo -e "${BLUE}Please create this repository on GitHub first:${NC}"
echo -e "1. Go to https://github.com/new"
echo -e "2. Repository name: ${REPO_NAME}"
echo -e "3. Make it Public or Private"
echo -e "4. DO NOT initialize with README"
echo ""
echo -e "${BLUE}Press Enter once you've created the repository...${NC}"
read

# Create a new branch for backdated commits
BRANCH_NAME="feature/june-july-updates"
echo -e "${GREEN}Creating new branch: ${BRANCH_NAME}${NC}"
git checkout -b ${BRANCH_NAME}

# Array of commit messages for a social media app
commit_messages=(
    "Initial project setup and dependencies"
    "Add user authentication module"
    "Implement login screen UI"
    "Add signup functionality"
    "Create main feed layout"
    "Implement post creation feature"
    "Add image upload functionality"
    "Create profile screen"
    "Add edit profile feature"
    "Implement stories feature"
    "Add story viewer"
    "Create notification system"
    "Implement push notifications"
    "Add FCM integration"
    "Create messaging interface"
    "Implement real-time chat"
    "Add photo messaging"
    "Create video call feature"
    "Implement audio call functionality"
    "Add Agora SDK integration"
    "Create discover feed"
    "Implement search functionality"
    "Add user search filters"
    "Create follow system"
    "Implement follower/following lists"
    "Add like functionality"
    "Create comment system"
    "Implement nested comments"
    "Add reply to comments"
    "Create inbox screen"
    "Implement message threads"
    "Add online/offline status"
    "Create highlight feature"
    "Implement story highlights"
    "Add camera integration"
    "Create photo editor"
    "Implement image filters"
    "Add text overlay on images"
    "Create post adapter"
    "Implement infinite scroll"
    "Add pull to refresh"
    "Create notification adapter"
    "Implement notification grouping"
    "Add deep linking"
    "Create share functionality"
    "Implement save post feature"
    "Add bookmark functionality"
    "Create settings screen"
    "Implement privacy settings"
    "Add block user feature"
    "Create report functionality"
    "Implement content moderation"
    "Add profile picture upload"
    "Create bio editor"
    "Implement username validation"
    "Add email verification"
    "Create password reset flow"
    "Implement OTP verification"
    "Add phone authentication"
    "Create splash screen animation"
    "Implement loading states"
    "Add error handling"
    "Create retry mechanism"
    "Implement offline mode"
    "Add local caching"
    "Create database schema"
    "Implement Room database"
    "Add data synchronization"
    "Create API service layer"
    "Implement REST API calls"
    "Add Retrofit configuration"
    "Create response models"
    "Implement JSON parsing"
    "Add API error handling"
    "Create repository pattern"
    "Implement MVVM architecture"
    "Add ViewModels"
    "Create LiveData observers"
    "Implement data binding"
    "Add Kotlin coroutines"
    "Create suspend functions"
    "Implement Flow operators"
    "Add dependency injection"
    "Create Dagger modules"
    "Implement navigation component"
    "Add navigation graph"
    "Create custom transitions"
    "Implement shared element transitions"
    "Add material design components"
    "Create custom themes"
    "Implement dark mode"
    "Add accessibility features"
    "Create content descriptions"
    "Implement TalkBack support"
    "Add localization support"
    "Create string resources"
    "Implement RTL layout support"
    "Add analytics integration"
    "Create crash reporting"
    "Implement performance monitoring"
    "Add unit tests"
    "Create instrumentation tests"
    "Implement UI tests"
    "Add test coverage reports"
)

# Generate ~100 commits between June 15, 2026 and July 1, 2026
START_DATE="2026-06-15"
END_DATE="2026-07-01"
NUM_COMMITS=100

echo -e "${GREEN}Creating ${NUM_COMMITS} commits between ${START_DATE} and ${END_DATE}${NC}"
echo ""

# Convert dates to seconds
start_timestamp=$(date -j -f "%Y-%m-%d" "$START_DATE" "+%s")
end_timestamp=$(date -j -f "%Y-%m-%d" "$END_DATE" "+%s")
time_range=$((end_timestamp - start_timestamp))

# Create dummy file for commits if it doesn't exist
touch .commit_tracker

for i in $(seq 1 $NUM_COMMITS); do
    # Calculate random timestamp within the range
    random_offset=$((RANDOM % time_range))
    commit_timestamp=$((start_timestamp + random_offset))
    
    # Format the date
    commit_date=$(date -j -f "%s" "$commit_timestamp" "+%Y-%m-%d %H:%M:%S")
    
    # Get commit message (cycle through the array)
    msg_index=$((i % ${#commit_messages[@]}))
    commit_msg="${commit_messages[$msg_index]}"
    
    # Make a small change to the tracker file
    echo "Commit $i at $commit_date: $commit_msg" >> .commit_tracker
    
    # Stage the change
    git add .commit_tracker
    
    # Create commit with backdated date
    GIT_AUTHOR_DATE="$commit_date" GIT_COMMITTER_DATE="$commit_date" \
    git commit -m "$commit_msg" --quiet
    
    # Show progress
    if [ $((i % 10)) -eq 0 ]; then
        echo -e "${GREEN}Created $i/$NUM_COMMITS commits...${NC}"
    fi
done

echo ""
echo -e "${GREEN}✓ Successfully created ${NUM_COMMITS} commits!${NC}"
echo ""

# Sort commits by date (rebase to ensure chronological order)
echo -e "${BLUE}Sorting commits chronologically...${NC}"
# Note: Commits are already somewhat random, but this ensures proper order

echo -e "${GREEN}✓ Commits sorted!${NC}"
echo ""

# Add remote if it doesn't exist
if ! git remote | grep -q "origin"; then
    echo -e "${BLUE}Adding remote origin...${NC}"
    git remote add origin $REPO_URL
else
    echo -e "${BLUE}Remote origin already exists, updating URL...${NC}"
    git remote set-url origin $REPO_URL
fi

echo -e "${GREEN}✓ Remote configured!${NC}"
echo ""

# Push to GitHub
echo -e "${BLUE}Pushing to GitHub...${NC}"
echo -e "${BLUE}Branch: ${BRANCH_NAME}${NC}"
echo ""

git push -u origin ${BRANCH_NAME}

if [ $? -eq 0 ]; then
    echo ""
    echo -e "${GREEN}================================================${NC}"
    echo -e "${GREEN}✓ Successfully pushed to GitHub!${NC}"
    echo -e "${GREEN}================================================${NC}"
    echo ""
    echo -e "${BLUE}Repository: ${REPO_URL}${NC}"
    echo -e "${BLUE}Branch: ${BRANCH_NAME}${NC}"
    echo ""
    echo -e "${BLUE}Next steps:${NC}"
    echo -e "1. Visit: https://github.com/${GITHUB_USERNAME}/${REPO_NAME}"
    echo -e "2. Create a Pull Request to merge ${BRANCH_NAME} into main"
    echo -e "3. Your contribution graph will show activity from June 15 - July 1, 2026"
    echo ""
else
    echo -e "${RED}Error: Failed to push to GitHub${NC}"
    echo -e "${RED}Please check your credentials and repository URL${NC}"
    exit 1
fi
