#!/usr/bin/env bash
set -euo pipefail

APK="android/app/build/outputs/apk/release/app-release-unsigned.apk"
AAB="android/app/build/outputs/bundle/release/app-release.aab"

[[ -s "$APK" ]] || { echo "Missing Android release APK: $APK"; exit 1; }
[[ -s "$AAB" ]] || { echo "Missing Android release AAB: $AAB"; exit 1; }

unzip -t "$APK" >/dev/null
unzip -t "$AAB" >/dev/null

echo "Android release artifacts verified:"
echo "  APK: $APK"
echo "  AAB: $AAB"