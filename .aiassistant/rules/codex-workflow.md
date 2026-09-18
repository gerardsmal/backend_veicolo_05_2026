---
apply: always
---

# Codex workflow rules

## Code changes
- Before modifying source code, explain which files will be created or modified and why.
- Wait for explicit user confirmation before applying the proposed code changes.
- Do not modify files unrelated to the requested task.

## Tests and commands
- Do not run tests, builds, or other commands automatically after changing code.
- Wait for explicit user confirmation before running tests or builds.
- For Spring Boot tests that require the local profile, use:
  `./mvnw test -Dspring.profiles.active=local`
- If a command requires access outside the sandbox, including localhost, network access, or external services, ask for explicit user confirmation before proceeding.
- If a test or build fails, report the error and stop. Do not automatically modify code to fix it.

## Git
- Never create a commit unless explicitly requested by the user.
- Never push to a remote repository unless explicitly requested by the user.
- Never merge, rebase, reset, or delete branches unless explicitly requested by the user.
- Before committing, report which files will be included in the commit.
- Before pushing, report the branch and remote that will be used.

## Safety
- Never expose, print, commit, or copy passwords, tokens, API keys, client secrets, or other credentials.
- Do not modify secret or environment-specific configuration unless explicitly requested.