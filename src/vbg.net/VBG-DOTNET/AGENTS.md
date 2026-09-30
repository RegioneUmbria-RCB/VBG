# AGENTS.md

SIGePro monorepo (Italian municipal PA software). ~66 projects under `projects/`: legacy .NET Framework 4.8 WebForms apps plus newer ASP.NET Core 10 services. Many shared libraries multi-target `net10.0;net48` or `netstandard2.0` — when editing a shared lib, do not break the net48 leg. Code, comments, commit messages and domain terms are in Italian.

## Solution map (root `*.slnx`, VS 2026 XML solutions)

Each `.slnx` at the root is an app boundary — open/build the smallest one for the task, not `tutto.slnx` (everything, slow):

- `AreaRiservata.slnx` — legacy frontoffice WebForms, entry `projects/Init.Sigepro.FrontEnd` (net48)
- `Backoffice.slnx` — legacy backoffice WebForms, entry `projects/Sigepro.net` (net48, huge old-style csproj)
- `AreaRiservataCore.slnx` — new Blazor frontoffice, entry `projects/AreaRiservataCore` (net10)
- `DomandaOnLinePNRR.slnx`, `ConfiguratoreCalcoli.slnx`, `GeneratoreRiepiloghiHtml.slnx`, `VBG.Backend.Protocollo.slnx`, `VBG.Backend.SIT.slnx`, `VBG.AspnetCore.Agent.slnx` — ASP.NET Core services, each with a matching root `dockerfile-*`
- `VbgAppHost.slnx` — .NET Aspire AppHost for local dev (see below)
- `Legacy/`, `ProgettiLibreriePerSchedeDinamiche/`, `ProgettiTestPerSIT/` — per-municipality verticalizzazioni (e.g. Firenze, Modena, JesiSIT) and their tests, mostly outside the main solutions

## Build

- SDKs: .NET 9 + 10 installed; net48 builds need MSBuild/VS on Windows. CLI builds of the legacy WebForms solutions generally fail — build those with Visual Studio (`VSBuild` is what CI uses).
- `dotnet build <Solution>.slnx` works for the ASP.NET Core solutions (requires .NET SDK 10+).
- Custom configurations: `ReleaseFramework` (Backoffice/Protocollo solutions), `ReleaseNoSVN` (used only as the docker `BUILD_CONFIGURATION` arg). Default to `Debug`/`Release` locally.
- **`Properties/AssemblyInfo.cs` is gitignored and auto-generated**: root `Directory.Build.targets` copies `Utils/TortoiseSVN/AssemblyInfo.cs` into each project before build. Never create or commit it. Docker builds must delete/skip `Directory.Build.targets` (CI does `DeleteFiles` on it before `docker build`; root dockerfiles don't rely on it).
- NuGet: `VBG.*` packages (e.g. `VBG.BlazorComponentsLibrary`, `VBG.DatiDinamici.Agid`) come from a private Azure DevOps feed via package source mapping. If restore fails on a `VBG.*` package: copy `projects/<App>/nuget.config.template` to `nuget.config` (same folder) and fill in a DevOps PAT (Packaging/Build/Release: Read). **Never commit `nuget.config`** — it is gitignored on purpose. Docker builds take the PAT via `--build-arg PAT=...` instead (root `dockerfile-*` pass it to `dotnet nuget add source`); VS reads it from the `DockerfileBuildArguments` msbuild property.
- Generated assets, do not edit/commit: CSS in `AreaRiservataCore/wwwroot/css/area-riservata/` and `Init.Sigepro.FrontEnd/styles/` is compiled from LESS (`compilerconfig.json`, BuildWebCompiler2022, Release builds); `ConfiguratoreCalcoli/wwwroot/webcomponent/v2/dist/*.js` is generated.

## Tests

- xunit v3 everywhere except `PersonalLib2.Tests` (MSTest). Most test projects target **net48** (Windows-only) — `dotnet test projects/<Name>.Tests/<Name>.Tests.csproj`.
- Test locations: `projects/*.Tests`, `projects/Frontoffice/*Tests`, `projects/Tests/`, plus per-municipality tests in `ProgettiTestPerSIT/`.
- CI does not run tests — verify locally before considering work done.

## Local full-stack dev

`dotnet run --project projects/VbgAppHost` (Aspire, net9) orchestrates: local MySQL container + Java backend containers pulled from internal registry `registry` (vbg.security, vbg.backend, auth gateway, gotenberg) + AreaRiservataCore + AspnetCoreServices. Requires Docker Desktop, access to the internal registry, and `C:\temp\vbg-filesystem` (bind-mounted into the backend). Containers are `Persistent` lifetime and reuse fixed ports 3306/8080/8180/8280/3000.

## CI / release

Azure DevOps pipelines are the `build-*.yml` files at root (no GitHub Actions). Trigger: `main` + tags. Framework builds run on `windows-2025-vs2026` with VSBuild 18.0; docker images build on a self-hosted pool. Branch naming matters: non-main branches are expected to be `<major>.<minor>.<patch>` — the pipeline derives the image tag from the first two segments. Images push to the registry located at `registry`;

## Style

Root `.editorconfig` is enforced: 4 spaces, CRLF, UTF-8 **with BOM** for `.cs`/razor, no final newline at EOF, braces always, `var` preferred, `_camelCase` private fields. Async misuse is an **error**, not a warning: CS4014, CS1998, CS0508, VSTHRD110/200/103, S3445, CA2017.
