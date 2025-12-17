[CmdletBinding()]
param(
    [string]$Message = "add java docs and update readme",
    [string]$Remote = "origin",
    [string]$Branch = "local"
)

Write-Host "commit_changes.ps1 - starting" -ForegroundColor Cyan

# Ensure git is available
if (-not (Get-Command git -ErrorAction SilentlyContinue)) {
    Write-Error "git command not found. Please install Git and ensure it's on PATH."
    exit 1
}

# Ensure we're in a git repo
$inRepo = git rev-parse --is-inside-work-tree 2>$null
if ($LASTEXITCODE -ne 0 -or $inRepo -ne 'true') {
    Write-Error "Current directory is not inside a Git repository. Please run this from the repository root."
    exit 1
}

# Get current branch
$currentBranch = (git rev-parse --abbrev-ref HEAD) -replace "\r",""
Write-Host "Current branch: $currentBranch"
if ($currentBranch -ne $Branch) {
    Write-Error "This script requires you to be on branch '$Branch'. Please switch to that branch and re-run (current: $currentBranch)."
    exit 1
}

# Check for changes
$changes = git status --porcelain
if (-not $changes) {
    Write-Host "No changes detected. Nothing to commit." -ForegroundColor Yellow
    exit 0
}

Write-Host "Staging changes..."
git add -A
if ($LASTEXITCODE -ne 0) {
    Write-Error "git add failed"
    exit $LASTEXITCODE
}

Write-Host "Committing with message: $Message"
git commit -m "$Message"
if ($LASTEXITCODE -ne 0) {
    Write-Error "git commit failed (possible reasons: no changes to commit or merge conflicts)."
    exit $LASTEXITCODE
}

Write-Host "Pulling latest from $Remote/$Branch (rebase)..."
git pull --rebase $Remote $Branch
if ($LASTEXITCODE -ne 0) {
    Write-Error "git pull --rebase failed. Resolve any conflicts and retry."
    exit $LASTEXITCODE
}

Write-Host "Pushing to $Remote/$Branch..."
git push $Remote $Branch
if ($LASTEXITCODE -ne 0) {
    Write-Error "git push failed. Check authentication or remote access and try again."
    exit $LASTEXITCODE
}

Write-Host "Changes successfully pushed to $Remote/$Branch" -ForegroundColor Green
exit 0

