#!/usr/bin/env bash
set -euo pipefail

# Simple cross-platform commit helper (POSIX / Git Bash / WSL)
# Usage: ./commit_changes.sh [message] [remote] [branch]
# Defaults: message="Test local branch commit", remote="origin", branch="main"

MSG="${1:-Test local branch commit}"
REMOTE="${2:-origin}"
BRANCH="${3:-main}"

echo "commit_changes.sh - starting"

# Ensure git is available
if ! command -v git >/dev/null 2>&1; then
  echo "git command not found. Please install Git and ensure it's on PATH." >&2
  exit 1
fi

# Ensure we're in a git repo
if ! git rev-parse --is-inside-work-tree >/dev/null 2>&1; then
  echo "Current directory is not inside a Git repository. Please run this from the repository root." >&2
  exit 1
fi

# Get current branch
currentBranch=$(git rev-parse --abbrev-ref HEAD)
echo "Current branch: $currentBranch"
if [ "$currentBranch" != "$BRANCH" ]; then
  echo "This script requires you to be on branch '$BRANCH'. Please switch to that branch and re-run (current: $currentBranch)." >&2
  exit 1
fi

# Check for changes
changes=$(git status --porcelain)
if [ -z "$changes" ]; then
  echo "No changes detected. Nothing to commit."
  exit 0
fi

echo "Staging changes..."
git add -A

echo "Committing with message: $MSG"
if ! git commit -m "$MSG"; then
  echo "git commit failed (possible reasons: no changes to commit or merge conflicts)." >&2
  exit 1
fi

echo "Pulling latest from $REMOTE/$BRANCH (rebase)..."
if ! git pull --rebase "$REMOTE" "$BRANCH"; then
  echo "git pull --rebase failed. Resolve any conflicts and retry." >&2
  exit 1
fi

echo "Pushing to $REMOTE/$BRANCH..."
if ! git push "$REMOTE" "$BRANCH"; then
  echo "git push failed. Check authentication or remote access and try again." >&2
  exit 1
fi

echo "Changes successfully pushed to $REMOTE/$BRANCH"
exit 0

