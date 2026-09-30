$ErrorActionPreference = "Stop"

$exe = Get-ChildItem -Path "desktopApp/build/compose/binaries" -Recurse -Filter "*.exe" | Select-Object -First 1
$msi = Get-ChildItem -Path "desktopApp/build/compose/binaries" -Recurse -Filter "*.msi" | Select-Object -First 1

if (-not $exe) { throw "Windows EXE artifact was not produced." }
if (-not $msi) { throw "Windows MSI artifact was not produced." }
if ($exe.Length -le 0) { throw "Windows EXE artifact is empty." }
if ($msi.Length -le 0) { throw "Windows MSI artifact is empty." }

Write-Host "Windows artifacts verified:"
Write-Host "  EXE: $($exe.FullName)"
Write-Host "  MSI: $($msi.FullName)"