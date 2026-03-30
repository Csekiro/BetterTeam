# Repository Guidelines

## Project Structure & Module Organization
This repository is a Fabric mod built with Gradle and Loom. Common code lives in `src/main/java/Csekiro`, with gameplay injections under `src/main/java/Csekiro/mixin` and shared logic in helpers such as `Csekiro.util.TeamHooks`. Client-only code and data generation entrypoints live in `src/client/java/Csekiro/client`. Mod metadata and mixin configs are in `src/main/resources` and `src/client/resources`. Treat `build/` and `run/` as generated output; do not commit changes from them.

## Build, Test, and Development Commands
Use the Gradle wrapper from the repository root:

- `.\gradlew.bat build` builds, remaps, and packages the mod jar.
- `.\gradlew.bat runClient` launches a local Minecraft client with the mod loaded.
- `.\gradlew.bat runServer` starts a local dedicated server for server-side checks.
- `.\gradlew.bat runDatagenClient` runs client-side data generation when datagen code is added or updated.

The first run may download Gradle, Minecraft, and Fabric dependencies.

## Coding Style & Naming Conventions
Target Java 21 and keep source files UTF-8 encoded. Follow the existing Java style: 4-space indentation, opening braces on the same line, and short methods with early returns where possible. Preserve the current package layout exactly, including the capitalized top-level package `Csekiro`. Use `PascalCase` for classes, `camelCase` for methods and fields, and name mixins after their target classes, for example `ProjectileEntityMixin`. Keep entrypoint classes minimal and move reusable logic into focused utility classes.

## Testing Guidelines
There is no automated test suite or coverage gate yet. Validate changes with `.\gradlew.bat build` and manual in-game checks through `runClient` or `runServer`, depending on whether the change is client- or server-facing. In pull requests, document the scenario tested and the affected entities or mechanics. If automated tests are introduced, place them under `src/test/java` and name them `*Test`.

## Commit & Pull Request Guidelines
Current history uses short, imperative commit subjects such as `init` and `projectile ignore same team`. Keep commits focused on one behavior change and write concise subjects in that style. Pull requests should include a short summary, linked issue if applicable, validation steps, and screenshots or clips for visible gameplay changes. Call out any updated mixins, resources, or generated data explicitly.
