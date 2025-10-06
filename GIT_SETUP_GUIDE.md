# Git Setup & Push Guide

## 📋 Complete Step-by-Step Guide to Push Code to Git

---

## 🎯 Prerequisites

Before starting, ensure you have:
- ✅ Git installed on your machine
- ✅ GitHub/GitLab/Bitbucket account
- ✅ Repository created on GitHub (can be done during this process)

---

## 📝 Step 1: Initialize Git Repository

```bash
cd /Users/sharathir/Desktop/AUTOMATION/SNAPCHAT
git init
```

**Expected Output:**
```
Initialized empty Git repository in /Users/sharathir/Desktop/AUTOMATION/SNAPCHAT/.git/
```

---

## 📝 Step 2: Configure Git User (If not done already)

```bash
# Set your name
git config user.name "Your Name"

# Set your email
git config user.email "your.email@example.com"

# Verify configuration
git config --list
```

**For global configuration (applies to all repos):**
```bash
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"
```

---

## 📝 Step 3: Create .gitignore File

Create a `.gitignore` file to exclude unnecessary files:

```bash
cat > .gitignore << 'EOF'
# Maven
target/
pom.xml.tag
pom.xml.releaseBackup
pom.xml.versionsBackup
pom.xml.next
release.properties
dependency-reduced-pom.xml
buildNumber.properties
.mvn/timing.properties
.mvn/wrapper/maven-wrapper.jar

# IntelliJ IDEA
.idea/
*.iws
*.iml
*.ipr
out/

# Eclipse
.classpath
.project
.settings/
bin/

# VS Code
.vscode/

# macOS
.DS_Store
.AppleDouble
.LSOverride

# Test Results & Reports
*.log
*.class
*.jar
*.war
*.ear
*.zip
*.tar.gz
*.rar
allure-results/
surefire-reports/

# Appium Logs
appium.log

# Screenshots & Videos (optional - uncomment if you don't want to commit these)
# src/test/resources/screenshots/*
# src/test/resources/videos/*

# Temporary files
*.tmp
*.bak
*.swp
*~.nib
EOF
```

---

## 📝 Step 4: Check Status & Add Files

```bash
# Check what files will be added
git status

# Add all files
git add .

# Or add specific files
git add src/
git add pom.xml
git add README.md
git add *.md

# Verify files are staged
git status
```

**Expected Output:**
```
On branch main

No commits yet

Changes to be committed:
  (use "git rm --cached <file>..." to unstage)
        new file:   .gitignore
        new file:   CODE_IMPROVEMENTS_SUMMARY.md
        new file:   README.md
        new file:   TEST_RUN_SUMMARY.md
        new file:   pom.xml
        new file:   src/test/java/SnapchatActionTest.java
        ...
```

---

## 📝 Step 5: Make Initial Commit

```bash
git commit -m "Initial commit: Snapchat automation framework with improved code

- Added clean and optimized SnapchatActionTest
- Implemented Snapchat app verification with assertions
- Added comprehensive documentation
- Removed unnecessary code and improved performance by 24%
- Test passes successfully with 128 Snapchat elements detected"
```

**Expected Output:**
```
[main (root-commit) abc1234] Initial commit: Snapchat automation framework...
 XX files changed, XXXX insertions(+)
 create mode 100644 .gitignore
 create mode 100644 pom.xml
 ...
```

---

## 📝 Step 6: Create Repository on GitHub

### Option A: Using GitHub Website

1. Go to https://github.com
2. Click the **"+"** icon (top right) → **"New repository"**
3. Fill in:
   - **Repository name:** `snapchat-automation`
   - **Description:** `Snapchat mobile automation framework using Appium`
   - **Visibility:** Choose Public or Private
   - **Do NOT** initialize with README (we already have one)
4. Click **"Create repository"**
5. Copy the repository URL (e.g., `https://github.com/username/snapchat-automation.git`)

### Option B: Using GitHub CLI (if installed)

```bash
gh repo create snapchat-automation --public --source=. --remote=origin --push
```

---

## 📝 Step 7: Add Remote Repository

```bash
# Replace YOUR_USERNAME with your actual GitHub username
git remote add origin https://github.com/YOUR_USERNAME/snapchat-automation.git

# Verify remote is added
git remote -v
```

**Expected Output:**
```
origin  https://github.com/YOUR_USERNAME/snapchat-automation.git (fetch)
origin  https://github.com/YOUR_USERNAME/snapchat-automation.git (push)
```

---

## 📝 Step 8: Rename Branch to 'main' (if needed)

```bash
# Check current branch
git branch

# Rename to main if it's master
git branch -M main
```

---

## 📝 Step 9: Push to GitHub

```bash
# Push to remote repository
git push -u origin main
```

**You may be prompted for authentication:**

### Authentication Options:

#### Option 1: Personal Access Token (Recommended)
1. Go to GitHub → Settings → Developer settings → Personal access tokens → Tokens (classic)
2. Click "Generate new token (classic)"
3. Select scopes: `repo` (full control of private repositories)
4. Generate and copy the token
5. When prompted for password, use the token instead

#### Option 2: SSH Key
```bash
# Generate SSH key (if you don't have one)
ssh-keygen -t ed25519 -C "your.email@example.com"

# Copy public key
cat ~/.ssh/id_ed25519.pub

# Add to GitHub: Settings → SSH and GPG keys → New SSH key
```

Then use SSH URL instead:
```bash
git remote set-url origin git@github.com:YOUR_USERNAME/snapchat-automation.git
git push -u origin main
```

**Expected Output:**
```
Enumerating objects: XX, done.
Counting objects: 100% (XX/XX), done.
Delta compression using up to X threads
Compressing objects: 100% (XX/XX), done.
Writing objects: 100% (XX/XX), XX.XX KiB | XX.XX MiB/s, done.
Total XX (delta X), reused 0 (delta 0), pack-reused 0
To https://github.com/YOUR_USERNAME/snapchat-automation.git
 * [new branch]      main -> main
Branch 'main' set up to track remote branch 'main' from 'origin'.
```

---

## 📝 Step 10: Verify on GitHub

1. Go to your repository URL: `https://github.com/YOUR_USERNAME/snapchat-automation`
2. Verify all files are uploaded
3. Check that README.md is displayed correctly

---

## 🔄 Future Updates: Making Changes & Pushing

After making changes to your code:

```bash
# 1. Check what changed
git status

# 2. View specific changes
git diff

# 3. Add changes
git add .
# Or add specific files
git add src/test/java/SnapchatActionTest.java

# 4. Commit changes
git commit -m "Description of changes made"

# 5. Push to GitHub
git push origin main
```

---

## 📋 Common Git Commands Cheat Sheet

| Command | Description |
|---------|-------------|
| `git status` | Check status of files |
| `git add .` | Stage all changes |
| `git add <file>` | Stage specific file |
| `git commit -m "message"` | Commit with message |
| `git push` | Push to remote |
| `git pull` | Pull latest changes |
| `git log` | View commit history |
| `git log --oneline` | View compact history |
| `git diff` | View uncommitted changes |
| `git branch` | List branches |
| `git checkout -b <branch>` | Create new branch |
| `git checkout <branch>` | Switch branch |
| `git merge <branch>` | Merge branch |
| `git remote -v` | View remote repositories |

---

## 🛠️ Troubleshooting

### Issue 1: Authentication Failed
**Solution:**
- Use Personal Access Token instead of password
- Or set up SSH key authentication

### Issue 2: Remote Already Exists
```bash
git remote remove origin
git remote add origin <your-repo-url>
```

### Issue 3: Branch Name Issues
```bash
git branch -M main
```

### Issue 4: Large Files Error
```bash
# Remove large files from staging
git rm --cached <large-file>

# Add to .gitignore
echo "<large-file>" >> .gitignore
```

### Issue 5: Merge Conflicts
```bash
# Pull latest changes first
git pull origin main

# Resolve conflicts in files
# Then commit and push
git add .
git commit -m "Resolved merge conflicts"
git push origin main
```

---

## 📊 Repository Structure After Push

```
snapchat-automation/
├── .git/                          # Git metadata
├── .gitignore                     # Ignored files
├── pom.xml                        # Maven configuration
├── README.md                      # Project documentation
├── CODE_IMPROVEMENTS_SUMMARY.md   # Code improvements
├── TEST_RUN_SUMMARY.md           # Test results
├── GIT_SETUP_GUIDE.md            # This guide
├── src/
│   ├── main/java/                # Main source (empty for now)
│   └── test/
│       ├── java/                 # Test files
│       │   ├── SnapchatActionTest.java
│       │   └── ...
│       └── resources/            # Test resources
│           ├── config/
│           ├── testdata/
│           └── ...
└── target/                       # Build output (ignored)
```

---

## ✅ Best Practices

1. **Commit Frequently:** Small, focused commits are better than large ones
2. **Write Clear Messages:** Describe what and why, not how
3. **Pull Before Push:** Always pull latest changes before pushing
4. **Use Branches:** Create feature branches for new work
5. **Review Before Commit:** Use `git diff` to review changes
6. **Don't Commit Sensitive Data:** Use .gitignore for passwords, tokens, etc.
7. **Keep Target/ Out:** Never commit build artifacts

---

## 🎯 Quick Start Commands

```bash
# Complete setup in one go
cd /Users/sharathir/Desktop/AUTOMATION/SNAPCHAT
git init
git add .
git commit -m "Initial commit: Snapchat automation framework"
git remote add origin https://github.com/YOUR_USERNAME/snapchat-automation.git
git branch -M main
git push -u origin main
```

---

## 📱 Next Steps After Pushing

1. ✅ Add CI/CD pipeline (GitHub Actions)
2. ✅ Set up branch protection rules
3. ✅ Add collaborators (if team project)
4. ✅ Create issues for future improvements
5. ✅ Set up project board for tracking

---

**Need Help?**
- GitHub Docs: https://docs.github.com
- Git Documentation: https://git-scm.com/doc
- Git Cheat Sheet: https://education.github.com/git-cheat-sheet-education.pdf

