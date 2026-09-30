#!/usr/bin/env bash
set -euo pipefail

COMMON_DIR="shared/src/commonMain"

if grep -RInE --include='*.kt' '(^|[[:space:]])import (android[.]|androidx[.]activity|androidx[.]compose[.]ui[.]window|java[.]awt|javax[.]swing)' "$COMMON_DIR"; then
    echo "Architecture check failed: platform-specific import in commonMain."
    exit 1
fi

if grep -RInE --exclude-dir=.git --exclude='*.md' '(AIza[0-9A-Za-z_-]{20,}|ghp_[0-9A-Za-z]{20,}|sk-[A-Za-z0-9]{20,})' .; then
    echo "Architecture check failed: possible credential pattern detected."
    exit 1
fi

echo "Architecture checks passed."