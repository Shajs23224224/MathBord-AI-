#!/bin/sh
set -eu

APP_HOME=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
WRAPPER_JAR="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"
WRAPPER_URL="https://raw.githubusercontent.com/gradle/gradle/v9.6.1/gradle/wrapper/gradle-wrapper.jar"
REQUIRED_SHA256="497c8c2a7e5031f6aa847f88104aa80a93532ec32ee17bdb8d1d2f67a194a9c7"

if [ -n "${JAVA_HOME:-}" ]; then
  JAVACMD="$JAVA_HOME/bin/java"
else
  JAVACMD="java"
fi

command -v "$JAVACMD" >/dev/null 2>&1 || {
  echo "ERROR: Java 17+ is required and was not found." >&2
  exit 1
}

mkdir -p "$(dirname "$WRAPPER_JAR")"
need_download=false
if [ ! -f "$WRAPPER_JAR" ]; then
  need_download=true
elif command -v sha256sum >/dev/null 2>&1; then
  actual_sha256=$(sha256sum "$WRAPPER_JAR" | cut -d ' ' -f 1)
  [ "$actual_sha256" = "$REQUIRED_SHA256" ] || need_download=true
elif command -v shasum >/dev/null 2>&1; then
  actual_sha256=$(shasum -a 256 "$WRAPPER_JAR" | cut -d ' ' -f 1)
  [ "$actual_sha256" = "$REQUIRED_SHA256" ] || need_download=true
else
  echo "ERROR: sha256sum or shasum is required to verify gradle-wrapper.jar." >&2
  exit 1
fi

if [ "$need_download" = true ]; then
  command -v curl >/dev/null 2>&1 || { echo "ERROR: curl is required to provision gradle-wrapper.jar." >&2; exit 1; }
  tmp_jar="$WRAPPER_JAR.tmp"
  rm -f "$tmp_jar"
  curl -fsSL --retry 3 --connect-timeout 15 "$WRAPPER_URL" -o "$tmp_jar"
  actual_sha256=$(sha256sum "$tmp_jar" | cut -d ' ' -f 1)
  if [ "$actual_sha256" != "$REQUIRED_SHA256" ]; then
    rm -f "$tmp_jar"
    echo "ERROR: Gradle Wrapper JAR checksum mismatch." >&2
    exit 1
  fi
  mv "$tmp_jar" "$WRAPPER_JAR"
fi

exec "$JAVACMD" -Dfile.encoding=UTF-8 -Dorg.gradle.appname=gradlew -jar "$WRAPPER_JAR" "$@"