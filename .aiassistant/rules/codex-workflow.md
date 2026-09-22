---
apply: always
---

# Codex workflow rules

## Code changes

- Before modifying source code, explain which files will be created or modified and why.
- Wait for explicit user confirmation before applying the proposed code changes.
- Do not modify files unrelated to the requested task.
- Do not make additional improvements, refactoring, or cleanup unless explicitly requested.
- After applying code changes, stop and report which files were modified.
- Do not automatically run tests, create commits, or push after modifying code.

## Tests and commands

- Do not run tests, builds, or other commands automatically after changing code.
- Wait for explicit user confirmation before running tests or builds.
- For Spring Boot tests using the `local` profile, follow the rules in the "Local database access" section.
- If a test or build fails after all required external resources have been made accessible, report the error and stop.
- Do not automatically modify source code or configuration to fix a failed test.
- Wait for explicit user instructions before attempting any correction.

## Local database access

- Tests using the Spring profile `local` require PostgreSQL on `localhost:5432`.
- Always run local-profile tests using:
  `./mvnw test -Dspring.profiles.active=local`
- BEFORE running this Maven command, request the permissions required to access PostgreSQL on `localhost:5432`.
- Never execute local-profile tests first in the restricted sandbox.
- Do not start Maven until the required localhost/database access has been granted.
- If access to `localhost:5432` cannot be obtained, stop and ask the user for authorization.
- A JDBC connection failure caused by sandbox network restrictions must not be treated as an application or test failure.
- Never modify source code, application configuration, datasource configuration, or test configuration to work around sandbox network restrictions.
- If the database is accessible and the tests fail for an application-related reason, report the failure and stop.

## Git

- Never create a commit unless explicitly requested by the user.
- Never push to a remote repository unless explicitly requested by the user.
- Never merge, rebase, reset, cherry-pick, delete branches, or perform other history-changing Git operations unless explicitly requested by the user.
- Do not create a commit for a development task unless the required tests have completed successfully.
- If the required tests fail, do not commit and do not push.
- Before committing, report exactly which files will be included in the commit.
- Before committing, propose a commit message and wait for explicit user confirmation.
- Include only files related to the requested task in the commit.
- Do not include unrelated staged or unstaged changes.
- After creating a commit, stop and report the commit hash and included files.
- Do not automatically push after a successful commit.
- Before pushing, report the local branch and remote branch that will be used.
- Wait for a separate explicit user confirmation before pushing.
- Do not automatically merge a feature branch into `develop` after pushing it.

## Safety

- Never expose, print, commit, or copy passwords, tokens, API keys, client secrets, or other credentials.
- Do not modify secret or environment-specific configuration unless explicitly requested.
- Do not add secrets or local credentials to Git.
- Do not display secret values when reporting configuration or command results.